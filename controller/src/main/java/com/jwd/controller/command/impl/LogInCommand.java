package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;
import com.jwd.service.ServiceFactory;
import com.jwd.service.domain.UserAccount;
import com.jwd.service.serviceLogic.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static java.util.Objects.nonNull;

public class LogInCommand implements Command {

    private final UserService userService = ServiceFactory.getServiceFactory().getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {
        try {
            //todo security
            final String login = request.getParameter("login");
            final String password = request.getParameter("password");

            // todo validation
            final UserAccount user = new UserAccount(login, password);
            UserAccount loginatedUser = userService.login(user);

            HttpSession session;
            session = request.getSession(false);
            if (nonNull(session)) {
                session.invalidate();
            }
            session = request.getSession();
            session.setAttribute("role", loginatedUser.getRole());
            response.sendRedirect("main.jsp");

        } catch (Exception e) {
            throw new ControllerException(e);
        }
    }

}
