<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Mon Agence</title>
	<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
</head>
<body>
	<c:import url="/inc/client/navbar.jsp"></c:import>
	<div class="container">
		<h2 class="display-2">Agence: ${ requestScope.agence.libelle }</h2>
		<table class="table">
			<tr>
				<th>Mon conseiller: </th>
				<td>
					${ requestScope.conseiller.nom } ${ requestScope.conseiller.prenom }
					<button class="btn btn-primary btn-sm">Envoyer un message</button>
				</td>
			</tr>
			<tr>
				<th>Téléphone: </th>
				<td>${ requestScope.agence.telephone }</td>
			</tr>
			<tr>
				<th>Adresse: </th>
				<td>${ requestScope.agence.adresse }</td>
			</tr>
			<tr>
				<th>Code Postal: </th>
				<td>${ requestScope.agence.codePostal }</td>
			</tr>
			<tr>
				<th>Ville: </th>
				<td>${ requestScope.agence.ville }</td>
			</tr>
			<tr>
				<th>Horaires Lundi: </th>
				<td>
					<c:if test="${ empty requestScope.agence.lundiOuverture }">
						Fermé
					</c:if>
					<c:if test="${ !empty requestScope.agence.lundiOuverture }">
						${ requestScope.agence.lundiOuverture } - ${ requestScope.agence.lundiFermeture }
					</c:if>
				</td>
			</tr>
			<tr>
				<th>Horaires Mardi: </th>
				<td>
					<c:if test="${ empty requestScope.agence.mardiOuverture }">
						Fermé
					</c:if>
					<c:if test="${ !empty requestScope.agence.mardiOuverture }">
						${ requestScope.agence.mardiOuverture } - ${ requestScope.agence.mardiFermeture }
					</c:if>
				</td>
			</tr>
			<tr>
				<th>Horaires Mercredi: </th>
				<td>
					<c:if test="${ empty requestScope.agence.mercrediOuverture }">
						Fermé
					</c:if>
					<c:if test="${ !empty requestScope.agence.mercrediOuverture }">
						${ requestScope.agence.mercrediOuverture } - ${ requestScope.agence.mercrediFermeture }
					</c:if>
				</td>
			</tr>
			<tr>
				<th>Horaires Jeudi: </th>
				<td>
					<c:if test="${ empty requestScope.agence.jeudiOuverture }">
						Fermé
					</c:if>
					<c:if test="${ !empty requestScope.agence.jeudiOuverture }">
						${ requestScope.agence.jeudiOuverture } - ${ requestScope.agence.jeudiFermeture }
					</c:if>
				</td>
			</tr>
			<tr>
				<th>Horaires Vendredi: </th>
				<td>
					<c:if test="${ empty requestScope.agence.vendrediOuverture }">
						Fermé
					</c:if>
					<c:if test="${ !empty requestScope.agence.vendrediOuverture }">
						${ requestScope.agence.vendrediOuverture } - ${ requestScope.agence.vendrediFermeture }
					</c:if>
				</td>
			</tr>
			<tr>
				<th>Horaires Samedi: </th>
				<td>
					<c:if test="${ empty requestScope.agence.samediOuverture }">
						Fermé
					</c:if>
					<c:if test="${ !empty requestScope.agence.samediOuverture }">
						${ requestScope.agence.samediOuverture } - ${ requestScope.agence.samediFermeture }
					</c:if>
				</td>
			</tr>
			<tr>
				<th>Horaires Dimanche: </th>
				<td>
					<c:if test="${ empty requestScope.agence.dimancheOuverture }">
						Fermé
					</c:if>
					<c:if test="${ !empty requestScope.agence.dimancheOuverture }">
						${ requestScope.agence.dimancheOuverture } - ${ requestScope.agence.dimancheFermeture }
					</c:if>
				</td>
			</tr>
			
		</table>
	</div>
</body>
</html>