package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.jwd.controller.util.Constants.JSP;
import static com.jwd.controller.util.Constants.PATH_TO_JSP;

public class LogOutCommand implements Command {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException {

        try {
            req.getSession().invalidate();
            req.getRequestDispatcher(PATH_TO_JSP + Command.prepareUri(req) + JSP).forward(req, resp);
        } catch (Exception e) {
            throw new ControllerException(e.getMessage());
        }
    }
}

