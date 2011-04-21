package com.lise.markowitz.client.view;

import com.google.gwt.core.client.GWT;
import com.lise.markowitz.client.db.datasources.NavigationTreeDataSource;
import com.lise.markowitz.client.localization.LocalizeConstant;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;

public class NavigationPanel extends VLayout {
	private TreeGrid treeGrid = new TreeGrid();

	private LocalizeConstant localizeConstant = GWT
			.create(LocalizeConstant.class);

	public NavigationPanel() {
		super();
		treeGrid.setWidth("40%");
		treeGrid.setHeight100();
		treeGrid.setCanEdit(false);
		treeGrid.setNodeIcon("icons/16/folder.png");
		treeGrid.setFolderIcon("icons/16/person.png");
		treeGrid.setAutoFetchData(true);
		treeGrid.setCanFreezeFields(true);
		treeGrid.setCanReparentNodes(true);
		treeGrid.setDataSource(NavigationTreeDataSource.getInstance());
		treeGrid.setLoadDataOnDemand(false);
		treeGrid.setShowAllRecords(true);

		TreeGridField nameField = new TreeGridField("name",
				localizeConstant.treeGridTitle());

		TreeGridField idField = new TreeGridField(
				localizeConstant.treeGridTitle());

		TreeGridField parentIdField = new TreeGridField(
				localizeConstant.treeGridTitle());

		nameField.setFrozen(true);
		nameField.setWidth("*");

		treeGrid.setShowRoot(false);
		treeGrid.setFields(nameField);
		addMember(treeGrid);
	}
}
