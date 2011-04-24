package com.lise.markowitz.client.view;

import com.google.gwt.core.client.GWT;
import com.lise.markowitz.client.db.datasources.NavigationTreeDataSource;
import com.lise.markowitz.client.form.MethodPanel;
import com.lise.markowitz.client.form.StatsPanel;
import com.lise.markowitz.client.form.WelcomePanel;
import com.lise.markowitz.client.form.WorkPanel;
import com.lise.markowitz.client.localization.LocalizeConstant;
import com.lise.markowitz.client.utils.Logger;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.events.LeafClickEvent;
import com.smartgwt.client.widgets.tree.events.LeafClickHandler;

public class NavigationPanel extends VLayout {
	private TreeGrid treeGrid = new TreeGrid();
	private final MainDynamicPanel parent;

	private LocalizeConstant localizeConstant = GWT
			.create(LocalizeConstant.class);

	public NavigationPanel(final MainDynamicPanel parent) {
		super();
		this.parent = parent;
		treeGrid.setWidth100();
		treeGrid.setHeight100();
		treeGrid.setCanEdit(false);
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

		TreeGridField codeField = new TreeGridField("code");
		codeField.setHidden(true);
		treeGrid.setCanPickFields(false);

		nameField.setFrozen(true);
		nameField.setWidth("*");

		treeGrid.setShowRoot(false);
		treeGrid.setFields(nameField, codeField);
		addMember(treeGrid);

		treeGrid.addLeafClickHandler(new LeafClickHandler() {

			@Override
			public void onLeafClick(LeafClickEvent event) {

				String title = event.getLeaf().getAttributeAsString("name");
				String code = event.getLeaf().getAttributeAsString("code");

				WorkPanel panel = null;

				if (code.equalsIgnoreCase("values")
						|| code.equalsIgnoreCase("profits")) {
					panel = new StatsPanel(code);
				}
				Logger.log("check1");
				if (code.equalsIgnoreCase("ArrHurv")
						|| code.equalsIgnoreCase("MonCar")
						|| code.equalsIgnoreCase("Zojt")
						|| code.equalsIgnoreCase("Lagr")
						|| code.equalsIgnoreCase("KunTak")
						|| code.equalsIgnoreCase("LevMark")
						|| code.equalsIgnoreCase("WolfFr")) {
					panel = new MethodPanel(code);

				}
				panel.setGroupTitle(title);
				((UniversalTabPanel) MainDynamicPanel.getInstance().getMainArea()).addMember(panel);
								
			}

		});
	}
}
