package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.service.ServiceFactory;
import com.jwd.service.domain.Address;
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

public class UpdateUserCommand implements Command {

    private final UserService userService = ServiceFactory.getServiceFactory().getUserService();
    private static final Logger logger = LoggerFactory.getLogger(UpdateUserCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            logger.info("#UpdateUserCommand starts.");
            HttpSession session;
            session = req.getSession(false);
            if (isNull(session)) {
                logger.info("#UpdateUserCommand session is null.");
                resp.sendRedirect("main?message=SessionError");
            } else {
                UserAccount user = (UserAccount) session.getAttribute(USER);
                final String fName = req.getParameter(FIRST_NAME);
                final String lName = req.getParameter(LAST_NAME);
                final String phone = req.getParameter(PHONE);
                final String city = req.getParameter(CITY);
                final String street = req.getParameter(STREET);
                final String building = req.getParameter(BUILDING);
                final String apartment = req.getParameter(APARTMENT);
                Address updatedAddress = new Address(city,street,building,apartment);

                UserAccount updatedUser = new UserAccount(user.getId(),user.getLogin(), "password", "role", fName,lName,phone,updatedAddress);
                int isSuccess = userService.update(updatedUser);
                switch (isSuccess) {
                    case -1:
                        logger.info("#UpdateUserCommand user not exists.");
                        resp.sendRedirect("home?message=userNotExists");
                        break;
                    case -2:
                        logger.info("#UpdateUserCommand incomplete info.");
                        resp.sendRedirect("profile?message=IncompleteInfo");
                        break;
                    case 1:
                        req.setAttribute(MESSAGE, "Updated successfully.");
                        req.getRequestDispatcher(PATH_TO_JSP + Command.prepareUri(req) + JSP).forward(req, resp);
                        break;
                    default:
                        logger.error("#UpdateUserCommand error.");
                        resp.sendRedirect("main?message=UpdateUserError");
                        break;
                }
                session.setAttribute(USER, updatedUser);
                session.setAttribute(ADDRESS, updatedUser.getAddress());
            }
        } catch (Exception e) {
            logger.error("#UpdateUserCommand throws exception.");
            resp.sendRedirect("main?message=UpdateUserError");
        }
    }

}


