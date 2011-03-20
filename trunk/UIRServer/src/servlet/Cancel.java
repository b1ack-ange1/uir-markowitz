package servlet;

import java.sql.SQLException;

import oracle.jdbc.driver.OracleConnection;

import org.json.JSONException;
import org.json.JSONObject;

import ru.softlab.rsdh.api.RsdhConnection;

@SuppressWarnings("serial")
public class Cancel extends RsdhServlet {
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		try {
			if (RsdhConnection.getConnection() instanceof OracleConnection)
				((OracleConnection) RsdhConnection.getConnection()).cancel();
		} catch (SQLException e) {
			if (!e.getMessage().contains("ORA-01013")) {
				e.printStackTrace();
				return proceedError(e.getLocalizedMessage());
			}
		}
		JSONObject jResponse = new JSONObject();
		jResponse.put("status", 0);
		return jResponse;
	}
}
