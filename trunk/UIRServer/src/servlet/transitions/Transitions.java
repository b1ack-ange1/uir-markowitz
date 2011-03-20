package servlet.transitions;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.softlab.rsdh.api.ObjectsFactory;
import ru.softlab.rsdh.api.classes.TBCLUserObject;
import ru.softlab.rsdh.api.classes.TObjectType;
import ru.softlab.rsdh.api.classes.TSRPSysUserTunes;
import ru.softlab.rsdh.api.classes.TUSRTransition;
import ru.softlab.rsdh.api.classes.TWFLDocument;
import ru.softlab.rsdh.api.classes.TWFLProcessScheme;
import ru.softlab.rsdh.api.rs.TRSTransitionList;
import servlet.RsdhServlet;
import utils.Constants;
import utils.Utils;

@SuppressWarnings("serial")
public class Transitions extends RsdhServlet {
	private ThreadLocal<HashMap<String, Object>> nodes = new ThreadLocal<HashMap<String, Object>>();
	private ThreadLocal<Integer> pathId = new ThreadLocal<Integer>();

	@Override
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {
		JSONObject jRequestData = jRequest.getJSONObject("data");

		double objectId = jRequestData.getDouble("objectId");
		String objectCode = null;
		String parentCode = null;
		if (jRequestData.has("objectCode")
				&& !jRequestData.isNull("objectCode"))
			objectCode = jRequestData.getString("objectCode");
		try {
			if (objectCode == null || objectCode.isEmpty()) {
				TBCLUserObject userObject = (TBCLUserObject) ObjectsFactory
						.createObjById("TBCLUserObject", new BigDecimal(
								objectId));

				objectCode = userObject.getCode();
			}

			TObjectType objectInstance = ObjectsFactory.getObjectByCode(
					"TBCLUserObject", objectCode);
			// Если объект является документом
			// получаем код родительской схемы документа
			if (objectInstance instanceof TWFLDocument) {
				TWFLProcessScheme processScheme = (TWFLProcessScheme) ObjectsFactory
						.createObjById("TWFLProcessScheme",
								((TWFLDocument) objectInstance)
										.getProcessSchemeId());
				parentCode = processScheme.getCode();
			}

			TSRPSysUserTunes sysUserTunes = (TSRPSysUserTunes) ObjectsFactory
					.getObjectByCode("TSRPSysUserTunes", "SysUserTunes");

			nodes.set(new HashMap<String, Object>());
			pathId.set(0);

			JSONArray jData = new JSONArray();
			ArrayList<TRSTransitionList> trList = null;
			ArrayList<TRSTransitionList> trParentList = null;
			try {
				if (parentCode != null && !parentCode.isEmpty()) {
					trParentList = sysUserTunes.getCurrentUserTransitionList(
							parentCode, null);
					transformTransitionList(jData, trParentList, false);
				}
				trList = sysUserTunes.getCurrentUserTransitionList(objectCode,
						null);
				transformTransitionList(jData, trList, false);
			} catch (SQLException e) {
			}
			try {
				if (parentCode != null && !parentCode.isEmpty()) {
					trParentList = sysUserTunes.getGeneralTransitionList(
							parentCode, null);
					transformTransitionList(jData, trParentList, true);
				}
				trList = sysUserTunes
						.getGeneralTransitionList(objectCode, null);
				transformTransitionList(jData, trList, true);
			} catch (SQLException e) {
			}

			JSONObject jResponse = new JSONObject();
			jResponse.put("data", jData);
			jResponse.put("status", 0);
			return jResponse;
		} catch (SQLException e) {
			return proceedError(e.getLocalizedMessage());
		}
	}

	@SuppressWarnings("unchecked")
	private int getPathId(JSONArray jData, String[] path, int level,
			HashMap<String, Object> parent) throws JSONException {
		if (parent.containsKey(path[level])) {
			if (level >= path.length - 1)
				return (Integer) ((HashMap<String, Object>) parent
						.get(path[level])).get("FolderId");
			else
				return getPathId(jData, path, level + 1,
						(HashMap<String, Object>) parent.get(path[level]));
		} else {
			HashMap<String, Object> newNode = new HashMap<String, Object>();
			pathId.set(pathId.get() - 1);
			newNode.put("FolderId", pathId.get());
			parent.put(path[level], newNode);
			JSONObject jRecord = new JSONObject();
			jRecord.put("Id", pathId.get());
			jRecord.put("ParentId", parent.get("FolderId"));
			jRecord.put("Name", path[level]);
			jRecord.put("isFolder", true);
			jData.put(jRecord);
			if (level >= path.length - 1)
				return pathId.get();
			else
				return getPathId(jData, path, level + 1, newNode);
		}
	}

	private void transformTransitionList(JSONArray jData,
			ArrayList<TRSTransitionList> trList, boolean general)
			throws JSONException, SQLException {
		for (TRSTransitionList transition : trList) {
			if (Constants.realizedClasses.contains(transition
					.getToUserObjectClassCode())) {
				JSONObject jRecord = new JSONObject();
				jRecord.put("Id", transition.getTransitionId());
				if (transition.getShortcutNote() != null
						&& transition.getShortcutNote().toLowerCase()
								.contains("<path>")
						&& transition.getShortcutNote().toLowerCase()
								.contains("</path>")) {
					String path = transition.getShortcutNote().substring(
							transition.getShortcutNote().toLowerCase()
									.indexOf("<path>") + 6,
							transition.getShortcutNote().toLowerCase()
									.indexOf("</path>"));

					jRecord.put(
							"ParentId",
							getPathId(jData, path.trim().split("\\\\"), 0,
									nodes.get()));
				} else {
					jRecord.put("ParentId", 0);
				}
				jRecord.put("isFolder", false);
				jRecord.put("Name", transition.getShortcutName());

				jRecord.put("ToUserObjectId", transition.getToUserObjectId());
				jRecord.put("UserObjectClassCode",
						transition.getToUserObjectClassCode());
				jRecord.put("BestAbstractUserObjectClassCode", Utils
						.getBestAbstractClassCode(ObjectsFactory.createObjById(
								"TBCLUserObject",
								transition.getToUserObjectId())));
				jRecord.put("General", general);

				TUSRTransition trans = (TUSRTransition) transition.getItem();
				jRecord.put("ShowParamInsp", trans.isShowParamInsp());
				if (trans.isNeedRequest())
					jRecord.put(
							"RequestText",
							(trans.getRequestText() == null || trans
									.getRequestText().isEmpty()) ? "Вы действительно хотите перейти к выбранному объекту?"
									: trans.getRequestText());
				jRecord.put("Kind", trans.getKind());
				jData.put(jRecord);
			}
		}
	}
}
