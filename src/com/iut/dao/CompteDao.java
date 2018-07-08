package com.iut.dao;

import java.util.ArrayList;

import com.iut.beans.Client;
import com.iut.beans.Compte;
import com.iut.beans.Transaction;

public interface CompteDao {
	ArrayList<Compte> getComptesByClient(Client client);
	boolean createCompte(Compte compte, Client client, Client autreTitulaire);
	Compte getCompteById(int id);
	ArrayList<Transaction> getDebitsByCompte(Compte compte);
	ArrayList<Transaction> getCreditsByCompte(Compte compte);
	boolean updateCompte(Compte compte);
	boolean disableCompte(int compteId);
}