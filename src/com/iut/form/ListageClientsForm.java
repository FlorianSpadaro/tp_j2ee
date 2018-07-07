package com.iut.form;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.iut.beans.Client;
import com.iut.beans.Conseiller;
import com.iut.dao.ClientDao;

public class ListageClientsForm {
	private static final String ATT_SESSION_CONSEILLER	= "conseiller";
	
	private HashMap<String, String> erreurs;
	private String resultat;
	private ClientDao clientDao;
	
	
	public ListageClientsForm(ClientDao clientDao) {
		super();
		this.clientDao = clientDao;
		erreurs = new HashMap<>();
	}

	public HashMap<String, String> getErreurs() {
		return erreurs;
	}


	public void setErreurs(HashMap<String, String> erreurs) {
		this.erreurs = erreurs;
	}


	public String getResultat() {
		return resultat;
	}


	public void setResultat(String resultat) {
		this.resultat = resultat;
	}


	public ArrayList<Client> listerClient(HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		Conseiller conseiller = (Conseiller) session.getAttribute(ATT_SESSION_CONSEILLER);
		if(conseiller == null)
		{
			setErreur(ATT_SESSION_CONSEILLER, "Aucun conseiller en session");
		}
		
		ArrayList<Client> listeClients = clientDao.getClientsByConseiller(conseiller);
		if(listeClients.size() == 0)
		{
			resultat = "Vous n'avez aucun client";
		}
		
		return listeClients;
	}
	
	/*
     * Ajoute un message correspondant au champ sp�cifi� � la map des erreurs.
     */
    private void setErreur( String champ, String message ) {
        erreurs.put( champ, message );
    }
	
	/*
     * M�thode utilitaire qui retourne null si un champ est vide, et son contenu
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
