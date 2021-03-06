<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Recherche Clients</title>
	<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
</head>
<body>
	<c:import url="/inc/conseiller/navbar.jsp"></c:import>
	<br/>
	<div class="container" >
		<h3 class="display-3">R�sultats pour: ${ requestScope.recherche }</h3>
		<div class="row">
		<c:if test="${ empty clients }">
			<label class="label label-info" >Aucun client ne correspond � cette recherche</label>
		</c:if>
		<c:forEach items="${ requestScope.clients }" var="client">
			<div class="col-sm-6" style="padding-top: 10px" >
				<div class="card">
					<h5 class="card-header">
						<form class="form-inline" method="POST" action="<c:url value='/conseiller/client/infos' />">
							<input type="hidden" name="client" value="${ client.id }" />
							<button class="btn btn-link">${ client.nom } ${ client.prenom }</button>
						</form>
					</h5>
					<div class="card-body">
						<ul class="list-group list-group-flush">
							<c:if test="${ empty client.comptes }">
								<label class="label label-info" >Ce client ne dispose actuellement d'aucun compte</label>
							</c:if>
							<c:forEach items="${ client.comptes }" var="compte">
								<li class="list-group-item">
									<form action="<c:url value='/conseiller/client/compte' />" method="POST" class="form-inline" >
										<input type="hidden" name="compte" value="${ compte.id }" />
										<input type="hidden" name="client" value="${ client.id }" />
										<button class="btn btn-link">${ compte.libelle }</button>
									</form>
								</li>
							</c:forEach>
						  </ul>
					</div>
					<div class="card-footer" >
						<form class="form-inline float-right" method="GET" action="<c:url value='/conseiller/client/message' />">
							<input type="hidden" name="client" value="${ client.id }" />
							<button class="btn btn-sm btn-primary" >Nouveau Message</button>
						</form>
						<form method="GET" action="<c:url value='/conseiller/creationCompte' />" class="form-inline float-right">
							<input type="hidden" name="client" value="${ client.id }" />
							<button class="btn btn-sm btn-success" style="margin-right: 10px" >Nouveau Compte</button>
						</form>
					</div>
				</div>
			</div>
		</c:forEach>
		</div>
	</div>
</body>
</html>