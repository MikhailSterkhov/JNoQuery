package com.itzstonlex.jnq.orm.data.repository.compile;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

import javax.tools.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.*;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class RuntimeCompiler {

    JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
    MapClassLoader mapClassLoader = new MapClassLoader();

    List<JavaFileObject> compilationUnits = new ArrayList<>();

    Map<String, byte[]> classData = new LinkedHashMap<>();

    ClassDataFileManager classDataFileManager;

    @SneakyThrows
    public RuntimeCompiler() {
        classDataFileManager = new ClassDataFileManager(javaCompiler.getStandardFileManager(null, Locale.getDefault(), Charset.defaultCharset()));
    }

    public void addClass(@NonNull String className, @NonNull String code) {
        compilationUnits.add(new MemoryJavaSourceFileObject(className, code));
    }

    @SneakyThrows
    public void addClass(@NonNull Class<?> cls) {
        compilationUnits.add(new MemoryJavaClassFileObject(cls.getName()));
    }

    public void compile() {
        JavaCompiler.CompilationTask compilationTask = javaCompiler.getTask(null, classDataFileManager,
                new DiagnosticCollector<>(), null, null, compilationUnits);

        compilationTask.call();
        compilationUnits.clear();
    }

    public Class<?> getCompiledClass(@NonNull String className) {
        return mapClassLoader.findClass(className);
    }

    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    public static class MemoryJavaSourceFileObject extends SimpleJavaFileObject {

        String sourceCode;

        private MemoryJavaSourceFileObject(@NonNull String fileName,
                                           @NonNull String sourceCode) {

            super(URI.create("string:///" + fileName.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
            this.sourceCode = sourceCode;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return sourceCode;
        }
    }

    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    public class MemoryJavaClassFileObject extends SimpleJavaFileObject {

        String className;

        private MemoryJavaClassFileObject(@NonNull String className) {
            super(URI.create("string:///" + className.replace('.', '/') + Kind.CLASS.extension), Kind.CLASS);
            this.className = className;
        }

        @Override
        public OutputStream openOutputStream() {
            return new ClassDataOutputStream(className);
        }
    }


    public class MapClassLoader extends ClassLoader {

        @Override
        public Class<?> findClass(@NonNull String name) {
            return defineClass(name, classData.get(name), 0, classData.get(name).length);
        }
    }

    public class ClassDataFileManager extends ForwardingJavaFileManager<StandardJavaFileManager> {

        private ClassDataFileManager(StandardJavaFileManager standardJavaFileManager) {
            super(standardJavaFileManager);
        }

        @Override
        public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) {
            return new MemoryJavaClassFileObject(className);
        }
    }

    @RequiredArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    public class ClassDataOutputStream extends OutputStream {

        String className;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        @Override
        public void write(int b) {
            byteArrayOutputStream.write(b);
        }

        @Override
        public void close() throws IOException {
            classData.put(className, byteArrayOutputStream.toByteArray());

            super.close();
        }
    }

}