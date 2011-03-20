package servlet.metadata;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.softlab.rsdh.api.ObjectsFactory;
import ru.softlab.rsdh.api.classes.TMDMDimData;
import ru.softlab.rsdh.api.classes.TMDMDimension;
import ru.softlab.rsdh.api.classes.TSRPDomainBase;
import ru.softlab.rsdh.api.classes.TWFLDocument;
import ru.softlab.rsdh.api.classes.TWFLProcessScheme;
import ru.softlab.rsdh.api.rs.TRSDomainValueList;
import ru.softlab.rsdh.api.rs.TRSUserObjectParamList;
import servlet.RsdhServlet;

@SuppressWarnings("serial")
public class DocumentAttributes extends RsdhServlet {

	@Override
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		String operationType = jRequest.getString("operationType");
		if (operationType.equalsIgnoreCase("update")) {
			try {
				return update(jRequest);
			} catch (SQLException e) {

			}
		} else if (operationType.equalsIgnoreCase("fetch")) {
			return fetch(jRequest);
		}
		return jRequest;

	}

	private JSONObject fetch(JSONObject jRequest) {

		try {
			JSONObject jRequestData = jRequest.getJSONObject("data");
			TWFLDocument document = null;
			if (jRequestData.has("objectId")
					&& jRequestData.get("objectId") != null) {
				String objectId = jRequestData.getString("objectId");
				document = (TWFLDocument) ObjectsFactory.createObjById(
						"TWFLDocument", new BigDecimal(objectId));
			}

			JSONArray jData = new JSONArray();

			if (document != null) {

				TWFLProcessScheme processScheme = (TWFLProcessScheme) ObjectsFactory
						.createObjById("TWFLProcessScheme",
								document.getProcessSchemeId());
				ArrayList<TRSUserObjectParamList> paramList = processScheme
						.getParamList();

				for (TRSUserObjectParamList param : paramList) {

					if (!param.isUOParamSystem()) {
						JSONObject jRecord = new JSONObject();
						jRecord.put("Id", param.getUOParamId());
						jRecord.put("Code", param.getUOParamCode());
						jRecord.put("Name", param.getUOParamName());
						jRecord.put("DomainId", param.getUOParamDomainId());
						double domainId = Double.parseDouble(param
								.getUOParamDomainId().toString());
						String domainCode = param.getUOParamDomainCode();
						jRecord.put("DomainCode", param.getUOParamDomainCode());
						jRecord.put("Value",
								document.getAttrValue(param.getUOParamCode()));
						jRecord.put("multiselect", param.isUOParamHidden());

						if ((param.getUOParamValDimId() != null)
								&& (!param.getUOParamDomainCode()
										.equalsIgnoreCase("fileid"))) {

							jRecord.put("ValDimId", param.getUOParamValDimId());

							jRecord.put("VDColumnCode",
									param.getUOParamVDColumnCode());

							TMDMDimension dimData = (TMDMDimension) ObjectsFactory
									.createObjById("TMDMDimension",
											param.getUOParamValDimId());
							if (dimData instanceof TMDMDimData) {
								boolean shortDim = ((TMDMDimData) dimData)
										.isIsShortDimension();
								if (document.getAttrValue(param.getUOParamName())==null) {
									jRecord.put("RealValue","");
								} else {
								jRecord.put("RealValue",
										((TMDMDimData) dimData).getItemName(
												document.getAttrValue(param
														.getUOParamName()),
												param.getUOParamVDColumnCode(),
												null, ""));
								}
								if (shortDim)
									for (TRSUserObjectParamList dimParam : dimData
											.getParamList()) {
										if (dimParam.isUOParamIsMandatory()
												&& (dimParam
														.getUOParamDefValue() == null || dimParam
														.getUOParamDefValue()
														.isEmpty()))
											shortDim = false;
									}

								if (param.isUOParamVDMultiSelect())
									shortDim = false;

								jRecord.put("ValDimShort", shortDim);
							}
						} else {
							String value = document.getAttrValue(param
									.getUOParamCode());

							if (domainCode.equalsIgnoreCase("Bool")) {
								if (value.equalsIgnoreCase("1")) {
									jRecord.put("RealValue", "true");

								} else {
									jRecord.put("RealValue", "false");

								}
							} else if (domainCode.equalsIgnoreCase("ExactDate")) {
								try {
									jRecord.put(
											"RealValue",
											value.substring(6, 8) + "."
													+ value.substring(4, 6)
													+ "."
													+ value.substring(0, 4));
								} catch (Exception e) {
									jRecord.put("RealValue", "");
									jRecord.put("Value", "");
								}
							} else if (domainCode.equalsIgnoreCase("Moment")) {
								try {
									jRecord.put(
											"RealValue",
											value.substring(8, 10) + ":"
													+ value.substring(10, 12)
													+ ":"
													+ value.substring(12, 14)
													+ " "
													+ value.substring(6, 8)
													+ "."
													+ value.substring(4, 6)
													+ "."
													+ value.substring(0, 4));
								} catch (Exception e) {
									jRecord.put("RealValue", "");
									jRecord.put("Value", "");
								}
							} else {
								int i = 0;
								TSRPDomainBase domain = new TSRPDomainBase(
										new BigDecimal(domainId));
								for (TRSDomainValueList valueList : domain
										.getValueList()) {
									i = 1;
									if (valueList.getDVValue().equals(
											document.getAttrValue(param
													.getUOParamCode()))) {
										jRecord.put("RealValue",
												valueList.getDVName());
										i = 2;
										break;
									}
								}
								if (i != 0)
									jRecord.put("isDomainValue", true);
								else
									jRecord.put("isDomainValue", false);

								if (!jRecord.has("Value")) {
									jRecord.put("Value", "");
									jRecord.put("RealValue", "");
								} else {
									if (!jRecord.has("RealValue"))
										jRecord.put("RealValue", value);
								}
							}

							jRecord.put("ValDimId", "0");
						}
						if (!jRecord.has("isDomainValue"))
							jRecord.put("isDomainValue", false);
						jRecord.put("Icon", "detail.gif");

						jData.put(jRecord);
					}

				}
			}
			JSONObject jResponse = new JSONObject();
			jResponse.put("data", jData);
			jResponse.put("status", 0);
			return jResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}

	}

	@SuppressWarnings("unchecked")
	public static Vector<HashMap<Integer, Object>> getData(String dataSourceId) {
		return (Vector<HashMap<Integer, Object>>) getSession().getAttribute(
				dataSourceId);
	}

	public static Double getUserObjectId(String dataSourceId) {
		return (Double) getSession()
				.getAttribute(dataSourceId + "userObjectId");
	}

	private JSONObject update(JSONObject jRequest) throws JSONException,
			SQLException {
		JSONObject jResponse = new JSONObject();

		String objectId = jRequest.getJSONObject("data").getString("objectId");

		TWFLDocument document = (TWFLDocument) ObjectsFactory.createObjById(
				"TWFLDocument", new BigDecimal(objectId));
		TWFLProcessScheme processScheme = (TWFLProcessScheme) ObjectsFactory
				.createObjById("TWFLProcessScheme",
						document.getProcessSchemeId());
		ArrayList<TRSUserObjectParamList> paramList = processScheme
				.getParamList();
		int i = 0;
		for (TRSUserObjectParamList param : paramList) {
			i++;
			if (param.getUOParamCode().equals(
					jRequest.getJSONObject("oldValues").getString("Code"))) {
				document.modifyAttrValue(jRequest.getJSONObject("oldValues")
						.getString("Code"), jRequest.getJSONObject("data")
						.getString("Value"));

				break;

			}

		}

		jResponse = fetch(jRequest);

		return jResponse;

	}
}
