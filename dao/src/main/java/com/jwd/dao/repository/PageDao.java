package com.jwd.dao.repository;

import com.jwd.dao.domain.*;
import com.jwd.dao.exception.DaoException;

public interface PageDao {

    /**
     * Makes a page for showing available products
     * @param daoProductPageable page params object
     * @return product page
     * @throws DaoException is a module exception
     */
    Pageable<Product> findPage(Pageable<Product> daoProductPageable) throws DaoException;

    Pageable<OrderDetail> findOrderPage(Pageable<OrderDetail> daoProductPageable) throws DaoException;


    Pageable<OrderDetail> findBasketPage(Pageable<OrderDetail> daoBasketPageable, String login) throws DaoException;

    /**
     * Makes a page for showing paid orders to admin
     * @param daoOrderPageable page params object
     * @return paid orders page
     * @throws DaoException is a module exception
     */
   // PageableOrder<Order> findPaidOrderPage(PageableOrder<Order> daoOrderPageable) throws DaoException;

    /**
     * Makes a page for showing all orders to admin (if ordered product wasn't deleted)
     * @param daoOrderPageable page params object
     * @return all orders page
     * @throws DaoException is a module exception
     */
   Pageable<Order> findAllOrderPage(Pageable<Order> daoOrderPageable) throws DaoException;

    /**
     * Makes a page for showing all products to admin
     * @param daoProductPageable page params object
     * @return all products page
     * @throws DaoException is a module exception
     */
    Pageable<Product> findAllProductsPage(Pageable<Product> daoProductPageable) throws DaoException;
}
