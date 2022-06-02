<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="" />
	<link rel="stylesheet" type="text/css" href="./style/allPage.css" />
	<link rel="stylesheet" type="text/css" href="./style/navbar.css" />
	<link rel="icon" href="./Images/imgApp/logoclimbthis.png">
	<meta charset="UTF-8">
	<title>Wall</title>
</head>
<body>
		<c:if test="${user.getUser_role()=='moderator' ||user.getUser_role()=='admin' }">
			<jsp:include page="NavbarAdmin.jsp"></jsp:include>
		</c:if>
    	<c:if test="${user.getUser_role()=='user'}">
			<jsp:include page="NavbarUser.jsp"></jsp:include>
		</c:if>
	
	<h4>Veuillez remplir le formulaire pour contacter un modérateur: </h4>
		
 <form method="POST" action="ControllerContactMod" id="contact">
  		<label for="nickname">Votre pseudo:</label><br>
  		<input readonly="readonly" class="readonly" type="text" id="nickname" name="nickname" value="${user.getNickname()}"><br>
  
  		
  		<label>Objet:</label><br>
  		<textarea  id="Object" name="object" ></textarea><br>
  		
  		<label >Contenu du message:</label><br>
  		<textarea id="subject" name="subject"></textarea><br>
  		
  		<button type="submit" class="btnMessageSend">Envoyez mon message</button>
  		<br>
  		
  		<c:set var="messageSent" value="${messageSent}" scope="request"/>
		<c:if test="${messageSent == true}">
			<c:out value="Votre message à bien été envoyé" />
		</c:if>
		<c:if test="${messageSent == false}">
			<c:out value="Une erreur s'est produite, votre message n'a pas été envoyé. Veuillez réessayer plus tard ou vous assurer que tous les champs soient complétés." />
		</c:if>
  	</form> 
</body>
</html>