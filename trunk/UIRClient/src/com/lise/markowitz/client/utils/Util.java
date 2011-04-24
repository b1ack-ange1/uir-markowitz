package com.lise.markowitz.client.utils;

import com.google.gwt.dom.client.Element;
import com.smartgwt.client.widgets.Label;

public class Util {
	public static Integer getTextWidth(String text) {
		Label label = new Label(text);
		label.setAutoWidth();
		label.setWrap(false);
		label.setVisible(false);
		label.draw();
		Integer width = label.getViewportWidth();
		label.destroy();
		return width;
	};

	public native static Boolean evaluateExpression(String expression)
	/*-{
		eval("var rsdhExprResult = " + expression + ";");
		return rsdhExprResult == null || rsdhExprResult === undefined ? null
				: @com.smartgwt.client.util.JSOHelper::toBoolean(Z)(rsdhExprResult);
	}-*/;

	public native static void setElementHeight(Element element, int h)
	/*-{
		element.style.height = h + "px";
	}-*/;

	public native static int getElementHeight(Element element)
	/*-{
		return element.style.height;
	}-*/;
}