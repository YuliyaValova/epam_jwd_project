<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
    <head>
    <style type = "text/css">
        div form {
            display:inline-block
        }
    </style>

            <fmt:setLocale value="${sessionScope.locale}" />
    		<fmt:setBundle basename="localization.local" var="loc" />
    		<fmt:message bundle="${loc}" key="locale.locbutton.name.en" var="en_button"/>
            <fmt:message bundle="${loc}" key="locale.locbutton.name.ru" var="ru_button" />
            <fmt:message bundle="${loc}" key="locale.goMain" var="goMain" />
            <fmt:message bundle="${loc}" key="locale.addProdPage" var="addProdPage" />
            <fmt:message bundle="${loc}" key="locale.p_name" var="p_name" />
            <fmt:message bundle="${loc}" key="locale.p_type" var="p_type" />
            <fmt:message bundle="${loc}" key="locale.p_price" var="p_price" />
            <fmt:message bundle="${loc}" key="locale.p_status" var="p_status" />
            <fmt:message bundle="${loc}" key="locale.addToMenu" var="addToMenu" />
            <fmt:message bundle="${loc}" key="locale.p_description" var="p_description" />
            <fmt:message bundle="${loc}" key="locale.errorMsg_fieldsFill" var="errorMsg_fieldsFill" />
            <fmt:message bundle="${loc}" key="locale.errorMsg_addProduct" var="errorMsg_addProduct" />
            <fmt:message bundle="${loc}" key="locale.errorMsg_productExists" var="errorMsg_productExists" />
            <fmt:message bundle="${loc}" key="locale.errorMsg_invalidPrice" var="errorMsg_invalidPrice" />

    <title>
        <c:out value = "${addProdPage}" />
    </title>
    </head>
        <body>
        <c:set var="error" value="${param.message}"/>

            <h2>${addProdPage}</h2>

            <!-- LOCALE -->
                        <div>
                                <form method="post" action="/addProduct" >
                                               <input type="hidden" name="command" value="locale"/>
                                               <input type="hidden" name="locale" value="en">
                                               <button type="submit">${en_button}</button>
                                </form>

                                <form method="post" action="/addProduct" >
                                               <input type="hidden" name="command" value="locale"/>
                                               <input type="hidden" name="locale" value="ru">
                                               <button type="submit">${ru_button}</button>
                                </form>


                                <form id="gotomain" method="get" action="/main" >
                                                <input type="hidden" name="command" value="gotomain"/>
                                                <button form="gotomain" type="submit">${goMain}</button>
                                </form>


                        </div>

            <c:if test="${error == 'IncompleteInfo'}">
            <p style="color: red">${errorMsg_fieldsFill}</p>
            </c:if>

            <c:if test="${error == 'AddProductError'}">
            <p style="color: red">${errorMsg_addProduct}</p>
            </c:if>

            <c:if test="${error == 'ProductExists'}">
            <p style="color: red">${errorMsg_productExists}</p>
            </c:if>

            <c:if test="${error == 'InvalidPriceError'}">
            <p style="color: red">${errorMsg_invalidPrice}</p>
            </c:if>

             <c:if test="${sessionScope.role == 'admin'}">
                 <h3>${fieldsFillMessage}<h3>

                 <form id="add" method="post" action="/main" >
                 <input type="hidden" name="command" value="addProductToMenu"/>
                 <p>${p_name}*</p>
                 <input type="text" name="name" value="">
                 <p>${p_type}*</p>
                 <input type="text" name="type" value="">
                 <p>${p_price}*</p>
                 <input type="text" name="price" value="">
                 <p>${p_description}</p>
                 <input type="text" name="description" value="">
                 <p>${p_status}*</p>
                 <input type="text" name="status" value="">
                 <button type="submit">${addToMenu}</button>
                 </form>

            </c:if>

        </body>

</html>