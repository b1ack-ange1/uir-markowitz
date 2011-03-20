package servlet.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.sql.BLOB;
import ru.softlab.rsdh.api.classes.TBCLStoredFile;

@SuppressWarnings("serial")
public class StoredFile extends HttpServlet {
	protected final void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String fileId = req.getParameter("fileId");

		if (fileId == null) {
			resp.getWriter().write(
					"Не задан обязательный параметр: \"fileId\".");
			resp.getWriter().close();
			return;
		}

		try {
			BLOB[] data = new BLOB[1];

			TBCLStoredFile storedFile = new TBCLStoredFile(new BigDecimal(
					fileId));

			resp.setContentType(MimetypesFileTypeMap.getDefaultFileTypeMap()
					.getContentType(storedFile.getFileName()));

			storedFile.getBLOBData(data);

			if (data[0] == null)
				throw new RuntimeException(
						"Check API!!! Something wrong with it!");

			InputStream buffer_stream = data[0].getBinaryStream();

			byte[] buffer = new byte[(int) data[0].length()];

			buffer_stream.read(buffer);

			buffer_stream.close();

			OutputStream out = resp.getOutputStream();
			out.flush();
			out.write(buffer);
			out.close();
		} catch (Exception e) {
			resp.getWriter().write(e.getLocalizedMessage());
			resp.getWriter().close();
		}
	}
}
