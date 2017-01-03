package ej.demo.ui.widget.keyboard;

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
		return "ASDFGHJKLM"; //$NON-NLS-1$
	}

	@Override
	public String getThirdRow() {
		return "ZXCVBN"; //$NON-NLS-1$
	}
}
