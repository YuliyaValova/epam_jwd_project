<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <body>
           <h1>Hello, it is page for products</h1>
            <c:if test="${sessionScope.role != null}">
                                  <p>Authorized = ${sessionScope.role} </p>
                                  <form method="get" action="/" >
                                      <input type="hidden" name="command" value="logout"/>
                                      <button type="submit">Log out</button>
                                  </form>
             </c:if>

    </body>
</html>