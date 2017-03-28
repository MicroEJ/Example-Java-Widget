package com.microej.demo.widget.keyboard;

import ej.widget.keyboard.Layout;

/**
 * Represents a lower case layout
 */
public class LowerCaseLayout implements Layout {

	@Override
	public String getFirstRow() {
		return "qwertyuiop"; //$NON-NLS-1$
	}

	@Override
	public String getSecondRow() {
		return "asdfghjkl\00"; //$NON-NLS-1$
	}

	@Override
	public String getThirdRow() {
		return "zxcvbnm"; //$NON-NLS-1$
	}
}
