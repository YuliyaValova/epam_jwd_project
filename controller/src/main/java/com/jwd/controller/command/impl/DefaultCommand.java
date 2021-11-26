package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class DefaultCommand implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException {
        try {
            resp.sendRedirect( Command.prepareUri(req)+".jsp");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ControllerException(e.getMessage());
        }
    }
}
