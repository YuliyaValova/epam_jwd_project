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

public class AddToBasketCommand implements Command {

    private final ProductService productService = ServiceFactory.getServiceFactory().getProductService();
    private static final Logger logger = LoggerFactory.getLogger(AddToBasketCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException, IOException {
        try {
            logger.info("#AddToBasketCommand starts.");
            HttpSession session;
            session = req.getSession(false);
            if (isNull(session)) {
                logger.info("#AddToBasketCommand session is null.");
                resp.sendRedirect("menu?message=BasketError");
            } else {
                // todo validation
                UserAccount user = (UserAccount) session.getAttribute(USER);
                long productId = Long.parseLong(req.getParameter(PROD_ID).replace("/", ""));
                boolean isAdded = productService.addToBasket(user.getId(), productId);
                if (!isAdded) {
                    req.setAttribute(MESSAGE, null);
                    logger.info("#AddToBasketCommand order exists.");
                    resp.sendRedirect("menu?message=OrderExists");
                } else {
                    req.setAttribute(MESSAGE, "Product was added to your basket");
                    req.getRequestDispatcher(PATH_TO_JSP + Command.prepareUri(req) + JSP).forward(req, resp);
                }
            }
        } catch (Exception e) {
            logger.error("#AddToBasketCommand throws exception.");
            resp.sendRedirect("menu?message=BasketError");
        }
    }
}
