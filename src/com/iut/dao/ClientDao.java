package com.iut.dao;

import java.util.ArrayList;

import com.iut.beans.Client;
import com.iut.beans.Compte;
import com.iut.beans.Conseiller;

public interface ClientDao {
	Client connexion(String login, String password);
	Client getClientById(int id);
	ArrayList<Client> getClientsByConseiller(Conseiller conseiller);
	ArrayList<Client> getListeClients();
	ArrayList<Client> getClientsByCompte(Compte compte);
}
