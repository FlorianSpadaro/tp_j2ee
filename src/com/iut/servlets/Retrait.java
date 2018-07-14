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
import com.iut.form.RetraitForm;

/**
 * Servlet implementation class Retrait
 */
@WebServlet("/Retrait")
public class Retrait extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final String CONF_DAO_FACTORY 	= "daofactory";
	public static final String ATT_COMPTE			= "compte";
	public static final String ATT_FORM				= "form";
	public static final String VUE					= "/WEB-INF/affichageCompteClient.jsp";
	
	private CompteDao compteDao;
	private ClientDao clientDao;
	
	public void init() throws ServletException {
        this.compteDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getCompteDao();
        this.clientDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getClientDao();
    }
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Retrait() {
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
		RetraitForm form = new RetraitForm(compteDao, clientDao);
		
		//On effectue le retrait
		form.retrait(request);
		
		//On récupère le Compte correspondant à l'ID passé en paramètre
		Compte compte = compteDao.getCompteById(Integer.parseInt(request.getParameter(ATT_COMPTE)));
		
		//On récupère la liste des propriétaires de ce Compte
		ArrayList<Client> proprietairesCompte = clientDao.getClientsByCompte(compte);
		compte.setProprietaire1(proprietairesCompte.get(0));
		compte.setProprietaire2(proprietairesCompte.get(1));
		
		//On récupère la liste des Transactions créditées de ce Compte
		compte.setCredits(compteDao.getCreditsByCompte(compte));
		for(Transaction transaction : compte.getCredits())
		{
			//Pour chaque Transaction, on récupère les propriétaires du Compte débiteur lié à cette Transaction
			if(transaction.getCompteDebiteur() != null)
			{
				ArrayList<Client> proprietaires = clientDao.getClientsByCompte(transaction.getCompteDebiteur());
				transaction.getCompteDebiteur().setProprietaire1(proprietaires.get(0));
				transaction.getCompteDebiteur().setProprietaire2(proprietaires.get(1));
			}
			
			//Pour améliorer l'affichage de la date de la Transaction
			transaction.setDateAffiche(transaction.afficherDate());
		}
		
		//On récupère la liste des Transactions débitées de ce Compte
		compte.setDebits(compteDao.getDebitsByCompte(compte));
		for(Transaction transaction : compte.getDebits())
		{
			//Pour chaque Transaction, on récupère les propriétaires du Compte créditeur lié à cette Transaction
			if(transaction.getCompteCrediteur() != null)
			{
				ArrayList<Client> proprietaires = clientDao.getClientsByCompte(transaction.getCompteCrediteur());
				transaction.getCompteCrediteur().setProprietaire1(proprietaires.get(0));
				transaction.getCompteCrediteur().setProprietaire2(proprietaires.get(1));
			}
			
			//Pour améliorer l'affichage de la date de la Transaction
			transaction.setDateAffiche(transaction.afficherDate());
		}
		
		request.setAttribute(ATT_FORM, form);
		request.setAttribute(ATT_COMPTE, compte);
		
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

}
