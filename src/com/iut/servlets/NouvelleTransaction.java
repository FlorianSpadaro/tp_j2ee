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

/**
 * Servlet implementation class NouvelleTransaction
 */
@WebServlet("/NouvelleTransaction")
public class NouvelleTransaction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final String CONF_DAO_FACTORY 	= "daofactory";
	public static final String ATT_LISTE_CLIENTS	= "listeClients";
	public static final String ATT_COMPTE			= "compte";
	public static final String VUE					= "/WEB-INF/nouvelleTransaction.jsp";
	
	private ClientDao clientDao;
	private CompteDao compteDao;
	
	public void init() throws ServletException {
        this.clientDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getClientDao();
        this.compteDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getCompteDao();
    }
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NouvelleTransaction() {
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
		//On récupère l'ID du Compte passé en paramètre
		String compte = request.getParameter(ATT_COMPTE);
		
		//On récupère la liste de tous les Clients
		ArrayList<Client> listeClients = clientDao.getListeClients();
		for(Client client : listeClients)
		{
			//Pour chaque Client, on récupère ses Comptes
			client.setComptes(compteDao.getComptesByClient(client));
		}
		
		request.setAttribute(ATT_COMPTE, compte);
		request.setAttribute(ATT_LISTE_CLIENTS, listeClients);
		
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
		
	}

}
