/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.microej.demo.widgets.style;

import ej.style.Style;
import ej.widget.basic.picto.PictoTheme;

/**
 *
 */
public class MicroEJPictoTheme implements PictoTheme {

	@Override
	public char getCheckboxCheckedCharacter() {
		return 0x24;
	}

	@Override
	public int getCheckboxCheckedColor(Style style) {
		return 0x10bdf1;
	}

	@Override
	public char getCheckboxUncheckedCharacter() {
		return 0x23;
	}

	@Override
	public int getCheckboxUncheckedColor(Style style) {
		return 0xbcbec0;
	}

	@Override
	public char getSwitchCheckedCharacter() {
		return 0x26;
	}

	@Override
	public int getSwitchCheckedColor(Style style) {
		return 0x10bdf1;
	}

	@Override
	public char getSwitchUncheckedCharacter() {
		return 0x25;
	}

	@Override
	public int getSwitchUncheckedColor(Style style) {
		return 0xbcbec0;
	}

	@Override
	public char getRadioButtonCheckedCharacter() {
		return 0x22;
	}

	@Override
	public int getRadioButtonCheckedColor(Style style) {
		return 0x10bdf1;
	}

	@Override
	public char getRadioButtonUncheckedCharacter() {
		return 0x21;
	}

	@Override
	public int getRadioButtonUncheckedColor(Style style) {
		return 0xbcbec0;
	}

	@Override
	public char getSliderBarCharacter() {
		return 0x28;
	}

	@Override
	public int getSliderBarColor(Style style) {
		return 0xbcbec0;
	}

	@Override
	public char getSliderCursorCharacter() {
		return 0x27;
	}

	@Override
	public int getSliderCursorColor(Style style) {
		return 0x10bdf1;
	}

	@Override
	public char getProgressBarCharacter(float percentComplete) {
		return 0x25;
	}

	@Override
	public int getProgressBarColor(float percentComplete, Style style) {
		return 0x10bdf1;
	}

	@Override
	public char[] getIndeterminateProgressBarPictos() {
		return new char[] { 0x22, 0x23, 0x24, 0x25, 0x26, 0x27, 0x28, 0x29 };
	}

	@Override
	public int getIndeterminateProgressBarColor(Style style) {
		return 0x10bdf1;
	}
}
