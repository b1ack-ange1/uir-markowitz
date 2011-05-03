package filter;

import java.io.IOException;
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

public class PortfolioFilter implements Filter {
	protected static final Logger LOGGER = Logger.getLogger("lise.webService");

	private String username;
	private String password;
	private String portfolio;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req;

		if (request instanceof HttpServletRequest) {
			req = (HttpServletRequest) request;
			LOGGER.log(Level.FINE, req.getServletPath());
			String usr = null;
			String pass = null;
			String port = null;

			if (username != null && password != null && portfolio != null) {
				usr = username;
				pass = password;
				port = portfolio;
			} else if (req.getSession().getAttribute("username") != null
					&& req.getSession().getAttribute("password") != null
					&& req.getSession().getAttribute("portfolio") != null) {
				usr = req.getSession().getAttribute("username").toString();
				pass = req.getSession().getAttribute("password").toString();
				port = req.getSession().getAttribute("portfolio").toString();
			}

			System.out.println("in filter portfolio");
			if (usr != null && pass != null && port != null) {
				try {
					/*
					 * RsdhConnection.init(ConnectionManager.getConnection(usr,
					 * pass));
					 */

					chain.doFilter(request, response);
				} catch (/* SQL */Exception e) {
					LOGGER.log(Level.SEVERE,
							PortfolioFilter.class.getName(), e);
					response.getWriter().write(
							Utils.proceedSCError(-1, e.getLocalizedMessage(),
									true).toString());
					response.getWriter().close();
				} finally {
					// RsdhConnection.closeConnection();
				}
			} else {
				if (req.getServletPath().contains("/sc/")) {
					System.out.println("contains sc portfolio");

				} else if (response instanceof HttpServletResponse)
					((HttpServletResponse) response)
							.sendError(HttpServletResponse.SC_FORBIDDEN);
				else
					response.getWriter().write("Need portfolio selection!");
				response.getWriter().close();
			}
		}

	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		username = fConfig.getInitParameter("username");
		password = fConfig.getInitParameter("password");
		portfolio = fConfig.getInitParameter("portfolioId");

	}

}
