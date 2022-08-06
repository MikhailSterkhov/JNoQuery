package com.itzstonlex.jnq;

import com.itzstonlex.jnq.exception.JnqException;
import com.itzstonlex.jnq.field.FieldOperator;
import com.itzstonlex.jnq.field.FieldType;
import com.itzstonlex.jnq.impl.content.SchemaContent;
import com.itzstonlex.jnq.impl.content.TableContent;
import com.itzstonlex.jnq.impl.field.IndexDataField;
import com.itzstonlex.jnq.impl.field.ValueDataField;
import com.itzstonlex.jnq.sql.SQLConnection;
import com.itzstonlex.jnq.sql.SQLHelper;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SQLTest {

    private DataConnection connection;
    private TableContent usersTable;

    @Test
    @Order(0)
    void testConnectionSetup() throws JnqException {
        connection = new SQLConnection(SQLHelper.toH2JDBC(), "root", "password")
                .setMode("MySQL"); // !!! this command for tests, this is not required

        SchemaContent schemaContent = connection.getSchemaContent(SQLHelper.H2_DEFAULT_SCHEMA_NAME);
        usersTable = schemaContent.createTableContent("reg_users");
    }

    @Test
    @Order(1)
    void testContents() throws JnqException {
        connection.createRequest(usersTable)
                .toFactory()
                .<IndexDataField>fromQuery("CREATE TABLE IF NOT EXISTS `{content}` ({0}, {1}, {2}, {3})")

                .sessionAppender()
                    .append(IndexDataField.createPrimaryNotNullAutoIncrement("id"))
                    .append(IndexDataField.createNotNull(FieldType.VARCHAR, "name"))
                    .append(IndexDataField.createNotNull(FieldType.LONG, "register_date"))
                    .append(IndexDataField.createNotNull(FieldType.LONG, "last_update_date"))
                    .endpoint()

                .compile()
                .updateTransaction();

        // update schemes & tables content caches after table create.
        connection.updateContents();
        assertTrue(usersTable.exists());
    }

    @Test
    @Order(2)
    void testInsert() throws JnqException {
        testContents();

        connection.createRequest(usersTable)
                .toFactory()
                .<ValueDataField>fromQuery("INSERT INTO `{content}` ({name0}, {name1}, {name2}) VALUES (?, ?, ?)")

                .sessionAppender()
                    .append(ValueDataField.create("name", "itzstonlex"))
                    .append(ValueDataField.create("register_date", System.currentTimeMillis()))
                    .append(ValueDataField.create("last_update_date", System.currentTimeMillis()))
                    .endpoint()

                .compile()
                .updateTransactionAsync()

                .whenComplete((updateResponse, error) -> {

                    System.out.println("INSERT RESPONSE:");
                    System.out.println(" Inserted User ID: " + updateResponse.getGeneratedKey());
                    System.out.println(" - " + updateResponse.getAffectedRows() + " affected rows");
                    System.out.println();
                });
    }

    @Test
    @Order(3)
    void testFinder() {
        connection.createRequest(usersTable)
                .toFactory()
                .fromQuery("SELECT {select} FROM `{content}` {where} {group} {sort} LIMIT 50")

                .sessionSelector("{select}")
                    .withUpperCase("name").as("upper_name")
                    .withAll()

                .sessionFilter("{where}")
                    .and(ValueDataField.create("name", "itzstonlex"))
                    .endpoint()

                .sessionGroup()
                    .by(ValueDataField.create("id"))
                    .endpoint()

                .sessionSort()
                    .byDesc(ValueDataField.create("id"))
                    .byAsc(ValueDataField.create("name"))
                    .endpoint()

                .compile()
                .fetchFirstLineAsync()

                .whenComplete((response, throwable) -> {

                    System.out.println("USER FETCH RESPONSE:");

                    System.out.println(" ID: " + response.getNullableInt("id"));
                    System.out.println(" Name (in upper-case): " + response.getNullableString("upper_name"));
                    System.out.println(" Name (without case): " + response.getNullableString("name"));
                    System.out.println(" Register Time Millis: " + response.getNullableTimestamp("register_date").toGMTString());
                    System.out.println(" Last Update Time Millis: " + response.getNullableTimestamp("register_date").toGMTString());
                    System.out.println();

                    System.out.println(" Response indexes: " + response.getIndexes());
                    System.out.println(" Response labels names: " + response.getLabels());
                    System.out.println();
                });
    }

    @Test
    @Order(4)
    void testUpdate() {
        connection.createRequest(usersTable)
                .toFactory()
                .fromQuery("UPDATE `{content}` SET {0} {filter}")

                .sessionAppender()
                    .append(ValueDataField.create("last_update_date", System.currentTimeMillis()))
                    .endpoint()

                .sessionFilter()
                    .and(FieldOperator.MORE_OR_EQUAL, ValueDataField.create("id", 1))
                    .and(ValueDataField.create("name", "itzstonlex"))
                    .endpoint()

                .compile()
                .updateTransactionAsync()

                .whenComplete((updateResponse, throwable) -> {

                    System.out.println("UPDATE RESPONSE:");
                    System.out.println(" - " + updateResponse.getAffectedRows() + " affected rows");
                    System.out.println();
                });
    }

}
