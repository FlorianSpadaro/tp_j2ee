package com.iut.form;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.iut.beans.Client;
import com.iut.beans.Compte;
import com.iut.dao.ClientDao;
import com.iut.dao.CompteDao;

public class ModificationCompteForm {
	private static final String ATT_COMPTE			= "compte";
	private static final String ATT_PROPRIETAIRE1 	= "proprietaire1";
	private static final String ATT_PROPRIETAIRE2 	= "proprietaire2";
	private static final String ATT_LIBELLE			= "libelle";
	private static final String ATT_MONTANT			= "montant";
	private static final String ATT_DECOUVERT		= "decouvert";
	
	private HashMap<String, String> erreurs;
	private String resultat;
	private CompteDao compteDao;
	private ClientDao clientDao;
	
	public ModificationCompteForm(CompteDao compteDao, ClientDao clientDao) {
		super();
		this.compteDao = compteDao;
		this.clientDao = clientDao;
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
	public void modifierCompte(HttpServletRequest request)
	{
		String libelle = getValeurChamp(request, ATT_LIBELLE);
		String montantString = getValeurChamp(request, ATT_MONTANT);
		String decouvertString = getValeurChamp(request, ATT_DECOUVERT);
		
		try {
			validationLibelle(libelle);
		}catch(Exception e) {
			setErreur(ATT_LIBELLE, e.getMessage());
		}
		try {
			validationMontant(montantString);
		}catch(Exception e) {
			setErreur(ATT_MONTANT, e.getMessage());
		}
		try {
			validationDecouvert(decouvertString);
		}catch(Exception e) {
			setErreur(ATT_DECOUVERT, e.getMessage());
		}
		
		if(erreurs.isEmpty())
		{
			Float montant = Float.parseFloat(montantString);
			Float decouvert = Float.parseFloat(decouvertString);
			Compte compte = new Compte();
			compte.setId(Integer.parseInt(request.getParameter(ATT_COMPTE)));
			compte.setLibelle(libelle);
			compte.setMontant(montant);
			compte.setDecouvertMax(decouvert);
			compte.setProprietaire1(clientDao.getClientById(Integer.parseInt(request.getParameter(ATT_PROPRIETAIRE1))));
			compte.setProprietaire2(clientDao.getClientById(Integer.parseInt(request.getParameter(ATT_PROPRIETAIRE2))));
			boolean result = compteDao.updateCompte(compte);
			if(result)
			{
				resultat = "Compte modifié avec succès.";
			}
			else {
				resultat = "Echec de modification de compte. Une erreur s'est produite.";
			}
		}
		else {
			resultat = "Echec de modification de compte. Veuillez vérifier vos données.";
		}
	}
	
	private void validationLibelle( String libelle ) throws Exception{
		if(libelle == null)
		{
			throw new Exception("Merci de saisir un libelle");
		}
	}
	
	private void validationMontant( String montant ) throws Exception{
		int difference = 0;
		try {
			Float ceMontant = Float.parseFloat(montant);
			Float montantMinimal = Float.parseFloat("50");
			difference = Float.compare(ceMontant, montantMinimal);
		}catch(Exception e) {
			throw new Exception("Veuillez entrer un montant valide");
		}
		if(difference < 0)
		{
			throw new Exception("Merci de choisir un montant d'au moins 50 euros");
		}
	}
	
	private void validationDecouvert( String decouvert ) throws Exception{
		int difference = 0;
		try {
			Float ceDecouvert = Float.parseFloat(decouvert);
			Float decouvertMinimal = Float.parseFloat("0");
			difference = Float.compare(ceDecouvert, decouvertMinimal);
		}catch(Exception e) {
			throw new Exception("Veuillez entrer un decouvert maximum valide");
		}
		if(difference < 0)
		{
			throw new Exception("Merci de choisir un decouvert maximum positif");
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
