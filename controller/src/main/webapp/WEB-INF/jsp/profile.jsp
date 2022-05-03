<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>

<style type = "text/css">

body{
	background: url('https://pbs.twimg.com/media/EGMuSK-WoAIRuRQ.jpg') top left;
	margin:0
}
 .form{
	width:340px;
	height:1000px;
	background:#e6e6e6;
	border-radius:8px;
	box-shadow:0 0 40px -10px #000;
	margin:calc(50vh - 220px) auto;
	padding:20px 30px;
	max-width:calc(100vw - 40px);
	box-sizing:border-box;
	font-family:'Montserrat',sans-serif;
	position:relative
}

 .find_form{
	width:340px;
	height:fit-content;
	background:#e6e6e6;
	border-radius:8px;
	box-shadow:0 0 40px -10px #000;
	margin:calc(50vh - 220px) auto;
	padding:20px 30px;
	max-width:calc(100vw - 40px);
	box-sizing:border-box;
	font-family:'Montserrat',sans-serif;
	position:relative
}

.form2{
	width:fit-content;
	height:90px;
	background:#e6e6e6;
	border-radius:8px;
	box-shadow:0 0 40px -10px #000;
	margin:calc(50vh - 220px) auto;
	padding:20px 30px;
	max-width:calc(100vw - 40px);
	box-sizing:border-box;
	font-family:'Montserrat',sans-serif;
	position:fixed;
	left:1000px;
	top:0px;
}

