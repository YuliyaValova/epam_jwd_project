package com.jwd.service.validator.impl;

import com.jwd.service.domain.Address;
import com.jwd.service.domain.UserAccount;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.validator.ServiceValidator;

import static com.mysql.cj.util.StringUtils.isNullOrEmpty;
import static java.util.Objects.isNull;

public class ServiceValidatorImpl implements ServiceValidator {
    @Override
    public boolean validateUserAccount(UserAccount acc) throws ServiceException {
        boolean isValid = true;
        if (isNull(acc)) {
            isValid = false;
        } else {
            if (isIncompleteUser(acc) || isIncompleteAddress(acc)) {
                isValid = false;
            }
        }
        return isValid;
    }

    @Override
    public boolean validateLoginAndPassword(String login, String password) throws ServiceException {
        boolean isValid = true;
        if (isNullOrEmpty(login) || isNullOrEmpty(password)) {
            isValid = false;
        }
        return isValid;
    }

    @Override
    public boolean validateId(long id) throws ServiceException {
        boolean isValid = true;
        if (id < 1L) {
            isValid = false;
        }
        return isValid;
    }

    @Override
    public boolean validatePassword(String password) {
        boolean isValid = true;
        if (isNullOrEmpty(password)) {
            isValid = false;
        }
        return isValid;
    }

    private boolean isIncompleteAddress(UserAccount acc) {
        boolean isIncomplete = false;
        if (isNull(acc.getAddress())) {
            isIncomplete = true;
        } else {
            Address address = acc.getAddress();
            if (
                    isNullOrEmpty(address.getCity()) ||
                            isNullOrEmpty(address.getStreet()) ||
                            isNullOrEmpty(address.getBuilding())
            ) {
                isIncomplete = true;
            }
        }
        return isIncomplete;
    }

    private boolean isIncompleteUser(UserAccount acc) {
        boolean isIncomplete = false;
        if (
                isNullOrEmpty(acc.getLogin()) ||
                        isNullOrEmpty(acc.getPassword()) ||
                        isNullOrEmpty(acc.getRole()) ||
                        isNullOrEmpty(acc.getFirstName()) ||
                        isNullOrEmpty(acc.getLastName()) ||
                        isNullOrEmpty(acc.getPhone())
        ) {
            isIncomplete = true;
        }
        return isIncomplete;
    }

}
