package servlet.graph;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.softlab.rsdh.api.ObjectsFactory;
import ru.softlab.rsdh.api.classes.TBCLAnaliticalMethod;
import ru.softlab.rsdh.api.classes.TMDMAggregate;
import ru.softlab.rsdh.api.classes.TQUETask;
import ru.softlab.rsdh.api.classes.TQUETaskActivation;
import ru.softlab.rsdh.api.rs.TRSQueueObjectList;
import ru.softlab.rsdh.api.rs.TRSTAObjectStateList;
import ru.softlab.rsdh.api.rs.TRSUserObjectParamValue;
import servlet.RsdhServlet;

@SuppressWarnings("serial")
public class GraphObjects extends RsdhServlet {

	@Override
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		JSONObject jRequestData = jRequest.getJSONObject("data");

		double objectId = jRequestData.getDouble("objectId");
		try {
			TQUETask task = null;
			TQUETaskActivation taskActivation = null;
			try {
				task = (TQUETask) ObjectsFactory.createObjById("TQUETask",
						new BigDecimal(objectId));
			} catch (SQLException inst) {
				taskActivation = (TQUETaskActivation) ObjectsFactory.createObjById(
						"TQUETaskActivation", new BigDecimal(objectId));
			}

			ArrayList<TRSQueueObjectList> objectList = null;
			ArrayList<TRSTAObjectStateList> statusList = null;

			if (task != null) {
				objectList = task.getTaskObjectList();
			} else if (taskActivation != null) {
				objectList = taskActivation.getQueueObjectList();
				statusList = taskActivation.getQueueObjectStatusList();
			}

			JSONArray jData = new JSONArray();
			if (objectList != null)
				for (TRSQueueObjectList object : objectList) {
					JSONObject jRecord = new JSONObject();
					jRecord.put("Id", object.getObjectId());
					jRecord.put("Code", object.getObjectCode());
					jRecord.put("Name", object.getObjectName());
					// Заплатка для rsdh00020144 >>>>>>>>>>>>>>>>>>>>>>
					String classCode = object.getObjClassCode();
					if (classCode.equalsIgnoreCase("TWIFModel"))
						classCode = (new TBCLAnaliticalMethod(
								new BigDecimal(-1))).getClassName();
					if (classCode.equalsIgnoreCase("TBIIAggregate"))
						classCode = (new TMDMAggregate(new BigDecimal(-1)))
								.getClassName();
					// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
					jRecord.put("ClassCode", classCode);
					if (taskActivation != null) {
						String params = "<ROW>";
						for (TRSUserObjectParamValue paramValue : taskActivation
								.getQueueObjectParamValueList(object
										.getObjectId())) {
							params += "<TPARAM><NAME>"
									+ paramValue.getUserObjectParamCode()
									+ "</NAME><VALUE>"
									+ (paramValue.getUserObjectParamValue() == null ? ""
											: paramValue
													.getUserObjectParamValue())
									+ "</VALUE></TPARAM>";
						}
						params += "</ROW>";
						jRecord.put("Params", params);
					}
					if (statusList != null)
						for (TRSTAObjectStateList status : statusList)
							if (object.getObjectId().equals(
									status.getQueueObjectId())) {
								jRecord.put("Status", status.getStatus());
								break;
							}
					jData.put(jRecord);
				}

			JSONObject jResponse = new JSONObject();
			jResponse.put("data", jData);
			if (taskActivation != null)
				jResponse.put("expStatus", taskActivation.getStatus());
			jResponse.put("status", 0);
			return jResponse;
		} catch (SQLException e) {
			return proceedError(e.getLocalizedMessage());
		}

	}
}
