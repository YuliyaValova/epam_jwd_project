package com.jwd.dao.validation;

import com.jwd.dao.domain.Address;
import com.jwd.dao.domain.UserAccount;
import com.jwd.dao.exception.DaoException;

public interface DaoValidator {

    void validateUserAccount(UserAccount acc) throws DaoException;

    void validateLoginAndPassword(String login, String password) throws DaoException;

    void validateId(long id) throws DaoException;

}
