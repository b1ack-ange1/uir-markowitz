package servlet.metadata;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.softlab.rsdh.api.ObjectsFactory;
import ru.softlab.rsdh.api.classes.TBCLEditableQuery;
import ru.softlab.rsdh.api.classes.TBCLUserObject;
import ru.softlab.rsdh.api.rs.TRSInterfaceMethodList;
import servlet.RsdhServlet;

@SuppressWarnings("serial")
public class AccessedMethods extends RsdhServlet {

	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		JSONObject jRequestData = jRequest.getJSONObject("data");

		double objectId = jRequestData.getDouble("objectId");
		try {
			TBCLUserObject userObject = (TBCLUserObject) ObjectsFactory
					.createObjById("TBCLUserObject", new BigDecimal(objectId));

			boolean[] enableInsert = { false };
			boolean[] enableUpdate = { false };
			boolean[] enableDelete = { false };

			if (userObject instanceof TBCLEditableQuery)
				try {
					((TBCLEditableQuery) userObject).getEnableModify(
							enableInsert, enableUpdate, enableDelete);
				} catch (SQLException e) {
					if (!e.getMessage().contains("ESIBAccessDenied"))
						throw e;
				}

			ArrayList<TRSInterfaceMethodList> accessedMethodList = userObject
					.getAccessedMethodsList();

			JSONArray jData = new JSONArray();

			for (TRSInterfaceMethodList method : accessedMethodList) {

				if (userObject instanceof TBCLEditableQuery) {
					if (!((method.getMethodCode().equalsIgnoreCase("InsertRow") && !enableInsert[0])
							|| (method.getMethodCode().equalsIgnoreCase(
									"UpdateRow") && !enableUpdate[0]) || (method
							.getMethodCode().equalsIgnoreCase("DeleteRow") && !enableDelete[0]))) {
						JSONObject jRecord = new JSONObject();
						jRecord.put("Code", method.getMethodCode());
						jData.put(jRecord);
					}
				} else {
					JSONObject jRecord = new JSONObject();
					jRecord.put("Code", method.getMethodCode());
					jData.put(jRecord);
				}
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
