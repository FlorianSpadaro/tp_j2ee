package com.iut.dao;

import com.iut.beans.Client;
import com.iut.beans.Conseiller;
import com.iut.beans.Message;

public interface ConseillerDao {
	Conseiller connexion(String login, String password);
	Conseiller getConseillerByClient(Client client);
	Conseiller getConseillerById(int id);
	Conseiller getConseillerByMessage(Message message);
}
