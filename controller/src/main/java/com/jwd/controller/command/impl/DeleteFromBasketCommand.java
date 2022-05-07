package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;
import com.jwd.dao.domain.OrderDetail;
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

public class DeleteFromBasketCommand implements Command {

    private final ProductService productService = ServiceFactory.getServiceFactory().getProductService();
    private static final Logger logger = LoggerFactory.getLogger(DeleteFromBasketCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException, IOException {
        try {
            logger.info("#DeleteFromBasketCommand starts.");
            HttpSession session;
            session = req.getSession(false);
            if (isNull(session)) {
                logger.info("#DeleteFromBasketCommand session is null.");
                resp.sendRedirect("basket?message=BasketError");
            } else {
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
                // todo validation
                UserAccount user = (UserAccount) session.getAttribute(USER);
                long productId = Long.parseLong(req.getParameter(PROD_ID).replace("/",""));
                productService.deleteFromBasket(user.getLogin(),productId);
                final Page<OrderDetail> pageable = productService.showBasket(pageRequest, user.getLogin());
                req.setAttribute(PAGEABLE, pageable);
                req.setAttribute(PAGE, SHOW);
                req.getRequestDispatcher(PATH_TO_JSP + Command.prepareUri(req) + JSP).forward(req, resp);
            }
        } catch (Exception e) {
            logger.error("#DeleteFromBasketCommand throws exception.");
            resp.sendRedirect("basket?message=BasketError");
        }
    }
}
