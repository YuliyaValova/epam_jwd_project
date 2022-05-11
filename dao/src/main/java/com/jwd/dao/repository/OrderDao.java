package com.jwd.dao.repository;

import com.jwd.dao.exception.DaoException;

public interface OrderDao {


    void deleteOrdersByUserLogin(String login) throws DaoException;


    long changeAllOrdersStatus(long id, String login, String comment) throws DaoException;

    void changeOrderStatus(long orderId, String newStatus) throws DaoException;

    boolean isBasketEmpty(String login) throws DaoException;
}
