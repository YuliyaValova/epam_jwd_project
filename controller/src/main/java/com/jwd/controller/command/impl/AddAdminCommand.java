package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;
import com.jwd.service.ServiceFactory;
import com.jwd.service.serviceLogic.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.jwd.controller.util.Constants.*;
import static java.util.Objects.isNull;

public class AddAdminCommand implements Command {

    private final UserService userService = ServiceFactory.getServiceFactory().getUserService();
    private static final Logger logger = LoggerFactory.getLogger(AddAdminCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException, IOException {
        try {
            logger.info("#AddAdminCommand starts.");
            HttpSession session;
            session = req.getSession(false);
            if (isNull(session)) {
                logger.info("#AddAdminCommand session is null.");
                resp.sendRedirect("main?message=SessionError");
            } else {
                // todo validation
                long userId = Long.parseLong(req.getParameter(ADMIN_ID).replace("/", ""));
                if (userService.addAdmin(userId)) {
                    req.setAttribute(MESSAGE, "Admin was added");
                    req.getRequestDispatcher(PATH_TO_JSP + Command.prepareUri(req) + JSP).forward(req, resp);
                } else {
                    logger.info("#AddAdminCommand user not exists.");
                    resp.sendRedirect("main?message=UserNotExists");
                }
            }

        } catch (Exception e) {
            logger.error("#AddAdminCommand throws exception.");
            resp.sendRedirect("main?message=AddAdminError");
        }
    }
}
