package com.lise.markowitz.client.form;

import com.google.gwt.core.client.GWT;
import com.lise.markowitz.client.db.datasources.ChangePasswordDataSource;
import com.lise.markowitz.client.localization.LocalizeConstant;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.events.ItemChangedEvent;
import com.smartgwt.client.widgets.form.events.ItemChangedHandler;
import com.smartgwt.client.widgets.form.events.ItemKeyPressEvent;
import com.smartgwt.client.widgets.form.events.ItemKeyPressHandler;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.validator.MatchesFieldValidator;
import com.smartgwt.client.widgets.layout.HLayout;

public class ChangePasswordForm extends Window {
	private LocalizeConstant localizeConstant = GWT
			.create(LocalizeConstant.class);

	private static ChangePasswordForm changePasswordWindow;

	public static ChangePasswordForm getInstance() {
		if (changePasswordWindow == null)
			changePasswordWindow = new ChangePasswordForm();
		return changePasswordWindow;
	}

	protected DynamicForm newPassForm = new DynamicForm();
	protected IButton okButton = new IButton(localizeConstant.ok());
	protected final HLayout buttons = new HLayout(5);

	protected ChangePasswordForm() {
		super();

		this.setTitle(localizeConstant.cpf_title());

		this.setIsModal(true);
		this.setCanDragReposition(true);
		this.setCanDragResize(false);

		this.setKeepInParentRect(true);

		this.setHeaderControls(HeaderControls.HEADER_ICON,
				HeaderControls.HEADER_LABEL, HeaderControls.CLOSE_BUTTON);

		newPassForm = new DynamicForm();
		newPassForm.setDataSource(ChangePasswordDataSource.getInstance());

		newPassForm.setPadding(5);
		newPassForm.setMargin(5);
		newPassForm.setWrapItemTitles(false);
		newPassForm.setCellSpacing(4);

		PasswordItem newPassword = new PasswordItem("newPassword");
		newPassword.setRequired(true);
		PasswordItem newPassword2 = new PasswordItem("newPassword2",
				localizeConstant.loginPassRepeate());
		newPassword2.setRequired(true);

		MatchesFieldValidator matchesValidator = new MatchesFieldValidator();
		matchesValidator.setOtherField("newPassword");
		matchesValidator.setErrorMessage(localizeConstant.loginPassDontMatch());
		newPassword2.setValidators(matchesValidator);

		newPassForm.setItems(newPassword, newPassword2);

		newPassForm.addItemChangedHandler(new ItemChangedHandler() {
			public void onItemChanged(ItemChangedEvent event) {
				okButton.setDisabled(!newPassForm.valuesAreValid(false));
			}
		});

		newPassForm.addItemKeyPressHandler(new ItemKeyPressHandler() {
			public void onItemKeyPress(ItemKeyPressEvent event) {
				if (event.getKeyName().equalsIgnoreCase("Enter"))
					okButtonClick();
			}
		});
		this.addItem(newPassForm);

		buttons.setPadding(5);
		buttons.setLayoutAlign(Alignment.CENTER);
		buttons.setAutoWidth();
		buttons.setAutoHeight();

		okButton.setDisabled(true);
		okButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				okButtonClick();
			}
		});
		buttons.addMember(okButton);

		IButton closeButton = new IButton(localizeConstant.cancel());
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
			}
		});

		buttons.addMember(closeButton);

		this.addItem(buttons);

		this.setAutoSize(true);
		this.setAutoCenter(true);
	}

	/**
	 * Вызывается по нажатию кнопки "Ок".
	 */
	protected void okButtonClick() {
		if (newPassForm.validate())
			newPassForm.saveData(new DSCallback() {
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						hide();
					}
				}
			});
	}

}
