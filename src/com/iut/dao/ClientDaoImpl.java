package com.iut.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.iut.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.iut.dao.DAOUtilitaire.initialisationRequetePreparee;

import com.iut.beans.Client;
import com.iut.beans.Compte;
import com.iut.beans.Conseiller;
import com.iut.beans.Message;

public class ClientDaoImpl implements ClientDao {
	private static final String SQL_CONNEXION 				= "SELECT * FROM client WHERE login = ? AND password = ?";
	private static final String SQL_GET_CLIENT_ID 			= "SELECT * FROM client WHERE id = ?";
	private static final String SQL_GET_CLIENTS_CONSEILLER	= "SELECT * FROM client WHERE conseiller_id = ?";
	private static final String SQL_GET_CLIENTS				= "SELECT * FROM client ORDER BY nom, prenom";
	private static final String SQL_GET_CLIENTS_COMPTE		= "SELECT titulaire_1, titulaire_2 FROM compte WHERE id = ?";
	private static final String SQL_GET_CLIENTS_SEARCH		= "SELECT * FROM client WHERE UPPER(nom) LIKE ? OR UPPER(prenom) LIKE ?";
	private static final String SQL_GET_CLIENT_MESSAGE		= "SELECT client.* FROM client JOIN message ON message.client_id = client.id WHERE message.id = ?";
	
	private DAOFactory daoFactory;
	
	public ClientDaoImpl(DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}


	/**
	 * R�cup�ration du client avec son login et son mot de passe
	 */
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
	
	/**
	 * R�cup�ration du bean Client, gr�ce � son ID pass� en param�tre
	 */
	public Client getClientById(int id)
	{
		Client client = null;
		
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_GET_CLIENT_ID, false, id);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next())
			{
				client = map(resultSet);
			}
		}catch(SQLException e) {
			throw new DAOException(e.getMessage());
		}finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}
		
		return client;
	}
	
	/**
	 * R�cup�ration de la liste des clients d'un conseiller
	 */
	public ArrayList<Client> getClientsByConseiller(Conseiller conseiller)
	{
		ArrayList<Client> clients = new ArrayList<>();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connection, SQL_GET_CLIENTS_CONSEILLER, false, conseiller.getId());
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				Client client = map(resultSet);
				clients.add(client);
			}
		}catch(SQLException e) {
			throw new DAOException(e.getMessage());
		}finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connection);
		}
		
		return clients;
	}
	
	/**
	 * Retourne la liste de tous les clients
	 */
	public ArrayList<Client> getListeClients()
	{
		ArrayList<Client> clients = new ArrayList<>();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connection, SQL_GET_CLIENTS, false);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				Client client = map(resultSet);
				clients.add(client);
			}
		}catch(SQLException e) {
			throw new DAOException(e.getMessage());
		}finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connection);
		}
		
		return clients;
	}
	
	/**
	 * Retourne la liste des clients d'un compte pass� en param�tre
	 */
	public ArrayList<Client> getClientsByCompte(Compte compte)
	{
		ArrayList<Client> clients = new ArrayList<>();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connection, SQL_GET_CLIENTS_COMPTE, false, compte.getId());
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next())
			{
				Client titulaire1 = getClientById(resultSet.getInt("titulaire_1"));
				clients.add(titulaire1);
				
				Client titulaire2 = null;
				if(resultSet.getString("titulaire_2") != null && !resultSet.getString("titulaire_2").equals("null"))
				{
					titulaire2 = getClientById(resultSet.getInt("titulaire_2"));
				}
				clients.add(titulaire2);
			}
		}catch(SQLException e) {
			throw new DAOException(e.getMessage());
		}finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connection);
		}
		
		return clients;
	}
	
	/**
	 * Retourne la liste de clients dont le nom ou le pr�nom correspond � la recherche pass�e en param�tre
	 */
	public ArrayList<Client> getClientsBySearch(String recherche)
	{
		ArrayList<Client> clients = new ArrayList<>();
		
		recherche = "%" + recherche.toUpperCase() + "%";
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connection, SQL_GET_CLIENTS_SEARCH, false, recherche, recherche);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				Client client = map(resultSet);
				clients.add(client);
			}
		}catch(SQLException e) {
			throw new DAOException(e.getMessage());
		}finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connection);
		}
		
		return clients;
	}
	
	/**
	 * Retourne le Client li� au Message pass� en param�tre
	 */
	public Client getClientByMessage(Message message)
	{
		Client client = null;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connection, SQL_GET_CLIENT_MESSAGE, false, message.getId());
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next())
			{
				client = map(resultSet);
			}
		}catch(SQLException e) {
			throw new DAOException(e.getMessage());
		}finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connection);
		}
		
		return client;
	}
	
	/**
	 * Fonction qui retourne le bean Client correspondant au ResultSet pass� en param�tre
	 */
	private static Client map(ResultSet resultSet) throws SQLException{
		Client client = new Client();
		client.setId(resultSet.getInt("id"));
		client.setNom(resultSet.getString("nom"));
		client.setPrenom(resultSet.getString("prenom"));
		return client;
	}
	
}
