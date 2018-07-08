<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Affichage Compte</title>
	<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
</head>
<body>
	<c:import url="/inc/conseiller/navbar.jsp"></c:import>
	<br/>
	<div class="container">
		<c:if test="${ !empty requestScope.resultat }">
			<div class="alert alert-danger">
				${ requestScope.resultat }
			</div>
		</c:if>
		<div class="row float-right d-none d-lg-block">
			<form class="form-inline float-right" method="POST" action="<c:url value='/conseiller/client/compte/supprimer' />">
				<input type="hidden" name="compte" value="${ compte.id }" />
				<input type="hidden" name="client" value="${ client.id }" />
				<button class="btn btn-danger" style="margin-left: 10px;" id="supprimerCompte">Désactiver</button>
			</form>
			<form class="form-inline float-right" method="GET" action="<c:url value='/conseiller/client/compte/modifier' />">
				<input type="hidden" name="compte" value="${ compte.id }" />
				<input type="hidden" name="client" value="${ client.id }" />
				<button class="btn btn-primary">Modifier</button>
			</form>
		</div>
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
		<div class="row">
			<form class="form-inline" method="POST" action="<c:url value='/conseiller/client/compte/transaction' />">
				<button class="btn btn-success">Nouvelle transaction</button>
			</form>
		</div>
		<br/>
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
			  		<div class="card col-md-3">
					  <div class="card-body">
					    <h5 class="card-title text-center" style="color: green;">
					    	+${ transaction.montant }&euro;
					    </h5>
					    <h6 class="card-subtitle mb-2 text-muted  text-center">${ transaction.dateAffiche }</h6>
					    <p class="card-text">
					    	<h6 class="display-6 text-center"><label class="badge badge-secondary">Correspondant: </label></h6>
					    	${ transaction.compteCorrespondant.proprietaire1.nom } ${ transaction.compteCorrespondant.proprietaire1.prenom }
					    	<c:if test="${ !empty transaction.compteCorrespondant.proprietaire2 }">
								<br/>
								${ transaction.compteCorrespondant.proprietaire2.nom } ${ transaction.compteCorrespondant.proprietaire2.prenom }
							</c:if>
							<hr/>
							<div>
								<label class="font-weight-bold">Compte: </label> ${ transaction.compteCorrespondant.libelle }
							</div>
					    </p>
					  </div>
					</div>
			  	</c:forEach>
		  	</div>
		  </div>
		  <div class="tab-pane fade" id="pills-debit" role="tabpanel" aria-labelledby="pills-debit-tab">
		  	<div class="row" style="justify-content: center;">
		  		<c:forEach items="${ requestScope.compte.debits }" var="transaction">
			  		<div class="card col-md-3">
					  <div class="card-body">
					    <h5 class="card-title  text-center" style="color: red;">
					    	-${ transaction.montant }&euro;
					    </h5>
					    <h6 class="card-subtitle mb-2 text-muted  text-center">${ transaction.dateAffiche }</h6>
					    <p class="card-text">
					    	<h6 class="display-6 text-center"><label class="badge badge-secondary">Correspondant: </label></h6>
					    	${ transaction.compteCorrespondant.proprietaire1.nom } ${ transaction.compteCorrespondant.proprietaire1.prenom }
					    	<c:if test="${ !empty transaction.compteCorrespondant.proprietaire2 }">
								<br/>
								${ transaction.compteCorrespondant.proprietaire2.nom } ${ transaction.compteCorrespondant.proprietaire2.prenom }
							</c:if>
							<hr/>
							<div>
								<label class="font-weight-bold">Compte: </label> ${ transaction.compteCorrespondant.libelle }
							</div>
					    </p>
					  </div>
					</div>
			  	</c:forEach>
		  	</div>
		  </div>
		</div>		
	</div>
	<br/>
	
	<!-- SCRIPTS -->
	<script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>
	<script>
		$(function(){
			$("#supprimerCompte").click(function(e){
				var reponse = confirm("Voulez-vous vraiment désactiver ce compte?");
				if(!reponse)
				{
					e.preventDefault();
				}
			});
		});
	</script>
</body>
</html>