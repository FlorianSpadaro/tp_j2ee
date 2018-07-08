package com.iut.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iut.dao.CompteDao;
import com.iut.dao.DAOFactory;

/**
 * Servlet implementation class SuppressionCompte
 */
@WebServlet("/SuppressionCompte")
public class SuppressionCompte extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final String CONF_DAO_FACTORY 	= "daofactory";
	public static final String ATT_COMPTE			= "compte";
	public static final String ATT_CLIENT			= "client";
	public static final String ATT_RESULTAT			= "resultat";
	public static final String VUE_AFFICHER_COMPTE	= "/conseiller/client/compte";
	public static final String VUE_CLIENTS			= "/conseiller/client";
	
	private CompteDao compteDao;
	
	public void init() throws ServletException {
        this.compteDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getCompteDao();
    }
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SuppressionCompte() {
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
		boolean reponse = compteDao.disableCompte(Integer.parseInt(request.getParameter(ATT_COMPTE)));
		if(reponse)
		{
			this.getServletContext().getRequestDispatcher(VUE_CLIENTS).forward(request, response);
		}
		else {
			String resultat = "Erreur lors de la suppression";
			
			request.setAttribute(ATT_RESULTAT, resultat);
			
			this.getServletContext().getRequestDispatcher(VUE_AFFICHER_COMPTE).forward(request, response);
		}
	}

}
