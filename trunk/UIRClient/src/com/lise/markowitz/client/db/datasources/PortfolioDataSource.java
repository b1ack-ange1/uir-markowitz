package com.lise.markowitz.client.db.datasources;

import com.lise.markowitz.client.Configuration;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class PortfolioDataSource extends JSONDataSource {
	
	private static PortfolioDataSource profilesListDataSource;

	public static PortfolioDataSource getInstance() {
		if (profilesListDataSource == null)
			profilesListDataSource = new PortfolioDataSource();
		return profilesListDataSource;
	}

	protected PortfolioDataSource() {
		super();

		setID("Portfolio");

		DataSourceTextField idField = new DataSourceTextField("Id");
		DataSourceTextField nameField = new DataSourceTextField("Name");
		DataSourceIntegerField quantField = new DataSourceIntegerField("Quantity");

		idField.setPrimaryKey(true);
		setTitleField("Name");

		setFields(nameField, idField, quantField);

		setDataURL(Configuration.getWebServiceAddr(true, false)
				+ "Portfolio");
	}
}
