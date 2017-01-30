package com.microej.demo.widget.keyboard;

import ej.widget.keyboard.Layout;

/**
 * Represents an upper case layout
 */
public class UpperCaseLayout implements Layout {

	@Override
	public String getFirstRow() {
		return "QWERTYUIOP"; //$NON-NLS-1$
	}

	@Override
	public String getSecondRow() {
		return "ASDFGHJKL\00"; //$NON-NLS-1$
	}

	@Override
	public String getThirdRow() {
		return "ZXCVBNM"; //$NON-NLS-1$
	}
}
