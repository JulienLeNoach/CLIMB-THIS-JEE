<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="./style/pics.css" />
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
	
	<h2>Voici toutes les photos de l'album : </h2>
	
	<div class="contentPics">
		<c:set var="i"  value="1" scope="page" />
		<c:set var="albumId" value="${club.getList_wall().get(wallIndex).getList_lane().get(laneIndex).getList_albums().get(albumIndex).getId_album()}" scope="page"/>
		<c:forEach var="listPics" items="${club.getList_wall().get(wallIndex).getList_lane().get(laneIndex).getList_albums().get(albumIndex).getList_photo()}">
			<div class="pics" id="pics">
				<img alt="Image ${i}" src="./Images/userAlbums/${albumId}/${listPics}" class="imgMin">
				<p>Photo ${i}</p>				
			</div>
			<c:set var="i"  value="${i + 1}" scope="page" />
		</c:forEach>
	</div>
	<div>
	<form method="POST" action="Album">
		<input name="idAlbum" value="${albumId}" type="hidden">
		<input name ="albumindex" value="${albumIndex}" type="hidden"> 
		<input name="deleteAlbum" type ="submit" value="Supprimer cet album">
	</form>
	</div>
	
	<div id="background"></div>
	<div id="pop"><img alt="Image d'escalade." src="" ></div>
	
	<script src="./JS/pic.js"></script>
</body>
</html>