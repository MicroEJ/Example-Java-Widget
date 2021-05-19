/*
 * Copyright 2021 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.barchart;

import com.microej.demo.widget.barchart.widget.BarChart;
import com.microej.demo.widget.barchart.widget.ChartPoint;
import com.microej.demo.widget.common.DemoColors;
import com.microej.demo.widget.common.Page;

import ej.mwt.Widget;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.outline.UniformOutline;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.ClassSelector;

/**
 * This is the base for the Pages demonstrating the Chart Widgets.
 */
public class BarChartPage implements Page {

	private static final int CLASS_BAR_CHART = 1610;

	private static final int CHART_MARGIN = 10;

	private static final String UNIT = "km"; //$NON-NLS-1$

	private static final float MAX_VALUE = 100f;

	@SuppressWarnings("nls")
	private static final String[] MONTHS = { "January", "February", "March", "April", "May", "June", "July", "August",
			"September", "October", "November", "December" };

	@Override
	public String getName() {
		return "Bar Chart"; //$NON-NLS-1$
	}

	@Override
	public void populateStylesheet(CascadingStylesheet stylesheet) {
		EditableStyle style = stylesheet.getSelectorStyle(new ClassSelector(CLASS_BAR_CHART));
		style.setMargin(new UniformOutline(CHART_MARGIN));
		style.setColor(DemoColors.DEFAULT_FOREGROUND);
		style.setExtraInt(BarChart.ID_GRAPH_LINE_COLOR, DemoColors.ALTERNATE_BACKGROUND);
		style.setExtraInt(BarChart.ID_POINT_COLOR, DemoColors.DEFAULT_FOREGROUND);
		style.setExtraInt(BarChart.ID_POINT_SELECTED_COLOR, DemoColors.CORAL);
	}

	@Override
	public Widget getContentWidget() {
		BarChart chart = new BarChart();
		chart.setUnit(UNIT);
		chart.addClassSelector(CLASS_BAR_CHART);
		fillChart(chart);
		return chart;
	}

	private void fillChart(BarChart chart) {
		for (String month : MONTHS) {
			String name = month.substring(0, 1);
			String fullName = month;
			float value = genRandomValue();
			ChartPoint point = new ChartPoint(name, fullName, value);
			chart.addPoint(point);
		}
	}

	private float genRandomValue() {
		return (float) (Math.random() * MAX_VALUE);
	}

}
