import com.itzstonlex.jnq.DataConnection;
import com.itzstonlex.jnq.impl.content.SchemaContent;
import com.itzstonlex.jnq.impl.field.MappingDataField;
import com.itzstonlex.jnq.orm.ObjectMappingService;
import com.itzstonlex.jnq.orm.annotation.Mapping;
import com.itzstonlex.jnq.orm.annotation.MappingColumn;
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

        @MappingColumn
        private String name;

        @MappingColumn("register_date")
        private long registerTimeMillis;

        @MappingColumn("last_update_date")
        private long lastUpdateTimeMillis;
    }

    public static void main(String[] args)
            throws JnqObjectMappingException {

        final DataConnection connection = new SQLConnection(SQLHelper.toH2JDBC(), "root", "password");
        final SchemaContent schemaContent = connection.getSchemaContent(SQLHelper.H2_DEFAULT_SCHEMA_NAME);

        if (schemaContent == null) {
            System.out.println("wtf ?");
            return;
        }

        // setup mysql mode for H2 driver.
        connection.createRequest(schemaContent)
                .toFactory()
                .fromQuery("set mode MySQL;")
                .compile()
                .updateTransaction();

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
