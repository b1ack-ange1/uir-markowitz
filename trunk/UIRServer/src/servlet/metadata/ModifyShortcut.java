package servlet.metadata;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import ru.softlab.rsdh.api.ObjectsFactory;
import ru.softlab.rsdh.api.classes.TSRPSysUserTunes;
import ru.softlab.rsdh.api.classes.TUSRShortcut;
import ru.softlab.rsdh.api.rs.TRSTuneList;
import ru.softlab.rsdh.api.rs.TRSUserTuneObjectParamValue;
import servlet.RsdhServlet;

/**
 * Servlet implementation class ModifyShortcut
 */
@SuppressWarnings("serial")
public class ModifyShortcut extends RsdhServlet {

	@Override
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		try {
			TUSRShortcut shortcut = new TUSRShortcut(new BigDecimal(jRequest.getString("shortcutId")));
			TSRPSysUserTunes sysUserTunes = (TSRPSysUserTunes) ObjectsFactory.getObjectByCode(
					"TSRPSysUserTunes", "SysUserTunes");			
			
			String parameterList = "";
			String detailTransitionIds = "";
			
			if(!jRequest.has("paramValues")){
				for (TRSUserTuneObjectParamValue listItem : shortcut.getShortcutParamValueList()){
					if(listItem.getUserObjectParamValue() != null)
						parameterList += "<TPARAM><NAME>" + listItem.getUserObjectParamCode() + "</NAME><VALUE>" + listItem.getUserObjectParamValue() + "</VALUE></TPARAM>";
				}
				parameterList = "<ROW>" + parameterList + "</ROW>";
			} else parameterList = jRequest.getString("paramValues");
			
			if(!jRequest.has("transitionIdList"))
				for (TRSTuneList listItem : shortcut.getDetailTransitionList()){
					detailTransitionIds += listItem.getTuneId().toString() + ";";
				}
			else detailTransitionIds = jRequest.getString("transitionIdList");
			
			try {
				if(shortcut.getUserId() == null)
					sysUserTunes.setPVShortcutGeneral(shortcut.getId(), parameterList, detailTransitionIds);
				else
					sysUserTunes.setPVShortcutAnyUser(shortcut.getId(), parameterList, detailTransitionIds);
			} catch (SQLException sqlerr) {
				if (sqlerr.getMessage().contains("ESIBAccessDenied"))
					sysUserTunes.setPVShortcutCurrentUser(shortcut.getId(), parameterList, detailTransitionIds);
				else
					throw sqlerr;
			}						
			
		} catch (Exception e) {
			return proceedError(e.getLocalizedMessage());
		}
		
		return new JSONObject().put("status", 0);
	}

}
