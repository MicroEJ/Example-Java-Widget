package ej.widget.chart.format;

public class DecimalsChartFormat implements ChartFormat {

	private static String DECIMALS_SEPARATOR = ".";

	private final int shortDecimals;
	private final int longDecimals;

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
	 * Sets the decimals separator
	 */
	private static void setDecimalsSeparator(String decimalsSeparator) {
		DECIMALS_SEPARATOR = decimalsSeparator;
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
