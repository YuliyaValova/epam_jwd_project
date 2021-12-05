package com.jwd.dao.repository;

import com.jwd.dao.domain.Order;
import com.jwd.dao.domain.Pageable;
import com.jwd.dao.domain.PageableOrder;
import com.jwd.dao.domain.Product;
import com.jwd.dao.exception.DaoException;

public interface PageDao {

    Pageable<Product> findPage(Pageable<Product> daoProductPageable) throws DaoException;

    Pageable<Product> findBasketPage(Pageable<Product> daoBasketPageable, long id) throws DaoException;

    PageableOrder<Order> findPaidOrderPage(PageableOrder<Order> daoOrderPageable) throws DaoException;

    PageableOrder<Order> findAllOrderPage(PageableOrder<Order> daoOrderPageable) throws DaoException;

}
