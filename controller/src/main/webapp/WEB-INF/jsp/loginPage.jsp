<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html>
    <body>
    <c:set var="error" value="${param.message}"/>
           <h1>Sign in</h1>

            <c:if test="${error == 'noSuchUser'}">
                <p style="color: red">There is no such user</p>
            </c:if>

            <c:if test="${error == 'IncompleteInfo'}">
                <p style="color: red">Not all fields are filled</p>
            </c:if>


           <form method="post" action="/main" >
               <input type="hidden" name="command" value="login"/>
               <p>login</p>
               <input type="text" name="login" value="">
               <p>password</p>
               <input type="password" name="password" value="">
               <button type="submit">log in</button>
           </form
    </body>
</html>