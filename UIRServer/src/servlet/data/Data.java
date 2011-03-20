package servlet.data;

import java.io.Reader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.Vector;

import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import oracle.sql.CLOB;
import oracle.sql.ROWID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ru.softlab.rsdh.api.ObjectsFactory;
import ru.softlab.rsdh.api.RsdhConnection;
import ru.softlab.rsdh.api.classes.TBCLDataSet;
import ru.softlab.rsdh.api.classes.TBCLEditableQuery;
import ru.softlab.rsdh.api.classes.TBCLEditableTable;
import ru.softlab.rsdh.api.classes.TLDRTableWH;
import ru.softlab.rsdh.api.rs.TRSDSColumnList;
import servlet.RsdhServlet;
import utils.Constants;
import db.ConnectionManager;

@SuppressWarnings("serial")
public class Data extends RsdhServlet {
	private final static String PREFETCHERROR = "preFetchError";
	private final static String METADATA = "metaData";
	private final static String USEROBJECTID = "userObjectId";
	private final static String USEROBJECTPARAMS = "userObjectParams";

	private class FetchThread extends Thread {
		private HttpSession session;
		private final String dataSource;
		private final String params;
		private final BigDecimal objectId;

		public FetchThread(HttpSession session, String dataSource,
				String params, BigDecimal objectId) {
			super(session.getId());
			this.session = session;
			this.dataSource = dataSource;
			this.params = params;
			this.objectId = objectId;
		}

		public void run() {
			setSession(session); // Нужно, так как мы уже в другом потоке.
			ResultSet resultSet[] = new ResultSet[1];
			try {
				RsdhConnection.init(ConnectionManager.getConnection(session
						.getAttribute("username").toString(), session
						.getAttribute("password").toString()));

				TBCLDataSet dataSet = (TBCLDataSet) ObjectsFactory
						.createObjById("TBCLDataSet", objectId);

				dataSet.getData(params, null, false, null, resultSet);

				ResultSetMetaData metaData = resultSet[0].getMetaData();

				Vector<String> columnCodes = new Vector<String>();

				for (int i = 1; i <= metaData.getColumnCount(); i++) {
					columnCodes.add(metaData.getColumnLabel(i).replace("#",
							"-_-"));
				}

				setMetaData(dataSource, columnCodes);

				Vector<HashMap<Integer, Object>> data = new Vector<HashMap<Integer, Object>>();

				int rownum = 1;
				while (resultSet[0].next()) {
					HashMap<Integer, Object> row = new HashMap<Integer, Object>();
					row.put(-1, rownum++);
					for (int i = 0; i < columnCodes.size(); i++) {
						if (resultSet[0].getObject(i + 1) instanceof Date)
							row.put(i, resultSet[0].getTimestamp(i + 1));
						else if (resultSet[0].getObject(i + 1) instanceof Clob) {
							try {
								Clob c = resultSet[0].getClob(i + 1);
								int buffLength = 256;
								if (c.length() < buffLength)
									buffLength = ((Long) c.length()).intValue();
								char[] buffer = new char[buffLength];
								int count = 0;
								String str = "";
								Reader reader = c.getCharacterStream();
								while ((count = reader.read(buffer)) > -1) {
									str += new String(buffer, 0, count);
								}
								row.put(i, str);
							} catch (Exception e) {
								row.put(i, e.getLocalizedMessage());
							}
						} else
							row.put(i, resultSet[0].getObject(i + 1));
					}
					data.add(row);
				}

				// TODO Придумать способ очистки устаревших данных.
				setData(dataSource, data);
			} catch (Exception e) {
				if (!e.getMessage().contains("ORA-01013")) {
					e.printStackTrace();
					setPreFetchError(dataSource, e);
				}
			} finally {
				if (resultSet[0] != null)
					try {
						resultSet[0].getStatement().close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				RsdhConnection.closeConnection();
			}
		}
	}

	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		String operationType = jRequest.getString("operationType");

