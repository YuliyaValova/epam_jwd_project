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

                     <form method="get" action="/main" >
                     <input type="hidden" name="command" value="gotomenu"/>
                     <button type="submit">Menu</button>
                     </form>

                     <form method="get" action="/main" >
                     <input type="hidden" name="command" value="gotobasket"/>
                     <button type="submit">Basket</button>
                     </form>

                     <form method="get" action="/main" >
                     <input type="hidden" name="command" value="gotoaccount"/>
                     <button type="submit">Account</button>
                     </form>

                     <c:if test="${sessionScope.page == 'menu'}">
                     <p>Status = ${sessionScope.page} </p>
                     </c:if>

                     <c:if test="${sessionScope.page == 'account'}">
                     <p>Status = ${sessionScope.page} </p>
                     </c:if>

                     <c:if test="${sessionScope.page == 'basket'}">
                     <p>Status = ${sessionScope.page} </p>
                     </c:if>

            </c:if>
    </body>
</html>