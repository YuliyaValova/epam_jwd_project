package com.jwd.service.serviceLogic.impl;

import com.jwd.dao.DaoFactory;
import com.jwd.dao.domain.Address;
import com.jwd.dao.exception.DaoException;
import com.jwd.dao.repository.UserDao;
import com.jwd.service.domain.UserAccount;
import com.jwd.service.domain.UserDto;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.serviceLogic.UserService;

import java.sql.SQLException;

public class UserServiceImpl implements UserService {

    private final UserDao userDao = DaoFactory.getFactory().getUserDao();

    @Override
    public void register(UserAccount user) throws ServiceException {
        try {
            //todo validation
            userDao.saveUserAccount(convertToDaoUserAccount(user));
        } catch (final DaoException | SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public UserAccount login(UserAccount user) throws ServiceException {
        try {
            // todo  validation

            String login = user.getLogin();
            System.out.println("login = "+ login);
            String password = user.getPassword();

            UserAccount loginatedUser = new UserAccount(userDao.getUserByLoginAndPassword(login, password));
            System.out.println("from lodin " + loginatedUser);
            return loginatedUser;

        } catch (final DaoException e) {
            throw new ServiceException(e);
        }
    }

    private com.jwd.dao.domain.UserAccount convertToDaoUserAccount(UserAccount user) {
        com.jwd.dao.domain.UserAccount acc = new com.jwd.dao.domain.UserAccount();
        acc.setId(user.getId());
        acc.setLogin(user.getLogin());
        acc.setPassword(user.getPassword());
        acc.setRole(user.getRole());
        acc.setFirstName(user.getFirstName());
        acc.setLastName(user.getLastName());
        acc.setPhone(user.getPhone());
        setAddress(acc, user.getAddress());
        return acc;
    }

    private void setAddress(com.jwd.dao.domain.UserAccount user, com.jwd.service.domain.Address address) {
        Address addressDao = new Address();
        addressDao.setId(address.getId());
        addressDao.setCity(address.getCity());
        addressDao.setStreet(address.getStreet());
        addressDao.setBuilding(address.getBuilding());
        addressDao.setApartment(address.getApartment());
        user.setAddress(addressDao);
    }

}
