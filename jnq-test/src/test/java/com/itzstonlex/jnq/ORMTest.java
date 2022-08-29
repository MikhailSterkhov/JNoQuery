package com.itzstonlex.jnq;

import com.itzstonlex.jnq.connection.H2Connection;
import com.itzstonlex.jnq.content.field.FieldOperator;
import com.itzstonlex.jnq.content.field.type.EntryField;
import com.itzstonlex.jnq.content.type.SchemaContent;
import com.itzstonlex.jnq.jdbc.JDBCHelper;
import com.itzstonlex.jnq.orm.annotation.*;
import com.itzstonlex.jnq.orm.data.ObjectMappingService;
import com.itzstonlex.jnq.orm.exception.JnqObjectMappingException;
import lombok.Getter;
import lombok.ToString;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ORMTest {

    @Mapping // annotation from JNQ
    @Getter
    @ToString
    public static class User {

        @MappingPrimary // annotation from JNQ
        @MappingID // annotation from JNQ
        private int id;

        @MappingColumn // annotation from JNQ
        private String name;

        @MappingColumn("register_date") // annotation from JNQ
        private long registerTimeMillis;

        @MappingColumn("last_update_date") // annotation from JNQ
        @MappingLastUpdateTime(unit = TimeUnit.MILLISECONDS) // annotation from JNQ
        private long lastUpdateTimeMillis;

        // required for new instance calling.
        private User() {}

        // You object realisations.
        public User(String name, long registerTimeMillis, long lastUpdateTimeMillis) {
            this.name = name;
            this.registerTimeMillis = registerTimeMillis;
            this.lastUpdateTimeMillis = lastUpdateTimeMillis;
        }

        @MappingInitMethod // annotation from JNQ
        private void init() {
            System.out.println("User " + name + " was initialized!");
        }
    }

    private ObjectMappingService objectMappings;

    @Test
    @Order(0)
    void testSetupConnection() throws JnqException {
        JnqConnection connection = new H2Connection("root", "password");

        // Called here to automatically generate the required schema.
        SchemaContent defaultSchema = connection.getSchema(JDBCHelper.H2_DEFAULT_SCHEMA_NAME);
        objectMappings = ObjectMappingService.instanceOf(defaultSchema, "reg_users");

        // because some syntax is not supported in the H2 driver, then in order for some ORM functionality to work correctly, you need to set a different SQL syntax mode.
        JDBCHelper.updateSyntaxMode(defaultSchema, "MySQL");
    }

    @Test
    @Order(1)
    void testSave() throws JnqObjectMappingException {
        objectMappings.getRequestFactory()
                .newUpdate()

                .markAutomapping()
                .compile()

                .save(new User("itzstonlex", System.currentTimeMillis(), System.currentTimeMillis()))
                .thenAccept(userID -> {

                    assertEquals(userID, 1);

                    System.out.println("Inserted User ID - " + userID);
                });
    }

    @Test
    @Order(2)
    void testFetch() throws JnqObjectMappingException {
        List<User> usersList = objectMappings.getRequestFactory()
                .newFinder()

                .limit(100)
                .and(FieldOperator.MORE_OR_EQUAL, EntryField.create("id", 1))

                .markAutomapping()
                .compile()

                .fetchAll(User.class);

        assertEquals(usersList.size(), 1);

        System.out.println("Find users count: " + usersList.size());

        for (User user : usersList) {
            assertEquals(user.getName(), "itzstonlex");

            System.out.println(" - User: " + user);
        }
    }

}
