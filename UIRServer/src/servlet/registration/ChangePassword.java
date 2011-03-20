package servlet.registration;

import javax.servlet.Servlet;

import org.json.JSONException;
import org.json.JSONObject;

import ru.softlab.rsdh.api.ObjectsFactory;
import ru.softlab.rsdh.api.classes.TSRPSysRep;
import servlet.RsdhServlet;

/**
 * Servlet implementation class ChangePassword
 */
@SuppressWarnings("serial")
public class ChangePassword extends RsdhServlet implements Servlet {
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		JSONObject jRequestData = jRequest.getJSONObject("data");

		try {
			if (jRequestData.has("newPassword")
					&& !jRequestData.getString("newPassword").isEmpty()) {
				TSRPSysRep sysRep = (TSRPSysRep) ObjectsFactory
						.getObjectByCode("TSRPSysRep", "SysRep");

				String newPassword = jRequestData.getString("newPassword");
				sysRep.ModifyPassword(newPassword);

				getSession().setAttribute("password", newPassword);

				JSONObject jResponse = new JSONObject();
				jResponse.put("status", 0);
				return jResponse;
			}
			return proceedError("Не все обязательные параметры заданы.");
		} catch (Exception e) {
			return proceedError(e.getLocalizedMessage());
		}
	}

}
