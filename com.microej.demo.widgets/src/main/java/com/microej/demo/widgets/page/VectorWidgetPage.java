/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.microej.demo.widgets.page;

import ej.widget.basic.drawing.Slider;
import ej.widget.composed.Check;
import ej.widget.composed.Radio;
import ej.widget.composed.Switch;
import ej.widget.composed.ToggleComposite;

/**
 * This page illustrates the drawn widgets.
 */
public class VectorWidgetPage extends WidgetPage {

	@Override
	protected String getTitle() {
		return "Drawings"; //$NON-NLS-1$
	}

	@Override
	protected ToggleComposite newCheckBox(String string) {
		return new Check(string);
	}

	@Override
	protected ToggleComposite newRadioButton(String string) {
		return new Radio(string);
	}

	@Override
	protected ToggleComposite newSwitch(String string) {
		return new Switch(string);
	}

	@Override
	protected Slider newSlider(int min, int max, int initial) {
		return new Slider(min, max, initial);
	}
}
