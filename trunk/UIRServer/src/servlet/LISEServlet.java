package servlet;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import utils.Utils;

@SuppressWarnings("serial")
public abstract class LISEServlet extends HttpServlet {
	protected static final Logger LOGGER = Logger
			.getLogger("lise.webService");

	protected static ThreadLocal<HttpServletRequest> request = new ThreadLocal<HttpServletRequest>();
	protected static ThreadLocal<HttpSession> _session = new ThreadLocal<HttpSession>();

	protected static HttpSession getSession() {
		if (_session.get() == null)
			return request.get().getSession();
		return _session.get();
	}

	protected static void setSession(HttpSession session) {
		_session.set(session);
	}

	public JSONObject proceedError(String msg) {
		return proceedError(-1, msg);
	}

	protected JSONObject proceedError(int status, String msg) {
		return Utils.proceedSCError(status, msg);
	}

	protected JSONObject proceedValidationError(String fieldCode, String msg) {
		return Utils.proceedSCValidationError(fieldCode, msg);
	}

	private void processRequest(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		req.setCharacterEncoding("cp1251");
		resp.setCharacterEncoding("utf-8");
		request.set(req);
		String jRequest = "";
		if (req.getContentLength() > 0) {
			int buffLength = 256;
			if (req.getContentLength() < buffLength)
				buffLength = req.getContentLength();
			char[] buffer = new char[buffLength];
			int i = 0;
			while ((i = req.getReader().read(buffer)) > -1) {
				jRequest += new String(buffer, 0, i);
			}
		} else {
			jRequest = "{}";
		}

		LOGGER.log(Level.FINEST, this.toString() + "	<-:	" + jRequest);

		try {
			String jResponse = new JSONObject().put("response",
					doAction(new JSONObject(jRequest))).toString();

			LOGGER.log(Level.FINEST, this.toString() + "	->:	" + jResponse);

			resp.getWriter().write(jResponse);
		} catch (Exception e) {
			try {
				LOGGER.log(Level.SEVERE, this.getClass().getName(), e);
				String jResponse = new JSONObject().put("response",
						proceedError(e.getLocalizedMessage())).toString();

				LOGGER.log(Level.FINEST, this.toString() + "	->:	" + jResponse);

				resp.getWriter().write(jResponse);
			} catch (JSONException e1) {
				LOGGER.log(Level.SEVERE, this.getClass().getName(), e1);

				resp.getWriter().write(e.getLocalizedMessage());
			}
		}
	}

	@SuppressWarnings("deprecation")
	protected final void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setStatus(HttpServletResponse.SC_BAD_REQUEST,
				"Use POST protocol instead.");
	}

	protected final void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		processRequest(req, resp);
	}

	protected abstract JSONObject doAction(JSONObject jRequest)
			throws JSONException;
}
