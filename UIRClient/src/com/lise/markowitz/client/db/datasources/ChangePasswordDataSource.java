package com.lise.markowitz.client.db.datasources;

import com.google.gwt.core.client.GWT;
import com.lise.markowitz.client.localization.LocalizeConstant;
import com.smartgwt.client.data.fields.DataSourcePasswordField;

/**
 * DataSource для смены пароля пользователя.
 */
public class ChangePasswordDataSource extends JSONDataSource {
	private LocalizeConstant localizeConstant = GWT
			.create(LocalizeConstant.class);

	private static ChangePasswordDataSource changePasswordDataSource;

	public static ChangePasswordDataSource getInstance() {
		if (changePasswordDataSource == null)
			changePasswordDataSource = new ChangePasswordDataSource();
		return changePasswordDataSource;
	}

	protected ChangePasswordDataSource() {
		super();

		setID("ChangePasswordDataSource");
		setShowPrompt(false);
		setSendExtraFields(false);

		setDataURL(getWebServiceAddr() + "ChangePassword");

		DataSourcePasswordField newPassword = new DataSourcePasswordField(
				"newPassword", localizeConstant.loginNewPasswordGroup());

		setFields(newPassword);
	}
}

