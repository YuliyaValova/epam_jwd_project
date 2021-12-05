package com.jwd.dao.repository;

import com.jwd.dao.domain.UserAccount;
import com.jwd.dao.exception.DaoException;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {

    List<UserAccount> getUsers() throws DaoException;

    UserAccount getUserById(long id) throws DaoException;

    UserAccount getUserByLoginAndPassword(String login, String password) throws DaoException;

    long saveUserAccount(UserAccount user) throws DaoException;

    void deleteUserById(long id) throws DaoException;

    void changePassword(long id, String oldPassword, String newPassword) throws DaoException;

    boolean makeAdmin(long userId) throws DaoException;

}
