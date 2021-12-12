package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.jwd.controller.util.Constants.*;

public class ChangeLanguageCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(ChangeLanguageCommand.class);
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException, IOException {
        try {
            logger.info("#ChangeLanguageCommand starts.");
            req.getSession(true).setAttribute(LOCAL, req.getParameter(LOCAL));
            req.getRequestDispatcher(PATH_TO_JSP + Command.prepareUri(req) + JSP).forward(req,resp);
        } catch (IOException | ServletException e) {
            logger.error("#ChangeLanguageCommand throws exception.");
            throw new ControllerException(e);
        }
    }
}
