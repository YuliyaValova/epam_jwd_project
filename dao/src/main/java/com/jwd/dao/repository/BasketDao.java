package com.jwd.dao.repository;

import com.jwd.dao.exception.DaoException;

public interface BasketDao {

    double getSum(long id) throws DaoException;

    void deleteFromBasket(long id, long productId) throws DaoException;

    boolean addToBasket(long id, long productId) throws DaoException;

}
