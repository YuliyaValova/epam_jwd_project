package com.jwd.dao.repository.impl;

import com.jwd.dao.connection.ConnectionPool;
import com.jwd.dao.connection.ConnectionUtil;
import com.jwd.dao.exception.DaoException;
import com.jwd.dao.repository.OrderDao;
import com.jwd.dao.validation.DaoValidator;
import com.jwd.dao.validation.impl.DaoValidatorImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MysqlOrderDaoImpl implements OrderDao {

    private static final String COUNT_BASKET_SORTED = "select count(*) from {0}";
    private static final String SAVE_ORDER_QUERY = " insert into Orders (date, status, totalPrice, customer_id, comment, creationDate)\n" +
            "values\n" +
            "(now(), \"Waiting for payment\", (select sum(item_price*product_amount) from {0}), ?, ?, now());";
    private static final String TRUNCATE_BASKET_QUERY = "truncate table {0};";
    private static final String SET_STATUS_QUERY = "update Orders\n" +
            "set status = ?\n" +
            "where id = ?;";
    private static final String SAVE_ORDER_DETAILS_QUERY = "insert into Order_details (order_id, order_detail_id, product_amount, item_price, product_id, product_detail, creationDate)\n" +
            "select {1}, order_detail_id, product_amount, item_price, product_id, product_detail, creationDate from {0};";

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
    public void changeAllOrdersStatus(long id, String login, String comment) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement2 = null;
        List<Object> parameters = Arrays.asList(
                id,
                comment
        );
        long orderId = 0L;
        try {
            validator.validateId(id);
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            String query_ = MessageFormat.format(SAVE_ORDER_QUERY, login + "_basket");
            preparedStatement = daoUtil.getPreparedStatement(query_, connection, parameters);
            int affectedRows = preparedStatement.executeUpdate();
            connection.commit();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                   orderId = generatedKeys.getLong(1);
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }

            }
            if(orderId != 0L) {
                String query = MessageFormat.format(SAVE_ORDER_DETAILS_QUERY, login + "_basket", orderId);
                preparedStatement2 = daoUtil.getPreparedStatement(query, connection, Collections.emptyList());
                affectedRows = preparedStatement2.executeUpdate();
                connection.commit();
            }
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

    @Override
    public boolean isBasketEmpty(String login) throws DaoException {
        boolean isEmpty = false;
        Connection connection = null;
        PreparedStatement preparedStatement1 = null;
        ResultSet resultSet1 = null;
        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            String query1 = MessageFormat.format(COUNT_BASKET_SORTED, login + "_basket");
            preparedStatement1 = daoUtil.getPreparedStatement(query1, connection, Collections.emptyList());
            resultSet1 = preparedStatement1.executeQuery();
            connection.commit();
            if (resultSet1.next()){
                long count = resultSet1.getLong(1);
                if(count == 0L) isEmpty = true;
            }
            return isEmpty;
        } catch (SQLException | DaoException e) {
            throw new DaoException(e);
        } finally {
            daoUtil.close(resultSet1);
            daoUtil.close(preparedStatement1);
            connectionPool.retrieveConnection(connection);
        }
    }
}
