package com.lise.markowitz.client.db.datasources;

import com.google.gwt.core.client.GWT;
import com.lise.markowitz.client.localization.LocalizeConstant;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class NavigationTreeDataSource extends JSONDataSource {

	private LocalizeConstant localizeConstant = GWT
			.create(LocalizeConstant.class);
	private static NavigationTreeDataSource instance = null;

	public NavigationTreeDataSource() {
		super();

		setID("TreeDataSource");
		setRecordXPath("/data/object/");
		setTitleField("Name");
		DataSourceTextField nameField = new DataSourceTextField("name",
				localizeConstant.treeGridTitle());

		DataSourceIntegerField idField = new DataSourceIntegerField("id", "ID");
		idField.setPrimaryKey(true);
		idField.setRequired(true);

		DataSourceIntegerField parentIdField = new DataSourceIntegerField(
				"parentId", "Parent ID");
		parentIdField.setRequired(true);
		parentIdField.setForeignKey("TreeDataSource.id");

		setFields(nameField, idField, parentIdField);

		setDataURL("/UIRServer/NavigationTreeServlet");

	}

	public static NavigationTreeDataSource getInstance() {
		if (instance == null) {
			instance = new NavigationTreeDataSource();
		}
		return instance;
	}

}
