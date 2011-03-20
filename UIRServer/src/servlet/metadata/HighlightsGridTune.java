package servlet.metadata;

import java.io.OutputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Blob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import oracle.sql.BLOB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXParseException;

import ru.softlab.rsdh.api.ObjectsFactory;
import ru.softlab.rsdh.api.RsdhConnection;
import ru.softlab.rsdh.api.classes.TSRPSysUserTunes;
import ru.softlab.rsdh.api.classes.TUSRGridTune;
import ru.softlab.rsdh.api.rs.TRSUSRObjTuneList;
import servlet.RsdhServlet;
import utils.exceptions.ProblemWithAPI;

/**
 * Servlet implementation class UserObjectViewTune
 */
@SuppressWarnings("serial")
public class HighlightsGridTune extends RsdhServlet {
	protected static int frequency(String str, String ch) {
		if (str.indexOf(ch) >= 0) {
			return 1 + frequency(str.substring(str.indexOf(ch) + 1), ch);
		} else {
			return 0;
		}
	}

	protected static String isAdvanced(String str) {
		if (frequency(str, "AND") > 0)
			return "AND";
		else if (frequency(str, "OR") > 0)
			return "OR";
		else if (frequency(str, "NOT") > 0)
			return "NOT";
		else
			return "";
	}

	protected static JSONObject parseStringJSON(String str,
			boolean isReallySimple) throws JSONException {
		if (frequency(str, "$") > 0) {
			if (frequency(str, "$") == 1) {
				JSONObject out = new JSONObject();
				str = str.substring(1, str.length() - 1);

				if (isAdvanced(str).isEmpty()) {
					// случай когда выражение простое
					String[] params = str.split(" ");
					if (params.length < 3)
						return new JSONObject(params[0]);
					out.put("fieldName", params[0]);
					if (params[2].equalsIgnoreCase("null")) {
						if (params[1].equals("="))

							out.put("operator", "isNull");
						else if (params[1].equals("<>"))
							out.put("operator", "notNull");

					} else {
						// AdvancedCriteria
						if (params[1].equals("="))
							out.put("operator", "equals");
						else if (params[1].equals("<>"))
							out.put("operator", "notEqual");
						else if (params[1].equals(">"))
							out.put("operator", "greaterThan");
						else if (params[1].equals("<"))
							out.put("operator", "lessThan");
						else if (params[1].equals(">="))
							out.put("operator", "greaterOrEqual");
						else if (params[1].equals("<="))
							out.put("operator", "lessOrEqual");
						else if (params[1].equalsIgnoreCase("LIKE"))
							out.put("operator", "regexp");

						out.put("value", params[2]);
						for (int i = 3; i < params.length; i++) {

							out.put("value", out.get("value") + " " + params[i]);
						}
						if (out.getString("value").contains("'")) {
							out.put("value",
									out.getString("value").replaceAll("'", ""));

							SimpleDateFormat myDateFormat = new SimpleDateFormat(
									"dd.MM.yyyy HH:mm:ss");
							try {
								Date temp = myDateFormat.parse(out.getString(
										"value").replaceAll("'", ""));
								out.put("value", temp);

							} catch (ParseException e) {
								myDateFormat = new SimpleDateFormat(
										"dd.MM.yyyy HH:mm");
								try {
									Date temp = myDateFormat.parse(out
											.getString("value").replaceAll("'",
													""));
									out.put("value", temp);

								} catch (ParseException e2) {
									myDateFormat = new SimpleDateFormat(
											"dd.MM.yyyy");
									try {
										Date temp = myDateFormat.parse(out
												.getString("value").replaceAll(
														"'", ""));
										out.put("value", temp);

									} catch (ParseException e3) {

										e.printStackTrace();
									}
								}
							}
							out.put("quote", true);
						} else {
							out.put("quote", false);
						}
					}
					if (isReallySimple) {
						JSONObject temp = new JSONObject();
						temp.put("operator", "and");
						temp.put("constructor", "AdvancedCriteria");
						JSONArray tempArray = new JSONArray();
						tempArray.put(out);
						temp.put("criteria", tempArray);
						out = new JSONObject();
						out.put("criteria", temp);
					}
					return out;
				} else {
					if (isAdvanced(str).equalsIgnoreCase("NOT")) {
						JSONObject tempNotCriteria = new JSONObject(
								str.split("NOT")[1]);

						if (tempNotCriteria.getString("operator")
								.equalsIgnoreCase("or")) {
							tempNotCriteria.put("operator", "not");

							return tempNotCriteria;
						} else if (tempNotCriteria.getString("operator")
								.equalsIgnoreCase("and")) {
							tempNotCriteria.put("operator", "or");
							tempNotCriteria.put("criteria",
									downNOT(tempNotCriteria
											.getJSONArray("criteria")));

							return tempNotCriteria;
						}

						return null;
					} else {
						String[] atoms = str.split(isAdvanced(str));
						JSONArray criteria = new JSONArray();

						out.put("operator", isAdvanced(str).toLowerCase());

						for (String atom : atoms) {

							criteria.put(new JSONObject(atom));
						}
						out.put("criteria", criteria);

						out.put("constructor", "AdvancedCriteria");

						return out;
					}
				}

			} else {

				if (isReallySimple) {
					JSONObject temp = new JSONObject();
					temp.put("criteria",
							parseStringJSON("$" + str + "@", false));
					return temp;
				}

				String simpleExpr = str.substring(0, str.indexOf('@') + 1);
				simpleExpr = simpleExpr.substring(simpleExpr.lastIndexOf('$'));
				String temp = str.substring(0, str.indexOf(simpleExpr))
						+ parseStringJSON(simpleExpr, false).toString()
						+ str.substring(str.indexOf('@') + 1);
				return parseStringJSON(temp, false);

			}

		} else {
			return null;
		}

	}

