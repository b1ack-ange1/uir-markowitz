package com.lise.markowitz.client.form;

import com.lise.markowitz.client.form.presentation.LISEPlot;
import com.lise.markowitz.client.utils.Logger;
import com.smartgwt.client.widgets.Label;

public class StatsPanel extends WorkPanel {
	private String code;

	public StatsPanel(String code) {
		super();
		this.code = code;
		panel.addMember(new Label("wwwwww"));
		try {
			panel.addMember(new LISEPlot());
		} catch (Exception e) {
			Logger.log(e.getMessage());
		}
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
