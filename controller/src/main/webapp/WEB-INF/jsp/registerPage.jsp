<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>

<style type = "text/css">
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
	&:focus, &:valid {
		outline: 0;
		border-bottom-color: #6658d3;
		&+.input-label {
			color: #6658d3;
			transform: translateY(-1.5rem);
		}
	}
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

    .header {
         position: relative;
         display:inline-block;
         font-size:30px;
             top: -40;
             left: 0;
             color:black;
             height: 60px;
             width: 100%;
             z-index: 2;
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
                 width: 100%;
                 z-index: 2;
        }

        div form {
            display:inline-block
        }
</style>

        <fmt:setLocale value="${sessionScope.locale}" />
		<fmt:setBundle basename="localization.local" var="loc" />
		<fmt:message bundle="${loc}" key="locale.locbutton.name.en" var="en_button"/>
        <fmt:message bundle="${loc}" key="locale.locbutton.name.ru" var="ru_button" />
        <fmt:message bundle="${loc}" key="locale.registerPage" var="registerPage" />
        <fmt:message bundle="${loc}" key="locale.signUp" var="signUp" />
        <fmt:message bundle="${loc}" key="locale.login" var="login" />
        <fmt:message bundle="${loc}" key="locale.password" var="password" />
        <fmt:message bundle="${loc}" key="locale.repPassword" var="repPassword" />
        <fmt:message bundle="${loc}" key="locale.fieldsFillMessage" var="fieldsFillMessage" />
        <fmt:message bundle="${loc}" key="locale.info" var="info" />
        <fmt:message bundle="${loc}" key="locale.address" var="address" />
        <fmt:message bundle="${loc}" key="locale.fname" var="fname" />
        <fmt:message bundle="${loc}" key="locale.lname" var="lname" />
        <fmt:message bundle="${loc}" key="locale.phone" var="phone" />
        <fmt:message bundle="${loc}" key="locale.city" var="city" />
        <fmt:message bundle="${loc}" key="locale.street" var="street" />
        <fmt:message bundle="${loc}" key="locale.building" var="building" />
        <fmt:message bundle="${loc}" key="locale.apartment" var="apartment" />
        <fmt:message bundle="${loc}" key="locale.errorMsg_noUser" var="errorMsg_noUser" />
        <fmt:message bundle="${loc}" key="locale.errorMsg_fieldsFill" var="errorMsg_fieldsFill" />
        <fmt:message bundle="${loc}" key="locale.errorMsg_alrExists" var="errorMsg_alrExists" />
        <fmt:message bundle="${loc}" key="locale.errorMsg_invOperation" var="errorMsg_invOperation" />
        <fmt:message bundle="${loc}" key="locale.errorMsg_injectDanger" var="errorMsg_injectDanger" />
        <fmt:message bundle="${loc}" key="locale.errorMsg_pswNotMatch" var="errorMsg_pswNotMatch" />

<title>
    <c:out value = "${registerPage}" />
</title>

</head>
    <body>

    <c:set var="error" value="${param.message}"/>

            <!-- LOCALE -->

             <div class="local">
                                            <form method="post" action="/registerPage" >
                                                           <input type="hidden" name="command" value="locale"/>
                                                           <input type="hidden" name="locale" value="en">
                                                           <button class ="loc" type="submit">${en_button}</button>

                                            </form>

                                            <form method="post" action="/registerPage" >
                                                           <input type="hidden" name="command" value="locale"/>
                                                           <input type="hidden" name="locale" value="ru">
                                                           <button class ="loc" type="submit">${ru_button}</button>
                                            </form>

 <c:if test="${error == 'noSuchUser'}">
                <p class="n-warning">${errorMsg_noUser}</p>
            </c:if>

            <c:if test="${error == 'IncompleteInfo'}">
                <p class="n-warning">${errorMsg_fieldsFill}</p>
            </c:if>

            <c:if test="${error == 'userExists'}">
                <p class="n-warning">${errorMsg_alrExists}</p>
            </c:if>

            <c:if test="${error == 'passwordsNotMatch'}">
                <p class="n-warning">${errorMsg_pswNotMatch}</p>
            </c:if>

                     </div>





            <div class="container">
            	<!-- code here -->
            	<div class="card">
            		<div class="card-image">
            			<h2 class="card-heading">
            				Get started...
            				<br>
            				<small>Let us create your account</small>
            			</h2>
            		</div>
            		<form class="card-form" method="post" action="/home">
            		<input type="hidden" name="command" value="registration"/>
            			<div class="input">
            				<input type="text" name="login" placeholder="${login}*" class="input-field" value="" required/>

            			</div>
            						<div class="input">
            				<input type="password" name="password1" placeholder="${password}*" class="input-field" value="" required/>

            			</div>
            			<div class="input">
                        <input type="password" name="password2" placeholder="${repPassword}*" class="input-field" value="" required/>

                        </div>

            			<div class="input">
                        <input type="text" name="fName" placeholder="${fname}*" class="input-field" value="" required/>
                        </div>

                        <div class="input">
                                                <input type="text" name="lName" placeholder="${lname}*" class="input-field" value="" required/>

                                                </div>

                         <div class="input">
                                                 <input type="text" name="phone" placeholder="${phone}*" class="input-field" value="" required/>

                                                 </div>

                        <div class="input">
                                                <input type="text" name="city" placeholder="${city}*" class="input-field" value="" required/>

                                                </div>
                        <div class="input">
                                                <input type="text" name="street" placeholder="${street}*" class="input-field" value="" required/>

                                                </div>
                        <div class="input">
                                                <input type="text" name="building" placeholder="${building}*" class="input-field" value="" required/>

                                                </div>
                         <div class="input">
                                                 <input type="text" name="apartment" placeholder="${apartment}" class="input-field" value=""/>
                                                 </div>

            			<div class="action">
            				<button type="submit" class="action-button">${signUp}</button>
            			</div>
            		</form>
            		<div class="card-info">
            			<p>${fieldsFillMessage}</p>
            		</div>
            	</div>
            </div>

       </body>
</html>