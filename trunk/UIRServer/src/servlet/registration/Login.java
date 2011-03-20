package servlet.registration;

import java.util.logging.Level;

import org.json.JSONException;
import org.json.JSONObject;

import ru.softlab.rsdh.api.RsdhConnection;
import servlet.RsdhServlet;
import db.ConnectionManager;

@SuppressWarnings("serial")
public class Login extends RsdhServlet {
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		JSONObject jRequestData = jRequest.getJSONObject("data");

		if (jRequestData.has("user")) {
			try {
				RsdhConnection.init(ConnectionManager.getConnection(
						jRequestData.getString("user"),
						jRequestData.getString("password")));
			} catch (Exception e) {
				LOGGER.log(Level.INFO, this.getClass().getName(), e);
				return proceedError(e.getLocalizedMessage());
			} finally {
				RsdhConnection.closeConnection();
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
