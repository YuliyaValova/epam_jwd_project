package com.jwd.dao.repository.impl;

import com.jwd.dao.connection.ConnectionPool;
import com.jwd.dao.connection.ConnectionUtil;
import com.jwd.dao.domain.Product;
import com.jwd.dao.exception.DaoException;
import com.jwd.dao.repository.ProductDao;
import com.jwd.dao.validation.DaoValidator;
import com.jwd.dao.validation.impl.DaoValidatorImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class MysqlProductDaoImpl implements ProductDao {

    //todo modify get-orders methods for cases where product was deleted
    private static final String SAVE_PRODUCT_QUERY = "insert into Products (name, type, description, price, isAvailable) values (?,?,?,?,?);";
    private static final String IS_PRODUCT_EXISTS_QUERY = "select id from Products where name = ?;";
    private static final String DELETE_PRODUCT_QUERY = "delete from Products where id = ?;";
    private static final String GET_PRODUCT_BY_ID_QUERY = "select * from Products where id = ?;";
    private static final String CHANGE_PRODUCT_STATUS_QUERY = "update Products set isAvailable = ? where id = ?;";
    //private static final String GET_ALL_PRODUCTS_QUERY = "select * from Products;";

    private final ConnectionPool connectionPool;
    private final ConnectionUtil daoUtil;
    private final DaoValidator validator = new DaoValidatorImpl();


    public MysqlProductDaoImpl(ConnectionPool connectionPool, ConnectionUtil daoUtil) {
        this.connectionPool = connectionPool;
        this.daoUtil = daoUtil;
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
            connection.setAutoCommit(true);
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

    /*@Override
    public List<Product> getAllProducts() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            preparedStatement = daoUtil.getPreparedStatement(GET_ALL_PRODUCTS_QUERY, connection, Collections.emptyList());
            resultSet = preparedStatement.executeQuery();
            final List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                products.add(getProductFromDb(resultSet));
            }
            connection.commit();
            return products;
        } catch (SQLException | DaoException e) {
            throw new DaoException(e);
        } finally {
            daoUtil.close(resultSet);
            daoUtil.close(preparedStatement);
            connectionPool.retrieveConnection(connection);
        }
    }*/

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
            connection.setAutoCommit(true);
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
    public void changeProductStatus(long productId, String status) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            validator.validateId(productId);
            validator.validateStatus(status);
            boolean newStatus = false;
            if (status.equals("false")){
                newStatus = true;
            }
            List<Object> parameters = Arrays.asList(
                    newStatus,
                    productId
            );
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            preparedStatement = daoUtil.getPreparedStatement(CHANGE_PRODUCT_STATUS_QUERY, connection, parameters);
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
    public long saveProduct(Product product) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

            try {
                long id = productIsExists(product);
                if (id == -1) {
                    List<Object> parameters = Arrays.asList(
                            product.getName(),
                            product.getType(),
                            product.getDescription(),
                            product.getPrice(),
                            product.getIsAvailable()
                    );

                    validator.validateProduct(product);
                connection = connectionPool.takeConnection();
                connection.setAutoCommit(false);
                preparedStatement = daoUtil.getPreparedStatement(SAVE_PRODUCT_QUERY, connection, parameters);
                int affectedRows = preparedStatement.executeUpdate();
                connection.commit();
                id = productIsExists(product);
                } else {
                    id = -1;
                }
                return id;
            } catch (SQLException | DaoException e) {
                throw new DaoException(e);
            } finally {
                daoUtil.close(preparedStatement);
                connectionPool.retrieveConnection(connection);
            }
    }

    public static void main(String[] args) {
        /*ConnectionPool pool = new ConnectionPoolImpl(new DatabaseConfig());
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
            }
        } catch (DaoException e) {
            System.out.println(e.getMessage());
        }*/
    }
}
