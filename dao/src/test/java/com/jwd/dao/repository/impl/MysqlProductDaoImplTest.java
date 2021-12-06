package com.jwd.dao.repository.impl;

import com.jwd.dao.DaoFactory;
import com.jwd.dao.config.DatabaseConfig;
import com.jwd.dao.connection.ConnectionPool;
import com.jwd.dao.connection.ConnectionUtil;
import com.jwd.dao.connection.impl.ConnectionPoolImpl;
import com.jwd.dao.domain.Product;
import com.jwd.dao.exception.DaoException;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import static java.util.Objects.nonNull;

public class MysqlProductDaoImplTest {
    //todo database independent
    //testing class
    private ConnectionPool connectionPool = new ConnectionPoolImpl(new DatabaseConfig());
    private Connection connection = connectionPool.takeConnection();
    private ConnectionUtil util = new ConnectionUtil(connectionPool);
    private ResultSet resultSet;
    private MysqlProductDaoImpl productDao = (MysqlProductDaoImpl) DaoFactory.getFactory().getProductDao();

    public MysqlProductDaoImplTest() throws DaoException {
    }

    @Test
    public void testSaveProduct() throws DaoException, SQLException {
        Product product = new Product("Ppesto", "pizza", "Yummi", 12.2, false);
        // check if products count = oldCount + 1;
        int countBeforeUpdate = 0, countAfterUpdate = 0;
        PreparedStatement preparedStatement = util.getPreparedStatement("select count(*) from Products;", connection, Arrays.asList());
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            countBeforeUpdate = resultSet.getInt(1);
        }
        productDao.saveProduct(product);
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            countAfterUpdate = resultSet.getInt(1);
        }
        connectionPool.retrieveConnection(connection);
        assert (countAfterUpdate == countBeforeUpdate + 1);
    }

    @Test
    public void testSaveProduct_productExists() throws DaoException {
        //given
        Product product = new Product("Ppesto", "pizza", "Yummi", 12.2, false);
        //when
        long id = productDao.saveProduct(product);
        //then
        assert id == -1;
    }

    @Test (expected = DaoException.class)
    public void testGetProductById_negative() throws DaoException {
        //given
        long id = -1;
        //when
        Product product = productDao.getProductById(id);
    }


    @Test
    public void testGetProductById() throws DaoException {
        //given
        long id = 1L;
        //when
        Product product = productDao.getProductById(id);
        //then
        assert nonNull(product);
    }

    @Test (expected = DaoException.class)
    public void testDeleteProductById_negative() throws DaoException {
        long id = -1l;
        productDao.deleteProductById(id);
    }

    @Test
    public void deleteProductById() throws DaoException, SQLException {
        long id = 21l;
        int countBeforeUpdate = 0, countAfterUpdate = 0;
        PreparedStatement preparedStatement = util.getPreparedStatement("select count(*) from Products;", connection, Arrays.asList());
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            countBeforeUpdate = resultSet.getInt(1);
        }
        productDao.deleteProductById(id);
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            countAfterUpdate = resultSet.getInt(1);
        }
        connectionPool.retrieveConnection(connection);
        assert (countAfterUpdate == countBeforeUpdate - 1);

    }

}