<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<html>

<head>

<style type = "text/css">
@import url(https://fonts.googleapis.com/css?family=Roboto:400,300,100,500);
body,
html {
  margin: 0;
  height: 100%;
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

input {
  border: none;
}

button:focus {
  outline: none;
}

::-webkit-input-placeholder {
  color: rgba(255, 255, 255, 1);
}

::-webkit-input-placeholder .input-line:focus +::input-placeholder {
  color: #fff;
}

.highlight {
  color: rgba(255, 255, 255, 0.8);
  font-weight: 400;
  cursor: pointer;
  transition: color .2s ease;
}

.highlight:hover {
  color: #fff;
  transition: color .2s ease;
}

.spacing {
  -webkit-box-flex: 1;
  -webkit-flex-grow: 1;
  -ms-flex-positive: 1;
  flex-grow: 1;
  height: 120px;
  font-weight: 300;
  text-align: center;
  margin-top: 10px;
  color: rgba(255, 255, 255, 0.65)
}

.input-line:focus {
  outline: none;
  border-color: #fff;
  -webkit-transition: all .2s ease;
  transition: all .2s ease;
}

.ghost-round {
  cursor: pointer;
  background: none;
  border: 1px solid rgba(255, 255, 255, 1);
  border-radius: 25px;
  color: rgba(255, 255, 255, 1);
  -webkit-align-self: flex-end;
  -ms-flex-item-align: end;
  align-self: flex-end;
  font-size: 19px;
  font-size: 1.2rem;
  font-family: roboto;
  font-weight: 300;
  line-height: 2.5em;
  margin-top: auto;
  margin-bottom: 25px;
  -webkit-transition: all .2s ease;
  transition: all .2s ease;
}

.ghost-round:hover {
  background: rgba(255, 255, 255, 0.15);
  color: #fff;
  -webkit-transition: all .2s ease;
  transition: all .2s ease;
}

.input-line {
  background: none;
  margin-bottom: 10px;
  line-height: 2.4em;
  color: #fff;
  font-family: roboto;
  font-weight: 300;
  letter-spacing: 0px;
  letter-spacing: 0.02rem;
  font-size: 19px;
  font-size: 1.2rem;
  border-bottom: 1px solid rgba(255, 255, 255, 1);
  -webkit-transition: all .2s ease;
  transition: all .2s ease;
}

.full-width {
  width: 100%;
}

.input-fields {
  margin-top: 25px;
}

.container {
  display: -webkit-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-align: center;
  -webkit-align-items: center;
  -ms-flex-align: center;
  align-items: center;
  -webkit-box-pack: center;
  -webkit-justify-content: center;
  -ms-flex-pack: center;
  justify-content: center;
  background: url('https://pbs.twimg.com/media/EGMuSK-WoAIRuRQ.jpg') top left;
  height: 100%;
}

.content {
  padding-left: 25px;
  padding-right: 25px;
  display: -webkit-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  -webkit-flex-flow: column;
  -ms-flex-flow: column;
  flex-flow: column;
  z-index: 5;
}

.welcome {
  font-weight: 200;
  margin-top: 75px;
  text-align: center;
  font-size: 40px;
  font-size: 2.5rem;
  letter-spacing: 0px;
  letter-spacing: 0.05rem;
}

.subtitle {
  text-align: center;
  line-height: 1em;
  font-weight: 100;
  letter-spacing: 0px;
  letter-spacing: 0.02rem;
}

.menu {
  background: rgba(0, 0, 0, 0);
  width: 100%;
  height: 50px;
}

.window {
  z-index: 100;
  color: #fff;
  font-family: roboto;
  position: relative;
  display: -webkit-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  -webkit-flex-flow: column;
  -ms-flex-flow: column;
  flex-flow: column;
  box-shadow: 0px 15px 50px 10px rgba(0, 0, 0, 0);
  box-sizing: border-box;
  height: 700px;
  width: 500px;
  background: #fff;
  background: url('https://pbs.twimg.com/media/EGMuSK-WoAIRuRQ.jpg') top left no-repeat;
}

.overlay {
  background: -webkit-linear-gradient(#1c9e2e, #9ad669);
  background: linear-gradient(#1c9e2e, #9ad669);
  opacity: 0.85;
  filter: alpha(opacity=85);
  height: 700px;
  position: absolute;
  width: 500px;
  z-index: 1;
}

.bold-line {
  background: #e7e7e7;
  position: absolute;
  top: 0px;
  bottom: 0px;
  margin: auto;
  width: 100%;
  height: 360px;
  z-index: 1;
  opacity:0.1;
    background: url('https://pexels.imgix.net/photos/27718/pexels-photo-27718.jpg?fit=crop&w=1280&h=823') left no-repeat;
  background-size:cover;
}

@media (max-width: 500px) {
  .window {
    width: 100%;
    height: 100%;
  }
  .overlay {
    width: 100%;
    height: 100%;
  }
}
 .header {
     position: relative;
     display:inline-block;
     font-size:30px;
         top: -40;
         left: 0;
         color:black        ;
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
        <fmt:message bundle="${loc}" key="locale.signInForm" var="signInForm" />
        <fmt:message bundle="${loc}" key="locale.login" var="login" />
        <fmt:message bundle="${loc}" key="locale.password" var="password" />
        <fmt:message bundle="${loc}" key="locale.errorMsg_noUser" var="errorMsg_noUser" />
        <fmt:message bundle="${loc}" key="locale.errorMsg_fieldsFill" var="errorMsg_fieldsFill" />

<title>
    <c:out value = "${signInForm}" />
</title>

</head>


    <body>
    <c:set var="error" value="${param.message}"/>

           <!-- LOCALE -->
         <div class="local">
                                <form method="post" action="/loginPage" >
                                               <input type="hidden" name="command" value="locale"/>
                                               <input type="hidden" name="locale" value="en">
                                               <button class ="loc" type="submit">${en_button}</button>

                                </form>

                                <form method="post" action="/loginPage" >
                                               <input type="hidden" name="command" value="locale"/>
                                               <input type="hidden" name="locale" value="ru">
                                               <button class ="loc" type="submit">${ru_button}</button>
                                </form>


           <c:if test="${error == 'noSuchUser'}">
           <div class="n-warning">
               <p>${errorMsg_noUser}</p>
           </div>

           </c:if>

                     <c:if test="${error == 'IncompleteInfo'}">
                    <div class="n-warning">
                        <p>${errorMsg_fieldsFill}</p>
                    </div>
                    </c:if>


         </div>
        <div class='bold-line'></div>
        <div class='container'>
          <div class='window'>
            <div class='overlay'></div>
            <div class='content'>
              <div class='welcome'>Welcome:)</div>
              <div class='subtitle'>Nice to meet you again!</div>
              <div class='input-fields'>
               <form method="post" action="/main" >
               <input type="hidden" name="command" value="login">
                <input type="login" name="login" placeholder=${login} class='input-line full-width'></input>
                <input type="password" name="password" placeholder=${password} class='input-line full-width'></input>

              </div>
              <div><button class='ghost-round full-width' type="submit">${signInForm}</button></div>

            </div>
          </div>
        </div>


    </body>
</html>