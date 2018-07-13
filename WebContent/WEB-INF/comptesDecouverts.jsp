<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Comptes à découverts</title>
	<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
</head>
<body>
	<c:import url="/inc/conseiller/navbar.jsp"></c:import>
	<br/>
	<div class="container" >
		<h1>Comptes découverts de mes clients</h1>
		<c:if test="${ empty comptes }">
			Aucun de vos clients n'a de compte à découvert
		</c:if>
		<div class="row" style="justify-content: center;">
			<c:forEach items="${ requestScope.comptes }" var="compte" >
				<div class="card bg-light mb-3 col-md-3" style="margin: 5px;">
				  <div class="card-header">
				  	<form class="form-inline" action="<c:url value='/conseiller/client/compte' />" method="POST">
				  		<input type="hidden" name="client" value="${ compte.proprietaire1.id }" />
				  		<input type="hidden" name="compte" value="${ compte.id }" />
				  		<button class="btn btn-link">${ compte.libelle }</button>
				  	</form>
				  </div>
				  <div class="card-body">
				    <h5 class="card-title" style="color: red;">${ compte.montant }&euro;</h5>
				    <p class="card-text">
				    	<form class="form-inline" action="<c:url value='/conseiller/client/infos' />" method="POST">
				    		<input type="hidden" name="client" value='${ compte.proprietaire1.id }' />
				    		<button class="btn btn-link" style="color: black">${ compte.proprietaire1.nom } ${ compte.proprietaire1.prenom }</button>
				    	</form>
				    	<c:if test="${ !empty compte.proprietaire2 }">
				    		<form class="form-inline" action="<c:url value='/conseiller/client/infos' />" method="POST">
					    		<input type="hidden" name="client" value='${ compte.proprietaire2.id }' />
					    		<button class="btn btn-link" style="color: black">${ compte.proprietaire2.nom } ${ compte.proprietaire2.prenom }</button>
					    	</form>
				    	</c:if>
				    </p>
				  </div>
				</div>
			</c:forEach>
		</div>
	</div>
</body>
</html>