<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Client</title>
	<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
	<style>
		th {
			font-weight: bold;
		}
	</style>
</head>
<body>
	<c:import url="/inc/conseiller/navbar.jsp"></c:import>
	<br/>
	<div class="container" >
		<div class="row">
			<div class="col-md-12" >
				<form class="form" method="GET" action="<c:url value='/conseiller/client/message' />">
					<input type="hidden" name="client" value="${ requestScope.client.id }" />
					<button class="btn btn-info float-right" >Ecrire Message</button>
				</form>
				<form method="GET" action="<c:url value='/conseiller/creationCompte' />" class="form-inline float-right">
					<input type="hidden" name="client" value="${ client.id }" />
					<button class="btn btn-success" style="margin-right: 10px" >Nouveau Compte</button>
				</form>
			</div>
		</div>
		<h1 class="display-1" >${ requestScope.client.nom } ${ requestScope.client.prenom }</h1>
		<br/>
		<ul class="nav nav-pills nav-fill" id="pills-tab" role="tablist">
		  <li class="nav-item">
		    <a class="nav-link active" id="pills-comptes-tab" data-toggle="pill" href="#pills-comptes" role="tab" aria-controls="pills-comptes" aria-selected="true">Comptes</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link" id="pills-transactions-tab" data-toggle="pill" href="#pills-transactions" role="tab" aria-controls="pills-transactions" aria-selected="false">Dernières Transactions</a>
		  </li>
		</ul>
		<br/>
		<div class="tab-content" id="pills-tabContent">
		  <div class="tab-pane fade show active" id="pills-comptes" role="tabpanel" aria-labelledby="pills-comptes-tab">
		  	<div class="row" style="justify-content: center;">
		  		<c:forEach items="${ requestScope.client.comptes }" var="compte">
		  			<div class="card" style="width: 18rem; margin: 5px">
					  <div class="card-body">
					    <h5 class="card-title">
					    	<form class="form-inline" method="POST" action="<c:url value='/conseiller/client/compte' />" style="justify-content: center;">
					    		<input type="hidden" name="compte" value="${ compte.id }" />
					    		<input type="hidden" name="client" value="${ client.id }" />
					    		<button class="btn btn-link">${ compte.libelle }</button>
					    	</form>
					    </h5>
					    <h6 class="card-subtitle mb-2 text-muted">
					    	<c:if test="${ compte.proprietaire1.id == requestScope.client.id }">
					    		<c:if test="${ empty compte.proprietaire2 }">
					    			Aucun autre propriétaire
					    		</c:if>
					    		<c:if test="${ !empty compte.proprietaire2 }">
					    			<form class="form-inline" action="<c:url value='/conseiller/client/infos' />" method="POST">
							    		<input type="hidden" name="client" value='${ compte.proprietaire2.id }' />
							    		<button class="btn btn-link" style="color: black">${ compte.proprietaire2.nom } ${ compte.proprietaire2.prenom }</button>
							    	</form>
					    		</c:if>
					    	</c:if>
					    	<c:if test="${ compte.proprietaire2.id == requestScope.client.id }">
					    		<form class="form-inline" action="<c:url value='/conseiller/client/infos' />" method="POST">
						    		<input type="hidden" name="client" value='${ compte.proprietaire1.id }' />
						    		<button class="btn btn-link" style="color: black">${ compte.proprietaire1.nom } ${ compte.proprietaire1.prenom }</button>
						    	</form>
					    	</c:if>
						</h6>
						<hr/>
					    <p class="card-text text-center" style="color: blue">
					    	${ compte.montant }&euro;
					    </p>
					  </div>
					</div>
		  		</c:forEach>
		  	</div>
		  </div>
		  <div class="tab-pane fade" id="pills-transactions" role="tabpanel" aria-labelledby="pills-transactions-tab">
		  	<div class="row" style="justify-content: center;">
		  		<c:forEach items="${ requestScope.transactions }" var="transaction">
			  		<div class="card col-md-3"  style="margin: 5px;">
					  <div class="card-body">
					    <h5 class="card-title  text-center" style="color: blue;">
					    	${ transaction.montant }&euro;
					    </h5>
					    <h6 class="card-subtitle mb-2 text-muted  text-center">${ transaction.dateAffiche }</h6>
					    <p class="card-text">
					    	<div>
					    		<div class="text-center">
					    			<label class="badge badge-secondary">Compte débiteur</label>
					    		</div>
					    		<c:if test="${ empty transaction.compteDebiteur }">
					    			<div class="text-center">
					    				<label class="badge badge-warning">Versement</label>
					    			</div>
					    		</c:if>
					    		<c:if test="${ !empty transaction.compteDebiteur }">
					    			<table>
					    				<tr>
					    					<th>Propriétaire: </th>
					    					<td>
					    						<form class="form-inline" action="<c:url value='/conseiller/client/infos' />" method="POST">
										    		<input type="hidden" name="client" value='${ transaction.compteDebiteur.proprietaire1.id }' />
										    		<button class="btn btn-link" style="color: black">${ transaction.compteDebiteur.proprietaire1.nom } ${ transaction.compteDebiteur.proprietaire1.prenom }</button>
										    	</form>
					    						<c:if test="${ !empty transaction.compteDebiteur.proprietaire2 }">
					    							<form class="form-inline" action="<c:url value='/conseiller/client/infos' />" method="POST">
											    		<input type="hidden" name="client" value='${ transaction.compteDebiteur.proprietaire2.id }' />
											    		<button class="btn btn-link" style="color: black">${ transaction.compteDebiteur.proprietaire2.nom } ${ transaction.compteDebiteur.proprietaire2.prenom }</button>
											    	</form>
					    						</c:if>
					    					</td>
					    				</tr>
					    				<tr>
					    					<th>Compte: </th>
					    					<td>${ transaction.compteDebiteur.libelle }</td>
					    				</tr>
					    			</table>
					    		</c:if>
					    	</div>
					    	<hr/>
					    	<div>
					    		<div class="text-center">
					    			<label class="badge badge-secondary">Compte créditeur</label>
					    		</div>
					    		<c:if test="${ empty transaction.compteCrediteur }">
					    			<div class="text-center">
					    				<label class="badge badge-warning">Retrait</label>
					    			</div>
					    		</c:if>
					    		<c:if test="${ !empty transaction.compteCrediteur }">
					    			<table>
					    				<tr>
					    					<th>Propriétaire: </th>
					    					<td>
					    						<form class="form-inline" action="<c:url value='/conseiller/client/infos' />" method="POST">
										    		<input type="hidden" name="client" value='${ transaction.compteCrediteur.proprietaire1.id }' />
										    		<button class="btn btn-link" style="color: black">${ transaction.compteCrediteur.proprietaire1.nom } ${ transaction.compteCrediteur.proprietaire1.prenom }</button>
										    	</form>
					    						<c:if test="${ !empty transaction.compteCrediteur.proprietaire2 }">
					    							<form class="form-inline" action="<c:url value='/conseiller/client/infos' />" method="POST">
											    		<input type="hidden" name="client" value='${ transaction.compteCrediteur.proprietaire2.id }' />
											    		<button class="btn btn-link" style="color: black">${ transaction.compteCrediteur.proprietaire2.nom } ${ transaction.compteCrediteur.proprietaire2.prenom }</button>
											    	</form>
					    						</c:if>
					    					</td>
					    				</tr>
					    				<tr>
					    					<th>Compte: </th>
					    					<td>${ transaction.compteCrediteur.libelle }</td>
					    				</tr>
					    			</table>
					    		</c:if>
					    	</div>
					    </p>
					  </div>
					</div>
			  	</c:forEach>
		  	</div>
		  </div>
		</div>
	</div>

	<!-- SCRIPTS -->
	<script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>


</body>
</html>