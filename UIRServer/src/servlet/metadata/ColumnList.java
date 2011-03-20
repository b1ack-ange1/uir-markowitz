package servlet.metadata;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.softlab.rsdh.api.ObjectsFactory;
import ru.softlab.rsdh.api.RsdhConstants;
import ru.softlab.rsdh.api.classes.TBCLDSColumn;
import ru.softlab.rsdh.api.classes.TBCLDataSet;
import ru.softlab.rsdh.api.classes.TMDMDimData;
import ru.softlab.rsdh.api.classes.TMDMDimension;
import ru.softlab.rsdh.api.classes.TSRPDomainBase;
import ru.softlab.rsdh.api.rs.TRSDSColumnList;
import ru.softlab.rsdh.api.rs.TRSDSColumnListShort;
import ru.softlab.rsdh.api.rs.TRSUserObjectParamList;
import servlet.RsdhServlet;

@SuppressWarnings("serial")
public class ColumnList extends RsdhServlet {
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		JSONObject jRequestData = jRequest.getJSONObject("data");

		double objectId = jRequestData.getDouble("objectId");
		try {
			TBCLDataSet dataSet = (TBCLDataSet) ObjectsFactory.createObjById(
					"TBCLDataSet", new BigDecimal(objectId));
			ArrayList<TRSDSColumnListShort> ediatbleColumns;
			ArrayList<TRSDSColumnListShort> insertableColumns;
			try {
				ediatbleColumns = dataSet
						.getColumnInSetList(BigDecimal
								.valueOf(Long
										.valueOf(RsdhConstants.cnRSDHColumnSetEditable)));
				insertableColumns = dataSet
						.getColumnInSetList(BigDecimal
								.valueOf(Long
										.valueOf(RsdhConstants.cnRSDHColumnSetInsertable)));
			} catch (SQLException e) {
				if (e.getMessage().contains("ESIBAccessDenied")) {
					ediatbleColumns = new ArrayList<TRSDSColumnListShort>();
					insertableColumns = new ArrayList<TRSDSColumnListShort>();
				} else
					throw e;
			}

			JSONArray jData = new JSONArray();

			for (TRSDSColumnList column : dataSet.getColumnList()) {
				JSONObject jRecord = new JSONObject();
				jRecord.put("Id", column.getDSColumnId());
				jRecord.put("Code", column.getDSColumnCode()
						.replace("#", "-_-"));
				jRecord.put("Name", column.getDSColumnName());
				jRecord.put("Note", column.getDSColumnNote());

				jRecord.put("DefValue", column.getDSColumnDefValue());

				jRecord.put("DataType", column.getDSColumnDataType());
				jRecord.put("DataLength", column.getDSColumnDataLength());
				jRecord.put("DataPrecision", column.getDSColumnDataPrecision());

				jRecord.put("DomainId", column.getDSColumnDomainId());
				jRecord.put("DomainCode", column.getDSColumnDomainCode());

				boolean isDomainValues = false;
				String domainFormat = "";
				if (column.getDSColumnDomainId() != null) {
					TSRPDomainBase domain = new TSRPDomainBase(column
							.getDSColumnDomainId());
					domainFormat = domain.getViewFormat();
					if (domain.getValueList() != null
							&& domain.getValueList().size() > 0)
						isDomainValues = true;
				}
				jRecord.put("DomainValues", isDomainValues);

				jRecord.put("ViewCharCount", column.getDSColumnViewCharCount());
				jRecord.put("ViewAlignment", column.getDSColumnViewAlignment());
				jRecord.put("ViewFormat",
						(column.getDSColumnViewFormat() != null ? column
								.getDSColumnViewFormat() : domainFormat));
				jRecord.put("ViewVisible", column.isDSColumnViewVisible());

				jRecord.put("IsMandatory", column.isDSColumnIsMandatory());

				if (column.getDSColumnValDimDataId() != null) {
					jRecord.put("ValDimDataId", column
							.getDSColumnValDimDataId());
					jRecord.put("ValDimDataCode", new TBCLDSColumn(column
							.getDSColumnVDColumnId()).getCode());

					TMDMDimension dimData = (TMDMDimension) ObjectsFactory
							.createObjById("TMDMDimension", column
									.getDSColumnValDimDataId());
					if (dimData instanceof TMDMDimData) {
						boolean shortDim = ((TMDMDimData) dimData)
								.isIsShortDimension();
						if (shortDim)
							for (TRSUserObjectParamList dimParam : dimData
									.getParamList()) {
								if (dimParam.isUOParamIsMandatory()
										&& (dimParam.getUOParamDefValue() == null || dimParam
												.getUOParamDefValue().isEmpty()))
									shortDim = false;
							}

						jRecord.put("ValDimShort", shortDim);
					}
				}

				if (column.getDSColumnSourceColumnId() != null) {
					TBCLDSColumn sourceColumn = new TBCLDSColumn(column
							.getDSColumnSourceColumnId());
					jRecord.put("SourceColumnCode", sourceColumn.getCode()
							.replace("#", "-_-"));
				}

				for (TRSDSColumnListShort col : insertableColumns)
					if (col.getDSColumnId().equals(column.getDSColumnId())) {
						jRecord.put("IsInsertable", true);
						break;
					}

				for (TRSDSColumnListShort col : ediatbleColumns)
					if (col.getDSColumnId().equals(column.getDSColumnId())) {
						jRecord.put("IsEditable", true);
						break;
					}

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
