/*
 * Copyright 2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package com.microej.demo.widget.common;

import com.microej.demo.widget.button.ButtonPage;
import com.microej.demo.widget.label.LabelPage;
import com.microej.demo.widget.scrollablelist.ScrollableListPage;

/**
 * List of the known pages.
 */
public class Pages {

	/** The label page name. */
	public static final String LABEL_PAGE = "Label"; //$NON-NLS-1$
	/** The button page name. */
	public static final String BUTTON_PAGE = "Button"; //$NON-NLS-1$
	/** The scrollable list page name. */
	public static final String SCROLLABLELIST_PAGE = "Scrollable List"; //$NON-NLS-1$

	public static final String[] ALL_PAGES = { LABEL_PAGE, BUTTON_PAGE, SCROLLABLELIST_PAGE };

	/**
	 * Gets the page matching the given name.
	 *
	 * @param name
	 *            the name of the page.
	 * @return the page instance.
	 */
	public static Page getPage(String name) {
		switch (name) {
		case LABEL_PAGE:
			return new LabelPage();
		case BUTTON_PAGE:
			return new ButtonPage();
		case SCROLLABLELIST_PAGE:
			return new ScrollableListPage();
		default:
			throw new IllegalArgumentException();
		}
	}

	private Pages() {
	}

}
