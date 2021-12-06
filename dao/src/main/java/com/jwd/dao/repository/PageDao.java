package com.jwd.dao.repository;

import com.jwd.dao.domain.Order;
import com.jwd.dao.domain.Pageable;
import com.jwd.dao.domain.PageableOrder;
import com.jwd.dao.domain.Product;
import com.jwd.dao.exception.DaoException;

public interface PageDao {

    /**
     * Makes a page for showing available products
     * @param daoProductPageable page params object
     * @return product page
     * @throws DaoException is a module exception
     */
    Pageable<Product> findPage(Pageable<Product> daoProductPageable) throws DaoException;

    /**
     * Makes a page for showing products from the user's basket
     * @param daoBasketPageable page params object
     * @param id identifies user
     * @return basket page
     * @throws DaoException is a module exception
     */
    Pageable<Product> findBasketPage(Pageable<Product> daoBasketPageable, long id) throws DaoException;

    /**
     * Makes a page for showing paid orders to admin
     * @param daoOrderPageable page params object
     * @return paid orders page
     * @throws DaoException is a module exception
     */
    PageableOrder<Order> findPaidOrderPage(PageableOrder<Order> daoOrderPageable) throws DaoException;

    /**
     * Makes a page for showing all orders to admin (if ordered product wasn't deleted)
     * @param daoOrderPageable page params object
     * @return all orders page
     * @throws DaoException is a module exception
     */
    PageableOrder<Order> findAllOrderPage(PageableOrder<Order> daoOrderPageable) throws DaoException;

}