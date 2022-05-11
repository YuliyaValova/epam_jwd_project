<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
<style type="text/css">
/* If you like this, please check my blog at codedgar.com.ve */
@import url('https://fonts.googleapis.com/css?family=Work+Sans');
body{
font-family: 'Work Sans', sans-serif;
background: url('https://pbs.twimg.com/media/EGMuSK-WoAIRuRQ.jpg') top left;
  /* Thanks to uigradients :) */
}

.container{
position:relative;
top:100px;
}

.card{
  background:#16181a;  border-radius:14px; max-width: 300px; display:block; margin:auto;
  padding:60px; padding-left:20px; padding-right:20px;box-shadow: 2px 10px 40px black; z-index:99;
}

.logo-card{max-width:50px; margin-bottom:15px; margin-top: -19px;}

label{display:flex; font-size:10px; color:white; opacity:.4;}

input{font-family: 'Work Sans', sans-serif;background:transparent; border:none; border-bottom:1px solid transparent; color:#dbdce0; transition: border-bottom .4s;}
input:focus{border-bottom:1px solid #1abc9c; outline:none;}

.cardnumber{display:block; font-size:20px; margin-bottom:8px; }

.name{display:block; font-size:15px; max-width: 200px; float:left; margin-bottom:15px;}

.toleft{float:left;}
.ccv{width:50px; margin-top:-5px; font-size:15px;}

.receipt{background: white; border-radius:4px; padding:5%; padding-top:200px; max-width:600px; display:block; margin:auto; margin-top:-180px; z-index:-999; position:relative;}

.col{width:50%; float:left;}
.bought-item{background:green; padding:2px;}
.bought-items{margin-top:-3px;}

.cost{color:green;}
.seller{color: green;}
.description{font-size: 13px;}
.price{font-size:12px;}
.comprobe{text-align:center;}
.proceed{position:absolute; transform:translate(300px, 10px); width:50px; height:50px; border-radius:50%; background:green; border:none;color:white; transition: box-shadow .2s, transform .4s; cursor:pointer;}
.proceed:active{outline:none; }
.proceed:focus{outline:none;box-shadow: inset 0px 0px 5px white;}
.sendicon{filter:invert(100%); padding-top:2px;}

@media (max-width: 500px){
  .proceed{transform:translate(250px, 10px);}
  .col{display:block; margin:auto; width:100%; text-align:center;}
}
button.but1{
left:-50px;
font-size:20px;
	float:right;
	padding:8px 12px;
	margin:8px 0 0;
	font-family:'Montserrat',sans-serif;
	border:2px solid #78788c;
	background:white;
	color:green;
	cursor:pointer;
	transition:all .3s
}
 button.but1:hover{
	background:green;
}


</style>
</head>
<body>

<div class="container">
  <div class="card">
  <form method="post" action="/history" >
  <input type="hidden" name="command" value="changeOrderStatus"/>
  <input type="hidden" name="status" value="Paid up"/>
  <input type="hidden" name="orderId" value=${sessionScope.lastorderid}/>
  <button class="proceed" type="submit"><svg class="sendicon" width="24" height="24" viewBox="0 0 24 24">
                                          <path d="M4,11V13H16L10.5,18.5L11.92,19.92L19.84,12L11.92,4.08L10.5,5.5L16,11H4Z"></path>
                                        </svg></button><br/>
  </form>

   <img src="https://seeklogo.com/images/V/VISA-logo-62D5B26FE1-seeklogo.com.png" class="logo-card">
 <label>Card number:</label>
 <input id="user" type="number" class="input cardnumber"  placeholder="1234 5678 9101 1121" required>
 <label>Name:</label>
 <input class="input name"  placeholder="Yuliya Valova" required>
 <label class="toleft">CVV:</label>
 <input class="input toleft ccv" type="number" placeholder="321" required>
  </div>
  <div class="receipt">
    <div class="col"><p>Cost:</p>
    <h2 class="cost">${sessionScope.paymentSum}</h2><br>
       </div>

    <p class="comprobe">Fill this fields to pay by card</p>

  </div>
</div>
</body>
</html>