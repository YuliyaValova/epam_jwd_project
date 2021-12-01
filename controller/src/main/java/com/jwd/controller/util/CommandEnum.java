package com.jwd.controller.util;

public enum CommandEnum {
    DEFAULT("default"),
    REGISTRATION("registration"),
    LOGIN("login"),
    LOGOUT("logout"),
    GOTOMENU("gotomenu"),
    GOTOMAIN("gotomain"),
    GOTOBASKET("gotobasket"),
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
    ADDPRODUCTTOMENU("addProductToMenu");


    private String name;

    CommandEnum(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
