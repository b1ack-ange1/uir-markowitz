package com.lise.markowitz.client.view;


import com.google.gwt.core.client.GWT;
import com.lise.markowitz.client.localization.LocalizeConstant;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuButton;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.MenuItemSeparator;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

public class ClientPanel extends VLayout {
	private LocalizeConstant localizeConstant = GWT
			.create(LocalizeConstant.class);

	protected Layout titlePanel;
	protected Label userInfo;

	protected MenuButton serviceMenuButton;
	protected IButton logoffButton;

	private static ClientPanel rootPanel;

	private ClientPanel() {
		super();
		setWidth100();
		setHeight100();

		// titlePanel
		VLayout topPanel = new VLayout();
		topPanel.setHeight(100);
		topPanel.setBackgroundImage("other/delimiter_small.gif");

		Layout spacer1 = new Layout();
		spacer1.setHeight(6);

		topPanel.addMember(spacer1);

		titlePanel = new HLayout();
		titlePanel.setDefaultLayoutAlign(VerticalAlignment.CENTER);

		Layout spacer2 = new Layout();
		spacer2.setWidth(20);
		titlePanel.addMember(spacer2);

		Img icon = new Img("other/ACIcon.gif", 32, 32);
		icon.setExtraSpace(30);
		titlePanel.addMember(icon);

		Label label = new Label("<font face='Times New Roman' size='5'>"
				+ localizeConstant.appTitle() + "</font>");
		label.setWidth("*");
		titlePanel.addMember(label);

		userInfo = new Label();
		userInfo.setWidth("*");
		userInfo.setMargin(5);
		userInfo.setAlign(Alignment.RIGHT);
		titlePanel.addMember(userInfo);

		Menu serviceMenu = new Menu();
		serviceMenu.setBorder("1px solid silver");
		serviceMenu.setShowShadow(true);

		MenuItem changePasswordItem = new MenuItem(
				localizeConstant.changePassword());
		changePasswordItem
				.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					public void onClick(MenuItemClickEvent event) {
						// ChangePasswordForm.getInstance().show();
					}
				});

		MenuItem aboutItem = new MenuItem(localizeConstant.about());
		aboutItem
				.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					public void onClick(MenuItemClickEvent event) {
						// AboutWindow.getInstance().show();
						// AboutWindow.getInstance().centerInPage();
					}
				});

		MenuItemSeparator separator = new MenuItemSeparator();
		serviceMenu.setItems(changePasswordItem, separator, aboutItem);

		serviceMenuButton = new MenuButton(localizeConstant.service(),
				serviceMenu);
		titlePanel.addMember(serviceMenuButton);

		logoffButton = new IButton(localizeConstant.logoff());
		logoffButton.setExtraSpace(10);
		logoffButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

			}
		});
		titlePanel.addMember(logoffButton);

		topPanel.addMember(titlePanel);

		this.addMember(topPanel);

		Layout layout = new HLayout();
		layout.setWidth100();
		layout.setHeight100();

		this.addMember(layout);
	}

	public static ClientPanel getInstance() {
		if (rootPanel == null)
			rootPanel = new ClientPanel();
		return rootPanel;
	}

	public Layout getTitlePanel() {
		return titlePanel;
	}

	public void setCanLogoff(boolean canLogoff) {
		logoffButton.setDisabled(!canLogoff);
	}

}
