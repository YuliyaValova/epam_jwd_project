package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.jwd.controller.util.Constants.*;
import static com.jwd.controller.util.Constants.JSP;
import static java.util.Objects.isNull;

public class GoToProfileCommand implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException, IOException {
        try {
            HttpSession session;
            session = req.getSession(false);
            if (isNull(session)) {
                resp.sendRedirect("main?message=AccountError");
            } else {
                session.setAttribute(PAGE, PROFILE);
                req.getRequestDispatcher(PATH_TO_JSP+ Command.prepareUri(req) + JSP).forward(req, resp);
            }
        } catch (Exception e) {
            resp.sendRedirect("main?message=AccountError");
        }
    }
}
