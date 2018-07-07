package com.iut.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.iut.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.iut.dao.DAOUtilitaire.initialisationRequetePreparee;

import com.iut.beans.Conseiller;

public class ConseillerDaoImpl implements ConseillerDao {
	private static final String SQL_CONNEXION 		= "SELECT * FROM conseiller WHERE login = ? AND password = ?";
	
	private DAOFactory daoFactory;

	public ConseillerDaoImpl(DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}
	
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
			throw new DAOException(e);
		}finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}
		
		return conseiller;
	}
	
	private static Conseiller map(ResultSet resultSet) throws SQLException{
		Conseiller conseiller = new Conseiller();
		conseiller.setId(resultSet.getInt("id"));
		conseiller.setNom(resultSet.getString("nom"));
		conseiller.setPrenom(resultSet.getString("prenom"));
		return conseiller;
	}
}
