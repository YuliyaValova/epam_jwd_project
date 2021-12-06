package com.jwd.dao.validation.impl;

import com.jwd.dao.domain.Address;
import com.jwd.dao.domain.Product;
import com.jwd.dao.domain.UserAccount;
import com.jwd.dao.exception.DaoException;
import com.jwd.dao.validation.DaoValidator;
import org.junit.Test;

import static org.junit.Assert.*;

public class DaoValidatorImplTest {

    private DaoValidator validator = new DaoValidatorImpl();

    @Test(expected = DaoException.class)
    public void testValidateUserAccount_negative_accountIsNull() throws DaoException {
        //arrange
        UserAccount account_null = null;
        //act
        validator.validateUserAccount(account_null);
    }

    @Test(expected = DaoException.class)
    public void testValidateUserAccount_negative_incompleteFields() throws DaoException {
        //arrange
        Address address = new Address("city", "street", "building", "apart");
        UserAccount account_incompleteFields = new UserAccount("login", "", "", "fn", "ln", "999", address);
        //act
        validator.validateUserAccount(account_incompleteFields);
    }

    @Test(expected = DaoException.class)
    public void testValidateUserAccount_negative_incompleteAddress() throws DaoException {
        //arrange
        UserAccount account_incompleteAddress = new UserAccount("login", "pas", "role", "fn", "ln", "999", null);
        //act
        validator.validateUserAccount(account_incompleteAddress);
    }

    @Test
    public void testValidateUserAccount() throws DaoException {
        //arrange
        Address address = new Address("city", "street", "building", "apart");
        UserAccount account = new UserAccount("login", "pass", "role", "fn", "ln", "999", address);
        //act
        validator.validateUserAccount(account);
    }

    @Test
    public void testValidateLoginAndPassword() throws DaoException {
        String login = "Login";
        String password = "Password";

        validator.validateLoginAndPassword(login, password);

    }

    @Test(expected = DaoException.class)
    public void testValidateLoginAndPassword_negative_null() throws DaoException {
        String login = null;
        String password = "Password";

        validator.validateLoginAndPassword(login, password);

    }

    @Test(expected = DaoException.class)
    public void testValidateLoginAndPassword_negative_empty() throws DaoException {
        String login = "Login";
        String password = "";

        validator.validateLoginAndPassword(login, password);

    }

    @Test
    public void testValidateId() throws DaoException {
        long id = 15L;
        validator.validateId(id);
    }

    @Test(expected = DaoException.class)
    public void testValidateId_negative() throws DaoException {
        long id = 0L;
        validator.validateId(id);
    }

    @Test
    public void testValidateProduct() throws DaoException {
        Product product = new Product("Pesto", "pizza", "Yummi", 17.7, true);
        validator.validateProduct(product);
    }

    @Test(expected = DaoException.class)
    public void testValidateProduct_negative_null() throws DaoException {
        Product product = null;
        validator.validateProduct(product);
    }

    @Test(expected = DaoException.class)
    public void testValidateProduct_negative_incompleteInfo() throws DaoException {
        Product product = new Product("Pesto", "", "Yummi", 17.7, true);
        validator.validateProduct(product);
    }

    @Test(expected = DaoException.class)
    public void testValidateProduct_negative_invalidPrice() throws DaoException {
        Product product = new Product("Pesto", "pizza", "Yummi", -5, true);
        validator.validateProduct(product);
    }

    @Test
    public void testValidatePassword() throws DaoException {
        String password = "1111";
        validator.validatePassword(password);
    }

    @Test(expected = DaoException.class)
    public void testValidatePassword_negative_null() throws DaoException {
        String password = null;
        validator.validatePassword(password);
    }

    @Test(expected = DaoException.class)
    public void testValidatePassword_negative_empty() throws DaoException {
        String password = "";
        validator.validatePassword(password);
    }

    @Test
    public void testValidateStatus() throws DaoException {
        String status = "Paid up";
        validator.validateStatus(status);
    }

    @Test(expected = DaoException.class)
    public void testValidateStatus_negative_null() throws DaoException {
        String status = null;
        validator.validateStatus(status);
    }

    @Test(expected = DaoException.class)
    public void testValidateStatus_negative_empty() throws DaoException {
        String status = "";
        validator.validateStatus(status);
    }
}