		if (operationType.equalsIgnoreCase("check")) {
			return check(jRequest);
		} else if (operationType.equalsIgnoreCase("preFetch")) {
			return preFetch(jRequest);
		} else if (operationType.equalsIgnoreCase("fetch")) {
			return fetch(jRequest);
		} else if (operationType.equalsIgnoreCase("add")) {
			return add(jRequest);
		} else if (operationType.equalsIgnoreCase("update")) {
			return update(jRequest);
		} else if (operationType.equalsIgnoreCase("remove")) {
			return remove(jRequest);
		} else if (operationType.equalsIgnoreCase("clear")) {
			return clear(jRequest);
		}
		return proceedError("Не задан обязательный параметр: \"operationType\".");
	}

	public static void clearData(String dataSourceId) {
		getSession().removeAttribute(dataSourceId);
		getSession().removeAttribute(dataSourceId + METADATA);
		getSession().removeAttribute(dataSourceId + USEROBJECTID);
		getSession().removeAttribute(dataSourceId + USEROBJECTPARAMS);
		getSession().removeAttribute(dataSourceId + PREFETCHERROR);

	}

	@SuppressWarnings("unchecked")
	public static Vector<HashMap<Integer, Object>> getData(String dataSourceId) {
		return (Vector<HashMap<Integer, Object>>) getSession().getAttribute(
				dataSourceId);
	}

	public static void setData(String dataSourceId,
			Vector<HashMap<Integer, Object>> data) {
		getSession().setAttribute(dataSourceId, data);
	}

	@SuppressWarnings("unchecked")
	public static Vector<String> getMetaData(String dataSourceId) {
		return (Vector<String>) getSession().getAttribute(
				dataSourceId + METADATA);
	}

	public static void setMetaData(String dataSourceId, Vector<String> metaData) {
		getSession().setAttribute(dataSourceId + METADATA, metaData);
	}

	public static Double getUserObjectId(String dataSourceId) {
		return (Double) getSession().getAttribute(dataSourceId + USEROBJECTID);
	}

	public static void setUserObjectId(String dataSourceId, Double userObjectId) {
		getSession().setAttribute(dataSourceId + USEROBJECTID, userObjectId);
	}

	public static String getParams(String dataSourceId) {
		return (String) getSession().getAttribute(
				dataSourceId + USEROBJECTPARAMS);
	}

	public static void setParams(String dataSourceId, String params) {
		getSession().setAttribute(dataSourceId + USEROBJECTPARAMS, params);
	}

	public static Exception getPreFetchError(String dataSourceId) {
		return (Exception) getSession().getAttribute(
				dataSourceId + PREFETCHERROR);
	}

	public static void setPreFetchError(String dataSourceId, Exception error) {
		getSession().setAttribute(dataSourceId + PREFETCHERROR, error);
	}

	private JSONObject check(JSONObject jRequest) throws JSONException {
		if (getPreFetchError(jRequest.getString("dataSource")) != null) {
			JSONObject error = proceedError(((Exception) getPreFetchError(jRequest
					.getString("dataSource"))).getLocalizedMessage());
			setPreFetchError(jRequest.getString("dataSource"), null);
			return error;
		}

		JSONObject jResponse = new JSONObject();
		if (getData(jRequest.getString("dataSource")) == null) {
			jResponse.put("status", 1);
		} else {
			jResponse.put("status", 0);
		}

		return jResponse;
	}

	private JSONObject preFetch(JSONObject jRequest) throws JSONException {
		double objectId = jRequest.getDouble("objectId");
		String objectParams = jRequest.getString("objectParams");

		JSONObject jResponse = new JSONObject();
		jResponse.put("status", 0);
		setData(jRequest.getString("dataSource"), null);
		setUserObjectId(jRequest.getString("dataSource"), objectId);
		setParams(jRequest.getString("dataSource"), objectParams);

		new FetchThread(getSession(), jRequest.getString("dataSource"),
				objectParams, new BigDecimal(objectId)).start();

		return jResponse;
	}

