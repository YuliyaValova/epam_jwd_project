package com.jwd.dao.repository;

import com.jwd.dao.exception.DaoException;

public interface BasketDao {

    /**
     * Calculates the value of a user's basket
     * @param id identifies user
     * @return total sum
     * @throws DaoException is a module exception
     */
    double getSum(long id) throws DaoException;

    /**
     * Deletes a product from user's basket
     * @param id identifies user
     * @param productId identifies product to be deleted
     * @throws DaoException is a module exception
     */
    void deleteFromBasket(long id, long productId) throws DaoException;

    /**
     * Adds a product to user's basket
     * @param id identifies user
     * @param productId identifies product to be added
     * @return true if added is successful, otherwise - false
     * @throws DaoException is a module exception
     */
    boolean addToBasket(long id, long productId) throws DaoException;

}
