/*
 * Java
 *
 * Copyright 2017-2019 MicroEJ Corp. All rights reserved.
 * For demonstration purpose only.
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
 */
package ej.widget.chart.format;

/**
 * Default implementation of chart format.
 */
public class DefaultChartFormat implements ChartFormat {

	@Override
	public String formatShort(float value) {
		return Float.toString(value);
	}

	@Override
	public String formatLong(float value) {
		return Float.toString(value);
	}

}
