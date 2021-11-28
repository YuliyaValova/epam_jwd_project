<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html>
    <body>

    <c:set var="error" value="${param.message}"/>

           <h1>Registration form</h1>
           <h3>Field with * must be filled in!<h3>

            <c:if test="${error == 'noSuchUser'}">
                <p style="color: red">There is no such user</p>
            </c:if>

            <c:if test="${error == 'IncompleteInfo'}">
                <p style="color: red">Not all fields are filled</p>
            </c:if>

            <c:if test="${error == 'userExists'}">
                <p style="color: red">Such user already exists</p>
            </c:if>

            <c:if test="${error == 'passwordsNotMatch'}">
                <p style="color: red">Entered passwords don't match</p>
            </c:if>

            <c:if test="${error == 'invalideCommand'}">
                <p style="color: red">You can't do this operation</p>
            </c:if>

            <c:if test="${error == 'InjectionDanger'}">
                <p style="color: red">Danger of injection</p>
            </c:if>

           <form method="post" action="/" >
               <input type="hidden" name="command" value="registration"/>
               <p>login*</p>
               <input type="text" name="login" value="">
               <p>password*</p>
               <input type="password" name="password1" value="">
               <p>repeat password*</p>
               <input type="password" name="password2" value="">
               <h4>Info</h4>
               <p>First Name*</p>
               <input type="text" name="fName" value="">
               <p>Last Name*</p>
               <input type="text" name="lName" value="">
               <p>Phone*</p>
               <input type="text" name="phone" value="">
               <h4>Address</h4>
               <p>City*</p>
               <input type="text" name="city" value="">
               <p>Street*</p>
               <input type="text" name="street" value="">
               <p>Building*</p>
               <input type="text" name="building" value="">
               <p>Apartment</p>
               <input type="text" name="apartment" value="">
               <br>
               <br>
               <button type="submit">Registration</button>
           </form



    </body>
</html>