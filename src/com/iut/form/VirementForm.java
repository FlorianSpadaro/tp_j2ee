package com.iut.form;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.iut.beans.Client;
import com.iut.beans.Compte;
import com.iut.beans.Transaction;
import com.iut.dao.ClientDao;
import com.iut.dao.CompteDao;

public class VirementForm {
	private static final String ATT_MONTANT			= "montantVirement";
	private static final String ATT_COMPTE			= "compte";
	private static final String ATT_DESTINATAIRE	= "destinataire";
	
	private HashMap<String, String> erreurs;
	private String resultat;
	private CompteDao compteDao;
	private ClientDao clientDao;
	
	public VirementForm(CompteDao compteDao, ClientDao clientDao) {
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
	
	public void virement(HttpServletRequest request) {
		String montantString = getValeurChamp(request, ATT_MONTANT);
    	
    	try {
			validationMontant(montantString);
		}catch(Exception e) {
			setErreur(ATT_MONTANT, e.getMessage());
		}
    	
    	if(erreurs.isEmpty())
    	{
    		Float montant = Float.parseFloat(montantString);
    		
    		Compte compteDebiteur = compteDao.getCompteById(Integer.parseInt(getValeurChamp(request, ATT_COMPTE)));
    		ArrayList<Client> proprietairesCompteDebiteur = clientDao.getClientsByCompte(compteDebiteur);
    		compteDebiteur.setProprietaire1(proprietairesCompteDebiteur.get(0));
    		compteDebiteur.setProprietaire2(proprietairesCompteDebiteur.get(1));
    		
    		try {
    			validationCompteDebiteur(compteDebiteur, montant);
    		}catch(Exception e) {
    			setErreur(ATT_COMPTE, e.getMessage());
    		}
    		
    		if(erreurs.isEmpty())
    		{
    			Compte compteCrediteur = compteDao.getCompteById(Integer.parseInt(getValeurChamp(request, ATT_DESTINATAIRE)));
        		ArrayList<Client> proprietairesCompteCrediteur = clientDao.getClientsByCompte(compteCrediteur);
        		compteCrediteur.setProprietaire1(proprietairesCompteCrediteur.get(0));
        		compteCrediteur.setProprietaire2(proprietairesCompteCrediteur.get(1));
        		
        		Transaction transaction = new Transaction();
        		transaction.setMontant(montant);
        		
        		boolean resultatTransaction = compteDao.createTransaction(transaction, compteDebiteur, compteCrediteur);
        		if(resultatTransaction)
        		{
        			Float nouveauMontantDebiteur = compteDebiteur.getMontant() - montant;
        			compteDebiteur.setMontant(nouveauMontantDebiteur);
        			
        			Float nouveauMontantCrediteur = compteCrediteur.getMontant() + montant;
        			compteCrediteur.setMontant(nouveauMontantCrediteur);
        			
        			boolean resultatCredit = compteDao.updateCompte(compteCrediteur);
        			if(resultatCredit)
        			{
        				boolean resultatDebit = compteDao.updateCompte(compteDebiteur);
        				if(resultatDebit)
        				{
        					resultat = "Virement effectué avec succès";
        				}
        				else {
        					nouveauMontantCrediteur = compteCrediteur.getMontant() - montant;
        					compteCrediteur.setMontant(nouveauMontantCrediteur);
        					compteDao.updateCompte(compteCrediteur);
        				}
        			}
        			else {
        				resultat = "Virement échoué";
        			}
        		}
    		}
    	}
    	else {
    		resultat = "Virement échoué. Veuillez vérifier vos données";
    	}
	}
	
	private void validationCompteDebiteur(Compte compte, Float montant) throws Exception{
		Float nouveauMontant = compte.getMontant() - montant;
		if(nouveauMontant < -(compte.getDecouvertMax()))
		{
			throw new Exception("Solde insuffisant du compte débiteur");
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
