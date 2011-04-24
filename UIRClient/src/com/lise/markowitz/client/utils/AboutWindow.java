package com.lise.markowitz.client.utils;

import com.google.gwt.core.client.GWT;
import com.lise.markowitz.client.form.LoginForm;
import com.lise.markowitz.client.localization.LocalizeConstant;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class AboutWindow extends Window {
	private LocalizeConstant localizeConstant = GWT
			.create(LocalizeConstant.class);

	private class CentralLabel extends Label {
		public CentralLabel(String text) {
			super(text);
			setWrap(false);
			setValign(VerticalAlignment.TOP);
		}
	}

	private static AboutWindow aboutWindow;

	public static AboutWindow getInstance() {
		if (aboutWindow == null)
			aboutWindow = new AboutWindow();
		return aboutWindow;
	}

	protected AboutWindow() {
		super();
		setTitle(localizeConstant.about());
		setHeaderControls(HeaderControls.HEADER_LABEL,
				HeaderControls.CLOSE_BUTTON);
		setIsModal(true);
		setAutoSize(true);
		setCanDragReposition(true);
		setKeepInParentRect(true);

		VLayout mainPanel = new VLayout();
		mainPanel.setWidth(511);

		mainPanel.addMember(new Img("other/logoLISE.gif", 510, 66));// title

		HLayout centerPanel = new HLayout();// center
		centerPanel.setWidth100();
		Img icon = new Img("other/ACIcon.gif", 52, 52);
		icon.setMargin(10);
		centerPanel.addMember(icon);

		VLayout centerText = new VLayout();
		centerText.setWidth("*");

		Label label1 = new CentralLabel(
				"<b><font size='5' face='Times New Roman'>Lise F</font></b>");
		label1.setHeight(40);
		centerText.addMember(label1);

		Label label2 = new CentralLabel(
				"<b><font size='3' face='Times New Roman'>"
						+ localizeConstant.appTitleFull() + " "
						+ "0.1" + "</font></b>");
		label2.setHeight(28);
		centerText.addMember(label2);

		Label label3 = new CentralLabel(!LoginForm.getInstance()
				.getConnectionDesc().isEmpty() ? localizeConstant.user() + ": "
				+ LoginForm.getInstance().getConnectionDesc() : "");
		label3.setHeight(40);
		centerText.addMember(label3);

		Label label4 = new CentralLabel(
				"Copyright&copy; 2011-2011, Anton Lapitskiy, Alexander Minenok, Konstantin Hrimpach");
		label4.setAutoFit(true);
		centerText.addMember(label4);

		centerPanel.addMember(centerText);

		mainPanel.addMember(centerPanel);

		Label links = new Label(linkHTML("mailto:antlapit@gmail.com") + "<br/>"
				+ linkHTML("http://code.google.com/p/uir-markowitz/"));
		links.setSize("100%", "65px");
		links.setMargin(10);
		links.setAlign(Alignment.RIGHT);
		links.setValign(VerticalAlignment.BOTTOM);
		mainPanel.addMember(links);

		Label hr = new Label("<hr>");
		hr.setSize("100%", "10px");
		mainPanel.addMember(hr);

		HLayout bottomPanel = new HLayout();// bottom
		bottomPanel.setWidth100();
		bottomPanel.setMargin(5);

		Label copyright = new Label("<font face='Times New Roman' size='1px'>"
				+ localizeConstant.aw_copyRight() + "</font>");
		copyright.setMargin(5);
		copyright.setWidth("*");
		bottomPanel.addMember(copyright);
		IButton okButton = new IButton(localizeConstant.ok());
		okButton.setValign(VerticalAlignment.CENTER);
		okButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				destroy();
				aboutWindow = null;
			}
		});
		bottomPanel.addMember(okButton);
		mainPanel.addMember(bottomPanel);

		this.addItem(mainPanel);
	}
}
