package servlet.graph.actions;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import ru.softlab.rsdh.api.classes.TWIFUserAction;
import servlet.RsdhServlet;

/**
 * Servlet implementation class FinishUserAction
 */
@SuppressWarnings("serial")
public class FinishUserAction extends RsdhServlet {
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		double objectId = jRequest.getDouble("objectId");
		double queObjectId = jRequest.getDouble("queObjectId");
		try {
			TWIFUserAction userAction = new TWIFUserAction(new BigDecimal(
					queObjectId));

			try {
				userAction.finishActionsAnyExp(new BigDecimal(objectId), true,
						"OK", null);
			} catch (SQLException ex) {
				if (ex.getMessage().contains("ESIBAccessDenied"))
					userAction.finishActionsOwnExp(new BigDecimal(objectId),
							true, "OK", null);
				else
					throw ex;
			}

			JSONObject jResponse = new JSONObject();
			jResponse.put("status", 0);
			return jResponse;
		} catch (SQLException e) {
			return proceedError(e.getLocalizedMessage());
		}
	}
}
