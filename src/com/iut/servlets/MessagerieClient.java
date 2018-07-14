package com.iut.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iut.beans.Client;
import com.iut.beans.Message;
import com.iut.dao.ConseillerDao;
import com.iut.dao.DAOFactory;
import com.iut.dao.MessageDao;

/**
 * Servlet implementation class MessagerieClient
 */
@WebServlet("/MessagerieClient")
public class MessagerieClient extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	public static final String CONF_DAO_FACTORY 	= "daofactory";
	public static final String ATT_MESSAGES_NON_LUS	= "messagesNonLus";
	public static final String ATT_MESSAGES_LUS		= "messagesLus";
	public static final String ATT_CLIENT			= "client";
	public static final String VUE					= "/WEB-INF/messagerieClient.jsp";
	
	private MessageDao messageDao;
	private ConseillerDao conseillerDao;
	
	public void init() throws ServletException {
        this.messageDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getMessageDao();
        this.conseillerDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getConseillerDao();
    }
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessagerieClient() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Client client = (Client) request.getSession().getAttribute(ATT_CLIENT);
		ArrayList<Message> messagesNonLus = messageDao.getMessagesNonLusByClient(client);
		for(Message message : messagesNonLus)
		{
			message.setConseiller(conseillerDao.getConseillerByMessage(message));
		}
		ArrayList<Message> messagesLus = messageDao.getMessagesLusByClient(client);
		for(Message message : messagesLus)
		{
			message.setConseiller(conseillerDao.getConseillerByMessage(message));
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
