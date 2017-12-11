/*
 * Java
 *
 * Copyright 2017 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package ej.widget.chart.format;

public interface ChartFormat {

	/**
	 * Calculates the string to show on the scale
	 */
	public abstract String formatShort(float value);

	/**
	 * Calculates the string to show when the point is selected
	 */
	public abstract String formatLong(float value);

}
