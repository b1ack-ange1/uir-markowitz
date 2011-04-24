package servlet.registration;

import javax.servlet.Servlet;

import org.json.JSONException;
import org.json.JSONObject;

import servlet.LISEServlet;

@SuppressWarnings("serial")
public class ChangePassword extends LISEServlet implements Servlet {
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		JSONObject jRequestData = jRequest.getJSONObject("data");

		try {
			if (jRequestData.has("newPassword")
					&& !jRequestData.getString("newPassword").isEmpty()) {
				
				
				String newPassword = jRequestData.getString("newPassword");
				// TODO смена пароля

				getSession().setAttribute("password", newPassword);

				JSONObject jResponse = new JSONObject();
				jResponse.put("status", 0);
				return jResponse;
			}
			return proceedError("Не все обязательные параметры заданы.");
		} catch (Exception e) {
			return proceedError(e.getLocalizedMessage());
		}
	}

}
