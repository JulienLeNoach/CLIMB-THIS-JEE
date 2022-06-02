<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="./style/connection.css" />
	<link rel="icon" href="./Images/imgApp/logoclimbthis.png" >
	<link rel="icon" href="./Images/imgApp/logoclimbthis.png">
	<title>Climb This!</title>
</head>
<body>
<Form method="POST" action=ResetPwd>
	<h2>Réinitialisation du mot de passe</h2>
	
	<p>
		<label for="user_name">Identifiant : </label> <br>
		<input id="user_name" name="user_name" >		
	</p>
	
	<p>
		<label for="email">Email : </label> <br>
		<input id="email" name="email" >
	</p>
	
	<p>
		<label for="newPwd">Nouveau mot de passe : </label> <br>
		<input type="password" id="newPwd" name="newPwd">
	</p>
	
	<div class="btn">
		<input type="submit" name="btn_submit" value="Réinitialiser le mot de passe">	
		<input type="submit" name="btn_submit" value="Retour">
	</div>
	
	<p>
		<c:set var="idFoundReset" value="${idFoundReset}" scope="request"/>
		<c:if test="${idFoundReset == false}">
			<c:out value="Identifiant ou email incorrect !" />
		</c:if>
	</p>
</Form>
</body>
</html>