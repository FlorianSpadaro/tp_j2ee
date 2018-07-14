package com.iut.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iut.beans.Client;
import com.iut.beans.Compte;
import com.iut.beans.Transaction;
import com.iut.dao.ClientDao;
import com.iut.dao.CompteDao;
import com.iut.dao.DAOFactory;

/**
 * Servlet implementation class AfficherInfosClient
 */
@WebServlet("/AfficherInfosClient")
public class AfficherInfosClient extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final String CONF_DAO_FACTORY = "daofactory";
	public static final String ATT_CLIENT		= "client";
	public static final String ATT_TRANSACTIONS	= "transactions";
	public static final String VUE				= "/WEB-INF/affichageInfosClient.jsp";
	
	private ClientDao clientDao;
	private CompteDao compteDao;
	
	public void init() throws ServletException {
        this.clientDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getClientDao();
        this.compteDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getCompteDao();
    }
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AfficherInfosClient() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//On récupère le Client correspondant à l'ID passé en paramètre
		Client client = clientDao.getClientById(Integer.parseInt(request.getParameter(ATT_CLIENT)));
		
		//On récupère les Comptes de ce Client
		client.setComptes(compteDao.getComptesByClient(client));
		for(Compte compte : client.getComptes())
		{
			//On récupère les propriétaires de chacun de ces Comptes
			ArrayList<Client> proprietaires = clientDao.getClientsByCompte(compte);
			compte.setProprietaire1(proprietaires.get(0));
			compte.setProprietaire2(proprietaires.get(1));
		}
		
		//On récupère les 8 dernières Transactions du Client
		ArrayList<Transaction> transactions = compteDao.getLastTransactionsByClient(client, 8);
		for(Transaction transaction : transactions)
		{
			//Pour chaque Transaction, on récupère les propriétaires du Compte débiteur et ceux du Compte créditeur liés à cette Transaction
			
			if(transaction.getCompteDebiteur() != null)
			{
				ArrayList<Client> proprietairesDebiteur = clientDao.getClientsByCompte(transaction.getCompteDebiteur());
				transaction.getCompteDebiteur().setProprietaire1(proprietairesDebiteur.get(0));
				transaction.getCompteDebiteur().setProprietaire2(proprietairesDebiteur.get(1));
			}
			
			if(transaction.getCompteCrediteur() != null)
			{
				ArrayList<Client> proprietairesCrediteur = clientDao.getClientsByCompte(transaction.getCompteCrediteur());
				transaction.getCompteCrediteur().setProprietaire1(proprietairesCrediteur.get(0));
				transaction.getCompteCrediteur().setProprietaire2(proprietairesCrediteur.get(1));
			}
		}
		
		request.setAttribute(ATT_CLIENT, client);
		request.setAttribute(ATT_TRANSACTIONS, transactions);
		
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

}
