package servlet.portfolio;

import java.sql.Connection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import portfolio.UserPortfolio;
import servlet.LISEServlet;
import userinf.AuthData;

import com.intersys.classes.RelationshipObject;
import com.intersys.objects.CacheDatabase;
import com.intersys.objects.Database;

import db.ConnectionManager;

public class PortfolioList extends LISEServlet {

	@Override
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		JSONObject jRequestData = jRequest.getJSONObject("data");
		Connection connect = null;
		try {
			if ((request.get().getSession().getAttribute("username") != null)
					&& (request.get().getSession().getAttribute("password") != null)) {

				// TODO запрос портфелей
				connect = ConnectionManager.getConnection(request.get()
						.getSession().getAttribute("username").toString(),
						request.get().getSession().getAttribute("password")
								.toString());

				System.out.println("check1");
				System.out.println("connection is null = " + (connect == null));
				userinf.AuthData auth = null;
				try {
					System.out.println("check2");

					Database db = CacheDatabase.getDatabase(connect);

					System.out.println("database is null = " + (db == null));
					System.out.println("check2.5");
					auth = (AuthData) userinf.AuthData.getObjectByLogin(db,
							request.get().getSession().getAttribute("username")
									.toString());

					System.out.println("check2.7");

				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(e.getMessage());
					connect.close();
				}
				System.out.println("check3");
				System.out.println("auth is null = " + (auth == null));

				if (auth == null) {
					JSONObject jResponse = new JSONObject();
					jResponse.put("status", 0);
					jResponse.put("data", "authorization is null");
					return jResponse;
				}
				List portList = auth.getPortfoliosList();
				// Map portList = (Map) auth.getPortfolios();

				System.out.println("check4");
				System.out.println("portList is null = " + (portList == null));

				JSONArray jData = new JSONArray();

				JSONObject jRecord = null;

				/*
				 * Iterator iter = portList.keySet().iterator(); while
				 * (iter.hasNext()) { UserPortfolio pn = (UserPortfolio)
				 * (portList.get(iter .next())); jRecord = new JSONObject();
				 * jRecord.put("Id", pn.getId()); jRecord.put("Name",
				 * pn.getName()); System.out.println(jRecord);
				 * jData.put(jRecord); }
				 */
				for (int i = 0; i < portList.size(); i++) {
					UserPortfolio pn = (UserPortfolio) (portList.get(i));
					jRecord = new JSONObject();
					jRecord.put("Id", pn.getId());
					jRecord.put("Name", pn.getName());
					System.out.println(jRecord);
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
