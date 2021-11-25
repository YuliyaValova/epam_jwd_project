package com.jwd.service.serviceLogic;


import com.jwd.service.domain.UserAccount;
import com.jwd.service.domain.UserDto;
import com.jwd.service.exception.ServiceException;

public interface UserService {

    void register (UserAccount user) throws ServiceException;

    UserAccount login (UserAccount user) throws ServiceException;

}
