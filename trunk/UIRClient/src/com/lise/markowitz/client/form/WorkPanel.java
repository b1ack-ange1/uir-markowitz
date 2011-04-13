package com.lise.markowitz.client.form;

import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

public abstract class WorkPanel extends VLayout {
	private ToolStrip toolStrip = new ToolStrip();
	private VLayout panel = new VLayout();

	public abstract void buildToolStrip();

	public abstract void buildPanel();

	public abstract void checkControls();

	public WorkPanel() {
		super();
		buildToolStrip();
		buildPanel();
		addMember(toolStrip);
		addMember(panel);
		checkControls();
		markForRedraw();
	}

}
