package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;
import com.jwd.dao.domain.Product;
import com.jwd.service.ServiceFactory;
import com.jwd.service.domain.Page;
import com.jwd.service.serviceLogic.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.jwd.controller.util.Constants.*;
import static com.mysql.cj.util.StringUtils.isNullOrEmpty;

public class SearchCommand implements Command {
    private final ProductService productService = ServiceFactory.getServiceFactory().getProductService();
    private static final Logger logger = LoggerFactory.getLogger(ShowProductsCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException, IOException {
        try {
            HttpSession session;
            session = req.getSession(false);
            Page<Product> pageable = null;

            logger.info("#ShowProductsCommand starts.");
            String currentPageParam = req.getParameter(CURRENT_PAGE);
            if (isNullOrEmpty(currentPageParam)) {
                currentPageParam = "1";
            }
            String currentLimitParam = req.getParameter(PAGE_LIMIT);
            if (isNullOrEmpty(currentLimitParam)) {
                currentLimitParam = "5";
            }
            String sort_field = req.getParameter("sort_type");
            if (isNullOrEmpty(sort_field)) {
                sort_field = (String) session.getAttribute("sortBy");
            } else {
                session.setAttribute("sortBy", sort_field);
            }
            String direction = req.getParameter("sort_direction");
            if (isNullOrEmpty(direction)) {
                direction = (String) session.getAttribute("direct");
            } else {
                session.setAttribute("direct", direction);
            }
            String filter = req.getParameter("type");
            if (isNullOrEmpty(filter)) {
                filter = (String) session.getAttribute("type");
            } else {
                session.setAttribute("type", filter);
            }
            int currentPage = Integer.parseInt(currentPageParam);
            int pageLimit = Integer.parseInt(currentLimitParam);
            final Page<Product> pageRequest = new Page<>();
            pageRequest.setSortBy(sort_field);
            pageRequest.setDirection(direction);
            pageRequest.setFilter(filter);
            pageRequest.setPageNumber(currentPage);
            pageRequest.setLimit(pageLimit);
            String searchRequest = req.getParameter("search");
            if (isNullOrEmpty(searchRequest)) {
                pageable = productService.showProducts(pageRequest);
            } else {
                pageable = productService.showProducts(pageRequest, searchRequest);
            }
            req.setAttribute(PAGEABLE, pageable);
            req.setAttribute(PAGE, SHOW);
            req.getRequestDispatcher(PATH_TO_JSP + Command.prepareUri(req) + JSP).forward(req, resp);
        } catch (Exception e) {
            logger.error("#ShowProductsCommand throws exception.");
            resp.sendRedirect("menu?message=ShowProductsError");
        }
    }
}
