package servlet.data.export;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
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

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import ru.softlab.rsdh.api.ObjectsFactory;
import ru.softlab.rsdh.api.RsdhConstants;
import ru.softlab.rsdh.api.classes.TBCLDataSet;
import ru.softlab.rsdh.api.classes.TSRPDomain;
import ru.softlab.rsdh.api.classes.TSRPDomainBase;
import ru.softlab.rsdh.api.rs.TRSDSColumnList;
import ru.softlab.rsdh.api.rs.TRSDomainValueList;
import servlet.data.Data;

@SuppressWarnings("serial")
public class ExcelExport extends HttpServlet {
	private final static int MAX_ROW_COUNT = 65534;
	private static final String CONTENT_TYPE = "application/x-msexcel; charset=UTF-8";

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

		// Сортируем
		Data.sortData(data, metaData, req.getParameter("_sortBy"));
		Data.setData(req.getParameter("_dataSource"), data);

		Vector<String> selectedRows = new Vector<String>();
		if (req.getParameter("selectedRows") != null
				&& !req.getParameter("selectedRows").equalsIgnoreCase("null")
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

		HSSFWorkbook workBook = new HSSFWorkbook();

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
						exportCol.put("cellStyle",
								createCellStyle(workBook, dsCol));

						if (dsCol.getDSColumnDomainId() != null) {
							TSRPDomainBase domain = new TSRPDomainBase(
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
		HSSFSheet sheet = createSheet(workBook, exportCols);
		// Данные
		int rowIndex = 0;
		for (int rowNum = 0; rowNum < data.size(); rowNum++) {
			if (selectedRows.size() == 0
					|| selectedRows.contains(data.get(rowNum).get(-1)
							.toString())) {
				if (Data.filter(data.get(rowNum), filter)) {
					if (rowIndex >= MAX_ROW_COUNT) {
						sheet = createSheet(workBook, exportCols);
						rowIndex = 0;
					}
					// Export this row!
					HSSFRow row = sheet.createRow(++rowIndex);
					int colNum = 0;
					for (Map<String, Object> exportCol : exportCols) {
						Object value = data.get(rowNum).get(
								exportCol.get("colNum"));
						if (value != null) {
							HSSFCell cell = row.createCell(colNum);

							// Попробуем преобразовать значение в число.
							try {
								Double d = new Double(value.toString());
								value = d;
							} catch (NumberFormatException e) {
							}

							if (exportCol.get("domainCode") != null
									&& exportCol.get("domainCode").toString()
											.equalsIgnoreCase("Bool")) {
								HSSFRichTextString cellText = null;
								if (value.toString().equalsIgnoreCase("1")
										|| value.toString().equalsIgnoreCase(
												"true"))
									cellText = new HSSFRichTextString("Да");// TODO
								// Translate
								else
									cellText = new HSSFRichTextString("Нет");// TODO
								// Translate
								cell.setCellValue(cellText);
							} else if (value instanceof String) {
								HSSFRichTextString CellText = new HSSFRichTextString(
										value.toString());
								cell.setCellValue(CellText);
							} else if (exportCol.get("domainValueList") != null
									&& ((ArrayList<TRSDomainValueList>) exportCol
											.get("domainValueList")).size() > 0) {
								ArrayList<TRSDomainValueList> domainValueList = (ArrayList<TRSDomainValueList>) exportCol
										.get("domainValueList");
								for (TRSDomainValueList domainValue : domainValueList)
									if (value.toString().equals(
											domainValue.getDVValue())) {
										HSSFRichTextString CellText = new HSSFRichTextString(
												domainValue.getDVName());
										cell.setCellValue(CellText);
										break;
									}
							} else if (value instanceof Number) {
								// Excel, если видит % в формате, автоматом
								// умножает число на 100.
								if (((HSSFCellStyle) exportCol.get("cellStyle"))
										.getDataFormatString().contains("%"))
									cell.setCellValue(((Number) value)
											.doubleValue() / 100);
								else
									cell.setCellValue(((Number) value)
											.doubleValue());
							} else if (value instanceof Date) {
								cell.setCellValue((Date) value);
							} else if (value instanceof ROWID) {
								HSSFRichTextString CellText = new HSSFRichTextString(
										((ROWID) value).stringValue());
								cell.setCellValue(CellText);
							} else {
								HSSFRichTextString CellText = new HSSFRichTextString(
										value.toString());
								cell.setCellValue(CellText);
							}

							cell.setCellStyle((HSSFCellStyle) exportCol
									.get("cellStyle"));
						}
						colNum++;
					}
				}
			}
		}

		for (short colNum = 0; colNum < exportCols.size(); colNum++)
			sheet.autoSizeColumn(colNum);
		workBook.setFirstVisibleTab(0);

		OutputStream out = resp.getOutputStream();
		workBook.write(out);
		out.close();
	}

	private HSSFCellStyle createCellStyle(HSSFWorkbook workBook,
			TRSDSColumnList dsCol) throws SQLException {
		HSSFCellStyle cellStyle = workBook.createCellStyle();
		cellStyle.setWrapText(true);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
		TSRPDomain domain = null;

		if (dsCol.getDSColumnViewAlignment() != null) {
			if (dsCol.getDSColumnViewAlignment().intValue() == RsdhConstants.cnViewAlignmentRight)
				cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
			else if (dsCol.getDSColumnViewAlignment().intValue() == RsdhConstants.cnViewAlignmentCenter)
				cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			else
				cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		} else if (dsCol.getDSColumnDomainId() != null) {
			domain = new TSRPDomain(dsCol.getDSColumnDomainId());
			if (domain.getViewAlignment() != null) {
				if (domain.getViewAlignment().intValue() == RsdhConstants.cnViewAlignmentRight)
					cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
				else if (domain.getViewAlignment().intValue() == RsdhConstants.cnViewAlignmentCenter)
					cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				else
					cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			} else {
				cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			}
		} else {
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		}

		if (dsCol.getDSColumnViewFormat() != null) {
			cellStyle.setDataFormat(workBook.createDataFormat().getFormat(
					dsCol.getDSColumnViewFormat()));
		} else if (dsCol.getDSColumnDomainId() != null) {
			if (domain == null)
				domain = new TSRPDomain(dsCol.getDSColumnDomainId());

			if (domain.getViewFormat() != null) {
				cellStyle.setDataFormat(workBook.createDataFormat().getFormat(
						domain.getViewFormat()));
			}
		}
		return cellStyle;
	}

	private HSSFSheet createSheet(HSSFWorkbook workBook,
			Vector<Map<String, Object>> columns) {
		HSSFSheet sheet = workBook.createSheet("Export sheet - "
				+ workBook.getNumberOfSheets() + 1);

		HSSFRow row = sheet.createRow(0);
		// row.setHeightInPoints((float)12.75);
		HSSFCellStyle style = workBook.createCellStyle();
		HSSFColor color = new HSSFColor.GREY_25_PERCENT();
		style.setFillForegroundColor(color.getIndex());
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setWrapText(true);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);

		int colNum = 0;
		for (Map<String, Object> column : columns) {
			HSSFCell cell = row.createCell(colNum++);
			HSSFRichTextString str = new HSSFRichTextString(column.get(
					"colName").toString());
			cell.setCellValue(str);
			cell.setCellStyle(style);
		}

		return sheet;
	}
}
