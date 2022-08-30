package com.itzstonlex.jnq.jdbc;

import com.itzstonlex.jnq.content.exception.JnqContentException;
import com.itzstonlex.jnq.content.UpdateResponseDecorator;
import com.itzstonlex.jnq.jdbc.request.JDBCRequest;
import com.itzstonlex.jnq.content.request.RequestConcurrency;
import com.itzstonlex.jnq.content.request.RequestFetchDirection;
import com.itzstonlex.jnq.content.request.RequestHoldability;
import com.itzstonlex.jnq.content.request.RequestType;
import com.itzstonlex.jnq.content.Response;
import com.itzstonlex.jnq.content.UpdateResponse;
import com.itzstonlex.jnq.jdbc.response.JDBCResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
public final class JDBCStatement {

    JDBCRequest request;

    Object[] valuesArray;

    private void _prepareStatementValues(@NonNull PreparedStatement statement)
    throws JnqContentException {

        if (valuesArray == null || valuesArray.length == 0) {
            return;
        }

        int index = 1;

        for (Object obj : valuesArray) {
            try {
                if (obj instanceof LocalDate) {
                    obj = Date.valueOf((LocalDate) obj);
                }

                if (obj instanceof LocalTime) {
                    obj = Time.valueOf((LocalTime) obj);
                }

                if (obj == null) {
                    statement.setNull(index, Types.NULL);
                }
                else {
                    statement.setObject(index, obj);
                }

                index++;
            }
            catch (SQLException exception) {
                throw new JnqContentException(exception);
            }
        }
    }

    public UpdateResponse update(@NonNull String query)
    throws JnqContentException {

        System.out.println(query);

        Connection connection = request.getContent().getJdbcConnection();

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            this._prepareStatementValues(statement);

            int affectedRows = statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {

                boolean generatedKeySupports = resultSet.next();
                int generatedKey = generatedKeySupports ? resultSet.getInt(1) : 0;

                return new UpdateResponseDecorator(generatedKeySupports, generatedKey, affectedRows);
            }

        } catch (SQLException exception) {
            throw new JnqContentException(exception);
        }
    }

    @SuppressWarnings("MagicConstant")
    public Response fetch(@NonNull String query)
    throws JnqContentException {

        int typeIndex = request.type() != null ? request.type().getIndex() : RequestType.FORWARD_ONLY.getIndex();

        int concurrencyIndex = request.concurrency() != null ? request.concurrency().getIndex() : RequestConcurrency.UPDATABLE.getIndex();

        int holdabilityIndex = request.holdability() != null ? request.holdability().getIndex() : RequestHoldability.HOLD_CURSORS_OVER_COMMIT.getIndex();

        int fetchDirectionIndex = request.fetchDirection() != null ? request.fetchDirection().getIndex() : RequestFetchDirection.FORWARD.getIndex();

        Connection connection = request.getContent().getJdbcConnection();

        try (PreparedStatement statement = connection.prepareStatement(query, typeIndex, concurrencyIndex, holdabilityIndex)) {
            statement.setFetchDirection(fetchDirectionIndex);

            this._prepareStatementValues(statement);

            try (ResultSet resultSet = statement.executeQuery()) {
                return new JDBCResponse(resultSet);
            }

        } catch (SQLException exception) {
            throw new JnqContentException(exception);
        }
    }

}
