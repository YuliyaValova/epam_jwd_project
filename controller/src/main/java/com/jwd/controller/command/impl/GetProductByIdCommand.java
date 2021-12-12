package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;
import com.jwd.dao.domain.Product;
import com.jwd.service.ServiceFactory;
import com.jwd.service.serviceLogic.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.jwd.controller.util.Constants.*;
import static java.util.Objects.isNull;

public class GetProductByIdCommand implements Command {

    private final ProductService productService = ServiceFactory.getServiceFactory().getProductService();
    private static final Logger logger = LoggerFactory.getLogger(GetProductByIdCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException, IOException {
        try {
            logger.info("#GetProductByIdCommand starts.");
            HttpSession session;
            session = req.getSession(false);
            if (isNull(session)) {
                logger.info("#GetProductByIdCommand session is null.");
                resp.sendRedirect("main?message=SessionError");
            } else {
                // todo validation
                long productId = Long.parseLong(req.getParameter(PROD_ID).replace("/", ""));
                Product product = productService.findProduct(productId);
                if (product.getId() == -1L) {
                    logger.info("#GetProductByIdCommand product not exists.");
                    resp.sendRedirect("main?message=ProductNotExists");
                } else {
                    session.setAttribute(PRODUCT, product);
                    session.setAttribute(PAGE, FIND_PRODUCT);
                    req.getRequestDispatcher(PATH_TO_JSP + Command.prepareUri(req) + JSP).forward(req, resp);
                }
            }
        } catch (Exception e) {
            logger.error("#GetProductByIdCommand throws exception.");
            resp.sendRedirect("main?message=FindProductError");
        }
    }
}
