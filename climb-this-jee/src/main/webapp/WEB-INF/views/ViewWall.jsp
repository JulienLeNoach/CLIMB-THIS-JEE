<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="./style/wall.css" />
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
	
	<div class="mainContent">
		<h2>Veuillez tout d'abord choisir le mur parmis les murs de  ${club.getClub_name()} </h2>
		
		 <form method="POST" action="ControllerWall" id="show lane" >
		 	 <p>
				<label for="listWalls">Liste des murs de ce club :</label>
				<select name="listWall" id="listWall">
					<c:set var="i"  value="0" scope="page" />
					<c:forEach var="listWall" items="${club.getList_wall()}">
						<option value="${i}">Mur ${i + 1}</option>
						<c:set var="i"  value="${i + 1}" scope="page" />
					</c:forEach>
				</select>
			</p>
			<input type="submit" name ="action1" value="Sélectionner ce mur et afficher les parcours">
			<input type="submit" name ="action2" value="Ajouter un mur">	
			<input type="submit" name="btnDeleteWall" value="Supprimer ce mur">
		</form>
			
	</div>   	 
</body>
</html>