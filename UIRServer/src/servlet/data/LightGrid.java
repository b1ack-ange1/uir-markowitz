package servlet.data;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import oracle.sql.BLOB;
import oracle.sql.CLOB;
import oracle.sql.ROWID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ru.softlab.rsdh.api.ObjectsFactory;
import ru.softlab.rsdh.api.RsdhConstants;
import ru.softlab.rsdh.api.classes.TBCLDSColumn;
import ru.softlab.rsdh.api.classes.TBCLDataSet;
import ru.softlab.rsdh.api.classes.TSRPDomainBase;
import ru.softlab.rsdh.api.classes.TSRPSysUserTunes;
import ru.softlab.rsdh.api.classes.TUSRGridTune;
import ru.softlab.rsdh.api.rs.TRSDSColumnList;
import ru.softlab.rsdh.api.rs.TRSDomainValueList;
import ru.softlab.rsdh.api.rs.TRSUSRObjTuneList;
import bsh.EvalError;
import bsh.Interpreter;

@SuppressWarnings("serial")
public class LightGrid extends HttpServlet {

	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

	public class HighLight {
		private String name = "";
		private String fieldList = "";
		private String originalExpression = "";
		private String expression = "";
		private String style = "";

		public HighLight(String name, String fieldList, String expression,
				String style) {
			this.setName(name);
			this.setFieldList(fieldList);
			this.setOriginalExpression(expression);
			this.setExpression(expression);
			this.setStyle(style);
		}

		protected void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		protected void setFieldList(String fieldList) {
			this.fieldList = fieldList;
		}

		public String getFieldList() {
			return fieldList;
		}

		protected void setOriginalExpression(String originalExpression) {
			this.originalExpression = originalExpression;
		}

		public String getOriginalExpression() {
			return originalExpression;
		}

		protected void setExpression(String expression) {
			this.expression = expression;
		}

		public String getExpression() {
			return expression;
		}

		protected void setStyle(String style) {
			this.style = style;
		}

		public String getStyle() {
			return style;
		}
	}

	private static String toHTMLColor(String hex) {
		// Цвета раскраски хранятся как hex в формате BGR, нам же нужен RGB.
		// При этом hex не имеет ведущих нулей.
		while (hex.length() < 6)
			hex = "0" + hex;
		return hex.substring(4) + hex.substring(2, 4) + hex.substring(0, 2);
	}

	private static String preParseExpression(String expression) {
		if (expression != null) {
			expression = expression.replaceAll("=", "==")
					.replaceAll("====", "==").replaceAll(">==", ">=")
					.replaceAll("<==", "<=");
			expression = expression.replaceAll("\\bOR\\b", "||");
			expression = expression.replaceAll("\\bor\\b", "||");
			expression = expression.replaceAll("\\bAND\\b", "&&");
			expression = expression.replaceAll("\\band\\b", "&&");
			expression = expression.replaceAll("\'", "\"");
		}

		return expression;
	}

	private static String parseExpression(String expression, String colCode,
			Object value) {
		String colValue;

		if (value == null)
			colValue = "null";
		else
			colValue = "\"" + value.toString() + "\"";

		expression = expression.replaceAll("\\b" + colCode + "\\b", colValue);
		expression = expression.replaceAll("\\bNULL\\b", "null");

		return expression;
	}

