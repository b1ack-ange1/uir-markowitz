package servlet.transitions;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.softlab.rsdh.api.classes.TUSRTransition;
import ru.softlab.rsdh.api.rs.TRSUserTuneObjectParamValue;
import servlet.RsdhServlet;

@SuppressWarnings("serial")
public class TransitionParams extends RsdhServlet {

	@Override
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		JSONObject jRequestData = jRequest.getJSONObject("data");

		double transitionId = jRequestData.getDouble("transitionId");

		TUSRTransition transition = new TUSRTransition(new BigDecimal(
				transitionId));

		JSONArray jData = new JSONArray();
		try {
			ArrayList<TRSUserTuneObjectParamValue> pvl = transition
					.getTransitionParamValueList(jRequestData
							.optString("params"), jRequestData
							.optString("columns"), jRequestData
							.optString("context"));

			for (TRSUserTuneObjectParamValue param : pvl) {
				JSONObject jRecord = new JSONObject();
				jRecord.put("Id", param.getUserObjectParamId());
				jRecord.put("Code", param.getUserObjectParamCode());
				jRecord.put("Value", param.getUserObjectParamValue());
				jRecord.put("IsHidden", param.isHiddenValue());
				jData.put(jRecord);
			}
		} catch (SQLException e) {
			return proceedError(e.getLocalizedMessage());
		}
		JSONObject jResponse = new JSONObject();
		jResponse.put("data", jData);
		jResponse.put("status", 0);
		return jResponse;
	}

}
