<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Accueil Conseiller</title>
	<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
</head>
<body>
	<c:import url="/inc/conseiller/navbar.jsp"></c:import>
	<br/>
	<div class="container" >
		<div class="row">
			<div class="col-xs-12" >
				<div class="jumbotron"&>
					<h1 class="display-1">Bienvenue ${ sessionScope.conseiller.nom } ${ sessionScope.conseiller.prenom }</h1>
				</div>
			</div>
		</div>
	</div>
</body>
</html>