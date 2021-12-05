package com.jwd.service.serviceLogic;


import com.jwd.service.domain.UserAccount;
import com.jwd.service.exception.ServiceException;

public interface UserService {

    /**
     * Registers a new user
     * @param user to be registered
     * @return -2 if user is not valid, -1 if such user already exists, 1 if registration is successful
     * @throws ServiceException is a module exception
     */
    int register(UserAccount user) throws ServiceException;

    /**
     * Lets the user into the system
     * @param user to be sign in
     * @return logged user account
     * @throws ServiceException is a module exception
     */
    UserAccount login(UserAccount user) throws ServiceException;

    /**
     * Deletes user account by id
     * @param id for searching account in db
     * @throws ServiceException is a module exception
     */
    void deleteAccount(long id) throws ServiceException;

    /**
     * Changes user's password
     * @param id for searching that user in db
     * @param oldPassword that is changed
     * @param newPassword that will be installed
     * @return true if change is successful, otherwise - false
     * @throws ServiceException is a module exception
     */
    boolean changePassword(long id, String oldPassword, String newPassword) throws ServiceException;

    /**
     * Sets administrator rights to the specified user
     * @param userId identifies the user to be searched for
     * @return true if appointment was successful, otherwise - false
     * @throws ServiceException is a module exception
     */
    boolean addAdmin(long userId) throws ServiceException;

    /**
     * Finds user by id
     * @param userId for searching user in db
     * @return searching user account
     * @throws ServiceException is a module exception
     */
    UserAccount findUser(long userId) throws ServiceException;
}
