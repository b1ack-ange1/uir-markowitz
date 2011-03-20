package servlet.metadata;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.softlab.rsdh.api.ObjectsFactory;
import ru.softlab.rsdh.api.classes.TWFLDocument;
import ru.softlab.rsdh.api.classes.TWFLProcessScheme;
import ru.softlab.rsdh.api.rs.TRSWFLActorRoleList;
import ru.softlab.rsdh.api.rs.TRSWFLDocumentUserList;
import servlet.RsdhServlet;

@SuppressWarnings("serial")
public class DocumentPerformers extends RsdhServlet {
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		JSONObject jRequestData = jRequest.getJSONObject("data");
		double objectId = jRequestData.getDouble("objectId");

		try {
			TWFLDocument document = (TWFLDocument) ObjectsFactory
					.createObjById("TWFLDocument", new BigDecimal(objectId));
			TWFLProcessScheme processScheme = (TWFLProcessScheme) ObjectsFactory
					.createObjById("TWFLProcessScheme",
							document.getProcessSchemeId());

			JSONArray jData = new JSONArray();
			// Передаем информацию о ролях
			for (TRSWFLActorRoleList role : processScheme.getActorRoleList()) {
				JSONObject jRow = new JSONObject();
				jRow.put("Id", role.getRoleId());
				jRow.put("RowId", role.getRoleId().toString());
				jRow.put("ParentId", JSONObject.NULL);
				jRow.put("Code", role.getRoleCode());
				jRow.put("Name", role.getName());
				jRow.put("icon", "classes/SRV.TWFLActorRole.gif");
				jRow.put("isFolder", false);
				jData.put(jRow);
			}
			// Передаем информацию о исполнителях
			for (TRSWFLDocumentUserList performer : document
					.getDocumentUserList(null, null)) {
				JSONObject jRow = new JSONObject();
				jRow.put("Id", performer.getSubjectId());
				jRow.put("RowId", performer.getRoleID().toString() + "_" + performer.getSubjectId());
				jRow.put("ParentId", performer.getRoleID());
				jRow.put("Code", performer.getSubjectCode());
				jRow.put("Name", performer.getSubjName());
				jRow.put("icon", "classes/SRV." + performer.getSubjClassCode() + ".gif");
				jRow.put("isFolder", false);
				jData.put(jRow);
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
