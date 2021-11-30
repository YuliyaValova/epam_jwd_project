package com.jwd.service.serviceLogic;

import com.jwd.dao.domain.Product;
import com.jwd.service.domain.Page;
import com.jwd.service.exception.ServiceException;

public interface ProductService {

    Page<Product> showProducts(Page<Product> productPageRequest) throws ServiceException;

    Page<Product> showBasket(Page<Product> pageRequest, long id) throws ServiceException;

    void cleanBasket(long id) throws ServiceException;

    double getSum(long id) throws ServiceException;

    void sendOrder(long id) throws ServiceException;
}
