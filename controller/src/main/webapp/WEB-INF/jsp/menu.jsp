<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>

<head>

<style type = "text/css">
@import url("https://fonts.googleapis.com/css2?family=Be+Vietnam+Pro:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap");





.service-section {

    background-color:black;
    width:500px;
    height:0px;
    display:inline-block;
    position:relative;
    left:34%;
    top:-20px;
}

.service-section-header {
	display: flex;
	justify-content: space-between;
	width:500px;
	height:fit-content;
}


.search-field {
	display: flex;
	flex-grow: 1;
	position: relative;
	input {
		width: 100%;
		padding-top: 0.5rem;
		padding-bottom: 0.5rem;
		border: 0;
		border-bottom: 1px solid green;
		background-color: transparent;
		padding-left: 1.5rem;
	}

	i {
		position: absolute;
		left: 0;
		top: 50%;
		transform: translateY(-50%);
	}
}

.dropdown-field {
	display: flex;
	flex-grow: 1;
	position: relative;

}

.flat-button {
    text-align:center;
	border-radius: 6px;
	background-color: white;
	padding: 0.5em 1.0em;
	border-bottom: 1px solid red;
}

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

.number {
	display:inline-block;
	 margin: 0 auto;
	position: relative;
	width: 100px;

}
.number input[type="number"] {
	display: block;
	height: 32px;
	line-height: 32px;
	width: 100%;
	padding: 0;
	margin: 0;
	box-sizing: border-box;
	text-align: center;
	-moz-appearance: textfield;
	-webkit-appearance: textfield;
	appearance: textfield;
}
.number input[type="number"]::-webkit-outer-spin-button,
.number input[type="number"]::-webkit-inner-spin-button {
	display: none;
}
.number-minus {
	position: absolute;
	top: 1px;
	left: 1px;
	bottom: 1px;
	width: 20px;
	padding: 0;
	display: block;
	text-align: center;
	border: none;
	border-right: 1px solid #ddd;
	font-size: 16px;
	font-weight: 600;
}
.number-plus {
	position: absolute;
	top: 1px;
	right: 1px;
	bottom: 1px;
	width: 20px;
	padding: 0;
	display: block;
	text-align: center;
	border: none;
	border-left: 1px solid #ddd;
	font-size: 16px;
	font-weight: 600;
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
                position:relative;
                top:20px;
                left:620px;
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

.menu {
        position:relative;
        top:90px;
        display:flex;
      justify-content:space-between;

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
    * {
       box-sizing: border-box;
    }
    .product-item {
       width: 300px;
       text-align: center;
       margin: 0 auto;
       padding:15px;
       border: 2px solid grey;
       background: white;
       font-family: "Open Sans";
       transition: .3s ease-in;
    }
    .pages{
            text-align: center;
            margin: 0 auto;
            padding:15px;
            position:relative;
            top:100px;
    }
    .product-item:hover {
       border-bottom: 2px solid #fc5a5a;
    }
    .product-item img {
       display: block;
       width: 100%;
    }
    .product-list {
       background: #fafafa;
       padding: 15px 0;
    }

    .price {
       font-size: 16px;
       color: #fc5a5a;
       display: block;
       margin-bottom: 12px;
    }

    .line{
      display:flex;
          justify-content:space-between;
    }




    .name {
      font-size: 20px;
             color: #fc5a5a;
             display: block;
             margin-bottom: 12px;
             text-decoration-line:underline;
             font-weight:bold;
    }

     .description {
           font-size: 16px;
           color: black;
           display: block;
           margin-bottom: 12px;
     }


    .button {
       text-decoration: none;
       display: inline-block;
       padding: 0 12px;
       background: #cccccc;
       color: white;
       text-transform: uppercase;
       font-size: 12px;
       line-height: 28px;
       transition: .3s ease-in;
    }
    .product-item:hover .button {
       background: #fc5a5a;
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
        <fmt:message bundle="${loc}" key="locale.changeStatus" var="changeStatus" />
        <fmt:message bundle="${loc}" key="locale.addBascket" var="addBascket" />
        <fmt:message bundle="${loc}" key="locale.deleteFromMenu" var="deleteFromMenu" />
        <fmt:message bundle="${loc}" key="locale.errorMsg_orderExistsError" var="errorMsg_orderExistsError" />


<title>
    <c:out value = "${menu}" />
</title>

</head>

 <body>


        <c:set var="error" value="${param.message}"/>



     <!-- LOCALE -->
                <div class="local">
                        <form method="post" action="/menu" >
                                       <input type="hidden" name="command" value="locale"/>
                                       <input type="hidden" name="locale" value="en">
                                       <button class="loc" type="submit">${en_button}</button>
                        </form>

                        <form method="post" action="/menu" >
                                       <input type="hidden" name="command" value="locale"/>
                                       <input type="hidden" name="locale" value="ru">
                                       <button class="loc" type="submit">${ru_button}</button>
                        </form>

                         <!--Errors-->

                                          <c:if test="${error == 'ShowProductsError'}">
                                               <p class="n-warning">${errorMsg_showProductsError}</p>

                                          </c:if>

                                          <c:if test="${error == 'OrderExists'}">
                                               <p class="n-warning">${errorMsg_orderExistsError}</p>

                                          </c:if>

                                          <c:if test="${requestScope.message != null}">
                                                <p class="message">${requestScope.message}</p>

                                          </c:if>

                                          <c:if test="${error == 'DeletingError'}">
                                          <p class="n-warning">DeletingError!</p>

                                          </c:if>

                </div>





                  <!--Menu-->

                      <c:if test="${sessionScope.role != null}">
                      <div class="header">
                          <form id="show_products" method="get" action="/menu" >
                              <input type="hidden" name="command" value="showproducts"/>
                              <button  class="but1" form="show_products" type="submit">${showProducts}</button>
                          </form>

                          <form id="gotomain" method="get" action="/main" >
                              <input type="hidden" name="command" value="gotomain"/>
                              <button  class="but1" form="gotomain" type="submit">${goMain}</button>
                          </form>
                      </div>

<br>
<br>
<br>
<div class="service-section">
				<div class="service-section-header">

					<div class="dropdown-field">
                    <form method="post" action="/menu" >
                    <input type="hidden" name="command" value="pizzafilter"/>
                    <button class = "flat-button" type="submit">Pizza</button><br/>
                    </div>

					<div class="dropdown-field">
                    <form method="post" action="/menu" >
                    <input type="hidden" name="command" value="colddrinksfilter"/>
                    <button class = "flat-button" type="submit">Cold drinks</button><br/>
                    </div>

					<div class="dropdown-field">
                    <form method="post" action="/menu" >
                    <input type="hidden" name="command" value="hotdrinksfilter"/>
                    <button class = "flat-button" type="submit">Hot drinks</button><br/>
                    </div>

					<div class="dropdown-field">
                    <form method="post" action="/menu" >
                    <input type="hidden" name="command" value="bakeryfilter"/>
                    <button class = "flat-button" type="submit">Bakery</button><br/>
                    </div>

				</div>
				<br>
				<div class="service-section-header">
                    <form class="card-form" method="post" action="/menu" >
                    <input type="hidden" name="command" value="sort"/>
					<div class="dropdown-field">
						<select name="sort_type" class="input-field" required="required">
							<option value="name">Name</option>
							<option value="price">Price</option>
						</select>
						<i class="ph-caret-down"></i>
					</div>
					<div class="dropdown-field">
						<select name="sort_direction" class="input-field" required="required">
							<option value="asc">Ascending</option>
							<option value="desc">Descending</option>
						</select>
						<i class="ph-caret-down"></i>
					</div>
					<button class="flat-button" type="submit">
						Sort
					</button>
					<div class="search-field">
					    <form class="card-form" method="post" action="/menu" >
                        <input type="hidden" name="command" value="search"/>
                    		<i class="ph-magnifying-glass"></i>
                    		<input type="text" placeholder="" value="">
                    	<button class="flat-button" type="submit">
                    		Search
                    	</button>

                    </div>
				</div>


</div>



                                <div class="menu">
                                 <c:forEach items="${requestScope.pageable.elements}" var="product">
                                      <div class="product-item">
                                       <img src=${product.image}>
                                       <div class="product-list">
                                         <div class="name">${product.name}</div>
                                           <span class="description">${product.description}</span>
                                           <span class="price">${product.price}</span>
                                           <c:if test="${sessionScope.role != 'admin'}">
                                            <form method="post" action="/menu" >
                                            <input type="hidden" name="command" value="addtobasket"/>
                                            <div class="line">
                                           <div class="number">
                                           	<button class="number-minus" type="button" onclick="this.nextElementSibling.stepDown(); this.nextElementSibling.onchange();">-</button>
                                           	<input type="number"  name="counter" min="1" max="10" value="1">
                                           	<button class="number-plus" type="button" onclick="this.previousElementSibling.stepUp(); this.previousElementSibling.onchange();">+</button>
                                           </div>
                                           <br>
                                           </div>
                                            <input type="hidden" name="productId" value=${product.id}/>
                                            <input type="hidden" name="price" value=${product.price}/>
                                            <br>
                                            <button class="button" type="submit">${addBascket}</button><br/>
                                            </form>
                                            </c:if>
                                            <c:if test="${sessionScope.role == 'admin'}">
                                             <form method="post" action="/menu" >
                                             <input type="hidden" name="command" value="deletefrommenu"/>
                                             <input type="hidden" name="productId" value=${product.id}/>
                                             <button type="submit">${deleteFromMenu}</button><br/>
                                             </form>
                                             <form method="post" action="/menu" >
                                             <input type="hidden" name="command" value="changeproductstatus"/>
                                             <input type="hidden" name="productId" value=${product.id}/>
                                             <input type="hidden" name="status" value=${product.isAvailable}/>
                                             <button type="submit">${changeStatus}</button><br/>
                                             </form>
                                             </c:if>

                                       </div>
                                    </div>


<br>




                                 </c:forEach>

                                 </div>


                             <div class="pages">
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

                     </c:if>