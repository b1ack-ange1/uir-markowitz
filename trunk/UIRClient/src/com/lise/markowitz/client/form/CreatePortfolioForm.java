package com.lise.markowitz.client.form;

import java.util.HashMap;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.lise.markowitz.client.Configuration;
import com.lise.markowitz.client.db.datasources.TickerListDataSource;
import com.lise.markowitz.client.localization.Localize;
import com.lise.markowitz.client.utils.Logger;
import com.lise.markowitz.client.view.MainDynamicPanel;
import com.lise.markowitz.client.view.NavigationPanel;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.rpc.RPCCallback;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.events.ItemKeyPressEvent;
import com.smartgwt.client.widgets.form.events.ItemKeyPressHandler;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HLayout;

public class CreatePortfolioForm extends Window {
	private DynamicForm form;
	private final ListGrid portfolioGrid;
	private final TextItem portfolioName;

	public CreatePortfolioForm() {
		super();

		this.setTitle("Создание портфеля");

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

		portfolioName = new TextItem("Название портфеля");
		form.setFields(portfolioName);

		ListGrid tickerGrid = new ListGrid();
		tickerGrid.setWidth(300);
		tickerGrid.setHeight(224);
		tickerGrid.setShowAllRecords(true);
		tickerGrid.setCanReorderRecords(true);
		tickerGrid.setCanDragRecordsOut(true);
		tickerGrid.setCanAcceptDroppedRecords(true);
		tickerGrid.setDragDataAction(DragDataAction.MOVE);

		ListGridField idField = new ListGridField("id", "Ticker");
		ListGridField nameField = new ListGridField("name", "Name");
		ListGridField weightField = new ListGridField("weight", "Weight");
		weightField.setCanEdit(true);
		
		tickerGrid.setFields(idField, nameField);
		tickerGrid.setDataSource(TickerListDataSource.getInstance());
		tickerGrid.setAutoFetchData(true);

		portfolioGrid = new ListGrid();
		portfolioGrid.setWidth(300);
		portfolioGrid.setHeight(224);
		portfolioGrid.setShowAllRecords(true);
		portfolioGrid.setEmptyMessage("Добавьте тикер");
		portfolioGrid.setCanReorderFields(true);
		portfolioGrid.setCanDragRecordsOut(true);
		portfolioGrid.setCanAcceptDroppedRecords(true);
		portfolioGrid.setDragDataAction(DragDataAction.MOVE);
		portfolioGrid.setFields(idField, nameField, weightField);
		//portfolioGrid.setDataSource(PortfolioInitDataSource.getInstance());

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

		IButton cancel = new IButton(Localize.getInstance().cancel());
		cancel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();

			}
		});

		buttons.addMember(ok);
		buttons.addMember(cancel);

		this.addItem(form);
		HLayout gridLayout = new HLayout();
		gridLayout.setMargin(5);
		gridLayout.setPadding(10);
		gridLayout.addMember(tickerGrid);
		gridLayout.addMember(portfolioGrid);
		this.addItem(gridLayout);
		this.addItem(buttons);
	}

	protected void okButtonClick() {
		Logger.log("PortfolioCreateForm.okButtonClick");
		String temp = portfolioName.getValue().toString();
		RecordList recordList = portfolioGrid.getDataAsRecordList();
		HashMap<String, Object> requestParams = new HashMap<String, Object>();
		requestParams.put("actionURL",
				Configuration.getWebServiceAddr(false, false) + "PortfolioNew");
		requestParams.put("useSimpleHttp", true);
		JSONObject requestData = new JSONObject();
		requestData.put("name", new JSONString(temp));

		JSONArray records = new JSONArray();

		for (int i = 0; i < recordList.getLength(); i++) {
			JSONObject record = new JSONObject();
			record.put("id",
					new JSONString(recordList.get(i).getAttribute("cacheId")));
			record.put("weight",
					new JSONString(recordList.get(i).getAttribute("weight")));
			records.set(i, record);
		}
		requestData.put("records", records);

		RPCManager.send(requestData.toString(), new RPCCallback() {
			public void execute(RPCResponse response, Object rawData,
					RPCRequest request) {

				hide();
				((NavigationPanel) MainDynamicPanel.getInstance().getTree()).treeGrid
						.fetchData();

			}
		}, requestParams);

		Logger.log("PortfolioCreateForm.okButtonClick end");
	}
}
