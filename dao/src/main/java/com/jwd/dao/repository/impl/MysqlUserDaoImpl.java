package com.jwd.dao.repository.impl;

import com.jwd.dao.config.DatabaseConfig;
import com.jwd.dao.connection.ConnectionUtil;
import com.jwd.dao.connection.impl.ConnectionPoolImpl;
import com.jwd.dao.domain.Address;
import com.jwd.dao.domain.User;
import com.jwd.dao.domain.UserAccount;
import com.jwd.dao.exception.DaoException;
import com.jwd.dao.repository.UserDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MysqlUserDaoImpl implements UserDao {
    private static final String FIND_ALL_USERS_QUERY = "select UserAccounts.id, login, password, role, fName, lName, phone, Addresses.id, city, street, building,apartment from UserAccounts left join Users on Users.id = UserAccounts.user_id left join Addresses on Addresses.id = Users.address_id;\n";
    private static final String FIND_USER_BY_ID_QUERY = "select UserAccounts.id, login, password, role, fName, lName, phone, Addresses.id, city, street, building,apartment from UserAccounts\n" +
            "left join Users on Users.id = UserAccounts.user_id\n" +
            "left join Addresses on Addresses.id = Users.address_id\n" +
            "where UserAccounts.id = ?;";
    private static final String FIND_USER_BY_LOGIN_AND_PASSWORD_QUERY = "select UserAccounts.id, login, password, role, fName, lName, phone, Addresses.id, city, street, building,apartment from UserAccounts\n" +
            "left join Users on Users.id = UserAccounts.user_id\n" +
            "left join Addresses on Addresses.id = Users.address_id\n" +
            "where UserAccounts.login = ? and UserAccounts.password = ?;";
    private static final String SAVE_ADDRESS_QUERY = "insert into Addresses (city, street, building, apartment) values (?,?,?,?);";
    private static final String SAVE_USER_QUERY = "insert into Users (fName,lName,phone, address_id) values (?,?,?,?);";
    private static final String SAVE_USER_ACCOUNT_QUERY = "insert into UserAccounts (login, password, role, user_id) values (?, ?, ?, ?);";
    private static final String IS_ADDRESS_EXISTS_QUERY = "select distinct id from Addresses where\n" +
            "city = ? and street = ? and building = ? and apartment = ?;";
    private static final String IS_USER_ACCOUNT_EXISTS_QUERY = "select id from UserAccounts where login = ?;";
    private static final String IS_USER_EXISTS_QUERY = "select distinct id from Users where\n" +
            "fName = ? and lName = ? and phone = ?;";
    private final DatabaseConfig dataBaseConfig;
    private final ConnectionPoolImpl connectionPool;
    private final ConnectionUtil daoUtil;

    public MysqlUserDaoImpl() {
        dataBaseConfig = new DatabaseConfig();
        connectionPool = new ConnectionPoolImpl(dataBaseConfig);
        daoUtil = new ConnectionUtil(connectionPool);
    }


    @Override
    public List<UserAccount> getUsers() {
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = daoUtil.getPreparedStatement(FIND_ALL_USERS_QUERY, connection, Collections.emptyList());
            ResultSet resultSet = preparedStatement.executeQuery();

            final List<UserAccount> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(getUserAccountFromDb(resultSet));
            }
            daoUtil.close(resultSet);
            daoUtil.close(preparedStatement);
            connectionPool.retrieveConnection(connection);
            return users;
        } catch (SQLException | DaoException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private UserAccount getUserAccountFromDb(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(1);
        String login = resultSet.getString(2);
        String password = resultSet.getString(3);
        String role = resultSet.getString(4);
        User user = getUserForAccount(resultSet);
        return new UserAccount(id, login, password, role, user);
    }

    private User getUserForAccount(ResultSet res) throws SQLException {
        User user = new User();
        user.setFirstName(res.getString(5));
        user.setLastName(res.getString(6));
        user.setPhone(res.getString(7));
        user.setAddress(getAddressForUser(res));
        return user;
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
    public UserAccount getUserById(Long id) throws DaoException {
        List<Object> parameters = Arrays.asList(
                id
        );
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = daoUtil.getPreparedStatement(FIND_USER_BY_ID_QUERY, connection, parameters);
            ResultSet resultSet = preparedStatement.executeQuery();
            UserAccount userAcc = null;
            while (resultSet.next()) {
                userAcc = getUserAccountFromDb(resultSet);
            }
            daoUtil.close(resultSet);
            daoUtil.close(preparedStatement);
            connectionPool.retrieveConnection(connection);
            return userAcc;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


    @Override
    public UserAccount getUserByLoginAndPassword(String login, String password) {
        List<Object> parameters = Arrays.asList(
                login,
                password
        );
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = daoUtil.getPreparedStatement(FIND_USER_BY_LOGIN_AND_PASSWORD_QUERY, connection, parameters);
            ResultSet resultSet = preparedStatement.executeQuery();
            UserAccount userAcc = null;
            while (resultSet.next()) {
                userAcc = getUserAccountFromDb(resultSet);
            }
            daoUtil.close(resultSet);
            daoUtil.close(preparedStatement);
            connectionPool.retrieveConnection(connection);
            return userAcc;
        } catch (SQLException | DaoException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public void saveUserAccount(UserAccount userAcc) {
        if (!userAccountIsExists(userAcc)) {
            Address address = userAcc.getUser().getAddress();
            Long addressId = saveAddress(address);
            User user = userAcc.getUser();
            Long userId = saveUser(user, addressId);
            List<Object> parameters = Arrays.asList(
                    userAcc.getLogin(),
                    userAcc.getPassword(),
                    userAcc.getRole(),
                    userId
            );
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            try {
                connection = connectionPool.takeConnection();
                preparedStatement = daoUtil.getPreparedStatement(SAVE_USER_ACCOUNT_QUERY, connection, parameters);
                int affectedRows = preparedStatement.executeUpdate();
            } catch (SQLException | DaoException e) {
                e.printStackTrace();
            } finally {
                daoUtil.close(preparedStatement);
                connectionPool.retrieveConnection(connection);
            }

        } else System.out.println("Already exists");

    }

    private Long saveUser(User user, Long addressId) {
        Long userId = getUserId(user);
        if (userId == null) {
            List<Object> parameters = Arrays.asList(
                    user.getFirstName(),
                    user.getLastName(),
                    user.getPhone(),
                    addressId
            );
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            try {
                connection = connectionPool.takeConnection();
                preparedStatement = daoUtil.getPreparedStatement(SAVE_USER_QUERY, connection, parameters);
                int affectedRows = preparedStatement.executeUpdate();
                userId = getUserId(user);
                return userId;
            } catch (SQLException | DaoException e) {
                e.printStackTrace();
            } finally {
                daoUtil.close(preparedStatement);
                connectionPool.retrieveConnection(connection);
            }
        }
        return userId;
    }

    private Long getUserId(User user) {
        Long userId = null;
        List<Object> parameters = Arrays.asList(
                user.getFirstName(),
                user.getLastName(),
                user.getPhone()
        );
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = daoUtil.getPreparedStatement(IS_USER_EXISTS_QUERY, connection, parameters);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userId = resultSet.getLong(1);
            } else userId = null;
        } catch (SQLException | DaoException e) {
            e.printStackTrace();
        } finally {
            daoUtil.close(resultSet);
            daoUtil.close(preparedStatement);
            connectionPool.retrieveConnection(connection);
        }
        return userId;
    }

    private Long saveAddress(Address address) {
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
            } finally {
                daoUtil.close(preparedStatement);
                connectionPool.retrieveConnection(connection);
            }
        }
        return addressId;
    }

    private Long getAddressId(Address address) {
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
        } finally {
            daoUtil.close(resultSet);
            daoUtil.close(preparedStatement);
            connectionPool.retrieveConnection(connection);
        }
        return addressId;
    }

    private boolean userAccountIsExists(UserAccount acc) {
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
            if (resultSet.next()) return true;
            else return false;

        } catch (SQLException | DaoException e) {
            e.printStackTrace();
            throw new RuntimeException();

        } finally {
            daoUtil.close(resultSet);
            daoUtil.close(preparedStatement);
            connectionPool.retrieveConnection(connection);
        }
    }

}
