/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.microej.demo.widgets.page;

import ej.transition.page.Page;
import ej.transition.page.PageNotFoundException;
import ej.transition.page.URLResolver;

/**
 * URL Resolver that assumes a URL corresponds to the class of a page.
 */
public class ClassNameURLResolver implements URLResolver {

	@Override
	public Page resolve(String url) throws PageNotFoundException {
		try {
			return (Page) Class.forName(url).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			throw new PageNotFoundException(url);
		}
	}

	@Override
	public boolean isSamePage(String url1, String url2) {
		return url1.equals(url2);
	}

}
