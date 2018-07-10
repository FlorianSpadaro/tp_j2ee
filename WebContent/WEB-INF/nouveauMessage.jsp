<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Nouveau Message</title>
	<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
</head>
<body>
	<c:import url="/inc/conseiller/navbar.jsp"></c:import>
	<br/>
	<div class="container" >
		<h3 class="display-3">
		Nouveau Message: 
		<c:if test="${ empty sessionScope.client }">${ requestScope.client.nom } ${ requestScope.client.prenom }</c:if>
		<c:if test="${ empty sessionScope.conseiller }">${ requestScope.conseiller.nom } ${ requestScope.conseiller.prenom }</c:if>
		</h3>
		<form class="form" action="<c:url value='/conseiller/client/message' />" method="POST">
			<input type="hidden" name="conseiller" value="${ requestScope.conseiller.id }" />
			<input type="hidden" name="client" value="${ requestScope.client.id }" />
			<div class="form-group">
				<label>Sujet: </label>
				<input type="text" name="sujet" class="form-control" />
			</div>
			<div class="form-group">
				<label>Contenu: </label>
				<textarea name="contenu" class="form-control">
				</textarea>
			</div>
			<button class="btn btn-primary" >Envoyer</button>
		</form>
	</div>
</body>
</html>