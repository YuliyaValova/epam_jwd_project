<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>

<head>

<style type = "text/css">

.delete {
margin-top:15px;
}


.table {

 text-align:center;
    overflow: auto;
    white-space: nowrap;
	width: 100%;
	margin-bottom: 20px;
	border: 1px solid #dddddd;
	border-collapse: collapse;
	height:fit-content;
}
.table th {
	font-weight: bold;
	padding: 5px;
	background: #efefef;
	border: 1px solid #dddddd;
}
.table td {
	border: 1px solid #dddddd;
	padding: 5px;
}

.table tr td:first-child:before {
    position:relative;

    color:#1d1f21;
    background:#c5c8c6;
}
.w3 {width:3%};
.message {
     border: 2px solid green;
              color: green;
              width:300px;
              margin: 30px;
              padding: 12px 12px 12px 12px;
              color: #333;
              border-radius: 2px;
              background: #fff;
              position: relative;
              font-weight: bold;
              font-family: Verdana, sans-serif;
              box-sizing: border-box;
          }
button.but1{
font-size:20px;
	float:right;
	padding:8px 12px;
	margin:8px 0 0;
	font-family:'Montserrat',sans-serif;
	border:2px solid #78788c;
	background:white;
	color:#5a5a6e;
	cursor:pointer;
	transition:all .3s
}
 button.but1:hover{
	background:red;
	color:#fff
}
div.header{
                display:inline-block;
                position:fixed;
                top:20px;
                left:500px;
                }

div.basket{
 display:inline-block;
 background:white;
 border:2px solid gray;
 width: fit-content;
 padding:20px;
                position:fixed;
                top:50px;
                left:20px;
}

div.orders{
 display:inline-block;
 background:white;
 border:2px solid gray;
 width: fit-content;
 padding:20px;
                position:relative;
                top:80px;
                left:20px;
}
.loc {
              font-size:20px;
            }
    .local {
        position: fixed;
             display:inline-block;
                 top: 35;
                 left: 30;
                 color:white;
                 height: 80px;
                 width: 150px;
                 z-index: 2;
        }
.n-warning {
    border: 2px solid red;
    color: red;
}
.n-warning {
    width:300px;
    margin: 5px;
    padding: 12px 12px 12px 12px;
    color: #333;
    border-radius: 2px;
    background: #fff;
    position: relative;
    font-weight: bold;
    font-family: Verdana, sans-serif;
    box-sizing: border-box;
}
body {

      height: 100%;
      background: url('https://pbs.twimg.com/media/EGMuSK-WoAIRuRQ.jpg') top left;
      }

    div form {
        display:inline-block
    }
</style>

        <fmt:setLocale value="${sessionScope.locale}" />
		<fmt:setBundle basename="localization.local" var="loc" />
		<fmt:message bundle="${loc}" key="locale.locbutton.name.en" var="en_button"/>
        <fmt:message bundle="${loc}" key="locale.locbutton.name.ru" var="ru_button" />
        <fmt:message bundle="${loc}" key="locale.basket" var="basket" />
        <fmt:message bundle="${loc}" key="locale.errorMsg_showBasketError" var="errorMsg_showBasketError" />
        <fmt:message bundle="${loc}" key="locale.errorMsg_basketError" var="errorMsg_basketError" />
        <fmt:message bundle="${loc}" key="locale.showBasket" var="showBasket" />
        <fmt:message bundle="${loc}" key="locale.goMain" var="goMain" />
        <fmt:message bundle="${loc}" key="locale.id" var="id" />
        <fmt:message bundle="${loc}" key="locale.name" var="name" />
        <fmt:message bundle="${loc}" key="locale.description" var="description" />
        <fmt:message bundle="${loc}" key="locale.price" var="price" />
        <fmt:message bundle="${loc}" key="locale.type" var="type" />
        <fmt:message bundle="${loc}" key="locale.deleteFromBascket" var="deleteFromBascket" />
        <fmt:message bundle="${loc}" key="locale.deleteAllBascket" var="deleteAllBascket" />
        <fmt:message bundle="${loc}" key="locale.sum" var="sum" />
        <fmt:message bundle="${loc}" key="locale.sendOrd" var="sendOrd" />


<title>
    <c:out value = "${basket}" />
