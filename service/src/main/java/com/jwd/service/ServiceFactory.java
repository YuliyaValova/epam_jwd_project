package com.jwd.service;

import com.jwd.dao.DaoFactory;
import com.jwd.service.serviceLogic.ProductService;
import com.jwd.service.serviceLogic.UserService;
import com.jwd.service.serviceLogic.impl.ProductServiceImpl;
import com.jwd.service.serviceLogic.impl.UserServiceImpl;

public class ServiceFactory {

    private static final ServiceFactory serviceFactory = new ServiceFactory();
    private final DaoFactory daoFactory = DaoFactory.getFactory();
    private final UserService userService = new UserServiceImpl();
    private final ProductService productService = new ProductServiceImpl(daoFactory.getProductDao(),
                                                                         daoFactory.getBasketDao(),
                                                                         daoFactory.getOrderDao(),
                                                                         daoFactory.getPageDao());

    private ServiceFactory() {
    }

    public static ServiceFactory getServiceFactory() {
        return serviceFactory;
    }

    public UserService getUserService() {
        return userService;
    }

    public ProductService getProductService() {
        return productService;
    }
}
