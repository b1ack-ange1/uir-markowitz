package servlet.graph;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.softlab.rsdh.api.ObjectsFactory;
import ru.softlab.rsdh.api.classes.TBCLQueueObject;
import ru.softlab.rsdh.api.classes.TSRPDomainBase;
import ru.softlab.rsdh.api.classes.TWIFExperiment;
import ru.softlab.rsdh.api.rs.TRSDomainValueList;
import ru.softlab.rsdh.api.rs.TRSQOLogItemRowTAList;
import ru.softlab.rsdh.api.rs.TRSTAObjectList;
import servlet.RsdhServlet;

@SuppressWarnings("serial")
public class TAObjectInfo extends RsdhServlet {

	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		JSONObject jRequestData = jRequest.getJSONObject("data");

		BigDecimal objectId = new BigDecimal(jRequestData.getDouble("objectId"));

		JSONObject jRecord = new JSONObject();
		try {
			TBCLQueueObject object = (TBCLQueueObject) ObjectsFactory
					.createObjById("TBCLQueueObject", objectId);

			jRecord.put("Code", object.getCode());
			jRecord.put("Name", object.getName());
			jRecord.put("Note", object.getNote());

			if (jRequestData.has("expId")) {
				TWIFExperiment experiment = new TWIFExperiment(new BigDecimal(
						jRequestData.getDouble("expId")));

				for (TRSTAObjectList tAObject : experiment.getTAObjectList()) {
					if (tAObject.getQueueObjectId().equals(objectId)) {
						TSRPDomainBase domain = (TSRPDomainBase) ObjectsFactory
								.getObjectByCode("TSRPDomainBase",
										"QUETAObjStatus");
						for (TRSDomainValueList value : domain.getValueList())
							if (new BigDecimal(value.getDVValue())
									.equals(tAObject.getTAObjectStatus())) {
								jRecord.put("Status", value.getDVName());
								break;
							}

						String log = "";
						for (TRSQOLogItemRowTAList row : experiment
								.getQOLogItemRowList(objectId, tAObject
										.getTAObjectQueueDate())) {
							log += row.getQOLogItemRowText() + "\n";
						}
						jRecord.put("Log", log);
						break;
					}
				}
			}
		} catch (SQLException e) {
			return proceedError(e.getLocalizedMessage());
		}
		JSONArray jData = new JSONArray();
		jData.put(jRecord);

		JSONObject jResponse = new JSONObject();
		jResponse.put("data", jData);
		jResponse.put("status", 0);
		return jResponse;
	}

}
