package com.jwd.dao.repository;
import com.jwd.dao.domain.Product;
import com.jwd.dao.exception.DaoException;

public interface ProductDao {

    void deleteProductById(long id) throws DaoException;

    long saveProduct(Product product) throws DaoException;

    Product getProductById(long id) throws DaoException;

    // boolean updateProduct (Product product)
    // List<Product> getAllProducts() throws DaoException;

}
