<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
        <link rel="stylesheet" type="text/css" href="./style/navbar.css" />
          <link rel="stylesheet" type="text/css" href="./style/allPage.css" />
          <link rel="icon" href="./Images/imgApp/logoclimbthis.png">
<title>Afficher les demandes</title>
</head>
<body>
    	<c:if test="${user.getUser_role()=='moderator' ||user.getUser_role()=='admin' }">
			<jsp:include page="NavbarAdmin.jsp"></jsp:include>
		</c:if>
    	<c:if test="${user.getUser_role()=='user'}">
			<jsp:include page="NavbarUser.jsp"></jsp:include>
		</c:if>
<form method="POST" action="MailBox">
<table border="1">
	<thead>
        <tr>
            <th colspan="1">Pseudo</th>
            <th colspan="1">Objet</th>
            <th colspan="1">Sujet</th>
            <th colspan="1">ID</th>
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
        		<td><input type="checkbox" value="<c:out value="${data[3]}" />" name="messageSelected"></td>      
        	</tr>               	        
   		 </c:forEach>
</table>
<input class="" name="submit" type="submit" value="Supprimer le message">
</form>
<p><c:set var="MailDeleted" value="${MailDeleted }" scope="request"/>
		<c:if test="${MailDeleted == false}">
			<c:out value="Veuillez séléctionner un message"/>
		</c:if></p>
</body>
</html>