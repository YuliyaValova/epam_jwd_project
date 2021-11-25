package com.jwd.dao.validation;

import com.jwd.dao.domain.Address;
import com.jwd.dao.domain.UserAccount;

public interface DaoValidator {
    boolean isUserAccountValid(UserAccount acc);
    boolean isAddressValid(Address address);
    }
