package com.jwd.dao.repository.impl;

import com.jwd.dao.connection.impl.ConnectionPoolImpl;
import com.jwd.dao.exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static java.util.Objects.nonNull;

public class ConnectionUtil {
    private final ConnectionPoolImpl connectionPool;

    protected ConnectionUtil(ConnectionPoolImpl connectionPool) {
        this.connectionPool = connectionPool;
    }

    protected Connection getConnection(final boolean hasAutocommit) throws SQLException, DaoException {
        final Connection connection = connectionPool.takeConnection();
        connection.setAutoCommit(hasAutocommit);
        return connection;
    }

    protected void retrieveConnection(final Connection connection) {
        connectionPool.retrieveConnection(connection);
    }


    protected PreparedStatement getPreparedStatement(final String query, final Connection connection,
                                                     final List<Object> parameters) throws SQLException {
        final PreparedStatement preparedStatement = connection.prepareStatement(query);
        setPreparedStatementParameters(preparedStatement, parameters);
        return preparedStatement;
    }

    protected void setPreparedStatementParameters(final PreparedStatement preparedStatement,
                                                  final List<Object> parameters) throws SQLException {
        for (int i = 0, queryParameterIndex = 1; i < parameters.size(); i++, queryParameterIndex++) {
            final Object parameter = parameters.get(i);
            setPreparedStatementParameter(preparedStatement, queryParameterIndex, parameter);
        }
    }

    protected void setPreparedStatementParameter(final PreparedStatement preparedStatement,
                                                 final int queryParameterIndex, final Object parameter) throws SQLException {
        if (Long.class == parameter.getClass()) {
            preparedStatement.setLong(queryParameterIndex, (Long) parameter);
        } else if (Integer.class == parameter.getClass()) {
            preparedStatement.setInt(queryParameterIndex, (Integer) parameter);
        } else if (String.class == parameter.getClass()) {
            preparedStatement.setString(queryParameterIndex, (String) parameter);
        }
    }


    protected void close(final ResultSet... resultSets) {
        try {
            if (nonNull(resultSets)) {
                for (final ResultSet resultSet : resultSets) {
                    if (nonNull(resultSet)) {
                        resultSet.close();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void close(final PreparedStatement... preparedStatements) {
        try {
            if (nonNull(preparedStatements)) {
                for (final PreparedStatement preparedStatement : preparedStatements) {
                    if (nonNull(preparedStatement)) {
                        preparedStatement.close();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}