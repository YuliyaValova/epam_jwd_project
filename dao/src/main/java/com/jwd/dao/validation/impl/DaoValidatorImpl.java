package com.jwd.dao.validation.impl;

import com.jwd.dao.domain.Address;
import com.jwd.dao.domain.UserAccount;
import com.jwd.dao.exception.DaoException;
import com.jwd.dao.validation.DaoValidator;

import static com.mysql.cj.util.StringUtils.isNullOrEmpty;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class DaoValidatorImpl implements DaoValidator {
    @Override
    public void validateUserAccount(UserAccount acc) throws DaoException {
        if (isNull(acc)) {
            throw new DaoException("User is null.");
        } else {
            if (isIncompleteUser(acc) || isIncompleteAddress(acc)) {
                throw new DaoException("The information is incomplete.");
            }
        }
    }

    @Override
    public void validateLoginAndPassword(String login, String password) throws DaoException {
        if (isNullOrEmpty(login)||isNullOrEmpty(password)){
            throw new DaoException("InvalidLoginOrPassword");
        }
    }

    @Override
    public void validateId(long id) throws DaoException {
        if(id<1L){
            throw new DaoException("InvalidId");
        }
    }

    private boolean isIncompleteAddress(UserAccount acc) {
        boolean isIncomplete = false;
        if (isNull(acc.getAddress())){
            isIncomplete = true;
        } else{
            Address address = acc.getAddress();
            if (
                    isNullOrEmpty(address.getCity()) ||
                    isNullOrEmpty(address.getStreet()) ||
                    isNullOrEmpty(address.getBuilding())
            ){
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
