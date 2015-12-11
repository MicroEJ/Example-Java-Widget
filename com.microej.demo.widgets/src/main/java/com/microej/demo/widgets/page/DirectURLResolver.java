/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.microej.demo.widgets.page;

import java.util.HashMap;
import java.util.Map;

import ej.transition.page.ClassNameURLResolver;
import ej.transition.page.Page;
import ej.transition.page.PageNotFoundException;

/**
 *
 */
public class DirectURLResolver extends ClassNameURLResolver {

	private final Map<String, Page> pages;

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
