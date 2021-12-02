package com.jwd.service.serviceLogic.impl;

import com.jwd.dao.domain.Order;
import com.jwd.dao.domain.Pageable;
import com.jwd.dao.domain.PageableOrder;
import com.jwd.dao.domain.Product;
import com.jwd.dao.exception.DaoException;
import com.jwd.dao.repository.ProductDao;
import com.jwd.service.domain.Page;
import com.jwd.service.domain.PageOrder;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.serviceLogic.ProductService;
import com.jwd.service.validator.ServiceValidator;
import com.jwd.service.validator.impl.ServiceValidatorImpl;

import java.sql.SQLException;


public class ProductServiceImpl implements ProductService {
    private final ProductDao productDao;
    private final ServiceValidator validator = new ServiceValidatorImpl();

    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public Page<Product> showProducts(Page<Product> productPageRequest) throws ServiceException {
        // todo validation
        try {
            final Pageable<Product> daoProductPageable = convertToPageable(productPageRequest);
            final Pageable<Product> productsPageable = productDao.findPage(daoProductPageable);
            return convertToServicePage(productsPageable);
        } catch (final DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Page<Product> showBasket(Page<Product> pageRequest, long id) throws ServiceException {
        try {
            validator.validateId(id);
            final Pageable<Product> daoBasketPageable = convertToPageable(pageRequest);
            final Pageable<Product> productsPageable = productDao.findBasketPage(daoBasketPageable, id);
            return convertToServicePage(productsPageable);
        } catch (final DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void cleanBasket(long id) throws ServiceException {
        try {
            productDao.deleteOrdersByUserId(id);
        } catch (final DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public double getSum(long id) throws ServiceException {
        try {
            validator.validateId(id);
            double sum = productDao.getSum(id);
            return sum;
        } catch (final DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void sendOrder(long id) throws ServiceException {
        try {
            validator.validateId(id);
            productDao.changeAllOrdersStatus(id, "Paid up", "Waiting for payment");
        } catch (final DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteFromBasket(long id, long productId) throws ServiceException {
        try {
            validator.validateId(id);
            validator.validateId(productId);
            productDao.deleteFromBasket(id, productId);
        } catch (final DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean addToBasket(long id, long productId) throws ServiceException {
        try {
            validator.validateId(id);
            validator.validateId(productId);
            return productDao.addToBasket(id, productId);

        } catch (final DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int addProductToMenu(Product product) throws ServiceException {
        try {
            int isSuccessful = -1;
            if (validator.validateProduct(product)) {
                long id = productDao.saveProduct(convertToDaoProduct(product));
                if (id != -1L) {
                    isSuccessful = 1;
                }
            } else {
                isSuccessful = -2;
            }
            return isSuccessful;
        } catch (final DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public double convertStringToDouble(String parameter) {
            double price = -1;
            try {
                price = Double.parseDouble(parameter);
                if (price < 0) {
                    price = -1;
                }
            } catch (Exception e){}
            return price;
    }

    @Override
    public PageOrder<Order> showPaidOrders(PageOrder<Order> pageRequest) throws ServiceException {
        // todo validation
        try {
            final PageableOrder<Order> daoProductPageable = convertToPageable(pageRequest);
            final PageableOrder<Order> productsPageable = productDao.findOrderPage(daoProductPageable);
            return convertToServicePage(productsPageable);
        } catch (final DaoException e) {
            throw new ServiceException(e);
        }
    }


    private com.jwd.dao.domain.Product convertToDaoProduct(Product product) {
        com.jwd.dao.domain.Product newProduct = new com.jwd.dao.domain.Product();
        newProduct.setId(product.getId());
        newProduct.setName(product.getName());
        newProduct.setDescription(product.getDescription());
        newProduct.setPrice(product.getPrice());
        newProduct.setType(product.getType());
        newProduct.setIsAvailable(product.getIsAvailable());
        return newProduct;
    }


    private PageOrder<Order> convertToServicePage(PageableOrder<Order> orderRowsPageable) {
        PageOrder<Order> page = new PageOrder<>();
        page.setPageNumber(orderRowsPageable.getPageNumber());
        page.setLimit(orderRowsPageable.getLimit());
        page.setStatus(orderRowsPageable.getStatus());
        page.setCustomerId(orderRowsPageable.getCustomerId());
        page.setTotalElements(orderRowsPageable.getTotalElements());
        page.setElements(orderRowsPageable.getElements());
        page.setSortBy(orderRowsPageable.getSortBy());
        page.setDirection(orderRowsPageable.getDirection());
        return page;
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

    private PageableOrder<Order> convertToPageable(PageOrder<Order> page) {
        final PageableOrder<Order> pageable = new PageableOrder<>();
        pageable.setPageNumber(page.getPageNumber());
        pageable.setLimit(page.getLimit());
        pageable.setStatus(page.getStatus());
        pageable.setCustomerId(page.getCustomerId());
        pageable.setTotalElements(page.getTotalElements());
        pageable.setSortBy(page.getSortBy());
        pageable.setDirection(page.getDirection());
        return pageable;
    }
}
