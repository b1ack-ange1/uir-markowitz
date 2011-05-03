package com.lise.markowitz.client.db.datasources;

import com.lise.markowitz.client.Configuration;
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

		idField.setPrimaryKey(true);
		setTitleField("Name");

		setFields(nameField, idField);

		setDataURL(Configuration.getWebServiceAddr(false, false)
				+ "TickerList");
		
	}
}
