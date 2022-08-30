package com.itzstonlex.jnq;

import com.itzstonlex.jnq.connection.H2Connection;
import com.itzstonlex.jnq.content.field.FieldOperator;
import com.itzstonlex.jnq.content.field.FieldType;
import com.itzstonlex.jnq.content.field.type.EntryField;
import com.itzstonlex.jnq.content.field.type.IndexField;
import com.itzstonlex.jnq.content.type.SchemaContent;
import com.itzstonlex.jnq.content.type.TableContent;
import com.itzstonlex.jnq.jdbc.JDBCHelper;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NoSQLTest {

    private JnqConnection connection;
    private TableContent usersTable;

    @Test
    @Order(0)
    void testSetupConnection() throws JnqException {
        connection = new H2Connection("root", "password");

        SchemaContent defaultSchema = connection.getSchema(JDBCHelper.H2_DEFAULT_SCHEMA_NAME);
        usersTable = defaultSchema.newTableInstance("reg_users");

        JDBCHelper.updateSyntaxMode(defaultSchema, "MySQL");
    }

    @Test
    @Order(1)
    void testContents() {
        usersTable.createRequest()
                .toFactory()
                .newCreateTable()

                .checkAvailability()

                .beginCollection()
                    .add(IndexField.createPrimaryNotNullAutoIncrement("id"))
                    .add(IndexField.createNotNull(FieldType.VARCHAR, "name"))
                    .add(IndexField.createNotNull(FieldType.LONG, "register_date"))
                    .add(IndexField.createNotNull(FieldType.LONG, "last_update_date"))
                    .endpoint()

                .compile()
                .updateTransaction();

        // update schemes & tables content caches after table create.
        connection.updateContents();
        assertTrue(usersTable.exists());
    }

    @Test
    @Order(2)
    void testInsert() {
        usersTable.createRequest()
                .toFactory()
                .newInsert()

                .beginCollection()
                    .add(EntryField.create("name", "itzstonlex"))
                    .add(EntryField.create("register_date", System.currentTimeMillis()))
                    .add(EntryField.create("last_update_date", System.currentTimeMillis()))
                    .endpoint()

                .compile()
                .updateTransactionAsync()

                .whenComplete((updateResponse, error) -> {

                    System.out.println("INSERT RESPONSE:");
                    System.out.println(" Inserted com.itzstonlex.jnq.orm.User ID: " + updateResponse.getGeneratedKey());
                    System.out.println(" - " + updateResponse.getAffectedRows() + " affected rows");
                    System.out.println();
                });
    }

    @Test
    @Order(3)
    void testFinder() {
        usersTable.createRequest()
                .toFactory()
                .newFinder()

                .markLimit(50)

                .beginSelection()
                    .withUpperCase("name").as("upper_name")
                    .withAll()

                .beginCondition()
                    .and(EntryField.create("name", "itzstonlex"))
                    .endpoint()

                .beginGrouping()
                    .by(EntryField.create("id"))
                    .endpoint()

                .beginSorting()
                    .byDesc(EntryField.create("id"))
                    .byAsc(EntryField.create("name"))
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
                .newUpdate()

                .beginUpdateCondition()
                    .and(EntryField.create("last_update_date", System.currentTimeMillis()))
                    .endpoint()

                .beginCondition()
                    .and(FieldOperator.MORE_OR_EQUAL, EntryField.create("id", 1))
                    .and(EntryField.create("name", "itzstonlex"))
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
