package com.lise.markowitz.servlet;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Configuration extends HttpServlet {
	Map<String, String> configMap = new HashMap<String, String>();

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/javascript");
		response.getWriter().write("window.rsdhConfiguration = new Object();");
		for (String key : configMap.keySet())
			response.getWriter().write(
					"window.rsdhConfiguration['" + key + "'] = '"
							+ configMap.get(key) + "';");
		response.getWriter().close();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		for (Enumeration<String> e = config.getInitParameterNames(); e
				.hasMoreElements();) {
			String paramName = e.nextElement();
			configMap.put(paramName, config.getInitParameter(paramName));
		}
	}
}