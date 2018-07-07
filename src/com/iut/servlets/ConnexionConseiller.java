package com.iut.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.iut.beans.Conseiller;
import com.iut.dao.ConseillerDao;
import com.iut.dao.DAOFactory;
import com.iut.form.ConnexionForm;

/**
 * Servlet implementation class ConnexionConseiller
 */
@WebServlet("/ConnexionConseiller")
public class ConnexionConseiller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final String CONF_DAO_FACTORY = "daofactory";
    public static final String ATT_FORM         = "form";
    public static final String ATT_TYPE 		= "type";
    public static final String ATT_CONSEILLER 	= "conseiller";
	public static final String VUE_INDEX 		= "/index.jsp";
	public static final String VUE_CONSEILLER 	= "/conseiller/accueil";
    
	private ConseillerDao conseillerDao;
	
	public void init() throws ServletException {
        this.conseillerDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getConseillerDao();
    }
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConnexionConseiller() {
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
		
		ConnexionForm form = new ConnexionForm(conseillerDao);
		Conseiller conseiller = form.connecterConseiller(request);
		
		if(conseiller != null)
		{
			session.setAttribute(ATT_CONSEILLER, conseiller);
			
			this.getServletContext().getRequestDispatcher(VUE_CONSEILLER).forward(request, response);
		}
		else {
			request.setAttribute(ATT_TYPE, type);
			request.setAttribute(ATT_FORM, form);
			
			this.getServletContext().getRequestDispatcher(VUE_INDEX).forward(request, response);
		}
	}

}
