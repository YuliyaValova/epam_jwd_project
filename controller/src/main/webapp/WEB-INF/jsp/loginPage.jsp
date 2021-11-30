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
        <fmt:message bundle="${loc}" key="locale.signInForm" var="signInForm" />
        <fmt:message bundle="${loc}" key="locale.login" var="login" />
        <fmt:message bundle="${loc}" key="locale.password" var="password" />
        <fmt:message bundle="${loc}" key="locale.errorMsg_noUser" var="errorMsg_noUser" />
        <fmt:message bundle="${loc}" key="locale.errorMsg_fieldsFill" var="errorMsg_fieldsFill" />

<title>
    <c:out value = "${signInForm}" />
</title>

</head>


    <body>
    <c:set var="error" value="${param.message}"/>
           <h1>${signInForm}</h1>
           <!-- LOCALE -->
           <div>
                   <form method="post" action="/loginPage" >
                                  <input type="hidden" name="command" value="locale"/>
                                  <input type="hidden" name="locale" value="en">
                                  <button type="submit">${en_button}</button>
                   </form>

                   <form method="post" action="/loginPage" >
                                  <input type="hidden" name="command" value="locale"/>
                                  <input type="hidden" name="locale" value="ru">
                                  <button type="submit">${ru_button}</button>
                   </form>

               </div>

            <c:if test="${error == 'noSuchUser'}">
                <p style="color: red">${errorMsg_noUser}</p>
            </c:if>

            <c:if test="${error == 'IncompleteInfo'}">
                <p style="color: red">${errorMsg_fieldsFill}</p>
            </c:if>


           <form method="post" action="/main" >
               <input type="hidden" name="command" value="login"/>
               <p>${login}</p>
               <input type="text" name="login" value="">
               <p>${password}</p>
               <input type="password" name="password" value="">
               <button type="submit">${signInForm}</button>
           </form>
    </body>
</html>