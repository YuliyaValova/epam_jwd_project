package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;
import com.jwd.service.ServiceFactory;
import com.jwd.service.domain.UserAccount;
import com.jwd.service.serviceLogic.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.jwd.controller.util.Constants.*;
import static java.util.Objects.isNull;

public class CleanBasketCommand implements Command {

    private final ProductService productService = ServiceFactory.getServiceFactory().getProductService();
    private static final Logger logger = LoggerFactory.getLogger(CleanBasketCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException, IOException {
        try {
            logger.info("#CleanBasketCommand starts.");
            HttpSession session;
            session = req.getSession(false);
            if (isNull(session)) {
                logger.info("#CleanBasketCommand session is null.");
                resp.sendRedirect("basket?message=BasketError");
            } else {
                // todo validation
                UserAccount user = (UserAccount) session.getAttribute(USER);
                productService.cleanBasket(user.getLogin());
                final double totalSum = productService.getSum(user.getLogin());
                String sum = String.format("%.1f",totalSum);
                session.setAttribute(SUM, sum);
                req.getRequestDispatcher(PATH_TO_JSP + Command.prepareUri(req) + JSP).forward(req, resp);
            }
        } catch (Exception e) {
            logger.error("#CleanBasketCommand throws exception.");
            resp.sendRedirect("basket?message=BasketError");
        }
    }
}
