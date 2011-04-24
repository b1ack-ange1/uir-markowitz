package com.lise.markowitz.client.db.datasources;

import com.google.gwt.core.client.GWT;
import com.lise.markowitz.client.Configuration;
import com.lise.markowitz.client.localization.LocalizeConstant;
import com.smartgwt.client.data.fields.DataSourcePasswordField;
import com.smartgwt.client.data.fields.DataSourceTextField;

/**
 * Производит вход в систему.
 */
public class LoginDataSource extends JSONDataSource {
	private LocalizeConstant localizeConstant = GWT
			.create(LocalizeConstant.class);

	private static LoginDataSource loginDataSource;

	public static LoginDataSource getInstance() {
		if (loginDataSource == null)
			loginDataSource = new LoginDataSource();
		return loginDataSource;
	}

	protected LoginDataSource() {
		super();

		setID("LoginDataSource");
		setShowPrompt(false);
		setSendExtraFields(false);

		setDataURL(Configuration.getWebServiceAddr(false, false) + "Login");

		DataSourceTextField userNameField = new DataSourceTextField("user",
				localizeConstant.user());
		userNameField.setPrimaryKey(true);
		userNameField.setRequired(true);

		DataSourcePasswordField passwordField = new DataSourcePasswordField(
				"password", localizeConstant.lds_password());
		passwordField.setRequired(true);

		DataSourcePasswordField newPassword = new DataSourcePasswordField(
				"newPassword");

		setFields(userNameField, passwordField, newPassword);
	}
}
