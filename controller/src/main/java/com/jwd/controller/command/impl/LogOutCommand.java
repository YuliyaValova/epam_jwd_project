package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;
import com.jwd.service.ServiceFactory;
import com.jwd.service.domain.UserAccount;
import com.jwd.service.serviceLogic.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.jwd.controller.util.Constants.*;

public class LogOutCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(LogOutCommand.class);
    private final UserService userService = ServiceFactory.getServiceFactory().getUserService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException {
        try {
            logger.info("#LogOutCommand starts.");
            UserAccount acc = (UserAccount) req.getSession().getAttribute(USER);
            String login = acc.getLogin();
            userService.deleteBasket(login);
            req.getSession().invalidate();
            req.getRequestDispatcher(PATH_TO_JSP + Command.prepareUri(req) + JSP).forward(req, resp);
        } catch (Exception e) {
            logger.error("#LogOutCommand throws exception.");
            throw new ControllerException(e.getMessage());
        }
    }
}

