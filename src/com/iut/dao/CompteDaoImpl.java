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

public class CompteDaoImpl implements CompteDao{
	private static final String SQL_GET_COMPTES_CLIENT	= "SELECT * FROM compte WHERE titulaire_1 = ? OR titulaire_2 = ?";
	private static final String SQL_CREATE_COMPTE		= "INSERT INTO compte(libelle, montant, titulaire_1, titulaire_2, decouvert_max) VALUES(?, ?, ?, ?, ?)";
	private static final String SQL_GET_COMPTE_ID		= "SELECT * FROM compte WHERE id = ?";
	
	private DAOFactory daoFactory;

	public CompteDaoImpl(DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}
	
	public ArrayList<Compte> getComptesByClient(Client client)
	{
		ArrayList<Compte> comptes = new ArrayList<>();
		
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_GET_COMPTES_CLIENT, false, client.getId(), client.getId());
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				Compte compte = map(resultSet);
				comptes.add(compte);
			}
		}catch(SQLException e) {
			throw new DAOException(e.getMessage());
		}
		finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}
		
		return comptes;
	}
	
	public boolean createCompte(Compte compte, Client client, Client autreTitulaire)
	{
		boolean result = false;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Integer resultSet = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connection, SQL_CREATE_COMPTE, true, compte.getLibelle(), compte.getMontant(), client.getId(), (autreTitulaire == null ? null : autreTitulaire.getId()), compte.getDecouvertMax() );
			resultSet = preparedStatement.executeUpdate();
			if(resultSet > 0)
			{
				result = true;
			}
		}catch(SQLException e) {
			throw new DAOException(e.getMessage());
		}finally {
			fermeturesSilencieuses(preparedStatement, connection);
		}
		
		return result;
	}
	
	public Compte getCompteById(int id)
	{
		Compte compte = null;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connection, SQL_GET_COMPTE_ID, false, id);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next())
			{
				compte = map(resultSet);
			}
		}catch(SQLException e) {
			throw new DAOException(e.getMessage());
		}finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connection);
		}
		
		return compte;
	}
	
	private Compte map(ResultSet resultSet) throws SQLException{
		Compte compte = new Compte();
		compte.setId(resultSet.getInt("id"));
		compte.setLibelle(resultSet.getString("libelle"));
		compte.setMontant(resultSet.getFloat("montant"));
		compte.setDecouvertMax(resultSet.getFloat("decouvert_max"));
		return compte;
	}
}
