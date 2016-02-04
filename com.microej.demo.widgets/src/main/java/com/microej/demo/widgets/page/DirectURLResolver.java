/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.microej.demo.widgets.page;

import java.util.HashMap;
import java.util.Map;

import ej.navigation.page.ClassNameURLResolver;
import ej.navigation.page.Page;
import ej.navigation.page.PageNotFoundException;

/**
 * The direct URL resolver is a class name resolver that put the loaded pages in a cache.
 */
public class DirectURLResolver extends ClassNameURLResolver {

	private final Map<String, Page> pages;

	/**
	 * Creates a direct URL resolver.
	 */
	public DirectURLResolver() {
		this.pages = new HashMap<>();
		// resolve(VectorWidgetPage.class.getName());
		// resolve(ImageWidgetPage.class.getName());
		// resolve(PictoWidgetPage.class.getName());
		// resolve(ProgressBarPage.class.getName());
		// resolve(ScrollableListPage.class.getName());
		// resolve(ScrollableTextPage.class.getName());
	}

	@Override
	public Page resolve(String url) throws PageNotFoundException {
		// Search in cached pages.
		Page page = this.pages.get(url);
		if (page == null) {
			page = super.resolve(url);
			this.pages.put(url, page);
		}
		return page;
	}

	@Override
	public boolean isSamePage(String url1, String url2) {
		return false;
	}

}
