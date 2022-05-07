package com.jwd.service.serviceLogic;

import com.jwd.dao.domain.Order;
import com.jwd.dao.domain.OrderDetail;
import com.jwd.dao.domain.Product;
import com.jwd.service.domain.Page;
import com.jwd.service.domain.PageOrder;
import com.jwd.service.exception.ServiceException;
import com.mysql.cj.x.protobuf.MysqlxCrud;

import java.util.ArrayList;

public interface ProductService {

    /**
     * Makes a page for showing available products in a menu
     * @param productPageRequest required pages params
     * @return product page
     * @throws ServiceException is a module exception
     */
    Page<Product> showProducts(Page<Product> productPageRequest) throws ServiceException;

    Page<OrderDetail> showBasket(Page<OrderDetail> pageRequest, String login) throws ServiceException;


    void cleanBasket(String login) throws ServiceException;

    double getSum(String login) throws ServiceException;

    /**
     * Changes the status of all items in the user's basket to "Paid up"
     * @param id identifies user
     * @throws ServiceException is a module exception
     */
    void sendOrder(long id, String login, String comment) throws ServiceException;

    void deleteFromBasket(String login, long productId) throws ServiceException;

    /**
     * Adds a product to user's basket
     * @param id identifies user
     * @param productId identifies product for adding
     * @return true if adding is successful, otherwise - false
     * @throws ServiceException is a module exception
     */
    boolean addToBasket(long id, String login, double price, long productId, int count) throws ServiceException;

    /**
     * Add a product to a menu
     * @param product for adding
     * @return -2 if product is invalid, -1 if such product already exists, 1 if adding is successful
     * @throws ServiceException is a module exception
     */
    int addProductToMenu(Product product) throws ServiceException;

    /**
     * Converts String param to double
     * @param parameter for converting
     * @return double value
     */
    double convertStringToDouble(String parameter);

    /**
     * Makes a page for showing paid orders to admin
     * @param pageRequest required page params
     * @return order page
     * @throws ServiceException is a module exception
     */
    //PageOrder<OrderDetail> showPaidOrders(PageOrder<OrderDetail> pageRequest) throws ServiceException;

    /**
     * Makes a page for showing all orders to admin (if ordered product wasn't deleted)
     * @param pageRequest required page params
     * @return order page
     * @throws ServiceException is a module exception
     */
    //PageOrder<Order> showAllOrders(PageOrder<Order> pageRequest) throws ServiceException;

    /**
     * Deletes a product from menu
     * @param productId identifies a product for deleting
     * @throws ServiceException is a module exception
     */
    void deleteFromMenu(long productId) throws ServiceException;

    /**
     * Finds a product by id
     * @param productId identifies a product for searching
     * @return product
     * @throws ServiceException is a module exception
     */
    Product findProduct(long productId) throws ServiceException;

    /**
     * Changes orders status to the specified
     * @param orderId identifies a order for updating
     * @param status new status
     * @return true if changing is successful, otherwise - false
     * @throws ServiceException is a module exception
     */
    boolean changeStatus(long orderId, String status) throws ServiceException;

    /**
     * Converts String param to boolean
     * @param status value for converting
     * @return boolean value
     */
    boolean convertStringToBoolean(String status);

    /**
     * Сhanges the status to the opposite
     * @param productId identifies a product for updating
     * @param status old product status
     * @return true if changing is successful, otherwise - false
     * @throws ServiceException is a module exception
     */
    boolean changeProductStatus(long productId, String status) throws ServiceException;

    /**
     * Makes a page for showing all products
     * @param pageRequest required pages params
     * @return product page
     * @throws ServiceException is a module exception
     */
    Page<Product> showAllProducts(Page<Product> pageRequest) throws ServiceException;

    /**
     * Updates product's info
     * @param product for updating
     * @return  -2 if product is invalid, -1 if such product not exists, 1 if updating is successful
     * @throws ServiceException is a module exception
     */
    int updateProduct(Product product) throws ServiceException;

    ArrayList getTypes() throws ServiceException;

    boolean writeToCSV(String path) throws ServiceException;

}
