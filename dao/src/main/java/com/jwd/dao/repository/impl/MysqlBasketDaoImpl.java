package com.jwd.dao.repository.impl;

import com.jwd.dao.connection.ConnectionPool;
import com.jwd.dao.connection.ConnectionUtil;
import com.jwd.dao.exception.DaoException;
import com.jwd.dao.repository.BasketDao;
import com.jwd.dao.validation.DaoValidator;
import com.jwd.dao.validation.impl.DaoValidatorImpl;

import java.sql.*;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MysqlBasketDaoImpl implements BasketDao {

    private static final String DELETE_FROM_BASKET_QUERY = "delete from {0} where order_detail_id = ?;";
    private static final String ADD_TO_BASKET_QUERY = "insert into {0} (item_price, product_id,  product_amount, creationDate)\n" +
            "values\n" +
            "(?, ?, ?, now());";
    private static final String GET_SUM_QUERY = "select sum(item_price*product_amount) from {0};";
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
    public double getSum(String login) throws DaoException {
        double sum = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            String query = MessageFormat.format(GET_SUM_QUERY,login + "_basket");
            preparedStatement = daoUtil.getPreparedStatement(query, connection, Collections.emptyList());
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
    public void deleteFromBasket(String login, long productId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Object> parameters = Arrays.asList(
                productId
        );
        try {
            validator.validateId(productId);
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            String query = MessageFormat.format(DELETE_FROM_BASKET_QUERY, login + "_basket");
            preparedStatement = daoUtil.getPreparedStatement(query, connection, parameters);
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
    public boolean addToBasket(long id, String login, double price, long productId, int count) throws DaoException {
        boolean isSuccess = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Object> parameters = Arrays.asList(
                price,
                productId,
                count
        );
        try {
            validator.validateId(id);
            validator.validateId(productId);
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(true);
            String query = MessageFormat.format(ADD_TO_BASKET_QUERY, login + "_basket");
            preparedStatement = daoUtil.getPreparedStatement(query, connection, parameters);
            preparedStatement.executeUpdate();
            isSuccess = true;
            return isSuccess;
        } catch (SQLException | DaoException e) {
            throw new DaoException(e);
        } finally {
            daoUtil.close(preparedStatement);
            connectionPool.retrieveConnection(connection);
        }
    }

   /* private boolean isOrderExists(long id, long productId) throws DaoException {
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
    }*/
}
