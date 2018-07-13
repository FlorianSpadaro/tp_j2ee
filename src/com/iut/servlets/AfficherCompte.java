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
 * Servlet implementation class AfficherCompte
 */
@WebServlet("/AfficherCompte")
public class AfficherCompte extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final String CONF_DAO_FACTORY = "daofactory";
	public static final String ATT_CLIENT		= "client";
	public static final String ATT_COMPTE		= "compte";
	public static final String ATT_CONSEILLER	= "conseiller";
	public static final String VUE				= "/WEB-INF/affichageCompte.jsp";
	public static final String VUE_CLIENT		= "/WEB-INF/affichageCompteClient.jsp";
	
	private ClientDao clientDao;
	private CompteDao compteDao;
	
	public void init() throws ServletException {
        this.clientDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getClientDao();
        this.compteDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getCompteDao();
    }
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AfficherCompte() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//this.getServletContext().getRequestDispatcher( VUE ).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Client client = clientDao.getClientById(Integer.parseInt(request.getParameter(ATT_CLIENT)));
		
		Compte compte = compteDao.getCompteById(Integer.parseInt(request.getParameter(ATT_COMPTE)));
		
		ArrayList<Client> proprietairesDuCompte = clientDao.getClientsByCompte(compte);
		compte.setProprietaire1(proprietairesDuCompte.get(0));
		compte.setProprietaire2(proprietairesDuCompte.get(1));
		
		compte.setCredits(compteDao.getCreditsByCompte(compte));
		for(Transaction transaction : compte.getCredits())
		{
			if(transaction.getCompteDebiteur() != null)
			{
				ArrayList<Client> proprietaires = clientDao.getClientsByCompte(transaction.getCompteDebiteur());
				transaction.getCompteDebiteur().setProprietaire1(proprietaires.get(0));
				transaction.getCompteDebiteur().setProprietaire2(proprietaires.get(1));
			}
			transaction.setDateAffiche(transaction.afficherDate());
		}
		
		compte.setDebits(compteDao.getDebitsByCompte(compte));
		for(Transaction transaction : compte.getDebits())
		{
			if(transaction.getCompteCrediteur() != null)
			{
				ArrayList<Client> proprietaires = clientDao.getClientsByCompte(transaction.getCompteCrediteur());
				transaction.getCompteCrediteur().setProprietaire1(proprietaires.get(0));
				transaction.getCompteCrediteur().setProprietaire2(proprietaires.get(1));
			}
			transaction.setDateAffiche(transaction.afficherDate());
		}
		
		request.setAttribute(ATT_CLIENT, client);
		request.setAttribute(ATT_COMPTE, compte);
		
		if(request.getSession().getAttribute(ATT_CONSEILLER) != null)
		{
			this.getServletContext().getRequestDispatcher( VUE ).forward(request, response);
		}
		else {
			this.getServletContext().getRequestDispatcher( VUE_CLIENT ).forward(request, response);
		}
	}

}
