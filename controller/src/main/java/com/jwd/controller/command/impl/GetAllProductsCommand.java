package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;
import com.jwd.dao.domain.Order;
import com.jwd.dao.domain.Product;
import com.jwd.service.ServiceFactory;
import com.jwd.service.domain.Page;
import com.jwd.service.domain.PageOrder;
import com.jwd.service.serviceLogic.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.jwd.controller.util.Constants.*;
import static com.mysql.cj.util.StringUtils.isNullOrEmpty;
import static java.util.Objects.isNull;

public class GetAllProductsCommand implements Command {

    private final ProductService productService = ServiceFactory.getServiceFactory().getProductService();
    private static final Logger logger = LoggerFactory.getLogger(GetAllProductsCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException, IOException {
        try {
            logger.info("#GetAllProductsCommand starts.");
            String currentPageParam = req.getParameter(CURRENT_PAGE);
            if (isNullOrEmpty(currentPageParam)) {
                currentPageParam = "1";
            }
            String currentLimitParam = req.getParameter(PAGE_LIMIT);
            if (isNullOrEmpty(currentLimitParam)) {
                currentLimitParam = "5";
            }
            int currentPage = Integer.parseInt(currentPageParam);
            int pageLimit = Integer.parseInt(currentLimitParam);
            final Page<Product> pageRequest = new Page<>();
            pageRequest.setPageNumber(currentPage);
            pageRequest.setLimit(pageLimit);
            HttpSession session;
            session = req.getSession(false);
            if (isNull(session)) {
                logger.info("#GetAllProductsCommand session is null.");
                resp.sendRedirect("main?message=SessionError");
            } else {
                // todo validation
                final Page<Product> pageableProduct = productService.showAllProducts(pageRequest);
                req.setAttribute(PAGEABLE3, pageableProduct);
                req.setAttribute(PAGE, SHOW_PROD);
                req.getRequestDispatcher(PATH_TO_JSP + Command.prepareUri(req) + JSP).forward(req, resp);
            }
        } catch (Exception e) {
            logger.error("#GetAllProductsCommand throws exception.");
            resp.sendRedirect("main?message=GetAllOrdersError");
        }
    }
}
