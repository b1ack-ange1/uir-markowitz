package com.lise.markowitz.client.form;

import com.google.gwt.core.client.GWT;
import com.lise.markowitz.client.db.datasources.OptionTickerListDataSource;
import com.lise.markowitz.client.localization.LocalizeConstant;
import com.lise.markowitz.client.utils.Logger;
import com.smartgwt.client.types.MultipleAppearance;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.MiniDateRangeItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class StatsPanel extends WorkPanel {
	private String code;
	private LocalizeConstant localizeConstant = GWT
			.create(LocalizeConstant.class);
	SelectItem tickerList = null;
	MiniDateRangeItem dateRange = null;

	public StatsPanel(String code) {
		super();
		this.code = code;

	}

	@Override
	public void buildToolStrip() {
		try {
			ToolStripButton refresh = new ToolStripButton(
					localizeConstant.refresh());
			refresh.setIcon("./actions/refresh.gif");
			refresh.setDisabled(false);
			refresh.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					long from = dateRange.getFromDate().getTime();
					long till = dateRange.getToDate().getTime();
					SC.logWarn(from + " " + till);
					ListGridRecord[] records = tickerList.getSelectedRecords();
					SC.logWarn("records size = " + records.length);
					try {
						panel.addMember(new Label("asdasdasd"));
						//panel.addMember(new LISEPlot(from, till, records));
					} catch (Exception e) {
						Logger.log(e.getMessage());
					}

				}
			});

			toolStrip.addButton(refresh);

			tickerList = new SelectItem();
			tickerList.setTitle(localizeConstant.chooseTickerList());
			tickerList.setMultiple(true);
			tickerList.setMultipleAppearance(MultipleAppearance.PICKLIST);
			tickerList.setOptionDataSource(OptionTickerListDataSource.getInstance());
			toolStrip.addFormItem(tickerList);

			dateRange = new MiniDateRangeItem(
					localizeConstant.chooseDatePeriod());
			toolStrip.addFormItem(dateRange);
		} catch (Exception e) {
			SC.logWarn(e.getMessage());
		}
	}

	@Override
	public void buildPanel() {

	}

	@Override
	public void checkControls() {
		// TODO Auto-generated method stub

	}

}
