package com.jwd.dao.repository.impl;

import com.jwd.dao.connection.ConnectionPool;
import com.jwd.dao.connection.ConnectionUtil;
import com.jwd.dao.exception.DaoException;
import com.jwd.dao.repository.OrderDao;
import com.jwd.dao.validation.DaoValidator;
import com.jwd.dao.validation.impl.DaoValidatorImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MysqlOrderDaoImpl implements OrderDao {

    private static final String SET_ALL_STATUS_QUERY = "update Orders\n" +
            "set Orders.status = ? \n" +
            "where Orders.customer_id = ? and Orders.status = ?;";
    private static final String TRUNCATE_BASKET_QUERY = "truncate table {0};";
    private static final String SET_STATUS_QUERY = "update Orders\n" +
            "set status = ?\n" +
            "where id = ?;";

    private final ConnectionPool connectionPool;
    private final ConnectionUtil daoUtil;
    private final DaoValidator validator = new DaoValidatorImpl();

    public MysqlOrderDaoImpl(ConnectionPool connectionPool, ConnectionUtil daoUtil) {
        this.connectionPool = connectionPool;
        this.daoUtil = daoUtil;
    }

    @Override
    public void deleteOrdersByUserLogin(String login) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            String query = MessageFormat.format(TRUNCATE_BASKET_QUERY, login + "_basket");
            preparedStatement = daoUtil.getPreparedStatement(query, connection, Collections.emptyList());
            int affectedRows = preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException | DaoException e) {
            throw new DaoException(e);
        } finally {
            daoUtil.close(preparedStatement);
            connectionPool.retrieveConnection(connection);
        }
    }

    @Override
    public void changeAllOrdersStatus(long id, String newStatus, String oldStatus) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Object> parameters = Arrays.asList(
                newStatus,
                id,
                oldStatus
        );
        try {
            validator.validateId(id);
            validator.validateStatus(newStatus);
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            preparedStatement = daoUtil.getPreparedStatement(SET_ALL_STATUS_QUERY, connection, parameters);
            int affectedRows = preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException | DaoException e) {
            throw new DaoException(e);
        } finally {
            daoUtil.close(preparedStatement);
            connectionPool.retrieveConnection(connection);
        }
    }

    @Override
    public void changeOrderStatus(long orderId, String newStatus) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Object> parameters = Arrays.asList(
                newStatus,
                orderId
        );
        try {
            validator.validateId(orderId);
            validator.validateStatus(newStatus);
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            preparedStatement = daoUtil.getPreparedStatement(SET_STATUS_QUERY, connection, parameters);
            int affectedRows = preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException | DaoException e) {
            throw new DaoException(e);
        } finally {
            daoUtil.close(preparedStatement);
            connectionPool.retrieveConnection(connection);
        }
    }
}
