package com.itzstonlex.jnq.sql;

import com.itzstonlex.jnq.exception.JnqException;
import com.itzstonlex.jnq.impl.response.WrapperUpdateResponse;
import com.itzstonlex.jnq.request.option.RequestConcurrency;
import com.itzstonlex.jnq.request.option.RequestFetchDirection;
import com.itzstonlex.jnq.request.option.RequestHoldability;
import com.itzstonlex.jnq.request.option.RequestType;
import com.itzstonlex.jnq.response.Response;
import com.itzstonlex.jnq.response.UpdateResponse;
import com.itzstonlex.jnq.sql.response.SQLResponse;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.sql.*;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public final class SQLWrapperStatement {

    SQLRequest request;

    Object[] valuesArray;

    private void _prepareStatementValues(@NonNull PreparedStatement statement) {
        if (valuesArray == null || valuesArray.length == 0) {
            return;
        }

        AtomicInteger indexAtomic = new AtomicInteger(1);

        for (Object obj : valuesArray) {

            int index = indexAtomic.getAndIncrement();

            try {
                if (obj instanceof LocalDate) {
                    obj = Date.valueOf((LocalDate) obj);
                }

                if (obj == null) {
                    statement.setNull(index, Types.NULL);
                } else {
                    statement.setObject(index, obj);
                }
            }
            catch (SQLException exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    public UpdateResponse update(@NonNull String query)
    throws JnqException {

        Connection connection = request.getConnection().getSqlConnection();

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            this._prepareStatementValues(statement);

            int affectedRows = statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {

                boolean generatedKeySupports = resultSet.next();
                int generatedKey = generatedKeySupports ? resultSet.getInt(1) : 0;

                return new WrapperUpdateResponse(generatedKeySupports, generatedKey, affectedRows);
            }

        } catch (SQLException exception) {
            throw new JnqException(exception);
        }
    }

    @SuppressWarnings("MagicConstant")
    public Response fetch(@NonNull String query)
    throws JnqException {

        int typeIndex = request.type() != null ? request.type().getIndex() : RequestType.FORWARD_ONLY.getIndex();

        int concurrencyIndex = request.concurrency() != null ? request.concurrency().getIndex() : RequestConcurrency.UPDATABLE.getIndex();

        int holdabilityIndex = request.holdability() != null ? request.holdability().getIndex() : RequestHoldability.HOLD_CURSORS_OVER_COMMIT.getIndex();

        int fetchDirectionIndex = request.fetchDirection() != null ? request.fetchDirection().getIndex() : RequestFetchDirection.FORWARD.getIndex();

        Connection connection = request.getConnection().getSqlConnection();

        try (PreparedStatement statement = connection.prepareStatement(query, typeIndex, concurrencyIndex, holdabilityIndex)) {
            statement.setFetchDirection(fetchDirectionIndex);

            this._prepareStatementValues(statement);

            try (ResultSet resultSet = statement.executeQuery()) {
                return new SQLResponse(resultSet);
            }

        } catch (SQLException exception) {
            throw new JnqException(exception);
        }
    }

}
