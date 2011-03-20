package servlet.registration;

import java.util.logging.Level;

import org.json.JSONException;
import org.json.JSONObject;

import servlet.RsdhServlet;

@SuppressWarnings("serial")
public class Logoff extends RsdhServlet {

	@Override
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		try {
			request.get().getSession().invalidate();
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, this.getClass().getName(), e);
		}

		JSONObject jResponse = new JSONObject();
		jResponse.put("status", 0);
		return jResponse;
	}

}