	private String parseColumns(JSONObject values) throws JSONException,
			ParserConfigurationException, TransformerFactoryConfigurationError,
			TransformerException {

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		docBuilder = dbf.newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		Transformer trans = TransformerFactory.newInstance().newTransformer();
		StringWriter sw = new StringWriter();

		Element root = doc.createElement("ROW");
		doc.appendChild(root);

		for (String key : JSONObject.getNames(values)) {
			Element params = doc.createElement("TPARAM");
			Element name = doc.createElement("NAME");
			name.appendChild(doc.createTextNode(key.replace("-_-", "#")));
			params.appendChild(name);

			Element value = doc.createElement("VALUE");
			if (values.opt(key) != null)
				value.appendChild(doc
						.createTextNode(values.get(key).toString()));
			params.appendChild(value);
			root.appendChild(params);
		}

		trans.transform(new DOMSource(doc), new StreamResult(sw));
		return sw.toString();
	}

	private String parseColumns(HashMap<String, String> values)
			throws JSONException, ParserConfigurationException,
			TransformerFactoryConfigurationError, TransformerException {
		JSONObject row = new JSONObject(values);
		return parseColumns(row);
	}

	private void normalize(JSONObject oldValues,
			ArrayList<TRSDSColumnList> columnList) throws JSONException {
		for (TRSDSColumnList column : columnList) {
			String colCode = column.getDSColumnCode().replace("#", "-_-");
			if (!oldValues.has(colCode) || oldValues.isNull(colCode)
					|| oldValues.getString(colCode).isEmpty()) {
				oldValues.put(colCode, "");
				continue;
			}

			if (column.getDSColumnDomainCode() != null
					&& column.getDSColumnDomainCode().equalsIgnoreCase("Bool")) {
				String value = oldValues.getString(colCode);
				if (value.equalsIgnoreCase("True")
						|| value.equalsIgnoreCase("1"))
					oldValues.put(colCode, "1");
				else
					oldValues.put(colCode, "0");
			}

			if (column.getDSColumnDataType().equalsIgnoreCase("Date")) {
				Date dt = null;
				try {
					DateFormat dateFormat = new SimpleDateFormat(
							"yyyy-MM-dd'T'HH:mm:ss");
					dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
					dt = dateFormat.parse(oldValues.getString(colCode));
				} catch (ParseException e) {
					try {
						DateFormat dateFormat = new SimpleDateFormat(
								"yyyy-MM-dd");
						dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
						dt = dateFormat.parse(oldValues.getString(colCode));
					} catch (ParseException e1) {
						e.printStackTrace();
					}
				}
				if (dt != null)
					oldValues.put(colCode,
							new SimpleDateFormat("yyyyMMdd").format(dt));
				// TODO
				// Check
				// when
				// server
				// will
				// be
				// fixed
			}
		}
	}

	private void normalize(HashMap<String, String> newValues,
			ArrayList<TRSDSColumnList> columnList) {
		for (TRSDSColumnList column : columnList) {

			String colCode = column.getDSColumnCode().replace("#", "-_-");
			String value = newValues.get(colCode);

			if (value != null && value.equals("null"))
				value = null;

			if (value != null && !value.isEmpty()) {
				if (column.getDSColumnDomainCode() != null
						&& column.getDSColumnDomainCode().equalsIgnoreCase(
								"Bool"))
					value = (value.equalsIgnoreCase("True") || value
							.equalsIgnoreCase("1")) ? "1" : "0";

				if (column.getDSColumnDataType().equalsIgnoreCase("Date")) {
					Date dt = null;
					try {
						DateFormat dateFormat = new SimpleDateFormat(
								"yyyy-MM-dd'T'HH:mm:ss");
						dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
						dt = dateFormat.parse(value);
					} catch (ParseException e) {
						try {
							DateFormat dateFormat = new SimpleDateFormat(
									"yyyy-MM-dd");
							dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
							dt = dateFormat.parse(value);
						} catch (ParseException e1) {
						}
					}
					if (dt != null)
						value = new SimpleDateFormat("yyyyMMdd").format(dt);// TODO
					// Check
					// when
					// server
					// will
					// be
					// fixed

				}
			}

			newValues.put(colCode, value);
		}
	}

