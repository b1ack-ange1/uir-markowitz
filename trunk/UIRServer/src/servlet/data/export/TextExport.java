package servlet.data.export;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
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

import oracle.sql.ROWID;
import ru.softlab.rsdh.api.ObjectsFactory;
import ru.softlab.rsdh.api.classes.TBCLDataSet;
import ru.softlab.rsdh.api.classes.TSRPDomainBase;
import ru.softlab.rsdh.api.rs.TRSDSColumnList;
import ru.softlab.rsdh.api.rs.TRSDomainValueList;
import servlet.data.Data;

@SuppressWarnings("serial")
public class TextExport extends HttpServlet {
	private static final String CONTENT_TYPE = "text/plain; charset=UTF-8";
	private static final String DELIMITER = ";";

	@SuppressWarnings("unchecked")
	protected final void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType(CONTENT_TYPE);

		String objectId = req.getParameter("objectId");
		if (objectId == null) {
			resp.getWriter().write(
					"Не задан обязательный параметр: \"objectId\".");
			resp.getWriter().close();
			return;
		}

		if (Data.getData(req.getParameter("_dataSource")) == null) {
			// TODO Translate
			resp.getWriter()
					.write("Ошибка получния данных. Возможно ваша сессия была завершена.");
			resp.getWriter().close();
			return;
		}

		TBCLDataSet dataSet;
		ArrayList<TRSDSColumnList> columnList;
		try {
			dataSet = (TBCLDataSet) ObjectsFactory.createObjById("TBCLDataSet",
					new BigDecimal(objectId));
			columnList = dataSet.getColumnList();
		} catch (SQLException e) {
			resp.getWriter().write(e.getLocalizedMessage());
			resp.getWriter().close();
			return;
		}

		Vector<HashMap<Integer, Object>> data = Data.getData(req
				.getParameter("_dataSource"));
		Vector<String> metaData = Data.getMetaData(req
				.getParameter("_dataSource"));

		StringWriter stringWriter = new StringWriter();

		// Сортируем
		Data.sortData(data, metaData, req.getParameter("_sortBy"));
		Data.setData(req.getParameter("_dataSource"), data);

		Vector<String> selectedRows = new Vector<String>();
		if (req.getParameter("selectedRows") != null
				&& !req.getParameter("selectedRows").isEmpty()) {
			for (String rowId : req.getParameter("selectedRows").split(";"))
				selectedRows.add(rowId);
		}
		String[] visibleCols = (req.getParameter("visibleCols") != null ? req
				.getParameter("visibleCols").split(";") : new String[0]);
		HashMap<Integer, String> filter = new HashMap<Integer, String>();
		for (int i = 0; i < metaData.size(); i++) {
			if (req.getParameterMap().containsKey(metaData.get(i))) {
				filter.put(i, req.getParameter(metaData.get(i)));
			}
		}

		Vector<Map<String, Object>> exportCols = new Vector<Map<String, Object>>();
		for (String visibleCol : visibleCols) {
			HashMap<String, Object> exportCol = new HashMap<String, Object>();
			String colCode = visibleCol.replace("-_-", "#");
			exportCol.put("colCode", colCode);
			for (int i = 0; i < metaData.size(); i++)
				if (metaData.get(i).equalsIgnoreCase(visibleCol)) {
					exportCol.put("colNum", i);
					break;
				}
			try {
				for (TRSDSColumnList dsCol : columnList)
					if (dsCol.getDSColumnCode().equalsIgnoreCase(colCode)) {
						exportCol.put("colName", dsCol.getDSColumnName());
						exportCol.put("domainCode",
								dsCol.getDSColumnDomainCode());
						TSRPDomainBase domain = null;
						if (dsCol.getDSColumnViewFormat() != null)
							exportCol.put(
									"format",
									dsCol.getDSColumnViewFormat().replaceAll(
											"%", "'%'"));
						else if (dsCol.getDSColumnDomainId() != null) {
							domain = new TSRPDomainBase(
									dsCol.getDSColumnDomainId());
							if (domain.getViewFormat() != null)
								exportCol.put("format", domain.getViewFormat()
										.replaceAll("%", "'%'"));
						}

						if (dsCol.getDSColumnDomainId() != null) {
							if (domain == null)
								domain = new TSRPDomainBase(
										dsCol.getDSColumnDomainId());
							exportCol.put("domainValueList",
									domain.getValueList());
						}
						break;
					}
				if (exportCol.get("colName").toString().contains("#N")) {
					colCode = colCode.replace("#N", "");
					for (TRSDSColumnList dsCol : columnList)
						if (dsCol.getDSColumnCode().equalsIgnoreCase(colCode)) {
							exportCol.put("colName", dsCol.getDSColumnName());
							break;
						}
				}
			} catch (SQLException e) {
				resp.getWriter().write(e.getLocalizedMessage());
				resp.getWriter().close();
				return;
			}

			exportCols.add(exportCol);
		}

