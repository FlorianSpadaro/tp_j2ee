<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Mon compte</title>
	<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
</head>
<body>
	<c:import url="/inc/client/navbar.jsp"></c:import>
	<br/>
	
	<div class="container">
		<c:if test="${ !empty requestScope.form }">
			<div class="alert alert-info">
				${ requestScope.form.resultat }
			</div>
		</c:if>
		<div class="row">
			<div class="jumbotron offset-md-3 col-md-6">
				<h5 class="display-5">Informations du compte</h5>
				<table>
					<tr>
						<th class="font-weight-bold">Propriétaire(s): </th>
						<td>
							${ requestScope.compte.proprietaire1.nom } ${ requestScope.compte.proprietaire1.prenom }
							<c:if test="${ !empty requestScope.compte.proprietaire2 }">
								<br/>
								${ requestScope.compte.proprietaire2.nom } ${ requestScope.compte.proprietaire2.prenom }
							</c:if>
						</td>
					</tr>
					<tr>
						<th class="font-weight-bold">Libellé: </th>
						<td>${ requestScope.compte.libelle }</td>
					</tr>
					<tr>
						<th class="font-weight-bold">Montant: </th>
						<td>${ requestScope.compte.montant }</td>
					</tr>
					<tr>
						<th class="font-weight-bold">Découvert maximum: </th>
						<td>${ requestScope.compte.decouvertMax }</td>
					</tr>
				</table>
			</div>
		</div>
		
		<div class="row">
			<h2 class="display-2">Transactions</h2>
		</div>
		<button class="btn btn-success" data-toggle="modal" data-target="#modaleRetirer">Retirer de l'argent</button>
		<br/><br/>
		<ul class="nav nav-pills nav-fill" id="pills-tab" role="tablist">
		  <li class="nav-item">
		    <a class="nav-link active" id="pills-credit-tab" data-toggle="pill" href="#pills-credit" role="tab" aria-controls="pills-credit" aria-selected="true">Crédits</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link" id="pills-debit-tab" data-toggle="pill" href="#pills-debit" role="tab" aria-controls="pills-debit" aria-selected="false">Débits</a>
		  </li>
		</ul>
		<br/>
		<div class="tab-content" id="pills-tabContent">
		  <div class="tab-pane fade show active" id="pills-credit" role="tabpanel" aria-labelledby="pills-credit-tab">
		  	<div class="row" style="justify-content: center;">
		  		<c:forEach items="${ requestScope.compte.credits }" var="transaction">
			  		<div class="card col-md-3" style="margin: 5px">
					  <div class="card-body">
					    <h5 class="card-title text-center" style="color: green;">
					    	+${ transaction.montant }&euro;
					    </h5>
					    <h6 class="card-subtitle mb-2 text-muted  text-center">${ transaction.dateAffiche }</h6>
					    <p class="card-text">
					    	<c:if test="${ !empty transaction.compteDebiteur }">
					    		<h6 class="display-6 text-center"><label class="badge badge-secondary">Correspondant: </label></h6>
					    		${ transaction.compteDebiteur.proprietaire1.nom } ${ transaction.compteDebiteur.proprietaire1.prenom }
						    	<c:if test="${ !empty transaction.compteDebiteur.proprietaire2 }">
									${ transaction.compteDebiteur.proprietaire2.nom } ${ transaction.compteDebiteur.proprietaire2.prenom }
								</c:if>
								<hr/>
								<div>
									<label class="font-weight-bold">Compte: </label> ${ transaction.compteDebiteur.libelle }
								</div>
					    	</c:if>
					    	<c:if test="${ empty transaction.compteDebiteur }">
					    		<div class="text-center">
					    			<label class="badge badge-warning">Versement</label>
					    		</div>
					    	</c:if>
					    </p>
					  </div>
					</div>
			  	</c:forEach>
		  	</div>
		  </div>
		  <div class="tab-pane fade" id="pills-debit" role="tabpanel" aria-labelledby="pills-debit-tab">
		  	<div class="row" style="justify-content: center;">
		  		<c:forEach items="${ requestScope.compte.debits }" var="transaction">
			  		<div class="card col-md-3"  style="margin: 5px">
					  <div class="card-body">
					    <h5 class="card-title  text-center" style="color: red;">
					    	-${ transaction.montant }&euro;
					    </h5>
					    <h6 class="card-subtitle mb-2 text-muted  text-center">${ transaction.dateAffiche }</h6>
					    <p class="card-text">
					    	<c:if test="${ !empty transaction.compteCrediteur}">
					    		<h6 class="display-6 text-center"><label class="badge badge-secondary">Correspondant: </label></h6>
					    		${ transaction.compteCrediteur.proprietaire1.nom } ${ transaction.compteCrediteur.proprietaire1.prenom }
						    	<c:if test="${ !empty transaction.compteCrediteur.proprietaire2 }">
									${ transaction.compteCrediteur.proprietaire2.nom } ${ transaction.compteCrediteur.proprietaire2.prenom }
								</c:if>
								<hr/>
								<div>
									<label class="font-weight-bold">Compte: </label> ${ transaction.compteCrediteur.libelle }
								</div>
					    	</c:if>
					    	<c:if test="${ empty transaction.compteCrediteur }">
					    		<div class="text-center">
					    			<label class="badge badge-warning">Retrait</label>
					    		</div>
					    	</c:if>
					    </p>
					  </div>
					</div>
			  	</c:forEach>
		  	</div>
		  </div>
		</div>		
	</div>
	<br/>
	
	<div class="modal fade" id="modaleRetirer" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
	  <div class="modal-dialog modal-dialog-centered" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLongTitle">Retirer de l'argent</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	        <form class="form" id="retraitForm" method="POST" action="<c:url value='/client/comptes/compte/retrait' />">
	        	<input type="hidden" name="compte" value="${ requestScope.compte.id }" />
	        	<div class="form-group">
	        		<label>Montant: </label>
	        		<input type="text" class="form-control" name="montant" required />
	        	</div>
	        </form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">Annuler</button>
	        <button type="button" class="btn btn-primary" id="validerRetrait">Valider</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<!-- SCRIPTS -->
	<script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>
	<script>
		$("#validerRetrait").click(function(){
			$("#retraitForm").submit();
		});
	</script>
</body>
</html>