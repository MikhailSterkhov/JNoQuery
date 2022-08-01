import com.itzstonlex.jnq.DataConnection;
import com.itzstonlex.jnq.exception.JnqException;
import com.itzstonlex.jnq.field.FieldType;
import com.itzstonlex.jnq.impl.content.SchemaContent;
import com.itzstonlex.jnq.impl.field.IndexDataField;
import com.itzstonlex.jnq.impl.field.ValueDataField;
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
                .<IndexDataField>fromQuery("CREATE TABLE IF NOT EXISTS `users` ({0}, {1}, {2}, {3})")

                .sessionAppender()
                    .append(IndexDataField.createPrimaryNotNullAutoIncrement("id"))
                    .append(IndexDataField.createNotNull(FieldType.VARCHAR, "name"))
                    .append(IndexDataField.createNotNull(FieldType.LONG, "register_date"))
                    .append(IndexDataField.createNotNull(FieldType.LONG, "last_update_date"))
                    .backward()

                .compile()
                .updateTransaction()
                .join();

        connection.updateContents();
        connection.createRequest(schemaContent.getTableContent("users"))
                .toFactory()
                .<ValueDataField>fromQuery("INSERT INTO `users` ({name0}, {name1}, {name2}) VALUES (?, ?, ?)")

                .sessionAppender()
                    .append(ValueDataField.create("name", "itzstonlex"))
                    .append(ValueDataField.create("register_date", System.currentTimeMillis()))
                    .append(ValueDataField.create("last_update_date", System.currentTimeMillis()))
                    .backward()

                .compile()
                .updateTransaction()

                .thenAccept(updateResponse ->  {

                    System.out.println(updateResponse.getAffectedRows() + " affected rows!");
                })
                .join();
    }

}
