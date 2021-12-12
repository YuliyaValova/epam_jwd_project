package com.jwd.service.serviceLogic.impl;

import com.jwd.dao.domain.Order;
import com.jwd.dao.domain.Pageable;
import com.jwd.dao.domain.PageableOrder;
import com.jwd.dao.domain.Product;
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
    public Page<Product> showBasket(Page<Product> pageRequest, long id) throws ServiceException {
        try {
            Pageable<Product> daoBasketPageable = null;
            Pageable<Product> productsPageable = null;
            if (validator.validateId(id)) {
                daoBasketPageable = convertToPageable(pageRequest);
                productsPageable = pageDao.findBasketPage(daoBasketPageable, id);
            } else {
                logger.info("#showBasket invalid id");
            }
            return convertToServicePage(productsPageable);
        } catch (final DaoException e) {
            logger.error("#showBasket throws exception.");
            throw new ServiceException(e);
        }
    }

    @Override
    public void cleanBasket(long id) throws ServiceException {
        try {
            if (validator.validateId(id)) {
                orderDao.deleteOrdersByUserId(id);
            } else {
                logger.info("#cleanBasket invalid id");
            }
        } catch (final DaoException e) {
            logger.error("#cleanBasket throws exception.");
            throw new ServiceException(e);
        }
    }

    @Override
    public double getSum(long id) throws ServiceException {
        try {
            double sum = 0;
            if (validator.validateId(id)) {
                sum = basketDao.getSum(id);
            } else {
                logger.info("#getSum invalid id.");
            }
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
    public void deleteFromBasket(long id, long productId) throws ServiceException {
        try {
            if (validator.validateId(id) && validator.validateId(productId)) {
                basketDao.deleteFromBasket(id, productId);
            } else {
                logger.info("#deleteFromBasket invalid id.");
            }
        } catch (final DaoException e) {
            logger.error("#deleteFromBasket throws exception.");
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean addToBasket(long id, long productId) throws ServiceException {
        try {
            Boolean isAdded = null;
            if (validator.validateId(id) && validator.validateId(productId)) {
                isAdded = basketDao.addToBasket(id, productId);
            } else {
                logger.info("#addToBasket tinvalid id.");
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

    @Override
    public PageOrder<Order> showPaidOrders(PageOrder<Order> pageRequest) throws ServiceException {
        // todo validation
        try {
            final PageableOrder<Order> daoOrderPageable = convertToPageable(pageRequest);
            final PageableOrder<Order> ordersPageable = pageDao.findPaidOrderPage(daoOrderPageable);
            return convertToServicePage(ordersPageable);
        } catch (final DaoException e) {
            logger.error("#showPaidOrders throws exception.");
            throw new ServiceException(e);
        }
    }

    @Override
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
    }

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
