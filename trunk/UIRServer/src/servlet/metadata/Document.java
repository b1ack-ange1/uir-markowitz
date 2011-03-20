package servlet.metadata;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.softlab.rsdh.api.ObjectsFactory;
import ru.softlab.rsdh.api.classes.TWFLDocument;
import ru.softlab.rsdh.api.classes.TWFLProcessState;
import ru.softlab.rsdh.api.classes.TWFLTrigger;
import ru.softlab.rsdh.api.rs.TRSDocumentComandList;
import servlet.RsdhServlet;

@SuppressWarnings("serial")
public class Document extends RsdhServlet {
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		JSONObject jRequestData = jRequest.getJSONObject("data");
		double objectId = jRequestData.getDouble("objectId");
		String operationType = jRequestData.getString("operationType");

		try {
			TWFLDocument document = (TWFLDocument) ObjectsFactory
					.createObjById("TWFLDocument", new BigDecimal(objectId));

			JSONObject jData = new JSONObject();

			// Выполняем запрошенную операцию
			if (operationType.equalsIgnoreCase("activate")) {
				document.activate();
			} else if (operationType.equalsIgnoreCase("deactivate")) {
				document.deactivate();
			} else if (operationType.equalsIgnoreCase("close")) {
				document.close();
			} else if (operationType.equalsIgnoreCase("reactivate")) {
				document.reactivate();
			} else if (operationType.equalsIgnoreCase("executeCommand")) {
				if (jRequestData.has("commandCode")) {
					document.executeCommand(
							jRequestData.getString("commandCode"),
							jRequestData.getString("commandNote"));
				}
			} else if (!operationType.equalsIgnoreCase("getInfo")) {
				return proceedError("Не задан обязательный параметр: \"operationType\".");
			}

			// Получаем сведения о текущей активности документа
			jData.put("Activity", document.getActivity());

			// Получаем сведения по текущему состоянию
			try {
				TWFLProcessState processState = (TWFLProcessState) ObjectsFactory
						.createObjById("TWFLProcessState",
								document.getCurrentStateId());

				jData.put("CloseAction", processState.getCloseAction());
				jData.put("CurrentState", processState.getName());
			} catch (SQLException e) {
				jData.put("CloseAction", JSONObject.NULL);
				jData.put("CurrentState", "");
			}

			// Получаем сведения по предыдущему состоянию
			if (document.getLastUser() != null) {
				jData.put("LastUser", document.getLastUser());
			} else {
				jData.put("LastUser", "");
			}

			try {
				TWFLTrigger lastTrigger = (TWFLTrigger) ObjectsFactory
						.createObjById("TWFLTrigger",
								document.getLastCommandId());

				jData.put("LastCommand", lastTrigger.getName());
				jData.put("LastCommandNote", lastTrigger.getNote());
			} catch (SQLException e) {
				jData.put("LastCommand", "");
				jData.put("LastCommandNote", "");
			}

			Timestamp lastCommandMoment = document.getLastCommandMoment();
			if (lastCommandMoment != null) {
				jData.put("LastCommandMoment", SimpleDateFormat
						.getDateTimeInstance().format(lastCommandMoment));
			} else {
				jData.put("LastCommandMoment", "");
			}

			// Получаем набор доступных команд
			JSONArray jTriggers = new JSONArray();
			for (TRSDocumentComandList trigger : document
					.getAvailibleTriggersList()) {
				JSONObject jRow = new JSONObject();
				jRow.put("name", trigger.getName());
				jRow.put("code", trigger.getCode());
				jTriggers.put(jRow);
			}
			jData.put("Triggers", jTriggers);

			JSONObject jResponse = new JSONObject();
			jResponse.put("data", jData);
			jResponse.put("status", 0);
			return jResponse;
		} catch (SQLException e) {
			return proceedError(e.getLocalizedMessage());
		}
	}
}
