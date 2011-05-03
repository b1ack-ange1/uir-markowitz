package com.lise.markowitz.client.db.datasources;

import com.lise.markowitz.client.Configuration;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class PortfolioListDataSource extends JSONDataSource {
	private static PortfolioListDataSource profilesListDataSource;

	public static PortfolioListDataSource getInstance() {
		if (profilesListDataSource == null)
			profilesListDataSource = new PortfolioListDataSource();
		return profilesListDataSource;
	}

	protected PortfolioListDataSource() {
		super();

		setID("PortfolioList");

		DataSourceTextField idField = new DataSourceTextField("Id");
		DataSourceTextField nameField = new DataSourceTextField("Name");

		idField.setPrimaryKey(true);
		setTitleField("Name");

		setFields(nameField, idField);

		setDataURL(Configuration.getWebServiceAddr(false, false)
				+ "PortfolioList");
	}
}
