package servlet.ticker;

import java.util.List;
import java.sql.Connection;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import portfolio.UserPortfolio;

import com.intersys.objects.CacheDatabase;
import com.intersys.objects.Database;

import db.ConnectionManager;

import resource.Ticker;
import servlet.LISEServlet;
import userinf.AuthData;

public class TickerList extends LISEServlet {

	@Override
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		JSONObject jResponse = new JSONObject();
		JSONArray jData = new JSONArray();

		if (jRequest.get("operationType").equals("fetch")) {
			try {
				if ((request.get().getSession().getAttribute("username") != null)
						&& (request.get().getSession().getAttribute("password") != null)) {

					// TODO запрос портфелей
					Connection connect = ConnectionManager.getConnection(
							request.get().getSession().getAttribute("username")
									.toString(), request.get().getSession()
									.getAttribute("password").toString());

					System.out.println("check1");
					System.out.println("connection is null = "
							+ (connect == null));

					System.out.println("check2");

					Database db = CacheDatabase.getDatabase(connect);

					System.out.println("database is null = " + (db == null));
					System.out.println("check2.5");
					List tickerList = Ticker.getAllTickers(db);

					System.out.println("check2.7");

					JSONObject jRecord = null;
					for (int i = 0; i < tickerList.size(); i++) {
						Ticker pn = (Ticker) (tickerList.get(i));
						jRecord = new JSONObject();
						jRecord.put("id", pn.getCode());
						jRecord.put("name", pn.getName());
						jRecord.put("cacheId", pn.getId());
						
						System.out.println(jRecord);
						jData.put(jRecord);
					}

					connect.close();

				} else {
					return proceedError("Login first!");
				}

			} catch (Exception e) {
				return proceedError(e.getMessage());
			}
		} else if (jRequest.get("operationType").equals("remove")) {

		} else if (jRequest.get("operationType").equals("add")) {

		}

		jResponse.put("data", jData);
		jResponse.put("status", "0");
		return jResponse;
	}

}
