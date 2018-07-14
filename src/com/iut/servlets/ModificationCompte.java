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
import com.iut.dao.ClientDao;
import com.iut.dao.CompteDao;
import com.iut.dao.DAOFactory;
import com.iut.form.ModificationCompteForm;

/**
 * Servlet implementation class ModificationCompte
 */
@WebServlet("/ModificationCompte")
public class ModificationCompte extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final String CONF_DAO_FACTORY = "daofactory";
	public static final String VUE 				= "/WEB-INF/modificationCompte.jsp";
	public static final String ATT_COMPTE		= "compte";
	public static final String ATT_CLIENT		= "listeClients";
	public static final String ATT_FORM			= "form";
	
	private CompteDao compteDao;
	private ClientDao clientDao;
	
	public void init() throws ServletException {
        this.compteDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getCompteDao();
        this.clientDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getClientDao();
    }
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModificationCompte() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//On récupère le Compte correspondant à l'ID passé en paramètre
		Compte compte = compteDao.getCompteById(Integer.parseInt(request.getParameter(ATT_COMPTE)));
		
		//On récupère la liste des propriétaires de ce Compte
		ArrayList<Client> proprietaires = clientDao.getClientsByCompte(compte);
		compte.setProprietaire1(proprietaires.get(0));
		compte.setProprietaire2(proprietaires.get(1));
		
		//On récupère la liste de tous les Clients
		ArrayList<Client> listeClients = clientDao.getListeClients();
		
		request.setAttribute(ATT_COMPTE, compte);
		request.setAttribute(ATT_CLIENT, listeClients);
		
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ModificationCompteForm form = new ModificationCompteForm(compteDao, clientDao);
		//On met à jour le Compte
		form.modifierCompte(request);
		
		//On récupère le Compte correspondant à l'ID passé en paramètre
		Compte compte = compteDao.getCompteById(Integer.parseInt(request.getParameter(ATT_COMPTE)));
		
		//On récupère la liste des propriétaires de ce Compte
		ArrayList<Client> proprietaires = clientDao.getClientsByCompte(compte);
		compte.setProprietaire1(proprietaires.get(0));
		compte.setProprietaire2(proprietaires.get(1));
		
		//On récupère la liste de tous les Clients
		ArrayList<Client> listeClients = clientDao.getListeClients();
		
		request.setAttribute(ATT_COMPTE, compte);
		request.setAttribute(ATT_CLIENT, listeClients);
		request.setAttribute(ATT_FORM, form);
		
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

}
