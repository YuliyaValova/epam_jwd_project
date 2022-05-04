<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
    <head>
    <style type = "text/css">


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
                             width: 100%;
                             z-index: 2;
                    }

            div form{
            display:inline-block;
            }
           @import url('https://fonts.googleapis.com/css2?family=DM+Sans:wght@400;500;700&display=swap');

           *, *:after, *:before {
           	box-sizing: border-box;
           }

           body {
           	font-family: "DM Sans", sans-serif;
           	line-height: 1.5;
           	background: url('https://pbs.twimg.com/media/EGMuSK-WoAIRuRQ.jpg') top left;
           	padding: 0 2rem;
           }

           img {
           	max-width: 100%;
           	display: block;
           }


           // iOS Reset
           input {
           	appearance: none;
           	border-radius: 0;
           }

           .card {
           	margin: 2rem auto;
           	display: flex;
           	flex-direction: column;
           	width: 100%;
           	max-width: 425px;
           	background-color: #FFF;
           	border-radius: 10px;
           	box-shadow: 0 10px 20px 0 rgba(#999, .25);
           	padding: .75rem;
           }

           .card-image {
           	border-radius: 8px;
           	overflow: hidden;
           	padding-bottom: 65%;
           	background-image: url('https://catherineasquithgallery.com/uploads/posts/2021-02/1614404414_61-p-pitstsa-na-temnom-fone-81.jpg');
           	background-repeat: no-repeat;
           	background-size: 150%;
           	background-position: 0 5%;
           	position: relative;
           }
           .n-warning {
               border: 2px solid red;
               color: red;
           }
           .n-warning {
               width:fit-content;
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
           .card-heading {
           	position: absolute;
           	left: 10%;
           	top: 15%;
           	right: 10%;
           	font-size: 1.75rem;
           	font-weight: 700;
           	color: green;
           	background: white;
           	opacity:0.85;
           	line-height: 1.222;
           	small {
           		display: block;
           		font-size: .75em;
           		font-weight: 400;
           		margin-top: .25em;
           	}
           }

           .card-form {
           	padding: 2rem 1rem 0;
           }

           .input {
           	display: flex;
           	flex-direction: column-reverse;
           	position: relative;
           	padding-top: 1.5rem;
           	&+.input {
           		margin-top: 1.5rem;
           	}
           }

           .input-field {
           	border: 0;
           	z-index: 1;
           	background-color: transparent;
           	border-bottom: 2px solid #eee;
           	font: inherit;
           	font-size: 1.125rem;
           	padding: .25rem 0;
           	color: #858585
           }

           .action {
           	margin-top: 2rem;
           }


           .action-button {
           	font: inherit;
           	font-size: 1.25rem;
           	padding: 1em;
           	width: 100%;
           	font-weight: 500;
           	background-color: green;
           	border-radius: 6px;
           	color: #FFF;
           	border: 0;
           	&:focus {
           		outline: 0;
           	}
           }

           .card-info {
           	padding: 1rem 1rem;
           	text-align: center;
           	font-size: .875rem;
           	color: #8597a3;
           	a {
           		display: block;
           		color: #6658d3;
           		text-decoration: none;
           	}
           }
    </style>

            <fmt:setLocale value="${sessionScope.locale}" />
    		<fmt:setBundle basename="localization.local" var="loc" />
    		<fmt:message bundle="${loc}" key="locale.locbutton.name.en" var="en_button"/>
            <fmt:message bundle="${loc}" key="locale.locbutton.name.ru" var="ru_button" />
            <fmt:message bundle="${loc}" key="locale.goMain" var="goMain" />
            <fmt:message bundle="${loc}" key="locale.addProdPage" var="addProdPage" />
            <fmt:message bundle="${loc}" key="locale.p_name" var="p_name" />
            <fmt:message bundle="${loc}" key="locale.p_type" var="p_type" />
            <fmt:message bundle="${loc}" key="locale.p_price" var="p_price" />
            <fmt:message bundle="${loc}" key="locale.p_status" var="p_status" />
            <fmt:message bundle="${loc}" key="locale.addToMenu" var="addToMenu" />
            <fmt:message bundle="${loc}" key="locale.p_description" var="p_description" />
            <fmt:message bundle="${loc}" key="locale.errorMsg_fieldsFill" var="errorMsg_fieldsFill" />
            <fmt:message bundle="${loc}" key="locale.errorMsg_addProduct" var="errorMsg_addProduct" />
            <fmt:message bundle="${loc}" key="locale.errorMsg_productExists" var="errorMsg_productExists" />
            <fmt:message bundle="${loc}" key="locale.errorMsg_invalidPrice" var="errorMsg_invalidPrice" />

    <title>
        <c:out value = "${addProdPage}" />
    </title>
    </head>
        <body>

        <c:set var="error" value="${param.message}"/>


            <!-- LOCALE -->
                       <div class="local">
                             <form method="post" action="/addProduct" >
                                            <input type="hidden" name="command" value="locale"/>
                                            <input type="hidden" name="locale" value="en">
                                            <button class ="loc" type="submit">${en_button}</button>

                             </form>

                             <form method="post" action="/addProduct" >
                                            <input type="hidden" name="command" value="locale"/>
                                            <input type="hidden" name="locale" value="ru">
                                            <button class ="loc" type="submit">${ru_button}</button>
                             </form>



<c:if test="${error == 'IncompleteInfo'}">
            <p class="n-warning">${errorMsg_fieldsFill}</p>
            </c:if>

            <c:if test="${error == 'AddProductError'}">
            <p class="n-warning">${errorMsg_addProduct}</p>
            </c:if>

            <c:if test="${error == 'productExists'}">
            <p class="n-warning">${errorMsg_productExists}</p>
            </c:if>

            <c:if test="${error == 'InvalidPriceError'}">
            <p class="n-warning">${errorMsg_invalidPrice}</p>
            </c:if>

            </div>

             <c:if test="${sessionScope.role == 'admin'}">

              <div class="container">
                       	<!-- code here -->
                       	<div class="card">
                       		<div class="card-image">
                       			<h2 class="card-heading">
                       				${addProdPage}
                       			</h2>
                       		</div>
                       		<form class="card-form" method="post" action="/main" >
                         		<input type="hidden" name="command" value="addProductToMenu"/>
                       			<div class="input">
                       				<input type="text" name="name" placeholder=" ${p_name}*" class="input-field" value="" required/>
                       			</div>

                                  <div class="input">
                                  <select name="type" class="input-field" required="required">
                                    <option value="">${p_type}*</option>
                                    <c:forEach items="${sessionScope.types}" var="type">
                                    <option value=${type}>${type}</option>
                                    </c:forEach>
                                  </select>
                                  </div>

                                 <div class="input">
                                 <input type="number" step="any" name="price" placeholder=" ${p_price}*" class="input-field" value="" required/>
                                 </div>
                                 <div class="input">
                                   <input type="text" name="description" placeholder=" ${p_description}*" class="input-field" value="" required/>
                                 </div>

                                 <div class="input">
                                 <select name="status" class="input-field" required="required">
                                   <option value="">${p_status}*</option>
                                   <option value="true">true</option>
                                   <option value="false">false</option>
                                 </select>
                                 </div>

                                 <div class="input">
                                 <input type="text" name="image" placeholder=" URL*" class="input-field" value="" required/>
                                 </div>

                       			<div class="action">
                       				<button type="submit" class="action-button">${addToMenu}</button>
                       			</div>
                       		</form>
                       		<div class="card-info">
                       			<p>${fieldsFillMessage}</p>
                       		</div>
                       	</div>
                       </div>



            </c:if>


        </body>

</html>