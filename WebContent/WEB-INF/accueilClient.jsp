<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Accueil Client</title>
	<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
</head>
<body>
	<c:import url="/inc/client/navbar.jsp"></c:import>
	<br/>
	<div class="row" style="justify-content: center;">
		<div class="col-xs-12" >
			<div class="jumbotron">
				<h1 class="display-1">Bienvenue ${ sessionScope.client.nom } ${ sessionScope.client.prenom }</h1>
			</div>
		</div>
	</div>
</body>
</html>