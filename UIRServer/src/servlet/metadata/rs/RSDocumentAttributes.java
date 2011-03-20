package servlet.metadata.rs;

import java.math.BigDecimal;

import org.json.JSONException;
import org.json.JSONObject;

import ru.softlab.rsdh.api.ObjectsFactory;
import ru.softlab.rsdh.api.classes.TWFLDocument;

@SuppressWarnings("serial")
public class RSDocumentAttributes extends RSBased {

	@Override
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		JSONObject jRequestData = jRequest.getJSONObject("data");

		String objectId = jRequestData.getString("objectId");
		try {
			TWFLDocument document = (TWFLDocument) ObjectsFactory.createObjById(
					"TWFLDocument", new BigDecimal(objectId));

			return getResultSetData("TRSWFLAttrValueList",
					document.getAttrValueList(true));
		} catch (Exception e) {
			return proceedError(e.getLocalizedMessage());
		}
	}

}
