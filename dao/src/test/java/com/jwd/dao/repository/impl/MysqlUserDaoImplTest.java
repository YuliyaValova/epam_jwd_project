package com.jwd.dao.repository.impl;

import com.jwd.dao.connection.ConnectionPool;
import com.jwd.dao.connection.ConnectionUtil;
import com.jwd.dao.domain.UserAccount;
import com.jwd.dao.exception.DaoException;
import com.jwd.dao.validation.DaoValidator;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import static org.mockito.Mockito.times;

public class MysqlUserDaoImplTest {

    ConnectionPool connectionPool = Mockito.mock(ConnectionPool.class);
    DaoValidator validator = Mockito.mock(DaoValidator.class);
    Connection connection = Mockito.mock(Connection.class);
    ResultSet resultSet = Mockito.mock(ResultSet.class);
    ConnectionUtil util = Mockito.mock(ConnectionUtil.class);
    PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);

    private final MysqlUserDaoImpl dao = new MysqlUserDaoImpl(connectionPool, util);

    @Test
    public void testGetUserById() throws DaoException, SQLException {
        long userId = 1L;
        String query = "select UserAccounts.id, login, password, role, fName, lName, phone, Addresses.id, city, street, building,apartment from UserAccounts\n" +
                "left join Addresses on Addresses.id = UserAccounts.address_id\n" +
                "where UserAccounts.id = ?;";

        Mockito.doNothing().when(validator).validateId(userId);
        Mockito.when(connectionPool.takeConnection()).thenReturn(connection);
        Mockito.doNothing().when(connection).setAutoCommit(true);
        Mockito.when(util.getPreparedStatement(query, connection, Arrays.asList(userId))).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        Mockito.doNothing().when(connectionPool).retrieveConnection(connection);

        dao.getUserById(userId);

        Mockito.verify(connectionPool).takeConnection();
        Mockito.verify(util).getPreparedStatement(query, connection, Arrays.asList(userId));
        Mockito.verify(preparedStatement, times(1)).executeQuery();
        Mockito.verify(resultSet, times(2)).next();
        Mockito.verify(connectionPool).retrieveConnection(connection);
    }

    @Test
    public void testGetUserByLoginAndPassword() throws DaoException, SQLException {
        UserAccount user = new UserAccount("login", "password", "role", "fn", "ln", "phone", null);

        String query = "select UserAccounts.id, login, password, role, fName, lName, phone, Addresses.id, city, street, building,apartment from UserAccounts\n" +
                "left join Addresses on Addresses.id = UserAccounts.address_id\n" +
                "where UserAccounts.login = ? and UserAccounts.password = ?;";

        Mockito.doNothing().when(validator).validateLoginAndPassword(user.getLogin(), user.getPassword());
        Mockito.when(connectionPool.takeConnection()).thenReturn(connection);

        Mockito.when(util.getPreparedStatement(query, connection, Arrays.asList(user.getLogin(), user.getPassword())))
                .thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        Mockito.doNothing().when(connectionPool).retrieveConnection(connection);

        dao.getUserByLoginAndPassword(user.getLogin(), user.getPassword());

        Mockito.verify(connectionPool).takeConnection();
        Mockito.verify(util).getPreparedStatement(query, connection, Arrays.asList(user.getLogin(), user.getPassword()));
        Mockito.verify(preparedStatement, times(1)).executeQuery();
        Mockito.verify(resultSet, times(1)).next();
        Mockito.verify(connectionPool).retrieveConnection(connection);
    }

    @Test
    public void testDeleteUserById() throws DaoException, SQLException {
        long userId = 1L;
        String query = "delete from UserAccounts where id = ?;";

        Mockito.doNothing().when(validator).validateId(userId);
        Mockito.when(connectionPool.takeConnection()).thenReturn(connection);
        Mockito.doNothing().when(connection).setAutoCommit(true);
        Mockito.when(util.getPreparedStatement(query, connection, Arrays.asList(userId))).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);
        Mockito.doNothing().when(connection).commit();
        Mockito.doNothing().when(connectionPool).retrieveConnection(connection);

        dao.deleteUserById(userId);

        Mockito.verify(connectionPool).takeConnection();
        Mockito.verify(util).getPreparedStatement(query, connection, Arrays.asList(userId));
        Mockito.verify(preparedStatement, times(1)).executeUpdate();
        Mockito.verify(connection, times(1)).commit();
        Mockito.verify(connectionPool).retrieveConnection(connection);
    }

    @Test
    public void testChangePassword() throws DaoException, SQLException {
        UserAccount user = new UserAccount(5L, "login", "password", "role", "fn", "ln", "phone", null);
        String newPassword = "newPassword";
        String query = "update UserAccounts\n" +
                "set password = ?\n" +
                "where id = ? and password = ?;";

        Mockito.doNothing().when(validator).validateId(user.getId());
        Mockito.doNothing().when(validator).validatePassword(user.getPassword());
        Mockito.doNothing().when(validator).validatePassword(newPassword);
        Mockito.when(connectionPool.takeConnection()).thenReturn(connection);
        Mockito.doNothing().when(connection).setAutoCommit(false);
        Mockito.when(util.getPreparedStatement(query, connection, Arrays.asList(newPassword, user.getId(), user.getPassword())))
                .thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);
        Mockito.doNothing().when(connection).commit();
        Mockito.doNothing().when(connectionPool).retrieveConnection(connection);

        dao.changePassword(user.getId(), user.getPassword(), newPassword);

        Mockito.verify(connectionPool).takeConnection();
        Mockito.verify(connection).setAutoCommit(false);
        Mockito.verify(util).getPreparedStatement(query, connection, Arrays.asList(newPassword, user.getId(), user.getPassword()));
        Mockito.verify(preparedStatement, times(1)).executeUpdate();
        Mockito.verify(connection, times(1)).commit();
        Mockito.verify(connectionPool).retrieveConnection(connection);
    }
}

