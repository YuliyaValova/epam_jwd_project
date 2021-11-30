package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;
import com.jwd.service.ServiceFactory;
import com.jwd.service.domain.UserAccount;
import com.jwd.service.serviceLogic.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.jwd.controller.util.Constants.*;
import static java.util.Objects.isNull;

public class ChangePasswordCommand implements Command {
    private final UserService userService = ServiceFactory.getServiceFactory().getUserService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException, IOException {

        try {
            HttpSession session;
            session = req.getSession(false);
            if (isNull(session)) {
                resp.sendRedirect("home?message=SessionError");
            } else {
                UserAccount user = (UserAccount) session.getAttribute(USER);
                String oldPassword = req.getParameter("oldPassword");
                String newPassword = req.getParameter("newPassword");
                if (userService.changePassword(user.getId(), oldPassword, newPassword)) {
                    req.getSession().invalidate();
                    req.setAttribute(MESSAGE, "Password is changed");
                    req.getRequestDispatcher(PATH_TO_JSP + Command.prepareUri(req) + JSP).forward(req, resp);
                } else {
                    req.setAttribute(MESSAGE, null);
                    resp.sendRedirect("profile?message=IncorrectOldPassword");
                }
            }
        } catch (Exception e) {
            resp.sendRedirect("main?message=AccountError");
        }
    }
}

