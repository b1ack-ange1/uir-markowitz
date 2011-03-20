package servlet.publications;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.softlab.rsdh.api.ObjectsFactory;
import ru.softlab.rsdh.api.RsdhConstants;
import ru.softlab.rsdh.api.classes.TPUBReport;
import ru.softlab.rsdh.api.classes.TSRPSysPublish;
import utils.exceptions.ProblemWithAPI;

@SuppressWarnings("serial")
public class Report extends HttpServlet {
	protected static final Logger LOGGER = Logger
			.getLogger("ru.softlab.rsdh.webService");

	private static final String CONTENT_TYPE_EXCEL = "application/x-msexcel";
	private static final String CONTENT_TYPE_FR = "application/x-fastreport";

	protected final void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String objectId = req.getParameter("objectId");

		if (objectId == null) {
			resp.getWriter().write(
					"Не задан обязательный параметр: \"objectId\".");
			resp.getWriter().close();
			return;
		}

		Blob[] data = new Blob[1];

		try {
			TPUBReport report = new TPUBReport(new BigDecimal(objectId));

			if (report != null)
				report.getStoredPublication(
						new BigDecimal(req.getParameter("spId")),
						new String[1], data);

			if (data[0] == null)
				LOGGER.log(Level.WARNING, this.getClass().getName(),
						new ProblemWithAPI());

			InputStream buffer_stream = data[0].getBinaryStream();

			byte[] buffer = new byte[(int) data[0].length()];

			buffer_stream.read(buffer);

			buffer_stream.close();

			if (report.getTypeRepGen().intValue() == RsdhConstants.cnTypeRepGenExcel)
				resp.setContentType(CONTENT_TYPE_EXCEL);
			else
				resp.setContentType(CONTENT_TYPE_FR);

			OutputStream out = resp.getOutputStream();
			out.flush();
			out.write(buffer);
			out.close();

			if (req.getParameter("delete") != null) {
				TSRPSysPublish sysPublish = (TSRPSysPublish) ObjectsFactory
						.getObjectByCode("TSRPSysPublish", "SysPublish");
				sysPublish.deleteReport(report.getCode());
			}
		} catch (Exception e) {
			resp.getWriter().write(e.getLocalizedMessage());
			resp.getWriter().close();
		}
	}
}
