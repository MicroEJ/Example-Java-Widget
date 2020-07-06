/*
 * Copyright 2016-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test.framework;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import ej.annotation.Nullable;
import ej.mwt.Widget;
import ej.mwt.stylesheet.selector.Selector;
import ej.mwt.stylesheet.selector.SelectorHelper;

/**
 * An instance selector selects by checking the element id (Java instance).
 * <p>
 * Use it sparingly. Prefer using wider scope selectors (such as type selector or class selector) that are more in line
 * with CSS philosophy.
 * <p>
 * Equivalent to a style defined in a HTML tag (no CSS selector). Its specificity is (1,0,0,0).
 * <p>
 * When set on the desktop stylesheet (accessible using {@link ej.mwt.Desktop#getStylesheet()}) a rule with an instance
 * selector is automatically removed from the stylesheet rules after garbage collection of the referenced element.
 * <p>
 * When set on another stylesheet or in a combinator the rule remains applicable for the lifetime of the stylesheet or
 * combinator.
 *
 * @see ej.mwt.stylesheet.selector.SelectorHelper
 * @see ej.mwt.Desktop#getStylesheet()
 * @see ej.mwt.stylesheet.cascading.CascadingStylesheet#resetSelectorStyle(Selector)
 */
public class InstanceSelector implements Selector {

	private final Reference<Widget> elementReference;
	private final int hashcode;

	/**
	 * Creates an instance selector.
	 *
	 * @param element
	 *            the element instance to check.
	 */
	public InstanceSelector(Widget element) {
		this.elementReference = new WeakReference<>(element);
		this.hashcode = element.hashCode();
	}

	@Override
	public boolean appliesToWidget(Widget widget) {
		return widget == this.elementReference.get();
	}

	@Override
	public int getSpecificity() {
		return SelectorHelper.getSpecificity(1, 0, 0, 0);
	}

	@Override
	public int hashCode() {
		return this.hashcode;
	}

	@Override
	public boolean equals(@Nullable Object obj) {
		if (obj instanceof InstanceSelector) {
			return ((InstanceSelector) obj).elementReference.get() == this.elementReference.get();
		}
		return false;
	}
}
