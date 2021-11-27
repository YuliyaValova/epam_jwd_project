package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.jwd.controller.util.Constants.*;


public class DefaultCommand implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException {
        try {
            req.getRequestDispatcher(PATH_TO_JSP + Command.prepareUri(req) + JSP).forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ControllerException(e.getMessage());
        }
    }
}
