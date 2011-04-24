package servlet.registration;

import java.util.logging.Level;

import org.json.JSONException;
import org.json.JSONObject;

import servlet.LISEServlet;
import db.ConnectionManager;

public class Login extends LISEServlet {
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		JSONObject jRequestData = jRequest.getJSONObject("data");

		if (jRequestData.has("user")) {
			try {
				if ((jRequestData.getString("user").equals("admin"))
						&& (jRequestData.getString("password").equals("admin"))) {

				} else {
					throw new Exception();
				}

				// TODO открыть соединение
			} catch (Exception e) {
				LOGGER.log(Level.INFO, this.getClass().getName(), e);
				return proceedError(e.getLocalizedMessage());
			} finally {
				// TODO закрыть соединение
			}

			request.get().getSession()
					.setAttribute("username", jRequestData.getString("user"));
			request.get()
					.getSession()
					.setAttribute("password",
							jRequestData.getString("password"));

			JSONObject jResponse = new JSONObject();
			jResponse.put("status", 0);
			return jResponse;
		}

		return proceedError("Не все обязательные параметры заданы.");
	}

}