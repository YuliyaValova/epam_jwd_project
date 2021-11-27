package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.service.ServiceFactory;
import com.jwd.service.domain.UserAccount;
import com.jwd.service.serviceLogic.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static com.jwd.controller.util.Constants.*;
import static java.util.Objects.nonNull;

public class LogInCommand implements Command {

    private final UserService userService = ServiceFactory.getServiceFactory().getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            //todo security
            final String login = request.getParameter(LOGIN_);
            final String password = request.getParameter(PASSWORD);

            // todo validation
            final UserAccount user = new UserAccount(login, password);
            UserAccount loginatedUser = userService.login(user);

            if (loginatedUser.getId() == 0L) {
                response.sendRedirect("home?message=noSuchUser");
            } else {
                HttpSession session;
                session = request.getSession(false);
                if (nonNull(session)) {
                    session.invalidate();
                }
                session = request.getSession();
                session.setAttribute(ROLE, loginatedUser.getRole());
                request.getRequestDispatcher(PATH_TO_JSP + Command.prepareUri(request) + JSP).forward(request, response);
            }

        } catch (Exception e) {
            response.sendRedirect("home?message=LoginationError");
        }
    }

}
