package com.lise.markowitz.client.db.datasources;

import com.google.gwt.json.client.JSONObject;
import com.lise.markowitz.client.Configuration;
import com.lise.markowitz.client.utils.Logger;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.OperationBinding;
import com.smartgwt.client.data.RestDataSource;
import com.smartgwt.client.types.DSDataFormat;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.DSProtocol;
import com.smartgwt.client.util.SC;

/**
 * Базовый класс реализующий взаимодействие с сервером по протоколу JSON.<br/>
 * <br/>
 * Передача дополнительных параметров серверу должна реализовываться через
 * customData:<br/>
 * someDataSource.getCustomData().put(key, value);
 * 
 */
public class JSONDataSource extends RestDataSource {
	private JSONObject customData = new JSONObject();

	public JSONDataSource() {
		super();

		setDataFormat(DSDataFormat.JSON);

		OperationBinding fetch = new OperationBinding();
		fetch.setOperationType(DSOperationType.FETCH);
		fetch.setDataProtocol(DSProtocol.POSTMESSAGE);

		OperationBinding add = new OperationBinding();
		add.setOperationType(DSOperationType.ADD);
		add.setDataProtocol(DSProtocol.POSTMESSAGE);

		OperationBinding update = new OperationBinding();
		update.setOperationType(DSOperationType.UPDATE);
		update.setDataProtocol(DSProtocol.POSTMESSAGE);

		OperationBinding remove = new OperationBinding();
		remove.setOperationType(DSOperationType.REMOVE);
		remove.setDataProtocol(DSProtocol.POSTMESSAGE);

		setOperationBindings(fetch, add, update, remove);

		DSRequest request = new DSRequest();
		request.setUseSimpleHttp(true);
		setRequestProperties(request);
	}

	protected String getWebServiceAddr() {
		return Configuration.getWebServiceAddr(true, false);
	}

	public JSONObject getCustomData() {
		return customData;
	}

	protected Object transformRequest(DSRequest dsRequest) {
		Logger.log("JsonDataSource.transformRequest");
		if (dsRequest.getData() == null)
			dsRequest.setData(customData.getJavaScriptObject());
		else {
			JSONObject jData = new JSONObject(dsRequest.getData());
			for (String key : customData.keySet())
				jData.put(key, customData.get(key));
			dsRequest.setData(jData.getJavaScriptObject());
		}
		Logger.log("JsonDataSource.transformRequest end");
		return super.transformRequest(dsRequest);
	}

	public void destroy() {
		Logger.log("JsonDataSource.destroy");
		try {
			customData = null;
		} catch (Exception e) {
			SC.logWarn(e.getLocalizedMessage());
		} finally {
			try {
				super.destroy();
			} catch (Exception e) {
				SC.logWarn(e.getLocalizedMessage());
			}
		}
		Logger.log("JsonDataSource.destroy end");
	}
}

