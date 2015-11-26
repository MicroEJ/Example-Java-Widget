/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.microej.demo.widgets.style;

import ej.widget.basic.picto.PictoTheme;

/**
 * The picto theme used in the application.
 */
public class WidgetsPictoTheme implements PictoTheme {

	private static final int PROGRESS_BAR = 0x25;
	private static final int SLIDER_CURSOR = 0x27;
	private static final int SLIDER_BAR = 0x28;
	private static final int RADIO_UNCHECKED = 0x21;
	private static final int RADIO_CHECKED = 0x22;
	private static final int SWITCH_UNCHECKED = 0x25;
	private static final int SWITCH_CHECKED = 0x26;
	private static final int CHECKBOX_UNCHECKED = 0x23;
	private static final int CHECKBOX_CHECK = 0x24;

	@Override
	public char getCheckboxCheckedCharacter() {
		return CHECKBOX_CHECK;
	}

	@Override
	public char getCheckboxUncheckedCharacter() {
		return CHECKBOX_UNCHECKED;
	}

	@Override
	public char getSwitchCheckedCharacter() {
		return SWITCH_CHECKED;
	}

	@Override
	public char getSwitchUncheckedCharacter() {
		return SWITCH_UNCHECKED;
	}

	@Override
	public char getRadioButtonCheckedCharacter() {
		return RADIO_CHECKED;
	}

	@Override
	public char getRadioButtonUncheckedCharacter() {
		return RADIO_UNCHECKED;
	}

	@Override
	public char getSliderBarCharacter() {
		return SLIDER_BAR;
	}

	@Override
	public char getSliderCursorCharacter() {
		return SLIDER_CURSOR;
	}

	@Override
	public char getProgressBarCharacter(float percentComplete) {
		return PROGRESS_BAR;
	}

	@Override
	public char[] getIndeterminateProgressBarPictos() {
		return new char[] { PROGRESS_BAR };
	}
}
