package com.lise.markowitz.client.view;

import com.google.gwt.core.client.JavaScriptObject;
import com.lise.markowitz.client.form.WelcomePanel;
import com.lise.markowitz.client.form.WorkPanel;
import com.smartgwt.client.widgets.layout.HLayout;

public class MainDynamicPanel extends HLayout {

	private WorkPanel mainPanel;
	private NavigationPanel treePanel;
	private static MainDynamicPanel self = null;

	public MainDynamicPanel() {
		super();
		treePanel = new NavigationPanel();
		mainPanel =  new WelcomePanel();
		addMember(treePanel);
		addMember(mainPanel);

	}

	public static MainDynamicPanel getInstance() {
		if (self == null)
			self = new MainDynamicPanel();
		return self;
	}

}
