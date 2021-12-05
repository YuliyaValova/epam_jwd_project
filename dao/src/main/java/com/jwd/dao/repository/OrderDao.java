package com.jwd.dao.repository;

import com.jwd.dao.exception.DaoException;

public interface OrderDao {

    void deleteOrdersByUserId(long id) throws DaoException;

    void changeAllOrdersStatus(long id, String newStatus, String oldStatus) throws DaoException;

    void changeOrderStatus(long orderId, String newStatus) throws DaoException;

}
