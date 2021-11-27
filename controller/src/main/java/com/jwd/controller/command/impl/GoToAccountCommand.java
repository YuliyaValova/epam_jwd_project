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

public class GoToAccountCommand implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException, IOException {
        try {
            HttpSession session;
            session = req.getSession(false);
            if (isNull(session)) {
                resp.sendRedirect("home?message=AccountError");
            } else {
                session.setAttribute(PAGE, ACCOUNT);
                req.getRequestDispatcher(PATH_TO_JSP + Command.prepareUri(req) + JSP).forward(req, resp);
            }
        } catch (Exception e) {
            resp.sendRedirect("home?message=AccountError");
        }
    }
}
