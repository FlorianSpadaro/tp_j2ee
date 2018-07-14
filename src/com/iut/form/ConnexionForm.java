package com.iut.form;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.iut.beans.Client;
import com.iut.beans.Conseiller;
import com.iut.dao.ClientDao;
import com.iut.dao.ConseillerDao;

public class ConnexionForm {
	private static final String CHAMP_LOGIN = "login";
	private static final String CHAMP_PASSWORD = "password";
	
	private String resultat;
	private Map<String, String> erreurs = new HashMap<>();
	private ClientDao clientDao;
	private ConseillerDao conseillerDao;
	
	public ConnexionForm(ClientDao clientDao) {
		super();
		this.clientDao = clientDao;
	}
	
	public ConnexionForm(ConseillerDao conseillerDao) {
		super();
		this.conseillerDao = conseillerDao;
	}
	public String getResultat() {
		return resultat;
	}
	public void setResultat(String resultat) {
		this.resultat = resultat;
	}
	public Map<String, String> getErreurs() {
		return erreurs;
	}
	public void setErreurs(Map<String, String> erreurs) {
		this.erreurs = erreurs;
	}
	
	/**
	 * Fonction qui retourne le Client correspondant au login et au mot de passe passé en paramètre
	 */
	public Client connecterClient(HttpServletRequest request)
	{
		/* Récupération des champs du formulaire */
		String login = getValeurChamp(request, CHAMP_LOGIN);
		String password = getValeurChamp(request, CHAMP_PASSWORD);
		
		Client client = null;
		
		/* On valide le login et le mot de passe, sinon on remplit la Map erreur */
		try {
			validationLogin(login);
		}catch(Exception e) {
			setErreur(CHAMP_LOGIN, e.getMessage());
		}
		try {
			validationPassword(password);
		}catch(Exception e) {
			setErreur(CHAMP_PASSWORD, e.getMessage());
		}
		
		//Si le login et le mot de passe sont valides, alors on utilise la fonction de connexion du ClientDao
		if(erreurs.isEmpty())
		{
			client = clientDao.connexion(login, EncryptageForm.encryptPassword(password));
			
			if(client == null)
			{
				resultat = "Identifiants incorrects";
			}
		}
		
		return client;
	}
	
	/**
	 * Fonction qui retourne le Conseiller correspondant au login et au mot de passe passé en paramètre
	 */
	public Conseiller connecterConseiller(HttpServletRequest request)
	{
		/* Récupération des champs du formulaire */
		String login = getValeurChamp(request, CHAMP_LOGIN);
		String password = getValeurChamp(request, CHAMP_PASSWORD);
		
		Conseiller conseiller = null;
		
		/* On valide le login et le mot de passe, sinon on remplit la Map erreur */
		try {
			validationLogin(login);
		}catch(Exception e) {
			setErreur(CHAMP_LOGIN, e.getMessage());
		}
		try {
			validationPassword(password);
		}catch(Exception e) {
			setErreur(CHAMP_PASSWORD, e.getMessage());
		}
		
		//Si le login et le mot de passe sont valides, alors on utilise la fonction de connexion du ConseillerDao
		if(erreurs.isEmpty())
		{
			conseiller = conseillerDao.connexion(login, EncryptageForm.encryptPassword(password));
			
			if(conseiller == null)
			{
				resultat = "Identifiants incorrects. Merci de vérifier votre nom d'utilisateur et votre mot de passe";
			}
		}
		
		return conseiller;
	}
	
	/* Valide le login saisi */
    private void validationLogin( String login ) throws Exception {
        if ( login == null ) {
            throw new Exception( "Merci de saisir un login" );
        }
    }
    
    /* Valide le mot de passe saisi */
    private void validationPassword( String password ) throws Exception {
        if ( password == null ) {
            throw new Exception( "Merci de saisir un mot de passe" );
        }
    }
	
	/*
     * Ajoute un message correspondant au champ spécifié à la map des erreurs.
     */
    private void setErreur( String champ, String message ) {
        erreurs.put( champ, message );
    }
	
	/*
     * Méthode utilitaire qui retourne null si un champ est vide, et son contenu
     * sinon.
     */
    private static String getValeurChamp( HttpServletRequest request, String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur;
        }
    }
}
