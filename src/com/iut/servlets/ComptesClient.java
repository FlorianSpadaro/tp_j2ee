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
 * Servlet implementation class ComptesClient
 */
@WebServlet("/ComptesClient")
public class ComptesClient extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final String CONF_DAO_FACTORY = "daofactory";
	public static final String ATT_CLIENT		= "client";
	public static final String ATT_COMPTES		= "comptes";
	public static final String ATT_TRANSACTIONS	= "transactions";
	public static final String VUE				= "/WEB-INF/mesComptes.jsp";
       
	private ClientDao clientDao;
	private CompteDao compteDao;
	
	public void init() throws ServletException {
        this.clientDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getClientDao();
        this.compteDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getCompteDao();
    }
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ComptesClient() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Client client = (Client) request.getSession().getAttribute(ATT_CLIENT);
		ArrayList<Compte> comptes = compteDao.getComptesByClient(client);
		for(Compte compte : comptes)
		{
			ArrayList<Client> proprietaires = clientDao.getClientsByCompte(compte);
			compte.setProprietaire1(proprietaires.get(0));
			compte.setProprietaire2(proprietaires.get(1));
		}
		
		ArrayList<Transaction> transactions = compteDao.getLastTransactionsByClient(client, 8);
		for(Transaction transaction : transactions )
		{
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
		
		request.setAttribute(ATT_COMPTES, comptes);
		request.setAttribute(ATT_TRANSACTIONS, transactions);
		
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
