package com.lise.markowitz.client.utils;

import com.google.gwt.core.client.JavaScriptObject;
import com.lise.markowitz.client.Configuration;
import com.smartgwt.client.util.SC;

public class Logger {
	public static void log(String msg) {
		if (Configuration.debugEnabled)
			SC.logWarn(msg, "LISE");
	}

	public static void echoAll(JavaScriptObject value) {
		if (Configuration.debugEnabled)
			SC.logEchoAll(value, "LISE");
	}
}
