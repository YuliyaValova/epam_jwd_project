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
        <fmt:message bundle="${loc}" key="locale.main" var="main" />
        <fmt:message bundle="${loc}" key="locale.logout" var="logout" />
        <fmt:message bundle="${loc}" key="locale.menu" var="menu" />
        <fmt:message bundle="${loc}" key="locale.account" var="account" />
        <fmt:message bundle="${loc}" key="locale.basket" var="basket" />
        <fmt:message bundle="${loc}" key="locale.add" var="add" />
        <fmt:message bundle="${loc}" key="locale.getPaid" var="getPaid" />
        <fmt:message bundle="${loc}" key="locale.getAll" var="getAll" />
        <fmt:message bundle="${loc}" key="locale.findUserById" var="findUserById" />
        <fmt:message bundle="${loc}" key="locale.findProductById" var="findProductById" />
        <fmt:message bundle="${loc}" key="locale.add_admin" var="add_admin" />
        <fmt:message bundle="${loc}" key="locale.changeStatus" var="changeStatus" />
        <fmt:message bundle="${loc}" key="locale.userId" var="userId" />
        <fmt:message bundle="${loc}" key="locale.status" var="status" />
        <fmt:message bundle="${loc}" key="locale.errorMsg_menuError" var="errorMsg_menuError" />
        <fmt:message bundle="${loc}" key="locale.errorMsg_accountError" var="errorMsg_accountError" />
        <fmt:message bundle="${loc}" key="locale.errorMsg_basketError" var="errorMsg_basketError" />
        <fmt:message bundle="${loc}" key="locale.id" var="id" />
        <fmt:message bundle="${loc}" key="locale.name" var="name" />
        <fmt:message bundle="${loc}" key="locale.description" var="description" />
        <fmt:message bundle="${loc}" key="locale.price" var="price" />
        <fmt:message bundle="${loc}" key="locale.type" var="type" />
        <fmt:message bundle="${loc}" key="locale.paidOrders" var="paidOrders" />
        <fmt:message bundle="${loc}" key="locale.getAll" var="getAll" />
        <fmt:message bundle="${loc}" key="locale.AllOrders" var="AllOrders" />


<title>
    <c:out value = "${main}" />
</title>

