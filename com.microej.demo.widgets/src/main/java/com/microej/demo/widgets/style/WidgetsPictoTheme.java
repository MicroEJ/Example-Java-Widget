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

	@Override
	public char getCheckboxCheckedCharacter() {
		return 0x24;
	}

	@Override
	public char getCheckboxUncheckedCharacter() {
		return 0x23;
	}

	@Override
	public char getSwitchCheckedCharacter() {
		return 0x26;
	}

	@Override
	public char getSwitchUncheckedCharacter() {
		return 0x25;
	}

	@Override
	public char getRadioButtonCheckedCharacter() {
		return 0x22;
	}

	@Override
	public char getRadioButtonUncheckedCharacter() {
		return 0x21;
	}

	@Override
	public char getSliderBarCharacter() {
		return 0x28;
	}

	@Override
	public char getSliderCursorCharacter() {
		return 0x27;
	}

	@Override
	public char getProgressBarCharacter(float percentComplete) {
		return 0x25;
	}

	@Override
	public char[] getIndeterminateProgressBarPictos() {
		return new char[] { 0x22, 0x23, 0x24, 0x25, 0x26, 0x27, 0x28, 0x29 };
	}
}
