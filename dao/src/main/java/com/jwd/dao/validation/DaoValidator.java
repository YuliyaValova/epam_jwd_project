package com.jwd.dao.validation;

import com.jwd.dao.domain.Product;
import com.jwd.dao.domain.UserAccount;
import com.jwd.dao.exception.DaoException;

public interface DaoValidator {

    void validateUserAccount(UserAccount acc) throws DaoException;

    void validateLoginAndPassword(String login, String password) throws DaoException;

    void validateId(long id) throws DaoException;

    void validateProduct(Product product) throws DaoException;

    void validateStatus(String status) throws DaoException;

    void validatePassword(String password) throws DaoException;
}
