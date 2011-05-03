package servlet.ticker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import servlet.LISEServlet;

public class TickerList extends LISEServlet {

	@Override
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		JSONObject jResponse = new JSONObject();
		JSONArray jData = new JSONArray();

		if (jRequest.get("operationType").equals("fetch")) {

			JSONObject jRecord = new JSONObject();
			jRecord.put("name", "РусГидро");
			jRecord.put("id", "HYDR");
			jData.put(jRecord);

			jRecord = new JSONObject();
			jRecord.put("name", "Лукойл");
			jRecord.put("id", "LUK");
			jData.put(jRecord);
		} else if (jRequest.get("operationType").equals("remove")) {
			
		} else if (jRequest.get("operationType").equals("add")) {
			
		}

		jResponse.put("data", jData);
		jResponse.put("status", "0");
		return jResponse;
	}

}
