package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

			JSONObject jObject = new JSONObject();
			JSONObject jObjData = new JSONObject();

			jObjData.put("name", "Статистика");
			jObjData.put("id", 1);
			jObjData.put("parentId", 0);
			jObject.put("object", jObjData);
			jData.put(jObject);

			jObjData = new JSONObject();
			jObject = new JSONObject();
			jObjData.put("name", "Управление портфелем");
			jObjData.put("id", 2);
			jObjData.put("parentId", 0);
			jObject.put("object", jObjData);
			jData.put(jObject);

			jObject = new JSONObject();
			jObjData = new JSONObject();
			jObjData.put("name", "Расчет оптимального портфеля");
			jObjData.put("id", 3);
			jObjData.put("parentId", 0);
			jObject.put("object", jObject);
			jData.put(jObjData);

			jResponse.put("data", jData);

			jResponse.put("status", "0");
			jResponse.put("startRow", "0");
			jResponse.put("endRow", counter - 1);
			jResponse.put("totalRows", counter);

			resp.setCharacterEncoding("utf-8");
			resp.getWriter().write(jResponse.toString());
		} catch (JSONException e) {

			e.printStackTrace();
		}
	}

}
