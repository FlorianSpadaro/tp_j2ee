package com.iut.form;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.iut.beans.Client;
import com.iut.beans.Compte;
import com.iut.beans.Transaction;
import com.iut.dao.ClientDao;
import com.iut.dao.CompteDao;

public class VersementForm {
	private static final String ATT_MONTANT	= "montant";
	private static final String ATT_COMPTE	= "compte";
	
	private HashMap<String, String> erreurs;
	private String resultat;
	private CompteDao compteDao;
	private ClientDao clientDao;
	
	public VersementForm(CompteDao compteDao, ClientDao clientDao) {
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
    
    public void versement(HttpServletRequest request) {
    	String montantString = getValeurChamp(request, ATT_MONTANT);
    	
    	try {
			validationMontant(montantString);
		}catch(Exception e) {
			setErreur(ATT_MONTANT, e.getMessage());
		}
    	
    	if(erreurs.isEmpty())
    	{
    		Float montant = Float.parseFloat(montantString);
    		Compte compte = compteDao.getCompteById(Integer.parseInt(getValeurChamp(request, ATT_COMPTE)));
    		
    		ArrayList<Client> proprietaires = clientDao.getClientsByCompte(compte);
    		compte.setProprietaire1(proprietaires.get(0));
    		compte.setProprietaire2(proprietaires.get(1));
    		
    		Transaction transaction = new Transaction();
    		transaction.setMontant(montant);
    		
    		int transactionId = compteDao.createTransaction(transaction, null, compte);
    		if(transactionId > 0)
    		{
    			Float nouveauMontant = montant + compte.getMontant();
        		compte.setMontant(nouveauMontant);
        		boolean result = compteDao.updateCompte(compte);
        		if(result)
        		{
        			resultat = "Versement effectu� avec succ�s";
        		}
        		else {
        			compteDao.removeTransaction(transactionId);
        			resultat = "Versement �chou�";
        		}
    		}
    		else {
    			resultat = "Versement �chou�";
    		}
    		
    	}
    	else {
    		resultat = "Versement �chou�. Veuillez v�rifier vos donn�es";
    	}
    }
	
	private void validationMontant( String montant ) throws Exception{
		int difference = 0;
		try {
			Float ceMontant = Float.parseFloat(montant);
			Float montantMinimal = Float.parseFloat("0");
			difference = Float.compare(ceMontant, montantMinimal);
		}catch(Exception e) {
			throw new Exception("Veuillez entrer un montant valide");
		}
		if(difference < 0)
		{
			throw new Exception("Merci de choisir un montant positif");
		}
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
