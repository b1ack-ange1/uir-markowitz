package servlet.metadata.rs;

import java.math.BigDecimal;

import org.json.JSONException;
import org.json.JSONObject;

import ru.softlab.rsdh.api.classes.TUSRShortcut;

/**
 * Servlet implementation class RSTuneList
 */
@SuppressWarnings("serial")
public class RSDetailTransitionsList extends RSBased {
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		JSONObject jRequestData = jRequest.getJSONObject("data");

		String objectId = jRequestData.getString("objectId");
		try {
			TUSRShortcut shortcut = new TUSRShortcut(new BigDecimal(objectId));

			return getResultSetData("TRSTuneList", shortcut
					.getDetailTransitionList());
		} catch (Exception e) {
			return proceedError(e.getLocalizedMessage());
		}
	}
}
