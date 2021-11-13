package com.jwd.service.serviceLogic.impl;

import com.jwd.dao.domain.Address;
import com.jwd.dao.domain.User;
import com.jwd.dao.exception.DaoException;
import com.jwd.dao.repository.UserDao;
import com.jwd.dao.repository.impl.MysqlUserDaoImpl;
import com.jwd.service.domain.UserAccount;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.serviceLogic.UserService;

import java.sql.SQLException;

public class UserServiceImpl implements UserService {
    private final UserDao userDao = new MysqlUserDaoImpl();

    @Override
    public boolean register(UserAccount user) throws ServiceException {
        boolean success = false;
        try {
            //todo validation
            userDao.saveUserAccount(convertToDaoUserAccount(user));
            success = true;
        } catch (final DaoException | SQLException e) {
            throw new ServiceException(e);
        }
        return success;
    }

    private com.jwd.dao.domain.UserAccount convertToDaoUserAccount(UserAccount user) {
        com.jwd.dao.domain.UserAccount acc = new com.jwd.dao.domain.UserAccount();
        acc.setId(user.getId());
        acc.setLogin(user.getLogin());
        acc.setPassword(user.getPassword());
        acc.setRole(user.getRole());
        setUser(acc, user);
        return acc;
    }

    private void setUser(com.jwd.dao.domain.UserAccount acc, UserAccount user) {
        User userDao = new User();
        userDao.setFirstName(user.getUser().getFirstName());
        userDao.setLastName(user.getUser().getLastName());
        userDao.setPhone(user.getUser().getPhone());
        setAddress(userDao, user.getUser().getAddress());
        acc.setUser(userDao);
    }

    private void setAddress(User user,com.jwd.service.domain.Address address) {
        Address addressDao = new Address();
        addressDao.setId(address.getId());
        addressDao.setCity(address.getCity());
        addressDao.setStreet(address.getStreet());
        addressDao.setBuilding(address.getBuilding());
        addressDao.setApartment(address.getApartment());
        user.setAddress(addressDao);
    }

}
