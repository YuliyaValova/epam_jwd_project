package com.jwd.dao.repository;
import com.jwd.dao.domain.Product;
import com.jwd.dao.exception.DaoException;

public interface ProductDao {

    /**
     * Deletes a product from db by id
     * @param id identifies product
     * @throws DaoException is a module exception
     */
    void deleteProductById(long id) throws DaoException;

    /**
     * Saves a product to db
     * @param product that is saved
     * @return saved product id
     * @throws DaoException is a module exception
     */
    long saveProduct(Product product) throws DaoException;

    /**
     * Receives a product from db by id
     * @param id identifies product
     * @return searching product
     * @throws DaoException is a module exception
     */
    Product getProductById(long id) throws DaoException;

    // boolean updateProduct (Product product)
    // List<Product> getAllProducts() throws DaoException;

}
