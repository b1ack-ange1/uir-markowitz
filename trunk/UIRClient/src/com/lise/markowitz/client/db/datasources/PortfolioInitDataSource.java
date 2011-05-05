package com.lise.markowitz.client.db.datasources;

import com.lise.markowitz.client.Configuration;
import com.smartgwt.client.data.fields.DataSourceFloatField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class PortfolioInitDataSource extends JSONDataSource {
	private static PortfolioInitDataSource profilesListDataSource;

	public static PortfolioInitDataSource getInstance() {
		if (profilesListDataSource == null)
			profilesListDataSource = new PortfolioInitDataSource();
		return profilesListDataSource;
	}

	protected PortfolioInitDataSource() {
		super();

		setID("PortfolioInit");

		DataSourceTextField idField = new DataSourceTextField("id");
		DataSourceTextField nameField = new DataSourceTextField("name");
		DataSourceFloatField weightField = new DataSourceFloatField("weight");
		weightField.setCanEdit(true);
		
		idField.setPrimaryKey(true);
		setTitleField("Name");

		setFields(nameField, idField, weightField);

		setDataURL(Configuration.getWebServiceAddr(false, false)
				+ "PortfolioInit");
	}
}
