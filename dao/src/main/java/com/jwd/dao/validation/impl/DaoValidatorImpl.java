package com.jwd.dao.validation.impl;

import com.jwd.dao.domain.Address;
import com.jwd.dao.domain.UserAccount;
import com.jwd.dao.validation.DaoValidator;

import static java.util.Objects.nonNull;

public class DaoValidatorImpl implements DaoValidator {
    @Override
    public boolean isUserAccountValid(UserAccount acc) {
        return false;
    }

    @Override
    public boolean isAddressValid(Address address) {
        return false;
    }
}
