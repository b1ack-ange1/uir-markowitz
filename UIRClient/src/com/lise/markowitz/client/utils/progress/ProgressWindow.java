package com.lise.markowitz.client.utils.progress;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.lise.markowitz.client.localization.LocalizeConstant;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class ProgressWindow implements ClickHandler {
	private LocalizeConstant localizeConstant = GWT
			.create(LocalizeConstant.class);

	private static List<ProgressWindow> progressWindows = new ArrayList<ProgressWindow>();
	private ProgressCancelHandler progressCancel;
	private Label label;
	private Label hint;
	private Img undefProgressImg;
	private IButton cancel;

	private Canvas canvas;
	private HLayout modal;
	private Canvas transparent;

	public ProgressWindow(Canvas canvas) {
		this.canvas = canvas;
		createModalPanel();

		VLayout progressPanel0 = new VLayout();
		progressPanel0.setWidth100();
		progressPanel0.setAutoHeight();
		progressPanel0.setDefaultLayoutAlign(Alignment.CENTER);

		VLayout progressPanel = new VLayout();
		progressPanel.setLayoutMargin(5);
		progressPanel.setAutoWidth();
		progressPanel.setBackgroundColor("white");
		progressPanel.setDefaultLayoutAlign(Alignment.CENTER);
		progressPanel.setBorder("1px solid black");
		progressPanel.setZIndex(transparent.getZIndex() + 2);
		progressPanel0.addMember(progressPanel);

		label = new Label();
		label.setAlign(Alignment.CENTER);
		label.setSize("220px", "*");
		label.setVisible(false);
		progressPanel.addMember(label);

		hint = new Label();
		hint.setAlign(Alignment.CENTER);
		hint.setSize("220px", "*");
		hint.setVisible(false);
		progressPanel.addMember(hint);

		undefProgressImg = new Img("ajax-loader.gif");
		undefProgressImg.setSize("220px", "19px");
		progressPanel.addMember(undefProgressImg);

		cancel = new IButton(localizeConstant.cancel());
		cancel.setAutoFit(true);
		cancel.setLayoutAlign(Alignment.CENTER);
		cancel.setDisabled(true);
		cancel.addClickHandler(this);
		progressPanel.addMember(cancel);

		modal.addMember(progressPanel0);

		progressWindows.add(this);
	}

	public void onClick(ClickEvent event) {
		if (progressCancel != null)
			progressCancel.onCancel();
		else
			RPCManager.cancelQueue();
		hide();
	}

	private void createModalPanel() {
		modal = new HLayout();
		modal.setWidth100();
		modal.setHeight100();
		modal.setDefaultLayoutAlign(Alignment.CENTER);
		modal.hide();

		transparent = new Canvas();
		transparent.setWidth100();
		transparent.setHeight100();
		transparent.setBackgroundColor("#555");
		transparent.setOpacity(30);

		modal.addChild(transparent);
		canvas.addChild(modal);
	}

	public void setCancelHandler(ProgressCancelHandler progressCancel) {
		this.progressCancel = progressCancel;
		cancel.setDisabled(false);
	}

	public void hide() {
		modal.hide();
	}

	public static void hideAll() {
		for (ProgressWindow progressWindow : progressWindows)
			progressWindow.hide();
	}

	public void show() {
		label.setVisible(false);
		hint.setVisible(false);
		modal.bringToFront();
		modal.show();
	}

	public void destroy() {
		progressCancel = null;
		canvas = null;
		modal = null;
		transparent = null;
		progressWindows.remove(this);
	}

	public void setLabelText(String text) {
		label.setContents(text);
		label.setVisible(true);
	}

	public void setHintText(String text) {
		hint.setContents(text);
		hint.setVisible(true);
	}
}
