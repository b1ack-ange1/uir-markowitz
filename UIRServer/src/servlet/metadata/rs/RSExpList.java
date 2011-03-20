package servlet.metadata.rs;

import java.math.BigDecimal;

import javax.servlet.Servlet;

import org.json.JSONException;
import org.json.JSONObject;

import ru.softlab.rsdh.api.classes.TWIFScenario;

/**
 * Servlet implementation class RSExpList
 */
@SuppressWarnings("serial")
public class RSExpList extends RSBased implements Servlet {

	@Override
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		JSONObject jRequestData = jRequest.getJSONObject("data");

		double objectId = jRequestData.getDouble("objectId");
		String operationType = jRequest.getString("operationType");
		try {
			TWIFScenario scenario = new TWIFScenario(new BigDecimal(objectId));

			if (operationType.equalsIgnoreCase("fetch")) {
				return getResultSetData("TRSExperimentList",
						scenario.getExperimentList(jRequestData
								.optString("objectParams")));
			} else if (operationType.equalsIgnoreCase("remove")) {
				if (jRequestData.getString("ExpExperimenterCode")
						.equalsIgnoreCase(
								getSession().getAttribute("username")
										.toString())) {
					scenario.deleteExperiment(new BigDecimal(jRequestData
							.getDouble("ExpId")));
				} else {
					scenario.deleteAnyExperiment(new BigDecimal(jRequestData
							.getDouble("ExpId")));
				}
				JSONObject jResponse = new JSONObject();
				jResponse.put("data", jRequestData);
				jResponse.put("status", 0);
				return jResponse;
			} else {
				throw new Exception("Не верный тип операции.");
			}
		} catch (Exception e) {
			return proceedError(e.getLocalizedMessage());
		}
	}
}
