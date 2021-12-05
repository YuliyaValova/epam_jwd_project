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
        <fmt:message bundle="${loc}" key="locale.registerPage" var="registerPage" />
        <fmt:message bundle="${loc}" key="locale.signUp" var="signUp" />
        <fmt:message bundle="${loc}" key="locale.login" var="login" />
        <fmt:message bundle="${loc}" key="locale.password" var="password" />
        <fmt:message bundle="${loc}" key="locale.repPassword" var="repPassword" />
        <fmt:message bundle="${loc}" key="locale.fieldsFillMessage" var="fieldsFillMessage" />
        <fmt:message bundle="${loc}" key="locale.info" var="info" />
        <fmt:message bundle="${loc}" key="locale.address" var="address" />
        <fmt:message bundle="${loc}" key="locale.fname" var="fname" />
        <fmt:message bundle="${loc}" key="locale.lname" var="lname" />
        <fmt:message bundle="${loc}" key="locale.phone" var="phone" />
        <fmt:message bundle="${loc}" key="locale.city" var="city" />
        <fmt:message bundle="${loc}" key="locale.street" var="street" />
        <fmt:message bundle="${loc}" key="locale.building" var="building" />
        <fmt:message bundle="${loc}" key="locale.apartment" var="apartment" />
        <fmt:message bundle="${loc}" key="locale.errorMsg_noUser" var="errorMsg_noUser" />
        <fmt:message bundle="${loc}" key="locale.errorMsg_fieldsFill" var="errorMsg_fieldsFill" />
        <fmt:message bundle="${loc}" key="locale.errorMsg_alrExists" var="errorMsg_alrExists" />
        <fmt:message bundle="${loc}" key="locale.errorMsg_invOperation" var="errorMsg_invOperation" />
        <fmt:message bundle="${loc}" key="locale.errorMsg_injectDanger" var="errorMsg_injectDanger" />
        <fmt:message bundle="${loc}" key="locale.errorMsg_pswNotMatch" var="errorMsg_pswNotMatch" />

<title>
    <c:out value = "${registerPage}" />
</title>

</head>
    <body>

    <c:set var="error" value="${param.message}"/>

           <h1>${registerPage}</h1>
            <!-- LOCALE -->
            <div>
                    <form method="post" action="/registerPage" >
                                   <input type="hidden" name="command" value="locale"/>
                                   <input type="hidden" name="locale" value="en">
                                   <button type="submit">${en_button}</button>
                    </form>

                    <form method="post" action="/registerPage" >
                                   <input type="hidden" name="command" value="locale"/>
                                   <input type="hidden" name="locale" value="ru">
                                   <button type="submit">${ru_button}</button>
                    </form>

            </div>

           <h3>${fieldsFillMessage}<h3>

            <c:if test="${error == 'noSuchUser'}">
                <p style="color: red">${errorMsg_noUser}</p>
            </c:if>

            <c:if test="${error == 'IncompleteInfo'}">
                <p style="color: red">${errorMsg_fieldsFill}</p>
            </c:if>

            <c:if test="${error == 'userExists'}">
                <p style="color: red">${errorMsg_alrExists}</p>
            </c:if>

            <c:if test="${error == 'passwordsNotMatch'}">
                <p style="color: red">${errorMsg_pswNotMatch}</p>
            </c:if>

           <form method="post" action="/home" >
               <input type="hidden" name="command" value="registration"/>
               <p>${login}*</p>
               <input type="text" name="login" value="">
               <p>${password}*</p>
               <input type="password" name="password1" value="">
               <p>${repPassword}*</p>
               <input type="password" name="password2" value="">
               <h2>${info}</h2>
               <p>${fname}*</p>
               <input type="text" name="fName" value="">
               <p>${lname}*</p>
               <input type="text" name="lName" value="">
               <p>${phone}*</p>
               <input type="text" name="phone" value="">
               <h2>${address}</h2>
               <p>${city}*</p>
               <input type="text" name="city" value="">
               <p>${street}*</p>
               <input type="text" name="street" value="">
               <p>${building}*</p>
               <input type="text" name="building" value="">
               <p>${apartment}</p>
               <input type="text" name="apartment" value="">
               <br>
               <br>
               <button type="submit">${signUp}</button>
           </form



    </body>
</html>