package com.lise.markowitz.client.db.datasources;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.lise.markowitz.client.Configuration;
import com.smartgwt.client.data.fields.DataSourceFloatField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class ValueTickerDataSource extends JSONDataSource {
	private static ValueTickerDataSource valueTickerDataSource;

	public static ValueTickerDataSource getInstance(long from, long till,
			ArrayList<Integer> tickerList) {
		if (valueTickerDataSource == null)
			valueTickerDataSource = new ValueTickerDataSource(from, till,
					tickerList);
		return valueTickerDataSource;
	}

	protected ValueTickerDataSource(long from, long till,
			ArrayList<Integer> tickerList) {
		super();

		setID("Portfolio");

		DataSourceTextField tickerIdField = new DataSourceTextField("id");
		DataSourceIntegerField nameField = new DataSourceIntegerField("date");
		DataSourceFloatField weightField = new DataSourceFloatField("value");
		weightField.setCanEdit(true);

		setFields(nameField, tickerIdField, weightField);

		getCustomData().put("from", new JSONNumber(from));
		getCustomData().put("till", new JSONNumber(till));

		JSONArray arr = new JSONArray();
		for (int i = 0; i < tickerList.size(); i++)
			arr.set(i, new JSONObject().put("id",
					new JSONNumber(tickerList.get(i))));

		getCustomData().put("list", arr);

		setDataURL(Configuration.getWebServiceAddr(true, false) + "ValueList");
	}
}
