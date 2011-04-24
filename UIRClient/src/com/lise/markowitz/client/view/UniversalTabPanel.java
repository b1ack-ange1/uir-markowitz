package com.lise.markowitz.client.view;

import java.util.ArrayList;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.lise.markowitz.client.form.WorkPanel;
import com.lise.markowitz.client.localization.Localize;
import com.lise.markowitz.client.utils.Logger;
import com.lise.markowitz.client.utils.Util;
import com.smartgwt.client.types.DragAppearance;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.EventHandler;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.DoubleClickEvent;
import com.smartgwt.client.widgets.events.DoubleClickHandler;
import com.smartgwt.client.widgets.events.DropEvent;
import com.smartgwt.client.widgets.events.DropHandler;
import com.smartgwt.client.widgets.events.DropMoveEvent;
import com.smartgwt.client.widgets.events.DropMoveHandler;
import com.smartgwt.client.widgets.events.IconClickEvent;
import com.smartgwt.client.widgets.events.IconClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.ImgTab;

public class UniversalTabPanel extends VLayout {
	private static final String tabElementIdSuffix = "_tabElement";

	private class TabElement extends ImgTab {
		private Canvas linkedCanvas;
		private TabElement self = this;
		private String fullTitle = "";
		private String drawTitle = "";

