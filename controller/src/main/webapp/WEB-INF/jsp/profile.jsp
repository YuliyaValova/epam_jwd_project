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
        <fmt:message bundle="${loc}" key="locale.login" var="login" />
        <fmt:message bundle="${loc}" key="locale.profile" var="profile" />
        <fmt:message bundle="${loc}" key="locale.password" var="password" />
        <fmt:message bundle="${loc}" key="locale.address" var="address" />
        <fmt:message bundle="${loc}" key="locale.fname" var="fname" />
        <fmt:message bundle="${loc}" key="locale.lname" var="lname" />
        <fmt:message bundle="${loc}" key="locale.phone" var="phone" />
        <fmt:message bundle="${loc}" key="locale.city" var="city" />
        <fmt:message bundle="${loc}" key="locale.street" var="street" />
        <fmt:message bundle="${loc}" key="locale.building" var="building" />
        <fmt:message bundle="${loc}" key="locale.apartment" var="apartment" />
        <fmt:message bundle="${loc}" key="locale.goMain" var="goMain" />
        <fmt:message bundle="${loc}" key="locale.deleteAccount" var="deleteAccount" />
        <fmt:message bundle="${loc}" key="locale.updateAccount" var="updateAccount" />
        <fmt:message bundle="${loc}" key="locale.changePassword" var="changePassword" />


<title>
    <c:out value = "${profile}" />
</title>

</head>
    <body>

    <c:set var="error" value="${param.message}"/>

           <h1>${profile}</h1>
            <!-- LOCALE -->
            <div>
                    <form method="post" action="/profile" >
                                   <input type="hidden" name="command" value="locale"/>
                                   <input type="hidden" name="locale" value="en">
                                   <button type="submit">${en_button}</button>
                    </form>

                    <form method="post" action="/profile" >
                                   <input type="hidden" name="command" value="locale"/>
                                   <input type="hidden" name="locale" value="ru">
                                   <button type="submit">${ru_button}</button>
                    </form>


                    <form id="gotomain" method="get" action="/main" >
                                    <input type="hidden" name="command" value="gotomain"/>
                                    <button form="gotomain" type="submit">${goMain}</button>
                    </form>


            </div>

            <c:if test="${sessionScope.role != null}">

               <h3>${login}:${sessionScope.user.login}</h3>
               <h3>${fname}: ${sessionScope.user.firstName}</h3>
               <h3>${lname}: ${sessionScope.user.lastName}</h3>
               <h3>${phone}: ${sessionScope.user.phone}</h3>
               <h3>${city}: ${sessionScope.addr.city}</h3>
               <h3>${street}: ${sessionScope.addr.street}</h3>
               <h3>${building}:${sessionScope.addr.building}</h3>
               <h3>${apartment}: ${sessionScope.addr.apartment}</h3>
               <br>



                <form method="get" action="/home" >
                               <input type="hidden" name="command" value="deleteAccount"/>
                               <button type="submit">${deleteAccount}</button>
                </form>

                   <!-- todo
                <form method="post" action="/profile" >
                               <input type="hidden" name="command" value="updateAcc"/>
                               <button type="submit">${updateAccount}</button>
                </form>

                -->

                <div>
                    <form id="changePassword" method="post" action="/home" >
                                <input type="hidden" name="command" value="changepassword"/>
                                <input type="text" name="oldPassword" value="old password"/>
                                <input type="text" name="newPassword" value="new password"/>
                                <button type="submit">${changePassword}</button>
                    </form>

                </div>

            </c:if>

    </body>
</html>