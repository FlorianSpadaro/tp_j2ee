package com.iut.dao;

import static com.iut.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.iut.beans.Agence;
import com.iut.beans.Client;

public class AgenceDaoImpl implements AgenceDao {
	private static final String SQL_GET_AGENCE_ID		= "SELECT * FROM agence WHERE id = ?";
	private static final String SQL_GET_AGENCE_CLIENT	= "SELECT agence.* FROM agence JOIN conseiller ON conseiller.agence_id = agence.id JOIN client ON client.conseiller_id = conseiller.id WHERE client.id = ?";
	
	private DAOFactory daoFactory;

	public AgenceDaoImpl(DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}
	
	public Agence getAgenceById(int id)
	{
		Agence agence = null;
		
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_GET_AGENCE_ID, false, id);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next())
			{
				agence = map(resultSet);
			}
		}catch(SQLException e) {
			throw new DAOException(e.getMessage());
		}
		
		return agence;
	}
	
	public Agence getAgenceByClient(Client client)
	{
		Agence agence = null;
		
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_GET_AGENCE_CLIENT, false, client.getId());
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next())
			{
				agence = map(resultSet);
			}
		}catch(SQLException e) {
			throw new DAOException(e.getMessage());
		}
		
		return agence;
	}
	
	private static Agence map(ResultSet resultSet) throws SQLException{
		Agence agence = new Agence();
		agence.setId(resultSet.getInt("id"));
		agence.setLibelle(resultSet.getString("libelle"));
		agence.setAdresse(resultSet.getString("adresse"));
		agence.setCodePostal(resultSet.getString("code_postal"));
		agence.setVille(resultSet.getString("ville"));
		agence.setLundiOuverture(resultSet.getString("lundi_ouverture"));
		agence.setLundiFermeture(resultSet.getString("lundi_fermeture"));
		agence.setMardiOuverture(resultSet.getString("mardi_ouverture"));
		agence.setMardiFermeture(resultSet.getString("mardi_fermeture"));
		agence.setMercrediOuverture(resultSet.getString("mercredi_ouverture"));
		agence.setMercrediFermeture(resultSet.getString("mercredi_fermeture"));
		agence.setJeudiOuverture(resultSet.getString("jeudi_ouverture"));
		agence.setJeudiFermeture(resultSet.getString("jeudi_fermeture"));
		agence.setVendrediOuverture(resultSet.getString("vendredi_ouverture"));
		agence.setVendrediFermeture(resultSet.getString("vendredi_fermeture"));
		agence.setSamediOuverture(resultSet.getString("samedi_ouverture"));
		agence.setSamediFermeture(resultSet.getString("samedi_fermeture"));
		agence.setDimancheOuverture(resultSet.getString("dimanche_ouverture"));
		agence.setDimancheFermeture(resultSet.getString("dimanche_fermeture"));
		return agence;
	}
}
