package servlet.graph.actions;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import ru.softlab.rsdh.api.classes.TWIFUserAction;
import servlet.RsdhServlet;

/**
 * Servlet implementation class StartUserAction
 */
@SuppressWarnings("serial")
public class StartUserAction extends RsdhServlet {
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		double objectId = jRequest.getDouble("objectId");
		double queObjectId = jRequest.getDouble("queObjectId");
		TWIFUserAction userAction = new TWIFUserAction(new BigDecimal(
				queObjectId));

		try {
			try {
				userAction.startActionsAnyExp(new BigDecimal(objectId));
			} catch (SQLException ex) {
				if (ex.getMessage().contains("ESIBAccessDenied"))
					userAction.startActionsOwnExp(new BigDecimal(objectId));
				else
					throw ex;
			}
		} catch (SQLException e) {
			return proceedError(e.getLocalizedMessage());
		}

		JSONObject jResponse = new JSONObject();
		jResponse.put("status", 0);
		return jResponse;

	}
}
