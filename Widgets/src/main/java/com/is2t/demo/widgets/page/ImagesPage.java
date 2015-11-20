/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.is2t.demo.widgets.page;

import ej.widget.basic.Slider;
import ej.widget.basic.Toggle;
import ej.widget.basic.image.ImageCheck;
import ej.widget.basic.image.ImageRadio;
import ej.widget.basic.image.ImageSlider;
import ej.widget.basic.image.ImageSwitch;

/**
 * This page illustrates the widgets using some images.
 */
public class ImagesPage extends WidgetPage {

	@Override
	protected String getTitle() {
		return "Images"; //$NON-NLS-1$
	}

	@Override
	protected Toggle newCheckBox(boolean checked) {
		return new ImageCheck(checked);
	}

	@Override
	protected Toggle newSwitch(boolean checked) {
		return new ImageSwitch(checked);
	}

	@Override
	protected Toggle newRadioButton(boolean checked) {
		return new ImageRadio(checked);
	}

	@Override
	protected Slider newSlider(int min, int max, int initial) {
		return new ImageSlider(min, max, initial);
	}

}
