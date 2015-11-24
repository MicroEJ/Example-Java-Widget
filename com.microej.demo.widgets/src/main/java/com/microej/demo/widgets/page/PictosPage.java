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
 * This page illustrates the widgets using some pictos.
 */
public class PictosPage extends WidgetPage {

	@Override
	protected String getTitle() {
		return "Pictos"; //$NON-NLS-1$
	}

	@Override
	protected Toggle newCheckBox(boolean checked) {
		PictoCheck checkbox = new PictoCheck(checked);
		return checkbox;
	}

	@Override
	protected Toggle newSwitch(boolean checked) {
		PictoSwitch switch_ = new PictoSwitch(checked);
		return switch_;
	}

	@Override
	protected Toggle newRadioButton(boolean checked) {
		PictoRadio radioButton = new PictoRadio(checked);
		return radioButton;
	}

	@Override
	protected AbstractSlider newSlider(int min, int max, int initial) {
		PictoSlider slider = new PictoSlider(min, max, initial);
		return slider;
	}
}
