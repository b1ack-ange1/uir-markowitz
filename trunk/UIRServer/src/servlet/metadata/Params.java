package servlet.metadata;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.softlab.rsdh.api.ObjectsFactory;
import ru.softlab.rsdh.api.classes.TBCLDataSet;
import ru.softlab.rsdh.api.classes.TBCLUserObject;
import ru.softlab.rsdh.api.classes.TMDMDimData;
import ru.softlab.rsdh.api.classes.TMDMDimension;
import ru.softlab.rsdh.api.classes.TSRPDomainBase;
import ru.softlab.rsdh.api.classes.TUSRShortcut;
import ru.softlab.rsdh.api.rs.TRSUserObjectParamList;
import ru.softlab.rsdh.api.rs.TRSUserObjectParamValue;
import ru.softlab.rsdh.api.rs.TRSUserTuneObjectParamValue;
import servlet.RsdhServlet;

@SuppressWarnings("serial")
public class Params extends RsdhServlet {
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		JSONObject jRequestData = jRequest.getJSONObject("data");

		TUSRShortcut shortcut = null;
		TBCLUserObject userObject = null;

		if (jRequestData.has("shortcutId")
				&& jRequestData.get("shortcutId") != null)
			shortcut = new TUSRShortcut(new BigDecimal(jRequestData
					.getDouble("shortcutId")));

		if (jRequestData.has("userObjectId")
				&& jRequestData.get("userObjectId") != null)
			userObject = new TBCLUserObject(new BigDecimal(jRequestData
					.getDouble("userObjectId")));

		JSONArray jData = new JSONArray();

		if (shortcut != null) {
			try {
				ArrayList<TRSUserTuneObjectParamValue> pvl = shortcut
						.getShortcutParamValueList();

				for (TRSUserTuneObjectParamValue param : pvl) {
					JSONObject jRecord = new JSONObject();
					jRecord.put("Id", param.getUserObjectParamId());
					jRecord.put("Code", param.getUserObjectParamCode());
					jRecord.put("Value", param.getUserObjectParamValue());
					jRecord.put("IsHidden", param.isHiddenValue());
					jData.put(jRecord);
				}
			} catch (SQLException e) {
				return proceedError(e.getLocalizedMessage());
			}
		} else if (userObject != null) {
			try {
				ArrayList<TRSUserObjectParamList> pvl = userObject
						.getParamList();

				for (TRSUserObjectParamList param : pvl) {
					JSONObject jRecord = new JSONObject();
					jRecord.put("Id", param.getUOParamId());
					jRecord.put("Code", param.getUOParamCode());
					jRecord.put("Name", param.getUOParamName());
					jRecord.put("DomainId", param.getUOParamDomainId());
					jRecord.put("DomainCode", param.getUOParamDomainCode());
					boolean isDomainValues = false;
					if (param.getUOParamDomainId() != null) {
						TSRPDomainBase domain = new TSRPDomainBase(param
								.getUOParamDomainId());
						if (domain.getValueList() != null
								&& domain.getValueList().size() > 0)
							isDomainValues = true;
					}
					jRecord.put("DomainValues", isDomainValues);
					jRecord.put("IsMandatory", param.isUOParamIsMandatory());
					jRecord.put("IsHidden", param.isUOParamHidden());
					jRecord
							.put("IsMultiSelect", param
									.isUOParamVDMultiSelect());
					jRecord.put("DefValue", param.getUOParamDefValue());
					jRecord.put("Value", param.getUOParamDefValue());
					if (param.getUOParamValDimId() != null) {
						jRecord.put("ValDimId", param.getUOParamValDimId());
						jRecord.put("VDColumnCode", param
								.getUOParamVDColumnCode());

						TMDMDimension dimData = (TMDMDimension) ObjectsFactory
								.createObjById("TMDMDimension", param
										.getUOParamValDimId());
						if (dimData instanceof TMDMDimData) {
							boolean shortDim = ((TMDMDimData) dimData)
									.isIsShortDimension();
							if (shortDim)
								for (TRSUserObjectParamList dimParam : dimData
										.getParamList()) {
									if (dimParam.isUOParamIsMandatory()
											&& (dimParam.getUOParamDefValue() == null || dimParam
													.getUOParamDefValue()
													.isEmpty()))
										shortDim = false;
								}

							if (param.isUOParamVDMultiSelect())
								shortDim = false;

							jRecord.put("ValDimShort", shortDim);
						}

					}
					jData.put(jRecord);
				}
			} catch (SQLException e) {
				return proceedError(e.getLocalizedMessage());
			}
		} else if (jRequestData.has("paramObjId")
				&& jRequestData.get("paramObjId") != null) {
			try {
				TBCLUserObject paramObject = (TBCLUserObject) ObjectsFactory
						.createObjById("TBCLUserObject", new BigDecimal(
								jRequestData.getDouble("paramObjId")));

				ArrayList<TRSUserObjectParamValue> paramList = null;
				String parsedParams = "<ROW>";
				if (jRequestData.has("columns")
						&& paramObject instanceof TBCLDataSet) {
					paramList = ((TBCLDataSet) paramObject)
							.getVDParamValueList(
									jRequestData.getString("code"),
									jRequestData.getString("objectParams"),
									jRequestData.getString("columns"));
				} else {
					paramList = paramObject.getParVDParamValueList(jRequestData
							.getString("code"), jRequestData
							.getString("objectParams"));
				}
				for (TRSUserObjectParamValue param : paramList) {
					JSONObject jRecord = new JSONObject();
					jRecord.put("Id", param.getUserObjectParamId());
					jRecord.put("Code", param.getUserObjectParamCode());
					jRecord.put("Name", param.getUserObjectParamName());
					jRecord.put("Value", param.getUserObjectParamValue());
					jData.put(jRecord);
				}
				parsedParams += "</ROW>";
			} catch (SQLException e) {
				return proceedError(e.getLocalizedMessage());
			}
		}

		JSONObject jResponse = new JSONObject();
		jResponse.put("data", jData);
		jResponse.put("status", 0);
		return jResponse;
	}
}
