package utils;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import filter.AuthorizationFilter;

public class Utils {
	protected static final Logger LOGGER = Logger
			.getLogger("ru.softlab.rsdh.webService");

	public static JSONObject proceedSCError(int status, String msg) {
		return proceedSCError(status, msg, false);
	}

	public static JSONObject proceedSCError(int status, String msg,
			boolean comleteResponse) {
		JSONObject jResponse = new JSONObject();
		try {
			jResponse.put("status", status);
			jResponse.put("data", msg);
			if (comleteResponse)
				jResponse = new JSONObject().put("response", jResponse);
		} catch (JSONException e) {
			LOGGER.log(Level.SEVERE, AuthorizationFilter.class.getName(), e);
		}
		return jResponse;
	}

	public static JSONObject proceedSCValidationError(String fieldCode,
			String msg) {
		if (msg.contains("<EUSERTEXT>")) {
			String[] strs = msg.split("EUSERTEXT>");
			msg = strs[1];
			msg = msg.substring(0, msg.length() - 2);
		}

		JSONObject jResponse = new JSONObject();
		try {
			jResponse.put("status", -4);
			JSONObject jField = new JSONObject();
			jField.put("errorMessage", msg);
			JSONObject jErrors = new JSONObject();
			jErrors.put(fieldCode, jField);
			jResponse.put("errors", jErrors);
		} catch (JSONException e) {
			LOGGER.log(Level.SEVERE, AuthorizationFilter.class.getName(), e);
		}
		return jResponse;
	}

}