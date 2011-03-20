package servlet.profiles;

import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.softlab.rsdh.api.ObjectsFactory;
import ru.softlab.rsdh.api.classes.TSRPSysRep;
import ru.softlab.rsdh.api.rs.TRSObjectList;
import servlet.RsdhServlet;

/**
 * Servlet implementation class ProfilesList
 */
public class ProfilesList extends RsdhServlet {

	@Override
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		try {
			TSRPSysRep sysRep = (TSRPSysRep) ObjectsFactory.getObjectByCode(
					"TSRPSysRep", "SysRep");
			ArrayList<TRSObjectList> profilesList = sysRep
					.getAccessedObjectList("TUSRProfile", true, null, null,
							null);

			JSONArray jData = new JSONArray();
			int totalRows = 0;
			for (TRSObjectList profile : profilesList) {
				JSONObject jRow = new JSONObject();
				jRow.put("Id", profile.getObjectId());
				jRow.put("Name", profile.getObjectName());
				jData.put(jRow);
				totalRows++;
			}
			JSONObject jResponse = new JSONObject();
			jResponse.put("data", jData);
			jResponse.put("startRow", 0);
			jResponse.put("endRow", totalRows - 1);
			jResponse.put("totalRows", totalRows);
			jResponse.put("status", 0);
			return jResponse;
		} catch (SQLException e) {
			return proceedError(e.getLocalizedMessage());
		}
	}

}
