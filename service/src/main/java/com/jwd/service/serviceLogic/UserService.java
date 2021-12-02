package com.jwd.service.serviceLogic;


import com.jwd.service.domain.UserAccount;
import com.jwd.service.exception.ServiceException;

public interface UserService {

    int register(UserAccount user) throws ServiceException;

    UserAccount login(UserAccount user) throws ServiceException;

    void deleteAccount(long id) throws ServiceException;

    boolean changePassword(long id, String oldPassword, String newPassword) throws ServiceException;

    void addAdmin(long userId) throws ServiceException;

    UserAccount findUser(long userId) throws ServiceException;
}
