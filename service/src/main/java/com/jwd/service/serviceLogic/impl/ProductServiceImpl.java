package com.jwd.service.serviceLogic.impl;

import com.jwd.dao.domain.Pageable;
import com.jwd.dao.domain.Product;
import com.jwd.dao.exception.DaoException;
import com.jwd.dao.repository.ProductDao;
import com.jwd.service.domain.Page;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.serviceLogic.ProductService;


public class ProductServiceImpl implements ProductService {
    private final ProductDao productDao;
    //private final ServiceValidator validator = new ServiceValidatorImpl();

    public ProductServiceImpl(ProductDao productDao){
        this.productDao = productDao;
    }

    @Override
    public Page<Product> showProducts(Page<Product> productPageRequest) throws ServiceException {
        // todo validation
        try {
            final Pageable<Product> daoProductPageable = convertToPageable(productPageRequest);
            final Pageable<Product> productRowsPageable = productDao.findPage(daoProductPageable);
            return convertToServicePage(productRowsPageable);
        } catch (final DaoException e) {
            throw new ServiceException(e);
        }
    }

    private Page<Product> convertToServicePage(Pageable<Product> productRowsPageable) {
        Page<Product> page = new Page<>();
        page.setPageNumber(productRowsPageable.getPageNumber());
        page.setLimit(productRowsPageable.getLimit());
        page.setTotalElements(productRowsPageable.getTotalElements());
        page.setElements(productRowsPageable.getElements());
        page.setSortBy(productRowsPageable.getSortBy());
        page.setDirection(productRowsPageable.getDirection());
        return page;
    }


    private Pageable<Product> convertToPageable(Page<Product> page) {
        final Pageable<Product> pageable = new Pageable<>();
        pageable.setPageNumber(page.getPageNumber());
        pageable.setLimit(page.getLimit());
        pageable.setTotalElements(page.getTotalElements());
        pageable.setSortBy(page.getSortBy());
        pageable.setDirection(page.getDirection());
        return pageable;
    }
}
