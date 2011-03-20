package servlet.metadata;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.softlab.rsdh.api.ObjectsFactory;
import ru.softlab.rsdh.api.classes.TBCLDataSet;
import ru.softlab.rsdh.api.classes.TBCLUserObject;
import servlet.RsdhServlet;

@SuppressWarnings("serial")
public class UserObjectInfo extends RsdhServlet {
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		JSONObject jRequestData = jRequest.getJSONObject("data");

		double objectId = jRequestData.getDouble("objectId");
		try {
			TBCLUserObject userObject = (TBCLUserObject) ObjectsFactory
					.createObjById("TBCLUserObject", new BigDecimal(objectId));

			JSONObject jRecord = new JSONObject();

			userObject.getObjectInfo();
			if (userObject instanceof TBCLDataSet) {
				boolean[] filterRequired = new boolean[1];
				((TBCLDataSet) userObject).getFilterRequired(new String[1],
						new String[1], new String[1], new Timestamp[1],
						new String[1], new BigDecimal[1], new String[1],
						filterRequired);

				jRecord.put("filterRequired", filterRequired[0]);
			}

			JSONObject jResponse = new JSONObject();
			JSONArray jData = new JSONArray();
			jData.put(jRecord);
			jResponse.put("data", jData);
			jResponse.put("status", 0);
			return jResponse;
		} catch (SQLException e) {
			return proceedError(e.getLocalizedMessage());
		}
	}
}
