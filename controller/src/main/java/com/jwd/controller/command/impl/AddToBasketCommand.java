package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;
import com.jwd.service.ServiceFactory;
import com.jwd.service.domain.UserAccount;
import com.jwd.service.serviceLogic.ProductService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.jwd.controller.util.Constants.*;
import static java.util.Objects.isNull;

public class AddToBasketCommand implements Command {

    private final ProductService productService = ServiceFactory.getServiceFactory().getProductService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException, IOException {
        try {
            HttpSession session;
            session = req.getSession(false);
            if (isNull(session)) {
                resp.sendRedirect("menu?message=BasketError");
            } else {
                // todo validation
                UserAccount user = (UserAccount) session.getAttribute(USER);
                long productId = Long.parseLong(req.getParameter(PROD_ID).replace("/", ""));
                boolean isAdded = productService.addToBasket(user.getId(), productId);
                if (!isAdded) {
                    req.setAttribute(MESSAGE, null);
                    resp.sendRedirect("menu?message=OrderExists");
                } else {
                    req.setAttribute(MESSAGE, "Product was added to your basket");
                    req.getRequestDispatcher(PATH_TO_JSP + Command.prepareUri(req) + JSP).forward(req, resp);
                }
            }
        } catch (Exception e) {
            resp.sendRedirect("menu?message=BasketError");
        }
    }
}