package com.jwd.service.serviceLogic;


import com.jwd.service.domain.UserAccount;
import com.jwd.service.exception.ServiceException;

public interface UserService {

    int register(UserAccount user) throws ServiceException;

    UserAccount login(UserAccount user) throws ServiceException;

}
