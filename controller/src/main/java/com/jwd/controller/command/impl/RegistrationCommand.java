package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;
import com.jwd.service.ServiceFactory;
import com.jwd.service.domain.Address;
import com.jwd.service.domain.UserAccount;
import com.jwd.service.serviceLogic.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationCommand implements Command {

    private final UserService userService = ServiceFactory.getServiceFactory().getUserService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException {
        try {

            final String login = req.getParameter("login");
            final String password1 = req.getParameter("password1");
            final String password2 = req.getParameter("password2");
            final String fName = req.getParameter("fName");
            final String lName = req.getParameter("lName");
            final String phone = req.getParameter("phone");
            final String city = req.getParameter("city");
            final String street = req.getParameter("street");
            final String building = req.getParameter("building");
            final String apartment = req.getParameter("apartment");

            //todo validation
            // todo password secure

            if (!password1.equals(password2)) throw new Exception("Passwords are not the same.");

            Address address = new Address(city, street, building, apartment);
            UserAccount userAccount = new UserAccount(login, password1, "default", fName, lName, phone, address);
            userService.register(userAccount);

            req.setAttribute("message", "User registered successfully.");
            req.getRequestDispatcher(Command.prepareUri(req) + ".jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ControllerException(e);
        }
    }

}


