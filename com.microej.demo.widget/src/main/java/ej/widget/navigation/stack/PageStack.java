/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.navigation.stack;

import java.util.EmptyStackException;

import ej.widget.navigation.page.Page;

/**
 * A pages stack is responsible for the pages history management.
 */
public interface PageStack {

	/**
	 * Gets whether the stack is empty or not.
	 *
	 * @return <code>true</code> if the stack is empty, <code>false</code> otherwise.
	 */
	boolean isEmpty();

	/**
	 * Pushes a page at the top of the stack.
	 *
	 * @param page
	 *            the page to push.
	 */
	void push(Page page);

	/**
	 * Gets and removes the page at the top of the stack.
	 *
	 * @return the page at the top of the stack.
	 * @throws EmptyStackException
	 *             if the stack is empty.
	 * @see #isEmpty()
	 */
	Page pop() throws EmptyStackException;

	/**
	 * Gets and removes the last page pushed with a URL. All pages pushed after this page are also removed.
	 * <p>
	 * If no page with this URL can be found in the stack, the stack is cleared and a new page is created with this URL.
	 *
	 * @param url
	 *            the URL to search for in the stack.
	 * @return a page with the given URL.
	 *
	 * @see ej.widget.navigation.page.URLResolver#isSamePage(String, String)
	 * @see ej.widget.navigation.page.URLResolver#resolve(String)
	 */
	Page popUntil(String url);

	/**
	 * Gets the page at the top of the stack.
	 *
	 * @return the page at the top of the stack.
	 * @throws EmptyStackException
	 *             if the stack is empty.
	 * @see #isEmpty()
	 */
	Page peek() throws EmptyStackException;

	/**
	 * Removes all pages in the stack.
	 */
	void removeAll();

	/**
	 * Gets the page at an index.
	 *
	 * @param index
	 *            the index of the page.
	 * @return the page at the given index.
	 * @throws ArrayIndexOutOfBoundsException
	 *             if the index is out of range ({@code index < 0 || index >= size()})
	 */
	Page elementAt(int index) throws ArrayIndexOutOfBoundsException;

	/**
	 * Gets the number of pages in the stack.
	 *
	 * @return the number of pages in the stack.
	 */
	int size();

}
