package com.iut.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.iut.beans.Client;
import com.iut.dao.ClientDao;
import com.iut.dao.DAOFactory;
import com.iut.form.ConnexionForm;

/**
 * Servlet implementation class ConnexionClient
 */
@WebServlet("/ConnexionClient")
public class ConnexionClient extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final String CONF_DAO_FACTORY = "daofactory";
	public static final String ATT_CLIENT       = "client";
    public static final String ATT_FORM         = "form";
    public static final String ATT_TYPE 		= "type";
	public static final String VUE_INDEX 		= "/index.jsp";
	public static final String VUE_CLIENT 		= "/client/accueil";
	
	private ClientDao clientDao;
	
	public void init() throws ServletException {
        this.clientDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getClientDao();
    }
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConnexionClient() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher(VUE_INDEX).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String type = (String)request.getParameter(ATT_TYPE);
		
		ConnexionForm form = new ConnexionForm(clientDao);
		Client client = form.connecterClient(request);
		
		if(client != null)
		{
			session.setAttribute(ATT_CLIENT, client);
			
			this.getServletContext().getRequestDispatcher(VUE_CLIENT).forward(request, response);
		}
		else {
			request.setAttribute(ATT_FORM, form);
			request.setAttribute(ATT_TYPE, type);
			
			this.getServletContext().getRequestDispatcher(VUE_INDEX).forward(request, response);
		}
	}

}
