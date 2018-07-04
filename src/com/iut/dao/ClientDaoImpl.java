package com.iut.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.iut.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.iut.dao.DAOUtilitaire.initialisationRequetePreparee;

import com.iut.beans.Client;

public class ClientDaoImpl implements ClientDao {
	private static final String SQL_CONNEXION = "SELECT * FROM client WHERE login = ? AND password = ?";
	
	private DAOFactory daoFactory;
	
	public ClientDaoImpl(DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}


	/* Récupération du client avec son login et son mot de passe */
	@Override
	public Client connexion(String login, String password) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Client client = null;
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_CONNEXION, false, login, password);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next())
			{
				client = map(resultSet);
			}
		}catch(SQLException e) {
			throw new DAOException(e);
		}finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}
		
		return client;
	}
	
	private static Client map(ResultSet resultSet) throws SQLException{
		Client client = new Client();
		client.setId(resultSet.getInt("id"));
		client.setNom(resultSet.getString("nom"));
		client.setPrenom(resultSet.getString("prenom"));
		return client;
	}
	
}
