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
import com.iut.form.ListageClientsForm;
import com.iut.form.ListageComptesClientForm;

/**
 * Servlet implementation class ListerClients
 */
@WebServlet("/ListerClients")
public class ListerClients extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final String ATT_CLIENTS		= "clients";
	public static final String ATT_FORM			= "form";
	public static final String CONF_DAO_FACTORY = "daofactory";
	public static final String VUE				= "/WEB-INF/mesClients.jsp";
	
	private ClientDao clientDao;
	private CompteDao compteDao;
	
	public void init() throws ServletException {
        this.clientDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getClientDao();
        this.compteDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getCompteDao();
    }
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListerClients() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ListageClientsForm form = new ListageClientsForm(clientDao);
		ListageComptesClientForm compteForm = new ListageComptesClientForm(compteDao);
		
		//On récupère la liste des Clients du Conseiller
		ArrayList<Client> clients = form.listerClient(request);
		for(Client client : clients)
		{
			//Pour chaque Client on récupère la liste de ses Comptes
			client.setComptes(compteForm.listerComptesClient(client));
		}
		
		request.setAttribute(ATT_FORM, form);
		request.setAttribute(ATT_CLIENTS, clients);
		
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
