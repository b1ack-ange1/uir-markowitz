package servlet.upload;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import ru.softlab.rsdh.api.ObjectsFactory;
import ru.softlab.rsdh.api.RsdhConnection;
import ru.softlab.rsdh.api.classes.TSRPSysRep;

/**
 * Servlet implementation class UploadFile
 */
@SuppressWarnings("serial")
public class UploadFile extends HttpServlet {
	@SuppressWarnings("unchecked")
	protected final void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8");
		req.setCharacterEncoding("UTF-8");

		try {
			String respString = "<script>";
			if (ServletFileUpload.isMultipartContent(req)) {
				ServletFileUpload servletFileUpload = new ServletFileUpload(
						new DiskFileItemFactory());
				servletFileUpload.setHeaderEncoding("UTF8");

				respString += "if (parent && parent.uploadChooseWindowCompleteHandler) {";

				try {
					List fileItemsList = servletFileUpload.parseRequest(req);

					TSRPSysRep sysRep = (TSRPSysRep) ObjectsFactory
							.getObjectByCode("TSRPSysRep", "SysRep");

					Iterator it = fileItemsList.iterator();
					while (it.hasNext()) {
						FileItem fileItem = (FileItem) it.next();
						if (!fileItem.isFormField() && fileItem.getSize() > 0)
							try {
								Blob blob = RsdhConnection.getConnection()
										.createBlob();
								OutputStream oStream = blob.setBinaryStream(1);

								InputStream iStream = fileItem.getInputStream();
								byte[] buf = new byte[1024];
								int length = iStream.read(buf);
								while (length != -1) {
									oStream.write(buf, 0, length);
									length = iStream.read(buf);
								}
								oStream.close();
								String fileName = fileItem.getName();
								if (fileName.contains("/"))
									fileName = fileName.substring(fileName
											.lastIndexOf("/") + 1);
								if (fileName.contains("\\"))
									fileName = fileName.substring(fileName
											.lastIndexOf("\\") + 1);
								BigDecimal idFile = sysRep.addStoredFile(
										fileItem.getContentType(), "From ICL",
										fileName,
										new Timestamp(new Date().getTime()),
										new Timestamp(new Date().getTime()),
										blob, null);

								respString += "parent.uploadChooseWindowCompleteHandler(\""
										+ new DecimalFormat("#").format(idFile
												.floatValue())
										+ "\",\""
										+ fileName + "\");";
							} catch (SQLException e) {
								e.printStackTrace();
								respString += "if (parent && parent.showAppError) { parent.showAppError(\""
										+ e.getLocalizedMessage() + "\"); }";
							}
					}
				} catch (Exception e) {
					e.printStackTrace();
					respString += "if (parent && parent.showAppError) { parent.showAppError(\""
							+ e.getLocalizedMessage() + "\"); }";
				} finally {
					respString += "}";
				}
			} else {
				respString += "if (parent && parent.showAppError) { parent.showAppError(\"Не верное кодирование формы.\"); }";
			}
			respString += "</script>";
			resp.getWriter().write(respString);
			resp.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
