package com.lise.markowitz.client.form;


import java.util.Date;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window.Location;
import com.lise.markowitz.client.db.datasources.LoginDataSource;
import com.lise.markowitz.client.localization.Localize;
import com.lise.markowitz.client.utils.Logger;
import com.lise.markowitz.client.view.ClientPanel;
import com.lise.markowitz.client.view.MainDynamicPanel;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.events.ItemKeyPressEvent;
import com.smartgwt.client.widgets.form.events.ItemKeyPressHandler;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.validator.MatchesFieldValidator;
import com.smartgwt.client.widgets.layout.HLayout;

/**
 * Форма входа в систему.
 * 
 * @see ru.softlab.rsdh.client.db.datasources.DataBasesDataSource
 * @see ru.softlab.rsdh.client.db.datasources.LoginDataSource
 */
public class LoginForm extends Window {
	private static LoginForm loginForm;
	private static boolean firstShow = true;

	private ValuesManager vm;
	private Label reloginLabel;
	private TextItem user;
	private PasswordItem password;
	private DynamicForm newPassForm;
	private IButton cancel;

	public void show() {
		newPassForm.hide();
		for (FormItem item : newPassForm.getFields())
			item.setRequired(false);
		user.setDisabled(!firstShow);
		cancel.setDisabled(firstShow);
		if (firstShow) {
			reloginLabel.hide();
			vm.editNewRecord();
		} else {
			reloginLabel.show();
			password.setValue("");
		}
		super.show();

		if (user.getValue() == null)
			user.focusInItem();
		else
			password.focusInItem();
	}

	private LoginForm() {
		super();
		setTitle(Localize.getInstance().loginTitle());
		setAutoSize(true);
		setIsModal(true);
		setShowModalMask(true);
		setKeepInParentRect(true);

		setHeaderControls(HeaderControls.HEADER_LABEL);// , info);
		setAutoCenter(true);
		buildLoginForm();
		draw();
	}

	private void buildLoginForm() {
		vm = new ValuesManager();
		vm.setDataSource(LoginDataSource.getInstance());

		// Основная форма логина
		final DynamicForm form = new DynamicForm();
		form.setID("loginForm");
		form.setDataSource(LoginDataSource.getInstance());
		form.setValuesManager(vm);

		form.setLayoutAlign(Alignment.CENTER);
		form.setPadding(5);
		form.setWrapItemTitles(false);
		form.setCellSpacing(4);

		user = new TextItem("user");
		password = new PasswordItem("password");
		if (Cookies.getCookie("lastUserName") != null)
			user.setValue(Cookies.getCookie("lastUserName"));

		form.setFields(user, password);

		form.addItemKeyPressHandler(new ItemKeyPressHandler() {
			public void onItemKeyPress(ItemKeyPressEvent event) {
				if (event.getKeyName().equalsIgnoreCase("Enter"))
					okButtonClick();
			}
		});

		// Форма нового пароля
		newPassForm = new DynamicForm();
		newPassForm.setID("newPassForm");
		newPassForm.setDataSource(LoginDataSource.getInstance());
		newPassForm.setValuesManager(vm);

		newPassForm.setIsGroup(true);
		newPassForm.setGroupTitle(Localize.getInstance()
				.loginNewPasswordGroup());
		newPassForm.setWidth100();
		newPassForm.setPadding(5);
		newPassForm.setMargin(5);
		newPassForm.setWrapItemTitles(false);
		newPassForm.setCellSpacing(4);

		PasswordItem newPassword = new PasswordItem("newPassword", Localize
				.getInstance().loginNewPassEnter());
		PasswordItem newPassword2 = new PasswordItem("newPassword2", Localize
				.getInstance().loginPassRepeate());

		MatchesFieldValidator matchesValidator = new MatchesFieldValidator();
		matchesValidator.setOtherField("newPassword");
		matchesValidator.setErrorMessage(Localize.getInstance()
				.loginPassDontMatch());
		newPassword2.setValidators(matchesValidator);

		newPassForm.setItems(newPassword, newPassword2);

		newPassForm.addItemKeyPressHandler(new ItemKeyPressHandler() {
			public void onItemKeyPress(ItemKeyPressEvent event) {
				if (event.getKeyName().equalsIgnoreCase("Enter"))
					okButtonClick();
			}
		});

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
		cancel = new IButton(Localize.getInstance().cancel());
		cancel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Location.reload();
			}
		});

		buttons.addMember(ok);
		buttons.addMember(cancel);

		// TODO Translate
		reloginLabel = new Label("<font color=\"red\">"
				+ Localize.getInstance().loginDisconnectMessage() + "</font>");
		reloginLabel.setAlign(Alignment.CENTER);
		reloginLabel.setAutoHeight();
		reloginLabel.setWrap(false);

		this.addItem(reloginLabel);
		this.addItem(form);
		this.addItem(newPassForm);
		this.addItem(buttons);
	}

	protected void okButtonClick() {
		if (vm.validate()) {
			login();
		}
	}

	public static LoginForm getInstance() {
		if (loginForm == null)
			loginForm = new LoginForm();
		return loginForm;
	}

	protected void login() {
		vm.saveData(new DSCallback() {
			public void execute(DSResponse response, Object rawData,
					DSRequest request) {
				Logger.log("LoginForm.saveData.DSCallback");
				Logger.echoAll(response.getJsObj());
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Date dt = new Date();
					dt.setTime(dt.getTime() + (1000 * 60 * 60 * 24 * 7));// 7
					// дней
					Cookies.setCookie("lastUserName", user.getValue()
							.toString(), dt);
					
					RPCManager.resendTransaction();
					hide();
					ClientPanel.getInstance().setLogin(Cookies.getCookie("lastUserName"));

					if (firstShow)
						new PortfolioPickerForm().show();

					firstShow = false;
					
				} else {
					if (rawData.toString().contains("ORA-28001")) {
						response.setStatus(DSResponse.STATUS_SUCCESS);

						newPassForm.show();
						for (FormItem item : newPassForm.getFields())
							item.setRequired(true);

						markForRedraw();
					}
				}
			}
		});
	}

	public String getConnectionDesc() {
		if (Cookies.getCookie("lastUserName") != null)
			return Cookies.getCookie("lastUserName") + "@"
					+ Cookies.getCookie("lastPortfolio");
		else
			return "";
	}
}
