/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.navigation.page;

/**
 * A URL resolver is responsible for creating the pages from URLs.
 */
public interface URLResolver {

	/**
	 * Gets a page from a URL.
	 *
	 * @param url
	 *            the URL of the page.
	 * @return the page for the given URL.
	 * @throws PageNotFoundException
	 *             if no page can be created for this URL.
	 */
	Page resolve(String url) throws PageNotFoundException;

	/**
	 * Gets whether or not two URLs refer to the same page.
	 *
	 * @param url1
	 *            the first URL.
	 * @param url2
	 *            the second URL.
	 * @return <code>true</code> if the given URLs refer to the same page, <code>false</code> otherwise.
	 */
	boolean isSamePage(String url1, String url2);

}
