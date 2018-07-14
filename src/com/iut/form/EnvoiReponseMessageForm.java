package com.iut.form;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.iut.beans.ReponseMessage;
import com.iut.dao.MessageDao;
import com.iut.enums.DestinataireMessage;

public class EnvoiReponseMessageForm {
	private static final String ATT_MESSAGE		= "message";
	private static final String ATT_REPONSE		= "reponse";
	private static final String ATT_CONSEILLER	= "conseiller";
	
	private HashMap<String, String> erreurs;
	private String resultat;
	private MessageDao messageDao;
	public EnvoiReponseMessageForm(MessageDao messageDao) {
		super();
		this.messageDao = messageDao;
		this.erreurs = new HashMap<>();
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
	
	public void envoyerReponse(HttpServletRequest request)
	{
		String contenu = getValeurChamp(request, ATT_REPONSE);
		
		try {
			validationContenu(contenu);
		}catch(Exception e) {
			setErreur(ATT_REPONSE, e.getMessage());
		}
		
		if(erreurs.isEmpty())
		{
			ReponseMessage reponse = new ReponseMessage();
			reponse.setContenu(contenu);
			reponse.setMessageLie(messageDao.getMessageById(Integer.parseInt(getValeurChamp(request, ATT_MESSAGE))));
			if(request.getSession().getAttribute(ATT_CONSEILLER) != null)
			{
				reponse.setDestinataire(DestinataireMessage.client);
			}
			else {
				reponse.setDestinataire(DestinataireMessage.conseiller);
			}
			int result = messageDao.createReponseMessage(reponse);
			if(result > 0)
			{
				resultat = "Réponse envoyée avec succès";
			}
			else {
				resultat = "Echec de l'envoi de la réponse";
			}
		}
		else {
			resultat = "Echec de l'envoi de la réponse: Veuillez vérifier vos informations";
		}
	}
	
	private void validationContenu(String contenu) throws Exception{
		if(contenu == null)
		{
			throw new Exception("Merci de saisir un contenu");
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
