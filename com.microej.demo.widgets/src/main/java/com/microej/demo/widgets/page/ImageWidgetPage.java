/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.microej.demo.widgets.page;

import ej.widget.basic.AbstractSlider;
import ej.widget.basic.image.ImageCheck;
import ej.widget.basic.image.ImageRadio;
import ej.widget.basic.image.ImageSlider;
import ej.widget.basic.image.ImageSwitch;
import ej.widget.composed.Toggle;
import ej.widget.composed.ToggleComposite;
import ej.widget.toggle.RadioModel;

/**
 * This page illustrates the widgets rendered with some images.
 */
public class ImageWidgetPage extends WidgetPage {

	@Override
	protected String getTitle() {
		return "Images"; //$NON-NLS-1$
	}

	@Override
	protected ToggleComposite newCheckBox(String string) {
		return new Toggle(new ImageCheck(), string);
	}

	@Override
	protected ToggleComposite newRadioButton(String string) {
		return new Toggle(new RadioModel(), new ImageRadio(), string);
	}

	@Override
	protected ToggleComposite newSwitch(String string) {
		return new Toggle(new ImageSwitch(), string);
	}

	@Override
	protected AbstractSlider newSlider(int min, int max, int initial) {
		return new ImageSlider(min, max, initial);
	}
}
