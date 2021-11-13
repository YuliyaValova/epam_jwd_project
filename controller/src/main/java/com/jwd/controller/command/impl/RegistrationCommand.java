package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;
import com.jwd.service.domain.Address;
import com.jwd.service.domain.User;
import com.jwd.service.domain.UserAccount;
import com.jwd.service.serviceLogic.UserService;
import com.jwd.service.serviceLogic.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationCommand implements Command {

    private final UserService userService = new UserServiceImpl();

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

            Address address = new Address(city,street,building,apartment);
            User user = new User(fName,lName,phone,address);
            UserAccount userAccount = new UserAccount(login, password1, "default", user);
            userService.register(userAccount);

            req.setAttribute("message", "User registered successfully.");
            req.getRequestDispatcher(Command.prepareUri(req) + ".jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ControllerException(e);
        }
    }

}


