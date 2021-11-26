package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogOutCommand implements Command {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException {

        try {
            req.getSession().invalidate();
            resp.sendRedirect("home.jsp");
        } catch (Exception e) {
            throw new ControllerException(e.getMessage());
        }
    }
}