	private static JSONArray downNOT(JSONArray criterias) throws JSONException {
		for (int i = 0; i < criterias.length(); i++) {
			JSONObject temp = criterias.getJSONObject(i);
			if (temp.has("criteria")) {

				if (temp.getString("operator").equalsIgnoreCase("or")) {
					temp.put("operator", "not");

				} else if (temp.getString("operator").equalsIgnoreCase("and")) {
					temp.put("operator", "or");
					temp.put("criteria", downNOT(temp.getJSONArray("criteria")));

				}
			} else {
				if (temp.getString("operator").equals("equals"))
					temp.put("operator", "notEqual");
				else if (temp.getString("operator").equals("notEqual"))
					temp.put("operator", "equals");
				else if (temp.getString("operator").equals("greaterThan"))
					temp.put("operator", "lessOrEqual");
				else if (temp.getString("operator").equals("lessThan"))
					temp.put("operator", "greaterOrEqual");
				else if (temp.getString("operator").equals("greaterOrEqual"))
					temp.put("operator", "lessThan");
				else if (temp.getString("operator").equals("lessOrEqual"))
					temp.put("operator", "greaterThan");

			}
		}
		return criterias;
	}

	private String toHTMLColor(String hex) {
		// hex в формате BGR, нам же нужен RGB. При этом
		// hex
		// не
		// имеет ведущих нулей.
		while (hex.length() < 6)
			hex = "0" + hex;
		return hex.substring(4) + hex.substring(2, 4) + hex.substring(0, 2);
	}

