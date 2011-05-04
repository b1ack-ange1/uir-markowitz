package servlet.configuration;

import java.io.IOException;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import portfolio.UserPortfolio;

import com.intersys.objects.CacheDatabase;
import com.intersys.objects.Database;

import db.ConnectionManager;

import servlet.LISEServlet;
import userinf.AuthData;
import utils.Constants;

public class NavigationTreeServlet extends LISEServlet {

	@Override
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		JSONObject jResponse = new JSONObject();

		int counter = 3;
		JSONArray jData = new JSONArray();

		JSONObject jRecord = new JSONObject();

		jRecord.put("name", "Статистика");
		jRecord.put("id", 1);
		jData.put(jRecord);

		counter++;
		jRecord = new JSONObject();
		jRecord.put("name", "Котировки акций");
		jRecord.put("id", counter);
		jRecord.put("parentId", 1);
		jRecord.put("code", "values");
		jData.put(jRecord);

		counter++;
		jRecord = new JSONObject();
		jRecord.put("name", "Доходности акций");
		jRecord.put("id", counter);
		jRecord.put("parentId", 1);
		jRecord.put("code", "profits");
		jData.put(jRecord);

		jRecord = new JSONObject();
		jRecord.put("name", "Портфели");
		jRecord.put("id", 2);
		jData.put(jRecord);

		jRecord = new JSONObject();
		jRecord.put("name", "Расчет оптимального портфеля");
		jRecord.put("id", 3);
		jData.put(jRecord);

		int root = 0;

		if (Constants.realizedMethodsClasses.contains("nonlinear")) {
			counter++;
			jRecord = new JSONObject();
			jRecord.put("name", "Нелинейное программирование");
			jRecord.put("id", counter);
			jRecord.put("parentId", 3);
			jData.put(jRecord);
			root = counter;

			if (Constants.realizedMethods.contains("Zojt")) {
				counter++;
				jRecord = new JSONObject();
				jRecord.put("name", "Метод Зойтендейка");
				jRecord.put("id", counter);
				jRecord.put("parentId", root);
				jRecord.put("code", "Zojt");
				jData.put(jRecord);

			}
			if (Constants.realizedMethods.contains("MonCar")) {
				counter++;
				jRecord = new JSONObject();
				jRecord.put("name", "Метод Монте-Карло");
				jRecord.put("id", counter);
				jRecord.put("parentId", root);
				jRecord.put("code", "MonCar");
				jData.put(jRecord);

			}
			if (Constants.realizedMethods.contains("Lagr")) {
				counter++;
				jRecord = new JSONObject();
				jRecord.put("name", "Метод множителей Лагранжа");
				jRecord.put("id", counter);
				jRecord.put("parentId", root);
				jRecord.put("code", "Lagr");
				jData.put(jRecord);

			}
		}

		if (Constants.realizedMethodsClasses.contains("convex")) {
			counter++;
			jRecord = new JSONObject();
			jRecord.put("name", "Выпуклое программирование");
			jRecord.put("id", counter);
			jRecord.put("parentId", 3);
			jData.put(jRecord);
			root = counter;

			if (Constants.realizedMethods.contains("ArrHurv")) {
				counter++;
				jRecord = new JSONObject();
				jRecord.put("name", "Метод Эрроу-Гурвица");
				jRecord.put("id", counter);
				jRecord.put("parentId", root);
				jRecord.put("code", "ArrHurv");
				jData.put(jRecord);

			}
			if (Constants.realizedMethods.contains("KunTak")) {
				counter++;
				jRecord = new JSONObject();
				jRecord.put("name", "Метод поиска седловой точки");
				jRecord.put("id", counter);
				jRecord.put("parentId", root);
				jRecord.put("code", "KunTak");
				jData.put(jRecord);

			}
		}

		if (Constants.realizedMethodsClasses.contains("square")) {
			counter++;
			jRecord = new JSONObject();
			jRecord.put("name", "Квадратичное программирование");
			jRecord.put("id", counter);
			jRecord.put("parentId", 3);
			jData.put(jRecord);
			root = counter;

			if (Constants.realizedMethods.contains("LevMark")) {
				counter++;
				jRecord = new JSONObject();
				jRecord.put("name", "Метод Левенберга-Марквардта");
				jRecord.put("id", counter);
				jRecord.put("parentId", root);
				jRecord.put("code", "LevMark");
				jData.put(jRecord);

			}
			if (Constants.realizedMethods.contains("WolfFr")) {
				counter++;
				jRecord = new JSONObject();
				jRecord.put("name", "Метод Вульфа-Фрэнка");
				jRecord.put("id", counter);
				jRecord.put("parentId", root);
				jRecord.put("code", "WolfFr");
				jData.put(jRecord);

			}
		}

		try {
			Connection connect = ConnectionManager.getConnection(request.get()
					.getSession().getAttribute("username").toString(), request
					.get().getSession().getAttribute("password").toString());

			System.out.println("check1");
			System.out.println("connection is null = " + (connect == null));
			userinf.AuthData auth = null;
			try {
				System.out.println("check2");

				Database db = CacheDatabase.getDatabase(connect);

				System.out.println("database is null = " + (db == null));
				System.out.println("check2.5");
				auth = (AuthData) userinf.AuthData
						.getObjectByLogin(db, request.get().getSession()
								.getAttribute("username").toString());

				System.out.println("check2.7");

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
				connect.close();
				proceedError(e.getMessage());
			}
			System.out.println("check3");
			System.out.println("auth is null = " + (auth == null));

			if (auth == null) {
				jResponse = new JSONObject();
				jResponse.put("status", 0);
				jResponse.put("data", "authorization is null");
				return jResponse;
			}
			/*
			 * List portList = auth.getPortfolios();
			 * 
			 * System.out.println("check4");
			 * System.out.println("portList is null = " + (portList == null));
			 * 
			 * for (int i = 0; i < portList.size(); i++) { UserPortfolio pn =
			 * (UserPortfolio) (portList.get(i)); jRecord = new JSONObject();
			 * counter++; jRecord.put("name", pn.getName()); jRecord.put("id",
			 * counter); jRecord.put("parentId", 2); jRecord.put("code",
			 * pn.getId()); System.out.println(jRecord); jData.put(jRecord);
			 * 
			 * }
			 */

			Map portList = (Map) auth.getPortfolios();

			System.out.println("check4");
			System.out.println("portList is null = " + (portList == null));

			Iterator iter = portList.keySet().iterator();
			while (iter.hasNext()) {
				UserPortfolio pn = (UserPortfolio) (portList.get(iter.next()));
				jRecord = new JSONObject();
				counter++;
				jRecord.put("name", pn.getName());
				jRecord.put("id", counter);
				jRecord.put("parentId", 2);
				jRecord.put("code", pn.getId());
				System.out.println(jRecord);
				jData.put(jRecord);
			}

			connect.close();
		} catch (Exception e) {
			e.printStackTrace();
			proceedError(e.getMessage());
		}

		jResponse.put("data", jData);

		jResponse.put("status", "0");

		/*
		 * jResponse.put("startRow", "0"); jResponse.put("endRow", counter - 1);
		 * jResponse.put("totalRows", counter);
		 */
		return jResponse;
	}
}