	private static void getData(TBCLDataSet dataSet, String paramValues,
			Vector<String> metaData, Vector<HashMap<Integer, Object>> data)
			throws SQLException {
		ResultSet[] resultSet = new ResultSet[1];
		try {
			dataSet.getData(paramValues, null, false, null, resultSet);

			ResultSetMetaData rsMetaData = resultSet[0].getMetaData();

			for (int i = 1; i <= rsMetaData.getColumnCount(); i++) {
				metaData.add(rsMetaData.getColumnLabel(i).replace("#", "-_-"));
			}

			int rownum = 1;
			while (resultSet[0].next()) {
				HashMap<Integer, Object> row = new HashMap<Integer, Object>();
				row.put(-1, rownum++);
				for (int i = 0; i < metaData.size(); i++) {
					if (resultSet[0].getObject(i + 1) instanceof Date)
						row.put(i, resultSet[0].getTimestamp(i + 1));
					else if (resultSet[0].getObject(i + 1) instanceof CLOB) {
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
		} finally {
			if (resultSet[0] != null)
				try {
					resultSet[0].getStatement().close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}

	private static void buildMeta(ArrayList<TRSDSColumnList> columnList,
			Vector<String> metaData, Vector<Map<String, Object>> exportCols)
			throws SQLException {
		for (TRSDSColumnList column : columnList) {
			String colCode = column.getDSColumnCode().replace("#", "-_-");
			HashMap<String, Object> exportCol = new HashMap<String, Object>();
			exportCol.put("colCode", colCode);
			exportCol.put("viewVisible", column.isDSColumnViewVisible());
			exportCol.put("viewCharCount", column.getDSColumnViewCharCount());
			for (int i = 0; i < metaData.size(); i++)
				if (metaData.get(i).equalsIgnoreCase(colCode)) {
					exportCol.put("colNum", i);
					break;
				}

			exportCol.put("colName", column.getDSColumnName());
			exportCol.put("domainCode", column.getDSColumnDomainCode());
			TSRPDomainBase domain = null;
			if (column.getDSColumnViewFormat() != null)
				exportCol.put("format", column.getDSColumnViewFormat()
						.replaceAll("%", "'%'"));
			else if (column.getDSColumnDomainId() != null) {
				domain = new TSRPDomainBase(column.getDSColumnDomainId());
				if (domain.getViewFormat() != null)
					exportCol.put("format",
							domain.getViewFormat().replaceAll("%", "'%'"));
			}
			if (exportCol.get("format") == null && domain != null) {
				if (domain.getDataType().equalsIgnoreCase("VARCHAR2")
						|| domain.getCode().equalsIgnoreCase("id"))
					exportCol.put("format", "###0");
			}

			if (column.getDSColumnViewAlignment() != null) {
				if (column.getDSColumnViewAlignment().intValue() == RsdhConstants.cnViewAlignmentRight)
					exportCol.put("aligment", "right");
				else if (column.getDSColumnViewAlignment().intValue() == RsdhConstants.cnViewAlignmentCenter)
					exportCol.put("aligment", "center");
			} else if (column.getDSColumnDomainId() != null) {
				if (domain == null)
					domain = new TSRPDomainBase(column.getDSColumnDomainId());
				if (domain.getViewAlignment() != null) {
					if (domain.getViewAlignment().intValue() == RsdhConstants.cnViewAlignmentRight)
						exportCol.put("aligment", "right");
					else if (domain.getViewAlignment().intValue() == RsdhConstants.cnViewAlignmentCenter)
						exportCol.put("aligment", "center");
				}
			}

			if (column.getDSColumnDomainId() != null) {
				if (domain == null)
					domain = new TSRPDomainBase(column.getDSColumnDomainId());
				exportCol.put("domainValueList", domain.getValueList());
			}

			// Разыменовка измерений
			String colSource = null;
			if (column.getDSColumnValDimDataId() != null) {
				for (TRSDSColumnList dsCol : columnList) {
					if (dsCol.getDSColumnCode().equals(
							column.getDSColumnCode() + "#N")) {
						colSource = dsCol.getDSColumnCode();
						break;
					}
				}
			}

			// Подстановка значений из другой колонки.
			if (column.getDSColumnSourceColumnId() != null)
				colSource = new TBCLDSColumn(column.getDSColumnSourceColumnId())
						.getCode();

			if (colSource != null) {
				colSource = colSource.replace("#", "-_-");
				for (int i = 0; i < metaData.size(); i++)
					if (metaData.get(i).equalsIgnoreCase(colSource)) {
						exportCol.put("displayColNum", i);
						break;
					}
			}

			exportCols.add(exportCol);
		}
	}

	private void getHighLights(Double objectId, Vector<HighLight> highLights)
			throws SQLException {
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

			if (data[0] != null) {
				try {
					Document xmlDocument = DocumentBuilderFactory.newInstance()
							.newDocumentBuilder()
							.parse(data[0].getBinaryStream());

					Vector<Map<String, String>> tuneRecords = new Vector<Map<String, String>>();

					NodeList hlsList = xmlDocument
							.getElementsByTagName("Highlights");
					for (int j = 0; j < hlsList.getLength(); j++) {
						NodeList hlList = hlsList.item(j).getChildNodes();
						for (int jj = hlList.getLength() - 1; jj >= 0; jj--) {
							// Временное решение проблемы с порядком раскрасок
							// for (int jj = 0; jj < hlList.getLength(); jj++) {
							NamedNodeMap hlAttrs = hlList.item(jj)
									.getAttributes();
							HashMap<String, String> tuneRecord = new HashMap<String, String>();
							for (int jjj = 0; jjj < hlAttrs.getLength(); jjj++) {
								tuneRecord.put(hlAttrs.item(jjj).getNodeName(),
										hlAttrs.item(jjj).getNodeValue());
							}
							tuneRecords.add(tuneRecord);
						}
					}
					// Идентификатор раскраски в пределах сервлета
					int highLightID = 0;

					for (Map<String, String> record : tuneRecords) {
						if (!record.get("Enabled").equals("0")) {
							String style = "";

							if (!record.get("BackColorCustom").equals("0")) {
								String hexColor = Integer
										.toHexString(new Integer(record
												.get("BackColor")));
								style += "background-color: #"
										+ toHTMLColor(hexColor) + ";";
							}
							if (!record.get("ForeColorCustom").equals("0")) {
								String hexColor = Integer
										.toHexString(new Integer(record
												.get("ForeColor")));
								style += "color: #" + toHTMLColor(hexColor)
										+ ";";
							}
							if (!record.get("FontBold").equals("0")) {
								style += "font-weight: bold;";
							}
							if (!record.get("FontItalic").equals("0")) {
								style += "font-style: italic;";
							}
							if (!record.get("FontUnderline").equals("0")) {
								style += "text-decoration: underline;";
							}

							HighLight highLight = new LightGrid.HighLight(
									"highLight_" + highLightID,
									record.get("FieldList"),
									preParseExpression(record.get("Expression")),
									style);
							highLights.add(highLight);
							highLightID++;
						}
					}
					// Глушим не важные нам ошибки.
				} catch (IOException e) {
				} catch (ParserConfigurationException e) {
				} catch (SAXException e) {
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private static Document generateHTML(String gridId,
			Vector<Map<String, Object>> exportCols,
			Vector<HashMap<Integer, Object>> data,
			Vector<HighLight> highLights, String sortCol) throws JSONException,
			ParserConfigurationException, DOMException, EvalError {
		Document document = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder().newDocument();

		// Родительская область
		Element parentDiv = document.createElement("div");
		parentDiv.setAttribute("id", gridId + "_parentDiv");
		parentDiv.setAttribute("onMouseMove", "rsdhLightGrid_resizeColl("
				+ gridId + "_header" + ", event);");
		parentDiv.setAttribute("onMouseUp", "rsdhLightGrid_stopResizing();");
		document.appendChild(parentDiv);

		// Область заголовков
		Element headerDiv = document.createElement("div");
		headerDiv.setAttribute("id", gridId + "_headerDiv");
		headerDiv.setAttribute("class", "headerBar");
		headerDiv.setAttribute("style",
				"width: 100%; height: 23px; overflow: hidden;");
		parentDiv.appendChild(headerDiv);

		// Область данных
		Element bodyDiv = document.createElement("div");
		bodyDiv.setAttribute("id", gridId + "_bodyDiv");
		bodyDiv.setAttribute("style", "width: 100%; overflow: auto;");
		// Inline скрипт - связывает скроллеры областей заголовков и итогов со
		// скроллером области данных
		bodyDiv.setAttribute("onScroll", gridId + "_headerDiv.scrollLeft = "
				+ gridId + "_bodyDiv.scrollLeft; " + gridId
				+ "_footerDiv.scrollLeft = " + gridId + "_bodyDiv.scrollLeft;");
		parentDiv.appendChild(bodyDiv);

		// Область итогов
		Element footerDiv = document.createElement("div");
		footerDiv.setAttribute("id", gridId + "_footerDiv");
		footerDiv.setAttribute("style",
				"width: 100%; height: 22px; overflow: hidden;");
		parentDiv.appendChild(footerDiv);

		// Таблица загловков
		Element header = document.createElement("table");
		header.setAttribute("id", gridId + "_header");
		header.setAttribute("width", "100%");
		header.setAttribute("border", "0");
		header.setAttribute("cellspacing", "0");
		header.setAttribute("cellpadding", "0");
		header.setAttribute("style",
				"table-layout:fixed;overflow:auto;wrap:false;");
		headerDiv.appendChild(header);

		// Таблица данных
		Element body = document.createElement("table");
		body.setAttribute("id", gridId + "_body");
		body.setAttribute("width", "100%");
		body.setAttribute("border", "0");
		body.setAttribute("class", "listTable");
		body.setAttribute("cellspacing", "0");
		body.setAttribute("cellpadding", "2");
		body.setAttribute("style",
				"table-layout:fixed;overflow:auto;wrap:false;");
		bodyDiv.appendChild(body);

		// Таблица итогов
		Element footer = document.createElement("table");
		footer.setAttribute("id", gridId + "_footer");
		footer.setAttribute("width", "100%");
		footer.setAttribute("border", "0");
		footer.setAttribute("class", "gridSummaryCell");
		footer.setAttribute("cellspacing", "0");
		footer.setAttribute("cellpadding", "2");
		footer.setAttribute("style",
				"table-layout:fixed;overflow:auto;wrap:false;");
		footerDiv.appendChild(footer);

		// Задаем ширины колонок для всех таблиц
		int colID = 0; // Счетчик колонок, для генерации ID колонки
		for (Map<String, Object> exportCol : exportCols) {
			if ((Boolean) exportCol.get("viewVisible")) {
				// Для заголовков
				Element col = document.createElement("col");
				col.setAttribute("id", gridId + "_Сol" + colID);
				if (exportCol.get("viewCharCount") != null
						&& Integer.valueOf(exportCol.get("viewCharCount")
								.toString()) > 0) {
					col.setAttribute(
							"width",
							String.valueOf(Integer.valueOf(exportCol.get(
									"viewCharCount").toString()) * 7));
				} else {
					col.setAttribute("width", "100");
				}
				header.appendChild(col);

				// Для данных
				col = document.createElement("col");
				col.setAttribute("id", gridId + "_Сol" + colID + "_b");
				if (exportCol.get("viewCharCount") != null
						&& Integer.valueOf(exportCol.get("viewCharCount")
								.toString()) > 0) {
					col.setAttribute(
							"width",
							String.valueOf(Integer.valueOf(exportCol.get(
									"viewCharCount").toString()) * 7));
				} else {
					col.setAttribute("width", "100");
				}
				body.appendChild(col);

				// Для итогов
				col = document.createElement("col");
				col.setAttribute("id", gridId + "_Сol" + colID + "_f");
				if (exportCol.get("viewCharCount") != null
						&& Integer.valueOf(exportCol.get("viewCharCount")
								.toString()) > 0) {
					col.setAttribute(
							"width",
							String.valueOf(Integer.valueOf(exportCol.get(
									"viewCharCount").toString()) * 7));
				} else {
					col.setAttribute("width", "100");
				}
				footer.appendChild(col);

				colID++; // Увеличиваем счетчик колонок
			}
		}

		// Создаем системные колонки для предотвращение "развала" элементов
		// скроллером
		// Для заголовков
		Element col = document.createElement("col");
		col.setAttribute("width", "2000px");
		header.appendChild(col);
		// Для данных
		col = document.createElement("col");
		col.setAttribute("width", "100%");
		body.appendChild(col);
		// Для итогов
		col = document.createElement("col");
		col.setAttribute("width", "2000px");
		footer.appendChild(col);

		// Экспортируем заголовки
		colID = 0; // Счетчик колонок, для генерации ID колонки
		for (Map<String, Object> exportCol : exportCols) {
			if ((Boolean) exportCol.get("viewVisible")) {
				// Генерируем ячейку заголовка
				Element td = document.createElement("td");
				// td.setAttribute("id", gridId + "_Сol" + colID);
				td.setAttribute("height", "23");
				td.setAttribute("background",
						"sc/skins/Enterprise/images/ListGrid/header.png");
				td.setAttribute("class", "headerTitle headerButton");
				td.setAttribute("style", "padding:0px;");
				td.setAttribute("align", "left");
				td.setAttribute("valign", "center");

				Element headTable = document.createElement("table");
				headTable.setAttribute("cellspacing", "0");
				headTable.setAttribute("cellpadding", "0");
				headTable
						.setAttribute("style",
								"height: 23px; width: 100%; table-layout:fixed; margin: 0px;");
				Element headTableTR = document.createElement("tr");
				Element headTableLeftTD = document.createElement("td");
				headTableLeftTD.setAttribute("style",
						"width: 3px; cursor: e-resize;");
				if (colID != 0) {
					headTableLeftTD.setAttribute("onMouseDown",
							"rsdhLightGrid_startResizing('" + gridId + "_Сol"
									+ (colID - 1) + "', event)");
				}

				Element headTableContentTD = document.createElement("td");
				// Задаем свойство overflow:hidden - запрет наложения текста на
				// соседнюю ячейку
				Element div = document.createElement("div");
				div.setAttribute("cellclipdiv", "true");
				div.setAttribute("style", "overflow:hidden; padding: 0px;");
				div.setAttribute("class", "headerTitle");
				// Задаем свойство nobr - запрет переноса строки в ячейке
				Element nobr = document.createElement("nobr");
				// Заполняем текст заголовка
				nobr.appendChild(document.createTextNode(exportCol.get(
						"colName").toString()));
				nobr.setAttribute("colCode", exportCol.get("colCode")
						.toString());
				div.appendChild(nobr);

				if (sortCol != null && !sortCol.isEmpty()
						&& !sortCol.equals("null")) {
					Element sortImg = document.createElement("img");
					sortImg.setAttribute("width", "9");
					sortImg.setAttribute("height", "6");
					sortImg.setAttribute("align", "TEXTTOP");
					sortImg.setAttribute("border", "0");
					sortImg.setAttribute("suppress", "TRUE");
					if (sortCol.equals(exportCol.get("colCode"))) {
						nobr.setAttribute("sortType", "1");
						sortImg.setAttribute("src",
								"sc/skins/Enterprise/images/ListGrid/sort_ascending.png");
						nobr.appendChild(sortImg);
					} else if (sortCol.equals("-" + exportCol.get("colCode"))) {
						sortImg.setAttribute("src",
								"sc/skins/Enterprise/images/ListGrid/sort_descending.png");
						nobr.appendChild(sortImg);
					}
				}

				headTableContentTD.appendChild(div);

				Element headTableRightTD = document.createElement("td");
				headTableRightTD.setAttribute("style",
						"width: 3px; float:right; cursor: e-resize;");
				headTableRightTD.setAttribute("onMouseDown",
						"rsdhLightGrid_startResizing('" + gridId + "_Сol"
								+ colID + "', event)");
				headTable.appendChild(headTableTR);

				headTableTR.appendChild(headTableLeftTD);
				headTableTR.appendChild(headTableContentTD);
				headTableTR.appendChild(headTableRightTD);

				td.appendChild(headTable);

				header.appendChild(td);

				colID++; // Увеличиваем счетчик колонок
			}
		}

		// Генерируем ячейку-хвост в строке заголовков
		{
			Element td = document.createElement("td");
			td.setAttribute("height", "23");
			td.setAttribute("background",
					"sc/skins/Enterprise/images/ListGrid/header.png");
			td.setAttribute("class", "headerTitle headerButton");
			Element div = document.createElement("div");
			td.appendChild(div);
			div.appendChild(document.createTextNode("\u00a0"));

			header.appendChild(td);
		}

		// Экспортируем данные
		JSONArray jData = new JSONArray();
		// Массив для подстчета итогов
		HashMap<String, BigDecimal> itogSum = new HashMap<String, BigDecimal>();
		for (int rowNum = 0; rowNum < data.size(); rowNum++) {
			JSONObject jRecord = new JSONObject();
			HashMap<String, Map<String, Element>> cellsForHighLight = new HashMap<String, Map<String, Element>>();
			// if (Data.filter(data.get(rowNum), filter)) {
			// Export this row!
			Element tr = document.createElement("tr");
			tr.setAttribute("index", String.valueOf(rowNum));
			for (Map<String, Object> exportCol : exportCols) {
				Element currentTD = null;
				HashMap<String, Element> cellForHighLight = new HashMap<String, Element>();

				Object value = data.get(rowNum).get(exportCol.get("colNum"));
				jRecord.put(exportCol.get("colCode").toString(), value);

				if (exportCol.get("displayColNum") != null)
					value = data.get(rowNum)
							.get(exportCol.get("displayColNum"));

				if ((Boolean) exportCol.get("viewVisible")) {
					Element td = document.createElement("td");
					td.setAttribute("height", "22");
					td.setAttribute("style",
							"padding-top: 0px; padding-bottom: 0px; overflow: hidden;");
					td.setAttribute("class", "cell");
					if (exportCol.get("aligment") != null)
						td.setAttribute("align", exportCol.get("aligment")
								.toString());

					Element div = document.createElement("div");
					div.setAttribute("cellclipdiv", "true");
					div.setAttribute("style", "overflow:hidden;");
					Element nobr = document.createElement("nobr");

					if (value != null) {
						if (exportCol.get("domainCode") != null
								&& exportCol.get("domainCode").toString()
										.equalsIgnoreCase("Bool")) {
							Element img = document.createElement("img");
							img.setAttribute("width", "13");
							img.setAttribute("height", "13");
							img.setAttribute("align", "TEXTTOP");
							img.setAttribute("style",
									"vertical-align:middle;margin-left:2px;margin-right:2px;");
							img.setAttribute("border", "0");
							img.setAttribute("suppress", "TRUE");
							if (value.toString().equalsIgnoreCase("1")
									|| value.toString()
											.equalsIgnoreCase("true"))
								img.setAttribute("src",
										"sc/skins/Enterprise/images/DynamicForm/checked.png");
							else {
								img.setAttribute("src",
										"sc/skins/Enterprise/images/DynamicForm/unchecked.png");
							}
							nobr.appendChild(img);
						} else if (exportCol.get("domainValueList") != null
								&& ((ArrayList<TRSDomainValueList>) exportCol
										.get("domainValueList")).size() > 0) {
							ArrayList<TRSDomainValueList> domainValueList = (ArrayList<TRSDomainValueList>) exportCol
									.get("domainValueList");
							for (TRSDomainValueList domainValue : domainValueList)
								if (value.toString().equals(
										domainValue.getDVValue())) {
									nobr.appendChild(document
											.createTextNode(domainValue
													.getDVName()));
									break;
								}
						} else if (value instanceof Date) {
							if (exportCol.get("format") != null) {
								DateFormat dateFormat = new SimpleDateFormat(
										exportCol.get("format").toString());
								nobr.appendChild(document
										.createTextNode(dateFormat
												.format(value)));
							} else {
								nobr.appendChild(document
										.createTextNode(DateFormat
												.getDateInstance()
												.format(value)));
							}
						} else if (value instanceof Number) {
							if (exportCol.get("format") != null) {
								NumberFormat numberFormat = new DecimalFormat(
										exportCol.get("format").toString());
								nobr.appendChild(document
										.createTextNode(numberFormat
												.format(value)));
							} else {
								NumberFormat numberFormat = new DecimalFormat();
								nobr.appendChild(document
										.createTextNode(numberFormat
												.format(value)));
							}

							// Подсчет итогов
							if (exportCol.get("domainCode") == null
									|| !exportCol.get("domainCode").toString()
											.equalsIgnoreCase("id")) {
								if (itogSum.get(exportCol.get("colCode")
										.toString()) != null)
									itogSum.put(
											exportCol.get("colCode").toString(),
											itogSum.get(
													exportCol.get("colCode")
															.toString()).add(
													(BigDecimal) value));
								else
									itogSum.put(exportCol.get("colCode")
											.toString(), (BigDecimal) value);
							} else {
								if (itogSum.get(exportCol.get("colCode")
										.toString()) != null)
									itogSum.put(
											exportCol.get("colCode").toString(),
											itogSum.get(
													exportCol.get("colCode")
															.toString()).add(
													BigDecimal.ONE));
								else
									itogSum.put(exportCol.get("colCode")
											.toString(), BigDecimal.ONE);
							}
						} else if (value instanceof ROWID) {
							nobr.appendChild(document
									.createTextNode(((ROWID) value)
											.stringValue()));
						} else
							nobr.appendChild(document.createTextNode(value
									.toString().replace(" ", "\u00a0")));
					} else {
						nobr.appendChild(document.createTextNode("\u00a0"));
					}

					currentTD = td;

					div.appendChild(nobr);
					td.appendChild(div);
					tr.appendChild(td);
				}

				// Определяем текущее условие раскраски в пределах строки
				for (HighLight highLight : highLights) {
					// Заменяем поле в условии раскраски на его значение
					highLight.setExpression(parseExpression(highLight
							.getExpression(), exportCol.get("colCode")
							.toString(), value));
					// Если поле учавствует в раскраске, запоминаем его в
					// cellsForHighLight
					if (currentTD != null) {
						String[] fieldNames = highLight.getFieldList().split(
								";");
						for (String fieldName : fieldNames) {
							if (fieldName.equalsIgnoreCase(exportCol.get(
									"colCode").toString())) {
								cellForHighLight.put(highLight.getName(),
										currentTD);
								cellsForHighLight.put(exportCol.get("colCode")
										.toString(), cellForHighLight);
							}
						}
					}
				}
			}
			jData.put(jRecord);
			body.appendChild(tr);
			// }

			// Применяем раскраску
			for (HighLight highLight : highLights) {
				// Если условие выполняется, задаем стиль ячейки
				if ((Boolean) new Interpreter().eval(highLight.getExpression())) {
					for (Map<String, Element> cellForHighLight : cellsForHighLight
							.values()) {
						Element currentTD = cellForHighLight.get(highLight
								.getName());
						if (currentTD != null)
							currentTD.setAttribute("style",
									highLight.getStyle());
					}
				}
				// Сбрасываем условие раскраски на исходное
				highLight.setExpression(highLight.getOriginalExpression());
			}
		}

		// Генерируем строку итогов
		for (Map<String, Object> exportCol : exportCols) {
			if ((Boolean) exportCol.get("viewVisible")) {
				// Генерируем ячейку итога
				Element td = document.createElement("td");
				td.setAttribute("height", "22");
				td.setAttribute("style",
						"overflow: hidden; padding-top:0px;padding-bottom:0px;");
				td.setAttribute("align", "left");
				td.setAttribute("valign", "center");
				// Задаем свойство overflow:hidden - запрет наложения текста на
				// соседнюю ячейку
				Element div = document.createElement("div");
				div.setAttribute("cellclipdiv", "true");
				div.setAttribute("style", "overflow:hidden;");
				// Задаем свойство nobr - запрет переноса строки в ячейке
				Element nobr = document.createElement("nobr");
				// Заполняем текст итога
				if (itogSum.get(exportCol.get("colCode").toString()) != null) {
					NumberFormat numberFormat = new DecimalFormat();
					nobr.appendChild(document.createTextNode(numberFormat
							.format(itogSum.get(exportCol.get("colCode")
									.toString()))));
				} else
					nobr.appendChild(document.createTextNode(" "));
				div.appendChild(nobr);
				td.appendChild(div);
				footer.appendChild(td);
			}
		}

		// Генерируем ячейку-хвост в строке итогов
		{
			Element td = document.createElement("td");
			td.setAttribute("height", "22");
			td.appendChild(document.createTextNode("\u00a0"));

			footer.appendChild(td);
		}

		// Генерируем строку RAW-дынных
		Element rawDataScript = document.createElement("script");
		rawDataScript.appendChild(document
				.createTextNode("window.initRsdhLightGridBody('" + gridId
						+ "', " + jData.toString() + ");"));
		parentDiv.appendChild(rawDataScript);

		return document;
	}

	protected static String proceedError(String msg) {
		String result = "<error style='display:none;'>" + msg + "</error>";
		return result;
	}

	protected final void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			req.setCharacterEncoding("UTF-8");
			resp.setContentType(CONTENT_TYPE);
			String objectId = req.getParameter("objectId");
			if (objectId == null) {
				resp.getWriter().write(
						"Не задан обязательный параметр: \"objectId\".");
				resp.getWriter().close();
				return;
			}
			String gridId = req.getParameter("gridId");
			if (gridId == null) {
				resp.getWriter().write(
						"Не задан обязательный параметр: \"gridId\".");
				resp.getWriter().close();
				return;
			}
			String paramValues = req.getParameter("paramValues");
			if (paramValues == null) {
				resp.getWriter().write(
						"Не задан обязательный параметр: \"paramValues\".");
				resp.getWriter().close();
				return;
			}
			// Структуры для хранения промежуточных данных
			Vector<HashMap<Integer, Object>> data = new Vector<HashMap<Integer, Object>>();
			Vector<String> metaData = new Vector<String>();
			Vector<Map<String, Object>> exportCols = new Vector<Map<String, Object>>();
			TBCLDataSet dataSet = (TBCLDataSet) ObjectsFactory.createObjById(
					"TBCLDataSet", new BigDecimal(objectId));
			ArrayList<TRSDSColumnList> columnList;
			Vector<HighLight> highLights = new Vector<HighLight>();

			// Получаем набор данных из хранилища
			getData(dataSet, paramValues, metaData, data);

			// Определяем тип ответа сервлета
			resp.setContentType(CONTENT_TYPE);

			// Определяем список колонок из набора данных
			columnList = dataSet.getColumnList();

			// Сортируем набор данных
			Data.sortData(data, metaData, req.getParameter("_sortBy"));

			// Преобразуем набор данных
			buildMeta(columnList, metaData, exportCols);

			// Определяем настройки раскраски
			this.getHighLights(Double.valueOf(objectId), highLights);

			// Генерируем HTML-фрагмент
			Document document = generateHTML(gridId, exportCols, data,
					highLights, req.getParameter("_sortBy"));

			// Преобразуем и передаем HTML-фрагмент клиенту
			Transformer trans = TransformerFactory.newInstance()
					.newTransformer();
			StringWriter sw = new StringWriter();
			trans.transform(new DOMSource(document), new StreamResult(sw));

			PrintWriter writer = resp.getWriter();
			writer.write(sw.toString());
			sw.close();
			writer.close();
		} catch (Exception e) {
			resp.getWriter().write(proceedError(e.getLocalizedMessage()));
			resp.getWriter().close();
		}
	}
}
