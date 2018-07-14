<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Message</title>
	<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
</head>
<body>
	<c:import url="/inc/client/navbar.jsp"></c:import>
	<br/>
	<div class="container" >
		<h3 class="display-3">${ requestScope.message.sujet }</h3>
		<h5 class="display-5">${ requestScope.message.conseiller.nom } ${ requestScope.message.conseiller.prenom }</h5>
		<br/>
		<div>
			<div class="row" style="margin-bottom: 10px;">
				<div class="card col-md-8 ${ requestScope.message.destinataire == 'client' ? 'offset-md-4 text-right text-white bg-info' : ''}">
				  <div class="card-header">
				  	<c:if test="${ requestScope.message.destinataire == 'conseiller' }">
				  		Moi
				  	</c:if>
				  	<c:if test="${ requestScope.message.destinataire == 'client' }">
				  		${ requestScope.message.conseiller.nom } ${ requestScope.message.conseiller.prenom }
				  	</c:if>
				  </div>
				  <div class="card-body">
				    <blockquote class="blockquote mb-0">
				      <p>${ requestScope.message.contenu }</p>
				      <footer class="blockquote-footer">${ requestScope.message.dateAffiche }</footer>
				    </blockquote>
				  </div>
				</div>
			</div>
			
			<c:forEach items="${ requestScope.message.reponses }" var="reponse" >
				<div class="row" style="margin-bottom: 10px;">
					<div class="card col-md-8 ${ reponse.destinataire == 'client' ? 'offset-md-4 text-right text-white bg-info' : '' }">
					  <div class="card-header">
					  	<c:if test="${ reponse.destinataire == 'conseiller' }">
					  		Moi
					  	</c:if>
					  	<c:if test="${ reponse.destinataire == 'client' }">
					  		${ requestScope.message.conseiller.nom } ${ requestScope.message.conseiller.prenom }
					  	</c:if>
					  </div>
					  <div class="card-body">
					    <blockquote class="blockquote mb-0">
					      <p>${ reponse.contenu }</p>
					      <footer class="blockquote-footer">${ reponse.dateAffiche }</footer>
					    </blockquote>
					  </div>
					</div>
				</div>
			</c:forEach>
			
		</div>
		
		<div>
			<div class="container">
				<hr/>
				<div style="text-align: center;">
					<span class="badge badge-danger" >${ requestScope.form.erreurs["reponse"] }</span>
				</div>
				<form method="POST" action="<c:url value='/client/messagerie/message' />" class="form-inline col-md-12" style="justify-content: center;">
					<input type="hidden" name="message" value="${ requestScope.message.id }" />
					<textarea name="reponse" class="form-control" style="width: 80%"></textarea>
					<button class="btn btn-success" style="margin-left: 10px">Répondre</button>
				</form>
				<br/>
			</div>
		</div>
		
	</div>
</body>
</html>