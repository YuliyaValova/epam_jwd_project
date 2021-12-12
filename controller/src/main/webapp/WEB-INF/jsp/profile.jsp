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
        <fmt:message bundle="${loc}" key="locale.account" var="account" />
        <fmt:message bundle="${loc}" key="locale.password" var="password" />
        <fmt:message bundle="${loc}" key="locale.address" var="address" />
        <fmt:message bundle="${loc}" key="locale.fname" var="fname" />
        <fmt:message bundle="${loc}" key="locale.lname" var="lname" />
        <fmt:message bundle="${loc}" key="locale.phone" var="phone" />
        <fmt:message bundle="${loc}" key="locale.role" var="role" />
        <fmt:message bundle="${loc}" key="locale.city" var="city" />
        <fmt:message bundle="${loc}" key="locale.street" var="street" />
        <fmt:message bundle="${loc}" key="locale.building" var="building" />
        <fmt:message bundle="${loc}" key="locale.apartment" var="apartment" />
        <fmt:message bundle="${loc}" key="locale.goMain" var="goMain" />
        <fmt:message bundle="${loc}" key="locale.Update" var="Update" />
        <fmt:message bundle="${loc}" key="locale.deleteAccount" var="deleteAccount" />
        <fmt:message bundle="${loc}" key="locale.updateAccount" var="updateAccount" />
        <fmt:message bundle="${loc}" key="locale.changePassword" var="changePassword" />
        <fmt:message bundle="${loc}" key="locale.IncorrectOldPassword" var="IncorrectOldPassword" />


<title>
    <c:out value = "${account}" />
</title>

</head>
    <body>

    <c:set var="error" value="${param.message}"/>

           <h1>${account}</h1>
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

             <c:if test="${requestScope.message != null}">
             <p style="color: green">${requestScope.message}</p>
             </c:if>

            <c:if test="${error == 'IncorrectOldPassword'}">
            <p style="color: red">${IncorrectOldPassword}</p>
            </c:if>

            <c:if test="${error == 'IncompleteInfo'}">
            <p style="color: red">All fields must be filled!</p>
            </c:if>

            <c:if test="${sessionScope.role != null}">

                <c:if test="${sessionScope.page != 'findUser'}">

                <form method="post" action="/main" >
                <input type="hidden" name="command" value="updateUser"/>
                <h3>${login}: ${sessionScope.user.login}</h3>
                <h3>${fname}:</h3>
                <input type="text" name="fName" value="${sessionScope.user.firstName}"/>
                <h3>${lname}:</h3>
                <input type="text" name="lName" value="${sessionScope.user.lastName}"/>
                <h3>${phone}:</h3>
                <input type="text" name="phone" value="${sessionScope.user.phone}"/>
                <h3>${city}:</h3>
                <input type="text" name="city" value="${sessionScope.addr.city}"/>
                <h3>${street}:</h3>
                <input type="text" name="street" value="${sessionScope.addr.street}"/>
                <h3>${building}:</h3>
                <input type="text" name="building" value="${sessionScope.addr.building}"/>
                <h3>${apartment}:</h3>
                <input type="text" name="apartment" value="${sessionScope.addr.apartment}"/>
                <br>
                <br>
                <button type="submit">${Update}</button>
                </form>
               <br>

                <form method="get" action="/home" >
                               <input type="hidden" name="command" value="deleteAccount"/>
                               <button type="submit">${deleteAccount}</button>
                </form>


                <div>
                    <form id="changePassword" method="post" action="/home" >
                                <input type="hidden" name="command" value="changepassword"/>
                                <input type="text" name="oldPassword" value="old password"/>
                                <input type="text" name="newPassword" value="new password"/>
                                <button type="submit">${changePassword}</button>
                    </form>

                </div>
                </c:if>

                <c:if test="${sessionScope.page == 'findUser'}">

                               <h3>${login}:${sessionScope.findUser.login}</h3>
                               <h3>${fname}: ${sessionScope.findUser.firstName}</h3>
                               <h3>${lname}: ${sessionScope.findUser.lastName}</h3>
                               <h3>${phone}: ${sessionScope.findUser.phone}</h3>
                               <h3>${role}: ${sessionScope.findUser.role}</h3>
                               <h3>${city}: ${sessionScope.findAddr.city}</h3>
                               <h3>${street}: ${sessionScope.findAddr.street}</h3>
                               <h3>${building}:${sessionScope.findAddr.building}</h3>
                               <h3>${apartment}: ${sessionScope.findAddr.apartment}</h3>
                               <br>

                </c:if>

            </c:if>

    </body>
</html>