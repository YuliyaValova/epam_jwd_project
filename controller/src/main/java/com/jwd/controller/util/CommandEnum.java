package com.jwd.controller.util;

public enum CommandEnum {
    DEFAULT("default"),
    REGISTRATION("registration"),
    LOGIN("login"),
    LOGOUT("logout"),
    GOTOMAIN("gotomain"),
    LOCALE("locale"),
    GOTOPROFILE("gotoprofile"),
    SHOWPRODUCTS("showproducts"),
    SHOWBASKET("showbasket"),
    CLEANBASKET("cleanbasket"),
    SENDORDER("sendOrder"),
    DELETEFROMBASKET("deletefrombasket"),
    ADDTOBASKET("addtobasket"),
    CHANGEPASSWORD("changepassword"),
    GOTOADDPRODUCTPAGE("gotoAddProductPage"),
    DELETEACCOUNT("deleteAccount"),
    ADDPRODUCTTOMENU("addProductToMenu"),
    GETPAIDORDERS("getPaidOrders"),
    GETALLORDERS("getAllOrders"),
    ADDADMIN("addAdmin"),
    FINDUSERBYID("findUserById"),
    HIDEPRODUCT("hideProduct"),
    CHANGEORDERSTATUS("changeOrderStatus"),
    FINDPRODUCTBYID("findProductById"),
    CHANGEPRODUCTSTATUS("changeproductstatus"),
    GETALLPRODUCTS("getAllProducts"),
    UPDATEUSER("updateUser"),
    UPDATEPRODUCT("updateProduct"),
    DELETEFROMMENU("deletefrommenu"),
    WRITETOCSV("writeToCSV");


    private String name;

    CommandEnum(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
