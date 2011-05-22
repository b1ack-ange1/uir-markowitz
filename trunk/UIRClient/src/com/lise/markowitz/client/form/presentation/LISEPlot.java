package com.lise.markowitz.client.form.presentation;

import java.util.ArrayList;
import java.util.Date;

import ca.nanometrics.gflot.client.Axis;
import ca.nanometrics.gflot.client.DataPoint;
import ca.nanometrics.gflot.client.PlotModel;
import ca.nanometrics.gflot.client.SeriesHandler;
import ca.nanometrics.gflot.client.SimplePlot;
import ca.nanometrics.gflot.client.options.AxisOptions;
import ca.nanometrics.gflot.client.options.PlotOptions;
import ca.nanometrics.gflot.client.options.TickFormatter;

import com.google.gwt.user.client.ui.FlowPanel;
import com.lise.markowitz.client.db.datasources.ValueTickerDataSource;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class LISEPlot extends FlowPanel {
	public LISEPlot(long from, long till, ListGridRecord[] records) {
		super();

		PlotModel model = new PlotModel();
		PlotOptions plotOptions = new PlotOptions();

		// add tick formatter to the options
		plotOptions.setXAxisOptions(new AxisOptions().setTicks(12)
				.setTickFormatter(new TickFormatter() {

					@Override
					public String formatTickValue(double tickValue, Axis axis) {
						return (new Date((long) tickValue)).toString();
					}
				}));

		ArrayList<Integer> tickerList = new ArrayList<Integer>();
		for (int i = 0; i < records.length; i++) {
			tickerList.add(Integer.parseInt(records[i]
					.getAttributeAsString("cacheId")));
		}
		ValueTickerDataSource ds = ValueTickerDataSource.getInstance(from,
				till, tickerList);

		ds.fetchData(null, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData,
					DSRequest request) {
				SC.logWarn(rawData.toString());

			}
		});

		// create a series
		SeriesHandler handler = model
				.addSeries(
						"Ottawa's Month Temperatures (Daily Average in &deg;C)",
						"blue");

		// add data
		handler.add(new DataPoint(1, -10.5));
		handler.add(new DataPoint(2, -8.6));
		handler.add(new DataPoint(3, -2.4));
		handler.add(new DataPoint(4, 6));
		handler.add(new DataPoint(5, 13.6));
		handler.add(new DataPoint(6, 18.4));
		handler.add(new DataPoint(7, 21));
		handler.add(new DataPoint(8, 19.7));
		handler.add(new DataPoint(9, 14.7));
		handler.add(new DataPoint(10, 8.2));
		handler.add(new DataPoint(11, 1.5));
		handler.add(new DataPoint(12, -6.6));

		// create the plot
		SimplePlot plot = new SimplePlot(model, plotOptions);
		add(plot);

	}
}