</head>


    <body>
    <c:set var="error" value="${param.message}"/>

            <h1>${main}</h1>
            <!-- LOCALE -->
            <div>
                    <form method="post" action="/main" >
                                   <input type="hidden" name="command" value="locale"/>
                                   <input type="hidden" name="locale" value="en">
                                   <button type="submit">${en_button}</button>
                    </form>

                    <form method="post" action="/main" >
                                   <input type="hidden" name="command" value="locale"/>
                                   <input type="hidden" name="locale" value="ru">
                                   <button type="submit">${ru_button}</button>
                    </form>

                </div>

        <c:if test="${requestScope.message != null}">
        <p style="color: green">${requestScope.message}</p>
        </c:if>



            <c:if test="${sessionScope.role != null}">

                     <div>

                     <form method="get" action="/menu" >
                     <input type="hidden" name="command" value="gotomenu"/>
                     <button type="submit">${menu}</button>
                     </form>

                     <c:if test = "${sessionScope.role != 'admin'}">

                     <form method="get" action="/basket" >
                     <input type="hidden" name="command" value="gotobasket"/>
                     <button type="submit">${basket}</button>
                     </form>

                     </c:if>

                     <form method="get" action="/profile" >
                     <input type="hidden" name="command" value="gotoprofile"/>
                     <button type="submit">${account}</button>
                     </form>

                      <form method="get" action="/" >
                      <input type="hidden" name="command" value="logout"/>
                      <button type="submit">${logout}</button>
                      </form>

                     </div>

                     <c:if test = "${sessionScope.role == 'admin'}">

                     <form method="get" action="/addProduct" >
                     <input type="hidden" name="command" value="gotoAddProductPage"/>
                     <button type="submit">${add}</button>
                     </form>

                     <form method="post" action="/main" >
                     <input type="hidden" name="command" value="findUserById"/>
                     <input type="text" name="id" value="id"/>
                     <button type="submit">${findUserById}</button>
                     </form>

                     <form method="post" action="/main" >
                     <input type="hidden" name="command" value="addAdmin"/>
                     <input type="text" name="admin_id" value="id"/>
                     <button type="submit">${add_admin}</button>
                     </form>

                     <form method="post" action="/main" >
                     <input type="hidden" name="command" value="findProductById"/>
                     <input type="text" name="product_id" value="id"/>
                     <button type="submit">${findProductById}</button>
                     </form>

                     </c:if>


                     <!--Show Paid Orders -->
                     <c:if test="${sessionScope.role == 'admin'}">

                     <form id="getPaidOrders" method="get" action="/main" >
                     <input type="hidden" name="command" value="getPaidOrders"/>
                     <button form="getPaidOrders" type="submit">${getPaid}</button>
                     </form>

                         <c:if test="${requestScope.page == 'show'}">

                        <div>
                             <table>
                                <thead>
                                    <tr>

                                        <p>${paidOrders}</p>
                                            <td><h4><c:out value="${id}"/></h4></td>
                                            <td><h4><c:out value="${name}"/></h4></td>
                                            <td><h4><c:out value="${type}"/></h4></td>
                                            <td><h4><c:out value="${description}"/></h4></td>
                                            <td><h4><c:out value="${price}"/></h4></td>
                                            <td><h4><c:out value="${userId}"/></h4></td>
                                            <td><h4><c:out value="${status}"/></h4></td>

                                    </tr>
                                </thead>

                                <tbody>
                                    <c:forEach items="${requestScope.pageable.elements}" var="order">
                                        <tr>
                                            <td>${order.id}</td>
                                            <td>${order.name}</td>
                                            <td>${order.type}</td>
                                            <td>${order.description}</td>
                                            <td>${order.price}</td>
                                            <td>${order.customerId}</td>
                                            <td>${order.status}</td>
                                            <td>
                                            <td>
                                            <div>
                                            <form method="post" action="/main" >
                                            <input type="hidden" name="command" value="changeOrderStatus"/>
                                            <input type="hidden" name="orderId" value=${order.id}/>
                                            <button type="submit">${changeStatus}</button><br/>
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
                                             <button style="color:red" form="getPaidOrders" type="submit" name="currentPage" value="${i}">${i}</button>
                                         </span>
                                     </c:if>
                                     <c:if test="${i != pageable.pageNumber}">
                                         <span>
                                             <button form="getPaidOrders" type="submit" name="currentPage" value="${i}">${i}</button>
                                         </span>
                                     </c:if>
                                 </c:forEach>
                             </div>
                        </div>
                        <br>
                        </c:if>
                     </c:if>

                     <c:if test="${sessionScope.role == 'admin'}">
                     <!--Show All Orders -->

                      <form id="getAllOrders" method="get" action="/main" >
                      <input type="hidden" name="command" value="getAllOrders"/>
                      <button form="getAllOrders" type="submit">${getAll}</button>
                      </form>

                        <c:if test="${requestScope.page == 'showAll'}">
                         <div>
                              <table>
                                 <thead>
                                     <tr>

                                         <p>${AllOrders}</p>
                                             <td><h4><c:out value="${id}"/></h4></td>
                                             <td><h4><c:out value="${name}"/></h4></td>
                                             <td><h4><c:out value="${type}"/></h4></td>
                                             <td><h4><c:out value="${description}"/></h4></td>
                                             <td><h4><c:out value="${price}"/></h4></td>
                                             <td><h4><c:out value="${userId}"/></h4></td>
                                             <td><h4><c:out value="${status}"/></h4></td>

                                     </tr>
                                 </thead>

                                 <tbody>
                                     <c:forEach items="${requestScope.pageable2.elements}" var="order">
                                         <tr>
                                             <td>${order.id}</td>
                                             <td>${order.name}</td>
                                             <td>${order.type}</td>
                                             <td>${order.description}</td>
                                             <td>${order.price}</td>
                                             <td>${order.customerId}</td>
                                             <td>${order.status}</td>
                                             <td>
                                             <td>
                                             <div>
                                             <form method="post" action="/main" >
                                             <input type="hidden" name="command" value="changeOrderStatus"/>
                                             <input type="hidden" name="orderId" value=${order.id}/>
                                             <button type="submit">${changeStatus}</button><br/>
                                             </form>
                                             </div>
                                             </td>
                                         </tr>
                                     </c:forEach>
                                 </tbody>
                             </table>
                                      <div style="margin-left: center">
                                  <c:forEach begin="1" end="${Math.ceil(pageable2.totalElements / pageable2.limit)}" var="i">
                                      <c:if test="${i == pageable2.pageNumber}">
                                          <span>
                                              <button style="color:red" form="getAllOrders" type="submit" name="currentPage" value="${i}">${i}</button>
                                          </span>
                                      </c:if>
                                      <c:if test="${i != pageable2.pageNumber}">
                                          <span>
                                              <button form="getAllOrders" type="submit" name="currentPage" value="${i}">${i}</button>
                                          </span>
                                      </c:if>
                                  </c:forEach>
                              </div>
                         </div>
                        </c:if>
                      </c:if>



                     <c:if test="${sessionScope.page == 'account'}">
                     <p>Status = ${sessionScope.page} </p>
                     </c:if>

                     <c:if test="${sessionScope.page == 'basket'}">
                     <p>Status = ${sessionScope.page} </p>
                     </c:if>

                     <c:if test="${error == 'MenuError'}">
                     <p style="color: red">${errorMsg_menuError}</p>
                     </c:if>

                     <c:if test="${error == 'SessionError'}">
                     <p style="color: red">SessionError!</p>
                     </c:if>

                     <c:if test="${error == 'GetPaidOrdersError'}">
                     <p style="color: red">GetPaidOrdersError!</p>
                     </c:if>

                     <c:if test="${error == 'AccountError'}">
                     <p style="color: red">${errorMsg_accountError}</p>
                     </c:if>

                     <c:if test="${error == 'BasketError'}">
                     <p style="color: red">${errorMsg_basketError}</p>
                     </c:if>

                     <c:if test="${error == 'GetAllOrdersError'}">
                     <p style="color: red">GetAllOrdersError!</p>
                     </c:if>


            </c:if>
    </body>
</html>