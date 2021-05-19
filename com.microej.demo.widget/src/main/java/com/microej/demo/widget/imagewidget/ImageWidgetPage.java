/*
 * Copyright 2021 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.imagewidget;

import com.microej.demo.widget.common.Page;

import ej.mwt.Widget;
import ej.mwt.style.EditableStyle;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.ClassSelector;
import ej.mwt.util.Alignment;
import ej.widget.basic.ImageWidget;

/**
 * Page showing an image.
 */
public class ImageWidgetPage implements Page {

	private static final int IMAGE = 1204;
	private static final String IMAGE_PATH = "/images/microej_background.png"; //$NON-NLS-1$

	@Override
	public String getName() {
		return "Image Widget"; //$NON-NLS-1$
	}

	@Override
	public void populateStylesheet(CascadingStylesheet stylesheet) {
		EditableStyle style = stylesheet.getSelectorStyle(new ClassSelector(IMAGE));
		style.setHorizontalAlignment(Alignment.HCENTER);
		style.setVerticalAlignment(Alignment.VCENTER);
	}

	@Override
	public Widget getContentWidget() {
		ImageWidget image = new ImageWidget(IMAGE_PATH);
		image.addClassSelector(IMAGE);
		return image;
	}

}
