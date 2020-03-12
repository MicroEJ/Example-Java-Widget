/*
 * Copyright 2014-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package com.microej.demo.widget.page;

import ej.widget.basic.AbstractSlider;
import ej.widget.basic.picto.PictoCheck;
import ej.widget.basic.picto.PictoRadio;
import ej.widget.basic.picto.PictoSlider;
import ej.widget.basic.picto.PictoSwitch;
import ej.widget.composed.Toggle;
import ej.widget.composed.ToggleWrapper;
import ej.widget.toggle.RadioModel;

/**
 * This page illustrates the widgets rendered with some pictos.
 */
public class PictoWidgetPage extends WidgetPage {

	/**
	 * Creates a picto widget page.
	 */
	public PictoWidgetPage() {
		super("Pictos"); //$NON-NLS-1$
	}

	@Override
	protected ToggleWrapper newCheckBox(String string) {
		return new Toggle(new PictoCheck(), string);
	}

	@Override
	protected ToggleWrapper newRadioButton(String string) {
		return new Toggle(new RadioModel(), new PictoRadio(), string);
	}

	@Override
	protected ToggleWrapper newSwitch(String string) {
		return new Toggle(new PictoSwitch(), string);
	}

	@Override
	protected AbstractSlider newSlider(int min, int max, int initial) {
		return new PictoSlider(min, max, initial);
	}
}
