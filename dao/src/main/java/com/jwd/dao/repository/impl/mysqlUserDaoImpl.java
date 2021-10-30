package com.jwd.dao.repository.impl;

import com.jwd.dao.config.DatabaseConfig;
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

public class mysqlUserDaoImpl implements UserDao {
    private static final String FIND_ALL_USERS_QUERY = "select UserAccounts.id, login, password, role, fName, lName, phone, Addresses.id, city, street, building,apartment from UserAccounts\n" +
            "left join Users on Users.id = UserAccounts.user_id\n" +
            "left join Addresses on Addresses.id = Users.address_id;";
    private static final String FIND_USER_BY_ID_QUERY = "select UserAccounts.id, login, password, role, fName, lName, phone, Addresses.id, city, street, building,apartment from UserAccounts\n" +
            "left join Users on Users.id = UserAccounts.user_id\n" +
            "left join Addresses on Addresses.id = Users.address_id\n" +
            "where UserAccounts.id = ?;";
    private static final String FIND_USER_BY_LOGIN_AND_PASSWORD_QUERY = "select UserAccounts.id, login, password, role, fName, lName, phone, Addresses.id, city, street, building,apartment from UserAccounts\n" +
            "left join Users on Users.id = UserAccounts.user_id\n" +
            "left join Addresses on Addresses.id = Users.address_id\n" +
            "where UserAccounts.login = ? and UserAccounts.password = ?;";
    private static final String SAVE_ADDRESS_QUERY = "insert into Addresses (city, street, building, apartment) values (?,?,?,?);";
    private static final String SAVE_USER_QUERY = "insert into Users (fName,lName,phone, address_id) values (?,?,?, (select id from Addresses where\n"+
            "city = ? and street = ? and building = ? and apartment = ?));";
    private static final String SAVE_USER_ACCOUNT_QUERY = "insert into UserAccounts (login, password, role, user_id) values (?, ?, ?, \n"+
            "(select id from Users where lName = ? and phone = ?));";
    private final DatabaseConfig dataBaseConfig;

    public mysqlUserDaoImpl() {
        dataBaseConfig = new DatabaseConfig();
    }

    @Override
    public List<UserAccount> getUsers() {
        try (Connection connection = dataBaseConfig.getConnection();
             PreparedStatement preparedStatement = getPreparedStatement(FIND_ALL_USERS_QUERY, connection, Collections.emptyList());
             ResultSet resultSet = preparedStatement.executeQuery();) {
            final List<UserAccount> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(getUserAccountFromDb(resultSet));
            }
            return users;
        } catch (SQLException e) {
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
    public UserAccount getUserById(Long id) {
        List<Object> parameters = Arrays.asList(
                id
        );
        try (Connection connection = dataBaseConfig.getConnection();
             PreparedStatement preparedStatement = getPreparedStatement(FIND_USER_BY_ID_QUERY, connection, parameters);
             ResultSet resultSet = preparedStatement.executeQuery();) {
            UserAccount userAcc = null;
            while (resultSet.next()) {
                userAcc = getUserAccountFromDb(resultSet);
            }
            return userAcc;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


    @Override
    public UserAccount getUserByLoginAndPassword(UserAccount user) {
        List<Object> parameters = Arrays.asList(
                user.getLogin(),
                user.getPassword()
        );
        try (Connection connection = dataBaseConfig.getConnection();
             PreparedStatement preparedStatement = getPreparedStatement(FIND_USER_BY_LOGIN_AND_PASSWORD_QUERY, connection, parameters);
             ResultSet resultSet = preparedStatement.executeQuery();) {
            UserAccount userAcc = null;
            while (resultSet.next()) {
                userAcc = getUserAccountFromDb(resultSet);
            }
            return userAcc;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public void saveUser(UserAccount user) {
        List<Object> parameters = Arrays.asList(
                user.getLogin(),
                user.getPassword(),
                user.getRole(),
                5
        );

        saveAddress(user);
        saveUser_(user);
        saveUserAccount(user);
        try (Connection connection = dataBaseConfig.getConnection();
             PreparedStatement preparedStatement = getPreparedStatement(SAVE_USER_QUERY, connection, parameters);) {


        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private void saveUserAccount(UserAccount user) {
        List<Object> parameters = Arrays.asList(
                user.getLogin(),
                user.getPassword(),
                user.getRole(),
                user.getUser().getLastName(),
                user.getUser().getPhone()
        );

        try (Connection connection = dataBaseConfig.getConnection();
             PreparedStatement preparedStatement = getPreparedStatement(SAVE_USER_ACCOUNT_QUERY, connection, parameters);) {
            int affectedRows = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private void saveUser_(UserAccount user) { //todo: find optimal variant to save address_id
        User u = user.getUser();
        List<Object> parameters = Arrays.asList(
                u.getFirstName(),
                u.getLastName(),
                u.getPhone(),
                u.getAddress().getCity(),
                u.getAddress().getStreet(),
                u.getAddress().getBuilding(),
                u.getAddress().getApartment()
        );

        try (Connection connection = dataBaseConfig.getConnection();
             PreparedStatement preparedStatement = getPreparedStatement(SAVE_ADDRESS_QUERY, connection, parameters);) {
            int affectedRows = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private void saveAddress(UserAccount user) {
        Address address = user.getUser().getAddress();
        List<Object> parameters = Arrays.asList(
                address.getCity(),
                address.getStreet(),
                address.getBuilding(),
                address.getApartment()
        );

        try (Connection connection = dataBaseConfig.getConnection();
             PreparedStatement preparedStatement = getPreparedStatement(SAVE_ADDRESS_QUERY, connection, parameters);) {
            int affectedRows = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private PreparedStatement getPreparedStatement(String query, Connection connection, final List<Object> parameters) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        setPreparedStatementParameters(preparedStatement, parameters);
        return preparedStatement;
    }

    private void setPreparedStatementParameters(PreparedStatement preparedStatement, List<Object> parameters) throws SQLException {
        for (int i = 0, queryParameterIndex = 1; i < parameters.size(); i++, queryParameterIndex++) {
            Object parameter = parameters.get(i);
            setPreparedStatementParameter(preparedStatement, queryParameterIndex, parameter);
        }
    }

    private void setPreparedStatementParameter(PreparedStatement preparedStatement, int queryParameterIndex, Object parameter) throws SQLException {
        if (Long.class == parameter.getClass()) {
            preparedStatement.setLong(queryParameterIndex, (Long) parameter);
        } else if (String.class == parameter.getClass()) {
            preparedStatement.setString(queryParameterIndex, (String) parameter);
        }
    }

/*    public static void main(String[] args) throws DaoException {
        UserDao intrf = new mysqlUserDaoImpl();
        UserAccount acc = new UserAccount(1L, "Yulka", "55555", "admin", null);
        intrf.saveUser(acc);
        UserAccount acc2 = new UserAccount(2L, "Yka", "77777", "polz", null);
        intrf.saveUser(acc2);

        List<UserAccount> users = new ArrayList<>();
        users = intrf.getUsers();
        for (UserAccount user : users) {
            System.out.println(user);
        }
    }*/
}
