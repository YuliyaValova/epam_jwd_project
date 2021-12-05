package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;
import com.jwd.service.ServiceFactory;
import com.jwd.service.serviceLogic.ProductService;
import com.jwd.service.serviceLogic.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.jwd.controller.util.Constants.*;
import static java.util.Objects.isNull;

public class ChangeOrderStatusCommand implements Command {

    private final ProductService productService = ServiceFactory.getServiceFactory().getProductService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException, IOException {
        try {
            HttpSession session;
            session = req.getSession(false);
            if (isNull(session)) {
                resp.sendRedirect("main?message=SessionError");
            } else {
                //todo trim
                long orderId = Long.parseLong(req.getParameter(ORDER_ID).replace("/", ""));
                String status = req.getParameter(STATUS);
                boolean isSuccess = productService.changeStatus(orderId, status);
                if (isSuccess) {
                    req.setAttribute(MESSAGE, "Status was changed");
                    req.getRequestDispatcher(PATH_TO_JSP + Command.prepareUri(req) + JSP).forward(req, resp);
                } else {
                    resp.sendRedirect("main?message=InvalidStatus");
                }

            }

        } catch (Exception e) {
            resp.sendRedirect("main?message=ChangeProductStatusError");
        }
    }
}
