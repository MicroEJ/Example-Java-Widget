/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package com.microej.demo.widget.page;

import com.microej.demo.widget.style.ClassSelectors;

import ej.widget.basic.Button;
import ej.widget.chart.BarChart;
import ej.widget.chart.BasicChart;
import ej.widget.chart.Chart;
import ej.widget.chart.ChartPoint;
import ej.widget.chart.LineChart;
import ej.widget.chart.format.DecimalsChartFormat;
import ej.widget.chart.scale.AdaptiveChartScale;
import ej.widget.container.Split;
import ej.widget.container.util.LayoutOrientation;
import ej.widget.listener.OnClickListener;

/**
 * This page illustrates a chart.
 */
public class ChartPage extends AbstractDemoPage {

	@SuppressWarnings("nls")
	private static final String[] MONTHS = { "January", "February", "March", "April", "May", "June", "July", "August",
			"September", "October", "November", "December" };

	private static final String UNIT = "km"; //$NON-NLS-1$
	private static final String SWITCH_TO_BAR = "Switch to bar chart"; //$NON-NLS-1$
	private static final String SWITCH_TO_LINE = "Switch to line chart"; //$NON-NLS-1$
	private static final float SPLIT_RATIO = 0.80f;

	/**
	 * Creates a chart page.
	 */
	public ChartPage() {
		super(false, "Chart"); //$NON-NLS-1$

		Chart chart = new BarChart();
		loadChart(chart);

		final Split split = new Split(LayoutOrientation.VERTICAL, SPLIT_RATIO);

		final Button switchButton = new Button(SWITCH_TO_LINE);
		switchButton.addClassSelector(ClassSelectors.SWITCH_BUTTON);
		switchButton.addOnClickListener(new OnClickListener() {
			private boolean isBar = true;

			@Override
			public void onClick() {
				this.isBar = !this.isBar;
				BasicChart newChart = (this.isBar ? new BarChart() : new LineChart(true, true));
				loadChart(newChart);
				split.setFirstChild(newChart);
				split.requestLayOut();
				switchButton.setText(this.isBar ? SWITCH_TO_LINE : SWITCH_TO_BAR);
			}
		});

		split.setFirstChild(chart);
		split.setLastChild(switchButton);
		setCenterChild(split);
	}

	private void loadChart(Chart chart) {
		// create charts
		chart.addClassSelector(ClassSelectors.CHART);

		// add points
		for (int i = 0; i < MONTHS.length; i++) {
			String name = MONTHS[i].substring(0, 1);
			String fullName = MONTHS[i];
			float value = genRandomValue();
			ChartPoint chartPoint = new ChartPoint(name, fullName, value);
			chart.addPoint(chartPoint);
		}

		// set scale
		chart.setScale(new AdaptiveChartScale(5));
		chart.updateScale();

		// set unit
		chart.setUnit(ChartPage.UNIT);

		// set format
		chart.setFormat(new DecimalsChartFormat(0, 3));
	}

	private float genRandomValue() {
		return (float) (Math.random() * 100.0);
	}
}
