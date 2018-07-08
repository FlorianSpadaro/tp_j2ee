<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Modification Compte</title>
	<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
</head>
<body>
	<c:import url="/inc/conseiller/navbar.jsp"></c:import>
	<br/>
	<div class="container" >
		<h1 class="display-1">Modification Compte</h1>
		<c:if test="${ !empty requestScope.form }">
			<div class="alert alert-info" role="alert">
				${ requestScope.form.resultat }
			</div>
		</c:if>
		
		<form class="form" method="POST" action="<c:url value='/conseiller/client/compte/modifier' />">
			<input type="hidden" name="compte" value="${ compte.id }" />
			<div class="form-group">
				<label>Propriétaire 1: </label>
				<select name="proprietaire1" class="form-control">
					<c:forEach items="${ listeClients }" var="client">
						<option style="color: black;" value="${ client.id }" <c:if test="${ client.id == compte.proprietaire1.id }">selected</c:if> >
							${ client.nom } ${ client.prenom }
						</option>
					</c:forEach>
				</select>
			</div>
			<div class="form-group">
				<label>Propriétaire 2: </label>
				<select name="proprietaire2" class="form-control">
					<option style="color: gray;" value="0">Aucun</option>
					<c:forEach items="${ listeClients }" var="client">
						<option style="color: black;" value="${ client.id }" <c:if test="${ client.id == compte.proprietaire2.id }">selected</c:if> >
							${ client.nom } ${ client.prenom }
						</option>
					</c:forEach>
				</select>
			</div>
			<div class="form-group">
				<label>Libellé: </label>
				<input type="text" class="form-control" name="libelle" value="${ compte.libelle }" />
				<label class="badge badge-danger">${ requestScope.form.erreurs["libelle"] }</label>
			</div>
			<div class="form-group">
				<label>Montant: </label>
				<input type="text" class="form-control" name="montant" value="${ compte.montant }" />
				<label class="badge badge-danger">${ requestScope.form.erreurs["montant"] }</label>
			</div>
			<div class="form-group">
				<label>Découvert maximum: </label>
				<input type="text" class="form-control" name="decouvert" value="${ compte.decouvertMax }" />
				<label class="badge badge-danger">${ requestScope.form.erreurs["decouvert"] }</label>
			</div>
			<div>
				<button type="submit" class="btn btn-success" >Valider</button>
			</div>
		</form>
	</div>
</body>
</html>