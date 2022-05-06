package com.jwd.dao.repository.impl;

import com.jwd.dao.connection.ConnectionPool;
import com.jwd.dao.connection.ConnectionUtil;
import com.jwd.dao.domain.Order;
import com.jwd.dao.domain.Pageable;
import com.jwd.dao.domain.PageableOrder;
import com.jwd.dao.domain.Product;
import com.jwd.dao.exception.DaoException;
import com.jwd.dao.repository.PageDao;
import com.jwd.dao.validation.DaoValidator;
import com.jwd.dao.validation.impl.DaoValidatorImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class MysqlPageDaoImpl implements PageDao {

    private static final String COUNT_BASKET_SORTED = "select count(*) from Products\n" +
            "join Orders on Orders.product_id = Products.id\n" +
            "join UserAccounts on UserAccounts.id = Orders.customer_id\n" +
            "where Orders.customer_id = ? and Orders.status = \"Waiting for payment\";";
    private static final String PARAM_FOR_BASKET_SORT = "select * from Products p\n" +
            "join Orders on Orders.product_id = p.id\n" +
            "join UserAccounts on UserAccounts.id = Orders.customer_id\n" +
            "where Orders.customer_id = ? and Orders.status = \"Waiting for payment\"\n" +
            "order by p.%s %s limit ? offset ?;";
    private static final String COUNT_ALL_ORDERS = "select count(*) from Orders left join Products on Products.id = Orders.product_id\n" +
            "where Products.id is not null;";
    private static final String PARAM_FOR_SORT_ALL_ORDERS = "select Orders.id, p.*, Orders.customer_id, Orders.status from Orders\n" +
            "join Products as p on p.id = Orders.product_id\n" +
            "order by p.%s %s limit ? offset ?;";
    private static final String COUNT_ALL_SORTED = "select count(*) from Products where isAvailable = true;";
    private static final String PARAM_FOR_SORT = "select * from Products p where p.isAvailable = true order by p.%s %s limit ? offset ?;";
    private static final String COUNT_ALL_PAID = "select count(*) from Orders \n" +
            "left join Products on Products.id = Orders.product_id\n" +
            "where status = \"Paid up\" and Products.id is not null;";
    private static final String PARAM_FOR_SORT_PAID = "select Orders.id, p.*, Orders.customer_id, Orders.status from Orders\n" +
            "join Products as p on p.id = Orders.product_id\n" +
            "where Orders.status = \"Paid up\" order by p.%s %s limit ? offset ?;";
    private static final String COUNT_ALL_PRODUCTS = "select count(*) from Products;";
    private static final String PARAM_FOR_SORT_ALL_PRODUCTS = "select * from Products p order by p.%s %s limit ? offset ?;";

    private final ConnectionPool connectionPool;
    private final ConnectionUtil daoUtil;
    private final DaoValidator validator = new DaoValidatorImpl(); //todo validation

    public MysqlPageDaoImpl(ConnectionPool connectionPool, ConnectionUtil daoUtil) {
        this.connectionPool = connectionPool;
        this.daoUtil = daoUtil;
    }

    @Override
    public Pageable<Product> findPage(Pageable<Product> daoProductPageable) throws DaoException {
        final int offset = (daoProductPageable.getPageNumber() - 1) * daoProductPageable.getLimit();
        List<Object> parameters1 = Collections.emptyList();
        List<Object> parameters2 = Arrays.asList(
                daoProductPageable.getLimit(),
                offset
        );
        Connection connection = null;
        PreparedStatement preparedStatement1 = null;
        PreparedStatement preparedStatement2 = null;
        ResultSet resultSet1 = null;
        ResultSet resultSet2 = null;
        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            preparedStatement1 = daoUtil.getPreparedStatement(COUNT_ALL_SORTED, connection, parameters1);
            final String findPageOrderedQuery = String.format(PARAM_FOR_SORT, daoProductPageable.getSortBy(), daoProductPageable.getDirection());
            preparedStatement2 = daoUtil.getPreparedStatement(findPageOrderedQuery, connection, parameters2);
            resultSet1 = preparedStatement1.executeQuery();
            resultSet2 = preparedStatement2.executeQuery();
            connection.commit();
            return getProductRowPageable(daoProductPageable, resultSet1, resultSet2);
        } catch (SQLException | DaoException e) {
            throw new DaoException(e);
        } finally {
            daoUtil.close(resultSet1, resultSet2);
            daoUtil.close(preparedStatement1, preparedStatement2);
            connectionPool.retrieveConnection(connection);
        }
    }

    @Override
    public Pageable<Product> findBasketPage(Pageable<Product> daoBasketPageable, long id) throws DaoException {
        final int offset = (daoBasketPageable.getPageNumber() - 1) * daoBasketPageable.getLimit();
        List<Object> parameters1 = Arrays.asList(
                id
        );
        List<Object> parameters2 = Arrays.asList(
                id,
                daoBasketPageable.getLimit(),
                offset
        );
        Connection connection = null;
        PreparedStatement preparedStatement1 = null;
        PreparedStatement preparedStatement2 = null;
        ResultSet resultSet1 = null;
        ResultSet resultSet2 = null;
        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            preparedStatement1 = daoUtil.getPreparedStatement(COUNT_BASKET_SORTED, connection, parameters1);
            final String findPageOrderedQuery = String.format(PARAM_FOR_BASKET_SORT, daoBasketPageable.getSortBy(), daoBasketPageable.getDirection());
            preparedStatement2 = daoUtil.getPreparedStatement(findPageOrderedQuery, connection, parameters2);
            resultSet1 = preparedStatement1.executeQuery();
            resultSet2 = preparedStatement2.executeQuery();
            connection.commit();
            return getProductRowPageable(daoBasketPageable, resultSet1, resultSet2);
        } catch (SQLException | DaoException e) {
            throw new DaoException(e);
        } finally {
            daoUtil.close(resultSet1, resultSet2);
            daoUtil.close(preparedStatement1, preparedStatement2);
            connectionPool.retrieveConnection(connection);
        }
    }

    @Override
    public PageableOrder<Order> findPaidOrderPage(PageableOrder<Order> daoOrderPageable) throws DaoException {
        final int offset = (daoOrderPageable.getPageNumber() - 1) * daoOrderPageable.getLimit();
        List<Object> parameters1 = Collections.emptyList();
        List<Object> parameters2 = Arrays.asList(
                daoOrderPageable.getLimit(),
                offset
        );
        Connection connection = null;
        PreparedStatement preparedStatement1 = null;
        PreparedStatement preparedStatement2 = null;
        ResultSet resultSet1 = null;
        ResultSet resultSet2 = null;
        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            preparedStatement1 = daoUtil.getPreparedStatement(COUNT_ALL_PAID, connection, parameters1);
            final String findPageOrderedQuery = String.format(PARAM_FOR_SORT_PAID, daoOrderPageable.getSortBy(), daoOrderPageable.getDirection());
            preparedStatement2 = daoUtil.getPreparedStatement(findPageOrderedQuery, connection, parameters2);
            resultSet1 = preparedStatement1.executeQuery();
            resultSet2 = preparedStatement2.executeQuery();
            connection.commit();
            return getOrderRowPageable(daoOrderPageable, resultSet1, resultSet2);
        } catch (SQLException | DaoException e) {
            throw new DaoException(e);
        } finally {
            daoUtil.close(resultSet1, resultSet2);
            daoUtil.close(preparedStatement1, preparedStatement2);
            connectionPool.retrieveConnection(connection);
        }
    }

    @Override
    public PageableOrder<Order> findAllOrderPage(PageableOrder<Order> daoOrderPageable) throws DaoException {
        final int offset = (daoOrderPageable.getPageNumber() - 1) * daoOrderPageable.getLimit();
        List<Object> parameters1 = Collections.emptyList();
        List<Object> parameters2 = Arrays.asList(
                daoOrderPageable.getLimit(),
                offset
        );
        Connection connection = null;
        PreparedStatement preparedStatement1 = null;
        PreparedStatement preparedStatement2 = null;
        ResultSet resultSet1 = null;
        ResultSet resultSet2 = null;
        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            preparedStatement1 = daoUtil.getPreparedStatement(COUNT_ALL_ORDERS, connection, parameters1);
            final String findPageOrderedQuery = String.format(PARAM_FOR_SORT_ALL_ORDERS, daoOrderPageable.getSortBy(), daoOrderPageable.getDirection());
            preparedStatement2 = daoUtil.getPreparedStatement(findPageOrderedQuery, connection, parameters2);
            resultSet1 = preparedStatement1.executeQuery();
            resultSet2 = preparedStatement2.executeQuery();
            connection.commit();
            return getOrderRowPageable(daoOrderPageable, resultSet1, resultSet2);
        } catch (SQLException | DaoException e) {
            throw new DaoException(e);
        } finally {
            daoUtil.close(resultSet1, resultSet2);
            daoUtil.close(preparedStatement1, preparedStatement2);
            connectionPool.retrieveConnection(connection);
        }
    }

    @Override
    public Pageable<Product> findAllProductsPage(Pageable<Product> daoProductPageable) throws DaoException {
        final int offset = (daoProductPageable.getPageNumber() - 1) * daoProductPageable.getLimit();
        List<Object> parameters1 = Collections.emptyList();
        List<Object> parameters2 = Arrays.asList(
                daoProductPageable.getLimit(),
                offset
        );
        Connection connection = null;
        PreparedStatement preparedStatement1 = null;
        PreparedStatement preparedStatement2 = null;
        ResultSet resultSet1 = null;
        ResultSet resultSet2 = null;
        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            preparedStatement1 = daoUtil.getPreparedStatement(COUNT_ALL_PRODUCTS, connection, parameters1);
            final String findPageOrderedQuery = String.format(PARAM_FOR_SORT_ALL_PRODUCTS, daoProductPageable.getSortBy(), daoProductPageable.getDirection());
            preparedStatement2 = daoUtil.getPreparedStatement(findPageOrderedQuery, connection, parameters2);
            resultSet1 = preparedStatement1.executeQuery();
            resultSet2 = preparedStatement2.executeQuery();
            connection.commit();
            return getProductRowPageable(daoProductPageable, resultSet1, resultSet2);
        } catch (SQLException | DaoException e) {
            throw new DaoException(e);
        } finally {
            daoUtil.close(resultSet1, resultSet2);
            daoUtil.close(preparedStatement1, preparedStatement2);
            connectionPool.retrieveConnection(connection);
        }
    }

    private PageableOrder<Order> getOrderRowPageable(PageableOrder<Order> daoOrderPageable, ResultSet resultSet1, ResultSet resultSet2) throws SQLException {
        final PageableOrder<Order> pageable = new PageableOrder<>();
        long totalElements = 0L;
        while (resultSet1.next()) {
            totalElements = resultSet1.getLong(1);
        }
        final List<Order> orders = new ArrayList<>();
        while (resultSet2.next()) {
          //  orders.add(getOrderFromDb(resultSet2));
        }
        pageable.setPageNumber(daoOrderPageable.getPageNumber());
        pageable.setLimit(daoOrderPageable.getLimit());
        pageable.setTotalElements(totalElements);
        pageable.setElements(orders);
        pageable.setSortBy(daoOrderPageable.getSortBy());
        pageable.setDirection(daoOrderPageable.getDirection());
        return pageable;
    }

   /* private Order getOrderFromDb(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(1);
        long productId = resultSet.getLong(2);
        String name = resultSet.getString(3);
        String type = resultSet.getString(4);
        String description = resultSet.getString(5);
        double price = resultSet.getDouble(6);
        boolean isAvailable = resultSet.getBoolean(7);
        long customerId = resultSet.getLong(8);
        String status = resultSet.getString(9);
        return new Order(id, productId, name, type, description, price, isAvailable, customerId, status);
    }*/

    private Pageable<Product> getProductRowPageable(Pageable<Product> daoProductPageable, ResultSet resultSet1, ResultSet resultSet2) throws SQLException {
        final Pageable<Product> pageable = new Pageable<>();
        long totalElements = 0L;
        while (resultSet1.next()) {
            totalElements = resultSet1.getLong(1);
        }
        final List<Product> products = new ArrayList<>();
        while (resultSet2.next()) {
            products.add(getProductFromDb(resultSet2));
        }
        pageable.setPageNumber(daoProductPageable.getPageNumber());
        pageable.setLimit(daoProductPageable.getLimit());
        pageable.setTotalElements(totalElements);
        pageable.setElements(products);
        pageable.setSortBy(daoProductPageable.getSortBy());
        pageable.setDirection(daoProductPageable.getDirection());
        return pageable;
    }

    private Product getProductFromDb(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(1);
        String name = resultSet.getString(2);
        String description = resultSet.getString(3);
        double price = resultSet.getDouble(4);
        boolean isAvailable = resultSet.getBoolean(5);
        String type = resultSet.getString(6);
        String image = resultSet.getString(7);
        return new Product(id, name,type, description, price, isAvailable, image);
    }

}