	private JSONObject add(JSONObject jRequest) throws JSONException {
		Double objectId = getUserObjectId(jRequest.getString("dataSource"));
		String objectParams = getParams(jRequest.getString("dataSource"));

		if (objectId == null
				|| getData(jRequest.getString("dataSource")) == null)
			// TODO Translate
			return proceedError(Constants.NEED_PREFETCH,
					"You must first preFetch data for this datasource!");

		JSONObject jRequestData = jRequest.getJSONObject("data");

		try {
			TBCLDataSet dataSet = (TBCLDataSet) ObjectsFactory.createObjById(
					"TBCLDataSet", new BigDecimal(objectId));

			HashMap<String, String> newValues = new HashMap<String, String>();

			ArrayList<TRSDSColumnList> columnList = dataSet.getColumnList();

			for (TRSDSColumnList column : columnList) {
				String value = "";
				String colCode = column.getDSColumnCode().replace("#", "-_-");
				if (jRequestData.has(colCode)) {
					value = jRequestData.getString(colCode);
				}
				newValues.put(colCode, value);
			}

			normalize(newValues, columnList);

			if (dataSet instanceof TBCLEditableTable) {
				TBCLEditableTable editable = (TBCLEditableTable) dataSet;
				String rowId = editable.insertRow(parseColumns(newValues));
				newValues.put("ROWID", rowId);
			} else if (dataSet instanceof TBCLEditableQuery) {
				TBCLEditableQuery editable = (TBCLEditableQuery) dataSet;

				String[] outValues = new String[1];
				editable.insertRow(parseColumns(newValues), outValues,
						objectParams);

				String[] outValuesByCol = outValues[0].split("<TPARAM>");
				for (int i = 1; i < outValuesByCol.length; i++) {
					String colName = outValuesByCol[i].substring(
							outValuesByCol[i].indexOf("<NAME>") + 6,
							outValuesByCol[i].indexOf("</NAME>"));
					String colValue = "";
					if (outValuesByCol[i].contains("<VALUE>"))
						colValue = outValuesByCol[i].substring(
								outValuesByCol[i].indexOf("<VALUE>") + 7,
								outValuesByCol[i].indexOf("</VALUE>"));

					newValues.put(colName, colValue);
				}
			} else if (dataSet instanceof TLDRTableWH) {
				TLDRTableWH editable = (TLDRTableWH) dataSet;
				String rowId = editable.insertRow(parseColumns(newValues));
				newValues.put("ROWID", rowId);
			}

			ResultSet resultSet[] = new ResultSet[1];
			dataSet.getData(objectParams, "", false, parseColumns(newValues),
					resultSet);

			Vector<String> columnCodes = getMetaData(jRequest
					.getString("dataSource"));

			Vector<HashMap<Integer, Object>> newData = new Vector<HashMap<Integer, Object>>();

			while (resultSet[0].next()) {
				HashMap<Integer, Object> row = new HashMap<Integer, Object>();
				for (int i = 0; i < columnCodes.size(); i++) {
					row.put(i, resultSet[0].getObject(i + 1));
				}
				newData.add(row);
			}
			resultSet[0].close();

			Vector<HashMap<Integer, Object>> data = getData(jRequest
					.getString("dataSource"));

			int position = data.size() + 1;

			JSONArray jData = new JSONArray();
			int rowCount = newData.size();
			for (HashMap<Integer, Object> newRow : newData) {
				newRow.put(-1, position);
				data.add(position - 1, newRow);
				JSONObject jRecord = new JSONObject();
				jRecord.put("ROWNUM__PK", position++);
				for (int i = 0; i < columnCodes.size(); i++) {
					if (newRow.get(i) == null)
						jRecord.put(columnCodes.get(i), JSONObject.NULL);
					else if (newRow.get(i) instanceof ROWID) {
						ROWID rowId = (ROWID) newRow.get(i);
						jRecord.put(columnCodes.get(i), rowId.stringValue());
					} else
						jRecord.put(columnCodes.get(i), newRow.get(i));
				}
				jData.put(jRecord);
			}

			JSONObject jResponse = new JSONObject();
			jResponse.put("data", jData);
			jResponse.put("status", 0);
			jResponse.put("startRow", 0);
			jResponse.put("endRow", rowCount - 1);
			jResponse.put("totalRows", rowCount);
			// UserContextFactory.getContext().getConnection().commit();
			return jResponse;
		} catch (Exception e) {
			// Проверка на возможную ошибку валидации данных.
			Vector<String> columnCodes = getMetaData(jRequest
					.getString("dataSource"));
			String colCode = null;
			for (int i = 0; i < columnCodes.size(); i++)
				if (e.getMessage().contains("\"" + columnCodes.get(i) + "\"")) {
					colCode = columnCodes.get(i);
					break;
				}

			JSONObject error;
			if (colCode != null)
				error = proceedValidationError(colCode, e.getLocalizedMessage());
			else
				error = proceedError(e.getLocalizedMessage());

			// try {
			// UserContextFactory.getContext().getConnection().rollback();
			// } catch (SQLException e1) {
			// e1.printStackTrace();
			// }
			return error;
		} finally {
			// try {
			// UserContextFactory.getContext().getConnection()
			// .setAutoCommit(true);
			// } catch (SQLException e) {
			// e.printStackTrace();
			// }
		}
	}

