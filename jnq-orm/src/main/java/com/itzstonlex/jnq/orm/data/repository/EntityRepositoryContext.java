package com.itzstonlex.jnq.orm.data.repository;

import com.itzstonlex.jnq.content.field.FieldOperator;
import com.itzstonlex.jnq.content.field.type.EntryField;
import com.itzstonlex.jnq.orm.data.ObjectMappingService;
import com.itzstonlex.jnq.orm.data.repository.annotation.EntityParam;
import com.itzstonlex.jnq.orm.data.repository.annotation.FindEntity;
import com.itzstonlex.jnq.orm.data.repository.annotation.FindEntityList;
import com.itzstonlex.jnq.orm.data.repository.annotation.SaveEntity;
import com.itzstonlex.jnq.orm.data.repository.compile.RuntimeCompiler;
import com.itzstonlex.jnq.orm.exception.JnqObjectMappingException;
import com.itzstonlex.jnq.orm.request.MappingRequestFactory;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class EntityRepositoryContext {

    ObjectMappingService objectMappingService;

    RuntimeCompiler runtimeCompiler = new RuntimeCompiler();

    Map<Class<?>, Object> weakEntityRepositories = new WeakHashMap<>();

    @SuppressWarnings("unchecked")
    private <T> T checkAvailability(@NonNull Class<T> cls) {
        if (weakEntityRepositories.containsKey(cls)) {
            return (T) weakEntityRepositories.get(cls);
        }

        return null;
    }

    public <T> T makeRepository(@NonNull Class<T> cls)
    throws JnqObjectMappingException {

        T repository = checkAvailability(cls);

        if (repository == null) {
            repository = generateImplementationBy(cls);

            if (repository == null) {
                throw new JnqObjectMappingException("Cannot be can generate an implementation for " + cls);
            }

            weakEntityRepositories.put(cls, repository);
        }

        return repository;
    }

    private <T> T generateImplementationBy(Class<T> cls) {
        try {
            String className = generateSourceName(cls.getSimpleName());
            String sourceCode = generateSourceCode(cls, className);

            runtimeCompiler.addClass(className, sourceCode);
            runtimeCompiler.compile();

            return getCompiledRelease(className);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T getCompiledRelease(String className)
    throws Exception {

        Class<?> compiledClass = runtimeCompiler.getCompiledClass(className);
        return (T) compiledClass.getConstructor(MappingRequestFactory.class).newInstance(objectMappingService.getRequestFactory());
    }

    private int generateSourceNameCode() {
        return ThreadLocalRandom.current().nextInt(100, 1000);
    }

    private String generateSourceName(String repositoryName) {
        return repositoryName.concat(Integer.toString(generateSourceNameCode())).concat("_Impl");
    }

    private String generateSourceCode(Class<?> source, String className)
    throws JnqObjectMappingException {

        SourceCodeBuilder sourceCodeBuilder = new SourceCodeBuilder(className, source.getSimpleName())
                .importType(source)
                .importType(MappingRequestFactory.class)
                .importType(FieldOperator.class)
                .importType(EntryField.class)

                .makeConstructor();

        for (Method method : source.getMethods()) {
            Parameter[] parameters = method.getParameters();

            sourceCodeBuilder.makeOverrideMethod(
                    method.isAnnotationPresent(SaveEntity.class) ? null : method.getReturnType(),
                    method.getName(),

                    parameters
            );

            if (method.isAnnotationPresent(SaveEntity.class)) {
                sourceCodeBuilder.beginBody()
                        .makeLine("try {")
                        .makeLine("    requestFactory.beginSaving().checkAvailability().markAutomapping().compile().save(arg0);")
                        .makeLine("} catch (Exception ex) {")
                        .makeLine("    ex.printStackTrace();")
                        .makeLine("}")
                        .endpointBody();
            }
            else if (method.isAnnotationPresent(FindEntity.class)) {
                sourceCodeBuilder.beginBody()
                        .makeLine("try {")
                        .sourceCode.append("\n        return requestFactory.beginSearch()");

                for (Parameter parameter : parameters) {
                    EntityParam entityParam = parameter.getAnnotation(EntityParam.class);

                    if (entityParam == null) {
                        throw new JnqObjectMappingException("@EntityParam for " + parameter + "(" + method + ") is not found");
                    }

                    sourceCodeBuilder.sourceCode.append(".and(")
                            .append("FieldOperator.")
                            .append(entityParam.operator().name())
                            .append(", EntryField.create(")
                            .append("\"")
                            .append(entityParam.name())
                            .append("\", ")
                            .append(entityParam.name())
                            .append("))");
                }

                sourceCodeBuilder.sourceCode.append(".markAutomapping().compile().fetchFirst(")
                        .append(method.getReturnType().getName())
                        .append(".class);");

                sourceCodeBuilder
                        .makeLine("} catch (Exception ex) {")
                        .makeLine("    throw new RuntimeException(ex);")
                        .makeLine("}")
                        .endpointBody();
            }
            else if (method.isAnnotationPresent(FindEntityList.class)) {
                sourceCodeBuilder.beginBody()
                        .makeLine("return null;")
                        .endpointBody();
            }
            else {
                throw new JnqObjectMappingException("Cannot be override a method " + method);
            }
        }

        return sourceCodeBuilder.buildSourceCode();
    }

    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    static class SourceCodeBuilder {

        String className;

        StringBuilder sourceCode;

        public SourceCodeBuilder(String className, String sourceName) {
            this.className = className;
            this.sourceCode = new StringBuilder("\npublic final class " + className + " implements " + sourceName);

            beginBody();
        }

        public SourceCodeBuilder importType(Class<?> cls) {
            sourceCode.insert(0, "import " + cls.getName() + ";\n");
            return this;
        }

        private SourceCodeBuilder beginBody() {
            sourceCode.append(" {");
            return this;
        }

        private SourceCodeBuilder endpointBody() {
            sourceCode.append("\n}");
            return this;
        }

        public SourceCodeBuilder makeConstructor() {
            String mappingRequestFactoryVar = "requestFactory";

            sourceCode.append("\n\nprivate final ").append("MappingRequestFactory").append(" ").append(mappingRequestFactoryVar).append(";\n\n");
            sourceCode.append("public ").append(className).append("(").append("MappingRequestFactory").append(" ").append(mappingRequestFactoryVar).append(")");

            beginBody().makeLine("this." + mappingRequestFactoryVar + " = " + mappingRequestFactoryVar + ";").endpointBody();
            return this;
        }

        public SourceCodeBuilder makeOverrideMethod(Class<?> returnType, String name, Parameter... parameters) {
            sourceCode.append("\n\n@Override\npublic ")
                    .append(returnType == null ? "void" : returnType.getName())
                    .append(" ")
                    .append(name).append("(");

            String parametersLine;

            if (returnType != null) {
                parametersLine = Arrays.stream(parameters)
                        .filter(parameter -> parameter.isAnnotationPresent(EntityParam.class))

                        .map(parameter -> parameter.getType().getName() + " " + parameter.getDeclaredAnnotation(EntityParam.class).name())
                        .collect(Collectors.joining(", "));
            }
            else {
                parametersLine = Arrays.stream(parameters)
                        .map(Parameter::toString)
                        .collect(Collectors.joining(", "));
            }

            sourceCode.append(parametersLine);
            sourceCode.append(")");

            return this;
        }

        public SourceCodeBuilder makeLine(String line) {
            sourceCode.append("\n    ").append(line);
            return this;
        }

        public String buildSourceCode() {
            return endpointBody().sourceCode.toString();
        }
    }

}
