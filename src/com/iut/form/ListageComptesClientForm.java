package com.iut.form;

import java.util.ArrayList;
import java.util.HashMap;

import com.iut.beans.Client;
import com.iut.beans.Compte;
import com.iut.dao.CompteDao;

public class ListageComptesClientForm {
	private HashMap<String, String> erreurs;
	private String resultat;
	private CompteDao compteDao;
	
	public ListageComptesClientForm(CompteDao compteDao) {
		super();
		this.compteDao = compteDao;
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
	
	/**
	 * Fonction qui retourne la liste des Comptes d'un Client passé en paramètre
	 */
	public ArrayList<Compte> listerComptesClient(Client client)
	{
		ArrayList<Compte> comptes = compteDao.getComptesByClient(client);
		
		return comptes;
	}
}
