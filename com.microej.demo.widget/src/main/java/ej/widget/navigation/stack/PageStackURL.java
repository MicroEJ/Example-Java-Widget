/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.navigation.stack;

import java.util.EmptyStackException;
import java.util.Stack;

import ej.widget.navigation.page.Page;
import ej.widget.navigation.page.URLResolver;

/**
 * Pages stack that stores URLs of the pages.
 * <p>
 * That means that it does not keep references to the previous pages, and when popping the page are re-created from the
 * saved URL.
 */
public class PageStackURL implements PageStack {

	private final URLResolver urlResolver;
	private final Stack<String> pageStack;

	/**
	 * Creates a pages stack.
	 *
	 * @param urlResolver
	 *            the URL resolver to use.
	 */
	public PageStackURL(URLResolver urlResolver) {
		this.urlResolver = urlResolver;
		this.pageStack = new Stack<>();
	}

	@Override
	public boolean isEmpty() {
		return this.pageStack.isEmpty();
	}

	@Override
	public void push(Page page) {
		this.pageStack.push(page.getCurrentURL());
	}

	@Override
	public Page pop() throws EmptyStackException {
		String url = this.pageStack.pop();
		return this.urlResolver.resolve(url);
	}

	@Override
	public Page popUntil(String url) {
		while (!this.pageStack.isEmpty()) {
			String currentURL = this.pageStack.pop();
			if (this.urlResolver.isSamePage(url, currentURL)) {
				break;
			}
		}
		Page page = this.urlResolver.resolve(url);
		return page;
	}

	@Override
	public Page peek() throws EmptyStackException {
		String url = this.pageStack.peek();
		return this.urlResolver.resolve(url);
	}

	@Override
	public void removeAll() {
		this.pageStack.removeAllElements();
	}

	@Override
	public Page elementAt(int index) throws ArrayIndexOutOfBoundsException {
		String url = this.pageStack.elementAt(index);
		return this.urlResolver.resolve(url);
	}

	@Override
	public int size() {
		return this.pageStack.size();
	}

}
