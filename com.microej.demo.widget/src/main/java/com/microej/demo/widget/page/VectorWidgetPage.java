/*
 * Java
 *
 * Copyright 2014-2019 MicroEJ Corp. All rights reserved.
 * For demonstration purpose only.
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
 */
package com.microej.demo.widget.page;

import ej.widget.basic.drawing.Slider;
import ej.widget.composed.Check;
import ej.widget.composed.Radio;
import ej.widget.composed.Switch;
import ej.widget.composed.ToggleWrapper;

/**
 * This page illustrates the drawn widgets.
 */
public class VectorWidgetPage extends WidgetPage {

	/**
	 * Creates a vector widget page.
	 */
	public VectorWidgetPage() {
		super("Drawings"); //$NON-NLS-1$
	}

	@Override
	protected ToggleWrapper newCheckBox(String string) {
		return new Check(string);
	}

	@Override
	protected ToggleWrapper newRadioButton(String string) {
		return new Radio(string);
	}

	@Override
	protected ToggleWrapper newSwitch(String string) {
		return new Switch(string);
	}

	@Override
	protected Slider newSlider(int min, int max, int initial) {
		return new Slider(min, max, initial);
	}
}
