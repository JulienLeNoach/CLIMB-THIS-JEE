<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Veuillez choisir les images à uploader.</title>
        <link rel="stylesheet" type="text/css" href="./style/requestalbum.css" />
        <link rel="stylesheet" type="text/css" href="./style/navbar.css" />
        <link rel="stylesheet" type="text/css" href="./style/allPage.css" />
        <link rel="icon" href="./Images/imgApp/logoclimbthis.png">
        <script>
            /* Cette fonction permet d'afficher une vignette pour chaque image sélectionnée */
            function readFilesAndDisplayPreview(files) {
                let imageList = document.querySelector('#list'); 
                imageList.innerHTML = "";
                
                for ( let file of files ) {
                    let reader = new FileReader();
                    
                    reader.addEventListener( "load", function( event ) {
                        let span = document.createElement('span');
                        span.innerHTML = '<img height="60" src="' + event.target.result + '" />';
                        imageList.appendChild( span );
                    });
                    reader.readAsDataURL( file );
                }
            }
        </script>
        
    </head>
    <body style="text-align: center">
    	<c:if test="${user.getUser_role()=='moderator' ||user.getUser_role()=='admin' }">
			<jsp:include page="NavbarAdmin.jsp"></jsp:include>
		</c:if>
    	<c:if test="${user.getUser_role()=='user'}">
			<jsp:include page="NavbarUser.jsp"></jsp:include>
		</c:if>
        
            <h1>Veuillez choisir les images à uploader.</h1>
        
        <form class="test" method="post" action="ControllerRequestAlbum" name="confirmationForm" enctype="multipart/form-data">
                   <label>Sujet de la demande :</label>
                   <input type="text" name="object">
                   <label>Choisissez un mur</label>
					<select id="id_wall" name="id_wall">
						<c:forEach var="listWall" items="${club.getList_wall()}">
							<option value="${listWall.getId_wall()}">Mur ${i+1}</option>
							<c:set var="i"  value="${i + 1}" scope="page" />
						</c:forEach>
					</select>
					<label>Choisissez une lane</label>
			<%-- 			<select name="color" id="listLane">
				<c:set var="wallIndex" value="${wallIndex}" scope="request" />
				<c:set var="i"  value="0" scope="page" />
				<c:forEach var="listLane" items="${club.getList_wall().get(wallIndex).getList_lane()}">
					<option value="${i}">${listLane.getColor()}</option>
					<c:set var="i"  value="${i + 1}" scope="page" />
				</c:forEach>
			</select> --%>
			<select name="color">
					<option  value="rouge">Rouge</option>
					<option value="bleu">Bleu</option>
					<option value="noir">Noir</option>
					<option value="jaune">Jaune</option>
					<option value="marron">Marron</option>
					<option value="vert">Vert</option>
					</select> 
					<label>Contenu du message :</label>
					<textarea   rows="5" cols="33" name="subject"></textarea>
           <label>Fichiers sélectionnés :</label>
            <input type="file" name="photo" accept="image/*" multiple
                   onchange="readFilesAndDisplayPreview(this.files);"/><br/>
            <input type="submit" value="Upload" /><br/>
            <div id="list"></div>
        </form>
        	<p>
		<c:set var="checkPhoto" value="${checkPhoto }" scope="request"/>
		<c:if test="${checkPhoto == false}">
			<c:out value="Veuillez ajoutez des photos avant d'envoyer votre demande"/>
		</c:if>
		<c:set var="checkForm" value="${checkForm }" scope="request"/>
		<c:if test="${checkForm == false}">
			<c:out value="Veuillez remplir le formulaire correctement"/>
		</c:if>
		<c:if test="${checkForm == true}">
			<c:out value="Votre demande a bien été envoyé"/>
		</c:if>
	</p>
    </body>
</html>