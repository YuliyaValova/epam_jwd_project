<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>

<head>

<style type = "text/css">
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
                top:150px;
                left:400px;
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
                        <form method="post" action="/basket" >
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



                  <!--Menu-->

                      <c:if test="${sessionScope.role != null}">
                      <div class="header">
                          <form id="showbasket" method="get" action="/basket" >
                              <input type="hidden" name="command" value="showbasket"/>
                              <button class="but1" form="showbasket" type="submit">${showBasket}</button>
                          </form>

                          <form id="cleanbasket" method="get" action="/basket" >
                              <input type="hidden" name="command" value="cleanbasket"/>
                              <button class="but1" form="cleanbasket" type="submit">${deleteAllBascket}</button>
                          </form>

                          <form id="gotomain" method="get" action="/main" >
                              <input type="hidden" name="command" value="gotomain"/>
                              <button class="but1" form="gotomain" type="submit">${goMain}</button>
                          </form>
                      </div>


                         <div class="basket">
                             <table>
                                 <thead>
                                 <tr>

                                     <td><h4><c:out value="${id}"/></h4></td>
                                     <td><h4><c:out value="${name}"/></h4></td>
                                     <td><h4><c:out value="${type}"/></h4></td>
                                     <td><h4><c:out value="${description}"/></h4></td>
                                     <td><h4><c:out value="${price}"/></h4></td>

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
                                         <form method="post" action="/basket" >
                                         <input type="hidden" name="command" value="deletefrombasket"/>
                                         <input type="hidden" name="productId" value=${product.id}/>
                                         <button type="submit">${deleteFromBascket}</button><br/>
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
                                             <button form="showbasket" type="submit" name="currentPage" value="${i}">${i}</button>
                                         </span>
                                     </c:if>
                                 </c:forEach>


                         </div>


                               <p>${sum}: ${sessionScope.sum}</p>
                                <form id="sendOrder" method="get" action="/basket" >
                                <input type="hidden" name="command" value="sendOrder"/>
                                <button form="sendOrder" type="submit">${sendOrd}</button>
                                </form>


                        </div>
                     </c:if>