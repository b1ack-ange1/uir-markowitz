package servlet.tree;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.softlab.rsdh.api.ObjectsFactory;
import ru.softlab.rsdh.api.classes.TSRPSysUserTunes;
import ru.softlab.rsdh.api.classes.TUSRProfile;
import ru.softlab.rsdh.api.rs.TRSFolderList;
import ru.softlab.rsdh.api.rs.TRSShortcutList;
import servlet.RsdhServlet;
import utils.Constants;
import utils.Utils;
import filter.ProfileFilter;

@SuppressWarnings("serial")
public class TreeData extends RsdhServlet {
	@SuppressWarnings("unchecked")
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		JSONObject jRequestData = jRequest.getJSONObject("data");

		JSONArray jData = new JSONArray();

		TUSRProfile profile = null;
		if (getSession().getAttribute(ProfileFilter.sessionProfileId) != null) {
			try {
				profile = (TUSRProfile) ObjectsFactory.createObjById(
						"TUSRProfile", new BigDecimal(getSession()
								.getAttribute(ProfileFilter.sessionProfileId)
								.toString()), false);
			} catch (SQLException e) {
				LOGGER.log(Level.WARNING, this.getClass().getName(), e);
			}
		}

		if (!jRequestData.has("ParentId")
				|| jRequestData.get("ParentId") == JSONObject.NULL) {
			JSONObject jResponse = new JSONObject();

			jResponse.put("status", "0");
			jResponse.put("startRow", "0");
			jResponse.put("endRow", "1");
			jResponse.put("totalRows", "2");

			JSONObject jRecord;
			try {
				if (profile != null && profile.isShowPublicFolders()) {
					jRecord = new JSONObject();
					jRecord.put("Name", "Общие каталоги");// TODO Translate
					jRecord.put("Id", "1");
					jData.put(jRecord);
				}
			} catch (SQLException e) {
				LOGGER.log(Level.WARNING, this.getClass().getName(), e);
			}
			try {

				if (profile != null && profile.isShowPrivateFolders()) {
					jRecord = new JSONObject();
					jRecord.put("Name", getSession().getAttribute("username"));
					jRecord.put("Id", "2");
					jData.put(jRecord);
				}
			} catch (SQLException e) {
				LOGGER.log(Level.WARNING, this.getClass().getName(), e);
			}

			jResponse.put("data", jData);
			return jResponse;
		}

		double parentId = jRequestData.getDouble("ParentId");

		TSRPSysUserTunes sysUserTunes = null;
		int counter = 0;
		try {
			sysUserTunes = (TSRPSysUserTunes) ObjectsFactory.getObjectByCode(
					"TSRPSysUserTunes", "SysUserTunes");
		} catch (SQLException e) {
			return proceedError(e.getLocalizedMessage());
		}

		try {
			if (parentId == 1 && profile != null
					&& profile.getRootFolderId() != null) {
				parentId = profile.getRootFolderId().doubleValue();
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, this.getClass().getName(), e);
		}

