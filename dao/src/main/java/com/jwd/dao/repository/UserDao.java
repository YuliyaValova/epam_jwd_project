package com.jwd.dao.repository;

import com.jwd.dao.domain.User;
import com.jwd.dao.domain.UserAccount;
import com.jwd.dao.domain.UserDto;
import com.jwd.dao.exception.DaoException;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {

    List<UserAccount> getUsers();

    UserAccount getUserById(Long id) throws DaoException, SQLException;

    UserAccount getUserByLoginAndPassword(String login, String password);

    void saveUserAccount(UserAccount user) throws DaoException, SQLException;

}
