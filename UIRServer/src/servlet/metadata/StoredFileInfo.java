package servlet.metadata;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import ru.softlab.rsdh.api.classes.TBCLStoredFile;
import servlet.RsdhServlet;

@SuppressWarnings("serial")
public class StoredFileInfo extends RsdhServlet {

	@Override
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		double fileId = jRequest.getDouble("fileId");

		try {
			TBCLStoredFile file = new TBCLStoredFile(new BigDecimal(fileId));
			JSONObject jResponse = new JSONObject();
			jResponse.put("FileName", file.getFileName());
			jResponse.put("status", 0);
			return jResponse;
		} catch (SQLException e) {
			return proceedError(e.getLocalizedMessage());
		}
	}

}
