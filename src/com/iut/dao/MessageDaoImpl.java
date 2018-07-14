package com.iut.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.iut.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.iut.dao.DAOUtilitaire.initialisationRequetePreparee;

import com.iut.beans.Message;
import com.iut.enums.DestinataireMessage;

public class MessageDaoImpl implements MessageDao {
	private static final String SQL_CREATE_MESSAGE	= "INSERT INTO message(client_id, conseiller_id, sujet, contenu, destinataire_client, destinataire_conseiller ) VALUES(?, ?, ?, ?, ?, ?)";
	
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
		
		if(resultSet.getBoolean("destinataire_client"))
		{
			message.setDestinataire(DestinataireMessage.client);
		}
		else {
			message.setDestinataire(DestinataireMessage.conseiller);
		}
		
		return message;
	}
	
}
