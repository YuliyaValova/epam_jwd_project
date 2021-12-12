package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.jwd.controller.util.Constants.JSP;
import static com.jwd.controller.util.Constants.PATH_TO_JSP;

public class LogOutCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(LogOutCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException {
        try {
            logger.info("#LogOutCommand starts.");
            req.getSession().invalidate();
            req.getRequestDispatcher(PATH_TO_JSP + Command.prepareUri(req) + JSP).forward(req, resp);
        } catch (Exception e) {
            logger.error("#LogOutCommand throws exception.");
            throw new ControllerException(e.getMessage());
        }
    }
}

