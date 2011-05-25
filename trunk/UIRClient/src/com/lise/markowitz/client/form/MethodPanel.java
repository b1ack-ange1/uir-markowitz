package com.lise.markowitz.client.form;

import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class MethodPanel extends WorkPanel {
	private String code;
	private TextItem epsilon;
	private TextItem expectedProfit;
	private TextItem beta;
	private TextItem nMax;
	private boolean showN = false, showBeta = false;
	private Label risk, riskCurrent, riskCounted, profit, profitCurrent,
			profitCounted;

	public MethodPanel(String code) {
		super();
		this.code = code;

	}

	@Override
	public void buildToolStrip() {

		ToolStripButton count = new ToolStripButton(
				localizeConstant.countSolution());
		count.setIcon("./actions/totalcfg.gif");
		count.setDisabled(false);
		count.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

			}

		});
		toolStrip.addButton(count);

		toolStrip.addSeparator();

		epsilon = new TextItem();
		epsilon.setTitle(localizeConstant.epsilon());
		toolStrip.addFormItem(epsilon);

		toolStrip.addSeparator();

		expectedProfit = new TextItem();
		expectedProfit.setTitle(localizeConstant.expectedProfit());
		toolStrip.addFormItem(expectedProfit);

		toolStrip.addSeparator();

		nMax = new TextItem();
		nMax.setTitle(localizeConstant.nMax());
		toolStrip.addFormItem(nMax);

		toolStrip.addSeparator();

		beta = new TextItem();
		beta.setTitle(localizeConstant.beta());
		toolStrip.addFormItem(beta);

	}

	@Override
	public void buildPanel() {
		risk = new Label(localizeConstant.risk());
		riskCurrent = new Label(localizeConstant.currentRisk() + " = ");
		riskCounted = new Label(localizeConstant.countedRisk() + " = ");

		profit = new Label(localizeConstant.profit());
		profitCurrent = new Label(localizeConstant.currentProfit() + " = ");
		profitCounted = new Label(localizeConstant.countedProfit() + " = ");

		panel.addMember(risk);
		panel.addMember(riskCurrent);
		panel.addMember(riskCounted);
		panel.addMember(profit);
		panel.addMember(profitCurrent);
		panel.addMember(profitCounted);
		risk.setAutoHeight();
		riskCurrent.setAutoHeight();
		riskCounted.setAutoHeight();
		profit.setAutoHeight();
		profitCurrent.setAutoHeight();
		profitCounted.setAutoHeight();
		panel.addMember(new Label("qqqqq"));
		// грид

		IButton save = new IButton(localizeConstant.saveNewKoeff());
		save.setAutoFit(true);
		panel.addMember(save);
	}

	@Override
	public void checkControls() {
		if (!showN)
			nMax.setVisible(false);
		if (!showBeta)
			beta.setVisible(false);

	}

	public void setNewProfit(String profit) {
		profitCurrent.setContents(localizeConstant.currentProfit() + " = "
				+ profit);
	}

	public void setNewRisk(String risk) {
		riskCurrent = new Label(localizeConstant.currentRisk() + " = " + risk);
	}

	public void setNewProfitCounted(String profit) {
		profitCounted.setContents(localizeConstant.countedProfit() + " = "
				+ profit);
	}

	public void setNewRiskCounted(String risk) {
		riskCounted = new Label(localizeConstant.countedRisk() + " = " + risk);
	}

}
