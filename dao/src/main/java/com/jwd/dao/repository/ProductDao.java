package com.jwd.dao.repository;

import com.jwd.dao.domain.Pageable;
import com.jwd.dao.domain.Product;
import com.jwd.dao.exception.DaoException;

import java.util.List;

public interface ProductDao {

    long addProduct(Product product) throws DaoException;

    void deleteProductById(long id) throws DaoException;

    List<Product> getAllProducts() throws DaoException;

    Product getProductById(long id) throws DaoException;

    Pageable<Product> findPage(Pageable<Product> daoProductPageable) throws DaoException;

    Pageable<Product> findBasketPage(Pageable<Product> daoBasketPageable, long id) throws DaoException;

    void deleteOrdersByUserId(long id) throws DaoException;

    double getSum(long id) throws DaoException;

    void changeAllOrdersStatus(long id, String newStatus, String oldStatus) throws DaoException;

    // boolean updateProduct (Product product)

}
