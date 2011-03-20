package servlet.publications;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import ru.softlab.rsdh.api.ObjectsFactory;
import ru.softlab.rsdh.api.RsdhConstants;
import ru.softlab.rsdh.api.classes.TPUBPublication;
import ru.softlab.rsdh.api.classes.TPUBReport;
import ru.softlab.rsdh.api.classes.TPUBReportTemplate;
import ru.softlab.rsdh.api.classes.TSRPDomainBase;
import ru.softlab.rsdh.api.classes.TSRPSysPublish;
import ru.softlab.rsdh.api.rs.TRSDomainValueList;
import ru.softlab.rsdh.api.rs.TRSQOLogItemRowList;
import ru.softlab.rsdh.api.rs.TRSStoredPublicationList;
import ru.softlab.rsdh.api.rs.TRSTempPublicationList;
import servlet.RsdhServlet;

@SuppressWarnings("serial")
public class ReportTemplate extends RsdhServlet {
	ThreadLocal<Double> objectId = new ThreadLocal<Double>();
	ThreadLocal<Double> reportId = new ThreadLocal<Double>();

	@Override
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		objectId.set(jRequest.getDouble("objectId"));

		String operationType = jRequest.getString("operationType");

		try {
			if (operationType.equalsIgnoreCase("create"))
				return create(jRequest);
			else if (operationType.equalsIgnoreCase("check"))
				return check(jRequest);
			else if (operationType.equalsIgnoreCase("delete"))
				return delete(jRequest);
			// Проверка наличия шаблона данных
			else if (operationType.equalsIgnoreCase("test"))
				return test(jRequest);

			return proceedError("Не задан обязательный параметр: \"operationType\"");
		} catch (SQLException e) {
			return proceedError(e.getLocalizedMessage());
		}
	}

	private JSONObject test(JSONObject jRequest) throws SQLException,
			JSONException {
		BigDecimal objId = new BigDecimal(objectId.get());
		TPUBReportTemplate reportTemplate = new TPUBReportTemplate(objId);

		// Проверяе существование обоих типов шаблонов (ExcelReport и
		// FastReport)
		boolean haveTypeRepGenExcel = reportTemplate
				.testTemplateData(BigDecimal
						.valueOf(RsdhConstants.cnTypeRepGenExcel));
		boolean haveTypeRepGenFastReport = reportTemplate
				.testTemplateData(BigDecimal
						.valueOf(RsdhConstants.cnTypeRepGenFastReport));

		JSONObject jResponse = new JSONObject();
		// Если есть хотябы один шаблон возвращаем true
		if (haveTypeRepGenExcel || haveTypeRepGenFastReport) {
			jResponse.put("data", new JSONObject().put("templateExists", true));
		} else {
			jResponse
					.put("data", new JSONObject().put("templateExists", false));
		}
		jResponse.put("status", 0);
		return jResponse;
	}

	private JSONObject delete(JSONObject jRequest) throws SQLException,
			JSONException {
		TSRPSysPublish sysPublish = (TSRPSysPublish) ObjectsFactory
				.getObjectByCode("TSRPSysPublish", "SysPublish");
		TPUBReport report = new TPUBReport(new BigDecimal(objectId.get()));

		sysPublish.deleteReport(report.getCode());

		JSONObject jResponse = new JSONObject();
		jResponse.put("status", 0);
		return jResponse;
	}

	private JSONObject check(JSONObject jRequest) throws SQLException,
			JSONException {
		BigDecimal objId = new BigDecimal(objectId.get());
		TPUBReport report = new TPUBReport(objId);
		TPUBReportTemplate reportTemplate = new TPUBReportTemplate(
				report.getPublicationTemplateId());
		ArrayList<TRSTempPublicationList> tempRepList = reportTemplate
				.getTemporaryReportList();

		boolean generated = false;
		int status = 0;

		if (tempRepList != null)
			for (TRSTempPublicationList tempPublication : tempRepList)
				if (tempPublication.getObjectId().equals(objId)
						&& tempPublication.getStatus() != null) {
					status = tempPublication.getStatus().intValue();
					if (status == RsdhConstants.cnQUETAObjStatusOK)
						generated = true;
					if (status == RsdhConstants.cnQUETAObjStatusError) {
						for (TRSQOLogItemRowList logRow : ((TPUBPublication) tempPublication
								.getItem()).getQOLogItemRowList(new BigDecimal(
								1), null)) {
							return proceedError("Ошибка при выпуске отчета: "
									+ logRow.getQOLogItemRowText() + ".");
						}
						return proceedError("Ошибка при выпуске отчета.");
					}
					if (status == RsdhConstants.cnQUETAObjStatusSkipped)
						return proceedError("Пропущено.");
				}

		ArrayList<TRSStoredPublicationList> storedPublicationList = report
				.getStoredPublicationList();

		JSONObject jResponse = new JSONObject();
		if (generated && storedPublicationList.size() > 0) {
			jResponse.put("status", 0);
			jResponse.put("spId", storedPublicationList.get(0).getSPId());
		} else {
			TSRPDomainBase domain = (TSRPDomainBase) ObjectsFactory
					.getObjectByCode("TSRPDomainBase", "QUETAObjStatus");
			for (TRSDomainValueList value : domain.getValueList())
				if (Integer.valueOf(value.getDVValue()).equals(status)) {
					JSONObject jRow = new JSONObject();
					jRow.put("Id", Double.valueOf(value.getDVValue()));
					jRow.put("Name", value.getDVName());
					jResponse.put("data", jRow);
					break;
				}
			jResponse.put("status", 1);
		}
		return jResponse;
	}

	private JSONObject create(JSONObject jRequest) throws SQLException,
			JSONException {
		TPUBReportTemplate reportTemplate = new TPUBReportTemplate(
				new BigDecimal(objectId.get()));

		Random rand = new Random();
		String repCode = "tmpCode" + Math.abs(rand.nextInt());
		BigDecimal tempPubId = null;

		boolean dubl = true;
		while (dubl) {
			try {
				tempPubId = reportTemplate.addTempPublicationInQueue(
						jRequest.getString("objectParams"), repCode, false,
						false,
						BigDecimal.valueOf(RsdhConstants.cnTypeRepGenExcel));
				dubl = false;
			} catch (SQLException sqlEx) {
				if (sqlEx.getMessage().contains("EObjectExists")) {
					repCode = reportTemplate.getCode()
							+ Math.abs(rand.nextInt());
				} else
					throw sqlEx;
			}
		}

		JSONObject jResponse = new JSONObject();
		jResponse.put("tempPubId", tempPubId);
		jResponse.put("status", 0);
		return jResponse;
	}

}
