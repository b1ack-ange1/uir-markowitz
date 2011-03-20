package servlet.metadata;

import java.io.OutputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import oracle.sql.BLOB;

import ru.softlab.rsdh.api.ObjectsFactory;
import ru.softlab.rsdh.api.RsdhConnection;
import ru.softlab.rsdh.api.classes.TSRPObject;
import ru.softlab.rsdh.api.classes.TSRPSysUserTunes;
import ru.softlab.rsdh.api.classes.TUSRGridTune;
import ru.softlab.rsdh.api.rs.TRSUSRObjTuneList;
import servlet.RsdhServlet;
import utils.exceptions.ProblemWithAPI;

public class PresentationExistCheck extends RsdhServlet {

	@Override
	protected JSONObject doAction(JSONObject jRequest) throws JSONException {

		String operationType = jRequest.getString("operationType");
		if (operationType.equalsIgnoreCase("update")) {
			return update(jRequest);
		} else if (operationType.equalsIgnoreCase("fetch")) {
			return fetch(jRequest);
		}
		return jRequest;
	}

	private JSONObject update(JSONObject jRequest) throws JSONException {
		JSONObject jRequestData = jRequest.getJSONObject("data").getJSONObject(
				"value");
		double objectId = jRequest.getJSONObject("data").getDouble("objectId");
		JSONObject jResponse = new JSONObject();

		try {
			TSRPSysUserTunes sysUserTunes = null;
			sysUserTunes = (TSRPSysUserTunes) ObjectsFactory.getObjectByCode(
					"TSRPSysUserTunes", "SysUserTunes");

			TSRPObject object = (TSRPObject) ObjectsFactory.createObjById(
					"TSRPObject", BigDecimal.valueOf(objectId));

			Document xmlDocument = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().newDocument();

			xmlDocument.setXmlStandalone(true);
			xmlDocument.setXmlVersion("1.0");
			xmlDocument.setTextContent("UTF-8");

			Element rsdhOptionGrids = (Element) xmlDocument
					.createElement("RSDHOptionGrids");
			xmlDocument.appendChild(rsdhOptionGrids);
			Element grids = (Element) xmlDocument.createElement("Grids");
			rsdhOptionGrids.appendChild(grids);
			Element resultSetDS = (Element) xmlDocument
					.createElement("ResultSetDS");
			grids.appendChild(resultSetDS);
			Element code = (Element) xmlDocument
					.createElement(object.getCode());
			resultSetDS.appendChild(code);

			Element highlights = (Element) xmlDocument
					.createElement("Highlights");
			highlights.setAttribute("Count", "0");
			code.appendChild(highlights);
			Transformer trans = TransformerFactory.newInstance()
					.newTransformer();
			StringWriter sw = new StringWriter();
			trans.transform(new DOMSource(xmlDocument), new StreamResult(sw));

			String out = sw.toString();
			sw.close();

			Blob outBlob = RsdhConnection.getConnection().createBlob();
			OutputStream oStream = outBlob.setBinaryStream(1);
			oStream.write(out.getBytes("utf-8"));
			oStream.close();

			if (jRequestData.getString("access").equals("0"))
				sysUserTunes.addCurUserGridTune(jRequestData.getString("name"),
						jRequestData.getString("comment"),
						BigDecimal.valueOf(objectId), outBlob);
			else
				sysUserTunes.addGeneralGridTune(jRequestData.getString("name"),
						jRequestData.getString("comment"),
						BigDecimal.valueOf(objectId), outBlob);
			jResponse.put("status", 0);

			return jResponse;
		} catch (Exception e) {
			jResponse.put("status", -1);
			jResponse.put("error", e.getLocalizedMessage());

			return jResponse;
		}
	}

	private JSONObject fetch(JSONObject jRequest) throws JSONException {
		JSONObject jRequestData = jRequest.getJSONObject("data");
		double objectId = jRequestData.getDouble("objectId");

		JSONObject jResponse = new JSONObject();
		JSONArray jResponseData = new JSONArray();
		JSONObject jResult = new JSONObject();
		try {

			TSRPSysUserTunes sysUserTunes = null;

			sysUserTunes = (TSRPSysUserTunes) ObjectsFactory.getObjectByCode(
					"TSRPSysUserTunes", "SysUserTunes");

			ArrayList<TRSUSRObjTuneList> gridTuneList = sysUserTunes
					.getGeneralRangeTuneList(BigDecimal.valueOf(objectId),
							new TUSRGridTune(null).getClassName());

			jResponse.put("status", 0);
			jResult.put("size", gridTuneList.size());
			jResponseData.put(jResult);
			jResponse.put("data", jResponseData);

			return jResponse;
		} catch (Exception e) {
			jResponse.put("status", -1);
			jResult.put("error", e.getLocalizedMessage());
			jResponseData.put(jResult);
			jResponse.put("data", jResponseData);
			jResponse.put("data", jResponseData);
			return jResponse;
		}
	}
}
