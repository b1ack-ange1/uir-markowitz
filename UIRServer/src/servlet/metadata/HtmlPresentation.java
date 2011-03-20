package servlet.metadata;

import java.io.BufferedReader;
import java.math.BigDecimal;

import oracle.sql.CLOB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.softlab.rsdh.api.ObjectsFactory;
import ru.softlab.rsdh.api.classes.TBCLUserObject;
import servlet.RsdhServlet;

public class HtmlPresentation extends RsdhServlet {

	@Override
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		JSONObject jRequestData = jRequest.getJSONObject("data");

		double objectId = jRequestData.getDouble("objectId");
		try {
			JSONObject jRecord = new JSONObject();

			TBCLUserObject userObject = (TBCLUserObject) ObjectsFactory
					.createObjById("TBCLUserObject", new BigDecimal(objectId));

			CLOB[] out = new CLOB[1];
			userObject.getHtmlPresentation(out, null);
			StringBuffer strOut = new StringBuffer();
			String aux;
			String temp = "";
			if (out[0] != null) {
				BufferedReader buffer = new BufferedReader(
						out[0].getCharacterStream());
				while ((aux = buffer.readLine()) != null)
					strOut.append(aux);
				temp += strOut.toString();

				jRecord.put("html", temp);
			}

			/*
			 * jRecord.put( "html", "<head>" +
			 * "<meta http-equiv=\"content-type\" content=\"text/html; charset=windows-1251\">"
			 * + "<style>" + ".body {    " + "background: blue;" +
			 * "color: white;" + "}" + ".txt {" + "background: transparent;" +
			 * "color: white;" + "font-weight: bold;" + "}" + ".paramname {" +
			 * "background: transparent;" + "color: black;" +
			 * "font-weight: bold;" + "}" + ".paramvalue {" +
			 * "background: transparent;" + "color: red;" + "}" + ".paramcode {"
			 * + "background: transparent;" + "background: blue;" +
			 * "color: green;" + "</style>" + "</head>" + "<body>" +
			 * "<span class=\"paramname\">Дата с</span>:&nbsp;<span class=\"paramvalue\">10.02.2010</span>,&nbsp;<span class=\"paramname\">Дата по</span>: <span class=\"paramvalue\">11.02.2010</span>"
			 * + "</body>" + "</html>");
			 */
			JSONObject jResponse = new JSONObject();
			JSONArray jData = new JSONArray();
			jData.put(jRecord);
			jResponse.put("data", jData);
			jResponse.put("status", 0);
			return jResponse;
		} catch (Exception e) {
			return proceedError(e.getLocalizedMessage());
		}
	}

}
