<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>Connexion</title>
		<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
	</head>
	<body>
		<div class="container">
			<h1 class="display-1">Connexion</h1>
			<div class="jumbotron">
				<ul class="nav nav-pills nav-fill">
				  <li class="nav-item">
				    <a class="nav-link <c:if test="${ (empty requestScope.type || requestScope.type == 'client') }">active</c:if>" href="#clientForm" data-toggle="tab" role="tab" aria-controls="clientForm" aria-selected="${ (empty requestScope.type || requestScope.type == 'client') ? 'true' : 'false' }">Client</a>
				  </li>
				  <li class="nav-item">
				    <a class="nav-link <c:if test="${ requestScope.type == 'conseiller' }">active</c:if>" href="#conseillerForm" data-toggle="tab" role="tab" aria-controls="conseillerForm" aria-selected="${ requestScope.type == 'conseiller' ? 'true' : 'false' }">Conseiller</a>
				  </li>
				</ul>
				<hr/>
				<div class="tab-content" id="myTabContent">
				  <div class="tab-pane fade <c:if test="${ empty requestScope.type || requestScope.type == 'client' }">show active</c:if>" id="clientForm" role="tabpanel" aria-labelledby="client-tab">
				  	<form method="post" action="<c:url value='/client' />">
				  		<input type="hidden" name="connexion" value="true" />
						<input type="hidden" name="type" value="client" />
						<div class="form-group">
							<label for="login" >Nom d'utilisateur</label>
							<input name="login" id="login" type="text" class="form-control" placeholder="Entrez votre nom d'utilisateur" />
							<c:if test="${ requestScope.type == 'client' }"><span class="badge badge-pill badge-danger" >${ requestScope.form.erreurs["login"] }</span></c:if>
						</div>
						<div class="form-group">
							<label for="password" >Mot de passe</label>
							<input name="password" id="password" type="password" class="form-control" placeholder="Entrez votre mot de passe" />
							<c:if test="${ requestScope.type == 'client' }"><span class="badge badge-pill badge-danger" >${ requestScope.form.erreurs["password"] }</span></c:if>
						</div>
						<div>
							<button class="btn btn-primary">Se connecter</button>
						</div>
						<c:if test="${ requestScope.type == 'client' && !empty requestScope.form.resultat }">
							<br/>
							<div class="alert alert-danger" role="alert">
							  ${ requestScope.form.resultat }
							</div>
						</c:if>
					</form>
				  </div>
				  <div class="tab-pane fade <c:if test="${ requestScope.type == 'conseiller' }">show active</c:if>" id="conseillerForm" role="tabpanel" aria-labelledby="conseiller-tab">
				  	<form method="post" action="<c:url value='/conseiller' />">
				  		<input type="hidden" name="connexion" value="true" />
						<input type="hidden" name="type" value="conseiller" />
						<div class="form-group">
							<label for="login" >Nom d'utilisateur</label>
							<input name="login" id="login" type="text" class="form-control" placeholder="Entrez votre nom d'utilisateur" />
							<c:if test="${ requestScope.type == 'conseiller' }"><span class="badge badge-pill badge-danger" >${ requestScope.form.erreurs["login"] }</span></c:if>
						</div>
						<div class="form-group">
							<label for="password" >Mot de passe</label>
							<input name="password" id="password" type="password" class="form-control" placeholder="Entrez votre mot de passe" />
							<c:if test="${ requestScope.type == 'conseiller' }"><span class="badge badge-pill badge-danger" >${ requestScope.form.erreurs["password"] }</span></c:if>
						</div>
						<div>
							<button class="btn btn-primary">Se connecter</button>
						</div>
						<c:if test="${ requestScope.type == 'conseiller' && !empty requestScope.form.resultat }">
							<br/>
							<div class="alert alert-danger" role="alert">
							  ${ requestScope.form.resultat }
							</div>
						</c:if>
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


