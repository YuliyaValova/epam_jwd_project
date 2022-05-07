package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;
import com.jwd.dao.domain.OrderDetail;
import com.jwd.dao.domain.Product;
import com.jwd.service.ServiceFactory;
import com.jwd.service.domain.Page;
import com.jwd.service.domain.UserAccount;
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

public class ShowBasketCommand implements Command {

    private final ProductService productService = ServiceFactory.getServiceFactory().getProductService();
    private static final Logger logger = LoggerFactory.getLogger(ShowBasketCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException, IOException {
        try {
            logger.info("#ShowBasketCommand starts.");
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
            final Page<OrderDetail> pageRequest = new Page<>();
            pageRequest.setPageNumber(currentPage);
            pageRequest.setLimit(pageLimit);
            HttpSession session;
            session = req.getSession(false);
            if (isNull(session)) {
                logger.info("#ShowBasketCommand session is null.");
                resp.sendRedirect("basket?message=BasketError");
            } else {
                // todo validation
                UserAccount user = (UserAccount) session.getAttribute(USER);

                final Page<OrderDetail> pageable = productService.showBasket(pageRequest, user.getLogin());
                final double totalSum = productService.getSum(user.getLogin());
                req.setAttribute(PAGEABLE, pageable);
                String sum = String.format("%.1f",totalSum);
                session.setAttribute(SUM, sum);
                req.setAttribute(PAGE, SHOW);
                req.getRequestDispatcher(PATH_TO_JSP + Command.prepareUri(req) + JSP).forward(req, resp);
            }
        } catch (Exception e) {
            logger.error("#ShowBasketCommand throws exception.");
            resp.sendRedirect("basket?message=ShowBasketError");
        }
    }
}
