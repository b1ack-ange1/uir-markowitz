package com.lise.markowitz.client.db.datasources;

import com.lise.markowitz.client.Configuration;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class OptionTickerListDataSource extends JSONDataSource {
	private static OptionTickerListDataSource optionTickerListDataSource;

	public static OptionTickerListDataSource getInstance() {
		if (optionTickerListDataSource == null)
			optionTickerListDataSource = new OptionTickerListDataSource();
		return optionTickerListDataSource;
	}

	protected OptionTickerListDataSource() {
		super();

		setID("OptionTickerList");

		DataSourceTextField nameField = new DataSourceTextField("name");
		DataSourceIntegerField cacheIdField = new DataSourceIntegerField(
				"cacheId");

		cacheIdField.setPrimaryKey(true);
		
		setFields(nameField, cacheIdField);

		setDataURL(Configuration.getWebServiceAddr(false, false) + "TickerList");

	}
}
