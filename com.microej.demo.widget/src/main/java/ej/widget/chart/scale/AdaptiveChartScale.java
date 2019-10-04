/*
 * Java
 *
 * Copyright  2016-2019 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
 */
package ej.widget.chart.scale;

/**
 * Represents a chart scale where the top value depends on the maximum value of the chart points
 */
public class AdaptiveChartScale extends ChartScale {

	/**
	 * Constructor
	 *
	 * @param numValues
	 *            the number of values to show on the scale
	 */
	public AdaptiveChartScale(int numValues) {
		super(numValues);
	}

	/**
	 * Gets the top value of the scale This implementation takes the 2 most-meaningful digits of the max value and
	 * returns the next divisor of getNumValues()
	 *
	 * @return the top value of the scale
	 */
	@Override
	public float getTopValue() {
		int numValues = getNumValues();
		float val = getMaxPointValue();
		float multiplier = 1.0f;
		while (val < 10.0f) {
			val *= 10.0f;
			multiplier /= 10.0f;
		}
		while (val > 10.0f * (2 * numValues)) {
			val /= 10.0f;
			multiplier *= 10.0f;
		}
		int n = (int) Math.ceil(val);
		int extra = n % numValues;
		if (extra > 0) {
			n += numValues - extra;
		}
		return n * multiplier;
	}
}
