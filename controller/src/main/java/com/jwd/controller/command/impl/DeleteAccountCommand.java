package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;
import com.jwd.service.ServiceFactory;
import com.jwd.service.domain.UserAccount;
import com.jwd.service.serviceLogic.ProductService;
import com.jwd.service.serviceLogic.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static com.jwd.controller.util.Constants.*;
import static com.jwd.controller.util.Constants.MAIN;
import static java.util.Objects.isNull;

public class DeleteAccountCommand implements Command {
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
                userService.deleteAccount(user.getId());
                req.getSession().invalidate();
                req.setAttribute(MESSAGE, "Account is deleted");
                req.getRequestDispatcher(PATH_TO_JSP + Command.prepareUri(req) + JSP).forward(req, resp);
            }
        } catch (Exception e) {
            resp.sendRedirect("main?message=AccountError");
        }
    }
}

