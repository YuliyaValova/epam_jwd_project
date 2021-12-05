package com.jwd.dao.repository.impl;

import com.jwd.dao.connection.ConnectionPool;
import com.jwd.dao.connection.ConnectionUtil;
import com.jwd.dao.exception.DaoException;
import com.jwd.dao.repository.BasketDao;
import com.jwd.dao.validation.DaoValidator;
import com.jwd.dao.validation.impl.DaoValidatorImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class MysqlBasketDaoImpl implements BasketDao {

    private static final String DELETE_FROM_BASKET_QUERY = "delete from Orders\n" +
            "where customer_id = ? and product_id = ?;";
    private static final String ADD_TO_BASKET_QUERY = "insert into Orders (date, status,  product_id, customer_id)\n" +
            "values\n" +
            "(now(), \"Waiting for payment\", ? , ?);";
    private static final String GET_SUM_QUERY = "select sum(Products.price) from Products\n" +
            "join Orders on Orders.product_id = Products.id\n" +
            "join UserAccounts on UserAccounts.id = Orders.customer_id\n" +
            "where Orders.customer_id = ? and Orders.status = \"Waiting for payment\";";
    private static final String IS_ORDER_EXISTS_QUERY = "select id from Orders\n" +
            "where customer_id = ? and product_id = ? and status = \"Waiting for payment\";";

    private final ConnectionPool connectionPool;
    private final ConnectionUtil daoUtil;
    private final DaoValidator validator = new DaoValidatorImpl();


    public MysqlBasketDaoImpl(ConnectionPool connectionPool, ConnectionUtil daoUtil) {
        this.connectionPool = connectionPool;
        this.daoUtil = daoUtil;
    }

    @Override
    public double getSum(long id) throws DaoException {
        double sum = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Object> parameters = Arrays.asList(
                id
        );
        try {
            validator.validateId(id);
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            preparedStatement = daoUtil.getPreparedStatement(GET_SUM_QUERY, connection, parameters);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sum = resultSet.getDouble(1);
            }
            connection.commit();
            return sum;
        } catch (SQLException | DaoException e) {
            throw new DaoException(e);
        } finally {
            daoUtil.close(resultSet);
            daoUtil.close(preparedStatement);
            connectionPool.retrieveConnection(connection);
        }
    }

    @Override
    public void deleteFromBasket(long id, long productId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Object> parameters = Arrays.asList(
                id,
                productId
        );
        try {
            validator.validateId(id);
            validator.validateId(productId);
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            preparedStatement = daoUtil.getPreparedStatement(DELETE_FROM_BASKET_QUERY, connection, parameters);
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
    public boolean addToBasket(long id, long productId) throws DaoException {
        boolean isSuccess = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Object> parameters = Arrays.asList(
                productId,
                id
        );
        try {
            validator.validateId(id);
            validator.validateId(productId);
            if (!isOrderExists(id, productId)) {
                isSuccess = true;
                connection = connectionPool.takeConnection();
                connection.setAutoCommit(false);
                preparedStatement = daoUtil.getPreparedStatement(ADD_TO_BASKET_QUERY, connection, parameters);
                int affectedRows = preparedStatement.executeUpdate();
                connection.commit();
            }
            return isSuccess;
        } catch (SQLException | DaoException e) {
            throw new DaoException(e);
        } finally {
            daoUtil.close(preparedStatement);
            connectionPool.retrieveConnection(connection);
        }
    }

    private boolean isOrderExists(long id, long productId) throws DaoException {
        boolean isExists = false;
        List<Object> parameters = Arrays.asList(
                id,
                productId
        );
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            validator.validateId(id);
            validator.validateId(productId);
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            preparedStatement = daoUtil.getPreparedStatement(IS_ORDER_EXISTS_QUERY, connection, parameters);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                isExists = true;
            }
            connection.commit();
            return isExists;

        } catch (SQLException | DaoException e) {
            e.printStackTrace();
            throw new DaoException(e);

        } finally {
            daoUtil.close(resultSet);
            daoUtil.close(preparedStatement);
            connectionPool.retrieveConnection(connection);
        }
    }
}
