package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Servlet Filter implementation class RequestCharacterEncodingFilter
 */
public class RequestCharacterEncodingFilter implements Filter {
	private String characterEncoding;

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
		request.setCharacterEncoding(characterEncoding);
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		characterEncoding = fConfig.getInitParameter("characterEncoding");
		if (characterEncoding == null)
			throw new ServletException(
					"\"characterEncoding\" parameter required for "
							+ this.getClass().getName() + " filter.");
	}

}
