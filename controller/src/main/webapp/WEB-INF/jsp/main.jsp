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
        <fmt:message bundle="${loc}" key="locale.main" var="main" />
        <fmt:message bundle="${loc}" key="locale.logout" var="logout" />
        <fmt:message bundle="${loc}" key="locale.menu" var="menu" />
        <fmt:message bundle="${loc}" key="locale.account" var="account" />
        <fmt:message bundle="${loc}" key="locale.basket" var="basket" />
        <fmt:message bundle="${loc}" key="locale.add" var="add" />
        <fmt:message bundle="${loc}" key="locale.getPaid" var="getPaid" />
        <fmt:message bundle="${loc}" key="locale.getAll" var="getAll" />
        <fmt:message bundle="${loc}" key="locale.findById" var="findById" />
        <fmt:message bundle="${loc}" key="locale.add_admin" var="add_admin" />
        <fmt:message bundle="${loc}" key="locale.errorMsg_menuError" var="errorMsg_menuError" />
        <fmt:message bundle="${loc}" key="locale.errorMsg_accountError" var="errorMsg_accountError" />
        <fmt:message bundle="${loc}" key="locale.errorMsg_basketError" var="errorMsg_basketError" />


<title>
    <c:out value = "${main}" />
</title>

</head>


    <body>
    <c:set var="error" value="${param.message}"/>

            <h1>${main}</h1>
            <!-- LOCALE -->
            <div>
                    <form method="post" action="/main" >
                                   <input type="hidden" name="command" value="locale"/>
                                   <input type="hidden" name="locale" value="en">
                                   <button type="submit">${en_button}</button>
                    </form>

                    <form method="post" action="/main" >
                                   <input type="hidden" name="command" value="locale"/>
                                   <input type="hidden" name="locale" value="ru">
                                   <button type="submit">${ru_button}</button>
                    </form>

                </div>




            <c:if test="${sessionScope.role != null}">

                     <p>Authorized = ${sessionScope.role} </p>

                     <div>

                     <form method="get" action="/menu" >
                     <input type="hidden" name="command" value="gotomenu"/>
                     <button type="submit">${menu}</button>
                     </form>

                     <c:if test = "${sessionScope.role != 'admin'}">

                     <form method="get" action="/basket" >
                     <input type="hidden" name="command" value="gotobasket"/>
                     <button type="submit">${basket}</button>
                     </form>

                     </c:if>

                     <form method="get" action="/profile" >
                     <input type="hidden" name="command" value="gotoprofile"/>
                     <button type="submit">${account}</button>
                     </form>

                      <form method="get" action="/" >
                      <input type="hidden" name="command" value="logout"/>
                      <button type="submit">${logout}</button>
                      </form>

                     </div>

                     <c:if test = "${sessionScope.role == 'admin'}">

                     <form method="get" action="/addProduct" >
                     <input type="hidden" name="command" value="gotoAddProductPage"/>
                     <button type="submit">${add}</button>
                     </form>

                     <form method="get" action="/main" >
                     <input type="hidden" name="command" value="getPaidOrders"/>
                     <button type="submit">${getPaid}</button>
                     </form>

                     <form method="get" action="/main" >
                     <input type="hidden" name="command" value="getAllOrders"/>
                     <button type="submit">${getAll}</button>
                     </form>

                     <form method="post" action="/main" >
                     <input type="hidden" name="command" value="findUserById"/>
                     <input type="text" name="id" value="id"/>
                     <button type="submit">${findById}</button>
                     </form>

                     <form method="post" action="/main" >
                     <input type="hidden" name="command" value="addAdmin"/>
                     <input type="text" name="admin_id" value="id"/>
                     <button type="submit">${add_admin}</button>
                     </form>

                     <form method="get" action="/main" >
                     <input type="hidden" name="command" value="addAdmin"/>
                     <input type="text" name="admin_id" value="id"/>
                     <button type="submit">${add_admin}</button>
                     </form>

                     </c:if>



                     <c:if test="${sessionScope.page == 'account'}">
                     <p>Status = ${sessionScope.page} </p>
                     </c:if>

                     <c:if test="${sessionScope.page == 'basket'}">
                     <p>Status = ${sessionScope.page} </p>
                     </c:if>

                     <c:if test="${error == 'MenuError'}">
                     <p style="color: red">${errorMsg_menuError}</p>
                     </c:if>

                     <c:if test="${error == 'AccountError'}">
                     <p style="color: red">${errorMsg_accountError}</p>
                     </c:if>

                     <c:if test="${error == 'BasketError'}">
                     <p style="color: red">${errorMsg_basketError}</p>
                     </c:if>


            </c:if>
    </body>
</html>