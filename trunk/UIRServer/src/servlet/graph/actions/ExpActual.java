package servlet.graph.actions;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import ru.softlab.rsdh.api.classes.TQUETaskActivation;
import ru.softlab.rsdh.api.classes.TWIFScenario;
import servlet.RsdhServlet;

@SuppressWarnings("serial")
public class ExpActual extends RsdhServlet {
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		double objectId = jRequest.getDouble("objectId");
		try {
			TQUETaskActivation taskActivation = new TQUETaskActivation(
					new BigDecimal(objectId));
			TWIFScenario scenario = new TWIFScenario(taskActivation.getTaskId());
			try {
				scenario.setAnyExperimentUrgency(taskActivation.getId(), jRequest.getBoolean("newActualState")); 
			} catch (SQLException e) {
				try {
				scenario.setOwnExperimentUrgency(taskActivation.getId(), jRequest.getBoolean("newActualState"));
				} catch (SQLException e2) {
					throw e2;
				}
			}
			JSONObject jResponse = new JSONObject();
			jResponse.put("status", 0);
			return jResponse;
		} catch (SQLException e) {
			return proceedError(e.getLocalizedMessage());
		}
	}

}
