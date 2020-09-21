/*
 * Copyright 2020 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
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
