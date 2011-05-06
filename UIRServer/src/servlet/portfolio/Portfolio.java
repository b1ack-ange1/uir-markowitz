package servlet.portfolio;

import java.sql.Connection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import portfolio.UserPortfolio;

import com.intersys.objects.CacheDatabase;
import com.intersys.objects.Database;
import com.intersys.objects.Id;

import db.ConnectionManager;

import resource.Ticker;
import servlet.LISEServlet;
import userinf.AuthData;

public class Portfolio extends LISEServlet {

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
			int code = Integer.parseInt(jRequest.get("code").toString());
			System.out.println("code="+code);
			UserPortfolio port = (UserPortfolio) UserPortfolio.open(db, new Id(code));
			
			if (jRequest.get("operationType").equals("fetch")) {
				List tickerList = port.getComposition();
				List weightList = port.getCovariances();
				
			} else if (jRequest.get("operationType").equals("commit")) {

			}
			connect.close();

		} catch (Exception e) {
			e.printStackTrace();
			return proceedError(e.getMessage());
		}

		jResponse.put("status", 0);
		jResponse.put("data", jData);
		return jResponse;
	}

}
