package servlet.portfolio;

import java.sql.Connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.intersys.objects.CacheDatabase;
import com.intersys.objects.Database;
import com.intersys.objects.Id;

import db.ConnectionManager;

import portfolio.UserPortfolio;

import resource.Ticker;
import servlet.LISEServlet;
import userinf.AuthData;

public class PortfolioNew extends LISEServlet {

	@Override
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		JSONObject jResponse = new JSONObject();
		JSONArray jData = new JSONArray();
		try {
			Connection connect = ConnectionManager.getConnection(request.get()
					.getSession().getAttribute("username").toString(), request
					.get().getSession().getAttribute("password").toString());

			System.out.println("check1");
			System.out.println("connection is null = " + (connect == null));
			userinf.AuthData auth = null;

			System.out.println("check2");

			Database db = CacheDatabase.getDatabase(connect);

			UserPortfolio port = new UserPortfolio(db);

			// новый портфель
			String name = jRequest.get("name").toString();
			System.out.println("name = " + name);
			JSONArray records = (JSONArray) jRequest.get("records");
			for (int i = 0; i < records.length(); i++) {
				int id = Integer.parseInt(records.getJSONObject(i).getString(
						"id"));
				double weight = Double.parseDouble(records.getJSONObject(i)
						.getString("weight"));
				port.addTicker((Ticker) Ticker.open(db, new Id(id)), weight);
				System.out.println("ticker = " + id + " " + weight);
			}
			port.save();
			auth = (AuthData) userinf.AuthData.getObjectByLogin(db, request
					.get().getSession().getAttribute("username").toString());
			auth.addPortfolio(port);
			auth.save();
		} catch (Exception e) {
			e.printStackTrace();
			return proceedError(e.getMessage());
		}
		jResponse.put("data", jData);
		jResponse.put("status", "0");
		return jResponse;
	}

}
