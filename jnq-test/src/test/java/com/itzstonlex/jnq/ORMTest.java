package com.itzstonlex.jnq;

import com.itzstonlex.jnq.connection.H2Connection;
import com.itzstonlex.jnq.field.FieldOperator;
import com.itzstonlex.jnq.impl.content.SchemaContent;
import com.itzstonlex.jnq.impl.field.MappingDataField;
import com.itzstonlex.jnq.jdbc.JDBCHelper;
import com.itzstonlex.jnq.orm.ObjectMappingService;
import com.itzstonlex.jnq.orm.annotation.Mapping;
import com.itzstonlex.jnq.orm.annotation.MappingColumn;
import com.itzstonlex.jnq.orm.annotation.MappingInitMethod;
import com.itzstonlex.jnq.orm.exception.JnqObjectMappingException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ORMTest {

    @Mapping // annotation from JNQ
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class User {

        @MappingColumn // annotation from JNQ
        private String name;

        @MappingColumn("register_date") // annotation from JNQ
        private long registerTimeMillis;

        @MappingColumn("last_update_date") // annotation from JNQ
        private long lastUpdateTimeMillis;

        @MappingInitMethod // annotation from JNQ
        private void init() {
            System.out.println("User " + name + " was initialized!");
        }
    }

    private ObjectMappingService<MappingDataField> objectMappings;

    @Test
    @Order(0)
    void testSetupConnection() throws JnqObjectMappingException {
        DataConnection connection = new H2Connection("root", "password");
        objectMappings = connection.getObjectMappings();

        // Called here to automatically generate the required schema.
        SchemaContent defaultSchema = connection.getSchema(JDBCHelper.H2_DEFAULT_SCHEMA_NAME);

        // because some syntax is not supported in the H2 driver, then in order for some ORM functionality to work correctly, you need to set a different SQL syntax mode.
        connection.createRequest(defaultSchema)
                .toFactory()
                .fromQuery("set mode MySQL")
                .compile()
                .updateTransaction();
    }

    @Test
    @Order(1)
    void testMap() throws JnqObjectMappingException {
        objectMappings.getRequestFactory("reg_users")
                .newUpdate()

                .withAutomapping()
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
        List<User> usersList = objectMappings.getRequestFactory("reg_users")
                .newFinder()

                .withLimit(100)
                .withInclude(MappingDataField.create(FieldOperator.MORE_OR_EQUAL, "id", 1))

                .withAutomapping()
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
