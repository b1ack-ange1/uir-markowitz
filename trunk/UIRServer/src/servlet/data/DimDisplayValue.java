package servlet.data;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.softlab.rsdh.api.ObjectsFactory;
import ru.softlab.rsdh.api.classes.TBCLDSColumn;
import ru.softlab.rsdh.api.classes.TMDMDimData;
import ru.softlab.rsdh.api.classes.TMDMDimension;
import servlet.RsdhServlet;

@SuppressWarnings("serial")
public class DimDisplayValue extends RsdhServlet {

	@Override
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		JSONObject jRequestData = jRequest.getJSONObject("data");

		String objectId = jRequestData.getString("objectId");
		try {
			TMDMDimension dimension = (TMDMDimension) ObjectsFactory
					.createObjById("TMDMDimension", new BigDecimal(objectId));

			if (dimension instanceof TMDMDimData) {
				TMDMDimData dimData = (TMDMDimData) dimension;

				String parsedParams = "<ROW></ROW>";
				if (jRequestData.has("objectParams"))
					parsedParams = jRequestData.getString("objectParams");

				JSONArray jData = new JSONArray();

				if (jRequestData.has("Id")
						&& !jRequestData.getString("Id").isEmpty()) {
					JSONObject jRecord = new JSONObject();

					jRecord.put("Id", jRequestData.getString("Id"));
					String[] ids = jRequestData.getString("Id").split(";");

					String name = "";
					String del = "";
					for (String id : ids) {
						try {
							name += del
									+ dimData.getItemName(id, jRequestData
											.getString("valDimColumnCode"),
											null, parsedParams);
						} catch (SQLException e) {
							name += del + id + " (не определено)";
						}
						del = "; ";
					}
					jRecord.put("Name", name);

					jData.put(jRecord);
				} else {
					ResultSet[] resultSet = new ResultSet[1];
					try {
						if (!parsedParams.contains("p_NameFilter")) {
							if (jRequestData.has("Name")
									&& !jRequestData.getString("Name")
											.isEmpty())
								parsedParams = parsedParams.replace(
										"</ROW>",
										"<TPARAM><NAME>p_NameFilter</NAME><VALUE>"
												+ jRequestData
														.getString("Name")
												+ "</VALUE></TPARAM></ROW>");
						}
						dimData.getData(parsedParams, null, false, null,
								resultSet);
						BigDecimal nameColId = dimData.getNameColumnId();
						TBCLDSColumn nameColumn = new TBCLDSColumn(nameColId);
						while (resultSet[0].next()) {
							JSONObject jRecord = new JSONObject();
							jRecord.put("Id", resultSet[0]
									.getObject(jRequestData
											.getString("valDimColumnCode")));
							jRecord.put("Name", resultSet[0]
									.getObject(nameColumn.getCode()));
							jData.put(jRecord);
						}
					} catch (SQLException e) {
					} finally {
						if (resultSet[0] != null)
							resultSet[0].getStatement().close();
					}
				}

				JSONObject jResponse = new JSONObject();
				jResponse.put("data", jData);
				jResponse.put("status", 0);
				return jResponse;
			}
			return proceedError("Объект с данным Id не является измерением.");
		} catch (SQLException e) {
			return proceedError(e.getLocalizedMessage());
		}
	}
}
