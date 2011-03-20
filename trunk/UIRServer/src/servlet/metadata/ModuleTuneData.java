package servlet.metadata;

import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.util.logging.Level;

import org.json.JSONException;
import org.json.JSONObject;

import ru.softlab.rsdh.api.ObjectsFactory;
import ru.softlab.rsdh.api.RsdhConnection;
import ru.softlab.rsdh.api.classes.TSRPSysUserTunes;
import servlet.RsdhServlet;

public class ModuleTuneData extends RsdhServlet {
	@Override
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		String operationType = jRequest.getString("operationType");

		JSONObject jData = null;
		try {
			TSRPSysUserTunes sysUserTunes = (TSRPSysUserTunes) ObjectsFactory
					.getObjectByCode("TSRPSysUserTunes", "SysUserTunes");
			if (operationType.equalsIgnoreCase("save")
					&& jRequest.has("tuneData")) {
				Blob tuneData = RsdhConnection.getConnection().createBlob();
				OutputStream oStream = tuneData.setBinaryStream(1);
				oStream.write(jRequest.getString("tuneData").getBytes());
				oStream.close();

				sysUserTunes.saveModuleTuneData(
						jRequest.getString("moduleCode"),
						jRequest.getString("tuneCode"),
						jRequest.has("objectId") ? BigDecimal.valueOf(jRequest
								.getLong("objectId")) : null, tuneData);
			}
			if (operationType.equalsIgnoreCase("get")) {
				boolean[] dataIsNull = new boolean[1];
				Blob[] tuneData = new Blob[1];

				sysUserTunes.getModuleTuneData(
						jRequest.getString("moduleCode"),
						jRequest.getString("tuneCode"),
						jRequest.has("objectId") ? BigDecimal.valueOf(jRequest
								.getLong("objectId")) : null, dataIsNull,
						tuneData);

				if (!dataIsNull[0]) {
					byte[] bytes = new byte[1024];
					String strTuneData = new String();
					InputStream iStream = tuneData[0].getBinaryStream();
					int length = iStream.read(bytes);
					while (length != -1) {
						strTuneData += new String(bytes, 0, length);
						length = iStream.read(bytes);
					}

					jData = new JSONObject(strTuneData);
				}
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, this.getClass().getName(), e);
			return proceedError(e.getLocalizedMessage());
		}
		return new JSONObject().put("data", jData).put("status", 0);
	}
}
