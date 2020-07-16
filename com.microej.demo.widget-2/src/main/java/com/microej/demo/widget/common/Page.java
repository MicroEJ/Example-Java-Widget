/*
 * Copyright 2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package com.microej.demo.widget.common;

import ej.mwt.Desktop;

/**
 * A page of the demo.
 */
public interface Page {

	/**
	 * Gets the desktop that represent the page.
	 *
	 * @return the desktop of the page.
	 */
	Desktop getDesktop();

}
