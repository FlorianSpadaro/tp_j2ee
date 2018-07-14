package com.iut.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.iut.beans.Client;
import com.iut.beans.Compte;
import com.iut.beans.Conseiller;
import com.iut.dao.ClientDao;
import com.iut.dao.CompteDao;
import com.iut.dao.DAOFactory;

/**
 * Servlet implementation class AfficherComptesDecouverts
 */
@WebServlet("/AfficherComptesDecouverts")
public class AfficherComptesDecouverts extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	public static final String CONF_DAO_FACTORY = "daofactory";
	public static final String ATT_COMPTES		= "comptes";
	public static final String ATT_CONSEILLER	= "conseiller";
	public static final String VUE				= "/WEB-INF/comptesDecouverts.jsp";
	
	private CompteDao compteDao;
	private ClientDao clientDao;
	
	public void init() throws ServletException {
        this.clientDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getClientDao();
        this.compteDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getCompteDao();
    }
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AfficherComptesDecouverts() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		//On récupère le Conseiller de la session
		Conseiller conseiller = (Conseiller) session.getAttribute(ATT_CONSEILLER);
		
		//On récupère la liste des comptes à découvert des Clients du Conseiller
		ArrayList<Compte> comptesDecouverts = compteDao.getComptesDecouverts(conseiller);
		for(Compte compte : comptesDecouverts)
		{
			//Pour chaque Compte à découvert, on récupère ses propriétaires
			ArrayList<Client> proprietaires = clientDao.getClientsByCompte(compte);
			compte.setProprietaire1(proprietaires.get(0));
			compte.setProprietaire2(proprietaires.get(1));
		}
		
		request.setAttribute(ATT_COMPTES, comptesDecouverts);
		
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		//On récupère le Conseiller de la session
		Conseiller conseiller = (Conseiller) session.getAttribute(ATT_CONSEILLER);
		
		//On récupère la liste des comptes à découvert des Clients du Conseiller
		ArrayList<Compte> comptesDecouverts = compteDao.getComptesDecouverts(conseiller);
		for(Compte compte : comptesDecouverts)
		{
			//Pour chaque Compte à découvert, on récupère ses propriétaires
			ArrayList<Client> proprietaires = clientDao.getClientsByCompte(compte);
			compte.setProprietaire1(proprietaires.get(0));
			compte.setProprietaire2(proprietaires.get(1));
		}
		
		request.setAttribute(ATT_COMPTES, comptesDecouverts);
		
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

}
