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
import static java.util.Objects.isNull;

public class SendOrderCommand implements Command {

    private final ProductService productService = ServiceFactory.getServiceFactory().getProductService();
    private static final Logger logger = LoggerFactory.getLogger(SendOrderCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException, IOException {
        try {
            logger.info("#SendOrderCommand starts.");
            HttpSession session;
            session = req.getSession(false);
            if (isNull(session)) {
                logger.info("#SendOrderCommand session is null.");
                resp.sendRedirect("basket?message=BasketError");
            } else {
                // todo validation
                UserAccount user = (UserAccount) session.getAttribute(USER);
                String comment = req.getParameter("comment");
                long id = productService.sendOrder(user.getId(), user.getLogin(), comment);
                session.setAttribute("paymentSum", session.getAttribute("sum"));
                if(id != -1L) session.setAttribute("lastorderid", id);
                else session.setAttribute("lastorderid", 0);
                productService.cleanBasket( user.getLogin());
                final double totalSum = productService.getSum(user.getLogin());
                String sum = String.format("%.1f",totalSum);
                final Page<OrderDetail> pageable = new Page<>();
                req.setAttribute(PAGEABLE, pageable);
                session.setAttribute(SUM, sum);
                req.setAttribute(MESSAGE, PAID_SUCCESSFULLY);
                req.getRequestDispatcher(PATH_TO_JSP + Command.prepareUri(req) + JSP).forward(req, resp);
            }
        } catch (Exception e) {
            logger.error("#SendOrderCommand throws exception.");
            resp.sendRedirect("basket?message=BasketError");
        }
    }
}