	private static String jsonToOracleString(JSONObject json)
			throws JSONException {
		if (!json.has("criteria")) {
			String str = "";

			str += "(" + json.getString("fieldName") + " ";
			if (json.getString("operator").equalsIgnoreCase("equals")) {
				str += "=";
				str += " " + json.getString("value") + ")";
			} else if (json.getString("operator").equalsIgnoreCase("notEqual")) {
				str += "<>";
				str += " " + json.getString("value") + ")";
			} else if (json.getString("operator").equalsIgnoreCase("lessThan")) {
				str += "<";
				str += " " + json.getString("value") + ")";

			} else if (json.getString("operator").equalsIgnoreCase(
					"greaterThan")) {
				str += ">";
				str += " " + json.getString("value") + ")";
			} else if (json.getString("operator").equalsIgnoreCase(
					"lessOrEqual")) {
				str += "<=";
				str += " " + json.getString("value") + ")";
			} else if (json.getString("operator").equalsIgnoreCase(
					"greaterOrEqual")) {
				str += ">=";
				str += " " + json.getString("value") + ")";
			} else if (json.getString("operator").equalsIgnoreCase("regexp")) {
				str += "LIKE";
				str += " " + json.getString("value") + ")";
			} else if (json.getString("operator").equalsIgnoreCase("isNull")) {
				str += "=";
				str += " NULL)";
			} else if (json.getString("operator").equalsIgnoreCase("notNull")) {
				str += "<>";
				str += " NULL)";
			}
			return str;
		} else {

			if (json.getString("operator").equalsIgnoreCase("not")) {
				json.put("operator", "or");
				return "(" + "NOT" + " (" + jsonToOracleString(json) + "))";
			} else {
				JSONArray subCriterias = json.getJSONArray("criteria");
				String out = "";
				for (int i = 0; i < subCriterias.length(); i++) {
					if (i != 0)
						out += " " + json.getString("operator").toUpperCase()
								+ " ";
					out += jsonToOracleString(subCriterias.getJSONObject(i));
				}
				return "(" + out + ")";
			}
		}

	}

