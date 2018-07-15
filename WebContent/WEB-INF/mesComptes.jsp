<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Mes Comptes</title>
	<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
	<style>
		th {
			font-weight: bold;
		}
	</style>
</head>
<body>
	<c:import url="/inc/client/navbar.jsp"></c:import>
	<br/>
	<div class="container" >
		<h1 class="display-1" >Mes Comptes</h1>
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
		  		<c:forEach items="${ requestScope.comptes }" var="compte">
		  			<div class="card" style="width: 18rem; margin: 5px">
					  <div class="card-body">
					    <h5 class="card-title">
					    	<form class="form-inline" method="POST" action="<c:url value='/client/comptes/compte' />" style="justify-content: center;">
					    		<input type="hidden" name="compte" value="${ compte.id }" />
					    		<input type="hidden" name="client" value="${ sessionScope.client.id }" />
					    		<button class="btn btn-link">${ compte.libelle }</button>
					    	</form>
					    </h5>
					    <h6 class="card-subtitle mb-2 text-muted">
					    	<c:if test="${ compte.proprietaire1.id == sessionScope.client.id }">
					    		<c:if test="${ empty compte.proprietaire2 }">
					    			Aucun autre propriétaire
					    		</c:if>
					    		<c:if test="${ !empty compte.proprietaire2 }">
							    	${ compte.proprietaire2.nom } ${ compte.proprietaire2.prenom }
					    		</c:if>
					    	</c:if>
					    	<c:if test="${ compte.proprietaire2.id == sessionScope.client.id }">
					    		${ compte.proprietaire1.nom } ${ compte.proprietaire1.prenom }
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
					    						${ transaction.compteDebiteur.proprietaire1.nom } ${ transaction.compteDebiteur.proprietaire1.prenom }
					    						<c:if test="${ !empty transaction.compteDebiteur.proprietaire2 }">
					    							${ transaction.compteDebiteur.proprietaire2.nom } ${ transaction.compteDebiteur.proprietaire2.prenom }
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
					    						${ transaction.compteCrediteur.proprietaire1.nom } ${ transaction.compteCrediteur.proprietaire1.prenom }
					    						<c:if test="${ !empty transaction.compteCrediteur.proprietaire2 }">
					    							${ transaction.compteCrediteur.proprietaire2.nom } ${ transaction.compteCrediteur.proprietaire2.prenom }
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