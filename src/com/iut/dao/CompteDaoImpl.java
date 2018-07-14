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
import com.iut.beans.Compte;
import com.iut.beans.Conseiller;
import com.iut.beans.Transaction;

public class CompteDaoImpl implements CompteDao{
	private static final String SQL_GET_COMPTES_CLIENT		= "SELECT * FROM compte WHERE (titulaire_1 = ? OR titulaire_2 = ?) AND actif = 1";
	private static final String SQL_CREATE_COMPTE			= "INSERT INTO compte(libelle, montant, titulaire_1, titulaire_2, decouvert_max) VALUES(?, ?, ?, ?, ?)";
	private static final String SQL_GET_COMPTE_ID			= "SELECT * FROM compte WHERE id = ?";
	private static final String SQL_GET_DEBITS_COMPTE		= "SELECT * FROM transaction WHERE compte_debiteur = ? ORDER BY date_transaction DESC";
	private static final String SQL_GET_CREDITS_COMPTE		= "SELECT * FROM transaction WHERE compte_crediteur = ? ORDER BY date_transaction DESC";
	private static final String SQL_UPDATE_COMPTE			= "UPDATE compte SET libelle = ?, montant = ?, decouvert_max = ?, titulaire_1 = ?, titulaire_2 = ? WHERE id = ?";
	private static final String SQL_DISABLE_COMPTE			= "UPDATE compte SET actif = 0 WHERE id = ?";
	private static final String SQL_CREATE_TRANSACTION		= "INSERT INTO transaction(date_transaction, montant, compte_debiteur, compte_crediteur) VALUES(CURRENT_DATE, ?, ?, ?)";
	private static final String SQL_GET_LAST_TRANSACTIONS	= "SELECT * FROM transaction JOIN compte ON compte.id = transaction.compte_debiteur OR compte.id = transaction.compte_crediteur JOIN client ON compte.titulaire_1 = client.id OR compte.titulaire_2 = client.id WHERE client.id = ? AND ROWNUM <= ? ORDER BY date_transaction";
	private static final String SQL_GET_COMPTES_DECOUVERTS	= "SELECT UNIQUE compte.ID, compte.LIBELLE, compte.MONTANT, compte.DECOUVERT_MAX, compte.TITULAIRE_1, compte.TITULAIRE_2, compte.ACTIF FROM compte JOIN client ON client.id = compte.titulaire_1 OR client.id = compte.titulaire_2 WHERE montant < 0 AND conseiller_id = ? ORDER BY montant";
	private static final String SQL_DELETE_TRANSACTION		= "DELETE FROM transaction WHERE id = ?";
	
	private DAOFactory daoFactory;

	public CompteDaoImpl(DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}
	
	/**
	 * Fonction qui retourne la liste de comptes d'un client
	 */
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
	
	/**
	 * Fonction de création d'un compte
	 */
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
	
	/**
	 * Fonction qui retourne le Compte en fonction de son ID passé en paramètre
	 */
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
	
	/**
	 * Fonction qui retourne la liste des transactions débitées d'un Compte passé en paramètre
	 */
	public ArrayList<Transaction> getDebitsByCompte(Compte compte)
	{
		ArrayList<Transaction> transactions = new ArrayList<>();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connection, SQL_GET_DEBITS_COMPTE, false, compte.getId());
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				Transaction transaction = mapTransaction(resultSet);
				
