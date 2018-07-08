package com.iut.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iut.dao.CompteDao;
import com.iut.dao.DAOFactory;
import com.iut.form.VersementForm;

/**
 * Servlet implementation class Versement
 */
@WebServlet("/Versement")
public class Versement extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final String CONF_DAO_FACTORY = "daofactory";
	public static final String ATT_FORM			= "form";
	public static final String VUE				= "/WEB-INF/nouvelleTransaction.jsp";
	
	CompteDao compteDao;
	
	public void init() throws ServletException {
        this.compteDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getCompteDao();
    }
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Versement() {
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
		VersementForm form = new VersementForm(compteDao);
		form.versement(request);
		
		request.setAttribute(VUE, form);
		
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

}
