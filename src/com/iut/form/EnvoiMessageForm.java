package com.iut.form;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.iut.beans.Message;
import com.iut.dao.ClientDao;
import com.iut.dao.ConseillerDao;
import com.iut.dao.MessageDao;
import com.iut.enums.DestinataireMessage;

public class EnvoiMessageForm {
	private static final String ATT_CLIENT			= "client";
	private static final String ATT_CONSEILLER		= "conseiller";
	private static final String ATT_DESTINATAIRE	= "destinataire";
	private static final String ATT_SUJET			= "sujet";
	private static final String ATT_CONTENU			= "contenu";
	
	private HashMap<String, String> erreurs;
	private String resultat;
	private MessageDao messageDao;
	private ClientDao clientDao;
	private ConseillerDao conseillerDao;
	
	public EnvoiMessageForm(MessageDao messageDao, ClientDao clientDao, ConseillerDao conseillerDao) {
		super();
		this.messageDao = messageDao;
		this.clientDao = clientDao;
		this.conseillerDao = conseillerDao;
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
	
	/**
	 * Fonction qui crée un nouveau Message
	 */
	public void envoyerMessage(HttpServletRequest request)
	{
		String sujet = getValeurChamp(request, ATT_SUJET);
		String contenu = getValeurChamp(request, ATT_CONTENU);
		
		try {
			validationSujet(sujet);
		}catch(Exception e) {
			setErreur(ATT_SUJET, e.getMessage());
		}
		
		try {
			validationContenu(contenu);
		}catch(Exception e) {
			setErreur(ATT_CONTENU, e.getMessage());
		}
		
		//Si les infos saisies sont correctes, alors on continue le traitement
		if(erreurs.isEmpty())
		{
			Message message = new Message();
			message.setSujet(sujet);
			message.setContenu(contenu);
			message.setClient(clientDao.getClientById(Integer.parseInt(getValeurChamp(request, ATT_CLIENT))));
			message.setConseiller(conseillerDao.getConseillerById(Integer.parseInt(getValeurChamp(request, ATT_CONSEILLER))));
			if(getValeurChamp(request, ATT_DESTINATAIRE).equals("conseiller"))
			{
				message.setDestinataire(DestinataireMessage.conseiller);
			}
			else {
				message.setDestinataire(DestinataireMessage.client);
			}
			
			int result = messageDao.createMessage(message);
			if(result > 0)
			{
				resultat = "Envoi du message avec succès.";
			}
			else {
				resultat = "Echec de l'envoi du message";
			}
		}else {
			resultat = "Echec d'envoi de message: Veuillez vérifier vos informations.";
		}
	}
	
	/**
	 * Fonction qui valide le sujet du Message
	 */
	private void validationSujet(String sujet) throws Exception{
		if(sujet == null)
		{
			throw new Exception("Merci de saisir un sujet");
		}
	}
	
	/**
	 * Fonction qui valide le contenu du Message
	 */
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
