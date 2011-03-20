package servlet.publications;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.softlab.rsdh.api.ObjectsFactory;
import ru.softlab.rsdh.api.classes.TPUBReport;
import ru.softlab.rsdh.api.rs.TRSStoredPublicationList;
import servlet.RsdhServlet;

@SuppressWarnings("serial")
@Deprecated
public class ReportList extends RsdhServlet {
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		JSONObject jRequestData = jRequest.getJSONObject("data");
		double objectId = jRequestData.getDouble("objectId");

		try {
			TPUBReport report = (TPUBReport) ObjectsFactory.createObjById(
					"TPUBReport", new BigDecimal(objectId));

			JSONArray jData = new JSONArray();
			for (TRSStoredPublicationList storedPublication : report
					.getStoredPublicationList()) {
				JSONObject jRecord = new JSONObject();
				jRecord.put("Id", storedPublication.getSPId());
				jRecord.put("Date", storedPublication.getSPQueueDate());//publicationId
				jRecord.put("ParamValues", storedPublication.getSPParamValues());
				jRecord.put("Moment", storedPublication.getMoment());
				jData.put(jRecord);
			}
			JSONObject jResponse = new JSONObject();
			jResponse.put("data", jData);
			jResponse.put("status", 0);
			return jResponse;
		} catch (SQLException e) {
			return proceedError(e.getLocalizedMessage());
		}
	}

}
