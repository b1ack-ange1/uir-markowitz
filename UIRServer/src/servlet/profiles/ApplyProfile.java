package servlet.profiles;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.softlab.rsdh.api.ObjectsFactory;
import ru.softlab.rsdh.api.classes.TUSRProfile;
import ru.softlab.rsdh.api.rs.TRSShortcutList;
import servlet.RsdhServlet;
import filter.ProfileFilter;

/**
 * Servlet implementation class ApplyProfile
 */
public class ApplyProfile extends RsdhServlet {
	@Override
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		BigDecimal id = new BigDecimal(jRequest.getDouble("objectId"));
		String paramValues = jRequest.getString("paramValues");
		JSONArray jData = new JSONArray();
		try {
			TUSRProfile profile = (TUSRProfile) ObjectsFactory.createObjById(
					"TUSRProfile", id);
			profile.applyProfile(paramValues);

			getSession().setAttribute(ProfileFilter.sessionProfileId, id);
			getSession().setAttribute(ProfileFilter.sessionProfileParams,
					paramValues);

			/*for (TRSShortcutList shortCut : profile.getAutoOpenObjectList()) {
				jData.put(shortCut.getShortcutId());
			}*/
		} catch (SQLException e) {
			e.printStackTrace(System.out);
			return proceedError(e.getLocalizedMessage());
		}
		return new JSONObject().put("data", jData).put("status", 0);
	}

}
