<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="./style/album.css" />
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
	
	<h2>Voici tout les album de la voie : </h2>
	
	 <form method="POST" action="Album" id="show lane" >
	 	 <div class="contentAlbum">
			<c:set var="i"  value="0" scope="page" />
			<c:forEach var="listAlbum" items="${club.getList_wall().get(wallIndex).getList_lane().get(laneIndex).getList_albums()}">
				<c:set var="albumIndex"  value="${club.getList_wall().get(wallIndex).getList_lane().get(laneIndex).getList_albums().get(i).getId_album()}" scope="page" />
				<c:set var="picIndex"  value="${club.getList_wall().get(wallIndex).getList_lane().get(laneIndex).getList_albums().get(i).getList_photo().get(0)}" scope="page" />
				<div class="album">
					<button type="submit" name="showAlbums" value="${i}"><img alt="Photo album ${i}" src="./Images/userAlbums/${albumIndex}/${picIndex}"></button>
					<c:set var="i"  value="${i + 1}" scope="page" />
					<p>Album ${i}</p>	
				</div>
			</c:forEach>
		</div>
  	</form> 
</body>
</html>