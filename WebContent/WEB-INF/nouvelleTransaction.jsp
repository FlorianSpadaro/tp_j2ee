<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Nouvelle Transaction</title>
	<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
</head>
<body>
	<c:import url="/inc/conseiller/navbar.jsp"></c:import>
	<br/>
	<div class="container" >
		<h1 class="display-1">Nouvelle Transaction</h1>
		<c:if test="${ !empty requestScope.form.resultat }">
			<div class="alert alert-info">
				${ requestScope.form.resultat }
			</div>
		</c:if>
		<ul class="nav nav-pills nav-fill" id="pills-tab" role="tablist">
		  <li class="nav-item">
		    <a class="nav-link <c:if test="${ empty requestScope.type }">active</c:if>" id="pills-versement-tab" data-toggle="pill" href="#pills-versement" role="tab" aria-controls="pills-versement" aria-selected="${ empty requestScope.type ? 'true' : 'false' }">Versement</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link <c:if test="${ !empty requestScope.type }">active</c:if>" id="pills-virement-tab" data-toggle="pill" href="#pills-virement" role="tab" aria-controls="pills-virement" aria-selected="${ !empty requestScope.type ? 'true' : 'false' }">Virement</a>
		  </li>
		</ul>
		<br/>
		<div class="tab-content" id="pills-tabContent">
		  <div class="tab-pane fade <c:if test="${ empty requestScope.type }">show active</c:if>" id="pills-versement" role="tabpanel" aria-labelledby="pills-versement-tab">
		  	<div class="row" style="justify-content: center;">
		  		<form class="form" method="POST" action="<c:url value='/conseiller/client/compte/transaction/versement' />">
		  			<input type="hidden" name="compte" value="${ requestScope.compte }" />
		  			<div class="form-group">
		  				<label>Montant:</label>
		  				<input type="text" name="montant" value="0" class="form-control" />
		  				<label class="badge badge-danger">${ requestScope.form.erreurs["montant"] }</label>
		  			</div>
		  			<button class="btn btn-success">Valider</button>
		  		</form>
		  	</div>
		  </div>
		  <div class="tab-pane fade <c:if test="${ !empty requestScope.type }">show active</c:if>" id="pills-virement" role="tabpanel" aria-labelledby="pills-virement-tab">
		  	<div class="row" style="justify-content: center;">
		  		<form class="form" method="POST" action="<c:url value='/conseiller/client/compte/transaction/virement' />">
		  			<input type="hidden" name="compte" value="${ requestScope.compte }" />
		  			<div class="form-group">
		  				<label>Destinataire:</label>
		  				<select class="form-control" name="destinataire">
		  					<c:forEach items="${ listeClients }" var="client">
		  						<optgroup label="${ client.nom } ${ client.prenom }">
		  							<c:forEach items="${ client.comptes }" var="ceCompte">
		  								<option value="${ ceCompte.id }">${ ceCompte.libelle }</option>
		  							</c:forEach>
		  						</optgroup>
		  					</c:forEach>
		  				</select>
		  			</div>
		  			<div class="form-group">
		  				<label>Montant:</label>
		  				<input type="text" name="montantVirement" value="0" class="form-control" />
		  				<label class="badge badge-danger">${ requestScope.form.erreurs["compte"] }</label>
		  				<label class="badge badge-danger">${ requestScope.form.erreurs["montantVirement"] }</label>
		  			</div>
		  			<button class="btn btn-success">Valider</button>
		  		</form>
		  	</div>
		  </div>
		</div>
	</div>
	
	
	<!-- SCRIPTS -->
	<script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>
	
</body>
</html>