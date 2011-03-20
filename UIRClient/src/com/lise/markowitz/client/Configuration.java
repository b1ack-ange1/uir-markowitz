package com.lise.markowitz.client;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.lise.markowitz.client.utils.Logger;
import com.smartgwt.client.util.SC;

public class Configuration {
	private native static String getNativeConfiguration(String tune)
	/*-{
		return $wnd.rsdhConfiguration[tune];
	}-*/;

	public static String getWebServiceAddr(boolean isSecure, boolean isNative) {
		return getNativeConfiguration("rsdhWebServiceAddr")
				+ (isSecure ? "/secure" : "") + (isNative ? "/native" : "/sc")
				+ "/";
	}

	public final static int reportCheckDelay = 5000;
	public final static int firstDataCheckDelay = 100;

	public final static int NEED_PREFETCH = -301;

	public final static String UsrObjLinkIdParamName = "Id";
	public final static String UsrObjLinkDBParamName = "LogonDatabase";
	public final static String UsrObjLinkClassCodeParamName = "ClassCode";

	public final static String moduleCode = "RSDH-ThinClient-ACenter";

	public final static boolean debugEnabled = true;

	// Все не final атрибуты должны браться с сервера при загрузке приложения.
	private static JSONObject generalTuneData;

	public static JSONValue getGeneralTune(String tune) {
		JSONValue value = null;
		if (generalTuneData != null)
			value = generalTuneData.get(tune);
		if (value == null) {
			String nativeConfig = getNativeConfiguration(tune);
			if (nativeConfig.equalsIgnoreCase("true")
					|| nativeConfig.equalsIgnoreCase("false"))
				value = JSONBoolean.getInstance(nativeConfig
						.equalsIgnoreCase("true"));
			else {
				try {
					value = new JSONNumber(NumberFormat.getDecimalFormat()
							.parse(nativeConfig));
				} catch (Exception e) {
					value = new JSONString(nativeConfig);
				}
			}
		}

		SC.logWarn("getGeneralTune(" + tune + ") = "
				+ value.getClass().getName());
		return value;
	}

	// public static boolean useWindowsStack = true;
	// public static boolean autoPinWindows = true;

	// Подсистема хранения настроек объектов.
	private final static Map<String, JSONObject> tuneCache = new HashMap<String, JSONObject>();

	private static JSONObject getCachedTune(String tuneCode, Float objectId) {
		Logger.log("Configuration.getCachedTune");
		return tuneCache.get(tuneCode + (objectId != null ? objectId : ""));
	}

	private static void cacheTune(String tuneCode, Float objectId,
			JSONObject tuneData) {
		Logger.log("Configuration.cacheTune");
		tuneCache.put(tuneCode + (objectId != null ? objectId : ""), tuneData);
	}

	public static void init() {
		
	
	}

}