	private JSONObject remove(JSONObject jRequest) throws JSONException {
		Double objectId = getUserObjectId(jRequest.getString("dataSource"));
		String objectParams = getParams(jRequest.getString("dataSource"));

		if (objectId == null
				|| getData(jRequest.getString("dataSource")) == null)
			// TODO Translate
			return proceedError(Constants.NEED_PREFETCH,
					"You must first preFetch data for this datasource!");

		JSONObject jRequestData = jRequest.getJSONObject("data");
		try {
			TBCLDataSet dataSet = (TBCLDataSet) ObjectsFactory.createObjById(
					"TBCLDataSet", new BigDecimal(objectId));

			HashMap<String, String> newValues = new HashMap<String, String>();

			ArrayList<TRSDSColumnList> columnList = dataSet.getColumnList();

			for (TRSDSColumnList column : columnList) {
				String value = "";
				String colCode = column.getDSColumnCode().replace("#", "-_-");
				if (jRequestData.has(colCode))
					value = jRequestData.getString(colCode);
				newValues.put(colCode, value);
			}

			normalize(newValues, columnList);

			if (dataSet instanceof TBCLEditableTable) {
				TBCLEditableTable editable = (TBCLEditableTable) dataSet;
				editable.deleteRow(newValues.get("ROWID"));
			} else if (dataSet instanceof TBCLEditableQuery) {
				TBCLEditableQuery editable = (TBCLEditableQuery) dataSet;

				editable.deleteRow(parseColumns(newValues), objectParams);
			} else if (dataSet instanceof TLDRTableWH) {
				TLDRTableWH editable = (TLDRTableWH) dataSet;
				editable.deleteRow(newValues.get("ROWID"));
			}

			Vector<HashMap<Integer, Object>> data = getData(jRequest
					.getString("dataSource"));

			int rownum = jRequestData.getInt("ROWNUM__PK");
			int position = rownum - 1;

			while (position >= data.size()
					|| (Integer) data.get(position).get(-1) != rownum)
				position--;
			data.remove(position);

			JSONArray jData = new JSONArray();
			JSONObject jRecord = new JSONObject();
			jRecord.put("ROWNUM__PK", jRequestData.getInt("ROWNUM__PK"));
			jData.put(jRecord);

			JSONObject jResponse = new JSONObject();
			jResponse.put("data", jData);
			jResponse.put("status", 0);
			// UserContextFactory.getContext().getConnection().commit();
			return jResponse;
		} catch (Exception e) {
			// try {
			// UserContextFactory.getContext().getConnection().rollback();
			// } catch (SQLException e1) {
			// e1.printStackTrace();
			// }
			return proceedError(e.getLocalizedMessage());
		} finally {
			// try {
			// UserContextFactory.getContext().getConnection()
			// .setAutoCommit(true);
			// } catch (SQLException e) {
			// e.printStackTrace();
			// }
		}
	}

