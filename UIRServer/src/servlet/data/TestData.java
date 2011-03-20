package servlet.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import servlet.RsdhServlet;

@SuppressWarnings("serial")
public class TestData extends RsdhServlet {

	@Override
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		JSONArray jData = new JSONArray();
		
		for (int i=0; i< 100; i++) {
			JSONObject jRecord = new JSONObject();
			jRecord.put("Col0", "Value0" + i);
			jRecord.put("Col1", "Value1" + i);
			jRecord.put("Col2", "Value2" + i);
			jRecord.put("Col3", "Value3" + i);
			jRecord.put("Col4", "Value4" + i);
			jData.put(jRecord);
		}
		
		JSONObject jResponse = new JSONObject();
		jResponse.put("data", jData);
		jResponse.put("status", 0);
		jResponse.put("startRow", 0);
		jResponse.put("endRow", 99);
		jResponse.put("totalRows", 100);
		return jResponse;
	}

}
