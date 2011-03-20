package servlet.graph.actions;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import ru.softlab.rsdh.api.classes.TQUETask;
import ru.softlab.rsdh.api.classes.TQUETaskActivation;
import servlet.RsdhServlet;

/**
 * Servlet implementation class ExpContinue
 */
@SuppressWarnings("serial")
public class ExpContinue extends RsdhServlet {
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		double objectId = jRequest.getDouble("objectId");
		try {
			TQUETaskActivation taskActivation = new TQUETaskActivation(
					new BigDecimal(objectId));
			TQUETask task = new TQUETask(taskActivation.getTaskId());

			task.resumeTaskActivation(taskActivation.getId());

			JSONObject jResponse = new JSONObject();
			jResponse.put("status", 0);
			return jResponse;
		} catch (SQLException e) {
			return proceedError(e.getLocalizedMessage());
		}
	}
}
