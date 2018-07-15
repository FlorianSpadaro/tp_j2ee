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
import com.iut.form.VirementForm;

/**
 * Servlet implementation class VirementClient
 */
@WebServlet("/VirementClient")
public class VirementClient extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	public static final String CONF_DAO_FACTORY 	= "daofactory";
	public static final String ATT_FORM				= "form";
	public static final String ATT_LISTE_CLIENTS	= "listeClients";
	public static final String ATT_COMPTE			= "compte";
	public static final String VUE 					= "/WEB-INF/affichageCompteClient.jsp";
	
	private CompteDao compteDao;
	private ClientDao clientDao;
	
	public void init() throws ServletException {
        this.compteDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getCompteDao();
        this.clientDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getClientDao();
	}
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VirementClient() {
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
		VirementForm form = new VirementForm(compteDao, clientDao);
		
		//On effectue le virement
		form.virement(request);
		
		//On récupère le Compte dont l'ID a été passé en paramètre
		Compte compte = compteDao.getCompteById(Integer.parseInt(request.getParameter(ATT_COMPTE)));
		
		//On récupère la liste des propriétaires de ce Compte
		ArrayList<Client> proprietairesDuCompte = clientDao.getClientsByCompte(compte);
		compte.setProprietaire1(proprietairesDuCompte.get(0));
		compte.setProprietaire2(proprietairesDuCompte.get(1));
		
		//On récupère la liste des Transactions débitées de ce Compte
				compte.setCredits(compteDao.getCreditsByCompte(compte));
				for(Transaction transaction : compte.getCredits())
				{
					if(transaction.getCompteDebiteur() != null)
					{
						//On récupère la liste des proprietaires du Compte débiteur de la Transaction
						ArrayList<Client> proprietaires = clientDao.getClientsByCompte(transaction.getCompteDebiteur());
						transaction.getCompteDebiteur().setProprietaire1(proprietaires.get(0));
						transaction.getCompteDebiteur().setProprietaire2(proprietaires.get(1));
					}
					
					//Pour améliorer l'affichage de la date
					transaction.setDateAffiche(transaction.afficherDate());
				}
				
				//On récupère la liste des Transactions créditées de ce Compte
				compte.setDebits(compteDao.getDebitsByCompte(compte));
				for(Transaction transaction : compte.getDebits())
				{
					if(transaction.getCompteCrediteur() != null)
					{
						//On récupère la liste des proprietaires du Compte créditeur de la Transaction
						ArrayList<Client> proprietaires = clientDao.getClientsByCompte(transaction.getCompteCrediteur());
						transaction.getCompteCrediteur().setProprietaire1(proprietaires.get(0));
						transaction.getCompteCrediteur().setProprietaire2(proprietaires.get(1));
					}
					//Pour améliorer l'affichage de la date
					transaction.setDateAffiche(transaction.afficherDate());
				}
				
				//On récupère la liste des Clients, ainsi que leurs Comptes (pour le virement)
				ArrayList<Client> listeClients = clientDao.getListeClients();
				for(Client ceClient : listeClients )
				{
					ceClient.setComptes(compteDao.getComptesByClient(ceClient));
				}
				
				
				request.setAttribute(ATT_FORM, form);
				request.setAttribute(ATT_COMPTE, compte);
				request.setAttribute(ATT_LISTE_CLIENTS, listeClients);
				
				this.getServletContext().getRequestDispatcher( VUE ).forward(request, response);
				
	}

}
