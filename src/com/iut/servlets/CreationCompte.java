package com.iut.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iut.beans.Client;
import com.iut.dao.ClientDao;
import com.iut.dao.CompteDao;
import com.iut.dao.DAOFactory;
import com.iut.form.CreationCompteForm;

/**
 * Servlet implementation class CreationCompte
 */
@WebServlet("/CreationCompte")
public class CreationCompte extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final String CONF_DAO_FACTORY 	= "daofactory";
	public static final String ATT_FORM				= "form";
	public static final String ATT_CLIENT			= "client";
	public static final String ATT_LISTE_CLIENTS	= "listeClients";
	private static final String ATT_AUTRE_TITULAIRE = "autreTitulaire";
	public static final String VUE					= "/WEB-INF/creationCompte.jsp";
	
	private CompteDao compteDao;
	private ClientDao clientDao;
	private ArrayList<Client> clients;
	
	public void init() throws ServletException {
        this.compteDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getCompteDao();
        this.clientDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getClientDao();
        //On récupère la liste des Clients
        this.clients = clientDao.getListeClients();
    }
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreationCompte() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//On récupère le Client correspondant à l'ID passé en paramètre
    	Client client = clientDao.getClientById(Integer.parseInt(request.getParameter(ATT_CLIENT)));
    	
    	request.setAttribute(ATT_CLIENT, client);
    	request.setAttribute(ATT_LISTE_CLIENTS, clients);
    	
    	this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
    }
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//On récupère le Client correspondant à l'ID passé en paramètre
		Client client = clientDao.getClientById(Integer.parseInt(request.getParameter(ATT_CLIENT)));
		//On récupère le l'autre titulaire du Compte correspondant à l'ID passé en paramètre
		Client autreTitulaire = clientDao.getClientById(Integer.parseInt(request.getParameter(ATT_AUTRE_TITULAIRE)));
		
		request.setAttribute(ATT_LISTE_CLIENTS, clients);
		request.setAttribute(ATT_CLIENT, client);
		request.setAttribute(ATT_AUTRE_TITULAIRE, autreTitulaire);
		
		//On crée le Compte
		CreationCompteForm form = new CreationCompteForm(compteDao);
		form.creerCompte(request);
		
		request.setAttribute(ATT_FORM, form);
		
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

}
