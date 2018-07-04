package com.iut.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iut.beans.Client;
import com.iut.beans.Conseiller;
import com.iut.dao.ClientDao;
import com.iut.dao.ConseillerDao;
import com.iut.dao.DAOFactory;
import com.iut.form.ConnexionForm;

/**
 * Servlet implementation class Connexion
 */
@WebServlet("/Connexion")
public class Connexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	public static final String CONF_DAO_FACTORY = "daofactory";
	public static final String ATT_CLIENT       = "client";
    public static final String ATT_FORM         = "form";
    public static final String ATT_TYPE 		= "type";
    public static final String ATT_CONSEILLER 	= "conseiller";
	public static final String VUE 				= "/index.jsp";
	
	private ClientDao clientDao;
	private ConseillerDao conseillerDao;
	
	public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.clientDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getClientDao();
        this.conseillerDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getConseillerDao();
    }
    
    public Connexion() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String type = (String)request.getParameter(ATT_TYPE);
		
		if(type.equals("client"))
		{
			ConnexionForm form = new ConnexionForm(clientDao);
			Client client = form.connecterClient(request);
			request.setAttribute(ATT_CLIENT, client);
			request.setAttribute(ATT_FORM, form);
			request.setAttribute(ATT_TYPE, "client");
		}
		else if(type.equals("conseiller"))
		{
			ConnexionForm form = new ConnexionForm(conseillerDao);
			Conseiller conseiller = form.connecterConseiller(request);
			request.setAttribute(ATT_CONSEILLER, conseiller);
			request.setAttribute(ATT_FORM, form);
			request.setAttribute(ATT_TYPE, "conseiller");
		}
		
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

}
