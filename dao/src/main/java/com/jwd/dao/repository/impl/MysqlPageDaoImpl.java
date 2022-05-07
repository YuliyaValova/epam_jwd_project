package com.jwd.dao.repository.impl;

import com.jwd.dao.connection.ConnectionPool;
import com.jwd.dao.connection.ConnectionUtil;
import com.jwd.dao.domain.*;
import com.jwd.dao.exception.DaoException;
import com.jwd.dao.repository.PageDao;
import com.jwd.dao.validation.DaoValidator;
import com.jwd.dao.validation.impl.DaoValidatorImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.*;

public class MysqlPageDaoImpl implements PageDao {

    private static final String COUNT_BASKET_SORTED = "select count(*) from {0}";
    private static final String PARAM_FOR_BASKET_SORT = "select b.*, p.name, p.description from {0} b join Products p on p.id = b.product_id order by {1} %s limit ? offset ? ; ";
    private static final String COUNT_ALL_ORDERS = "select count(*) from Orders";
    private static final String PARAM_FOR_SORT_ALL_ORDERS = "select o.* from Orders o " +
            "order by o.%s %s limit ? offset ?;";
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
    private static final String GET_DETAILS_QUERY = "select o.*, p.name from Order_details o \n" +
            "join Products p on p.id = o.product_id\n" +
            "where order_id = {0}\n" +
            "order by o.order_id;";

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
    public Pageable<OrderDetail> findOrderPage(Pageable<OrderDetail> daoProductPageable) throws DaoException {
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
            return getOrderRowPageable(daoProductPageable, resultSet1, resultSet2);
        } catch (SQLException | DaoException e) {
            throw new DaoException(e);
        } finally {
            daoUtil.close(resultSet1, resultSet2);
            daoUtil.close(preparedStatement1, preparedStatement2);
            connectionPool.retrieveConnection(connection);
        }
    }

    @Override
    public Pageable<OrderDetail> findBasketPage(Pageable<OrderDetail> daoBasketPageable, String login) throws DaoException {
        final int offset = (daoBasketPageable.getPageNumber() - 1) * daoBasketPageable.getLimit();
        List<Object> parameters2 = Arrays.asList(
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
            String query1 = MessageFormat.format(COUNT_BASKET_SORTED, login + "_basket");
            String query2 = MessageFormat.format(PARAM_FOR_BASKET_SORT, login + "_basket", "order_detail_" + daoBasketPageable.getSortBy());
            preparedStatement1 = daoUtil.getPreparedStatement(query1, connection, Collections.emptyList());
            final String findPageOrderedQuery = String.format(query2, daoBasketPageable.getDirection());
            preparedStatement2 = daoUtil.getPreparedStatement(findPageOrderedQuery, connection, parameters2);
            resultSet1 = preparedStatement1.executeQuery();
            resultSet2 = preparedStatement2.executeQuery();
            connection.commit();
            return getOrderRowPageable(daoBasketPageable, resultSet1, resultSet2);
        } catch (SQLException | DaoException e) {
            throw new DaoException(e);
        } finally {
            daoUtil.close(resultSet1, resultSet2);
            daoUtil.close(preparedStatement1, preparedStatement2);
            connectionPool.retrieveConnection(connection);
        }
    }

 /*   @Override
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
    }*/


    @Override
    public Pageable<Order> findAllOrderPage(Pageable<Order> daoOrderPageable) throws DaoException {
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
            final String findPageOrderedQuery = String.format(PARAM_FOR_SORT_ALL_ORDERS, "order_" + daoOrderPageable.getSortBy(), daoOrderPageable.getDirection());
            preparedStatement2 = daoUtil.getPreparedStatement(findPageOrderedQuery, connection, parameters2);
            resultSet1 = preparedStatement1.executeQuery();
            resultSet2 = preparedStatement2.executeQuery();
            connection.commit();
            return getOrderPageable(daoOrderPageable, resultSet1, resultSet2);
        } catch (SQLException | DaoException e) {
            throw new DaoException(e);
        } finally {
            daoUtil.close(resultSet1, resultSet2);
            daoUtil.close(preparedStatement1, preparedStatement2);
            connectionPool.retrieveConnection(connection);
        }
    }

    private Pageable<Order> getOrderPageable(Pageable<Order> daoOrderPageable, ResultSet resultSet1, ResultSet resultSet2) throws SQLException, DaoException {
        final Pageable<Order> pageable = new Pageable<>();
        long totalElements = 0L;
        while (resultSet1.next()) {
            totalElements = resultSet1.getLong(1);
        }
        final List<Order> orders = new ArrayList<>();
        while (resultSet2.next()) {
            Order order = getOrderFromDb(resultSet2);
            order.setDetails(getDetailsFromDb(order.getOrderId()));
            orders.add(order);
        }
        pageable.setPageNumber(daoOrderPageable.getPageNumber());
        pageable.setLimit(daoOrderPageable.getLimit());
        pageable.setTotalElements(totalElements);
        pageable.setElements(orders);
        pageable.setSortBy(daoOrderPageable.getSortBy());
        pageable.setDirection(daoOrderPageable.getDirection());
        return pageable;
    }

    private ArrayList<OrderDetail> getDetailsFromDb(long orderId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ArrayList<OrderDetail> details = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            String query = MessageFormat.format(GET_DETAILS_QUERY, orderId);
            preparedStatement = daoUtil.getPreparedStatement(query, connection, Collections.emptyList());
            resultSet = preparedStatement.executeQuery();
            connection.commit();
            while (resultSet.next()){
                OrderDetail detail = getOrderDetailFromDbForOrder(resultSet);
                details.add(detail);
            }
            return details;
        } catch (SQLException | DaoException e) {
            throw new DaoException(e);
        } finally {
            daoUtil.close(preparedStatement);
            connectionPool.retrieveConnection(connection);
        }
    }

    private Order getOrderFromDb(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(1);
        String date = resultSet.getString(2);
        String status = resultSet.getString(3);
        double totalPrice = resultSet.getDouble(4);
        long customer_id = resultSet.getLong(5);
        String comment = resultSet.getString(6);
        ArrayList<OrderDetail> details = new ArrayList<>();
        return new Order(id, date, status, totalPrice, customer_id, details, comment);
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

    private Pageable<OrderDetail> getOrderRowPageable(Pageable<OrderDetail> daoOrderPageable, ResultSet resultSet1, ResultSet resultSet2) throws SQLException {
        final Pageable<OrderDetail> pageable = new Pageable<>();
        long totalElements = 0L;
        while (resultSet1.next()) {
            totalElements = resultSet1.getLong(1);
        }
        final List<OrderDetail> orders = new ArrayList<>();
        while (resultSet2.next()) {
            orders.add(getOrderDetailFromDb(resultSet2));
        }
        pageable.setPageNumber(daoOrderPageable.getPageNumber());
        pageable.setLimit(daoOrderPageable.getLimit());
        pageable.setTotalElements(totalElements);
        pageable.setElements(orders);
        pageable.setSortBy(daoOrderPageable.getSortBy());
        pageable.setDirection(daoOrderPageable.getDirection());
        return pageable;
    }

    private OrderDetail getOrderDetailFromDbForOrder(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(2);
        int productAmount = resultSet.getInt(3);
        Double itemPrice = resultSet.getDouble(4);
        int productId = resultSet.getInt(5);
        String productDetail = resultSet.getString(6);
        String productName = resultSet.getString(9);
        return new OrderDetail(id, productAmount, itemPrice, productId, productName, productDetail);
    }

    private OrderDetail getOrderDetailFromDb(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(1);
        int productAmount = resultSet.getInt(2);
        Double itemPrice = resultSet.getDouble(3);
        int productId = resultSet.getInt(4);
        String productDetail = resultSet.getString(9);
        String productName = resultSet.getString(8);
        return new OrderDetail(id, productAmount, itemPrice, productId, productName, productDetail);
    }

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
        return new Product(id, name, type, description, price, isAvailable, image);
    }

}
