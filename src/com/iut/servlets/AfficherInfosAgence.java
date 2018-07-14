package com.iut.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iut.beans.Agence;
import com.iut.beans.Client;
import com.iut.beans.Conseiller;
import com.iut.dao.AgenceDao;
import com.iut.dao.ConseillerDao;
import com.iut.dao.DAOFactory;

/**
 * Servlet implementation class AfficherInfosAgence
 */
@WebServlet("/AfficherInfosAgence")
public class AfficherInfosAgence extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String VUE				= "/WEB-INF/affichageInfosAgence.jsp";
	public static final String CONF_DAO_FACTORY = "daofactory";
	private static final String ATT_CLIENT		= "client";
	private static final String ATT_AGENCE		= "agence";
	private static final String ATT_CONSEILLER	= "conseiller";
	
	private AgenceDao agenceDao;
	private ConseillerDao conseillerDao;
	
	public void init() throws ServletException {
        this.agenceDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getAgenceDao();
        this.conseillerDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getConseillerDao();
    }
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AfficherInfosAgence() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//On récupère le Client de la session
		Client client = (Client) request.getSession().getAttribute(ATT_CLIENT);
		
		//On récupère l'Agence de ce Client
		Agence agence = agenceDao.getAgenceByClient(client);
		
		//On récupère le Conseiller de ce Client
		Conseiller conseiller = conseillerDao.getConseillerByClient(client);
		
		request.setAttribute(ATT_AGENCE, agence);
		request.setAttribute(ATT_CONSEILLER, conseiller);
		
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
