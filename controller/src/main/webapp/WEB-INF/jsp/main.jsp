<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
			<html>

			<head>
				<style type="text/css">
				body{
                	background: url('https://pbs.twimg.com/media/EGMuSK-WoAIRuRQ.jpg') top left;
                	margin:0
                }
				.message {
                     border: 2px solid green;
                              color: green;
                              width:fit-content;
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
                 div form {
                	 display:inline-block;
                }

                div.header{
                display:inline-block;
                position:relative;
                top:20px;
                left:550px;
                }
                 .message {
                	 width:fit-content;
                	 margin: 5px;
                	 padding: 12px 12px 12px 12px;
                	 color: #333;
                	 border-radius: 2px;
                	 background: #fff;
                	 position: fixed;
                	 top:20px;
                	 left:170px;
                	 font-weight: bold;
                	 font-family: Verdana, sans-serif;
                	 box-sizing: border-box;
                	 border: 2px solid green;
                	 color: green;
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
.slideshow {
  list-style-type: none;
}

/** SLIDESHOW **/
.slideshow,
.slideshow:after {
    top: 80px; /*Not sure why I needed this fix*/
		position: fixed;
    width: 100%;
    height: 100%;
    left: 0px;
    z-index: 0;
}

.slideshow li span {
		position: absolute;
    width: 100%;
    height: 100%;
    top: 0px;
    left: 0px;
    color: transparent;
    background-size: cover;
    background-position: 50% 50%;
    background-repeat: no-repeat;
    opacity: 0;
    z-index: 0;
    animation: imageAnimation 24s linear infinite 0s;
}



.slideshow li:nth-child(1) span {
    background-image: url("https://www.eluniversal.com.mx/sites/default/files/food-g16e1721aa_1920.jpg");
}
.slideshow li:nth-child(2) span {
    background-image: url("https://kartinkin.net/uploads/posts/2021-07/1625211342_33-kartinkin-com-p-pitstsa-margarita-yeda-krasivo-foto-46.jpg");
    animation-delay: 6s;
}
.slideshow li:nth-child(3) span {
    background-image: url("https://catherineasquithgallery.com/uploads/posts/2021-02/1614386844_6-p-pitstsa-na-svetlom-fone-8.jpg");
    animation-delay: 12s;
}
.slideshow li:nth-child(4) span {
    background-image: url("https://zhiznsovkusom.ru/wp-content/uploads/2021/09/rabstol_net_pizza_28.jpg");
    animation-delay: 18s;
}



@keyframes imageAnimation {
    0% { opacity: 0; animation-timing-function: ease-in; }
    8% { opacity: 1; animation-timing-function: ease-out; }
    17% { opacity: 1 }
    25% { opacity: 0 }
    100% { opacity: 0 }
}


@keyframes titleAnimation {
    0% { opacity: 0 }
    8% { opacity: 1 }
    17% { opacity: 1 }
    19% { opacity: 0 }
    100% { opacity: 0 }
}


.no-cssanimations .cb-slideshow li span {
	opacity: 1;
}



 button.but2:hover{
	background:red;
	color:#fff
}
div.admin{
position:relative;
width:fit-content;
top:50px;
padding:20px;
border:2px solid gray;
left:50px;
background:white;
}
 .n-warning {
         border: 2px solid red;
         color: red;
     }
     .n-warning {
         width:fit-content;
         margin: 30px;
         padding: 12px 12px 12px 12px;
         color: #333;
         border-radius: 2px;
         background: #fff;
         position: fixed;
         top:0px;
         left:170px;
         font-weight: bold;
         font-family: Verdana, sans-serif;
         box-sizing: border-box;
     }

				</style>
				<fmt:setLocale value="${sessionScope.locale}" />
				<fmt:setBundle basename="localization.local" var="loc" />
				<fmt:message bundle="${loc}" key="locale.locbutton.name.en" var="en_button" />
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
				<fmt:message bundle="${loc}" key="locale.orderId" var="orderId" />
				<fmt:message bundle="${loc}" key="locale.productId" var="productId" />
				<fmt:message bundle="${loc}" key="locale.name" var="name" />
				<fmt:message bundle="${loc}" key="locale.description" var="description" />
				<fmt:message bundle="${loc}" key="locale.price" var="price" />
				<fmt:message bundle="${loc}" key="locale.type" var="type" />
				<fmt:message bundle="${loc}" key="locale.paidOrders" var="paidOrders" />
				<fmt:message bundle="${loc}" key="locale.getAll" var="getAll" />
				<fmt:message bundle="${loc}" key="locale.getAllProd" var="getAllProd" />
				<fmt:message bundle="${loc}" key="locale.hideProd" var="hideProd" />
				<fmt:message bundle="${loc}" key="locale.isAvailable" var="isAvailable" />
				<fmt:message bundle="${loc}" key="locale.AllOrders" var="AllOrders" />
				<fmt:message bundle="${loc}" key="locale.AllProducts" var="AllProducts" />
				<fmt:message bundle="${loc}" key="locale.name" var="name" />
				<fmt:message bundle="${loc}" key="locale.description" var="description" />
				<fmt:message bundle="${loc}" key="locale.price" var="price" />
				<fmt:message bundle="${loc}" key="locale.type" var="type" />
				<fmt:message bundle="${loc}" key="locale.changeStatus" var="changeStatus" />
				<fmt:message bundle="${loc}" key="locale.deleteFromMenu" var="deleteFromMenu" />
				<fmt:message bundle="${loc}" key="locale.Update" var="Update" />
				<title>
					<c:out value="${main}" />
				</title>
			</head>

			<body>
				 <body>
                    <c:set var="error" value="${param.message}"/>


                            <!-- LOCALE -->
                            <div class="local">
                                    <form method="post" action="/main" >
                                                   <input type="hidden" name="command" value="locale"/>
                                                   <input type="hidden" name="locale" value="en">
                                                   <button class="loc" type="submit">${en_button}</button>
                                    </form>

                                    <form method="post" action="/main" >
                                                   <input type="hidden" name="command" value="locale"/>
                                                   <input type="hidden" name="locale" value="ru">
                                                   <button class="loc" type="submit">${ru_button}</button>
                                    </form>

<c:if test="${requestScope.message != null}">
                        <p class="message">${requestScope.message}</p>
                        </c:if>

                         <c:if test="${error == 'MenuError'}">
                                                             <p class="n-warning">${errorMsg_menuError}</p>
                                                             </c:if>

                                                             <c:if test="${error == 'HideProductError'}">
                                                             <p class="n-warning">HideProductError!</p>
                                                             </c:if>

                                                             <c:if test="${error == 'AddAdminError'}">
                                                             <p class="n-warning">AddAdminError!</p>
                                                             </c:if>

                                                             <c:if test="${error == 'SessionError'}">
                                                             <p class="n-warning">SessionError!</p>
                                                             </c:if>

                                                             <c:if test="${error == 'GetPaidOrdersError'}">
                                                             <p class="n-warning">GetPaidOrdersError!</p>
                                                             </c:if>

                                                             <c:if test="${error == 'AccountError'}">
                                                             <p class="n-warning">${errorMsg_accountError}</p>
                                                             </c:if>

                                                             <c:if test="${error == 'FindUserError'}">
                                                             <p class="n-warning">FindUserError!</p>
                                                             </c:if>

                                                             <c:if test="${error == 'BasketError'}">
                                                             <p class="n-warning">${errorMsg_basketError}</p>
                                                             </c:if>

                                                             <c:if test="${error == 'UserNotExists'}">
                                                             <p class="n-warning">UserNotExists!</p>
                                                             </c:if>

                                                             <c:if test="${error == 'GetAllOrdersError'}">
                                                             <p class="n-warning">GetAllOrdersError!</p>
                                                             </c:if>

                                                             <c:if test="${error == 'ProductNotExists'}">
                                                             <p class="n-warning">ProductNotExists!</p>
                                                             </c:if>

                                                             <c:if test="${error == 'UpdateUserError'}">
                                                             <p class="n-warning">UpdateUserError!</p>
                                                             </c:if>

                                                             <c:if test="${error == 'FindProductError'}">
                                                             <p class="n-warning">FindProductError!</p>
                                                             </c:if>

                                                             <c:if test="${error == 'ChangeProductStatusError'}">
                                                             <p class="n-warning">ChangeProductStatusError!</p>
                                                             </c:if>

                                                             <c:if test="${error == 'productNotExists'}">
                                                             <p class="n-warning">ProductNotExists!</p>
                                                             </c:if>

                                                             <c:if test="${error == 'IncompleteInfo'}">
                                                             <p class="n-warning">Incomplete Info!</p>
                                                             </c:if>

                                                             <c:if test="${error == 'InvalidPriceError'}">
                                                             <p class="n-warning">InvalidPriceError!</p>
                                                             </c:if>
                                </div>





                            <c:if test="${sessionScope.role != null}">

                                     <div class="header">

                                     <form method="get" action="/menu" >
                                     <input type="hidden" name="command" value="showproducts"/>
                                     <button class="but1" type="submit">${menu}</button>
                                     </form>

                                     <c:if test = "${sessionScope.role != 'admin'}">


                                     <form method="get" action="/basket" >
                                     <input type="hidden" name="command" value="showbasket"/>
                                     <button class="but1" type="submit">${basket}</button>
                                     </form>

                                     </c:if>

                                     <form method="get" action="/profile" >
                                     <input type="hidden" name="command" value="gotoprofile"/>
                                     <button class="but1" type="submit">${account}</button>
                                     </form>

                                      <form method="get" action="/" >
                                      <input type="hidden" name="command" value="logout"/>
                                      <button class="but1" type="submit">${logout}</button>
                                      </form>

                                     </div>

                                     <c:if test = "${sessionScope.role != 'admin'}">
                                     <ul class="slideshow">
                                     	<li><span></span></li>
                                       <li><span></span></li>
                                       	<li><span></span></li>
                                     	<li><span></span></li>
                                     </ul>
                                     </c:if>

                                     <c:if test = "${sessionScope.role == 'admin'}">

                                    <div class="admin">
                                     <form method="get" action="/addProduct" >
                                     <input type="hidden" name="command" value="gotoAddProductPage"/>
                                     <button class="but2" type="submit">${add}</button>
                                     </form>

                                     <br>
                                      <form method="get" action="/main" >
                                      <input type="hidden" name="command" value="writeToCSV"/>
                                      <input type="text" name="path" placeholder="path" value=""/>
                                      <button class="but2" type="submit">Write products to csv</button>
                                      </form>
                                     <br>

                                     <form method="post" action="/profile" >
                                     <input type="hidden" name="command" value="findUserById"/>
                                     <input type="text" name="id" placeholder="id" value=""/>
                                     <button  class="but2" type="submit">${findUserById}</button>
                                     </form>

                                        <br>

                                     <form method="post" action="/main" >
                                     <input type="hidden" name="command" value="addAdmin"/>
                                     <input type="text" name="admin_id" placeholder="id" value=""/>
                                     <button class="but2" type="submit">${add_admin}</button>
                                     </form>

                                        <br>

                                     <div>
                                     <form method="post" action="/main" >
                                     <input type="hidden" name="command" value="findProductById"/>
                                     <input type="text" name="productId" placeholder="id" value=""/>
                                     <button class="but2" type="submit">${findProductById}</button>
                                     </form>

                                     <c:if test="${sessionScope.page == 'findProduct'}">
                                     <form method="get" action="/main" >
                                     <input type="hidden" name="command" value="hideProduct"/>
                                     <button class="but2" type="submit">${hideProd}</button>
                                     </form>
                                     </c:if>
                                     </div>


                                     </c:if>


                                     <c:if test="${sessionScope.role == 'admin'}">
                                        <c:if test="${sessionScope.page == 'findProduct'}">
                                            <p>Product name: ${sessionScope.product.name}</p>
                                            <p>Product price: ${sessionScope.product.description}</p>
                                            <p>Product type: ${sessionScope.product.price}</p>
                                            <p>Product description: ${sessionScope.product.type}</p>
                                            <p>Product status is available: ${sessionScope.product.isAvailable}</p>
                                        </c:if>
                                     </c:if>

                                     <!--Show Paid Orders -->
                                     <c:if test="${sessionScope.role == 'admin'}">

                                     <form id="getPaidOrders" method="get" action="/main" >
                                     <input type="hidden" name="command" value="getPaidOrders"/>
                                     <button class="but2" form="getPaidOrders"  type="submit">${getPaid}</button>
                                     </form>

                                         <c:if test="${requestScope.page == 'show'}">

                                        <div>
                                             <table>
                                                <thead>
                                                    <tr>

                                                        <p>${paidOrders}</p>
                                                            <td><h4><c:out value="${orderId}"/></h4></td>
                                                            <td><h4><c:out value="${productId}"/></h4></td>
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
                                                            <td>${order.productId}</td>
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
                                                            <input type="text" name="status" value="new status"/>
                                                            <input type="hidden" name="orderId" value=${order.id}/>
                                                            <button class="but2" type="submit">${changeStatus}</button><br/>
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
                                                             <button class="but2" form="getPaidOrders" type="submit" name="currentPage" value="${i}">${i}</button>
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
                                      <button class="but2" form="getAllOrders" type="submit">${getAll}</button>
                                      </form>

                                        <c:if test="${requestScope.page == 'showAll'}">
                                         <div>
                                              <table>
                                                 <thead>
                                                     <tr>

                                                         <p>${AllOrders}</p>
                                                             <td><h4><c:out value="${orderId}"/></h4></td>
                                                             <td><h4><c:out value="${productId}"/></h4></td>
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
                                                             <td>${order.productId}</td>
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
                                                             <input type="text" name="status" value="new status"/>
                                                             <input type="hidden" name="orderId" value=${order.id}/>
                                                             <button class="but2" type="submit">${changeStatus}</button><br/>
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
                                                              <button class="but2" form="getAllOrders" type="submit" name="currentPage" value="${i}">${i}</button>
                                                          </span>
                                                      </c:if>
                                                  </c:forEach>
                                              </div>
                                         </div>
                                         <br>
                                        </c:if>
                                      </c:if>

                                      <c:if test="${sessionScope.role == 'admin'}">
                                      <!--Show All Products -->

                                       <form id="getAllProducts" method="get" action="/main" >
                                       <input type="hidden" name="command" value="getAllProducts"/>
                                       <button class="but2" form="getAllProducts" type="submit">${getAllProd}</button>
                                       </form>

                                         <c:if test="${requestScope.page == 'showProducts'}">
                                          <div>
                                               <table>
                                                  <thead>
                                                      <tr>
                                                          <p>${AllProducts}</p>
                                                              <td><h4><c:out value="${id}"/></h4></td>
                                                              <td><h4><c:out value="${name}"/></h4></td>
                                                              <td><h4><c:out value="${description}"/></h4></td>
                                                              <td><h4><c:out value="${type}"/></h4></td>
                                                              <td><h4><c:out value="${price}"/></h4></td>
                                                              <td><h4><c:out value="${isAvailable}"/></h4></td>
                                                              <td><h4><c:out value="URL"/></h4></td>
                                                      </tr>
                                                  </thead>

                                                  <tbody>
                                                      <c:forEach items="${requestScope.pageable3.elements}" var="product">
                                                          <tr>
                                                          <div>
                                                          <form method="post" action="/main" >
                                                           <input type="hidden" name="command" value="updateProduct"/>
                                                           <input type="hidden" name="productId" value=${product.id}/>
                                                           <input type="hidden" name="status" value=${product.isAvailable}/>
                                                              <td>${product.id}</td>
                                                              <td><input type="text" id = "form_name"  name="name" value="${product.name}"/></td>
                                                              <td><input type="text" id = "form_description" name="description" value="${product.description}"/></td>
                                                              <td><input type="text" id = "form_type" name="type" value="${product.type}"/></td>
                                                              <td><input type="text" id = "form_price" name="price" value="${product.price}"/></td>
                                                              <td>${product.isAvailable}</td>
                                                              <td><input type="text" id = "form_image" name="image" value=""/></td>
                                                              <td><button class="but2" type="submit">${Update}</button>
                                                           </form>



                                                              <form method="post" action="/main" >
                                                              <input type="hidden" name="command" value="deletefrommenu"/>
                                                              <input type="hidden" name="productId" value=${product.id}/>
                                                              <button class="but2" type="submit">${deleteFromMenu}</button><br/>
                                                              </form>



                                                              <form method="post" action="/main" >
                                                              <input type="hidden" name="command" value="changeproductstatus"/>
                                                              <input type="hidden" name="productId" value=${product.id}/>
                                                              <input type="hidden" name="status" value=${product.isAvailable}/>
                                                              <button class="but2" type="submit">${changeStatus}</button><br/>
                                                              </form>

                                                              </div>
                                                              </td>
                                                          </tr>
                                                      </c:forEach>
                                                  </tbody>
                                              </table>
                                                       <div style="margin-left: center">
                                                   <c:forEach begin="1" end="${Math.ceil(pageable3.totalElements / pageable3.limit)}" var="i">
                                                       <c:if test="${i == pageable3.pageNumber}">
                                                           <span>
                                                               <button style="color:red" form="getAllProducts" type="submit" name="currentPage" value="${i}">${i}</button>
                                                           </span>
                                                       </c:if>
                                                       <c:if test="${i != pageable3.pageNumber}">
                                                           <span>
                                                               <button form="getAllProducts" type="submit" name="currentPage" value="${i}">${i}</button>
                                                           </span>
                                                       </c:if>
                                                   </c:forEach>
                                               </div>
                                          </div>
                                         </c:if>
                                       </c:if>

</div>




                            </c:if>
                    </body>
                </html>