package servlet.portfolio;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import portfolio.CompositionInternal;
import portfolio.UserPortfolio;
import resource.Ticker;
import servlet.LISEServlet;

import com.intersys.objects.CacheDatabase;
import com.intersys.objects.Database;
import com.intersys.objects.Id;

import db.ConnectionManager;

public class Portfolio extends LISEServlet {

	@Override
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		JSONObject jResponse = new JSONObject();
		JSONArray jData = new JSONArray();

		Connection connect = null;
		try {

			connect = ConnectionManager.getConnection(request.get()
					.getSession().getAttribute("username").toString(), request
					.get().getSession().getAttribute("password").toString());

			System.out.println("check1");
			System.out.println("connection is null = " + (connect == null));
			userinf.AuthData auth = null;

			System.out.println("check2");

			Database db = CacheDatabase.getDatabase(connect);

			System.out.println(jRequest);
			System.out.println(jRequest.getJSONObject("data"));
			System.out
					.println(jRequest.getJSONObject("data").getString("code"));
			int code = Integer.parseInt(jRequest.getJSONObject("data")
					.getString("code").toString());

			System.out.println("code=" + code);

			UserPortfolio port = (UserPortfolio) UserPortfolio.open(db, new Id(
					code));
			
			JSONObject jRecord = null;
			if (jRequest.get("operationType").equals("fetch")) {
				List tickerList = port.getComposition();
				for (int i=0; i<tickerList.size(); i++) {
					jRecord=new JSONObject();
					CompositionInternal composition = (CompositionInternal) tickerList.get(i);
					Ticker tick =composition.getTicker();
					jRecord.put("id", tick.getId());
					jRecord.put("name", tick.getName());
					jRecord.put("weight", composition.getPercentage());
					jData.put(jRecord);
				}
				List weightList = port.getCovariances();

			} else if (jRequest.get("operationType").equals("commit")) {

			}

		} catch (Exception e) {
			e.printStackTrace();
			if (connect != null) {
				try {
					connect.close();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
			return proceedError(e.getMessage());
		} finally {

		}

		jResponse.put("status", 0);
		jResponse.put("data", jData);
		return jResponse;
	}

}
