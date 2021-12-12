package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;
import com.jwd.service.ServiceFactory;
import com.jwd.service.domain.Address;
import com.jwd.service.domain.UserAccount;
import com.jwd.service.serviceLogic.ProductService;
import com.jwd.service.serviceLogic.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.jwd.controller.util.Constants.*;
import static java.util.Objects.isNull;

public class GetUserByIdCommand implements Command {

    private final UserService userService = ServiceFactory.getServiceFactory().getUserService();
    private static final Logger logger = LoggerFactory.getLogger(GetUserByIdCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException, IOException {
        try {
            logger.info("#GetUserByIdCommand starts.");
            HttpSession session;
            session = req.getSession(false);
            if (isNull(session)) {
                logger.info("#GetUserByIdCommand session is null.");
                resp.sendRedirect("main?message=SessionError");
            } else {
                // todo validation
                long userId = Long.parseLong(req.getParameter(ID).replace("/", ""));
                UserAccount account = userService.findUser(userId);
                if (account.getId() == 0L) {
                    logger.info("#GetUserByIdCommand user not exists.");
                    resp.sendRedirect("main?message=UserNotExists");
                } else {
                    Address address = account.getAddress();
                    session.setAttribute(FIND_USER, account);
                    session.setAttribute(FIND_ADDRESS, address);
                    session.setAttribute(PAGE, FIND_USER);
                    req.getRequestDispatcher(PATH_TO_JSP + Command.prepareUri(req) + JSP).forward(req, resp);
                }
            }
        } catch (Exception e) {
            logger.error("#GetUserByIdCommand throws exception.");
            resp.sendRedirect("main?message=FindUserError");
        }
    }
}