	private JSONObject update(JSONObject jRequest) throws JSONException {
		Double objectId = getUserObjectId(jRequest.getString("dataSource"));
		String objectParams = getParams(jRequest.getString("dataSource"));

		if (objectId == null
				|| getData(jRequest.getString("dataSource")) == null)
			// TODO Translate
			return proceedError(Constants.NEED_PREFETCH,
					"You must first preFetch data for this datasource!");

		JSONObject jRequestData = jRequest.getJSONObject("data");

		try {
			TBCLDataSet dataSet = (TBCLDataSet) ObjectsFactory.createObjById(
					"TBCLDataSet", new BigDecimal(objectId));

			JSONObject oldValues = jRequest.getJSONObject("oldValues");

			HashMap<String, String> newValues = new HashMap<String, String>();

			ArrayList<TRSDSColumnList> columnList = dataSet.getColumnList();

			normalize(oldValues, columnList);

			for (TRSDSColumnList column : columnList) {
				String value = "";
				String colCode = column.getDSColumnCode().replace("#", "-_-");
				if (jRequestData.has(colCode)) {
					value = jRequestData.getString(colCode);
				} else {
					value = oldValues.getString(colCode);
				}
				newValues.put(colCode, value);
			}

			normalize(newValues, columnList);

			if (dataSet instanceof TBCLEditableTable) {
				TBCLEditableTable editable = (TBCLEditableTable) dataSet;
				editable.updateRow(oldValues.getString("ROWID"),
						parseColumns(newValues));
			} else if (dataSet instanceof TBCLEditableQuery) {
				TBCLEditableQuery editable = (TBCLEditableQuery) dataSet;

				String[] outValues = new String[1];
				editable.updateRow(parseColumns(newValues), outValues,
						parseColumns(oldValues), objectParams);

				String[] outValuesByCol = outValues[0].split("<TPARAM>");
				for (int i = 1; i < outValuesByCol.length; i++) {
					String colName = outValuesByCol[i].substring(
							outValuesByCol[i].indexOf("<NAME>") + 6,
							outValuesByCol[i].indexOf("</NAME>"));
					String colValue = "";
					if (outValuesByCol[i].contains("<VALUE>"))
						colValue = outValuesByCol[i].substring(
								outValuesByCol[i].indexOf("<VALUE>") + 7,
								outValuesByCol[i].indexOf("</VALUE>"));

					newValues.put(colName, colValue);
				}
			} else if (dataSet instanceof TLDRTableWH) {
				TLDRTableWH editable = (TLDRTableWH) dataSet;
				editable.updateRow(oldValues.getString("ROWID"),
						parseColumns(newValues));
			}

			ResultSet[] resultSet = new ResultSet[1];
			dataSet.getData(objectParams, "", false, parseColumns(newValues),
					resultSet);

			Vector<String> columnCodes = getMetaData(jRequest
					.getString("dataSource"));

			Vector<HashMap<Integer, Object>> newData = new Vector<HashMap<Integer, Object>>();

			while (resultSet[0].next()) {
				HashMap<Integer, Object> row = new HashMap<Integer, Object>();
				for (int i = 0; i < columnCodes.size(); i++) {
					row.put(i, resultSet[0].getObject(i + 1));
				}
				newData.add(row);
			}
			resultSet[0].close();

			Vector<HashMap<Integer, Object>> data = getData(jRequest
					.getString("dataSource"));

			int position = jRequestData.getInt("ROWNUM__PK");
			// Если от базы пришел пустой ответ
			if (newData.size() == 0) {
				HashMap<Integer, Object> row = data.get(position - 1);
				for (int i = 0; i < columnCodes.size(); i++) {
					if (jRequestData.has(columnCodes.get(i))) {
						// Заменяем старые значения в кеше на новые
						row.put(i, jRequestData.getString(columnCodes.get(i)));
					}
				}
				// Беремо строку для newData из кэша
				newData.add(row);
			}
			data.remove(position - 1);

			JSONArray jData = new JSONArray();
			int rowCount = newData.size();
			for (HashMap<Integer, Object> newRow : newData) {
				newRow.put(-1, position);
				data.add(position - 1, newRow);

				JSONObject jRecord = new JSONObject();
				jRecord.put("ROWNUM__PK", position++);
				for (int i = 0; i < columnCodes.size(); i++) {
					if (newRow.get(i) == null)
						jRecord.put(columnCodes.get(i), JSONObject.NULL);
					else if (newRow.get(i) instanceof ROWID) {
						ROWID rowId = (ROWID) newRow.get(i);
						jRecord.put(columnCodes.get(i), rowId.stringValue());
					} else
						jRecord.put(columnCodes.get(i), newRow.get(i));
				}
				jData.put(jRecord);
			}

			JSONObject jResponse = new JSONObject();
			jResponse.put("data", jData);
			jResponse.put("status", 0);
			jResponse.put("startRow", 0);
			jResponse.put("endRow", rowCount - 1);
			jResponse.put("totalRows", rowCount);
			// UserContextFactory.getContext().getConnection().commit();
			return jResponse;
		} catch (Exception e) {
			// Проверка на возможную ошибку валидации.
			Vector<String> columnCodes = getMetaData(jRequest
					.getString("dataSource"));
			String colCode = null;
			for (int i = 0; i < columnCodes.size(); i++)
				if (e.getMessage().contains("\"" + columnCodes.get(i) + "\"")) {
					colCode = columnCodes.get(i);
					break;
				}

			JSONObject error;
			if (colCode != null)
				error = proceedValidationError(colCode, e.getLocalizedMessage());
			else
				error = proceedError(e.getLocalizedMessage());

			// try {
			// UserContextFactory.getContext().getConnection().rollback();
			// } catch (SQLException e1) {
			// e1.printStackTrace();
			// }

			return error;
		} finally {
			// try {
			// UserContextFactory.getContext().getConnection()
			// .setAutoCommit(true);
			// } catch (SQLException e) {
			// e.printStackTrace();
			// }
		}
	}

