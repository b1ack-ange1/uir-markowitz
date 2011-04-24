package com.lise.markowitz.client.view;

import com.google.gwt.core.client.GWT;
import com.lise.markowitz.client.form.WelcomePanel;
import com.lise.markowitz.client.localization.LocalizeConstant;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;

public class MainDynamicPanel extends HLayout {

	private LocalizeConstant localizeConstant = GWT
			.create(LocalizeConstant.class);
	private final NavigationPanel treePanel;
	private final UniversalTabPanel tabSet;
	private static MainDynamicPanel self = null;

	public MainDynamicPanel() {
		super();
		treePanel = new NavigationPanel(self);
		treePanel.setWidth("25%");
		treePanel.setCanDragResize(true);
		treePanel.setHeight100();

		final WelcomePanel welcomePane = new WelcomePanel();
		welcomePane.setGroupTitle(localizeConstant.welcomePage());

		tabSet = new UniversalTabPanel(welcomePane);

		addMember(treePanel);
		addMember(tabSet);

	}

	public static MainDynamicPanel getInstance() {
		if (self == null)
			self = new MainDynamicPanel();
		return self;
	}

	public Layout getMainArea() {
		return tabSet;
	}

	public Canvas getTree() {
		return treePanel;
	}
}
