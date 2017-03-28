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
