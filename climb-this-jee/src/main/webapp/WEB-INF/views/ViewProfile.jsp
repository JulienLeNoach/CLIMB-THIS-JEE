<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="./style/CSSProfile.css" />
<link rel="stylesheet" type="text/css" href="./style/allPage.css" />
<link rel="stylesheet" type="text/css" href="./style/navbar.css" />
<link rel="icon" href="./Images/imgApp/logoclimbthis.png">
<meta charset="UTF-8">
<title>Profile</title>
</head>
<body>
	<c:if test="${user.getUser_role()=='moderator' ||user.getUser_role()=='admin' }">
			<jsp:include page="NavbarAdmin.jsp"></jsp:include>
		</c:if>
    	<c:if test="${user.getUser_role()=='user'}">
			<jsp:include page="NavbarUser.jsp"></jsp:include>
		</c:if>
	<h1>Votre profil </h1>
	<h2>Vous pouvez modifier vos informations ici</h2>
	
	 <form method="POST" action="ControllerProfile" id="test" onSubmit="return validateProfile()">
  		<label for="nickname" >Pseudo:</label><br>
  		<input readonly="readonly" class="readonly" type="text" id="nickname" name="nickname" value="${user.getNickname()}"><br>
  
  		<label for="firstname">Prénom:</label><br>
  		<input readonly="readonly"  class="readonly" type="text" id="firstname" name="firstname" value="${user.getFirst_name()}"><br>
  	  		
  		<label for="name">Nom:</label><br>   
  		<input readonly="readonly" class="readonly"  type="text" id="last_name" name="last_name" value="${user.getName()}"><br>
  		
  		<label for="email">e-mail:</label><br>
  		<input readonly="readonly"  class="readonly" type="text" id="email" name="email" value="${user.getEmail()}"><br>
  		
  		<label for="club">Club actuel:</label><br>
  		<input readonly="readonly"   type="text" id="club" name="club" value="${user.getClub_name()}"><br>
  		
  		<label for="club">Compteur compte bloqué</label><br>
  		<input readonly="readonly"  disabled type="text" id="nbLock" name="nbLock" value="${user.getnbLock()}"><br>

  		<button type="submit" class="btnProfileUpdate" > Valider les changements </button>
  	</form> 
	
	<button id="btnProfile" class="btnProfile" onclick="updateProfile()"> Modifier</button>
	
	<p> Assurez vous que tous les champs soient tous complétés.</p>
	

 <script src="./JS/JSProfile.js"></script> 
</body>
</html>