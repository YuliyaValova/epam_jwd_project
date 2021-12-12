package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
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
import static java.util.Objects.nonNull;

public class LogInCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(LogInCommand.class);
    private final UserService userService = ServiceFactory.getServiceFactory().getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            //todo security
            logger.info("#LogInCommand starts.");
            final String login = request.getParameter(LOGIN_);
            final String password = request.getParameter(PASSWORD);

            final UserAccount user = new UserAccount(login, password);
            UserAccount loginatedUser = userService.login(user);

            if (isNull(loginatedUser)) {
                logger.info("#LogInCommand incompleteInfo.");
                response.sendRedirect("loginPage?message=IncompleteInfo");
            } else {
                if (loginatedUser.getId() == -1L) {
                    logger.info("#LogInCommand no such user.");
                    response.sendRedirect("loginPage?message=noSuchUser");
                } else {
                    HttpSession session;
                    session = request.getSession(false);
                    if (nonNull(session)) {
                        logger.info("#LogInCommand session is null.");
                        session.invalidate();
                    }
                    session = request.getSession();
                    session.setAttribute(USER, loginatedUser);
                    session.setAttribute(ADDRESS, loginatedUser.getAddress());
                    session.setAttribute(ROLE, loginatedUser.getRole());
                    request.getRequestDispatcher(PATH_TO_JSP + Command.prepareUri(request) + JSP).forward(request, response);
                }
            }
        } catch (Exception e) {
            logger.error("#LogInCommand throws exception.");
            response.sendRedirect("home?message=LoginationError");
        }
    }

}
