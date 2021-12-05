package com.jwd.service.validator;

import com.jwd.dao.domain.Product;
import com.jwd.service.domain.UserAccount;
import com.jwd.service.exception.ServiceException;

public interface ServiceValidator {

    /**
     * Validates user account
     * @param acc for validation
     * @return true if valid, otherwise - false
     * @throws ServiceException is a module exception
     */
    boolean validateUserAccount(UserAccount acc) throws ServiceException;

    /**
     * Validates user's login and password
     * @param login for validation
     * @param password for validation
     * @return true if login and password not null or empty, otherwise - false
     * @throws ServiceException is a module exception
     */
    boolean validateLoginAndPassword(String login, String password) throws ServiceException;

    /**
     * Validates id
     * @param id for validation
     * @return true if id is valid, otherwise - false
     * @throws ServiceException is a module exception
     */
    boolean validateId(long id) throws ServiceException;

    /**
     * Validates user's password
     * @param oldPassword for validation
     * @return true if password not null or empty, otherwise - false
     */
    boolean validatePassword(String oldPassword);

    /**
     * Validates product
     * @param product for validation
     * @return true if product is valid, otherwise - false
     */
    boolean validateProduct(Product product);

    /**
     * Validates order's status
     * @param newStatus for validation
     * @return true if it is in the list of valid values, otherwise - false
     */
    boolean validateStatus(String newStatus);
}
