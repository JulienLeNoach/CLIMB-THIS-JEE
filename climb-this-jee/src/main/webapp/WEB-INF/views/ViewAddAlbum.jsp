<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="./style/navbar.css" />
    <link rel="stylesheet" type="text/css" href="./style/allPage.css" />
    <link rel="stylesheet" type="text/css" href="./style/pics.css" />
    <link rel="icon" href="./Images/imgApp/logoclimbthis.png">
	<title>Proposer un album</title>
</head>
<body>
    <jsp:include page="NavbarAdmin.jsp"></jsp:include>
	<form method="POST" action="AddAlbum">
		<table border="1">
			<thead>
		        <tr>
		            <th colspan="1">Pseudo</th>
		            <th colspan="1">Sujet de la demande</th>
		            <th colspan="1">Contenu du message</th>
		            <th colspan="1">ID</th>
		            <th colspan="1">Couleur</th>
					<th colspan="1">Lane</th>
					<th colspan="1">Mur</th>
		        </tr>
		    </thead>
		    <tbody>
				<c:set var="i" value="${i}" scope="request"/>
		    	<c:forEach var="data" items="${data}" begin="1" end="${i-1}">
			    	<tr>
				        <td><c:out value="${data[0]}" /></td>
				        <td><c:out value="${data[1]}" /></td>
				        <td><c:out value="${data[2]}" /></td>
				        <td><c:out value="${data[3]}" /></td>
				        <td><c:out value="${data[4]}" /></td>
				        <td><input class="itable" type="text" value="<c:out value="${data[6]}" />" readonly style="width:60px;border:none;" name="idLane" ></td>
						<td><input type="text" class="itable" value="<c:out value="${data[5]}" />" readonly style="width:60px;border:none;" name="idWall" ></td>	
						<td class="flex">
							<c:forEach var="albumPhoto" items="${albumPhoto}">
								<c:if test="${albumPhoto[0] eq data[3]}">
					    			<div class="pics imgwait" id="pics"><img  height="60" src="<c:out value="${albumPhoto[1]}"/>" class=""></div>
					    		</c:if>
							</c:forEach></td>
			        	<td><input type="checkbox" value="<c:out value="${data[3]}" />" name="albumSelected"></td>      
			        </tr>
		    	</c:forEach>
		</table>
		<input class="" name ="submit" type="submit" value="Accepter la demande">
		<input class="" name="submit" type="submit" value="Supprimer la demande">
	</form>
	
	<p>
		<c:set var="checkForm" value="${checkForm }" scope="request"/>
		<c:if test="${checkForm == false}">
			<c:out value="Veuillez séléctionnez une demande"/>
		</c:if>
	</p>
	
	<div id="background"></div>
	<div id="pop"><img alt="" src="" ></div>
		
	<script src="./JS/pic.js"></script>
</body>
</html>