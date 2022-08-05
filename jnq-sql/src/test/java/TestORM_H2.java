import com.itzstonlex.jnq.DataConnection;
import com.itzstonlex.jnq.impl.content.SchemaContent;
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

import java.util.List;

public class TestORM_H2 {

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

    public static void main(String[] args)
    throws JnqObjectMappingException {

        final DataConnection connection = new SQLConnection(SQLHelper.toH2JDBC(), "root", "password")
                .setMode("MySQL");

        // getting objects-mapper service.
        final ObjectMappingService<MappingDataField> objectMappingService = connection.getObjectMappings();

        // insert new user value.
        objectMappingService.getRequestFactory("reg_users")
                .newUpdate()

                .withAutomapping()
                .compile()

                .map(new User("itzstonlex", System.currentTimeMillis(), System.currentTimeMillis()))
                .thenAccept(userID -> {

                    System.out.println("Inserted User ID - " + userID);
                });

        // getting first 100 users values.
        List<User> usersList = objectMappingService.getRequestFactory("reg_users")
                .newFinder()

                .withLimit(100)

                .withAutomapping()
                .compile()

                .fetchAll(User.class);

        System.out.println("Users found count: " + usersList.size());

        for (User user : usersList) {
            System.out.println(" - " + user.toString());
        }
    }

}
