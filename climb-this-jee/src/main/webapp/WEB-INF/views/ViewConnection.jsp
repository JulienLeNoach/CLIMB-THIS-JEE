<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
	<link rel="stylesheet" type="text/css" href="./style/connection.css" />
	<link rel="icon" href="./Images/imgApp/logoclimbthis.png" >
	<link rel="icon" href="./Images/imgApp/logoclimbthis.png">
	<meta charset="UTF-8">
	<title>Climb This!</title>
</head>

<body>
<Form method="POST" action="Connection">
	<h2>Connexion</h2>
	
	<p>
		<label for="user_name">Identifiant : </label> <br>
		<input id="user_name" name="user_name"></input>		
	</p>
	
	<p>
		<label for="pwd">Mot de passe : </label> <br>
		<input id="pwd" name="pwd" type="password"></input>
	</p>
	
	<p>
		<label for="selected_club_name">Club :</label>
		<select name="selected_club_name" id="selected_club_name">
			<c:forEach var="listNameClub" items="${listNameClub }">
				<option value="${listNameClub}">${listNameClub }</option>
			</c:forEach>
		</select>
	</p>
	
	<div class="btn">
		<input type="submit" name="btn_submit" value="Se connecter">	
	</div>
	
	<div class="btn">
		<input type="submit" name="btn_submit" value="S'incrire">
		<input type="submit" name="btn_submit" value="Mot de passe oublié ?">
	</div>
	
	<p>
		<c:set var="badForm" value="${badForm}" scope="request"/>
		<c:set var="idFound" value="${idFound}" scope="request"/>
		<c:set var="idFoundReset" value="${idFoundReset}" scope="request"/>
		<c:if test="${idFoundReset == true}">
			<c:out value="Vous avez bien réinitialiser votre mot de passe." />
		</c:if>
		<c:if test="${badForm == false}">
			<c:out value="Votre compte à bien été créé" />
		</c:if>
		<c:if test="${idFound == false}">
			<c:out value="Identifiant ou mot de passe incorrect !" />
		</c:if>
				<c:if test="${cptLock == true}">
			<c:out value="Compte bloqué" />
		</c:if>
	</p>
</Form>
</body>
</html>