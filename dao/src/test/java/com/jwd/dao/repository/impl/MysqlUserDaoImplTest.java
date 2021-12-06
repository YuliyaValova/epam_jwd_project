package com.jwd.dao.repository.impl;

import com.jwd.dao.connection.ConnectionPool;
import com.jwd.dao.connection.ConnectionUtil;
import com.jwd.dao.exception.DaoException;
import com.jwd.dao.repository.UserDao;
import com.jwd.dao.validation.DaoValidator;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.times;

public class MysqlUserDaoImplTest {

    ConnectionPool connectionPool = Mockito.mock(ConnectionPool.class);
    DaoValidator validator = Mockito.mock(DaoValidator.class);
    Connection connection = Mockito.mock(Connection.class);
    ResultSet resultSet = Mockito.mock(ResultSet.class);
    ConnectionUtil util = Mockito.mock(ConnectionUtil.class);
    PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);

    private MysqlUserDaoImpl dao = new MysqlUserDaoImpl(connectionPool, util);

    @Test
    public void testGetUserById() throws DaoException, SQLException {
        long userId = 1L;
        String query = "select UserAccounts.id, login, password, role, fName, lName, phone, Addresses.id, city, street, building,apartment from UserAccounts\\n\" +\n" +
                "\"left join Addresses on Addresses.id = UserAccounts.address_id\\n\" +\n" +
                "\"where UserAccounts.id = ?;";

        Mockito.doNothing().when(validator).validateId(userId);
        Mockito.when(connectionPool.takeConnection()).thenReturn(connection);
        Mockito.doNothing().when(connection).setAutoCommit(true);
        Mockito.when(util.getPreparedStatement(query,connection, Arrays.asList(userId))).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        Mockito.doNothing().when(connectionPool).retrieveConnection(connection);

        dao.getUserById(userId);

        Mockito.verify(connectionPool).takeConnection();
        Mockito.verify(connection).prepareStatement(query);
        Mockito.verify(preparedStatement, times(1)).executeQuery();
        Mockito.verify(resultSet, times(2)).next();
        Mockito.verify(connectionPool).retrieveConnection(connection);
    }

    @Test
    public void testGetUserByLoginAndPassword() {
    }

    @Test
    public void testDeleteUserById() {
    }

    @Test
    public void testChangePassword() {
    }

}