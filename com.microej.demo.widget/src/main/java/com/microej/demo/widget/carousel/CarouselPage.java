/*
 * Copyright 2021-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.carousel;

import com.microej.demo.widget.carousel.widget.Carousel;
import com.microej.demo.widget.carousel.widget.CarouselEntry;
import com.microej.demo.widget.common.DemoColors;
import com.microej.demo.widget.common.Page;

import ej.bon.Immutables;
import ej.microui.display.Colors;
import ej.microui.display.Image;
import ej.mwt.Widget;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.outline.FlexibleOutline;
import ej.mwt.style.outline.border.FlexibleRectangularBorder;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.ClassSelector;
import ej.mwt.util.Alignment;
import ej.widget.basic.Label;
import ej.widget.basic.OnClickListener;
import ej.widget.container.LayoutOrientation;
import ej.widget.container.List;

/**
 * This page illustrates a Carousel.
 */
public class CarouselPage implements Page {

	private static final int CLASS_CAROUSEL = 1501;
	private static final int CLASS_LAST_CLICKED_LABEL = 1502;
	private static final int CLASS_LAST_CLICKED_NAME = 1503;

	private static final int CAROUSEL_PADDING_BOTTOM = 5;
	private static final int CAROUSEL_BORDER_BOTTOM_SIZE = 1;

	private static final String[] IMAGES = (String[]) Immutables.get("AvatarsImages"); //$NON-NLS-1$
	private static final String[] NAMES = (String[]) Immutables.get("AvatarsNames"); //$NON-NLS-1$

	private static final String IMAGE_PATH = "/images/carousel/"; //$NON-NLS-1$
	private static final int NUM_ENTRIES = 10;
	private static final int INITIAL_ENTRY = NUM_ENTRIES / 2;

	@Override
	public String getName() {
		return "Carousel"; //$NON-NLS-1$
	}

	@Override
	public void populateStylesheet(CascadingStylesheet stylesheet) {
		EditableStyle style = stylesheet.getSelectorStyle(new ClassSelector(CLASS_CAROUSEL));
		style.setHorizontalAlignment(Alignment.LEFT);
		style.setVerticalAlignment(Alignment.TOP);
		style.setColor(Colors.WHITE);
		style.setPadding(new FlexibleOutline(0, 0, CAROUSEL_PADDING_BOTTOM, 0));
		style.setBorder(new FlexibleRectangularBorder(DemoColors.DEFAULT_BORDER, 0, 0, CAROUSEL_BORDER_BOTTOM_SIZE, 0));

		style = stylesheet.getSelectorStyle(new ClassSelector(CLASS_LAST_CLICKED_LABEL));
		style.setHorizontalAlignment(Alignment.LEFT);

		style = stylesheet.getSelectorStyle(new ClassSelector(CLASS_LAST_CLICKED_NAME));
		style.setHorizontalAlignment(Alignment.RIGHT);
	}

	@Override
	public Widget getContentWidget() {
		List list = new List(LayoutOrientation.VERTICAL);

		int entryWidth = 0;
		int entryHeight = 0;
		Image[] images = new Image[IMAGES.length];
		for (int i = 0; i < IMAGES.length; i++) {
			images[i] = Image.getImage(IMAGE_PATH + IMAGES[i]);
			entryWidth = Math.max(entryWidth, images[i].getWidth());
			entryHeight = Math.max(entryHeight, images[i].getHeight());
		}

		List lastSelectedEntryList = new List(LayoutOrientation.HORIZONTAL);
		Label label = new Label("Last selected entry: "); //$NON-NLS-1$
		label.addClassSelector(CLASS_LAST_CLICKED_LABEL);
		lastSelectedEntryList.addChild(label);
		final Label lastSelectedEntry = new Label("None"); //$NON-NLS-1$
		lastSelectedEntry.addClassSelector(CLASS_LAST_CLICKED_NAME);
		lastSelectedEntryList.addChild(lastSelectedEntry);

		CarouselEntry[] carouselEntries = new CarouselEntry[NUM_ENTRIES];
		for (int e = 0; e < NUM_ENTRIES; e++) {
			int f = e % IMAGES.length;

			final String name = NAMES[f];
			assert (name != null);

			// If the image could not be loaded during init there will be no entry created.
			Image image = images[f];
			if (image != null) {
				final CarouselEntry entry = new CarouselEntry(e, image, name);
				entry.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick() {
						lastSelectedEntry.setText(name);
						lastSelectedEntry.requestRender();
					}
				});
				carouselEntries[e] = entry;
			}
		}
		Carousel carousel = new Carousel(carouselEntries, INITIAL_ENTRY, entryWidth, entryHeight);
		carousel.addClassSelector(CLASS_CAROUSEL);

		list.addChild(carousel);
		list.addChild(lastSelectedEntryList);

		return list;
	}
}
