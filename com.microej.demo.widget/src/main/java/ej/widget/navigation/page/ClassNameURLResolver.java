/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.navigation.page;

/**
 * The class name URL resolver resolves the URLs using reflection. That means the URL is the fully qualified name of the
 * page to instantiate.
 * <p>
 * The resolution is done by:<code>(Page) Class.forName(url).newInstance()</code>
 * <p>
 * If the class is not found or there is a problem during this instantiation or does not extends {@link Page}, a
 * {@link PageNotFoundException} is thrown.
 * <p>
 * It checks that URLs are equal to ensure it is the same one.
 */
public class ClassNameURLResolver implements URLResolver {

	@Override
	public Page resolve(String url) throws PageNotFoundException {
		try {
			return (Page) Class.forName(url).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			throw new PageNotFoundException(url, e);
		}
	}

	@Override
	public boolean isSamePage(String url1, String url2) {
		return url1.equals(url2);
	}

}
