/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.widgets.page;

import com.is2t.demo.widgets.widget.theme.Pictos;

import ej.mwt.MWT;
import ej.mwt.Widget;
import ej.widgets.composites.BorderComposite;
import ej.widgets.widgets.LookExtension;
import ej.widgets.widgets.label.TitleLabel;
import ej.widgets.widgets.spinner.MultipleSpinner;
import ej.widgets.widgets.spinner.Value;
import ej.widgets.widgets.spinner.int_.EndlessIntegerValue;
import ej.widgets.widgets.spinner.int_.IntegerWheel;

/**
 * Page illustrating the #MultipleSpinner widget.
 */
public class SecurityPage extends WidgetsPage {

	private static final int MAX_DIGIT = 9;
	private static final int[] INITIAL_CODE = { 1, 9, 0, 2 };

	@Override
	protected String getTitle() {
		return "Security";
	}

	@Override
	protected char getPictoTitle() {
		return Pictos.SECURITY;
	}

	@Override
	protected boolean canGoBack() {
		return true;
	}

	@Override
	protected Widget createMainContent() {
		BorderComposite mainContentLayout = new BorderComposite();

		TitleLabel titleLabel = new TitleLabel("Set your code");
		titleLabel.setFontSize(LookExtension.GET_MEDIUM_FONT_INDEX);
		mainContentLayout.addAt(titleLabel, MWT.TOP);

		// One wheel per digit.
		MultipleSpinner codeSpinner = new MultipleSpinner();
		for (int digitIndex = 0; digitIndex < INITIAL_CODE.length; digitIndex++) {
			codeSpinner.add(createCodeDigit(INITIAL_CODE[digitIndex]));
		}
		mainContentLayout.add(codeSpinner);

		return mainContentLayout;
	}

	/**
	 * Creates a endless wheel formed with the digits.
	 * 
	 * @param initialValue
	 *            the initial value of the wheel.
	 * @return created wheel.
	 */
	private static IntegerWheel createCodeDigit(int initialValue) {
		Value<Integer> codeDigit = new EndlessIntegerValue(0, MAX_DIGIT, 1, initialValue, "");
		IntegerWheel codeDigitWheel = new IntegerWheel();
		codeDigitWheel.setModel(codeDigit);
		return codeDigitWheel;
	}
}
