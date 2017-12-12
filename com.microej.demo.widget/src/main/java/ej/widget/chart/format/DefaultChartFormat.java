/*
 * Java
 *
 * Copyright 2017 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package ej.widget.chart.format;

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
