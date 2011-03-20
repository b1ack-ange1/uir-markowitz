package servlet.metadata.rs;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.softlab.rsdh.api.ObjectsFactory;
import ru.softlab.rsdh.api.classes.TSRPResultSet;
import ru.softlab.rsdh.api.rs.TRSRSColumnList;
import servlet.RsdhServlet;

@SuppressWarnings("serial")
public abstract class RSBased extends RsdhServlet {
	@SuppressWarnings("unchecked")
	public JSONObject getResultSetData(String rsCode, ArrayList rsList)
			throws JSONException {
		TSRPResultSet resultSet;
		try {
			resultSet = (TSRPResultSet) ObjectsFactory.getObjectByCode(
					"TSRPResultSet", rsCode);

			JSONArray jData = new JSONArray();
			int pk = 0;
			for (Object exp : rsList) {
				JSONObject jRecord = new JSONObject();
				jRecord.put("RSPK", pk++);
				for (TRSRSColumnList rsCol : resultSet.getRSColumnList()) {
					try {
						jRecord.put(rsCol.getRSColumnCode(), exp.getClass()
								.getMethod("get" + rsCol.getRSColumnCode().substring(0, 1).toUpperCase()+rsCol.getRSColumnCode().substring(1),
										new Class[0])
								.invoke(exp, new Object[0]));
					} catch (NoSuchMethodException e) {
						jRecord.put(rsCol.getRSColumnCode(), exp.getClass()
								.getMethod("is" + rsCol.getRSColumnCode().substring(0, 1).toUpperCase()+rsCol.getRSColumnCode().substring(1),
										new Class[0])
								.invoke(exp, new Object[0]));
					}
				}
				jData.put(jRecord);
			}

			JSONObject jResponse = new JSONObject();
			jResponse.put("data", jData);
			jResponse.put("status", 0);
			return jResponse;
		} catch (Exception e) {
			return proceedError(e.getLocalizedMessage());
		}
	}

}
