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
        <fmt:message bundle="${loc}" key="locale.menu" var="menu" />
        <fmt:message bundle="${loc}" key="locale.errorMsg_showProductsError" var="errorMsg_showProductsError" />
        <fmt:message bundle="${loc}" key="locale.showProducts" var="showProducts" />
        <fmt:message bundle="${loc}" key="locale.goMain" var="goMain" />
        <fmt:message bundle="${loc}" key="locale.id" var="id" />
        <fmt:message bundle="${loc}" key="locale.name" var="name" />
        <fmt:message bundle="${loc}" key="locale.description" var="description" />
        <fmt:message bundle="${loc}" key="locale.price" var="price" />
        <fmt:message bundle="${loc}" key="locale.type" var="type" />
        <fmt:message bundle="${loc}" key="locale.addBascket" var="addBascket" />


<title>
    <c:out value = "${menu}" />
</title>

</head>

 <body>
    <c:set var="error" value="${param.message}"/>
    <h1>${menu}</h1>

     <!-- LOCALE -->
                <div>
                        <form method="post" action="/menu" >
                                       <input type="hidden" name="command" value="locale"/>
                                       <input type="hidden" name="locale" value="en">
                                       <button type="submit">${en_button}</button>
                        </form>

                        <form method="post" action="/menu" >
                                       <input type="hidden" name="command" value="locale"/>
                                       <input type="hidden" name="locale" value="ru">
                                       <button type="submit">${ru_button}</button>
                        </form>

                </div>

                <!--Errors-->

                  <c:if test="${error == 'ShowProductsError'}">
                       <p style="color: red">${errorMsg_showProductsError}</p>
                  </c:if>


                  <!--Menu-->

                      <c:if test="${sessionScope.role != null}">
                      <div>
                          <form id="show_products" method="get" action="/menu" >
                              <input type="hidden" name="command" value="showproducts"/>
                              <button form="show_products" type="submit">${showProducts}</button>
                          </form>

                          <form id="gotomain" method="get" action="/main" >
                              <input type="hidden" name="command" value="gotomain"/>
                              <button form="gotomain" type="submit">${goMain}</button>
                          </form>
                      </div>


                         <div>
                             <table>
                                 <thead>
                                 <tr>
                                 <c:if test="${requestScope.page == 'show'}">
                                     <td><h4><c:out value="${id}"/></h4></td>
                                     <td><h4><c:out value="${name}"/></h4></td>
                                     <td><h4><c:out value="${type}"/></h4></td>
                                     <td><h4><c:out value="${description}"/></h4></td>
                                     <td><h4><c:out value="${price}"/></h4></td>
                                 </c:if>
                                 </tr>
                                 </thead>

                                 <tbody>
                                 <c:forEach items="${requestScope.pageable.elements}" var="product">
                                     <tr>
                                         <td>${product.id}</td>
                                         <td>${product.name}</td>
                                         <td>${product.type}</td>
                                         <td>${product.description}</td>
                                         <td>${product.price}</td>
                                         <td>
                                         <td>
                                         <div>
                                         <form method="post" action="/menu" >
                                         <input type="hidden" name="command" value="addtobasket"/>
                                         <input type="number" min = "1" max = "10" size = "1" name="amount" value="1"/>
                                         <button type="button">${addBascket}</button><br/>
                                         </form>
                                         </div>
                                         </td>
                                     </tr>
                                 </c:forEach>
                                 </tbody>
                             </table>
                             <div style="margin-left: center">
                                 <c:forEach begin="1" end="${Math.ceil(pageable.totalElements / pageable.limit)}" var="i">
                                     <c:if test="${i == pageable.pageNumber}">
                                         <span>
                                             <button style="color:red" form="show_products" type="submit" name="currentPage" value="${i}">${i}</button>
                                         </span>
                                     </c:if>
                                     <c:if test="${i != pageable.pageNumber}">
                                         <span>
                                             <button form="show_products" type="submit" name="currentPage" value="${i}">${i}</button>
                                         </span>
                                     </c:if>
                                 </c:forEach>
                             </div>
                         </div>
                     </c:if>