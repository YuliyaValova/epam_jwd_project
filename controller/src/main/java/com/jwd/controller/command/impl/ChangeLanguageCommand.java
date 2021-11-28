package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.jwd.controller.util.Constants.JSP;
import static com.jwd.controller.util.Constants.PATH_TO_JSP;

public class ChangeLanguageCommand implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException, IOException {
        try {
            req.getSession(true).setAttribute("locale", req.getParameter("locale"));
            req.getRequestDispatcher(PATH_TO_JSP + Command.prepareUri(req) + JSP).forward(req,resp);
        } catch (IOException | ServletException e) {
            throw new ControllerException(e);
        }
    }
}
