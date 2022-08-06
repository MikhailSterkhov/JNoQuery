package com.itzstonlex.jnq;

import com.itzstonlex.jnq.connection.H2Connection;
import com.itzstonlex.jnq.impl.field.MappingDataField;
import com.itzstonlex.jnq.orm.ObjectMappingService;
import com.itzstonlex.jnq.orm.annotation.Mapping;
import com.itzstonlex.jnq.orm.annotation.MappingColumn;
import com.itzstonlex.jnq.orm.annotation.MappingInitMethod;
import com.itzstonlex.jnq.orm.exception.JnqObjectMappingException;
import com.itzstonlex.jnq.sql.SQLConnection;
import com.itzstonlex.jnq.sql.SQLHelper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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
    void testSetupConnection() throws JnqObjectMappingException {
        DataConnection connection = new H2Connection("root", "password");
        objectMappings = connection.getObjectMappings();
    }

    @Test
    void testMap() throws JnqObjectMappingException {
        objectMappings.getRequestFactory("reg_users")
                .newUpdate()

                .withAutomapping()
                .compile()

                .map(new User("itzstonlex", System.currentTimeMillis(), System.currentTimeMillis()))
                .thenAccept(userID -> {

                    assertEquals(userID, 1);

                    System.out.println("Inserted User ID - " + userID);
                });
    }

    @Test
    void testFetch() throws JnqObjectMappingException {
        List<User> usersList = objectMappings.getRequestFactory("reg_users")
                .newFinder()

                .withLimit(100)

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
