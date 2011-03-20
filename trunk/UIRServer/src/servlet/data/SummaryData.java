package servlet.data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import servlet.RsdhServlet;

@SuppressWarnings("serial")
public class SummaryData extends RsdhServlet {
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		JSONObject jRequestData = jRequest.getJSONObject("data");

		Vector<HashMap<Integer, Object>> data = Data.getData(jRequestData
				.getString("masterDataSourceId"));
		Vector<String> metaData = Data.getMetaData(jRequestData
				.getString("masterDataSourceId"));

		if (data == null) {
			JSONObject jResponse = new JSONObject();
			jResponse.put("data", new JSONArray());
			jResponse.put("status", 0);
			return jResponse;
		}

		JSONArray jData = new JSONArray();
		JSONObject jRecord = new JSONObject();
		for (int i = 0; i < metaData.size(); i++) {
			String colCode = metaData.get(i);
			if (jRequestData.has(colCode)) {
				BigDecimal value = BigDecimal.valueOf(0);
				for (HashMap<Integer, Object> row : data) {
					if (row.get(i) instanceof Number) {
						if (jRequestData.getString(colCode).equalsIgnoreCase(
								"sum")) {
							value = value.add(BigDecimal.valueOf(((Number) row
									.get(i)).doubleValue()));
						} else if (jRequestData.getString(colCode)
								.equalsIgnoreCase("count"))
							value = value.add(BigDecimal.ONE);
					}
				}
				jRecord.put(colCode, value);
			}
		}
		jData.put(jRecord);
		JSONObject jResponse = new JSONObject();
		jResponse.put("data", jData);
		jResponse.put("status", 0);
		return jResponse;
	}
}