</title>

</head>

 <body>
    <c:set var="error" value="${param.message}"/>


     <!-- LOCALE -->
                <div class="local">
                        <form method="post"  action="/basket" >
                                       <input type="hidden" name="command" value="locale"/>
                                       <input type="hidden" name="locale" value="en">
                                       <button class="loc" type="submit">${en_button}</button>
                        </form>

                        <form method="post" action="/basket" >
                                       <input type="hidden" name="command" value="locale"/>
                                       <input type="hidden" name="locale" value="ru">
                                       <button class="loc" type="submit">${ru_button}</button>
                        </form>

                         <!--Errors-->

                                          <c:if test="${error == 'BasketError'}">
                                               <p class="n-warning">${errorMsg_basketError}</p>
                                          </c:if>

                                          <c:if test="${error == 'ShowBasketError'}">
                                               <p class="n-warning">${errorMsg_showBasketError}</p>
                                          </c:if>

                                        <!--Message-->
                                        <c:if test="${requestScope.message != null}">
                                             <p class="message">${requestScope.message}</p>
                                        </c:if>


                </div>



<div class="orders">
                                                            <form id="getOrders" method="get" action="/history" >
                                                            <input type="hidden" name="command" value="getOrders"/>
                                                            <input type="hidden" name="id" value="${sessionScope.user.id}"/>
                                                             <button form="getOrders" type="submit">Refresh</button>
                                                            </form>

                                                              <div>
                                                                   <table class="table">
                                                                   <caption>
                                                                    <p>Orders history</p>
                                                                   </caption>
                                                                      <thead>
                                                                          <tr>
                                                                                  <td><h4><c:out value="${orderId}"/></h4></td>
                                                                                  <td><h4><c:out value="date"/></h4></td>
                                                                                  <td><h4><c:out value="status"/></h4></td>
                                                                                  <td><h4><c:out value="totalPrice"/></h4></td>
                                                                                  <td><h4><c:out value="comment"/></h4></td>
                                                                                  <td><h4><c:out value="products"/></h4></td>
                                                                          </tr>
                                                                      </thead>

                                                                      <tbody>
                                                                          <c:forEach items="${sessionScope.pageable4.elements}" var="order">
                                                                              <tr>
                                                                                  <td>${order.orderId}</td>
                                                                                  <td>${order.date}</td>
                                                                                  <td>${order.status}</td>
                                                                                  <td>${order.totalPrice}</td>
                                                                                  <td>${order.comment}</td>
                                                                                  <td>

                                                                                  <table>
                                                                                   <tr>
                                                                                   <td><h4><c:out value="№ product"/></h4></td>
                                                                                   <td><h4><c:out value="product_amount"/></h4></td>
                                                                                   <td><h4><c:out value="item_price"/></h4></td>
                                                                                   <td><h4><c:out value="product_name"/></h4></td>
                                                                                   </tr>
                                                                                  <thead>
                                                                                  </thead>
                                                                                   <c:forEach items="${order.details}" var="detail">

                                                                                     <tr>
                                                                                      <td>${detail.orderDetailId}</td>
                                                                                      <td>${detail.productAmount}</td>
                                                                                      <td>${detail.itemPrice}</td>

                                                                                      <td>${detail.productName}</td>
                                                                                     </tr>
                                                                                     </c:forEach>

                                                                                  </table>
                                                                                  </td>

                                                                              </tr>
                                                                          </c:forEach>
                                                                      </tbody>
                                                                  </table>
                                                                           <div style="margin-left: center">
                                                                       <c:forEach begin="1" end="${Math.ceil(pageable4.totalElements / pageable4.limit)}" var="i">
                                                                           <c:if test="${i == pageable4.pageNumber}">
                                                                               <span>
                                                                                   <button style="color:red" form="getOrders" type="submit" name="currentPage" value="${i}">${i}</button>
                                                                               </span>
                                                                           </c:if>
                                                                           <c:if test="${i != pageable4.pageNumber}">
                                                                               <span>
                                                                                   <button class="but2" form="getOrders" type="submit" name="currentPage" value="${i}">${i}</button>
                                                                               </span>
                                                                           </c:if>
                                                                       </c:forEach>
                                                                   </div>
                                                              </div>

                                                              </div>
                                                              <br>