		public TabElement(Canvas member) {
			super();
			Logger.log("tabElement1");
			this.setSkinImgDir("images/Tab/top/");
			Logger.log("tabElement2");
			linkedCanvas = member;
			this.setID(member.getID() + tabElementIdSuffix);
			Logger.log("tabElement3");
			drawTitle = fullTitle = member.getGroupTitle();
			Logger.log("tabElement4");
			if (fullTitle.length() > 33) {
				this.setPrompt(fullTitle);
				drawTitle = drawTitle.substring(0, 30) + "...";
			}
			Logger.log("tabElement5");
			String icon = "";

			this.setTitle(icon + drawTitle);

			this.setWidth(Util.getTextWidth("<span class='tabTitleSelected'>"
					+ icon + drawTitle + "</span>") + 30);

			if (!member.getGroupTitle().equals(
					Localize.getInstance().welcomePage())) {
				this.setIconOrientation("right");
				this.setIconAlign("right");
				Logger.log("tabElement6");
				this.setIcon(Page.getSkinImgDir() + "TabSet/close.png");
				this.setIconSize(10);
				Logger.log("tabElement7");
				this.addIconClickHandler(new IconClickHandler() {
					@Override
					public void onIconClick(IconClickEvent event) {
						getUniversalTabPanel().removeMember(linkedCanvas);
						linkedCanvas.destroy();
					}
				});
			}

			this.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					focusTab(linkedCanvas);
				}
			});
			
			member.setGroupTitle("");
		}

		public void select() {
			super.select();
			linkedCanvas.setVisible(true);
		}

		public void deselect() {
			super.deselect();
			linkedCanvas.setVisible(false);
		}

		public Canvas getLinkedCanvas() {
			return linkedCanvas;
		}

		public UniversalTabPanel getUniversalTabPanel() {
			return (UniversalTabPanel) this.getParentElement()
					.getParentElement();
		}

		public void refreshRadioGroup() {
			try {
				this.addToRadioGroup(getUniversalTabPanel().getID()
						+ "_tabGroup");
			} catch (Exception e) {
				SC.logWarn(e.getLocalizedMessage());
				this.addToRadioGroup("null_tabGroup");
			}
		}

		@Override
		public void destroy() {
			try {

			} catch (Exception e) {
				SC.logWarn(e.getLocalizedMessage());
			} finally {
				super.destroy();
			}
		}

	}

	private static UniversalTabPanel singleton = null;

	private HStack tabStack;

	public static UniversalTabPanel getSingleton() {
		if (singleton == null)
			singleton = new UniversalTabPanel();
		return singleton;
	}

	public UniversalTabPanel() {
		super();
		Logger.log("Создание панели.");
		this.setWidth100();
		this.setHeight100();

		this.setBackgroundColor("white");

		this.setOverflow(Overflow.HIDDEN);
		Logger.log("Создание панели закладок.");
		tabStack = new HStack();
		tabStack.setWidth100();
		tabStack.setHeight(24);
		super.addMember(tabStack);

	}

	public UniversalTabPanel(Canvas member) {
		this();
		addMember(member);
	}

	@Override
	public void addMember(Canvas member) {
		addMember(member, tabStack.getMembers().length);
	}

	@Override
	public void addMember(Canvas member, int position) {
		if (member.getID() == null)
			member.setID(SC.generateID());
		Logger.log("Добавляем новую закладку.");
		try {
			tabStack.addMember(new TabElement(member), position);
		} catch (Exception e) {
			Logger.log(e.getMessage());
		}
		Logger.log("Добавляем нового мембера.");
		super.addMember(member);
		Logger.log("Выделяем новую закладку.");
		Logger.log("" + (member == null));
		try {
			focusTab(member);
		} catch (Exception e) {
			Logger.log(e.getMessage());
		}
	}

	public void removeMember(Canvas member) {
		Logger.log("Удаляем мембера");
		int index = 0;
		super.removeMember(member);
		Logger.log("Удаляем закладку, запоминаем её номер.");
		for (Canvas canvas : tabStack.getMembers())
			if (canvas instanceof TabElement
					&& ((TabElement) canvas).getLinkedCanvas() == member) {
				index = tabStack.getMemberNumber(canvas);
				canvas.destroy();
				break;
			}

		if (tabStack.getMembers().length == 0) {
			Logger.log("Закладок больше нет.");
			if (!this.equals(singleton))
				destroy();
		} else {
			Logger.log("Закладки ещё есть, выделяем следующую или последнюю");
			try {
				if (tabStack.getMembers().length > index)
					focusTab(index);
				else
					focusTab(index - 1);
			} catch (Exception e) {
				SC.logWarn(e.getLocalizedMessage());
			}
		}

		Logger.log("Мембер удален.");
	}

	@SuppressWarnings("deprecation")
	public void focusTab(final String tabId) {
		Logger.log("focusTab1");
		for (Canvas member : this.getMembers()) {
			if (!member.equals(tabStack))
				member.setVisible(false);
		}
		Logger.log("focusTab2");
		final TabElement tabElement = (TabElement) tabStack.getMember(tabId);
		Logger.log("focusTab3");
		if (tabElement != null) {
			DeferredCommand.addCommand(new Command() {
				@Override
				public void execute() {
					tabElement.refreshRadioGroup();
					tabElement.select();
				}
			});
		}
		Logger.log("focusTab4");
	}

	public void focusTab(Canvas member) {
		focusTab(member.getID() + tabElementIdSuffix);
	}

	public void focusTab(int index) {
		focusTab(tabStack.getMember(index).getID());
	}

	@Override
	public void destroy() {
		try {
			Logger.log("Уничтожаемся.");
			Logger.log("this.getParentElement() = " + this.getParentElement());
			Logger.log("((Layout)this.getParentElement()).getMembers().length = "
					+ ((Layout) this.getParentElement()).getMembers().length);

			if (this.getParentElement() instanceof Layout) {
				// Если это был последний элемент в Layout, предыдущему не нужен
				// resizeBar
				Layout parent = (Layout) this.getParentElement();
				int memberCount = parent.getMembers().length;
				int index = parent.getMemberNumber(this);
				if (index == memberCount - 1) {
					Logger.log("Это последняя панель на данном контейнере.");
					parent.getMember(index - 1).setShowResizeBar(false);
					parent.getMember(index - 1).setWidth100();
					parent.getMember(index - 1).setHeight100();
				} else {
					parent.getMember(index + 1).setWidth100();
					parent.getMember(index + 1).setHeight100();
				}
				if (memberCount == 2) {
					Logger.log("На текущем контейнере останется ещё одна панель.");
					parent.removeMember(this);
					Canvas panel = parent.getMember(0);
					panel.setShowResizeBar(parent.getShowResizeBar());
					parent.removeMember(panel);
					// Удаляем не нужный Layout
					Logger.log("Переносим её на родителя контейнера и удаляем контейнер.");
					if (parent.getParentElement() instanceof Layout)
						((Layout) parent.getParentElement()).addMember(panel,
								((Layout) parent.getParentElement())
										.getMemberNumber(parent));
					else
						parent.getParentElement().addChild(panel);
					parent.destroy();
				} else
					parent.reflow();
			}
		} catch (Exception e) {
			SC.logWarn(e.getLocalizedMessage());
		} finally {
			super.destroy();

		}
		Logger.log("Уничтожились.");
	}

}
