package servlet.configuration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import servlet.LISEServlet;
import utils.Constants;

public class NavigationTreeServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("cp1251");
		try {
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
			jRecord.put("name", "Управление портфелем");
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

			jResponse.put("data", jData);

			jResponse.put("status", "0");
			jResponse.put("startRow", "0");
			jResponse.put("endRow", counter - 1);
			jResponse.put("totalRows", counter);

			JSONObject jOut = new JSONObject();
			jOut.put("response", jResponse);

			resp.setCharacterEncoding("utf-8");
			resp.getWriter().write(jOut.toString());
		} catch (JSONException e) {

			e.printStackTrace();
		}
	}
}
