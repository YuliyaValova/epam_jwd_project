package com.jwd.service.validator;

import com.jwd.service.domain.UserAccount;
import com.jwd.service.exception.ServiceException;

public interface ServiceValidator {

    boolean validateUserAccount(UserAccount acc) throws ServiceException;

    boolean validateLoginAndPassword(String login, String password) throws ServiceException;

    boolean validateId(long id) throws ServiceException;

}