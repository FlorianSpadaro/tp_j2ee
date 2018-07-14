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
import com.iut.dao.MessageDao;
import com.iut.form.EnvoiMessageForm;

/**
 * Servlet implementation class NouveauMessage
 */
@WebServlet("/NouveauMessage")
public class NouveauMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final String CONF_DAO_FACTORY = "daofactory";
	public static final String ATT_CONSEILLER	= "conseiller";
	public static final String ATT_CLIENT		= "client";
	public static final String ATT_FORM			= "form";
	public static final String VUE				= "/WEB-INF/nouveauMessage.jsp";
       
	private ClientDao clientDao;
	private ConseillerDao conseillerDao;
	private MessageDao messageDao;
	
	public void init() throws ServletException {
        this.clientDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getClientDao();
        this.conseillerDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getConseillerDao();
        this.messageDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getMessageDao();
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
		Client client = null;
		Conseiller conseiller = null;
		if(request.getSession().getAttribute(ATT_CONSEILLER) != null)
		{
			//S'il s'agit d'une session d'un Conseiller, alors on récupère le Client correspondant à l'ID passé en paramètre
			client = clientDao.getClientById(Integer.parseInt(request.getParameter(ATT_CLIENT)));
			
			//Puis on récupère le Conseiller de la session
			conseiller = (Conseiller) request.getSession().getAttribute(ATT_CONSEILLER);
		}
		else {
			//S'il s'agit d'une session d'un Client, alors on récupère le Conseiller correspondant à l'ID passé en paramètre et le Client de la session
			client = (Client) request.getSession().getAttribute(ATT_CLIENT);
			conseiller = conseillerDao.getConseillerById(Integer.parseInt(request.getParameter(ATT_CONSEILLER)));
		}
		
		request.setAttribute(ATT_CLIENT, client);
		request.setAttribute(ATT_CONSEILLER, conseiller);
		
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EnvoiMessageForm form = new EnvoiMessageForm(messageDao, clientDao, conseillerDao);
		
		//On crée le Message
		form.envoyerMessage(request);
		
		Client client = null;
		Conseiller conseiller = null;
		if(request.getSession().getAttribute(ATT_CONSEILLER) != null)
		{
			//S'il s'agit d'une session d'un Conseiller, alors on récupère le Client correspondant à l'ID passé en paramètre
			client = clientDao.getClientById(Integer.parseInt(request.getParameter(ATT_CLIENT)));
			
			//Puis on récupère le Conseiller de la session
			conseiller = (Conseiller) request.getSession().getAttribute(ATT_CONSEILLER);
		}
		else {
			//S'il s'agit d'une session d'un Client, alors on récupère le Conseiller correspondant à l'ID passé en paramètre et le Client de la session
			client = (Client) request.getSession().getAttribute(ATT_CLIENT);
			conseiller = conseillerDao.getConseillerById(Integer.parseInt(request.getParameter(ATT_CONSEILLER)));
		}
		
		request.setAttribute(ATT_CLIENT, client);
		request.setAttribute(ATT_CONSEILLER, conseiller);
		request.setAttribute(ATT_FORM, form);
		
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

}
