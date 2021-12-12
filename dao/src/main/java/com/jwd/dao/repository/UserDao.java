package com.jwd.dao.repository;

import com.jwd.dao.domain.UserAccount;
import com.jwd.dao.exception.DaoException;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {

    /**
     * Receives a list of all users from db
     * @return a list of user accounts
     * @throws DaoException is a module exception
     */
    List<UserAccount> getUsers() throws DaoException;

    /**
     * Receives a user from db by id
     * @param id identifies user
     * @return user account
     * @throws DaoException is a module exception
     */
    UserAccount getUserById(long id) throws DaoException;

    /**
     * Receives a user from db by login and password
     * @param login for searching
     * @param password for searching
     * @return user account
     * @throws DaoException is a module exception
     */
    UserAccount getUserByLoginAndPassword(String login, String password) throws DaoException;

    /**
     * Saves user account in database
     * @param user for saving
     * @return saved user id
     * @throws DaoException is a module exception
     */
    long saveUserAccount(UserAccount user) throws DaoException;

    /**
     * Deletes user from db by id
     * @param id identifies user
     * @throws DaoException is a module exception
     */
    void deleteUserById(long id) throws DaoException;

    /**
     * Changes user's password
     * @param id identifies user
     * @param oldPassword password to be changed
     * @param newPassword password that is set
     * @throws DaoException is a module exception
     */
    void changePassword(long id, String oldPassword, String newPassword) throws DaoException;

    /**
     * Gives the user administrator rights
     * @param userId identifies user
     * @return true if changed is successful, otherwise - false
     * @throws DaoException is a module exception
     */
    boolean makeAdmin(long userId) throws DaoException;

    /**
     * Updates user's information
     * @param  user for updating
     * @return user id
     * @throws DaoException
     */
    long updateUserAccount(UserAccount user)throws DaoException;
}