		if (parentId == 1) {// General
			try {
				ArrayList<TRSFolderList> generalFolderList = sysUserTunes
						.getGeneralFolderList(null, false);
				for (TRSFolderList folder : generalFolderList) {
					if (folder.getSupFolderId() == null) {
						JSONObject jRecord = new JSONObject();
						jRecord.put("Name", folder.getFolderName());
						jRecord.put("Id", folder.getFolderId());
						jRecord.put("ParentId", parentId);
						jData.put(jRecord);
						counter++;
					}
				}
			} catch (SQLException e) {
				return proceedError(e.getLocalizedMessage());
			}
		} else if (parentId == 2) {// Current User
			try {
				ArrayList<TRSFolderList> userFolderList = sysUserTunes
						.getCurrentUserFolderList(null, false);
				for (TRSFolderList folder : userFolderList) {
					if (folder.getSupFolderId() == null) {
						JSONObject jRecord = new JSONObject();
						jRecord.put("Name", folder.getFolderName());
						jRecord.put("Id", folder.getFolderId());
						jRecord.put("ParentId", parentId);
						jData.put(jRecord);
						counter++;
					}
				}
			} catch (SQLException e) {
				return proceedError(e.getLocalizedMessage());
			}
		} else {
			try {
				ArrayList<TRSFolderList> generalFolderList = sysUserTunes
						.getGeneralFolderList(null, false);
				ArrayList<TRSFolderList> userFolderList = sysUserTunes
						.getCurrentUserFolderList(null, false);

				ArrayList<TRSShortcutList> shortcutList = sysUserTunes
						.getGeneralShortcutList(new BigDecimal(parentId), null);
				for (TRSShortcutList shortcut : shortcutList) {
					if (Constants.realizedClasses.contains(shortcut
							.getUserObjectClassCode())) {
						JSONObject jRecord = new JSONObject();
						jRecord.put("Name", shortcut.getShortcutName());
						jRecord.put("Id", shortcut.getShortcutId()
								.doubleValue());
						jRecord.put("ParentId", parentId);
						jRecord.put("UserObjectId", shortcut.getUserObjectId()
								.doubleValue());
						jRecord.put("UserObjectClassCode",
								shortcut.getUserObjectClassCode());
						jRecord.put("BestAbstractUserObjectClassCode", Utils
								.getBestAbstractClassCode(shortcut
										.getUserObjectClassCode()));
						jRecord.put("IsImmediate", shortcut.isImmediateObj());
						jRecord.put(
								"icon",
								"classes/SRV."
										+ shortcut.getUserObjectClassCode()
										+ ".gif");
						jRecord.put("isFolder", false);
						jData.put(jRecord);
						counter++;
					}
				}

				for (TRSFolderList folder : generalFolderList) {
					if (folder.getSupFolderId() != null
							&& parentId == folder.getSupFolderId()
									.doubleValue()) {
						JSONObject jRecord = new JSONObject();
						jRecord.put("Name", folder.getFolderName());
						jRecord.put("Id", folder.getFolderId());
						jRecord.put("ParentId", parentId);
						jData.put(jRecord);
						counter++;
					}
				}

				shortcutList = sysUserTunes.getCurrentUserShortcutList(
						new BigDecimal(parentId), null);
				for (TRSShortcutList shortcut : shortcutList) {
					if (Constants.realizedClasses.contains(shortcut
							.getUserObjectClassCode())) {
						JSONObject jRecord = new JSONObject();
						jRecord.put("Name", shortcut.getShortcutName());
						jRecord.put("Id", shortcut.getShortcutId()
								.doubleValue());
						jRecord.put("ParentId", parentId);
						jRecord.put("UserObjectId", shortcut.getUserObjectId()
								.doubleValue());
						jRecord.put("UserObjectClassCode",
								shortcut.getUserObjectClassCode());
						jRecord.put("BestAbstractUserObjectClassCode", Utils
								.getBestAbstractClassCode(shortcut
										.getUserObjectClassCode()));
						jRecord.put("IsImmediate", shortcut.isImmediateObj());
						jRecord.put(
								"icon",
								"classes/SRV."
										+ shortcut.getUserObjectClassCode()
										+ ".gif");
						jRecord.put("isFolder", false);
						jData.put(jRecord);
						counter++;
					}
				}

				for (TRSFolderList folder : userFolderList) {
					if (folder.getSupFolderId() != null
							&& parentId == folder.getSupFolderId()
									.doubleValue()) {
						JSONObject jRecord = new JSONObject();
						jRecord.put("Name", folder.getFolderName());
						jRecord.put("Id", folder.getFolderId());
						jRecord.put("ParentId", parentId);
						jData.put(jRecord);
						counter++;
					}
				}
			} catch (SQLException e) {
				return proceedError(e.getLocalizedMessage());
			}
		}

		JSONObject jResponse = new JSONObject();
		jResponse.put("data", jData);
		jResponse.put("status", 0);
		jResponse.put("startRow", 0);
		jResponse.put("endRow", (counter - 1));
		jResponse.put("totalRows", counter);
		return jResponse;
	}
}
