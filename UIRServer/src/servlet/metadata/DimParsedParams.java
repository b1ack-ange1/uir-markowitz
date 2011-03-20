package servlet.metadata;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.Servlet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.softlab.rsdh.api.ObjectsFactory;
import ru.softlab.rsdh.api.classes.TBCLDataSet;
import ru.softlab.rsdh.api.classes.TBCLUserObject;
import ru.softlab.rsdh.api.rs.TRSUserObjectParamValue;
import servlet.RsdhServlet;

/**
 * Servlet implementation class DimParsedParams
 */
@SuppressWarnings("serial")
public class DimParsedParams extends RsdhServlet implements Servlet {
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		JSONObject jRequestData = jRequest.getJSONObject("data");

		try {
			ArrayList<TRSUserObjectParamValue> paramList = new ArrayList<TRSUserObjectParamValue>();
			if (jRequestData.has("columnCode")) {
				TBCLDataSet columnObj = (TBCLDataSet) ObjectsFactory.createObjById(
						"TBCLDataSet", new BigDecimal(jRequestData
								.getDouble("objectId")));

				String columnValues = null;
				if (jRequestData.has("columnValues"))
					columnValues = jRequestData.getString("columnValues");
				paramList = columnObj.getVDParamValueList(jRequestData
						.getString("columnCode"), jRequestData
						.getString("paramValues"), columnValues);

			} else if (jRequestData.has("paramCode")) {
				TBCLUserObject paramObj = (TBCLUserObject) ObjectsFactory
						.createObjById("TBCLUserObject", new BigDecimal(
								jRequestData.getDouble("objectId")));

				paramList = paramObj.getParVDParamValueList(jRequestData
						.getString("paramCode"), jRequestData
						.getString("paramValues"));
			}

			JSONArray jData = new JSONArray();
			for (TRSUserObjectParamValue param : paramList) {
				JSONObject jRecord = new JSONObject();
				jRecord.put("Name", param.getUserObjectParamCode());
				if (param.getUserObjectParamValue() != null
						&& !param.getUserObjectParamValue().isEmpty())
					jRecord.put("Value", param.getUserObjectParamValue());
				jData.put(jRecord);
			}
			JSONObject jResponse = new JSONObject();
			jResponse.put("data", jData);
			jResponse.put("status", 0);
			return jResponse;
		} catch (SQLException e) {
			return proceedError(e.getLocalizedMessage());
		}
	}
}
