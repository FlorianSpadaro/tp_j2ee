package com.iut.dao;

import java.util.ArrayList;

import com.iut.beans.Client;
import com.iut.beans.Conseiller;
import com.iut.beans.Message;
import com.iut.beans.ReponseMessage;

public interface MessageDao {
	int createMessage(Message message);
	ArrayList<Message> getMessagesNonLusByConseiller(Conseiller conseiller);
	ArrayList<Message> getMessagesLusByConseiller(Conseiller conseiller);
	ArrayList<Message> getMessagesNonLusByClient(Client client);
	ArrayList<Message> getMessagesLusByClient(Client client);
	ArrayList<ReponseMessage> getReponsesByMessage(Message message);
	int createReponseMessage(ReponseMessage reponse);
	Message getMessageById(int id);
}
