<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Ma Messagerie</title>
	<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
</head>
<body>
	<c:import url="/inc/conseiller/navbar.jsp"></c:import>
	<br/>
	<div class="container">
		<h1 class="display-1">Ma messagerie</h1>
		
		<br/>
		<ul class="nav nav-pills nav-fill" id="pills-tab" role="tablist">
		  <li class="nav-item">
		    <a class="nav-link active" id="pills-nonLus-tab" data-toggle="pill" href="#pills-nonLus" role="tab" aria-controls="pills-nonLus" aria-selected="true">Messages non lus</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link" id="pills-lus-tab" data-toggle="pill" href="#pills-lus" role="tab" aria-controls="pills-lus" aria-selected="false">Messages lus</a>
		  </li>
		</ul>
		<br/>
		<div class="tab-content" id="pills-tabContent">
		  <div class="tab-pane fade show active" id="pills-nonLus" role="tabpanel" aria-labelledby="pills-nonLus-tab">
		  	<div class="row">
		  		<table class="table">
		  			<tr>
		  				<th>Sujet</th>
		  				<th>Correspondant</th>
		  				<th>Date</th>
		  			</tr>
		  			<c:forEach items="${ requestScope.messagesNonLus }" var="message">
		  				<tr>
		  					<td>
		  						<form class="form-inline" method="POST" action="<c:url value='/conseiller/messagerie/message' />">
		  							<input type="hidden" name="message" value="${ message.id }" />
		  							<button class="btn btn-link">${ message.sujet }</button>
		  						</form>
		  					</td>
		  					<td>
		  						<form class="form-inline" method="POST" action="<c:url value='/conseiller/client/infos' />">
		  							<input type="hidden" name="client" value="${ message.client.id }" />
		  							<button class="btn btn-link">${ message.client.nom } ${ message.client.prenom }</button>
		  						</form>
		  					</td>
		  					<td>${ message.dateDerniereReponseAffiche }</td>
		  				</tr>
		  			</c:forEach>
		  		</table>
		  	</div>
		  </div>
		  <div class="tab-pane fade" id="pills-lus" role="tabpanel" aria-labelledby="pills-lus-tab">
		  	<div class="row">
		  		<table class="table">
		  			<tr>
		  				<th>Sujet</th>
		  				<th>Correspondant</th>
		  				<th>Date</th>
		  			</tr>
		  			<c:forEach items="${ requestScope.messagesLus }" var="message">
		  				<tr>
		  					<td>
		  						<form class="form-inline" method="POST" action="<c:url value='/conseiller/messagerie/message' />">
		  							<input type="hidden" name="message" value="${ message.id }" />
		  							<button class="btn btn-link">${ message.sujet }</button>
		  						</form>
		  					</td>
		  					<td>
		  						<form class="form-inline" method="POST" action="<c:url value='/conseiller/client/infos' />">
		  							<input type="hidden" name="client" value="${ message.client.id }" />
		  							<button class="btn btn-link">${ message.client.nom } ${ message.client.prenom }</button>
		  						</form>
		  					</td>
		  					<td>${ message.dateDerniereReponseAffiche }</td>
		  				</tr>
		  			</c:forEach>
		  		</table>
		  	</div>
		  </div>
		</div>		
	</div>
	<br/>
	
	<!-- SCRIPTS -->
	<script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>
</body>
</html>