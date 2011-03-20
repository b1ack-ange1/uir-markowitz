package servlet.metadata;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.softlab.rsdh.api.classes.TSRPDomainBase;
import ru.softlab.rsdh.api.rs.TRSDomainValueList;
import servlet.RsdhServlet;

@SuppressWarnings("serial")
public class DomainValues extends RsdhServlet {
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		JSONObject jRequestData = jRequest.getJSONObject("data");

		double domainId = jRequestData.getDouble("domainId");
		try {
			TSRPDomainBase domain = new TSRPDomainBase(new BigDecimal(domainId));

			JSONArray jData = new JSONArray();
			int totalRows = 0;
			for (TRSDomainValueList value : domain.getValueList()) {
				JSONObject jRecord = new JSONObject();
				jRecord.put("Id", value.getDVValue());
				jRecord.put("Name", value.getDVName());
				jData.put(jRecord);
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
