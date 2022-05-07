package com.jwd.dao.repository;

import com.jwd.dao.exception.DaoException;

public interface OrderDao {

    /**
     * Delete all orders of the user with such id
     * @param id identifies user
     * @throws DaoException is a module exception
     */
    void deleteOrdersByUserLogin(String login) throws DaoException;

    /**
     * Changes status of all orders of the user with such id
     * @param id identifies user
     * @param newStatus the status to be set
     * @param oldStatus status to be changed
     * @throws DaoException is a module exception
     */
    void changeAllOrdersStatus(long id, String login, String comment) throws DaoException;

    /**
     * Changes the status of the specified order
     * @param orderId identifies order
     * @param newStatus status to be set
     * @throws DaoException is a module exception
     */
    void changeOrderStatus(long orderId, String newStatus) throws DaoException;

}
