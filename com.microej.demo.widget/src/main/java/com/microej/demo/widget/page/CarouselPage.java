/*
 * Java
 *
 * Copyright 2015-2017 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.microej.demo.widget.page;

import java.io.IOException;

import ej.microui.display.Image;
import ej.widget.carousel.Carousel;
import ej.widget.carousel.CarouselEntry;
import ej.widget.listener.OnClickListener;

/**
 * This page illustrates a carousel
 */
public class CarouselPage extends AbstractDemoPage {

	@SuppressWarnings("nls")
	private static final String[] IMAGES = { "cowboy.png", "frenchie.png", "santa.png", "space.png", "superjeo.png" };

	@SuppressWarnings("nls")
	private static final String[] NAMES = { "Cowboy", "Frenchie", "Santa", "Space", "Super Jeo" };

	private static final String IMAGE_PATH = "/images/carousel/"; //$NON-NLS-1$
	private static final int NUM_ENTRIES = 10;
	private static final int INITIAL_ENTRY = NUM_ENTRIES / 2;

	/**
	 * Creates a carousel page.
	 */
	public CarouselPage() {
		super(false, "Carousel"); //$NON-NLS-1$

		Image[] images = new Image[IMAGES.length];
		int entryWidth = 0;
		for (int i = 0; i < IMAGES.length; i++) {
			images[i] = loadImage(IMAGE_PATH + IMAGES[i]);
			entryWidth = Math.max(entryWidth, images[i].getWidth());
		}

		CarouselEntry[] carouselEntries = new CarouselEntry[NUM_ENTRIES];
		for (int e = 0; e < NUM_ENTRIES; e++) {
			int f = e % IMAGES.length;
			final String name = NAMES[f];
			final CarouselEntry entry = new CarouselEntry(e, images[f], name);
			entry.addOnClickListener(new OnClickListener() {
				@Override
				public void onClick() {
					System.out.println("Clicked on '" + name + "'"); //$NON-NLS-1$ //$NON-NLS-2$
				}
			});
			carouselEntries[e] = entry;
		}
		setCenter(new Carousel(carouselEntries, INITIAL_ENTRY, entryWidth));
	}

	private Image loadImage(String string) {
		try {
			return Image.createImage(string);
		} catch (IOException e) {
			System.out.println("Could not load image '" + string + "'"); //$NON-NLS-1$ //$NON-NLS-2$
			return null;
		}
	}
}
