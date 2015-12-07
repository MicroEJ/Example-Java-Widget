/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.microej.demo.widgets.page;

import ej.widget.basic.AbstractSlider;
import ej.widget.basic.Toggle;
import ej.widget.basic.picto.PictoCheck;
import ej.widget.basic.picto.PictoRadio;
import ej.widget.basic.picto.PictoSlider;
import ej.widget.basic.picto.PictoSwitch;

/**
 * This page illustrates the widgets rendered with some pictos.
 */
public class PictoWidgetPage extends WidgetPage {

	@Override
	protected String getTitle() {
		return "Pictos"; //$NON-NLS-1$
	}

	@Override
	protected Toggle newCheckBox(boolean checked) {
		return new PictoCheck(checked);
	}

	@Override
	protected Toggle newSwitch(boolean checked) {
		return new PictoSwitch(checked);
	}

	@Override
	protected Toggle newRadioButton(boolean checked) {
		return new PictoRadio(checked);
	}

	@Override
	protected AbstractSlider newSlider(int min, int max, int initial) {
		return new PictoSlider(min, max, initial);
	}
}
