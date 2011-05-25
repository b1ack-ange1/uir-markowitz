package com.lise.markowitz.client.form;

import com.lise.markowitz.client.db.datasources.PortfolioDataSource;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.MenuItemSeparator;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.toolbar.ToolStripMenuButton;

public class PortfolioPanel extends WorkPanel {
	private String code;
	private ListGrid portfolioGrid;
	private Label profitLabel;
	private Label riskLabel;

	public PortfolioPanel(String code) {
		super();
		this.code = code;

	}

	@Override
	public void buildToolStrip() {
		ToolStripButton delete = new ToolStripButton(localizeConstant.delete());
		delete.setIcon("./actions/rollback.gif");
		delete.setDisabled(false);
		delete.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

			}

		});

		ToolStripButton redact = new ToolStripButton(localizeConstant.redact());
		redact.setIcon("./actions/detail.gif");
		redact.setDisabled(false);
		redact.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

			}

		});

		toolStrip.addButton(delete);

		toolStrip.addSeparator();
		toolStrip.addButton(redact);

		toolStrip.addSeparator();
		Menu menu = new Menu();
		Menu nonLinear = new Menu();
		Menu convex = new Menu();
		Menu quadratic = new Menu();

		MenuItem lagr = new MenuItem(localizeConstant.lagrange());
		MenuItem moncar = new MenuItem(localizeConstant.montecatlo());
		MenuItem zojt = new MenuItem(localizeConstant.zojtendake());
		MenuItem kuntak = new MenuItem(localizeConstant.kuntakker());
		MenuItem arrhurv = new MenuItem(localizeConstant.arrhurvic());
		MenuItem wolffr = new MenuItem(localizeConstant.wolffrank());
		MenuItem levmar = new MenuItem(localizeConstant.levenmarq());

		nonLinear.setItems(lagr, moncar, zojt);
		convex.setItems(kuntak, arrhurv);
		quadratic.setItems(wolffr, levmar);

		MenuItem nonLinearItem = new MenuItem(localizeConstant.nonlinear());
		nonLinearItem.setSubmenu(nonLinear);

		MenuItem convexItem = new MenuItem(localizeConstant.convex());
		convexItem.setSubmenu(convex);

		MenuItem quadraticItem = new MenuItem(localizeConstant.quadratic());
		quadraticItem.setSubmenu(quadratic);
	
		MenuItemSeparator separator = new MenuItemSeparator();
		menu.setItems(nonLinearItem, separator, convexItem, separator,
				quadraticItem);
		ToolStripMenuButton menuButton = new ToolStripMenuButton(
				localizeConstant.countSolution(), menu);
		toolStrip.addMenuButton(menuButton);
	}

	public void setNewProfit(String profit) {
		profitLabel.setContents(localizeConstant.currentProfit() + " = "
				+ profit);
	}

	public void setNewRisk(String risk) {
		riskLabel = new Label(localizeConstant.currentRisk() + " = " + risk);
	}

	@Override
	public void buildPanel() {
		String profit = "34.23";
		String risk = "0.23";

		profitLabel = new Label(localizeConstant.currentProfit() + " = "
				+ profit);
		riskLabel = new Label(localizeConstant.currentRisk() + " = " + risk);

		profitLabel.setAutoHeight();
		riskLabel.setAutoHeight();

		panel.addMember(profitLabel);
		panel.addMember(riskLabel);

		ListGridField idField = new ListGridField("id", "Ticker");
		ListGridField nameField = new ListGridField("name", "Name");
		ListGridField weightField = new ListGridField("weight", "Weight");

		portfolioGrid = new ListGrid();
		portfolioGrid.setWidth(300);
		portfolioGrid.setHeight(224);
		portfolioGrid.setShowAllRecords(true);
		portfolioGrid.setFields(idField, nameField, weightField);
		portfolioGrid.setDataSource(PortfolioDataSource.getInstance(code));
		portfolioGrid.setAutoFetchData(true);

		panel.addMember(portfolioGrid);

	}

	@Override
	public void checkControls() {
		// TODO Auto-generated method stub

	}
}
