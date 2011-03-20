package servlet.graph.actions;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import ru.softlab.rsdh.api.classes.TQUETaskActivation;
import ru.softlab.rsdh.api.classes.TWIFScenario;
import servlet.RsdhServlet;

@SuppressWarnings("serial")
public class ExpArchive extends RsdhServlet {
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		double objectId = jRequest.getDouble("objectId");
		try {
			TQUETaskActivation taskActivation = new TQUETaskActivation(
					new BigDecimal(objectId));
			TWIFScenario scenario = new TWIFScenario(taskActivation.getTaskId());
			try {
				if (!jRequest.getBoolean("newArchiveState"))
					scenario.unArchiveAnyExperiment(taskActivation.getId());
				else
					scenario.archiveAnyExperiment(taskActivation.getId());
			} catch (SQLException e) {
				try {
					if (!jRequest.getBoolean("newArchiveState"))
						scenario.unArchiveExperiment(taskActivation.getId());
					else
						scenario.archiveExperiment(taskActivation.getId());
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
