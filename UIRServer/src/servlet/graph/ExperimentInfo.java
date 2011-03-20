package servlet.graph;

import java.math.BigDecimal;
import java.sql.SQLException;

import javax.servlet.Servlet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.softlab.rsdh.api.ObjectsFactory;
import ru.softlab.rsdh.api.classes.TQUETaskActivation;
import ru.softlab.rsdh.api.rs.TRSUserObjectParamValue;
import servlet.RsdhServlet;

/**
 * Servlet implementation class ExperimentInfo
 */
@SuppressWarnings("serial")
public class ExperimentInfo extends RsdhServlet implements Servlet {
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		double experimentId = jRequest.getDouble("experimentId");
		try {
			TQUETaskActivation taskActivation = null;
			taskActivation = (TQUETaskActivation) ObjectsFactory.createObjById(
					"TQUETaskActivation", new BigDecimal(experimentId));

			JSONObject jResponse = new JSONObject();
			JSONObject jRecord = new JSONObject();
			jRecord.put("Name", taskActivation.getName());
			String paramValues = "<ROW>";
			for (TRSUserObjectParamValue param : taskActivation
					.getTAParamValueList()) {
				paramValues += "<TPARAM><NAME>"
						+ param.getUserObjectParamCode() + "</NAME>";
				if (param.getUserObjectParamValue() != null)
					paramValues += "<VALUE>" + param.getUserObjectParamValue()
							+ "</VALUE>";
				paramValues += "</TPARAM>";
			}
			paramValues += "</ROW>";
			jRecord.put("paramValues", paramValues);

			JSONArray jData = new JSONArray();
			jData.put(jRecord);
			jResponse.put("data", jData);
			jResponse.put("status", 0);
			return jResponse;
		} catch (SQLException e) {
			return proceedError(e.getLocalizedMessage());
		}
	}
}
