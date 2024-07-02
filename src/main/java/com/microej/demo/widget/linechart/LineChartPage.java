/*
 * Copyright 2021-2024 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.linechart;

import com.microej.demo.widget.common.DemoColors;
import com.microej.demo.widget.common.Page;
import com.microej.demo.widget.linechart.widget.ChartPoint;
import com.microej.demo.widget.linechart.widget.LineChart;

import ej.bon.Immutables;
import ej.mwt.Widget;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.outline.UniformOutline;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.ClassSelector;

/**
 * This is the base for the Pages demonstrating the Chart Widgets.
 */
public class LineChartPage implements Page {

	private static final int CLASS_LINE_CHART = 1600;

	private static final int CHART_MARGIN = 10;
	private static final int POINT_RADIUS = 4;

	private static final String UNIT = "km"; //$NON-NLS-1$

	private static final float MAX_VALUE = 100f;

	private static final String[] MONTHS = (String[]) Immutables.get("Months"); //$NON-NLS-1$

	@Override
	public String getName() {
		return "Line Chart"; //$NON-NLS-1$
	}

	@Override
	public void populateStylesheet(CascadingStylesheet stylesheet) {
		EditableStyle style = stylesheet.getSelectorStyle(new ClassSelector(CLASS_LINE_CHART));
		style.setMargin(new UniformOutline(CHART_MARGIN));
		style.setColor(DemoColors.DEFAULT_FOREGROUND);
		style.setExtraInt(LineChart.ID_GRAPH_LINE_COLOR, DemoColors.ALTERNATE_BACKGROUND);
		style.setExtraInt(LineChart.ID_POINT_COLOR, DemoColors.DEFAULT_FOREGROUND);
		style.setExtraInt(LineChart.ID_POINT_SELECTED_COLOR, DemoColors.CORAL);
		style.setExtraInt(LineChart.ID_LINE_COLOR, DemoColors.DEFAULT_BORDER);
		style.setExtraInt(LineChart.ID_POINT_RADIUS, POINT_RADIUS);
	}

	@Override
	public Widget getContentWidget() {
		LineChart chart = new LineChart(true);
		chart.addClassSelector(CLASS_LINE_CHART);
		chart.setUnit(UNIT);
		fillChart(chart);
		return chart;
	}

	private void fillChart(LineChart chart) {
		for (String month : MONTHS) {
			String name = month.substring(0, 1);
			float value = genRandomPositiveValue();
			ChartPoint point = new ChartPoint(name, month, value);
			chart.addPoint(point);
		}
	}

	private float genRandomPositiveValue() {
		return (float) (Math.random() * MAX_VALUE);
	}

}
