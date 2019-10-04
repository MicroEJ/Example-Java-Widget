/*
 * Java
 *
 * Copyright  2017-2019 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
 */
package ej.widget.chart.format;

/**
 *
 */
public interface ChartFormat {

	/**
	 * Calculates the string to show on the scale
	 *
	 * @param value
	 *            the value to format.
	 * @return the formatted value.
	 */
	public abstract String formatShort(float value);

	/**
	 * Calculates the string to show when the point is selected
	 *
	 * @param value
	 *            the value to format.
	 * @return the formatted value.
	 */
	public abstract String formatLong(float value);

}