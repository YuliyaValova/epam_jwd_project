package com.jwd.dao.validation;

import com.jwd.dao.domain.Product;
import com.jwd.dao.domain.UserAccount;
import com.jwd.dao.exception.DaoException;

public interface DaoValidator {

    /**
     * Validates user account
     * @param acc for validation
     * @return true if valid, otherwise - false
     * @throws DaoException is a module exception
     */
    void validateUserAccount(UserAccount acc) throws DaoException;

    /**
     * Validates user's login and password
     * @param login for validation
     * @param password for validation
     * @return true if login and password not null or empty, otherwise - false
     * @throws DaoException is a module exception
     */
    void validateLoginAndPassword(String login, String password) throws DaoException;

    /**
     * Validates id
     * @param id for validation
     * @return true if id is valid, otherwise - false
     * @throws DaoException is a module exception
     */
    void validateId(long id) throws DaoException;

    /**
     * Validates product
     * @param product for validation
     * @return true if product is valid, otherwise - false
     */
    void validateProduct(Product product) throws DaoException;

    /**
     * Validates order's status
     * @param status for validation
     * @return true if it valid, otherwise - false
     */
    void validateStatus(String status) throws DaoException;

    /**
     * Validates user's password
     * @param password for validation
     * @return true if password not null or empty, otherwise - false
     */
    void validatePassword(String password) throws DaoException;
}
