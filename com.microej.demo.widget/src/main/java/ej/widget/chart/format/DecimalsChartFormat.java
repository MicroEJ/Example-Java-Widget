/*
 * Copyright 2017-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.chart.format;

/**
 * This implementation of chart format allows to choose the number of decimals for the short and long formats.
 */
public class DecimalsChartFormat implements ChartFormat {

	private static String DECIMALS_SEPARATOR = "."; //$NON-NLS-1$

	private final int shortDecimals;
	private final int longDecimals;

	/**
	 * Creates a decimals chart format.
	 *
	 * @param shortDecimals
	 *            the number of decimals in the short format.
	 * @param longDecimals
	 *            the number of decimals in the long format.
	 */
	public DecimalsChartFormat(int shortDecimals, int longDecimals) {
		this.shortDecimals = shortDecimals;
		this.longDecimals = longDecimals;
	}

	@Override
	public String formatShort(float value) {
		return getFloatString(value, this.shortDecimals);
	}

	@Override
	public String formatLong(float value) {
		return getFloatString(value, this.longDecimals);
	}

	/**
	 * Converts float to string
	 */
	private static String getFloatString(float value, int decimals) {
		StringBuilder builder = new StringBuilder();
		builder.append((int) value);
		if (decimals > 0) {
			builder.append(DECIMALS_SEPARATOR);
			for (int i = 0; i < decimals; i++) {
				value *= 10.0f;
				builder.append((int) value % 10);
			}
		}
		return builder.toString();
	}

}
