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
import java.io.IOException;

import static com.jwd.controller.util.Constants.*;

public class RegistrationCommand implements Command {

    private final UserService userService = ServiceFactory.getServiceFactory().getUserService();
    private static final Logger logger = LoggerFactory.getLogger(RegistrationCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            logger.info("#RegistrationCommand starts.");
            final String login = req.getParameter(LOGIN_);
            final String password1 = req.getParameter(PASSWORD1);
            final String password2 = req.getParameter(PASSWORD2);
            final String fName = req.getParameter(FIRST_NAME);
            final String lName = req.getParameter(LAST_NAME);
            final String phone = req.getParameter(PHONE);
            final String city = req.getParameter(CITY);
            final String street = req.getParameter(STREET);
            final String building = req.getParameter(BUILDING);
            final String apartment = req.getParameter(APARTMENT);

            // todo password secure

            if (!password1.equals(password2)) {
                logger.info("#RegistrationCommand passwords not match.");
                resp.sendRedirect("registerPage?message=passwordsNotMatch");
            } else {
                Address address = new Address(city, street, building, apartment);
                UserAccount userAccount = new UserAccount(login, password1, DEFAULT_, fName, lName, phone, address);
                int isRegister = userService.register(userAccount);
                switch (isRegister) {
                    case -1:
                        logger.info("#RegistrationCommand user exists.");
                        resp.sendRedirect("registerPage?message=userExists");
                        break;
                    case -2:
                        logger.info("#RegistrationCommand incomplete info.");
                        resp.sendRedirect("registerPage?message=IncompleteInfo");
                        break;
                    case 1:
                        req.setAttribute(MESSAGE, "User registered successfully.");
                        req.getRequestDispatcher(PATH_TO_JSP + Command.prepareUri(req) + JSP).forward(req, resp);
                        break;
                    default:
                        logger.error("#RegistrationCommand error.");
                        resp.sendRedirect("home?message=RegistrationError");
                        break;
                }
            }
        } catch (Exception e) {
            logger.error("#RegistrationCommand throws exception.");
            resp.sendRedirect("home?message=RegistrationError");
        }
    }

}


