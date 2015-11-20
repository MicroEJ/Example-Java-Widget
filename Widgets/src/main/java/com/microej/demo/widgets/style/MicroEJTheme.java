/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.microej.demo.widgets.style;

import ej.style.Style;
import ej.widget.basic.DrawingTheme;

/**
 *
 */
public class MicroEJTheme implements DrawingTheme {

	@Override
	public int getSliderBarColor(Style style) {
		return 0xbcbec0;
	}

	@Override
	public int getSliderCompleteColor(Style style) {
		return 0x10bdf1;
	}

	@Override
	public int getSliderCursorColor(Style style) {
		return 0x10bdf1;
	}

	@Override
	public int getProgressBarBarColor(Style style) {
		return 0xbcbec0;
	}

	@Override
	public int getProgressBarCompleteColor(Style style) {
		return 0x10bdf1;
	}

	@Override
	public int getSwitchCheckedBarColor(Style style) {
		return 0x1185a8;
	}

	@Override
	public int getSwitchCheckedCursorColor(Style style) {
		return 0x10bdf1;
	}

	@Override
	public int getSwitchUncheckedBarColor(Style style) {
		return 0xbcbec0;
	}

	@Override
	public int getSwitchUncheckedCursorColor(Style style) {
		return 0xffffff;
	}

	@Override
	public int getCheckboxCheckColor(Style style) {
		return 0xffffff;
	}

	@Override
	public int getCheckboxCheckedInsideColor(Style style) {
		return 0x10bdf1;
	}

	@Override
	public int getCheckboxUncheckedOutlineColor(Style style) {
		return 0x10bdf1;
	}

	@Override
	public int getRadioButtonCheckColor(Style style) {
		return 0x10bdf1;
	}

	@Override
	public int getRadioButtonCheckedOutlineColor(Style style) {
		return 0x10bdf1;
	}

	@Override
	public int getRadioButtonUncheckedOutlineColor(Style style) {
		return 0xbcbec0;
	}

}
