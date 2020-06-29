/*
 * Copyright 2014-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package com.microej.demo.widget.page;

import com.microej.demo.widget.style.Images;

import ej.widget.basic.AbstractSlider;
import ej.widget.basic.image.ImageCheck;
import ej.widget.basic.image.ImageRadio;
import ej.widget.basic.image.ImageSlider;
import ej.widget.basic.image.ImageSwitch;
import ej.widget.composed.Toggle;
import ej.widget.composed.ToggleWrapper;
import ej.widget.toggle.RadioModel;

/**
 * This page illustrates the widgets rendered with some images.
 */
public class ImageWidgetPage extends WidgetPage {

	/**
	 * Creates an image widget page.
	 */
	public ImageWidgetPage() {
		super("Images"); //$NON-NLS-1$
	}

	@Override
	protected ToggleWrapper newCheckBox(String string) {
		return new Toggle(new ImageCheck(Images.IMAGE_LOADER), string);
	}

	@Override
	protected ToggleWrapper newRadioButton(String string) {
		return new Toggle(new RadioModel(), new ImageRadio(Images.IMAGE_LOADER), string);
	}

	@Override
	protected ToggleWrapper newSwitch(String string) {
		return new Toggle(new ImageSwitch(Images.IMAGE_LOADER), string);
	}

	@Override
	protected AbstractSlider newSlider(int min, int max, int initial) {
		return new ImageSlider(Images.IMAGE_LOADER, min, max, initial);
	}
}
