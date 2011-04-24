package com.lise.markowitz.client.db;

import com.lise.markowitz.client.form.LoginForm;
import com.lise.markowitz.client.utils.Logger;
import com.lise.markowitz.client.view.MainDynamicPanel;
import com.smartgwt.client.rpc.LoginRequiredCallback;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.rpc.RPCResponse;

public class LoginRequiredCallbackImpl implements LoginRequiredCallback {
	public void loginRequired(int transactionNum, RPCRequest request,
			RPCResponse response) {
		MainDynamicPanel.getInstance().getTree().hide();
		try {
			LoginForm.getInstance().show();
		} catch (Exception e) {
			Logger.log(e.getMessage());
		}
	}
}
