/*
 * Copyright 2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package com.microej.demo.widget.common;

import ej.mwt.Widget;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;

/**
 * A page of the demo.
 */
public interface Page {

	/**
	 * Returns the name of this page.
	 *
	 * @return the name of this page.
	 */
	String getName();

	/**
	 * Populates the stylesheet that customizes this page.
	 *
	 * @param stylesheet
	 *            the stylesheet of this page.
	 */
	void populateStylesheet(CascadingStylesheet stylesheet);

	/**
	 * Gets the widget that represents the content of this page.
	 *
	 * @return the content widget of this page.
	 */
	Widget getContentWidget();
}
