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
	
	/**
	 * Fonction qui réalise un versement d'une somme d'argent d'un Compte vers un autre
	 */
	public void virement(HttpServletRequest request) {
		String montantString = getValeurChamp(request, ATT_MONTANT);
    	
    	try {
			validationMontant(montantString);
		}catch(Exception e) {
			setErreur(ATT_MONTANT, e.getMessage());
		}
    	
    	//Si le montant saisi est valide, alors continue le traitement
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
    		
    		//Si le solde du Compte débiteur est suffisant, alors on continue le traitement
    		if(erreurs.isEmpty())
    		{
    			Compte compteCrediteur = compteDao.getCompteById(Integer.parseInt(getValeurChamp(request, ATT_DESTINATAIRE)));
        		ArrayList<Client> proprietairesCompteCrediteur = clientDao.getClientsByCompte(compteCrediteur);
        		compteCrediteur.setProprietaire1(proprietairesCompteCrediteur.get(0));
        		compteCrediteur.setProprietaire2(proprietairesCompteCrediteur.get(1));
        		
        		//On crée la Transaction de ce virement
        		Transaction transaction = new Transaction();
        		transaction.setMontant(montant);
        		
        		int transactionId = compteDao.createTransaction(transaction, compteDebiteur, compteCrediteur);
        		
        		//Si la transaction s'est correctement créée, alors on met à jour le montant du compte créditeur en premier, puis celui du compte débiteur
        		if(transactionId > 0)
        		{
        			Float nouveauMontantDebiteur = compteDebiteur.getMontant() - montant;
        			compteDebiteur.setMontant(nouveauMontantDebiteur);
        			
        			Float nouveauMontantCrediteur = compteCrediteur.getMontant() + montant;
        			compteCrediteur.setMontant(nouveauMontantCrediteur);
        			
        			boolean resultatCredit = compteDao.updateCompte(compteCrediteur);
        			
        			//Si le solde du Compte créditeur s'est correctement mis à jour, alors on met à jour le solde du Compte débiteur
        			if(resultatCredit)
        			{
        				boolean resultatDebit = compteDao.updateCompte(compteDebiteur);
        				if(resultatDebit)
        				{
        					resultat = "Virement effectué avec succès";
        				}
        				else {
        					//Si un problème a eut lieu lors de la mise à jour du solde du Compte débiteur, alors on retire la somme versée sur le Compte créditeur, puis on supprime la Transaction qui vient d'être créée
        					nouveauMontantCrediteur = compteCrediteur.getMontant() - montant;
        					compteCrediteur.setMontant(nouveauMontantCrediteur);
        					compteDao.updateCompte(compteCrediteur);
        					
        					compteDao.removeTransaction(transactionId);
        					
        					resultat = "Virement échoué";
        				}
        			}
        			else {
        				//Si un problème a eut lieu lors de la mise à jour du solde du Compte créditeur, alors on supprime la Transaction que l'on vient de créer
        				compteDao.removeTransaction(transactionId);
        				resultat = "Virement échoué";
        			}
        		}
    		}
    	}
    	else {
    		resultat = "Virement échoué. Veuillez vérifier vos données";
    	}
	}
	
	/**
	 * Fonction qui vérifie si le Compte débiteur a un solde suffisant pour effectuer ce versement
	 */
	private void validationCompteDebiteur(Compte compte, Float montant) throws Exception{
		Float nouveauMontant = compte.getMontant() - montant;
		if(nouveauMontant < -(compte.getDecouvertMax()))
		{
			throw new Exception("Solde insuffisant du compte débiteur");
		}
	}
	
	/**
	 * Fonction qui valide le montant saisi
	 */
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
