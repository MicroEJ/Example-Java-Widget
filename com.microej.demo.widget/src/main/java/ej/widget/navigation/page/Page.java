/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.navigation.page;

import ej.widget.composed.Wrapper;

/**
 * A page is a wrapper with a URL.
 */
public abstract class Page extends Wrapper {

	/**
	 * Gets the current URL of the page.
	 * <p>
	 * The URL can include the state of the page. That means:
	 * <ul>
	 * <li>if the page does not change, the URL stays the same,</li>
	 * <li>but if the page state can be modified, the URL may be different between two calls of the method.</li>
	 * </ul>
	 * For example, if the page contains fields that the user can fill, it is interesting to add the value of these
	 * fields in the URL to restore them when going back in the stack of pages.
	 * <p>
	 * By default, the returned URL is the class name (to be used with {@link ClassNameURLResolver}).
	 *
	 * @return the current URL.
	 */
	public String getCurrentURL() {
		return getClass().getName();
	}

}
