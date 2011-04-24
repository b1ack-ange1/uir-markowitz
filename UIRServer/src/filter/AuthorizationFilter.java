package filter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.Utils;

import db.ConnectionManager;

/**
 * Servlet Filter implementation class AuthorizationFilter
 */
public class AuthorizationFilter implements Filter {
	protected static final Logger LOGGER = Logger
			.getLogger("ru.softlab.rsdh.webService");

	private String username;
	private String password;

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req;

		if (request instanceof HttpServletRequest) {
			req = (HttpServletRequest) request;
			LOGGER.log(Level.FINE, req.getServletPath());
			String usr = null;
			String pass = null;
			//req.getSession().setAttribute("username", "admin");
			//req.getSession().setAttribute("password", "admin");

			if (username != null && password != null) {
				usr = username;
				pass = password;
			} else if (req.getSession().getAttribute("username") != null
					&& req.getSession().getAttribute("password") != null) {
				usr = req.getSession().getAttribute("username").toString();
				pass = req.getSession().getAttribute("password").toString();
			}

			System.out.println("in filter");
			if (usr != null && pass != null) {
				try {
					/*
					 * RsdhConnection.init(ConnectionManager.getConnection(usr,
					 * pass));
					 */

					chain.doFilter(request, response);
				} catch (/* SQL */Exception e) {
					LOGGER.log(Level.SEVERE,
							AuthorizationFilter.class.getName(), e);
					response.getWriter().write(
							Utils.proceedSCError(-1, e.getLocalizedMessage(),
									true).toString());
					response.getWriter().close();
				} finally {
					// RsdhConnection.closeConnection();
				}
			} else {
				if (req.getServletPath().contains("/sc/"))
				{	
					System.out.println("contains sc");
					response.getWriter().write(
							"<SCRIPT>//'\"]]>>isc_loginRequired");
				}
				else if (response instanceof HttpServletResponse)
					((HttpServletResponse) response)
							.sendError(HttpServletResponse.SC_FORBIDDEN);
				else
					response.getWriter().write("Need authorization!");
				response.getWriter().close();
			}
		}

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		username = fConfig.getInitParameter("username");
		password = fConfig.getInitParameter("password");
	}
}