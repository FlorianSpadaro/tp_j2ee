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
import com.iut.form.VirementForm;

/**
 * Servlet implementation class Virement
 */
@WebServlet("/Virement")
public class Virement extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final String CONF_DAO_FACTORY 	= "daofactory";
	public static final String ATT_FORM				= "form";
	public static final String ATT_COMPTE			= "compte";
	public static final String ATT_LISTE_CLIENTS	= "listeClients";
	public static final String ATT_TYPE				= "type";
	public static final String VUE					= "/WEB-INF/nouvelleTransaction.jsp";
    
	CompteDao compteDao;
	ClientDao clientDao;
	
	public void init() throws ServletException {
        this.compteDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getCompteDao();
        this.clientDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getClientDao();
    }
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Virement() {
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
		String compte = request.getParameter(ATT_COMPTE);
		String type = "virement";
		
		ArrayList<Client> listeClients = clientDao.getListeClients();
		for(Client client : listeClients)
		{
			client.setComptes(compteDao.getComptesByClient(client));
		}
		
		VirementForm form = new VirementForm(compteDao, clientDao);
		form.virement(request);
		
		request.setAttribute(ATT_FORM, form);
		request.setAttribute(ATT_COMPTE, compte);
		request.setAttribute(ATT_LISTE_CLIENTS, listeClients);
		request.setAttribute(ATT_TYPE, type);
		
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

}
