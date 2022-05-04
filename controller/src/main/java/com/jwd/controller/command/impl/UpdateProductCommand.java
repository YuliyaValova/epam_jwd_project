package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.dao.domain.Product;
import com.jwd.service.ServiceFactory;
import com.jwd.service.serviceLogic.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.jwd.controller.util.Constants.*;

public class UpdateProductCommand implements Command {

    private final ProductService productService = ServiceFactory.getServiceFactory().getProductService();
    private static final Logger logger = LoggerFactory.getLogger(UpdateProductCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            logger.info("#UpdateProductCommand starts.");
            long productId = Long.parseLong(req.getParameter(PROD_ID).replace("/", ""));
            final String name = req.getParameter(NAME);
            final String type = req.getParameter(TYPE);
            final String description = req.getParameter(DESCRIPTION);
            final String status = req.getParameter(STATUS).replace("/", "");
            double price = productService.convertStringToDouble(req.getParameter(PRICE));
            boolean isAvailable = productService.convertStringToBoolean(status);
            String image = req.getParameter(IMAGE);

            if (price != -1) {
                Product product = new Product(productId, name, type, description, price, isAvailable, image);
                int isSuccess = productService.updateProduct(product);
                switch (isSuccess) {
                    case -1:
                        logger.info("#UpdateProductCommand product not exists.");
                        resp.sendRedirect("main?message=productNotExists");
                        break;
                    case -2:
                        logger.info("#UpdateProductCommand incomplete info.");
                        resp.sendRedirect("main?message=IncompleteInfo");
                        break;
                    case 1:
                        req.setAttribute(MESSAGE, UPDATED_SUCCESSFULLY);
                        req.getRequestDispatcher(PATH_TO_JSP + Command.prepareUri(req) + JSP).forward(req, resp);
                        break;
                    default:
                        logger.info("#UpdateProductCommand unexpected result.");
                        resp.sendRedirect("main?message=UpdateProductError");
                        break;
                }
            } else {
                logger.info("#UpdateProductCommand invalid price.");
                resp.sendRedirect("main?message=InvalidPriceError");
            }
        } catch (Exception e) {
            logger.error("#UpdateProductCommand throws exception.");
            resp.sendRedirect("main?message=UpdateProductError");
        }
    }

}


