package com.lise.markowitz.client.db.datasources;

import com.lise.markowitz.client.Configuration;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class TickerListDataSource extends JSONDataSource {
	private static TickerListDataSource profilesListDataSource;

	public static TickerListDataSource getInstance() {
		if (profilesListDataSource == null)
			profilesListDataSource = new TickerListDataSource();
		return profilesListDataSource;
	}

	protected TickerListDataSource() {
		super();

		setID("TickerList");

		DataSourceTextField idField = new DataSourceTextField("id");
		DataSourceTextField nameField = new DataSourceTextField("name");
		DataSourceIntegerField cacheIdField = new DataSourceIntegerField(
				"cacheId");

		idField.setPrimaryKey(true);
		setTitleField("Name");

		setFields(nameField, idField, cacheIdField);

		setDataURL(Configuration.getWebServiceAddr(false, false) + "TickerList");

	}
}
