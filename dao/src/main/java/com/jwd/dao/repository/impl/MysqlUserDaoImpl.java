package com.jwd.dao.repository.impl;

import com.jwd.dao.connection.ConnectionPool;
import com.jwd.dao.connection.ConnectionUtil;
import com.jwd.dao.domain.Address;
import com.jwd.dao.domain.UserAccount;
import com.jwd.dao.exception.DaoException;
import com.jwd.dao.repository.UserDao;
import com.jwd.dao.validation.DaoValidator;
import com.jwd.dao.validation.impl.DaoValidatorImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//todo validation
//todo logger

public class MysqlUserDaoImpl implements UserDao {
    private static final String FIND_ALL_USERS_QUERY = "select UserAccounts.id, login, password, role, fName, lName, phone, Addresses.id, city, street, building,apartment from UserAccounts\n" +
            "left join Addresses on Addresses.id = UserAccounts.address_id;\n";
    private static final String FIND_USER_BY_ID_QUERY = "select UserAccounts.id, login, password, role, fName, lName, phone, Addresses.id, city, street, building,apartment from UserAccounts\n" +
            "left join Addresses on Addresses.id = UserAccounts.address_id\n" +
            "where UserAccounts.id = ?;";
    private static final String FIND_USER_BY_LOGIN_AND_PASSWORD_QUERY = "select UserAccounts.id, login, password, role, fName, lName, phone, Addresses.id, city, street, building,apartment from UserAccounts\n" +
            "left join Addresses on Addresses.id = UserAccounts.address_id\n" +
            "where UserAccounts.login = ? and UserAccounts.password = ?;";
    private static final String SAVE_ADDRESS_QUERY = "insert into Addresses (city, street, building, apartment) values (?,?,?,?);";
    private static final String SAVE_USER_ACCOUNT_QUERY = "insert into UserAccounts (login, password, role, fName, lName, phone, address_id) values ( ?, ?, ?, ?, ?, ?, ?);";
    private static final String IS_ADDRESS_EXISTS_QUERY = "select distinct id from Addresses where\n" +
            "city = ? and street = ? and building = ? and apartment = ?;";
    private static final String IS_USER_ACCOUNT_EXISTS_QUERY = "select id from UserAccounts where login = ?;";
    private final ConnectionPool connectionPool;
    private final ConnectionUtil daoUtil;
    private final DaoValidator validator = new DaoValidatorImpl();

    public MysqlUserDaoImpl(ConnectionPool connectionPool, ConnectionUtil daoUtil) {
        this.connectionPool = connectionPool;
        this.daoUtil = daoUtil;
    }


