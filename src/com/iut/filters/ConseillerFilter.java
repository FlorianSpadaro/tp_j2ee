package com.iut.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class ConseillerFilter
 */
@WebFilter("/ConseillerFilter")
public class ConseillerFilter implements Filter {
	public static final String ATT_SESSION_CONSEILLER	= "conseiller";
	private static final String ATT_CONNEXION			= "connexion";
	public static final String VUE_INDEX 				= "/index.jsp";

    /**
     * Default constructor. 
     */
    public ConseillerFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute(ATT_SESSION_CONSEILLER) == null && (request.getParameter(ATT_CONNEXION) == null || !request.getParameter(ATT_CONNEXION).equals("true")))
		{
			response.sendRedirect(request.getContextPath() + VUE_INDEX );
		}
		else {
			chain.doFilter( request, response );
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
