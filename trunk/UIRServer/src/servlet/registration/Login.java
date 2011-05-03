package servlet.registration;

import java.sql.Connection;
import java.util.logging.Level;

import org.json.JSONException;
import org.json.JSONObject;

import servlet.LISEServlet;
import db.ConnectionManager;

public class Login extends LISEServlet {
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		JSONObject jRequestData = jRequest.getJSONObject("data");
		Connection connect = null;

		if (jRequestData.has("user")) {
			try {

				connect = ConnectionManager.getConnection(
						jRequestData.getString("user"),
						jRequestData.getString("password"));

				connect.close();

				request.get()
						.getSession()
						.setAttribute("username",
								jRequestData.getString("user"));
				request.get()
						.getSession()
						.setAttribute("password",
								jRequestData.getString("password"));

				JSONObject jResponse = new JSONObject();
				jResponse.put("status", 0);
				System.out.println("login yahoo good");
				return jResponse;

			} catch (Exception e) {
				LOGGER.log(Level.INFO, this.getClass().getName(), e);
				return proceedError(e.getLocalizedMessage());
			} finally {

			}

		}

		return proceedError("Не все обязательные параметры заданы.");
	}

}