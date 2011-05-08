package com.lise.markowitz.client.form;

import com.lise.markowitz.client.db.datasources.PortfolioDataSource;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

public class PortfolioPanel extends WorkPanel {
	private String code;
	private ListGrid portfolioGrid;

	public PortfolioPanel(String code) {
		super();
		this.code = code;
		
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
	public void buildToolStrip() {
		// TODO Auto-generated method stub

	}

	@Override
	public void buildPanel() {
		// TODO Auto-generated method stub

	}

	@Override
	public void checkControls() {
		// TODO Auto-generated method stub

	}
}
