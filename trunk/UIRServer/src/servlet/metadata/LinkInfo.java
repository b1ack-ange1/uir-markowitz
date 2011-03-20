package servlet.metadata;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import ru.softlab.rsdh.api.ObjectsFactory;
import ru.softlab.rsdh.api.classes.TBCLUserObject;
import ru.softlab.rsdh.api.classes.TObjectType;
import ru.softlab.rsdh.api.classes.TUSRShortcut;
import ru.softlab.rsdh.api.rs.TRSUserTuneObjectParamValue;
import servlet.RsdhServlet;
import utils.Utils;

@SuppressWarnings("serial")
public class LinkInfo extends RsdhServlet {
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		double usrObjLinkId = jRequest.getDouble("id");
		String usrObjLinkClassCode = jRequest.getString("classCode");

		try {
			TObjectType object = ObjectsFactory.createObjById(
					usrObjLinkClassCode, new BigDecimal(usrObjLinkId));

			JSONObject jResponse = new JSONObject();

			if (object instanceof TUSRShortcut) {
				TUSRShortcut shortcut = (TUSRShortcut) object;

				jResponse.put("UserObjectId", shortcut.getUserObjectId()
						.doubleValue());
				jResponse.put("Name", shortcut.getUserObject().getName());
				jResponse.put("ClassCode", shortcut.getUserObject()
						.getClassName());
				jResponse.put("BestAbstractUserObjectClassCode", Utils
						.getBestAbstractClassCode(shortcut.getUserObject()));
				String paramValues = "<ROW>";
				for (TRSUserTuneObjectParamValue param : shortcut
						.getShortcutParamValueList())
					paramValues += "<TPARAM><NAME>"
							+ param.getUserObjectParamCode()
							+ "</NAME><VALUE>"
							+ (param.getUserObjectParamValue() != null ? param
									.getUserObjectParamValue() : "")
							+ "</VALUE></TPARAM>";
				paramValues += "</ROW>";
				jResponse.put("ParamValues", paramValues);
			} else if (object instanceof TBCLUserObject) {
				TBCLUserObject userObject = (TBCLUserObject) object;

				jResponse.put("UserObjectId", userObject.getId().doubleValue());
				jResponse.put("Name", userObject.getName());
				jResponse.put("ClassCode", userObject.getClassName());
				jResponse.put("BestAbstractUserObjectClassCode", Utils
						.getBestAbstractClassCode(userObject));
				jResponse.put("ParamValues", "<ROW></ROW>");
			} else {
				// TODO Translate
				return proceedError("Не поддерживаемый тип объекта.");
			}
			jResponse.put("status", 0);
			return jResponse;
		} catch (SQLException e) {
			return proceedError(e.getLocalizedMessage());
		}
	}
}
