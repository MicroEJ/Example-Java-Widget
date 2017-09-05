/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.microej.demo.widget.page;

import com.microej.demo.widget.style.ClassSelectors;

import ej.mwt.Widget;
import ej.widget.basic.Button;
import ej.widget.chart.BarChart;
import ej.widget.chart.BasicChart;
import ej.widget.chart.Chart;
import ej.widget.chart.ChartPoint;
import ej.widget.chart.LineChart;
import ej.widget.chart.format.DecimalsChartFormat;
import ej.widget.chart.scale.AdaptiveChartScale;
import ej.widget.container.Split;
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

	@Override
	protected String getTitle() {
		return "Chart"; //$NON-NLS-1$
	}

	@Override
	protected Widget createMainContent() {
		Chart chart = new BarChart();
		loadChart(chart);

		final Split split = new Split(false, SPLIT_RATIO);

		final Button switchButton = new Button(SWITCH_TO_LINE);
		switchButton.addClassSelector(ClassSelectors.SWITCH_BUTTON);
		switchButton.addOnClickListener(new OnClickListener() {
			private boolean isBar = true;

			@Override
			public void onClick() {
				this.isBar = !this.isBar;
				BasicChart newChart = (this.isBar ? new BarChart() : new LineChart(false, true));
				loadChart(newChart);
				newChart.onStopAnimation();
				split.setFirst(newChart);
				split.revalidate();
				switchButton.setText(this.isBar ? SWITCH_TO_LINE : SWITCH_TO_BAR);
			}
		});

		split.setFirst(chart);
		split.setLast(switchButton);
		return split;
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
