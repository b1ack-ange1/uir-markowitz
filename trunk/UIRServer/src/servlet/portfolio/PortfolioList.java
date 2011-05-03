package servlet.portfolio;

import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import portfolio.UserPortfolio;

import servlet.LISEServlet;
import userinf.AuthData;

import com.intersys.classes.RelationshipObject;
import com.jalapeno.ApplicationContext;
import com.jalapeno.ObjectManager;

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

				ObjectManager objManager = ApplicationContext
						.createObjectManager(connect);
				System.out.println("check2");
				System.out.println("objManager is null = "
						+ (objManager == null));

				AuthData auth = null;
				System.out.println("check2.5");
				try {
					String sql = "SELECT SQLUser.\"Authorization\".%ID FROM SQLUser.\"Authorization\" WHERE (SQLUser.\"Authorization\".Login = 'Admin')";

					System.out.println("check2.6");
					Iterator employees = objManager.openByQuery(AuthData.class,
							sql, null);
					System.out.println("check2.7");
					for (Iterator it = employees; it.hasNext();) {
						System.out.println("check2.8");
						AuthData nextEmp = (AuthData) it.next();
						auth = nextEmp;
						break;
					}
					/*
					 * auth = (Authorization) objManager.openById(
					 * Authorization.class, 1);
					 */

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

				RelationshipObject portList = auth.getPortfolios();
				System.out.println("check4");
				System.out.println("portList is null = " + (portList == null));

				JSONArray jData = new JSONArray();

				JSONObject jRecord = null;

				for (int i = 0; i < portList.size(); i++) {
					jRecord.put("Id", ((UserPortfolio) portList.get(i)).getId());
					jRecord.put(
							"Name",
							"Портфель"
									+ ((UserPortfolio) portList.get(i)).getId());
					jData.put(jRecord);
				}/*
				 * JSONObject jRecord = new JSONObject();
				 * 
				 * jRecord.put("Id", 123); jRecord.put("Name", "Портфель new");
				 * jData.put(jRecord);
				 * 
				 * jRecord = new JSONObject();
				 * 
				 * jRecord.put("Id", 234); jRecord.put("Name", "Портфель new2");
				 * jData.put(jRecord);
				 * 
				 * JSONObject jResponse = new JSONObject();
				 * jResponse.put("status", 0); jResponse.put("data", jData);
				 */

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
