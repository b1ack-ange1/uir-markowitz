package com.lise.markowitz.client.form;

import com.smartgwt.client.widgets.Label;

public class WelcomePanel extends WorkPanel {

	public WelcomePanel() {
		super();
		panel.addMember(new Label("Стартовая страница, всем здрасьте!"));
		removeMember(toolStrip);
	}

	@Override
	public void buildToolStrip() {
		
	}

	@Override
	public void buildPanel() {

	}

	@Override
	public void checkControls() {

	}

}
