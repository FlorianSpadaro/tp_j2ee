package com.iut.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iut.beans.Message;
import com.iut.dao.ClientDao;
import com.iut.dao.DAOFactory;
import com.iut.dao.MessageDao;
import com.iut.form.EnvoiReponseMessageForm;

/**
 * Servlet implementation class MessageConseiller
 */
@WebServlet("/MessageConseiller")
public class MessageConseiller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final String CONF_DAO_FACTORY = "daofactory";
	private static final String ATT_MESSAGE		= "message";
	private static final String ATT_REPONSE		= "reponse";
	private static final String ATT_FORM		= "form";
	private static final String VUE				= "/WEB-INF/messageConseiller.jsp";
	
	private MessageDao messageDao;
	private ClientDao clientDao;
	
	public void init() throws ServletException {
        this.messageDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getMessageDao();
        this.clientDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getClientDao();
    }
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessageConseiller() {
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
		Message message = messageDao.getMessageById(Integer.parseInt(request.getParameter(ATT_MESSAGE)));
		message.setClient(clientDao.getClientByMessage(message));
		
		if(request.getParameter(ATT_REPONSE) != null)
		{
			EnvoiReponseMessageForm form = new EnvoiReponseMessageForm(messageDao);
			form.envoyerReponse(request);
			
			request.setAttribute(ATT_FORM, form);
		}
		
		message.setReponses(messageDao.getReponsesByMessage(message));
		
		messageDao.updateMessageLuConseiller(message);
		
		request.setAttribute(ATT_MESSAGE, message);
		
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

}