	@Override
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		String operationType = jRequest.getString("operationType");
		if (operationType.equalsIgnoreCase("update")) {
			return update(jRequest);
		} else if (operationType.equalsIgnoreCase("fetch")) {
			return fetch(jRequest);
		}
		return jRequest;
	}

	private JSONObject update(JSONObject jRequest) throws JSONException {
		JSONObject jResponse = new JSONObject();
		try {
			JSONObject jRequestData = jRequest.getJSONObject("data");

			double objectId = jRequestData.getDouble("objectId");

			TSRPSysUserTunes sysUserTunes = null;

			sysUserTunes = (TSRPSysUserTunes) ObjectsFactory.getObjectByCode(
					"TSRPSysUserTunes", "SysUserTunes");

			ArrayList<TRSUSRObjTuneList> gridTuneList = sysUserTunes
					.getGeneralRangeTuneList(BigDecimal.valueOf(objectId),
							new TUSRGridTune(null).getClassName());

			if (gridTuneList.size() > 0) {
				TUSRGridTune gridTune = (TUSRGridTune) gridTuneList.get(0)
						.getItem();// Берем самую приоритетную настройку.

				BLOB[] data = new BLOB[1];
				gridTune.getUSRGridTuneData(data);
				JSONArray newTunes;
				if (jRequestData.getString("newValues").equals("")) {
					// пустая раскраска
					newTunes = new JSONArray();
				} else {
					newTunes = new JSONArray(jRequestData
							.getString("newValues").substring(
									1,
									jRequestData.getString("newValues")
											.length() - 1));
				}

				Document xmlDocument = DocumentBuilderFactory.newInstance()
						.newDocumentBuilder().parse(data[0].getBinaryStream());
				xmlDocument.setXmlStandalone(true);
				xmlDocument.setXmlVersion("1.0");
				xmlDocument.setTextContent("UTF-8");

				Element highlightRoot = (Element) xmlDocument
						.getElementsByTagName("Highlights").item(0);
				highlightRoot.setAttribute("Count", "" + newTunes.length());
				NodeList highlightRootChildren = highlightRoot.getChildNodes();
				int len = highlightRootChildren.getLength();

				for (int i = 0; i < len; i++)
					highlightRoot.removeChild(highlightRootChildren.item(0));

				for (int jj = 0; jj < newTunes.length(); jj++) {

					JSONObject hilite = newTunes.getJSONObject(jj);
					Element highlightUnit = xmlDocument
							.createElement("Highlights" + (jj + 1));

					String temp = "";

					JSONArray helpArray = hilite.getJSONArray("fieldName");
					for (int k = 0; k < helpArray.length(); k++) {
						temp += helpArray.get(k).toString() + ";";
					}
					temp.substring(0, temp.length() - 3);
					highlightUnit.setAttribute("FieldList", temp);
					String temp2 = jsonToOracleString(hilite
							.getJSONObject("criteria"));
					temp2 = temp2.substring(1, temp2.length() - 1);
					highlightUnit.setAttribute("Expression",
							hilite.getString("ExpressionNative"));
					highlightUnit.setAttribute("Caption",
							hilite.getString("Caption"));
					highlightUnit.setAttribute("Enabled",
							hilite.getString("Enabled"));
					highlightUnit.setAttribute("BackColorCustom",
							hilite.getString("BackColorCustom"));
					String color = hilite.getString("BackColor");
					highlightUnit.setAttribute("BackColor", ""
							+ htmlToOracle(color));
					highlightUnit.setAttribute("ForeColorCustom",
							hilite.getString("ForeColorCustom"));
					color = hilite.getString("ForeColor");
					highlightUnit.setAttribute("ForeColor", ""
							+ htmlToOracle(color));
					highlightUnit.setAttribute("FontBold",
							hilite.getString("FontBold"));
					highlightUnit.setAttribute("FontItalic",
							hilite.getString("FontItalic"));
					highlightUnit.setAttribute("FontUnderline",
							hilite.getString("FontUnderline"));
					highlightUnit.setAttribute("ShowText",
							hilite.getString("ShowText"));
					highlightUnit.setAttribute("ShowIcon",
							hilite.getString("ShowIcon"));
					highlightUnit.setAttribute("IconLayout",
							hilite.getString("IconLayout"));
					highlightUnit
							.setAttribute("Icon", hilite.getString("Icon"));
					highlightRoot.appendChild(highlightUnit);
				}

				Transformer trans = TransformerFactory.newInstance()
						.newTransformer();
				StringWriter sw = new StringWriter();
				trans.transform(new DOMSource(xmlDocument),
						new StreamResult(sw));

				String out = sw.toString();
				sw.close();

				Blob outBlob = RsdhConnection.getConnection().createBlob();
				OutputStream oStream = outBlob.setBinaryStream(1);
				oStream.write(out.getBytes("utf-8"));
				oStream.close();

				gridTune.ModifyUSRGridTuneData(outBlob);

				jResponse.put("status", 0);
			}

		} catch (Exception e) {
			jResponse.put("status", -1);
			jResponse.put("ERROR!!!!!ALARM!!!!!", e.getMessage());
		}

		return jResponse;
	}

	private int htmlToOracle(String color) {
		int a = 0;
		try {
			a = Integer.parseInt(color.substring(5, 7) + color.substring(3, 5)
					+ color.substring(1, 3), 16);
			return a;
		} catch (Exception e) {
			return -1;
		}

	}

	private JSONObject fetch(JSONObject jRequest) throws JSONException {
		JSONObject jRequestData = jRequest.getJSONObject("data");
		double objectId = jRequestData.getDouble("objectId");

		try {
			TSRPSysUserTunes sysUserTunes = null;
			sysUserTunes = (TSRPSysUserTunes) ObjectsFactory.getObjectByCode(
					"TSRPSysUserTunes", "SysUserTunes");

			ArrayList<TRSUSRObjTuneList> gridTuneList = sysUserTunes
					.getGeneralRangeTuneList(BigDecimal.valueOf(objectId),
							new TUSRGridTune(null).getClassName());

			JSONArray jData = new JSONArray();

			if (gridTuneList.size() > 0) {
				TUSRGridTune gridTune = (TUSRGridTune) gridTuneList.get(0)
						.getItem();// Берем самую приоритетную настройку.

				BLOB[] data = new BLOB[1];
				gridTune.getUSRGridTuneData(data);

				if (data[0] == null) {
					LOGGER.log(Level.INFO, this.getClass().getName(),
							new ProblemWithAPI());
				} else {
					try {
						Document xmlDocument = DocumentBuilderFactory
								.newInstance().newDocumentBuilder()
								.parse(data[0].getBinaryStream());

						NodeList hlsList = xmlDocument
								.getElementsByTagName("Highlights");
						for (int j = 0; j < hlsList.getLength(); j++) {
							NodeList hlList = hlsList.item(j).getChildNodes();
							for (int jj = 0; jj < hlList.getLength(); jj++) {
								NamedNodeMap hlAttrs = hlList.item(jj)
										.getAttributes();
								JSONObject jRecord = new JSONObject();
								String style = "";
								String styleClear = "";
								for (int jjj = 0; jjj < hlAttrs.getLength(); jjj++) {

									if (hlAttrs.item(jjj).getNodeName()
											.equalsIgnoreCase("Expression")) {
										if (hlAttrs.item(jjj).getNodeValue()
												.equalsIgnoreCase("")) {
											jRecord.put(hlAttrs.item(jjj)
													.getNodeName(), "");
										} else {
											String temp = hlAttrs.item(jjj)
													.getNodeValue()
													.replace('(', '$');
											temp = temp.replace(')', '@');
											JSONObject expr = parseStringJSON(
													temp, true);
											// String
											// expr=hlAttrs.item(jjj).getNodeValue();
											jRecord.put("ExpressionNative",
													hlAttrs.item(jjj)
															.getNodeValue());
											jRecord.put(hlAttrs.item(jjj)
													.getNodeName(), expr
													.toString());
										}

									} else if (hlAttrs.item(jjj).getNodeName()
											.equalsIgnoreCase("FieldList")) {
										jRecord.put(
												"fieldName",
												new JSONArray(hlAttrs.item(jjj)
														.getNodeValue()
														.split(";")));
										jRecord.put(hlAttrs.item(jjj)
												.getNodeName(),
												hlAttrs.item(jjj)
														.getNodeValue());
									} else {
										jRecord.put(hlAttrs.item(jjj)
												.getNodeName(),
												hlAttrs.item(jjj)
														.getNodeValue());

									}

								}
								if (jRecord.getInt("BackColorCustom") != 0) {
									String hexColor = Integer
											.toHexString(jRecord
													.getInt("BackColor"));

									style += "background-color: #"
											+ toHTMLColor(hexColor) + ";";

									jRecord.put("BackColor", "#"
											+ toHTMLColor(hexColor));
								}
								if (jRecord.getInt("ForeColorCustom") != 0) {
									String hexColor = Integer
											.toHexString(jRecord
													.getInt("ForeColor"));

									style += "color: #" + toHTMLColor(hexColor)
											+ ";";

									jRecord.put("ForeColor", "#"
											+ toHTMLColor(hexColor));
								}
								if (jRecord.getInt("FontBold") != 0) {
									style += "font-weight: bold;";
									styleClear += "font-weight: bold;";
								}
								if (jRecord.getInt("FontItalic") != 0) {
									style += "font-style: italic;";
									styleClear += "font-style: italic;";
								}
								if (jRecord.getInt("FontUnderline") != 0) {
									style += "text-decoration: underline;";
									styleClear += "text-decoration: underline;";
								}
								if (jRecord.getInt("ShowText") == 0) {
									style += "visibility: hidden;";
									styleClear += "visibility: hidden;";
								}

								jRecord.put("style", style);
								jRecord.put("styleClear", styleClear);
								jData.put(jRecord);
							}
						}
					} catch (SAXParseException e) {
						LOGGER.log(Level.INFO, this.getClass().getName(), e);
					}
				}
			}
			JSONObject jResponse = new JSONObject();
			jResponse.put("data", jData);
			jResponse.put("status", 0);
			return jResponse;
		} catch (Exception e) {
			return proceedError(e.getLocalizedMessage());
		}
	}
}
