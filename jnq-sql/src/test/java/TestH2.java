import com.itzstonlex.jnq.DataConnection;
import com.itzstonlex.jnq.exception.JnqException;
import com.itzstonlex.jnq.field.FieldType;
import com.itzstonlex.jnq.impl.content.SchemaContent;
import com.itzstonlex.jnq.impl.field.IndexDataField;
import com.itzstonlex.jnq.impl.field.ValueDataField;
import com.itzstonlex.jnq.response.ResponseLine;
import com.itzstonlex.jnq.sql.SQLConnection;

public class TestH2 {

    public static void main(String[] args)
    throws JnqException {

        DataConnection connection = new SQLConnection("org.h2.Driver", "jdbc:h2:mem:", "root", "password");
        SchemaContent schemaContent = connection.getSchemaContent("public");

        if (schemaContent == null) {
            System.out.println("wtf ?");
            return;
        }
        
        connection.createRequest(schemaContent)
                .toFactory()
                .<IndexDataField>fromQuery("CREATE TABLE IF NOT EXISTS `reg_users` ({0}, {1}, {2}, {3})")

                .sessionAppender()
                    .append(IndexDataField.createPrimaryNotNullAutoIncrement("id"))
                    .append(IndexDataField.createNotNull(FieldType.VARCHAR, "name"))
                    .append(IndexDataField.createNotNull(FieldType.LONG, "register_date"))
                    .append(IndexDataField.createNotNull(FieldType.LONG, "last_update_date"))
                    .backward()

                .compile()
                .updateTransaction();

        connection.createRequest(schemaContent)
                .toFactory()
                .<ValueDataField>fromQuery("INSERT INTO `{content}`.`reg_users` ({name0}, {name1}, {name2}) VALUES (?, ?, ?)")

                .sessionAppender()
                    .append(ValueDataField.create("name", "itzstonlex"))
                    .append(ValueDataField.create("register_date", System.currentTimeMillis()))
                    .append(ValueDataField.create("last_update_date", System.currentTimeMillis()))
                    .backward()

                .compile()
                .updateTransactionAsync()

                .whenComplete((updateResponse, error) ->  {

                    System.out.println(updateResponse.getAffectedRows() + " affected rows");

                    System.out.println(updateResponse.supportsGeneratedKey());
                    System.out.println("new user id - " + updateResponse.getGeneratedKey());
                })
                .join();

        connection.createRequest(schemaContent)
                .toFactory()
                .fromQuery("SELECT {selection} FROM `reg_users` WHERE {filter}")

                .sessionSelector("{selection}")
                    .withAll()

                .sessionFilter()
                    .and(ValueDataField.create("name", "itzstonlex"))
                    .backward()

                .compile()
                .fetchTransactionAsync()

                .whenComplete((responses, error) -> {

                    System.out.printf("Find %s users count!%n", responses.size());

                    for (ResponseLine responseLine : responses) {

                        int id = responseLine.nextNullableInt();
                        long registerDate = responseLine.getNullableLong(3);

                        String name = responseLine.getNullableString("name");

                        System.out.printf("-> User(id=%s, name=%s, register_date=%s)%n", id, name, registerDate);
                    }
                })
                .join();
    }

}
