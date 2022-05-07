package com.jwd.dao.repository.impl;

import com.jwd.dao.connection.ConnectionPool;
import com.jwd.dao.connection.ConnectionUtil;
import com.jwd.dao.domain.Address;
import com.jwd.dao.domain.UserAccount;
import com.jwd.dao.exception.DaoException;
import com.jwd.dao.repository.UserDao;
import com.jwd.dao.validation.DaoValidator;
import com.jwd.dao.validation.impl.DaoValidatorImpl;

import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//todo logger

public class MysqlUserDaoImpl implements UserDao {
    private static final String FIND_ALL_USERS_QUERY = "select UserAccounts.id, login, password, role, fName, lName, phone, Addresses.id, city, street, building,apartment from UserAccounts\n" +
            "left join Addresses on Addresses.id = UserAccounts.address_id;\n";
    private static final String FIND_USER_BY_ID_QUERY = "select UserAccounts.id, login, password, role, fName, lName, phone, Addresses.id, city, street, building,apartment from UserAccounts\n" +
            "left join Addresses on Addresses.id = UserAccounts.address_id\n" +
            "where UserAccounts.id = ?;";
    private static final String FIND_USER_BY_LOGIN_AND_PASSWORD_QUERY = "select UserAccounts.id, login, password, role, fName, lName, phone, Addresses.id, city, street, building,apartment from UserAccounts\n" +
            "left join Addresses on Addresses.id = UserAccounts.address_id\n" +
            "where UserAccounts.login = ? and UserAccounts.password = MD5(?);";
    private static final String SAVE_ADDRESS_QUERY = "insert into Addresses (city, street, building, apartment) values (?,?,?,?);";
    private static final String SAVE_USER_ACCOUNT_QUERY = "insert into UserAccounts (login, password, role, fName, lName, phone, address_id, registrDate, status) values ( ?, MD5(?), ?, ?, ?, ?, ?, now(), 'active');";
    private static final String IS_ADDRESS_EXISTS_QUERY = "select distinct id from Addresses where\n" +
            "city = ? and street = ? and building = ? and apartment = ?;";
    private static final String IS_USER_ACCOUNT_EXISTS_QUERY = "select id from UserAccounts where login = ?;";
    private static final String DELETE_USER_QUERY = "delete from UserAccounts where id = ?;";
    private static final String UPDATE_PASSWORD_QUERY = "update UserAccounts\n" +
            "set password = MD5(?), useraccounts.updationDate = now()\n" +
            "where id = ? and password = MD5(?);";
    private static final String MAKE_ADMIN_QUERY = "update UserAccounts\n" +
            "set role = \"admin\" , useraccounts.updationDate = now()\n" +
            "where id = ?;";
    private static final String IS_USER_ACCOUNT_EXISTS_BY_ID_QUERY = "select id from UserAccounts where id = ?";
    private static final String UPDATE_USER_ACCOUNT_QUERY = "update UserAccounts\n" +
            "join Addresses on Addresses.id = UserAccounts.address_id\n" +
            "set fName = ?, lName = ?, phone = ?, address_id = ?, useraccounts.updationDate = now()\n" +
            "where UserAccounts.login = ?;";
    private static final String CREATE_BASKET_QUERY = "create table if not exists {0} (\n" +
            "order_detail_id int primary key auto_increment,\n" +
            "product_amount int,\n" +
            "item_price double,\n" +
            "product_id int, \n" +
            "product_detail varchar(100),\n" +
            "creationDate datetime,\n" +
            "updationDate datetime\n" +
            ");";
    private static final String DELETE_BASKET_QUERY = "drop table {0};";

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
            connection.setAutoCommit(true);
            preparedStatement = daoUtil.getPreparedStatement(FIND_ALL_USERS_QUERY, connection, Collections.emptyList());
            resultSet = preparedStatement.executeQuery();
            final List<UserAccount> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(getUserAccountFromDb(resultSet));
            }
            return users;
        } catch (SQLException | DaoException e) {
            throw new DaoException(e);
        } finally {
            daoUtil.close(resultSet);
            daoUtil.close(preparedStatement);
            connectionPool.retrieveConnection(connection);
        }
    }

    private UserAccount getUserAccountFromDb(ResultSet resultSet) throws DaoException {
        try {
            long id = resultSet.getLong(1);
            String login = resultSet.getString(2);
            String password = resultSet.getString(3);
            String role = resultSet.getString(4);
            String firstName = resultSet.getString(5);
            String lastName = resultSet.getString(6);
            String phone = resultSet.getString(7);
            Address address = getAddressForUser(resultSet);
            return new UserAccount(id, login, password, role, firstName, lastName, phone, address);
        } catch (SQLException | DaoException e) {
            throw new DaoException(e);
        }
    }

    private Address getAddressForUser(ResultSet res) throws DaoException {
        try {
            Address address = new Address();
            address.setId(res.getLong(8));
            address.setCity(res.getString(9));
            address.setStreet(res.getString(10));
            address.setBuilding(res.getString(11));
            address.setApartment(res.getString(12));
            return address;
        } catch(SQLException e) {
            throw new DaoException(e);
        }
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
            connection.setAutoCommit(true);
            preparedStatement = daoUtil.getPreparedStatement(FIND_USER_BY_ID_QUERY, connection, parameters);
            resultSet = preparedStatement.executeQuery();
            UserAccount userAcc = null;
            while (resultSet.next()) {
                userAcc = getUserAccountFromDb(resultSet);
            }
            return userAcc;
        } catch (SQLException e) {
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
            connection.setAutoCommit(true);
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
            try {
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
                    validator.validateUserAccount(userAcc);
                    connection = connectionPool.takeConnection();
                    connection.setAutoCommit(false);
                    preparedStatement = daoUtil.getPreparedStatement(SAVE_USER_ACCOUNT_QUERY, connection, parameters);
                    int affectedRows = preparedStatement.executeUpdate();
                    connection.commit();
                    id = userAccountIsExists(userAcc);
                } else {
                    id = -1L;
                }
                return id;
            } catch (SQLException | DaoException e) {
                throw new DaoException(e);
            } finally {
                daoUtil.close(preparedStatement);
                connectionPool.retrieveConnection(connection);
            }
    }

    @Override
    public void deleteUserById(long id) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        List<Object> parameters = Arrays.asList(
                id
        );

        try {
            validator.validateId(id);
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            preparedStatement = daoUtil.getPreparedStatement(DELETE_USER_QUERY, connection, parameters);
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
    public void changePassword(long id, String oldPassword, String newPassword) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        List<Object> parameters = Arrays.asList(
                newPassword,
                id,
                oldPassword
        );

        try {
            validator.validateId(id);
            validator.validatePassword(oldPassword);
            validator.validatePassword(newPassword);
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            preparedStatement = daoUtil.getPreparedStatement(UPDATE_PASSWORD_QUERY, connection, parameters);
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
    public boolean makeAdmin(long userId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        List<Object> parameters = Arrays.asList(
                userId
        );

        try {
            boolean isMakedAdmin = true;
            validator.validateId(userId);
            if (userAccountIsExists(userId) == -1L){
                isMakedAdmin = false;
            }
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            preparedStatement = daoUtil.getPreparedStatement(MAKE_ADMIN_QUERY, connection, parameters);
            int affectedRows = preparedStatement.executeUpdate();
            connection.commit();
            return isMakedAdmin;
        } catch (SQLException | DaoException e) {
            throw new DaoException(e);
        } finally {
            daoUtil.close(preparedStatement);
            connectionPool.retrieveConnection(connection);
        }
    }

    @Override
    public long updateUserAccount(UserAccount user) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            long id = userAccountIsExists(user);
            if (id != -1) {
                Address address = user.getAddress();
                //todo update address but not save
                Long addressId = saveAddress(address);
                List<Object> parameters = Arrays.asList(
                        user.getFirstName(),
                        user.getLastName(),
                        user.getPhone(),
                        addressId,
                        user.getLogin()
                );
                validator.validateUserAccount(user);
                connection = connectionPool.takeConnection();
                connection.setAutoCommit(false);
                preparedStatement = daoUtil.getPreparedStatement(UPDATE_USER_ACCOUNT_QUERY, connection, parameters);
                int affectedRows = preparedStatement.executeUpdate();
                connection.commit();
                id = userAccountIsExists(user);
            } else {
                id = -1L;
            }
            return id;
        } catch (SQLException | DaoException e) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            throw new DaoException(e);
        } finally {
            daoUtil.close(preparedStatement);
            connectionPool.retrieveConnection(connection);
        }
    }

    @Override
    public void createBasket(String login) throws DaoException {
        Connection connection = null;
        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(true);
            Statement preparedStatement1 = connection.createStatement();
            preparedStatement1.execute(MessageFormat.format(CREATE_BASKET_QUERY, login+"_basket"));
            preparedStatement1.close();
        } catch (SQLException | DaoException e) {
            throw new DaoException(e);
        } finally {
            connectionPool.retrieveConnection(connection);
        }
    }

    @Override
    public void deleteBasket(String login) throws DaoException {
        Connection connection = null;
        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(true);
            Statement preparedStatement1 = connection.createStatement();
            preparedStatement1.execute(MessageFormat.format(DELETE_BASKET_QUERY, login+"_basket"));
            preparedStatement1.close();
        } catch (SQLException | DaoException e) {
            throw new DaoException(e);
        } finally {
            connectionPool.retrieveConnection(connection);
        }
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
                connection.setAutoCommit(false);
                preparedStatement = daoUtil.getPreparedStatement(SAVE_ADDRESS_QUERY, connection, parameters);
                int affectedRows = preparedStatement.executeUpdate();
                connection.commit();
                addressId = getAddressId(address);
            } catch (SQLException | DaoException e) {
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
            connection.setAutoCommit(true);
            preparedStatement = daoUtil.getPreparedStatement(IS_ADDRESS_EXISTS_QUERY, connection, parameters);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                addressId = resultSet.getLong(1);
            } else {
                addressId = null;
            }
        } catch (SQLException | DaoException e) {
            throw new DaoException(e);
        } finally {
            daoUtil.close(resultSet);
            daoUtil.close(preparedStatement);
            connectionPool.retrieveConnection(connection);
        }
        return addressId;
    }

    private long userAccountIsExists(UserAccount acc) throws DaoException {
        long id = -1L;
        List<Object> parameters = Arrays.asList(
                acc.getLogin()
        );
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            validator.validateUserAccount(acc);
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(true);
            preparedStatement = daoUtil.getPreparedStatement(IS_USER_ACCOUNT_EXISTS_QUERY, connection, parameters);
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

    private long userAccountIsExists(long id) throws DaoException {
        long userId = -1L;
        List<Object> parameters = Arrays.asList(
                id
        );
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            validator.validateId(id);
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(true);
            preparedStatement = daoUtil.getPreparedStatement(IS_USER_ACCOUNT_EXISTS_BY_ID_QUERY, connection, parameters);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) userId = resultSet.getLong(1);
            return userId;
        } catch (SQLException | DaoException e) {
            throw new DaoException(e);
        } finally {
            daoUtil.close(resultSet);
            daoUtil.close(preparedStatement);
            connectionPool.retrieveConnection(connection);
        }
    }

}
