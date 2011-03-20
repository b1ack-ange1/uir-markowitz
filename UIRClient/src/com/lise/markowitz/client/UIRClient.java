package com.lise.markowitz.client;


import com.google.gwt.core.client.EntryPoint;
import com.lise.markowitz.client.localization.Localize;
import com.lise.markowitz.client.view.ClientPanel;
import com.smartgwt.client.core.KeyIdentifier;
import com.smartgwt.client.util.KeyCallback;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class UIRClient implements EntryPoint {
	public void onModuleLoad() {
		subscribeLoad();

		Canvas screen = new Canvas();

		screen.setWidth100();
		screen.setHeight100();

		screen.addChild(ClientPanel.getInstance());
		
		KeyIdentifier debugKey = new KeyIdentifier();
		debugKey.setCtrlKey(true);
		debugKey.setAltKey(true);
		debugKey.setKeyName("D");

		Page.registerKey(debugKey, new KeyCallback() {
			public void execute(String keyName) {
				SC.showConsole();
			}
		});

		KeyIdentifier aboutKey = new KeyIdentifier();
		aboutKey.setCtrlKey(true);
		aboutKey.setAltKey(true);
		aboutKey.setKeyName("1");

		// Configuration.init();

		Page.setTitle(Localize.getInstance().appTitleFull());
		screen.draw();

		/*
		 * RsdhHandleErrorCallback rsdhHandleErrorCallback = new
		 * RsdhHandleErrorCallback();
		 * RPCManager.setHandleTransportErrorCallback(rsdhHandleErrorCallback);
		 * RPCManager.setHandleErrorCallback(rsdhHandleErrorCallback);
		 * 
		 * RPCManager.setLoginRequiredCallback(new LoginRequiredCallbackImpl());
		 * // RPCManager.setShowPrompt(false);
		 * 
		 * // РћС‚СЂР°Р±РѕС‚Р°РµРј СЃСЃС‹Р»РєСѓ String userObjLinkId = Location
		 * .getParameter(Configuration.UsrObjLinkIdParamName); String
		 * userObjLinkClassCode = Location
		 * .getParameter(Configuration.UsrObjLinkClassCodeParamName); if
		 * (userObjLinkId != null) { RsdhRootPanel .getInstance() .getTree()
		 * .openObject(Double.valueOf(userObjLinkId), userObjLinkClassCode); }
		 */
	}

	private native void subscribeLoad() /*-{
										$wnd.dojo.addOnLoad(@com.lise.markowitz.client.UIRClient::doDojoLoad());
										}-*/;

	public static native void doDojoLoad() /*-{
											$wnd.dojo.require("dojox.gfx");
											$wnd.dojo.require("dojox.charting.DataChart");
											}-*/;
}
