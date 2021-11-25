<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <body>

    <c:set var="error" value="${param.message}"/>

           <div>
               <h2>Home!</h2>
               <!-- NAVIGATION -->
               <a href="/registerPage">Registration</a>
               </br>
               <a href="/loginPage">Log In</a>

                <c:if test="${error == 'noSuchUser'}">
                       <p style="color: red">There is no such user</p>
                   </c:if>
                   <c:if test="${error == 'userExists'}">
                       <p style="color: red">Such user already exists exists</p>
                   </c:if>
                   <c:if test="${error == 'passwordsNotMatch'}">
                       <p style="color: red">Entered passwords don't match</p>
                   </c:if>
                   <c:if test="${error == 'RegistrationError'}">
                       <p style="color: red">Registration is failed</p>
                   </c:if>
                    <c:if test="${error == 'LoginationError'}">
                       <p style="color: red">Logination is failed</p>
                    </c:if>

               <br/>
           </div>
       </body>
</html>