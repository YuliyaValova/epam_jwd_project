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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.security.provider.MD5;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.util.Objects.isNull;

public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
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
                logger.info("#register invalid user info.");
                isSuccessful = -2;
            }
            return isSuccessful;
        } catch (final DaoException e) {
            logger.error("#register throws exception.");
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
            } else {
                logger.info("#login invalid user info.");
            }
            return loginatedUser;

        } catch (final DaoException e) {
            logger.error("#login throws exception.");
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteAccount(long id) throws ServiceException {
        try {
            if (validator.validateId(id)) {
                userDao.deleteUserById(id);
            } else {
                logger.info("#deleteAccount invalid id.");
            }
        } catch (final DaoException e) {
            logger.error("#deleteAccount throws exception.");
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean changePassword(long id, String oldPassword, String newPassword) throws ServiceException {
        boolean isSuccessful = false;
        try {
            String hashedPassword = getHash(oldPassword);
            if (validator.validatePassword(oldPassword) && validator.validatePassword(newPassword) && validator.validateId(id)) {
                UserAccount acc = new UserAccount(userDao.getUserById(id));
                if (!isNull(acc) && acc.getPassword().equals(hashedPassword)) {
                    userDao.changePassword(id, oldPassword, newPassword);
                    isSuccessful = true;
                }
            } else {
                logger.info("#changePassword invalid info.");
            }
            return isSuccessful;
        } catch (final DaoException | UnsupportedEncodingException | NoSuchAlgorithmException e) {
            logger.error("#changePassword throws exception.");
            throw new ServiceException(e);
        }
    }

    private String getHash(String oldPassword) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] oldPasswordBytes = oldPassword.getBytes("UTF-8");
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] theMD5digest = md.digest(oldPasswordBytes);
        StringBuffer sb = new StringBuffer();
        for (byte aByteData : theMD5digest) {
            sb.append(Integer.toString((aByteData & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    @Override
    public boolean addAdmin(long userId) throws ServiceException {
        try {
            boolean isSuccess = false;
            if (validator.validateId(userId)) {
                if (userDao.makeAdmin(userId)) {
                    isSuccess = true;
                    logger.info("New admin is added! It's id - " + userId);
                }
            } else {
                logger.info("#addAdmin invalid id.");
            }
            return isSuccess;
        } catch (final DaoException e) {
            logger.error("#addAdmin throws exception.");
            throw new ServiceException(e);
        }
    }

    @Override
    public UserAccount findUser(long userId) throws ServiceException {
        try {
            UserAccount account = null;
            if (validator.validateId(userId)) {
                account = new UserAccount(userDao.getUserById(userId));
            } else {
                logger.info("#findUser invalid id.");
            }
            return account;
        } catch (final DaoException e) {
            logger.error("#findUser throws exception.");
            throw new ServiceException(e);
        }
    }

    @Override
    public int update(UserAccount user) throws ServiceException {
        try {
            int isSuccessful = -1;
            if (validator.validateUserAccount(user)) {
                long id = userDao.updateUserAccount(convertToDaoUserAccount(user));
                if (id != -1L) {
                    isSuccessful = 1;
                }
            } else {
                logger.info("#update invalid user info.");
                isSuccessful = -2;
            }
            return isSuccessful;
        } catch (final DaoException e) {
            logger.error("#update throws exception.");
            throw new ServiceException(e);
        }
    }

    @Override
    public void createBasket(String login) throws ServiceException {
        try {
            userDao.createBasket(login);
        } catch (final DaoException e) {
            logger.error("#createBasket throws exception.");
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteBasket(String login) throws ServiceException {
        try {
            userDao.deleteBasket(login);
        } catch (final DaoException e) {
            logger.error("#createBasket throws exception.");
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
