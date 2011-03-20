package filter;

import java.io.IOException;
import java.math.BigDecimal;
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

import ru.softlab.rsdh.api.ObjectsFactory;
import ru.softlab.rsdh.api.classes.TSRPSysUserTunes;
import ru.softlab.rsdh.api.classes.TUSRProfile;
import utils.Utils;

/**
 * Servlet Filter implementation class ProfileFilter
 */
public class ProfileFilter implements Filter {
	protected static final Logger LOGGER = Logger
			.getLogger("ru.softlab.rsdh.webService");

	public static final String sessionProfileId = "RsdhProfileId";
	public static final String sessionProfileParams = "RsdhProfileParams";

	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req;

		if (request instanceof HttpServletRequest) {
			req = (HttpServletRequest) request;
			// Инициализируем контекст.
			TSRPSysUserTunes sysUserTunes;
			try {
				if (req.getSession().getAttribute(sessionProfileId) == null) {
					sysUserTunes = (TSRPSysUserTunes) ObjectsFactory
							.getObjectByCode("TSRPSysUserTunes", "SysUserTunes");
					sysUserTunes.initContext();
				} else {
					BigDecimal id = new BigDecimal(req.getSession()
							.getAttribute(sessionProfileId).toString());
					TUSRProfile profile = (TUSRProfile) ObjectsFactory
							.createObjById("TUSRProfile", id);
					profile.applyProfile(req.getSession()
							.getAttribute(sessionProfileParams).toString());
					System.out.println("Profile applyed successfully");
				}

				chain.doFilter(request, response);
			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, this.getClass().getName(), e);
				response.getWriter().write(
						Utils.proceedSCError(-1, e.getLocalizedMessage())
								.toString());
				response.getWriter().close();
			}
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
