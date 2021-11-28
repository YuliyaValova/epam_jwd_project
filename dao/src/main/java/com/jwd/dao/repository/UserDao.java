package com.jwd.dao.repository;

import com.jwd.dao.domain.UserAccount;
import com.jwd.dao.exception.DaoException;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {

    List<UserAccount> getUsers() throws DaoException;

    UserAccount getUserById(long id) throws DaoException, SQLException;

    UserAccount getUserByLoginAndPassword(String login, String password) throws DaoException;

    long saveUserAccount(UserAccount user) throws DaoException, SQLException;

}
