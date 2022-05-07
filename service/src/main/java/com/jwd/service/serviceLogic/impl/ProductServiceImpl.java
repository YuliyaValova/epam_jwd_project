package com.jwd.service.serviceLogic.impl;

import com.jwd.dao.domain.*;
import com.jwd.dao.exception.DaoException;
import com.jwd.dao.repository.BasketDao;
import com.jwd.dao.repository.OrderDao;
import com.jwd.dao.repository.PageDao;
import com.jwd.dao.repository.ProductDao;
import com.jwd.service.domain.Page;
import com.jwd.service.domain.PageOrder;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.serviceLogic.ProductService;
import com.jwd.service.validator.ServiceValidator;
import com.jwd.service.validator.impl.ServiceValidatorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;


public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final ProductDao productDao;
    private final BasketDao basketDao;
    private final OrderDao orderDao;
    private final PageDao pageDao;
    private final ServiceValidator validator = new ServiceValidatorImpl();

    public ProductServiceImpl(ProductDao productDao, BasketDao basketDao, OrderDao orderDao, PageDao pageDao) {
        this.productDao = productDao;
        this.basketDao = basketDao;
        this.orderDao = orderDao;
        this.pageDao = pageDao;
    }

    @Override
    public Page<Product> showProducts(Page<Product> productPageRequest) throws ServiceException {
        // todo validation
        try {
            final Pageable<Product> daoProductPageable = convertToPageable(productPageRequest);
            final Pageable<Product> productsPageable = pageDao.findPage(daoProductPageable);
            return convertToServicePage(productsPageable);
        } catch (final DaoException e) {
            logger.error("#showProduct throws exception.");
            throw new ServiceException(e);
        }
    }

    @Override
    public Page<OrderDetail> showBasket(Page<OrderDetail> pageRequest, String login) throws ServiceException {
        try {
            Pageable<OrderDetail> daoBasketPageable = null;
            Pageable<OrderDetail> productsPageable = null;
                daoBasketPageable = convertOrderToPageable(pageRequest);
                productsPageable = pageDao.findBasketPage(daoBasketPageable, login);
            return convertOrderDetailToServicePage(productsPageable);
        } catch (final DaoException e) {
            logger.error("#showBasket throws exception.");
            throw new ServiceException(e);
        }
    }

   @Override
    public void cleanBasket(String login) throws ServiceException {
        try {
                orderDao.deleteOrdersByUserLogin(login);
        } catch (final DaoException e) {
            logger.error("#cleanBasket throws exception.");
            throw new ServiceException(e);
        }
    }

   @Override
    public double getSum(String login) throws ServiceException {
        try {
            double sum = 0;
                sum = basketDao.getSum(login);
            return sum;
        } catch (final DaoException e) {
            logger.error("#getSum throws exception.");
            throw new ServiceException(e);
        }
    }

    @Override
    public void sendOrder(long id) throws ServiceException {
        try {
            if (validator.validateId(id)) {
                orderDao.changeAllOrdersStatus(id, "Paid up", "Waiting for payment");
            } else {
                logger.info("#sendOrder invalid id.");
            }
        } catch (final DaoException e) {
            logger.error("#sendOrder throws exception.");
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteFromBasket(String login, long productId) throws ServiceException {
        try {
            if (validator.validateId(productId)) {
                basketDao.deleteFromBasket(login, productId);
            } else {
                logger.info("#deleteFromBasket invalid id.");
            }
        } catch (final DaoException e) {
            logger.error("#deleteFromBasket throws exception.");
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean addToBasket(long id, String login, double price, long productId, int count) throws ServiceException {
        try {
            Boolean isAdded = null;
            if (validator.validateId(id) && validator.validateId(productId)) {
                isAdded = basketDao.addToBasket(id, login, price, productId, count);
            } else {
                logger.info("#addToBasket invalid id.");
            }
            return isAdded;
        } catch (final DaoException e) {
            logger.error("#addToBasket throws exception.");
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
                logger.info("#addProductToMenu invalid product info.");
                isSuccessful = -2;
            }
            return isSuccessful;
        } catch (final DaoException e) {
            logger.error("#addProductToMenu throws exception.");
            throw new ServiceException(e);
        }
    }

    @Override
    public double convertStringToDouble(String parameter) {
        double price = -1;
        try {
            price = Double.parseDouble(parameter);
            if (price < 0) {
                logger.info("#convertStringToDouble invalid price.");
                price = -1;
            }
        } catch (Exception e) {
            logger.error("#convertStringToDouble throws exception.");
        }
        return price;
    }

   /* @Override
    public PageOrder<OrderDetail> showPaidOrders(PageOrder<OrderDetail> pageRequest) throws ServiceException {
        // todo validation
        try {
            final PageableOrder<OrderDetail> daoOrderPageable = convertOrderToPageable(pageRequest);
            final PageableOrder<OrderDetail> ordersPageable = pageDao.findPaidOrderPage(daoOrderPageable);
            return convertToServicePage(ordersPageable);
        } catch (final DaoException e) {
            logger.error("#showPaidOrders throws exception.");
            throw new ServiceException(e);
        }
    }*/

   /* @Override
    public PageOrder<Order> showAllOrders(PageOrder<Order> pageRequest) throws ServiceException {
        // todo validation
        try {
            final PageableOrder<Order> daoOrderPageable = convertToPageable(pageRequest);
            final PageableOrder<Order> ordersPageable = pageDao.findAllOrderPage(daoOrderPageable);
            return convertToServicePage(ordersPageable);
        } catch (final DaoException e) {
            logger.error("#showAllOrders throws exception.");
            throw new ServiceException(e);
        }
    }*/

    @Override
    public void deleteFromMenu(long id) throws ServiceException {
        try {
            if (validator.validateId(id)) {
                productDao.deleteProductById(id);
            } else {
                logger.info("#deleteFromMenu invalid id.");
            }
        } catch (final DaoException e) {
            logger.error("#deleteFromMenu throws exception.");
            throw new ServiceException(e);
        }
    }

    @Override
    public Product findProduct(long productId) throws ServiceException {
        try {
            Product product = null;
            if (validator.validateId(productId)) {
                product = new Product(productDao.getProductById(productId));
            } else {
                logger.info("#findProduct invalid id.");
            }
            return product;
        } catch (final DaoException e) {
            logger.error("#findProduct throws exception.");
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean changeStatus(long orderId, String newStatus) throws ServiceException {
        try {
            boolean isChanged = false;
            if (validator.validateId(orderId) && validator.validateStatus(newStatus)) {
                orderDao.changeOrderStatus(orderId, newStatus);
                isChanged = true;
            } else {
                logger.info("#changeStatus invalid info.");
            }
            return isChanged;
        } catch (final DaoException e) {
            logger.error("#changeStatus throws exception.");
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean convertStringToBoolean(String status) {
        boolean value = false;
        if (status.equals("true")) {
            value = true;
        }
        return value;
    }

    @Override
    public boolean changeProductStatus(long productId, String status) throws ServiceException {
        try {
            boolean isChanged = false;
            if (validator.validateId(productId) && validator.validateProductStatus(status)) {
                productDao.changeProductStatus(productId, status);
                isChanged = true;
            } else {
                logger.info("#changeProductStatus invalid info.");
            }
            return isChanged;
        } catch (final DaoException e) {
            logger.error("#changeProductStatus throws exception.");
            throw new ServiceException(e);
        }
    }

    @Override
    public Page<Product> showAllProducts(Page<Product> pageRequest) throws ServiceException {
        // todo validation
        try {
            final Pageable<Product> daoProductPageable = convertToPageable(pageRequest);
            final Pageable<Product> productsPageable = pageDao.findAllProductsPage(daoProductPageable);
            return convertToServicePage(productsPageable);
        } catch (final DaoException e) {
            logger.error("#showProduct throws exception.");
            throw new ServiceException(e);
        }
    }

    @Override
    public int updateProduct(Product product) throws ServiceException {
        try {
            int isSuccessful = -1;
            if (validator.validateProduct(product)) {
                long id = productDao.updateProduct(convertToDaoProduct(product));
                if (id != -1L) {
                    isSuccessful = 1;
                }
            } else {
                logger.info("#updateProduct invalid product info.");
                isSuccessful = -2;
            }
            return isSuccessful;
        } catch (final DaoException e) {
            logger.error("#updateProduct throws exception.");
            throw new ServiceException(e);
        }
    }

    @Override
    public ArrayList getTypes() throws ServiceException {
        try {
           return productDao.getProductTypes();
        } catch (Exception e){
            logger.error("#getTypes throws exception.");
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean writeToCSV(String path) throws ServiceException {
        try {
            boolean isSuccessful = false;
            long success = productDao.writeToCsv(path);
            if (success != -1L) {
                isSuccessful = true;
            } else {
                logger.info("#update invalid user info.");
            }
            return isSuccessful;
        } catch (final DaoException e) {
            logger.error("#update throws exception.");
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
        newProduct.setImage(product.getImage());
        return newProduct;
    }


    private Page<OrderDetail> convertOrderDetailToServicePage(Pageable<OrderDetail> orderRowsPageable) {
        Page<OrderDetail> page = new Page<>();
        page.setPageNumber(orderRowsPageable.getPageNumber());
        page.setLimit(orderRowsPageable.getLimit());
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


    private Pageable<OrderDetail> convertOrderToPageable(Page<OrderDetail> page) {
        final Pageable<OrderDetail> pageable = new Pageable<>();
        pageable.setPageNumber(page.getPageNumber());
        pageable.setLimit(page.getLimit());
        pageable.setTotalElements(page.getTotalElements());
        pageable.setSortBy(page.getSortBy());
        pageable.setDirection(page.getDirection());
        return pageable;
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
