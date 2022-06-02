<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="./style/allPage.css" />
<link rel="stylesheet" type="text/css" href="./style/navbar.css" />
<link rel="stylesheet" type="text/css" href="./style/UserManagement.css" />
<link rel="icon" href="./Images/imgApp/logoclimbthis.png">
<title>Gestion utilisateur</title>
</head>
<body>
<jsp:include page="NavbarAdmin.jsp"></jsp:include>


<div id="la_page">

<div id="error">
<% if(request.getAttribute("error") != null) {%>
    <div class="error"><%=request.getAttribute("error")%></div>
    <%}%>
</div>
	
<table>
<thead>
    <tr>
        <th>Id</th>
        <th>Nickname</th>
        <th>User role</th>
    </tr>
</thead>

<tbody>
<c:set var="i" value="${cpt}" scope="request"/>
    <c:forEach var="data" items="${data}" begin="0" end="${cpt}">
    <tr class="data" id="${data[0]}")>
        <td><c:out value="${data[0]}" /></td>
        <td><c:out value="${data[1]}" /></td>
        <td><c:out value="${data[2]}" /></td>     
        </tr>               	        
    </c:forEach>
</tbody>                        
</table >



<div id="window_modify">

<button id="close">
X
</button>

<form action="ControllerUserManagement" method="POST">

<p>Identifiant de la personne : <input id="id_person"  name="id_person" readonly="readonly" type="text" value=""></p>

<p>Nickname : <input id="nickname" readonly="readonly" type="text" value=""></p>

<label for="user_role">Choisir un rôle</label>
<select name="user_role" id="user_role">
    <option value="user">user</option>
    <option value="moderator">moderator</option>
    <option value="admin">admin</option>
</select>





<input type="submit" name="action1" value="Valider les modifications" />
<input type="submit" name="action2" value="Supprimer le compte" />

</form>



</div>

</div>


 <script type="text/javascript" src="./JS/UserManagement.js"></script>
</body>
</html>