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
	<title>Lane</title>
</head>
<body>
		<c:if test="${user.getUser_role()=='moderator' ||user.getUser_role()=='admin' }">
			<jsp:include page="NavbarAdmin.jsp"></jsp:include>
		</c:if>
    	<c:if test="${user.getUser_role()=='user'}">
			<jsp:include page="NavbarUser.jsp"></jsp:include>
		</c:if>
	
	<h1>Veuillez choisir la voie du mur : </h1>
	
	 <form method="POST" action="ControllerLane">
	 	 <p>
			<label for="listwalls">Liste des voies de ce mur :</label>
			
			<select name="listLane" id="listLane">
				<c:set var="wallIndex" value="${wallIndex}" scope="request" />
				<c:set var="i"  value="0" scope="page" />
				<c:forEach var="listLane" items="${club.getList_wall().get(wallIndex).getList_lane()}">
					<option value="${i}">${listLane.getColor()}</option>
					<c:set var="i"  value="${i + 1}" scope="page" />
				</c:forEach>
			</select>
		</p>
		
		<input type="submit" name="showLane" value="Choisir ce parcours et afficher les albums">
			<c:if test="${user.getUser_role()=='admin' }">
			<input type="submit" name="deleteLane" value="Supprimer ce parcours">
			<p>
			<h3>Ajouter un nouveau parcours : </h3>
			<select name="color"> 
			<option value="Jaune">Jaune</option>
			<option value="Vert">Vert</option>
			<option value="Bleu">Bleu</option>
			<option value="Rouge">Rouge</option>
			<option value="Marron">Marron</option>
			<option value="Noir">Noir</option>
		</select>
		
		<input type="submit" name="addLane" value="Ajouter un parcours">
		</p>
		 </c:if>
		</form>
		
		

</body>
</html>