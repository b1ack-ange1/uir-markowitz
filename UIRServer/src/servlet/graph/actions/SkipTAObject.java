package servlet.graph.actions;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import ru.softlab.rsdh.api.classes.TQUETaskActivation;
import ru.softlab.rsdh.api.rs.TRSTAObjectList;
import servlet.RsdhServlet;

/**
 * Servlet implementation class SkipObject
 */
@SuppressWarnings("serial")
public class SkipTAObject extends RsdhServlet {
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		double objectId = jRequest.getDouble("objectId");
		double queObjectId = jRequest.getDouble("queObjectId");
		boolean skip = jRequest.getBoolean("skip");
		try {
			TQUETaskActivation taskActivation = new TQUETaskActivation(
					new BigDecimal(objectId));

			BigDecimal queueObjectId = new BigDecimal(queObjectId);
			for (TRSTAObjectList taObject : taskActivation.getTAObjectList())
				if (taObject.getQueueObjectId().equals(queueObjectId)) {
					taskActivation.skipTAObject(taObject.getTAObjectId(), skip);
					break;
				}

			JSONObject jResponse = new JSONObject();
			jResponse.put("status", 0);
			return jResponse;
		} catch (SQLException e) {
			return proceedError(e.getLocalizedMessage());
		}
	}
}
