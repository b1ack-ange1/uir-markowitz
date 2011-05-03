package com.lise.markowitz.client.form;

import java.util.Date;
import java.util.HashMap;

import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Cookies;
import com.lise.markowitz.client.Configuration;
import com.lise.markowitz.client.db.datasources.PortfolioListDataSource;
import com.lise.markowitz.client.localization.Localize;
import com.lise.markowitz.client.utils.Logger;
import com.lise.markowitz.client.view.ClientPanel;
import com.lise.markowitz.client.view.MainDynamicPanel;
import com.lise.markowitz.client.view.NavigationPanel;
import com.smartgwt.client.rpc.RPCCallback;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.events.ItemKeyPressEvent;
import com.smartgwt.client.widgets.form.events.ItemKeyPressHandler;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.events.DataArrivedEvent;
import com.smartgwt.client.widgets.form.fields.events.DataArrivedHandler;
import com.smartgwt.client.widgets.layout.HLayout;

public class PortfolioPickerForm extends Window {
	private DynamicForm form;
	private ComboBoxItem PortfolioIdPicker;

	public PortfolioPickerForm() {
		super();

		this.setTitle("Выбор портфеля");

		setAutoSize(true);
		setIsModal(true);
		setShowModalMask(true);
		setKeepInParentRect(true);

		setHeaderControls(HeaderControls.HEADER_LABEL);
		setAutoCenter(true);

		form = new DynamicForm();

		form.setLayoutAlign(Alignment.CENTER);
		form.setPadding(5);
		form.setWrapItemTitles(false);
		form.setCellSpacing(4);

		form.addItemKeyPressHandler(new ItemKeyPressHandler() {
			public void onItemKeyPress(ItemKeyPressEvent event) {
				if (event.getKeyName().equalsIgnoreCase("Enter"))
					okButtonClick();
			}
		});

		PortfolioIdPicker = new ComboBoxItem("PortfolioId");
		PortfolioIdPicker.setOptionDataSource(PortfolioListDataSource
				.getInstance());
		PortfolioIdPicker.setDisplayField("Name");
		PortfolioIdPicker.setValueField("Id");
		PortfolioIdPicker.setAutoFetchData(true);
		PortfolioIdPicker.addDataArrivedHandler(new DataArrivedHandler() {
			@Override
			public void onDataArrived(DataArrivedEvent event) {
				if (event.getData().getLength() == 0) {
					SC.say("Не найдено портфелей! Создайте новый портфель!");
					hide();
					new CreatePortfolioForm().show();
				}
			}
		});

		form.setFields(PortfolioIdPicker);

		HLayout buttons = new HLayout(5);
		buttons.setPadding(5);
		buttons.setLayoutAlign(Alignment.CENTER);
		buttons.setAutoHeight();
		IButton ok = new IButton(Localize.getInstance().ok());
		ok.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				okButtonClick();
			}
		});
		IButton newPortfolio = new IButton(Localize.getInstance()
				.newPortfolio());
		newPortfolio.setAutoFit(true);
		newPortfolio.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
				new CreatePortfolioForm().show();
			}
		});

		buttons.addMember(ok);
		buttons.addMember(newPortfolio);

		this.addItem(form);
		this.addItem(buttons);
	}

	protected void okButtonClick() {
		Logger.log("PortfolioPickerForm.okButtonClick");
		pickPortfolio();
		Logger.log("PortfolioPickerForm.okButtonClick end");
	}

	private void pickPortfolio() {
		Logger.log("PortfolioPickerForm.pickPortfolio");
		HashMap<String, Object> requestParams = new HashMap<String, Object>();
		requestParams
				.put("actionURL", Configuration.getWebServiceAddr(false, false)
						+ "PortfolioPick");
		requestParams.put("useSimpleHttp", true);
		JSONObject requestData = new JSONObject();
		requestData.put("id",
				new JSONNumber(new Float(form.getValue("PortfolioId")
						.toString())));

		RPCManager.send(requestData.toString(), new RPCCallback() {
			public void execute(RPCResponse response, Object rawData,
					RPCRequest request) {

				hide();
				Date dt = new Date();
				dt.setTime(dt.getTime() + (1000 * 60 * 60 * 24 * 7));// 7
				// дней

				Cookies.setCookie("lastPortfolio", form.getValue("PortfolioId")
						.toString(), dt);
				ClientPanel.getInstance().setPortfolio(
						PortfolioIdPicker.getDisplayValue());
				((NavigationPanel) MainDynamicPanel.getInstance().getTree()).treeGrid
						.fetchData();
				MainDynamicPanel.getInstance().getTree().show();

			}
		}, requestParams);

		Logger.log("PortfolioPickerForm.pickPortfolio end");
	}
}
