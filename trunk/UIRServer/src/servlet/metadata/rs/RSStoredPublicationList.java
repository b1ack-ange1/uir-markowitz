package servlet.metadata.rs;

import java.math.BigDecimal;

import org.json.JSONException;
import org.json.JSONObject;

import ru.softlab.rsdh.api.ObjectsFactory;
import ru.softlab.rsdh.api.classes.TPUBReport;

@SuppressWarnings("serial")
public class RSStoredPublicationList extends RSBased {

	@Override
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		JSONObject jRequestData = jRequest.getJSONObject("data");

		String objectId = jRequestData.getString("objectId");
		try {
			TPUBReport report = (TPUBReport) ObjectsFactory.createObjById(
					"TPUBReport", new BigDecimal(objectId));

			return getResultSetData("TRSStoredPublicationList",
					report.getStoredPublicationList());
		} catch (Exception e) {
			return proceedError(e.getLocalizedMessage());
		}
	}

}
