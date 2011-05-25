package com.lise.markowitz.client.form;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.lise.markowitz.client.localization.LocalizeConstant;
import com.lise.markowitz.client.utils.progress.ProgressWindow;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

public abstract class WorkPanel extends VLayout {
	protected ToolStrip toolStrip = new ToolStrip();
	protected VLayout panel = new VLayout();
	protected ProgressWindow progressWindow;

	protected LocalizeConstant localizeConstant = GWT
			.create(LocalizeConstant.class);

	public abstract void buildToolStrip();

	public abstract void buildPanel();

	public abstract void checkControls();

	public WorkPanel() {
		super();
		setWidth100();
		setHeight100();
		toolStrip.setWidth100();
		try {
			buildToolStrip();
		} catch (Exception e) {
			SC.logWarn("1" + e.getMessage());
		}
		try {
			buildPanel();
		} catch (Exception e) {
			SC.logWarn("2" + e.getMessage());
		}
		addMember(toolStrip);
		addMember(panel);
		checkControls();
		markForRedraw();
	}

	public void showProgressWindow() {
		if (progressWindow == null)
			progressWindow = new ProgressWindow(this);
		progressWindow.show();
	}

	@SuppressWarnings("deprecation")
	public void hideProgressWindow() {
		if (progressWindow != null)
			DeferredCommand.addCommand(new Command() {
				public void execute() {
					progressWindow.hide();
				}
			});
	}
}
