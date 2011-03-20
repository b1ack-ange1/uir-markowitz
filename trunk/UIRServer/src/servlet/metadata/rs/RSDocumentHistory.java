package servlet.metadata.rs;

import java.sql.Timestamp;

import org.json.JSONException;
import org.json.JSONObject;

import ru.softlab.rsdh.api.ObjectsFactory;
import ru.softlab.rsdh.api.classes.TSRPSysEventManager;

@SuppressWarnings("serial")
public class RSDocumentHistory extends RSBased {

	@Override
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		JSONObject jRequestData = jRequest.getJSONObject("data");

		String objectId = jRequestData.getString("objectId");

		try {
			TSRPSysEventManager sysEventManager = (TSRPSysEventManager) ObjectsFactory
					.getObjectByCode("TSRPSysEventManager", "SysEventManager");

			Timestamp p_StartDate = null;
			if (!jRequestData.isNull("startDate"))
				p_StartDate = new Timestamp(jRequestData.getLong("startDate"));
			Timestamp p_EndDate = null;
			if (!jRequestData.isNull("endDate"))
				p_EndDate = new Timestamp(jRequestData.getLong("endDate"));
			String p_EventCodeFilter = "";
			String p_UserCodeFilter = "";
			String p_ObjectFilter = objectId;
			String p_ObjectClassFilter = "TWFLDocument";

			return getResultSetData("TRSEventLog",
					sysEventManager.getEvetlLogRecodrs(p_StartDate, p_EndDate,
							p_EventCodeFilter, p_UserCodeFilter,
							p_ObjectFilter, p_ObjectClassFilter));
		} catch (Exception e) {
			return proceedError(e.getLocalizedMessage());
		}
	}
}
