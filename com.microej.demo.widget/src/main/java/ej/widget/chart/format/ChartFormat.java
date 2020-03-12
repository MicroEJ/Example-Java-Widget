/*
 * Copyright 2017-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
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
