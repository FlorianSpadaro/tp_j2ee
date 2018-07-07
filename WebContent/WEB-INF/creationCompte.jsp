<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Création Compte</title>
	<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
</head>
<body>
	<c:import url="/inc/conseiller/navbar.jsp"></c:import>
	<div class="container">
		<h1 class="display-1">Nouveau Compte: ${ requestScope.client.nom } ${ requestScope.client.prenom }</h1>
		<form class="form"  autocomplete="off" method="POST" action="<c:url value='/conseiller/creationCompte' />">
			<input type="hidden" name="client" value="${ requestScope.client.id }" />
			<div class="form-group">
				<label>Libellé: </label>
				<input type="text" name="libelle" id="libelle" class="form-control" />
				<span class="badge badge-danger">${ requestScope.form.erreurs["libelle"] }</span>
			</div>
			<div class="form-group">
				<label>Montant: </label>
				<input type="text" name="montant" id="montant" class="form-control" />
				<span class="badge badge-danger">${ requestScope.form.erreurs["montant"] }</span>
			</div>
			<div class="form-group">
				<label>Découvert maximum: </label>
				<input type="text" name="decouvert" id="decouvert" class="form-control" value="0" />
				<span class="badge badge-danger">${ requestScope.form.erreurs["decouvert"] }</span>
			</div>
			<div class="form-group">
				<label>Autre titulaire: </label>
				<select name="autreTitulaire" id="autreTitulaire" class="form-control">
					<option value="0" style="color: gray;">Aucun</option>
					<c:forEach items="${ requestScope.listeClients }" var="ceClient">
						<option value="${ ceClient.id }" style="color: black;">${ ceClient.nom } ${ ceClient.prenom }</option>
					</c:forEach>
				</select>
			</div>
			<button class="btn btn-success">Valider</button>
		</form>
		<br/>
		<c:if test="${ !empty requestScope.form.resultat }">
			<div class="alert alert-success" role="alert">
			  ${ requestScope.form.resultat }
			</div>
		</c:if>
	</div>
</body>
</html>