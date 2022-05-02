<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>

<head>

<style type = "text/css">

body {

      height: 100%;
    background-image: url(https://sun9-67.userapi.com/c850336/v850336040/78acd/SdNEP-ZPNik.jpg);
}
    div.links a {
    align-content:center;
    display:inline-block;
    }
    .header {
     position: relative;
     display:inline-block;
     font-size:30px;
         top: -40;
         left: 0;
         color:white;
         height: 60px;
         width: 100%;
         z-index: 2;
    }

    .loc {
      font-size:20px;
    }

    .local {
    position: fixed;
         display:inline-block;
             top: 25;
             left: 580;
             color:white;
             height: 80px;
             width: 100%;
             z-index: 2;
    }


    div form{
    display:inline-block;
    }
     a.blubtn {
     position:relative;
     top:20;
       width:fit-content;
       text-align:center;
       display:block;
       align-items: center;
       font-family: arial;
       text-decoration: none;
       font-weight: 300;
       font-size: 50px;
       border: white 1px solid;
       color: white;
       padding: 3px;
       padding-left: 5px;
       padding-right: 5px;
       transition: .5s;
       border-radius: 0px;
       z-index:3;
     }
     a.blubtn:hover {
       top: 5px;
       transition: .5s;
       color: #10FF58;
       border: #10FF58 1px solid;
       border-radius: 10px;
     }
     a.blubtn:active {
       color: #000;
       border: #1A1A1A 1px solid;
       transition: .07s;
       background-color: #FFF;
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


               <!-- LOCALE -->
               <div class="header">
               <h1>Welcome to LulPizza</h1>
               </div>

                <div class="local">
                       <form method="post" action="/" >
                                      <input type="hidden" name="command" value="locale"/>
                                      <input type="hidden" name="locale" value="en">
                                      <button class ="loc" type="submit">${en_button}</button>

                       </form>

                       <form method="post" action="/" >
                                      <input type="hidden" name="command" value="locale"/>
                                      <input type="hidden" name="locale" value="ru">
                                      <button class ="loc" type="submit">${ru_button}</button>
                       </form>

                       </div>


               </div>

               <!-- NAVIGATION -->

               <div class="links">
               <a href="/registerPage" class="blubtn">${signUp}</a>
               <a href="/loginPage" class="blubtn">${signIn}</a>
               </div>




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

       </body>
</html>