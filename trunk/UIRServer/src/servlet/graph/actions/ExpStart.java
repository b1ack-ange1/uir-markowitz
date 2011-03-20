package servlet.graph.actions;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.softlab.rsdh.api.classes.TWIFExperiment;
import ru.softlab.rsdh.api.classes.TWIFScenario;
import servlet.RsdhServlet;

/**
 * Servlet implementation class ExpStart
 */
@SuppressWarnings("serial")
public class ExpStart extends RsdhServlet {
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		double objectId = jRequest.getDouble("objectId");
		try {
			TWIFScenario scen = new TWIFScenario(new BigDecimal(objectId));

			BigDecimal expId = scen.startExperiment(
					jRequest.optString("newName"),
					jRequest.optString("newNote"), scen.getCode(), null,
					jRequest.getString("objectParams"),
					jRequest.optBoolean("newSkipUA"));
			// TODO p_SkipUA

			TWIFExperiment experiment = new TWIFExperiment(expId);

			JSONObject jRecord = new JSONObject();
			jRecord.put("expId", expId);
			jRecord.put("name", experiment.getName());
			JSONArray jData = new JSONArray();
			jData.put(jRecord);
			JSONObject jResponse = new JSONObject();
			jResponse.put("data", jData);
			jResponse.put("status", 0);
			return jResponse;
		} catch (SQLException e) {
			return proceedError(e.getLocalizedMessage());
		}
	}
}
