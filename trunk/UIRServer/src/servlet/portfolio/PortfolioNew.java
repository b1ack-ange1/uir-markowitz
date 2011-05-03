package servlet.portfolio;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import servlet.LISEServlet;

public class PortfolioNew extends LISEServlet {

	@Override
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		JSONObject jResponse = new JSONObject();
		JSONArray jData = new JSONArray();

	

			// новый портфель
			String name = jRequest.get("name").toString();
			System.out.println("name = " + name);
			JSONArray records = (JSONArray) jRequest.get("records");
			for (int i = 0; i < records.length(); i++) {
				System.out.println("ticker = "
						+ records.getJSONObject(i).getString("id"));
			}

	
		jResponse.put("data", jData);
		jResponse.put("status", "0");
		return jResponse;
	}

}
