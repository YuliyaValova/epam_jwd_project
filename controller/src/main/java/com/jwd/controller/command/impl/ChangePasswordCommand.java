package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;
import com.jwd.service.ServiceFactory;
import com.jwd.service.domain.UserAccount;
import com.jwd.service.serviceLogic.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.jwd.controller.util.Constants.*;
import static java.util.Objects.isNull;

public class ChangePasswordCommand implements Command {
    private final UserService userService = ServiceFactory.getServiceFactory().getUserService();
    private static final Logger logger = LoggerFactory.getLogger(ChangePasswordCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException, IOException {
        try {
            logger.info("#ChangePasswordCommand starts.");
            HttpSession session;
            session = req.getSession(false);
            if (isNull(session)) {
                logger.info("#ChangePasswordCommand session is null.");
                resp.sendRedirect("home?message=SessionError");
            } else {
                UserAccount user = (UserAccount) session.getAttribute(USER);
                String oldPassword = req.getParameter(OLD_PASSWORD);
                String newPassword = req.getParameter(NEW_PASSWORD);
                if (userService.changePassword(user.getId(), oldPassword, newPassword)) {
                    req.getSession().invalidate();
                    req.setAttribute(MESSAGE, PASSWORD_CHANGED);
                    req.getRequestDispatcher(PATH_TO_JSP + Command.prepareUri(req) + JSP).forward(req, resp);
                } else {
                    req.setAttribute(MESSAGE, null);
                    logger.info("#ChangePasswordCommand incorrect old password.");
                    resp.sendRedirect("profile?message=IncorrectOldPassword");
                }
            }
        } catch (Exception e) {
            logger.error("#ChangePasswordCommand throws exception.");
            resp.sendRedirect("main?message=AccountError");
        }
    }
}