    @Override
    public List<UserAccount> getUsers() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = daoUtil.getPreparedStatement(FIND_ALL_USERS_QUERY, connection, Collections.emptyList());
            resultSet = preparedStatement.executeQuery();
            final List<UserAccount> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(getUserAccountFromDb(resultSet));
            }
            return users;
        } catch (SQLException | DaoException e) {
            e.printStackTrace();
            throw new DaoException(e);
        } finally {
            daoUtil.close(resultSet);
            daoUtil.close(preparedStatement);
            connectionPool.retrieveConnection(connection);
        }
    }

    private UserAccount getUserAccountFromDb(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(1);
        String login = resultSet.getString(2);
        String password = resultSet.getString(3);
        String role = resultSet.getString(4);
        String firstName = resultSet.getString(5);
        String lastName = resultSet.getString(6);
        String phone = resultSet.getString(7);
        Address address = getAddressForUser(resultSet);
        return new UserAccount(id, login, password, role, firstName, lastName, phone, address);
    }

    private Address getAddressForUser(ResultSet res) throws SQLException {
        Address address = new Address();
        address.setId(res.getLong(8));
        address.setCity(res.getString(9));
        address.setStreet(res.getString(10));
        address.setBuilding(res.getString(11));
        address.setApartment(res.getString(12));
        return address;
    }

    @Override
    public UserAccount getUserById(long id) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<Object> parameters = Arrays.asList(
                id
        );
        try {
            validator.validateId(id);
            connection = connectionPool.takeConnection();
            preparedStatement = daoUtil.getPreparedStatement(FIND_USER_BY_ID_QUERY, connection, parameters);
            resultSet = preparedStatement.executeQuery();
            UserAccount userAcc = null;
            while (resultSet.next()) {
                userAcc = getUserAccountFromDb(resultSet);
            }
            return userAcc;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException(e);
        } finally {
            daoUtil.close(resultSet);
            daoUtil.close(preparedStatement);
            connectionPool.retrieveConnection(connection);
        }
    }

    @Override
    public UserAccount getUserByLoginAndPassword(String login, String password) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<Object> parameters = Arrays.asList(
                login,
                password
        );
        try {
            validator.validateLoginAndPassword(login, password);
            connection = connectionPool.takeConnection();
            preparedStatement = daoUtil.getPreparedStatement(FIND_USER_BY_LOGIN_AND_PASSWORD_QUERY, connection, parameters);
            resultSet = preparedStatement.executeQuery();
            UserAccount userAcc = null;
            if (resultSet.next()) {
                userAcc = getUserAccountFromDb(resultSet);
            } else {
                userAcc = new UserAccount();
                userAcc.setId(-1L);
            }
            return userAcc;
        } catch (SQLException | DaoException e) {
            e.printStackTrace();
            throw new DaoException(e);
        } finally {
            daoUtil.close(resultSet);
            daoUtil.close(preparedStatement);
            connectionPool.retrieveConnection(connection);
        }

    }

    @Override
    public long saveUserAccount(UserAccount userAcc) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        long id = userAccountIsExists(userAcc);
        if (id == -1) {
            Address address = userAcc.getAddress();
            Long addressId = saveAddress(address);
            List<Object> parameters = Arrays.asList(

                    userAcc.getLogin(),
                    userAcc.getPassword(),
                    userAcc.getRole(),
                    userAcc.getFirstName(),
                    userAcc.getLastName(),
                    userAcc.getPhone(),
                    addressId
            );

            try {
                validator.validateUserAccount(userAcc);
                connection = connectionPool.takeConnection();
                connection.setAutoCommit(false);
                preparedStatement = daoUtil.getPreparedStatement(SAVE_USER_ACCOUNT_QUERY, connection, parameters);
                int affectedRows = preparedStatement.executeUpdate();
                connection.commit();
                return userAccountIsExists(userAcc);
            } catch (SQLException | DaoException e) {
                e.printStackTrace();
                throw new DaoException(e);
            } finally {
                daoUtil.close(preparedStatement);
                connectionPool.retrieveConnection(connection);
            }

        } else return -1;

    }

    private Long saveAddress(Address address) throws DaoException {
        Long addressId = getAddressId(address);
        if (addressId == null) {
            List<Object> parameters = Arrays.asList(
                    address.getCity(),
                    address.getStreet(),
                    address.getBuilding(),
                    address.getApartment()
            );
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            try {
                connection = connectionPool.takeConnection();
                preparedStatement = daoUtil.getPreparedStatement(SAVE_ADDRESS_QUERY, connection, parameters);
                int affectedRows = preparedStatement.executeUpdate();
                addressId = getAddressId(address);
                return addressId;
            } catch (SQLException | DaoException e) {
                e.printStackTrace();
                throw new DaoException(e);
            } finally {
                daoUtil.close(preparedStatement);
                connectionPool.retrieveConnection(connection);
            }
        }
        return addressId;
    }

    private Long getAddressId(Address address) throws DaoException {
        Long addressId = null;
        List<Object> parameters = Arrays.asList(
                address.getCity(),
                address.getStreet(),
                address.getBuilding(),
                address.getApartment()
        );
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = daoUtil.getPreparedStatement(IS_ADDRESS_EXISTS_QUERY, connection, parameters);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                addressId = resultSet.getLong(1);
            } else addressId = null;
        } catch (SQLException | DaoException e) {
            e.printStackTrace();
            throw new DaoException(e);
        } finally {
            daoUtil.close(resultSet);
            daoUtil.close(preparedStatement);
            connectionPool.retrieveConnection(connection);
        }
        return addressId;
    }

    private long userAccountIsExists(UserAccount acc) throws DaoException {
        Long id = -1L;
        List<Object> parameters = Arrays.asList(
                acc.getLogin()
        );
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = daoUtil.getPreparedStatement(IS_USER_ACCOUNT_EXISTS_QUERY, connection, parameters);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) id = resultSet.getLong(1);
            return id;

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
