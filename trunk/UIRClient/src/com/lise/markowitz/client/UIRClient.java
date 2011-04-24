package com.lise.markowitz.client;


import com.google.gwt.core.client.EntryPoint;
import com.lise.markowitz.client.db.LoginRequiredCallbackImpl;
import com.lise.markowitz.client.localization.Localize;
import com.lise.markowitz.client.utils.AboutWindow;
import com.lise.markowitz.client.view.ClientPanel;
import com.smartgwt.client.core.KeyIdentifier;
import com.smartgwt.client.rpc.RPCManager;
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
		
		Page.registerKey(aboutKey, new KeyCallback() {
			public void execute(String keyName) {
				AboutWindow.getInstance().show();
				AboutWindow.getInstance().centerInPage();
			}
		});

		
	
		// Configuration.init();

		Page.setTitle(Localize.getInstance().appTitleFull());
		screen.draw();
		
		RPCManager.setLoginRequiredCallback(new LoginRequiredCallbackImpl());

	}

	private native void subscribeLoad() /*-{
										$wnd.dojo.addOnLoad(@com.lise.markowitz.client.UIRClient::doDojoLoad());
										}-*/;

	public static native void doDojoLoad() /*-{
											$wnd.dojo.require("dojox.gfx");
											$wnd.dojo.require("dojox.charting.DataChart");
											}-*/;
}
