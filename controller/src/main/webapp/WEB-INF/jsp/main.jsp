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

<title>
    <c:out value = "${main}" />
</title>

</head>


    <body>
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

                     <form method="get" action="/main" >
                     <input type="hidden" name="command" value="gotomenu"/>
                     <button type="submit">${menu}</button>
                     </form>

                     <form method="get" action="/main" >
                     <input type="hidden" name="command" value="gotobasket"/>
                     <button type="submit">${basket}</button>
                     </form>

                     <form method="get" action="/main" >
                     <input type="hidden" name="command" value="gotoaccount"/>
                     <button type="submit">${account}</button>
                     </form>

                      <form method="get" action="/" >
                      <input type="hidden" name="command" value="logout"/>
                      <button type="submit">${logout}</button>
                      </form>

                     </div>

                     <c:if test="${sessionScope.page == 'menu'}">
                     <p>Status = ${sessionScope.page} </p>
                     </c:if>

                     <c:if test="${sessionScope.page == 'account'}">
                     <p>Status = ${sessionScope.page} </p>
                     </c:if>

                     <c:if test="${sessionScope.page == 'basket'}">
                     <p>Status = ${sessionScope.page} </p>
                     </c:if>


            </c:if>
    </body>
</html>