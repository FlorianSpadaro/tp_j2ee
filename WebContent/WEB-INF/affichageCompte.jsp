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
		<div class="row float-right d-none d-lg-block">
			<button class="btn btn-primary">Modifier</button>
			<button class="btn btn-danger" style="margin-left: 10px;">Supprimer</button>
		</div>
		<div class="row">
			<div class="jumbotron offset-md-3 col-md-6">
				<h5 class="display-5">Informations du compte</h5>
				<table>
					<tr>
						<th class="font-weight-bold">Propriétaire: </th>
						<td>${ requestScope.client.nom } ${ requestScope.client.prenom }</td>
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
		
	</div>
</body>
</html>