package servlet.ticker;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import resource.Ticker;
import servlet.LISEServlet;

import com.intersys.objects.CacheDatabase;
import com.intersys.objects.Database;
import com.intersys.objects.Id;

import db.ConnectionManager;

public class ValueList extends LISEServlet {

	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		JSONObject jRequestData = jRequest.getJSONObject("data");
		JSONArray jData = new JSONArray();
		Connection connect = null;
		try {
			if ((request.get().getSession().getAttribute("username") != null)
					&& (request.get().getSession().getAttribute("password") != null)) {

				// TODO запрос портфелей
				connect = ConnectionManager.getConnection(request.get()
						.getSession().getAttribute("username").toString(),
						request.get().getSession().getAttribute("password")
								.toString());

				Database db = CacheDatabase.getDatabase(connect);
				int from = jRequestData.getInt("from");
				int till = jRequestData.getInt("till");

				JSONArray jTickIn = new JSONArray();
				JSONObject jRecord = null;
				for (int i = 0; i < jTickIn.length(); i++) {
					int id = ((JSONObject) jTickIn.get(i)).getInt("id");
					Ticker tick = (Ticker) Ticker.open(db, new Id(id));
					Map<Timestamp, Double> priceMap = tick.getPrices(
							new Timestamp(from), new Timestamp(till));

					Set<Timestamp> set = priceMap.keySet();
					Object[] setArr = set.toArray();
					for (int j = 0; j < setArr.length; j++) {
						jRecord.put("date", ((Timestamp) setArr[i]).getTime());
						jRecord.put("value", priceMap.get(setArr[j]));
					}

					jRecord = new JSONObject();
					jRecord.put("id", id);
					jData.put(jRecord);
				}

				connect.close();
				JSONObject jResponse = new JSONObject();
				jResponse.put("status", 0);
				jResponse.put("data", jData);
				return jResponse;

			} else {
				return proceedError("Login first!");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return proceedError(e.getMessage());
		} finally {

		}

	}
}
