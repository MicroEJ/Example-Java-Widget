/*
 * Copyright 2021-2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.secretscroll;

import com.microej.demo.widget.common.DemoColors;
import com.microej.demo.widget.common.Page;
import com.microej.demo.widget.common.scroll.Scroll;
import com.microej.demo.widget.common.scroll.ScrollableList;

import ej.mwt.Widget;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.background.RectangularBackground;
import ej.mwt.style.dimension.FixedDimension;
import ej.mwt.style.outline.FlexibleOutline;
import ej.mwt.style.outline.UniformOutline;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.ClassSelector;
import ej.mwt.stylesheet.selector.TypeSelector;
import ej.mwt.util.Alignment;
import ej.widget.basic.ImageWidget;
import ej.widget.basic.Label;
import ej.widget.container.LayoutOrientation;

/**
 * Page showing a secret scroll.
 */
public class SecretScrollPage implements Page {

	private static final String IMAGE_PATH = "/images/microej_background.png"; //$NON-NLS-1$

	private static final int HIDDEN_ITEM = 71410;
	private static final int IMAGE_ITEM = 71411;

	private static final int SCROLL_MARGIN_SIDES = 15;
	private static final int HIDDEN_ITEM_MARGIN_SIDES = 10;
	private static final int IMAGE_WIDTH = 420;
	private static final int IMAGE_HEIGHT = 198;

	@Override
	public String getName() {
		return "Secret Scroll"; //$NON-NLS-1$
	}

	@Override
	public void populateStylesheet(CascadingStylesheet stylesheet) {
		EditableStyle style = stylesheet.getSelectorStyle(new TypeSelector(Scroll.class));
		style.setMargin(new FlexibleOutline(0, SCROLL_MARGIN_SIDES, 0, SCROLL_MARGIN_SIDES));

		style = stylesheet.getSelectorStyle(new ClassSelector(HIDDEN_ITEM));
		style.setPadding(new UniformOutline(HIDDEN_ITEM_MARGIN_SIDES));
		style.setMargin(new FlexibleOutline(0, 0, HIDDEN_ITEM_MARGIN_SIDES, 0));
		style.setHorizontalAlignment(Alignment.HCENTER);
		style.setBackground(new RectangularBackground(DemoColors.ALTERNATE_BACKGROUND));

		style = stylesheet.getSelectorStyle(new ClassSelector(IMAGE_ITEM));
		style.setDimension(new FixedDimension(IMAGE_WIDTH, IMAGE_HEIGHT));
	}

	@Override
	public Widget getContentWidget() {
		Label hiddenChild = new Label("Hidden Item"); //$NON-NLS-1$
		hiddenChild.addClassSelector(HIDDEN_ITEM);
		ImageWidget imageWidget = new ImageWidget(IMAGE_PATH);
		imageWidget.addClassSelector(IMAGE_ITEM);
		final Scroll secretScroll = new Scroll(LayoutOrientation.VERTICAL);
		ScrollableList secretList = new ScrollableList(LayoutOrientation.VERTICAL, true) {
			@Override
			protected void onLaidOut() {
				super.onLaidOut();
				secretScroll.scrollTo(Integer.MAX_VALUE); // hide secret
			}
		};
		secretList.addChild(hiddenChild);
		secretList.addChild(imageWidget);
		secretScroll.setChild(secretList);
		secretScroll.showScrollbar(false);
		secretScroll.setAllowExcess(false);
		return secretScroll;
	}

}