				transactions.add(transaction);
			}
		}catch(SQLException e) {
			throw new DAOException(e.getMessage());
		}finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connection);
		}
		
		return transactions;
	}
	
	/**
	 * Fonction qui retourne la liste des transactions créditées d'un Compte passé en paramètre
	 */
	public ArrayList<Transaction> getCreditsByCompte(Compte compte)
	{
		ArrayList<Transaction> transactions = new ArrayList<>();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connection, SQL_GET_CREDITS_COMPTE, false, compte.getId());
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				Transaction transaction = mapTransaction(resultSet);
				
				transactions.add(transaction);
			}
		}catch(SQLException e) {
			throw new DAOException(e.getMessage());
		}finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connection);
		}
		
		return transactions;
	}
	
	/**
	 * Fonction qui met à jour le compte passé en paramètre
	 */
	public boolean updateCompte(Compte compte)
	{
		boolean result = false;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int resultSet;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connection, SQL_UPDATE_COMPTE, false, compte.getLibelle(), compte.getMontant(), compte.getDecouvertMax(), compte.getProprietaire1().getId(), ( compte.getProprietaire2() == null ? null : compte.getProprietaire2().getId()), compte.getId());
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
	
	/**
	 * Fonction qui désactive le compte dont l'ID a été passé en paramètre
	 */
	public boolean disableCompte(int compteId)
	{
		boolean result = false;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int resultset;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connection, SQL_DISABLE_COMPTE, false, compteId);
			resultset = preparedStatement.executeUpdate();
			if(resultset > 0)
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
	
	/**
	 * Fonction qui crée une Transaction
	 */
	public int createTransaction(Transaction transaction, Compte compteDebiteur, Compte compteCrediteur)
	{
		int transactionId = 0;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connection, SQL_CREATE_TRANSACTION, true, transaction.getMontant(), (compteDebiteur == null ? null : compteDebiteur.getId() ), (compteCrediteur == null ? null : compteCrediteur.getId() ));
			transactionId = preparedStatement.executeUpdate();
			
		}catch(SQLException e) {
			throw new DAOException(e.getMessage());
		}finally {
			fermeturesSilencieuses(preparedStatement, connection);
		}
		
		return transactionId;
	}
	
	/**
	 * Fonction qui retourne la liste des dernières transactions d'un Client passé en paramètre. 
	 * La limite, également passée en paramètre, permet de limiter le nombre de transactions qui seront retournées
	 */
	public ArrayList<Transaction> getLastTransactionsByClient(Client client, int limite)
	{
		ArrayList<Transaction> transactions = new ArrayList<>();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connection, SQL_GET_LAST_TRANSACTIONS, false, client.getId(), limite);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				Transaction transaction = mapTransaction(resultSet);
				
				transactions.add(transaction);
			}
		}catch(SQLException e) {
			throw new DAOException(e.getMessage());
		}finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connection);
		}
		
		return transactions;
	}
	
	/**
	 * Fonction qui retourne la liste des comptes à découvert des clients d'un conseiller passé en paramètre
	 */
	public ArrayList<Compte> getComptesDecouverts(Conseiller conseiller)
	{
		ArrayList<Compte> comptes = new ArrayList<>();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connection, SQL_GET_COMPTES_DECOUVERTS, false, conseiller.getId());
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				Compte compte = map(resultSet);
				comptes.add(compte);
			}
		}catch(SQLException e) {
			throw new DAOException(e.getMessage());
		}finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connection);
		}
		
		return comptes;
	}
	
	/**
	 * Fonction qui supprime la transaction dont l'ID a été passé en paramètre
	 */
	public boolean removeTransaction(int transactionId)
	{
		boolean result = false;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int resultSet;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connection, SQL_DELETE_TRANSACTION, false, transactionId);
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
	
	/**
	 * Fonction qui crée le bean Compte qui correspond au ResultSet passé en paramètre
	 */
	private Compte map(ResultSet resultSet) throws SQLException{
		Compte compte = new Compte();
		compte.setId(resultSet.getInt("id"));
		compte.setLibelle(resultSet.getString("libelle"));
		compte.setMontant(resultSet.getFloat("montant"));
		compte.setDecouvertMax(resultSet.getFloat("decouvert_max"));
		return compte;
	}
	
	/**
	 * Fonction qui crée le bean Transaction qui correspond au ResultSet passé en paramètre
	 */
	private Transaction mapTransaction(ResultSet resultSet) throws SQLException{
		Transaction transaction = new Transaction();
		transaction.setId(resultSet.getInt("id"));
		transaction.setMontant(resultSet.getFloat("montant"));
		
		String dateStr = resultSet.getString("date_transaction").substring(0, 19);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime dateTime = LocalDateTime.parse(dateStr, formatter);
		transaction.setDate(dateTime);
		
		transaction.setDateAffiche(transaction.afficherDate());
		
		Compte compteDebiteur = getCompteById(resultSet.getInt("compte_debiteur"));
		transaction.setCompteDebiteur(compteDebiteur);
		
		Compte compteCrediteur = getCompteById(resultSet.getInt("compte_crediteur"));
		transaction.setCompteCrediteur(compteCrediteur);
		
		return transaction;
	}
}
