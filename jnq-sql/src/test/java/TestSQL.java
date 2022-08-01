import com.itzstonlex.jnq.DataConnection;
import com.itzstonlex.jnq.impl.content.TableContent;
import com.itzstonlex.jnq.exception.JnqException;
import com.itzstonlex.jnq.field.FieldType;
import com.itzstonlex.jnq.field.FieldOperator;
import com.itzstonlex.jnq.impl.field.IndexDataField;
import com.itzstonlex.jnq.impl.field.ValueDataField;
import com.itzstonlex.jnq.request.option.RequestConcurrency;
import com.itzstonlex.jnq.request.option.RequestFetchDirection;
import com.itzstonlex.jnq.request.option.RequestHoldability;
import com.itzstonlex.jnq.request.option.RequestType;
import com.itzstonlex.jnq.request.query.session.RequestSessionJoiner;
import com.itzstonlex.jnq.response.ResponseLine;
import com.itzstonlex.jnq.sql.SQLConnection;
import com.itzstonlex.jnq.sql.utility.SQLUtility;
import lombok.SneakyThrows;

public class TestSQL {

    @SneakyThrows({JnqException.class})
    public static void main(String[] args) {

        // create a sql-connection channel.
        String jdbcUrl = SQLUtility.toMysqlJDBC("test_scheme", "localhost", 3306);
        String driverCls = SQLUtility.MYSQL_DRIVER_CLASS;

        DataConnection connection = new SQLConnection(driverCls, jdbcUrl, "root", "");

        // management with a table.
        TableContent tableContent = connection.getTableContent("test_scheme", "registered_users");

        if (!tableContent.exists()) {
            tableContent.create()
                    .append(IndexDataField.createPrimaryNotNullAutoIncrement("id"))
                    .append(IndexDataField.createNotNull(FieldType.VAR_CHAR, "nickname"))
                    .append(IndexDataField.createNotNull(FieldType.INT, "age"))
                    .backward()

                    .compile()

                    .updateSync()
                    .thenRun(() -> System.out.printf("Table `%s` was success created!%n", tableContent.getName()));
        }

        // execution a no-sql queries by remote sql-connection.
        connection.createRequest(tableContent)
                .set(RequestType.FORWARD_ONLY)
                .set(RequestFetchDirection.REVERSE)
                .set(RequestConcurrency.UPDATABLE)
                .set(RequestHoldability.CLOSE_CURSORS_AT_COMMIT)

                .factory().newFind()

                .sessionSelector()
                    .withLowerCase("lower_nickname")
                    .withAll()

                .sessionJoiner()
                    .joinAt("users_ids", RequestSessionJoiner.Direction.LEFT, RequestSessionJoiner.Type.INNER, "user_id", "id")

                .sessionFilter()
                    .and(FieldOperator.LIKE, ValueDataField.create("nickname", ""))
                    .and(FieldOperator.MORE, ValueDataField.create("age", 18))
                    .or(FieldOperator.EQUAL, ValueDataField.create("age", 18))
                    .backward()

                .sessionOrder()
                    .and(FieldOperator.MORE, ValueDataField.create("age", 14))
                    .backward()

                .compile()

                .fetchFirstAsync()
                .thenAccept(TestSQL::handleFetchResponseLine);

        // execution a sql queries by remote sql-connection.
        connection.createRequest(tableContent).factory()
                .fromQuery("SELECT * FROM {scheme}.{table} WHERE {0}")

                .sessionFilter()
                    .and(FieldOperator.LESS, ValueDataField.create("id", 5))
                    .backward()

                .compile()

                .fetchSync()
                .thenAccept(response -> {

                    for (ResponseLine responseLine : response) {
                        handleFetchResponseLine(responseLine);
                    }
                });

        // common manipulations with a table.
        tableContent.clear().thenRun(() -> System.out.printf("Table `%s` was success clear!%n", tableContent.getName()));
        tableContent.drop().thenRun(() -> System.out.printf("Table `%s` was success dropped!%n", tableContent.getName()));

        // close a sql-connection.
        connection.close().thenRun(() -> System.out.println("connection was closed"));
    }

    private static void handleFetchResponseLine(ResponseLine responseLine) {
        int id = responseLine.getInt("id");
        int age = responseLine.getInt("age");

        String nickname = responseLine.getString("nickname");

        System.out.printf("Success find a `%s` (id=%s, age=%s)%n", nickname, id, age);
    }

}
