package com.iut.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iut.beans.Conseiller;
import com.iut.beans.Message;
import com.iut.dao.ClientDao;
import com.iut.dao.DAOFactory;
import com.iut.dao.MessageDao;

/**
 * Servlet implementation class MessagesConseiller
 */
@WebServlet("/MessagesConseiller")
public class MessagerieConseiller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final String CONF_DAO_FACTORY 	= "daofactory";
	public static final String ATT_CONSEILLER		= "conseiller";
	public static final String ATT_MESSAGES_NON_LUS	= "messagesNonLus";
	public static final String ATT_MESSAGES_LUS		= "messagesLus";
	public static final String VUE					= "/WEB-INF/messagerieConseiller.jsp";
	
	private MessageDao messageDao;
	private ClientDao clientDao;
	
	public void init() throws ServletException {
        this.messageDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getMessageDao();
        this.clientDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getClientDao();
    }
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessagerieConseiller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Conseiller conseiller = (Conseiller) request.getSession().getAttribute(ATT_CONSEILLER);
		ArrayList<Message> messagesNonLus = messageDao.getMessagesNonLusByConseiller(conseiller);
		for(Message message : messagesNonLus)
		{
			message.setClient(clientDao.getClientByMessage(message));
		}
		ArrayList<Message> messagesLus = messageDao.getMessagesLusByConseiller(conseiller);
		for(Message message : messagesLus)
		{
			message.setClient(clientDao.getClientByMessage(message));
		}
		
		request.setAttribute(ATT_MESSAGES_LUS, messagesLus);
		request.setAttribute(ATT_MESSAGES_NON_LUS, messagesNonLus);
		
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
