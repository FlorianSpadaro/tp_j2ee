package com.iut.dao;

import java.util.ArrayList;

import com.iut.beans.Client;
import com.iut.beans.Compte;

public interface CompteDao {
	ArrayList<Compte> getComptesByClient(Client client);
	boolean createCompte(Compte compte, Client client, Client autreTitulaire);
	Compte getCompteById(int id);
}
