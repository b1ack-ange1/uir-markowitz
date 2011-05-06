package com.lise.markowitz.client.db.datasources;

import java.util.HashMap;

import com.lise.markowitz.client.Configuration;
import com.smartgwt.client.data.fields.DataSourceFloatField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class PortfolioDataSource extends JSONDataSource {

	private static PortfolioDataSource profilesListDataSource;

	public static PortfolioDataSource getInstance(String code) {
		if (profilesListDataSource == null)
			profilesListDataSource = new PortfolioDataSource(code);
		return profilesListDataSource;
	}

	protected PortfolioDataSource(String code) {
		super();

		setID("Portfolio");
		
		DataSourceTextField idField = new DataSourceTextField("id");
		DataSourceTextField nameField = new DataSourceTextField("name");
		DataSourceFloatField weightField = new DataSourceFloatField("weight");
		weightField.setCanEdit(true);

		idField.setPrimaryKey(true);
		setTitleField("Name");

		setFields(nameField, idField, weightField);
		
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("code", code);
		setDefaultParams(params);
		setDataURL(Configuration.getWebServiceAddr(true, false) + "Portfolio");
	}
}
