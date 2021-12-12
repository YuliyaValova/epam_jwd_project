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
        <fmt:message bundle="${loc}" key="locale.home" var="home" />
        <fmt:message bundle="${loc}" key="locale.signIn" var="signIn" />
        <fmt:message bundle="${loc}" key="locale.signUp" var="signUp" />
        <fmt:message bundle="${loc}" key="locale.errorMsg_registrError" var="errorMsg_registrError" />
        <fmt:message bundle="${loc}" key="locale.errorMsg_loginError" var="errorMsg_loginError" />
        <fmt:message bundle="${loc}" key="locale.errorMsg_basketError" var="errorMsg_basketError" />
        <fmt:message bundle="${loc}" key="locale.errorMsg_accountError" var="errorMsg_accountError" />
        <fmt:message bundle="${loc}" key="locale.errorMsg_injectDanger" var="errorMsg_injectDanger" />
        <fmt:message bundle="${loc}" key="locale.errorMsg_invOperation" var="errorMsg_invOperation" />
        <fmt:message bundle="${loc}" key="locale.errorMsg_sessionError" var="errorMsg_sessionError" />

<title>
    <c:out value = "${home}" />
</title>

</head>


    <body>

    <c:set var="error" value="${param.message}"/>

           <div>
               <h2>${home}</h2>
               <!-- LOCALE -->
               <div>
                       <form method="post" action="/" >
                                      <input type="hidden" name="command" value="locale"/>
                                      <input type="hidden" name="locale" value="en">
                                      <button type="submit">${en_button}</button>
                       </form>

                       <form method="post" action="/" >
                                      <input type="hidden" name="command" value="locale"/>
                                      <input type="hidden" name="locale" value="ru">
                                      <button type="submit">${ru_button}</button>
                       </form>

                   </div>

               <!-- NAVIGATION -->
               <a href="/registerPage">${signUp}</a>
               </br>
               <a href="/loginPage" >${signIn}</a>



                    <c:if test="${requestScope.message != null}">
                        <p style="color: green">${requestScope.message}</p>
                    </c:if>

                   <c:if test="${error == 'RegistrationError'}">
                       <p style="color: red">${errorMsg_registrError}</p>
                   </c:if>

                   <c:if test="${error == 'SessionError'}">
                       <p style="color: red">${errorMsg_sessionError}</p>
                   </c:if>

                    <c:if test="${error == 'LoginationError'}">
                       <p style="color: red">${errorMsg_loginError}</p>
                    </c:if>
                     <c:if test="${error == 'invalideCommand'}">
                       <p style="color: red">${errorMsg_invOperation}</p>
                     </c:if>

                     <c:if test="${error == 'InjectionDanger'}">
                       <p style="color: red">${errorMsg_injectDanger}</p>
                     </c:if>


                     <c:if test="${error == 'BasketError'}">
                     <p style="color: red">${errorMsg_basketError}</p>
                     </c:if>

                     <c:if test="${error == 'userNotExists'}">
                     <p style="color: red">Your account is not exists!</p>
                     </c:if>

                     <c:if test="${error == 'AccountError'}">
                     <p style="color: red">${errorMsg_accountError}</p>
                     </c:if>
               <br/>
           </div>
       </body>
</html>