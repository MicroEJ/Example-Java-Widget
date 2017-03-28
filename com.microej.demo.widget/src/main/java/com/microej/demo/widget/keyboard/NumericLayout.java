package com.microej.demo.widget.keyboard;

import ej.widget.keyboard.Layout;

/**
 * Represents a numeric layout
 */
public class NumericLayout implements Layout {

	@Override
	public String getFirstRow() {
		return "1234567890"; //$NON-NLS-1$
	}

	@Override
	public String getSecondRow() {
		return "-/:;()$&@\""; //$NON-NLS-1$
	}

	@Override
	public String getThirdRow() {
		return ".,?!\'§¤"; //$NON-NLS-1$
	}
}
