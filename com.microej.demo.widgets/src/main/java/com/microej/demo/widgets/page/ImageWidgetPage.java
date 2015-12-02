/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.microej.demo.widgets.page;

import ej.widget.basic.AbstractSlider;
import ej.widget.basic.Toggle;
import ej.widget.basic.image.ImageCheck;
import ej.widget.basic.image.ImageRadio;
import ej.widget.basic.image.ImageSlider;
import ej.widget.basic.image.ImageSwitch;

/**
 * This page illustrates the widgets rendered with some images.
 */
public class ImageWidgetPage extends WidgetPage {

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
	protected AbstractSlider newSlider(int min, int max, int initial) {
		return new ImageSlider(min, max, initial);
	}
}
