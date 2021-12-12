package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.jwd.controller.util.Constants.*;


public class DefaultCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(DefaultCommand.class);
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException {
        try {
            logger.info("#DefaultCommand starts.");
            req.getRequestDispatcher(PATH_TO_JSP + Command.prepareUri(req) + JSP).forward(req, resp);
        } catch (Exception e) {
            logger.error("#DefaultCommand throws exception.");
            throw new ControllerException(e);
        }
    }
}