	public static boolean filter(HashMap<Integer, Object> row,
			HashMap<Integer, String> filter) {
		for (Integer i : filter.keySet()) {
			if (filter.get(i) != null && row.get(i) == null)
				return false;
			if ((filter.get(i) == null || filter.get(i).isEmpty())
					&& (row.get(i) != null && !row.get(i).toString().isEmpty())) {
				return false;
			} else if (row.get(i) instanceof Date) {
				Date dt = null;
				try {
					DateFormat dateFormat = new SimpleDateFormat(
							"yyyy-MM-dd'T'HH:mm:ss");
					dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
					dt = dateFormat.parse(filter.get(i));
				} catch (ParseException e) {
					try {
						DateFormat dateFormat = new SimpleDateFormat(
								"yyyy-MM-dd");
						dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
						dt = dateFormat.parse(filter.get(i));
					} catch (ParseException e1) {
						DateFormat dateFormat = new SimpleDateFormat(
								"dd.MM.yyyy");
						return dateFormat.format((Date) row.get(i)).toString()
								.contains(filter.get(i));
					}
				}
				return dt != null && dt.compareTo((Date) row.get(i)) == 0;

			} else if (row.get(i) instanceof ROWID) {
				ROWID rowId = (ROWID) row.get(i);
				if (!rowId.stringValue().toLowerCase()
						.contains(filter.get(i).toLowerCase()))
					return false;
			} else if (row.get(i) != null
					&& !row.get(i).toString().toLowerCase()
							.contains(filter.get(i).toLowerCase()))
				return false;
		}
		return true;
	}

	public static void sortData(Vector<HashMap<Integer, Object>> data,
			Vector<String> metaData, final String sortBy) {
		if (sortBy != null && !sortBy.isEmpty() && !sortBy.equals("null")) {
			Integer tmpCol = 0;
			for (int i = 0; i < metaData.size(); i++)
				if (sortBy.endsWith(metaData.get(i))) {
					tmpCol = i;
					break;
				}
			Integer tmpSortType = 1;
			if (sortBy.startsWith("-"))
				tmpSortType = -1;

			final Integer sortCol = tmpCol;
			final Integer sortType = tmpSortType;
			Collections.sort(data, new Comparator<HashMap<Integer, Object>>() {
				@SuppressWarnings({ "rawtypes", "unchecked" })
				public int compare(HashMap<Integer, Object> arg0,
						HashMap<Integer, Object> arg1) {
					if ((arg0.get(sortCol) instanceof Comparable)
							&& (arg1.get(sortCol) instanceof Comparable)) {
						return sortType
								* ((Comparable) arg0.get(sortCol))
										.compareTo(arg1.get(sortCol));
					}

					if (arg0.get(sortCol) == null)
						return -1 * sortType;

					if (arg1.get(sortCol) == null)
						return sortType;

					return 0;
				}
			});
		}
	}

