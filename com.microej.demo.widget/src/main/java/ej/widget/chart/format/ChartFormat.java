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
