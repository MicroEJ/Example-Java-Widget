/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.microej.demo.widgets.page;

import ej.widget.basic.Check;
import ej.widget.basic.Radio;
import ej.widget.basic.Slider;
import ej.widget.basic.Switch;
import ej.widget.basic.Toggle;

/**
 * This page illustrates the drawn widgets.
 */
public class DrawingsPage extends WidgetPage {

	@Override
	protected String getTitle() {
		return "Drawings"; //$NON-NLS-1$
	}

	@Override
	protected Toggle newCheckBox(boolean checked) {
		Check checkbox = new Check(checked);
		return checkbox;
	}

	@Override
	protected Toggle newSwitch(boolean checked) {
		Switch switch_ = new Switch(checked);
		return switch_;
	}

	@Override
	protected Toggle newRadioButton(boolean checked) {
		Radio radioButton = new Radio(checked);
		return radioButton;
	}

	@Override
	protected Slider newSlider(int min, int max, int initial) {
		Slider slider = new Slider(min, max, initial);
		return slider;
	}
}