.password{
	width:200px;
	height:200px;
	background:#e6e6e6;
	border-radius:8px;
	box-shadow:0 0 40px -10px #000;
	margin:calc(50vh - 220px) auto;
	padding:20px 30px;
	max-width:calc(100vw - 40px);
	box-sizing:border-box;
	font-family:'Montserrat',sans-serif;
	position:fixed;
	left:1000px;
	top:100px;
}

 h2{
	margin:10px 0;
	padding-bottom:10px;
	width:180px;
	color:#78788c;
	border-bottom:3px solid #78788c
}
 input{
	width:100%;
	padding:10px;
	box-sizing:border-box;
	background:none;
	outline:none;
	resize:none;
	border:0;
	font-family:'Montserrat',sans-serif;
	transition:all .3s;
	border-bottom:2px solid #bebed2
}
 input:focus{
	border-bottom:2px solid #78788c
}
 h3:before{
	content:attr(type);
	display:block;
	margin:28px 0 0;
	font-size:14px;
	color:#5a5a5a
}
 button{
	float:right;
	padding:8px 12px;
	margin:8px 0 0;
	font-family:'Montserrat',sans-serif;
	border:2px solid #78788c;
	background:#e6e6e6;
	color:#5a5a6e;
	cursor:pointer;
	transition:all .3s
}
 button:hover{
	background:#78788c;
	color:#fff
}

 span{
	margin:0 5px 0 15px
}

      .header {
             position: fixed;
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
            .success {
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
                             border: 2px solid green;
                                            color: green;
            }
</style>

        <fmt:setLocale value="${sessionScope.locale}" />
		<fmt:setBundle basename="localization.local" var="loc" />
		<fmt:message bundle="${loc}" key="locale.locbutton.name.en" var="en_button"/>
        <fmt:message bundle="${loc}" key="locale.locbutton.name.ru" var="ru_button" />
        <fmt:message bundle="${loc}" key="locale.login" var="login" />
        <fmt:message bundle="${loc}" key="locale.profile" var="profile" />
        <fmt:message bundle="${loc}" key="locale.account" var="account" />
        <fmt:message bundle="${loc}" key="locale.password" var="password" />
        <fmt:message bundle="${loc}" key="locale.address" var="address" />
        <fmt:message bundle="${loc}" key="locale.fname" var="fname" />
        <fmt:message bundle="${loc}" key="locale.lname" var="lname" />
        <fmt:message bundle="${loc}" key="locale.phone" var="phone" />
        <fmt:message bundle="${loc}" key="locale.role" var="role" />
        <fmt:message bundle="${loc}" key="locale.city" var="city" />
        <fmt:message bundle="${loc}" key="locale.street" var="street" />
        <fmt:message bundle="${loc}" key="locale.building" var="building" />
        <fmt:message bundle="${loc}" key="locale.apartment" var="apartment" />
        <fmt:message bundle="${loc}" key="locale.goMain" var="goMain" />
        <fmt:message bundle="${loc}" key="locale.Update" var="Update" />
        <fmt:message bundle="${loc}" key="locale.deleteAccount" var="deleteAccount" />
        <fmt:message bundle="${loc}" key="locale.updateAccount" var="updateAccount" />
        <fmt:message bundle="${loc}" key="locale.changePassword" var="changePassword" />
        <fmt:message bundle="${loc}" key="locale.IncorrectOldPassword" var="IncorrectOldPassword" />


<title>
    <c:out value = "${account}" />
</title>

</head>
    <body>

    <c:set var="error" value="${param.message}"/>


            <!-- LOCALE -->
            <div class="local">
               <form method="post" action="/profile" >
                              <input type="hidden" name="command" value="locale"/>
                              <input type="hidden" name="locale" value="en">
                              <button class ="loc" type="submit">${en_button}</button>

               </form>

               <form method="post" action="/profile" >
                              <input type="hidden" name="command" value="locale"/>
                              <input type="hidden" name="locale" value="ru">
                              <button class ="loc" type="submit">${ru_button}</button>
               </form>

             <c:if test="${requestScope.message != null}">
                         <p class="success">${requestScope.message}</p>
                         </c:if>

                        <c:if test="${error == 'IncorrectOldPassword'}">
                        <p class="n-warning">${IncorrectOldPassword}</p>
                        </c:if>

                        <c:if test="${error == 'IncompleteInfo'}">
                        <p class="n-warning">All fields must be filled!</p>
                        </c:if>


                                 </div>


            <c:if test="${sessionScope.role != null}">

                <c:if test="${sessionScope.page != 'findUser'}">


                <form class="form" method="post" action="/main">
                  <h2>${account}</h2>
                  <input type="hidden" name="command" value="updateUser"/>
                 <h3>${login}: ${sessionScope.user.login}</h3>
                   <h3>${fname}:</h3>
                   <input type="text" name="fName" value="${sessionScope.user.firstName}"/>
                   <h3>${lname}:</h3>
                   <input type="text" name="lName" value="${sessionScope.user.lastName}"/>
                   <h3>${phone}:</h3>
                   <input type="text" name="phone" value="${sessionScope.user.phone}"/>
                   <h3>${city}:</h3>
                   <input type="text" name="city" value="${sessionScope.addr.city}"/>
                   <h3>${street}:</h3>
                   <input type="text" name="street" value="${sessionScope.addr.street}"/>
                   <h3>${building}:</h3>
                   <input type="text" name="building" value="${sessionScope.addr.building}"/>
                   <h3>${apartment}:</h3>
                   <input type="text" name="apartment" value="${sessionScope.addr.apartment}"/>
                   <br>
                   <br>

                   <button type="submit">${Update}</button>
                   </form>
                                <br>



                <form class="form2" method="get" action="/home" >
                               <input type="hidden" name="command" value="deleteAccount"/>
                               <button type="submit">${deleteAccount}</button>
                </form>

                    <form id="changePassword" class="password" method="post" action="/home" >
                                <input type="hidden" name="command" value="changepassword"/>
                                <input type="text" name="oldPassword" placeholder="old password" value=""/>
                                <input type="text" name="newPassword" placeholder="new password" value=""/>
                                <br>
                                <br>
                                <button type="submit">${changePassword}</button>
                    </form>


                </c:if>


                <c:if test="${sessionScope.page == 'findUser'}">

                              <div class="find_form">
                               <h3>${login}:${sessionScope.findUser.login}</h3>
                               <h3>${fname}: ${sessionScope.findUser.firstName}</h3>
                               <h3>${lname}: ${sessionScope.findUser.lastName}</h3>
                               <h3>${phone}: ${sessionScope.findUser.phone}</h3>
                               <h3>${role}: ${sessionScope.findUser.role}</h3>
                               <h3>${city}: ${sessionScope.findAddr.city}</h3>
                               <h3>${street}: ${sessionScope.findAddr.street}</h3>
                               <h3>${building}:${sessionScope.findAddr.building}</h3>
                               <h3>${apartment}: ${sessionScope.findAddr.apartment}</h3>
                               <br>
                               </div>

                </c:if>

            </c:if>

    </body>
</html>