	private JSONObject fetch(JSONObject jRequest) throws JSONException {
		if (getPreFetchError(jRequest.getString("dataSource")) != null) {
			JSONObject error = proceedError(((Exception) getPreFetchError(jRequest
					.getString("dataSource"))).getLocalizedMessage());
			setPreFetchError(jRequest.getString("dataSource"), null);
			return error;
		}

		JSONObject jRequestData = null;
		if (jRequest.has("data"))
			jRequestData = jRequest.getJSONObject("data");

		if (getData(jRequest.getString("dataSource")) == null) {
			JSONObject jResponse = new JSONObject();
			jResponse.put("data", new JSONArray());
			jResponse.put("status", 0);
			jResponse.put("startRow", 0);
			jResponse.put("endRow", -1);
			jResponse.put("totalRows", 0);
			return jResponse;
		}

		Integer startRow = 0;
		Integer endRow = 0;
		if (jRequest.has("startRow")) {
			startRow = jRequest.getInt("startRow");
			endRow = jRequest.getInt("endRow");
		}

		Vector<HashMap<Integer, Object>> data = getData(jRequest
				.getString("dataSource"));
		Vector<String> metaData = getMetaData(jRequest.getString("dataSource"));

		Integer totalRows = data.size();

		JSONArray jData = new JSONArray();

		if (jRequest.has("sortBy") && !jRequest.isNull("sortBy")
				&& jRequest.getJSONArray("sortBy").length() > 0)
			sortData(data, metaData,
					jRequest.getJSONArray("sortBy").getString(0));

		setData(jRequest.getString("dataSource"), data);

		HashMap<Integer, String> filter = new HashMap<Integer, String>();
		if (jRequestData != null)
			for (int i = 0; i < metaData.size(); i++) {
				if (jRequestData.has(metaData.get(i))) {
					filter.put(i, jRequestData.getString(metaData.get(i)));
				}
			}
		int rowCount = 0;
		for (int rowNum = 0, allRows = totalRows; rowNum < allRows; rowNum++) {
			if (Data.filter(data.get(rowNum), filter)) {
				if (rowNum >= startRow) {
					if (rowCount < endRow - startRow || endRow == 0) {
						rowCount++;
						JSONObject jRecord = new JSONObject();
						jRecord.put("ROWNUM__PK", data.get(rowNum).get(-1));
						for (int i = 0; i < metaData.size(); i++) {
							if (data.get(rowNum).get(i) == null)
								jRecord.put(metaData.get(i), JSONObject.NULL);
							else if (data.get(rowNum).get(i) instanceof ROWID) {
								ROWID rowId = (ROWID) data.get(rowNum).get(i);
								jRecord.put(metaData.get(i),
										rowId.stringValue());
							} else if (data.get(rowNum).get(i) instanceof CLOB) {
								try {
									CLOB c = (CLOB) data.get(rowNum).get(i);
									c.setPhysicalConnectionOf(RsdhConnection
											.getConnection());
									int buffLength = 256;
									if (c.getLength() < buffLength)
										buffLength = ((Long) c.getLength())
												.intValue();
									char[] buffer = new char[buffLength];
									int count = 0;
									String str = "";
									Reader reader = c.getCharacterStream();
									while ((count = reader.read(buffer)) > -1) {
										str += new String(buffer, 0, count);
									}
									jRecord.put(metaData.get(i), str);
								} catch (Exception e) {
									jRecord.put(metaData.get(i),
											e.getLocalizedMessage());
								}
							} else
								jRecord.put(metaData.get(i), data.get(rowNum)
										.get(i));
						}
						jData.put(jRecord);
					} else if (filter.size() == 0)
						break;
				}
			} else
				totalRows--;
		}

		JSONObject jResponse = new JSONObject();
		jResponse.put("data", jData);
		jResponse.put("status", 0);
		jResponse.put("startRow", startRow);
		jResponse.put("endRow", rowCount + startRow - 1);
		jResponse.put("totalRows", totalRows);
		return jResponse;
	}

	private JSONObject clear(JSONObject jRequest) throws JSONException {
		clearData(jRequest.getString("dataSource"));

		JSONObject jResponse = new JSONObject();
		jResponse.put("status", 0);
		return jResponse;
	}
}
