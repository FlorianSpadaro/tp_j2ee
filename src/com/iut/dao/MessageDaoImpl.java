package com.iut.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static com.iut.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.iut.dao.DAOUtilitaire.initialisationRequetePreparee;

import com.iut.beans.Client;
import com.iut.beans.Conseiller;
import com.iut.beans.Message;
import com.iut.beans.ReponseMessage;
import com.iut.enums.DestinataireMessage;

public class MessageDaoImpl implements MessageDao {
	private static final String SQL_CREATE_MESSAGE					= "INSERT INTO message(client_id, conseiller_id, sujet, contenu, destinataire_client, destinataire_conseiller ) VALUES(?, ?, ?, ?, ?, ?)";
	private static final String SQL_GET_MESSAGES_NON_LUS_CONSEILLER	= "SELECT UNIQUE message.id, message.client_id, message.conseiller_id, message.sujet, dbms_lob.substr(message.contenu,32767) contenu, message.date_message, message.lu, message.destinataire_client, message.destinataire_conseiller, message.date_derniere_reponse FROM message LEFT JOIN reponse_message ON reponse_message.message_id = message.id WHERE ( (message.lu = 0 AND message.destinataire_conseiller = 1 ) OR (reponse_message.lu = 0 AND reponse_message.destinataire_conseiller = 1 )) AND message.conseiller_id = ? ORDER BY message.date_derniere_reponse";
	private static final String SQL_GET_MESSAGES_LUS_CONSEILLER		= "SELECT UNIQUE message.id, message.client_id, message.conseiller_id, message.sujet, dbms_lob.substr(message.contenu,32767) contenu, message.date_message, message.lu, message.destinataire_client, message.destinataire_conseiller, message.date_derniere_reponse FROM message WHERE message.conseiller_id = ? AND message.id NOT IN (SELECT message.id FROM message LEFT JOIN reponse_message ON reponse_message.message_id = message.id WHERE ( (message.lu = 0 AND message.destinataire_conseiller = 1 ) OR (reponse_message.lu = 0 AND reponse_message.destinataire_conseiller = 1 )) AND message.conseiller_id = ? ) ORDER BY message.date_derniere_reponse";
	private static final String SQL_GET_MESSAGES_NON_LUS_CLIENT		= "SELECT message.* FROM message LEFT JOIN reponse_message ON reponse_message.message_id = message.id WHERE ( (message.lu = 0 AND message.destinataire_client = 1 ) OR ( reponse_message.lu = 0 AND reponse_message.destinataire_client = 1 ) ) AND message.client_id = ? ORDER BY message.date_derniere_reponse";
	private static final String SQL_GET_MESSAGES_LUS_CLIENT			= "SELECT message.* FROM message WHERE message.client_id = ? AND message.id NOT IN (SELECT message.id FROM message LEFT JOIN reponse_message ON reponse_message.message_id = message.id WHERE ( (message.lu = 0 AND message.destinataire_client = 1 ) OR ( reponse_message.lu = 0 AND reponse_message.destinataire_client = 1 ) ) AND message.client_id = ?) ORDER BY message.date_derniere_reponse";
	private static final String SQL_GET_REPONSES_MESSAGE			= "SELECT reponse_message.* FROM reponse_message LEFT JOIN message ON message.id = reponse_message.message_id WHERE message.id = ?";
	private static final String SQL_CREATE_REPONSE_MESSAGE			= "INSERT INTO reponse_message(message_id, contenu, destinataire_client, destinataire_conseiller) VALUES(?, ?, ?, ?)";
	private static final String SQL_UPDATE_DATE_DERNIER_MESSAGE		= "UPDATE message SET date_derniere_reponse = CURRENT_DATE";
	private static final String SQL_GET_MESSAGE_ID					= "SELECT * FROM message WHERE id = ?";
	
	private DAOFactory daoFactory;

