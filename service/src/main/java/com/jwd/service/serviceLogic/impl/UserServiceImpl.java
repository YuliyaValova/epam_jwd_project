package com.jwd.service.serviceLogic.impl;

import com.jwd.dao.DaoFactory;
import com.jwd.dao.domain.Address;
import com.jwd.dao.exception.DaoException;
import com.jwd.dao.repository.UserDao;
import com.jwd.service.domain.UserAccount;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.serviceLogic.UserService;
import com.jwd.service.validator.ServiceValidator;
import com.jwd.service.validator.impl.ServiceValidatorImpl;

import java.sql.SQLException;

import static java.util.Objects.isNull;

public class UserServiceImpl implements UserService {

    private final UserDao userDao = DaoFactory.getFactory().getUserDao();
    private final ServiceValidator validator = new ServiceValidatorImpl();

    @Override
    public int register(UserAccount user) throws ServiceException {
        try {
            int isSuccessful = -1;
            if (validator.validateUserAccount(user)) {
                long id = userDao.saveUserAccount(convertToDaoUserAccount(user));
                if (id != -1L) {
                    isSuccessful = 1;
                }
            } else {
                isSuccessful = -2;
            }
            return isSuccessful;
        } catch (final DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public UserAccount login(UserAccount user) throws ServiceException {
        try {
            UserAccount loginatedUser = null;
            String login = user.getLogin();
            String password = user.getPassword();
            if (validator.validateLoginAndPassword(login, password)) {
                loginatedUser = new UserAccount(userDao.getUserByLoginAndPassword(login, password));
            }
            return loginatedUser;

        } catch (final DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteAccount(long id) throws ServiceException {
        try {
            if (validator.validateId(id)) {
                userDao.deleteUserById(id);
            }
        } catch (final DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean changePassword(long id, String oldPassword, String newPassword) throws ServiceException {
        boolean isSuccessful = false;
        try {
            if (validator.validatePassword(oldPassword) && validator.validatePassword(newPassword) && validator.validateId(id)) {
                UserAccount acc = new UserAccount(userDao.getUserById(id));
                if (!isNull(acc)&&acc.getPassword().equals(oldPassword)) {
                    userDao.changePassword(id, oldPassword, newPassword);
                    isSuccessful = true;
                }
            }
            return isSuccessful;
        } catch (final DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean addAdmin(long userId) throws ServiceException {
        try {
            boolean isSuccess = false;
            if (validator.validateId(userId)) {
                if (userDao.makeAdmin(userId)) {
                    isSuccess = true;
                }
            }
            return isSuccess;
        } catch (final DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public UserAccount findUser(long userId) throws ServiceException {
        try {
            UserAccount account = null;
            if (validator.validateId(userId)) {
                account = new UserAccount(userDao.getUserById(userId));
            }
            return account;
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
