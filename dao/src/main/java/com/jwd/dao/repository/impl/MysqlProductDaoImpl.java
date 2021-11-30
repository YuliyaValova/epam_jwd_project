package com.jwd.dao.repository.impl;

import com.jwd.dao.config.DatabaseConfig;
import com.jwd.dao.connection.ConnectionPool;
import com.jwd.dao.connection.ConnectionUtil;
import com.jwd.dao.connection.impl.ConnectionPoolImpl;
import com.jwd.dao.domain.Pageable;
import com.jwd.dao.domain.Product;
import com.jwd.dao.exception.DaoException;
import com.jwd.dao.repository.ProductDao;
import com.jwd.dao.validation.DaoValidator;
import com.jwd.dao.validation.impl.DaoValidatorImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class MysqlProductDaoImpl implements ProductDao {
    //todo transactions

    private static final String SAVE_PRODUCT_QUERY = "insert into Products (name, type, description, price, isAvailable) values (?,?,?,?,?);";
    private static final String IS_PRODUCT_EXISTS_QUERY = "select id from Products where name = ?;";
    private static final String DELETE_PRODUCT_QUERY = "delete from Products where id = ?;";
    private static final String GET_ALL_PRODUCTS_QUERY = "select * from Products;";
    private static final String GET_PRODUCT_BY_ID_QUERY = "select * from Products where id = ?;";
    private static final String COUNT_ALL_SORTED = "select count(*) from Products where isAvailable = true;";
    private static final String PARAM_FOR_SORT = "select * from Products p where p.isAvailable = true order by p.%s %s limit ? offset ?;";
    private static final String COUNT_BASKET_SORTED = "select count(*) from Products\n" +
            "join Orders on Orders.product_id = Products.id\n" +
            "join UserAccounts on UserAccounts.id = Orders.customer_id\n" +
            "where Orders.customer_id = ?;";
    private static final String PARAM_FOR_BASKET_SORT = "select * from Products p\n" +
            "join Orders on Orders.product_id = p.id\n" +
            "join UserAccounts on UserAccounts.id = Orders.customer_id\n" +
            "where Orders.customer_id = ?\n" +
            "order by p.%s %s limit ? offset ?;";
    private final ConnectionPool connectionPool;
    private final ConnectionUtil daoUtil;
    private final DaoValidator validator = new DaoValidatorImpl();


    public MysqlProductDaoImpl(ConnectionPool connectionPool, ConnectionUtil daoUtil) {
        this.connectionPool = connectionPool;
        this.daoUtil = daoUtil;
    }

    @Override
    public long addProduct(Product product) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        long id = productIsExists(product);
        if (id == -1) {

            List<Object> parameters = Arrays.asList(

                    product.getName(),
                    product.getType(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getIsAvailable()
            );

            try {
                validator.validateProduct(product);
                connection = connectionPool.takeConnection();
                connection.setAutoCommit(false);
                preparedStatement = daoUtil.getPreparedStatement(SAVE_PRODUCT_QUERY, connection, parameters);
                int affectedRows = preparedStatement.executeUpdate();
                connection.commit();
                return productIsExists(product);
            } catch (SQLException | DaoException e) {
                throw new DaoException(e);
            } finally {
                daoUtil.close(preparedStatement);
                connectionPool.retrieveConnection(connection);
            }

        } else return -1L;

    }

    private long productIsExists(Product product) throws DaoException {
        long id = -1L;
        List<Object> parameters = Arrays.asList(
                product.getName()
        );
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            validator.validateProduct(product);
            connection = connectionPool.takeConnection();
            preparedStatement = daoUtil.getPreparedStatement(IS_PRODUCT_EXISTS_QUERY, connection, parameters);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getLong(1);
            }
            return id;

        } catch (SQLException | DaoException e) {
            throw new DaoException(e);

        } finally {
            daoUtil.close(resultSet);
            daoUtil.close(preparedStatement);
            connectionPool.retrieveConnection(connection);
        }
    }

    @Override
    public void deleteProductById(long id) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        List<Object> parameters = Arrays.asList(
                id
        );

        try {
            validator.validateId(id); //todo validation
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            preparedStatement = daoUtil.getPreparedStatement(DELETE_PRODUCT_QUERY, connection, parameters);
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
    public List<Product> getAllProducts() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = daoUtil.getPreparedStatement(GET_ALL_PRODUCTS_QUERY, connection, Collections.emptyList());
            resultSet = preparedStatement.executeQuery();
            final List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                products.add(getProductFromDb(resultSet));
            }
            return products;
        } catch (SQLException | DaoException e) {
            throw new DaoException(e);
        } finally {
            daoUtil.close(resultSet);
            daoUtil.close(preparedStatement);
            connectionPool.retrieveConnection(connection);
        }
    }

    private Product getProductFromDb(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(1);
        String name = resultSet.getString(2);
        String type = resultSet.getString(3);
        String description = resultSet.getString(4);
        double price = resultSet.getDouble(5);
        boolean isAvailable = resultSet.getBoolean(6);
        return new Product(id, name, type, description, price, isAvailable);
    }

    @Override
    public Product getProductById(long id) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<Object> parameters = Arrays.asList(
                id
        );
        try {
            validator.validateId(id);
            connection = connectionPool.takeConnection();
            preparedStatement = daoUtil.getPreparedStatement(GET_PRODUCT_BY_ID_QUERY, connection, parameters);
            resultSet = preparedStatement.executeQuery();
            Product product = null;
            while (resultSet.next()) {
                product = getProductFromDb(resultSet);
            }
            return product;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            daoUtil.close(resultSet);
            daoUtil.close(preparedStatement);
            connectionPool.retrieveConnection(connection);
        }
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

    private Pageable<Product> getProductRowPageable(Pageable<Product> daoProductPageable,ResultSet resultSet1, ResultSet resultSet2) throws SQLException {
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


    public static void main(String[] args) {
        ConnectionPool pool = new ConnectionPoolImpl(new DatabaseConfig());
        ConnectionUtil util = new ConnectionUtil(pool);
        ProductDao dao = new MysqlProductDaoImpl(pool, util);
        Product product = new Product(null, "pizza", "Lolololo", 5, true);
        try {
            dao.addProduct(product);
            List<Product> products = dao.getAllProducts();
            for (Product prod : products) {
                System.out.println(prod.toString());
            }/*
            Product pr = dao.getProductById(0);
            System.out.println("---------------------");
            System.out.println(pr.toString());
            /*dao.deleteProductById(1);
            products = dao.getAllProducts();
            System.out.println("---------------------");
            for (Product prod:products) {
                System.out.println(prod.toString());
            }*/
        } catch (DaoException e) {
            System.out.println(e.getMessage());
        }
    }
}
