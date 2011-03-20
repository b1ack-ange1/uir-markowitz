package servlet.metadata.rs;

import java.sql.SQLException;

import javax.servlet.Servlet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.softlab.rsdh.api.ObjectsFactory;
import ru.softlab.rsdh.api.classes.TSRPDomainBase;
import ru.softlab.rsdh.api.classes.TSRPResultSet;
import ru.softlab.rsdh.api.rs.TRSRSColumnList;
import servlet.RsdhServlet;

/**
 * Servlet implementation class RSColumnList
 */
@SuppressWarnings("serial")
public class RSColumnList extends RsdhServlet implements Servlet {

	@Override
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		JSONObject jRequestData = jRequest.getJSONObject("data");

		String rsCode = jRequestData.getString("rsCode");
		try {
			TSRPResultSet resultSet = (TSRPResultSet) ObjectsFactory.getObjectByCode(
					"TSRPResultSet", rsCode);

			JSONArray jData = new JSONArray();
			for (TRSRSColumnList rsColumn : resultSet.getRSColumnList()) {
				JSONObject jRecord = new JSONObject();
				jRecord.put("Id", rsColumn.getRSColumnId());
				jRecord.put("Code", rsColumn.getRSColumnCode().replace("#",
						"-_-"));
				jRecord.put("Name", rsColumn.getRSColumnName());
				jRecord.put("Note", rsColumn.getRSColumnNote());

				boolean isDomainValues = false;
				String domainFormat = "";
				String dataType = "";
				if (rsColumn.getRSColumnDomainId() != null) {
					TSRPDomainBase domain = new TSRPDomainBase(rsColumn
							.getRSColumnDomainId());
					dataType = domain.getDataType();
					domainFormat = domain.getViewFormat();
					if (domain.getValueList() != null
							&& domain.getValueList().size() > 0)
						isDomainValues = true;
				}
				jRecord.put("DataType", dataType);

				jRecord.put("DomainId", rsColumn.getRSColumnDomainId());
				jRecord.put("DomainCode", rsColumn.getRSColumnDomainCode());

				jRecord.put("DomainValues", isDomainValues);

				jRecord.put("ViewCharCount", rsColumn
						.getRSColumnViewCharCount());
				jRecord.put("ViewAlignment", rsColumn
						.getRSColumnViewAlignment());
				jRecord.put("ViewFormat",
						(rsColumn.getRSColumnViewFormat() != null ? rsColumn
								.getRSColumnViewFormat() : domainFormat));
				jRecord.put("ViewVisible", rsColumn.isRSColumnViewVisible());

				jData.put(jRecord);
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