		// Экспортируем

		// Заголовок
		int colNum = 0;
		for (Map<String, Object> exportCol : exportCols) {
			if (colNum++ > 0)
				stringWriter.append(DELIMITER);
			stringWriter.append(exportCol.get("colName").toString());
		}
		stringWriter.append("\r\n");
		// Данные
		for (int rowNum = 0; rowNum < data.size(); rowNum++) {
			if (selectedRows.size() == 0
					|| selectedRows.contains(data.get(rowNum).get(-1)
							.toString())) {
				if (Data.filter(data.get(rowNum), filter)) {
					// Export this row!
					colNum = 0;
					for (Map<String, Object> exportCol : exportCols) {
						if (colNum++ > 0)
							stringWriter.append(DELIMITER);
						Object value = data.get(rowNum).get(
								exportCol.get("colNum"));
						if (value != null) {
							// Попробуем преобразовать значение в число.
							try {
								Integer d = new Integer(value.toString());
								value = d;
							} catch (NumberFormatException ex) {
							try {
								Double d = new Double(value.toString());
								value = d;
							} catch (NumberFormatException e) {
							}
							}
							if (exportCol.get("domainCode") != null
									&& exportCol.get("domainCode").toString()
											.equalsIgnoreCase("Bool")) {
								if (value.toString().equalsIgnoreCase("1.0")
										||value.toString().equalsIgnoreCase("1")
										|| value.toString().equalsIgnoreCase(
												"true"))
									stringWriter.append("Да");// TODO
								// Translate
								else
									stringWriter.append("Нет");// TODO
								// Translate
							} else if (value instanceof String) {
								stringWriter.append(value.toString());
							} else if (exportCol.get("domainValueList") != null
									&& ((ArrayList<TRSDomainValueList>) exportCol
											.get("domainValueList")).size() > 0) {
								ArrayList<TRSDomainValueList> domainValueList = (ArrayList<TRSDomainValueList>) exportCol
										.get("domainValueList");
								for (TRSDomainValueList domainValue : domainValueList)
									if (value.toString().equals(
											domainValue.getDVValue())) {
										stringWriter.append(domainValue
												.getDVName());
										break;
									}
							} else if (value instanceof Date) {
								if (exportCol.get("format") != null) {
									DateFormat dateFormat = new SimpleDateFormat(
											exportCol.get("format").toString());
									stringWriter.append(dateFormat
											.format(value));
								} else {
									stringWriter.append(DateFormat
											.getDateInstance().format(value));
								}
							} else if (value instanceof Number
									&& exportCol.get("format") != null) {
								NumberFormat numberFormat = new DecimalFormat(
										exportCol.get("format").toString());
								stringWriter.append(numberFormat.format(value));
							} else if (value instanceof ROWID) {
								stringWriter.append(((ROWID) value)
										.stringValue());
							} else {
								stringWriter.append(value.toString());
							}
						}
					}
					stringWriter.append("\r\n");
				}
			}
		}

		resp.getWriter().write(stringWriter.toString());
		stringWriter.close();
		resp.getWriter().close();
	}

}
