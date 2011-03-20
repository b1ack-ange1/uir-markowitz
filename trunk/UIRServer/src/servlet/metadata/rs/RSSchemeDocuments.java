package servlet.metadata.rs;

import java.math.BigDecimal;

import org.json.JSONException;
import org.json.JSONObject;

import ru.softlab.rsdh.api.ObjectsFactory;
import ru.softlab.rsdh.api.classes.TSRPSysWorkFlow;

@SuppressWarnings("serial")
public class RSSchemeDocuments extends RSBased {

	@Override
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		JSONObject jRequestData = jRequest.getJSONObject("data");

		String objectId = jRequestData.getString("objectId");
		try {
			TSRPSysWorkFlow sysWorkFlow = (TSRPSysWorkFlow) ObjectsFactory
					.getObjectByCode("TSRPSysWorkFlow", "SysWorkFlow");

			String p_NameMask = "";
			BigDecimal p_SchemeId = new BigDecimal(objectId);
			BigDecimal p_Activity = null;
			String p_StateNameMask = "";
			String p_CommandNoteMask = "";
			String p_LastUser = "";
			BigDecimal p_AMId = null;

			return getResultSetData("TRSWFLExtendDocumentList",
					sysWorkFlow.getAllUserWorkDocList(p_NameMask, p_SchemeId,
							p_Activity, p_StateNameMask, p_CommandNoteMask,
							p_LastUser, p_AMId));
		} catch (Exception e) {
			return proceedError(e.getLocalizedMessage());
		}
	}

}
