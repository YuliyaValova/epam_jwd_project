package com.jwd.dao.repository;

import com.jwd.dao.domain.User;
import com.jwd.dao.domain.UserAccount;
import com.jwd.dao.domain.UserDto;
import com.jwd.dao.exception.DaoException;

import java.util.List;

public interface UserDao {

    List<UserAccount> getUsers();

    UserAccount getUserById(Long id);

    UserAccount getUserByLoginAndPassword(UserAccount user);

    void saveUser(UserAccount user) throws DaoException;

}
