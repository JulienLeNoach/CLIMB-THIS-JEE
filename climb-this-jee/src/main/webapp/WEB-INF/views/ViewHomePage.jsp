<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="./style/allPage.css" />
<link rel="stylesheet" type="text/css" href="./style/navbar.css" />
<link rel="stylesheet" type="text/css" href="./style/HomePage.css" />
    <link rel="icon" href="./Images/imgApp/logoclimbthis.png">
<style>
@import url('https://fonts.googleapis.com/css2?family=Rubik:wght@300&display=swap');
</style>
<title>Climb This!</title>
</head>
<body>
<c:set var="UserRole" value="${user.getUser_role()}" scope="request"/>
    <c:if test="${user.getUser_role()=='moderator' ||user.getUser_role()=='admin' }">
            <jsp:include page="NavbarAdmin.jsp"></jsp:include>
        </c:if>
        <c:if test="${user.getUser_role()=='user'}">
            <jsp:include page="NavbarUser.jsp"></jsp:include>
        </c:if>
    <div id="difficulty" >
        <img src="./Images/imgApp/theroof.jpg">
        <div><p id="title">Bienvenue sur  ${club.getClub_name()},  ${user.getNickname()} ! </p>
           <div id="information">
            <ul>
                <li>Très difficile</li>
                <li>Difficile</li>
                <li>Assez difficile</li>
                <li>Moyen</li>
                <li>Facile</li>
                <li>Très Facile</li>
            </ul>
            <p id="resume"> The Roof Brest est une salle de bloc proposant des parcours n'utilisant qu'une seule couleur. A chaque début de bloc on retrouve 4 étiquettes qui nous indiquent la difficulté du parcours et imposent le départ.
            Pour commencer à grimper il faut poser chacun de ses 4 appuis (2 mains / 2 pieds) sur une prise avec une étiquette. Pour terminer le bloc il faut matcher la dernière prise.
         </p>
        </div>
     </div>
    </div>    
</body>
</html>
