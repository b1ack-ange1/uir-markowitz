package servlet.graph;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.Servlet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.softlab.rsdh.api.ObjectsFactory;
import ru.softlab.rsdh.api.classes.TQUETask;
import ru.softlab.rsdh.api.classes.TQUETaskActivation;
import ru.softlab.rsdh.api.rs.TRSQueueObjectLink;
import servlet.RsdhServlet;

/**
 * Servlet implementation class GraphLinks
 */
@SuppressWarnings("serial")
public class GraphLinks extends RsdhServlet implements Servlet {
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		JSONObject jRequestData = jRequest.getJSONObject("data");

		double objectId = jRequestData.getDouble("objectId");
		try {
			ArrayList<TRSQueueObjectLink> linkList = null;
			try {
				TQUETask task = (TQUETask) ObjectsFactory.createObjById("TQUETask",
						new BigDecimal(objectId));
				linkList = task.getQueueObjectLinkList();
			} catch (SQLException inst) {
				TQUETaskActivation taskActivation = (TQUETaskActivation) ObjectsFactory
						.createObjById("TQUETaskActivation", new BigDecimal(
								objectId));
				linkList = taskActivation.getQueueObjectLinkList();
			}

			JSONArray jData = new JSONArray();
			if (linkList != null)
				for (TRSQueueObjectLink link : linkList) {
					JSONObject jRecord = new JSONObject();
					jRecord.put("IdFrom", link.getFromQueueObjectId());
					jRecord.put("IdTo", link.getToQueueObjectId());
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
