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
import com.iut.dao.CompteDao;
import com.iut.dao.DAOFactory;

/**
 * Servlet implementation class NouveauMessage
 */
@WebServlet("/NouveauMessage")
public class NouveauMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final String CONF_DAO_FACTORY = "daofactory";
	public static final String ATT_CONSEILLER	= "conseiller";
	public static final String ATT_CLIENT		= "client";
	public static final String VUE				= "/WEB-INF/nouveauMessage.jsp";
       
	ClientDao clientDao;
	
	public void init() throws ServletException {
        this.clientDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getClientDao();
    }
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NouveauMessage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Client client = clientDao.getClientById(Integer.parseInt(request.getParameter(ATT_CLIENT)));
		Conseiller conseiller = (Conseiller) request.getSession().getAttribute(ATT_CONSEILLER);
		
		request.setAttribute(ATT_CLIENT, client);
		request.setAttribute(ATT_CONSEILLER, conseiller);
		
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
