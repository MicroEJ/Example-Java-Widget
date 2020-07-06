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
 * Pages stack that stores references to the pages.
 * <p>
 * That means that all previous pages are kept in memory, but popping a page is faster.
 */
public class PageStackReference implements PageStack {

	private final URLResolver urlResolver;
	private final Stack<Page> pagesStack;

	/**
	 * Creates a pages stack.
	 *
	 * @param urlResolver
	 *            the URL resolver to use.
	 */
	public PageStackReference(URLResolver urlResolver) {
		this.urlResolver = urlResolver;
		this.pagesStack = new Stack<>();
	}

	@Override
	public boolean isEmpty() {
		return this.pagesStack.isEmpty();
	}

	@Override
	public void push(Page page) {
		this.pagesStack.push(page);
	}

	@Override
	public Page pop() throws EmptyStackException {
		return this.pagesStack.pop();
	}

	@Override
	public Page popUntil(String url) {
		while (!this.pagesStack.isEmpty()) {
			Page currentPage = this.pagesStack.pop();
			if (this.urlResolver.isSamePage(url, currentPage.getCurrentURL())) {
				return currentPage;
			}
		}
		Page page = this.urlResolver.resolve(url);
		return page;
	}

	@Override
	public Page peek() throws EmptyStackException {
		return this.pagesStack.peek();
	}

	@Override
	public void removeAll() {
		this.pagesStack.removeAllElements();
	}

	@Override
	public Page elementAt(int index) throws ArrayIndexOutOfBoundsException {
		return this.pagesStack.elementAt(index);
	}

	@Override
	public int size() {
		return this.pagesStack.size();
	}

}
