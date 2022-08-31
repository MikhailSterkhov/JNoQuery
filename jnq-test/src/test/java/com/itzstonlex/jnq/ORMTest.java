package com.itzstonlex.jnq;

import com.itzstonlex.jnq.connection.H2Connection;
import com.itzstonlex.jnq.content.field.FieldOperator;
import com.itzstonlex.jnq.content.field.type.EntryField;
import com.itzstonlex.jnq.content.type.SchemaContent;
import com.itzstonlex.jnq.jdbc.JDBCHelper;
import com.itzstonlex.jnq.orm.User;
import com.itzstonlex.jnq.orm.UserRepository;
import com.itzstonlex.jnq.orm.data.ObjectMappingService;
import com.itzstonlex.jnq.orm.data.repository.EntityRepositoryContext;
import com.itzstonlex.jnq.orm.exception.JnqObjectMappingException;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ORMTest {

    private ObjectMappingService objectMappings;

    private User user;

    @Test
    @Order(0)
    void testSetupConnection() throws JnqException {
        JnqConnection connection = new H2Connection("root", "password");

        // Called here to automatically generate the required schema.
        SchemaContent defaultSchema = connection.getSchema(JDBCHelper.H2_DEFAULT_SCHEMA_NAME);
        objectMappings = ObjectMappingService.instanceOf(defaultSchema, "reg_users");

        // because some syntax is not supported in the H2 driver, then in order for some ORM functionality to work correctly, you need to set a different SQL syntax mode.
        JDBCHelper.updateSyntaxMode(defaultSchema, "MySQL");

        // Initialize user-data object.
        user = new User("itzstonlex", System.currentTimeMillis());
    }

    @Test
    @Order(1)
    void testRepository() throws JnqObjectMappingException {
        EntityRepositoryContext entityRepositoryContext = objectMappings.getEntityRepositoryContext();

        UserRepository repository = entityRepositoryContext.makeRepository(UserRepository.class);

        repository.save(user);

        User userByName = repository.findByName("itzstonlex");
        User userByID = repository.findByID(1);

        assertEquals(userByID.getId(), userByName.getId());
        assertEquals(userByID.getName(), userByName.getName());
    }

    @Test
    @Order(2)
    void testSaving() throws JnqObjectMappingException {
        objectMappings.getRequestFactory()
                .beginSaving()

                .checkAvailability()

                .markAutomapping()
                .compile()

                .save(user)
                .thenAccept(userID -> {

                    assertEquals(userID, 1);

                    user.setId(userID);
                    System.out.println("Inserted User ID - " + userID);
                });
    }

    @Test
    @Order(3)
    void testSearch() throws JnqObjectMappingException {
        List<User> usersList = objectMappings.getRequestFactory()
                .beginSearch()

                .markLimit(10)
                .and(FieldOperator.MORE_OR_EQUAL, EntryField.create("id", 1))

                .markAutomapping()
                .compile()

                .fetchAll(User.class);

        assertEquals(usersList.size(), 1);

        System.out.println("Find users count: " + usersList.size());

        for (User user : usersList) {
            assertEquals(user.getName(), "itzstonlex");

            System.out.println(" - " + user);
        }
    }

}
