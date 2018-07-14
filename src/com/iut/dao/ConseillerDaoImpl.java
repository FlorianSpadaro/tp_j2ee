package com.iut.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.iut.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.iut.dao.DAOUtilitaire.initialisationRequetePreparee;

import com.iut.beans.Client;
import com.iut.beans.Conseiller;
import com.iut.beans.Message;

public class ConseillerDaoImpl implements ConseillerDao {
	private static final String SQL_CONNEXION 				= "SELECT * FROM conseiller WHERE login = ? AND password = ?";
	private static final String SQL_GET_CONSEILLER_CLIENT	= "SELECT conseiller.* FROM conseiller JOIN client ON client.conseiller_id = conseiller.id WHERE client.id = ?";
	private static final String SQL_GET_CONSEILLER_ID		= "SELECT * FROM conseiller WHERE id = ?";
	private static final String SQL_GET_CONSEILLER_MESSAGE	= "SELECT conseiller.* FROM conseiller JOIN message ON message.conseiller_id = conseiller.id WHERE message.id = ?";
	
	
	private DAOFactory daoFactory;

	public ConseillerDaoImpl(DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}
	
	/**
	 * Fonction qui retourne le Conseiller correspondant au login et au mot de passe passés en paramètres
	 */
	public Conseiller connexion(String login, String password)
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Conseiller conseiller = null;
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_CONNEXION, false, login, password);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next())
			{
				conseiller = map(resultSet);
			}
		}catch(SQLException e) {
			throw new DAOException(e.getMessage());
		}finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}
		
		return conseiller;
	}
	
	/**
	 * Fonction qui retourn le Conseiller d'un Client passé en paramètre
	 */
	public Conseiller getConseillerByClient(Client client)
	{
		Conseiller conseiller = null;
		
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_GET_CONSEILLER_CLIENT, false, client.getId());
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next())
			{
				conseiller = map(resultSet);
			}
		}catch(SQLException e) {
			throw new DAOException(e.getMessage());
		}finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}
		
		return conseiller;
	}
	
	/**
	 * Fonction qui retourne le bean Conseiller correspondant à l'ID passé en paramètre
	 */
	public Conseiller getConseillerById(int id)
	{
		Conseiller conseiller = null;
		
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_GET_CONSEILLER_ID, false, id);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next())
			{
				conseiller = map(resultSet);
			}
		}catch(SQLException e) {
			throw new DAOException(e.getMessage());
		}finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}
		
		return conseiller;
	}
	
	/**
	 * Fonction qui retourne le Conseiller lié au Message passé en paramètre
	 */
	public Conseiller getConseillerByMessage(Message message)
	{
		Conseiller conseiller = null;
		
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_GET_CONSEILLER_MESSAGE, false, message.getId());
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next())
			{
				conseiller = map(resultSet);
			}
		}catch(SQLException e) {
			throw new DAOException(e.getMessage());
		}finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}
		
		return conseiller;
	}
	
	/**
	 * Fonction qui retourne le bean Conseiller correspondant au ResultSet passé en paramètre
	 */
	private static Conseiller map(ResultSet resultSet) throws SQLException{
		Conseiller conseiller = new Conseiller();
		conseiller.setId(resultSet.getInt("id"));
		conseiller.setNom(resultSet.getString("nom"));
		conseiller.setPrenom(resultSet.getString("prenom"));
		return conseiller;
	}
}