	public MessageDaoImpl(DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	public int createMessage(Message message)
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int messageId;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connection, SQL_CREATE_MESSAGE, true, message.getClient().getId(), message.getConseiller().getId(), message.getSujet(), message.getContenu(), (message.getDestinataire().equals(DestinataireMessage.client) ? 1 : 0), (message.getDestinataire().equals(DestinataireMessage.conseiller) ? 1 : 0) );
			messageId = preparedStatement.executeUpdate();
		}catch(SQLException e) {
			throw new DAOException(e.getMessage());
		}finally {
			fermeturesSilencieuses(preparedStatement, connection);
		}
		
		return messageId;
	}
	
	public ArrayList<Message> getMessagesNonLusByConseiller(Conseiller conseiller)
	{
		ArrayList<Message> messagesNonLus = new ArrayList<>();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connection, SQL_GET_MESSAGES_NON_LUS_CONSEILLER, false, conseiller.getId());
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				Message message = map(resultSet);
				messagesNonLus.add(message);
			}
		}catch(SQLException e) {
			throw new DAOException(e.getMessage());
		}finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connection);
		}
		
		return messagesNonLus;
	}
	
	public ArrayList<Message> getMessagesLusByConseiller(Conseiller conseiller)
	{
		ArrayList<Message> messagesLus = new ArrayList<>();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connection, SQL_GET_MESSAGES_LUS_CONSEILLER, false, conseiller.getId(), conseiller.getId());
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				Message message = map(resultSet);
				messagesLus.add(message);
			}
		}catch(SQLException e) {
			throw new DAOException(e.getMessage());
		}finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connection);
		}
		
		return messagesLus;
	}
	
	public ArrayList<Message> getMessagesNonLusByClient(Client client)
	{
		ArrayList<Message> messagesNonLus = new ArrayList<>();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connection, SQL_GET_MESSAGES_NON_LUS_CLIENT, false, client.getId());
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				Message message = map(resultSet);
				messagesNonLus.add(message);
			}
		}catch(SQLException e) {
			throw new DAOException(e.getMessage());
		}finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connection);
		}
		
		return messagesNonLus;
	}
	
	public ArrayList<Message> getMessagesLusByClient(Client client)
	{
		ArrayList<Message> messagesLus = new ArrayList<>();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connection, SQL_GET_MESSAGES_LUS_CLIENT, false, client.getId(), client.getId());
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				Message message = map(resultSet);
				messagesLus.add(message);
			}
		}catch(SQLException e) {
			throw new DAOException(e.getMessage());
		}finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connection);
		}
		
		return messagesLus;
	}
	
	public ArrayList<ReponseMessage> getReponsesByMessage(Message message)
	{
		ArrayList<ReponseMessage> reponses = new ArrayList<>();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connection, SQL_GET_REPONSES_MESSAGE, false, message.getId());
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				ReponseMessage reponse = mapReponse(resultSet);
				reponses.add(reponse);
			}
		}catch(SQLException e) {
			throw new DAOException(e.getMessage());
		}finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connection);
		}
		
		return reponses;
	}
	
	public int createReponseMessage(ReponseMessage reponse)
	{
		int reponseId = 0;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connection, SQL_CREATE_REPONSE_MESSAGE, false, reponse.getMessageLie().getId(), reponse.getContenu(), (reponse.getDestinataire().equals(DestinataireMessage.client) ? 1 : 0), (reponse.getDestinataire().equals(DestinataireMessage.conseiller) ? 1 : 0));
			reponseId = preparedStatement.executeUpdate();
			if(reponseId > 0)
			{
				preparedStatement = initialisationRequetePreparee(connection, SQL_UPDATE_DATE_DERNIER_MESSAGE, false);
				preparedStatement.executeUpdate();
			}
		}catch(SQLException e) {
			throw new DAOException(e.getMessage());
		}finally {
			fermeturesSilencieuses(preparedStatement, connection);
		}
		return reponseId;
	}
	
	public Message getMessageById(int id)
	{
		Message message = null;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connection, SQL_GET_MESSAGE_ID, false, id);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				message = map(resultSet);
			}
		}catch(SQLException e) {
			throw new DAOException(e.getMessage());
		}finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connection);
		}
		
		return message;
	}
	
	private static Message map(ResultSet resultSet) throws SQLException{
		Message message = new Message();
		message.setId(resultSet.getInt("id"));
		message.setSujet(resultSet.getString("sujet"));
		message.setContenu(resultSet.getString("contenu"));
		message.setLu(resultSet.getBoolean("lu"));
		
		String dateStr = resultSet.getString("date_message").substring(0, 19);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime dateTime = LocalDateTime.parse(dateStr, formatter);
		message.setDate(dateTime);
		
		dateStr = resultSet.getString("date_derniere_reponse").substring(0, 19);
		dateTime = LocalDateTime.parse(dateStr, formatter);
		message.setDateDerniereReponse(dateTime);
		
		if(resultSet.getBoolean("destinataire_client"))
		{
			message.setDestinataire(DestinataireMessage.client);
		}
		else {
			message.setDestinataire(DestinataireMessage.conseiller);
		}
		
		return message;
	}
	
	private static ReponseMessage mapReponse(ResultSet resultSet) throws SQLException{
		ReponseMessage reponse = new ReponseMessage();
		reponse.setId(resultSet.getInt("id"));
		reponse.setContenu(resultSet.getString("contenu"));
		reponse.setLu(resultSet.getBoolean("lu"));
		
		String dateStr = resultSet.getString("date_reponse").substring(0, 19);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime dateTime = LocalDateTime.parse(dateStr, formatter);
		reponse.setDate(dateTime);
		
		if(resultSet.getBoolean("destinataire_client"))
		{
			reponse.setDestinataire(DestinataireMessage.client);
		}
		else {
			reponse.setDestinataire(DestinataireMessage.conseiller);
		}
		
		return reponse;
	}
	
}
