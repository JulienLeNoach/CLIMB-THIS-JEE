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
<Form method="POST" action="Register">
	<h2>S'inscrire</h2>
	
	<p>
		<label for="lastname">Nom :</label> <br>
		<input id="lastname" name="lastname"  ></input>
	</p>
		
	<p>
		<label for="firstname">Prénom :</label> <br>
		<input id="firstname" name="firstname" ></input>
	</p>
		
	<p>
		<label for="user_name">Identifiant :</label> <br>
		<input id="user_name" name="user_name"  ></input>		
	</p>
		
	<p>
		<label for="email">Adresse email :</label> <br>
		<input id="email" name="email" >
	</p>
		
	<p>
		<label for="pwd">Mot de passe :</label> <br>
		<input id="pwd" name="pwd" type="password" ></input>
	</p>
		
	<p>
		<label for="pwdCheck">Confirmation mot de passe :</label> <br>
		<input id="pwdCheck" name="pwdCheck" type="password" ></input>
	</p>
		
	<p>
		<label for="nickname">Pseudo :</label> <br>
		<input id="nickname" name="nickname" >
	</p>
		
	<p>
		<label for="selected_club_name">Club :</label>
		<select name="selected_club_name" id="selected_club_name">
			<c:forEach var="listNameClub" items="${listNameClub}">
				<option value="${listNameClub}">${listNameClub}</option>
			</c:forEach>
		</select>
	</p>
		
	<div class="btn">
		<input type="submit" name="btn_submit" value="S'incrire">
		<input type="submit" name="btn_submit" value="Retour">
	</div>
	
	<p>
		<c:set var="badForm" value="${badForm}" scope="request"/>
		<c:set var="emptyForm" value="${emptyForm}" scope="request"/>
		<c:if test="${badForm == true && emptyForm == false}">
			<c:out value="Identifiant, e-mail ou pseudo déjà utilisé." />
		</c:if>
		<c:if test="${emptyForm == true}">
			<c:out value="Respecter les conditions d'inscription." />
		</c:if>
	</p>
</Form>
</body>
</html>