<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <a class="navbar-brand" href="<c:url value='/client/accueil' />">Crédit Agrituel</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item active">
        <a class="nav-link" href="<c:url value='/client/accueil' />">Accueil <span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="<c:url value='/client/comptes' />">Mes Comptes</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="<c:url value='/client/messagerie' />">Messagerie</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="<c:url value='/client/agence' />">Mon Agence</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="<c:url value='/client/deconnexion' />" style="color: red;">Déconnexion</a>
      </li>
    </ul>
  </div>
</nav>