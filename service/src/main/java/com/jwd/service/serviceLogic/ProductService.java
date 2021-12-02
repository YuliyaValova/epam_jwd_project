package com.jwd.service.serviceLogic;

import com.jwd.dao.domain.Order;
import com.jwd.dao.domain.Product;
import com.jwd.service.domain.Page;
import com.jwd.service.domain.PageOrder;
import com.jwd.service.exception.ServiceException;

public interface ProductService {

    Page<Product> showProducts(Page<Product> productPageRequest) throws ServiceException;

    Page<Product> showBasket(Page<Product> pageRequest, long id) throws ServiceException;

    void cleanBasket(long id) throws ServiceException;

    double getSum(long id) throws ServiceException;

    void sendOrder(long id) throws ServiceException;

    void deleteFromBasket(long id, long productId) throws ServiceException;

    boolean addToBasket(long id, long productId) throws ServiceException;

    int addProductToMenu(Product product) throws ServiceException;

    double convertStringToDouble(String parameter);

    PageOrder<Order> showPaidOrders(PageOrder<Order> pageRequest) throws ServiceException;

    PageOrder<Order> showAllOrders(PageOrder<Order> pageRequest) throws ServiceException;

    void deleteFromMenu(long productId) throws ServiceException;

    Product findProduct(long productId) throws ServiceException;
}
