/*
 * Copyright 2016-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test.composed;

import com.is2t.testsuite.support.CheckHelper;

import ej.microui.display.Display;
import ej.mwt.Desktop;
import ej.mwt.Widget;
import ej.mwt.style.dimension.Dimension;
import ej.mwt.style.dimension.NoDimension;
import ej.mwt.style.dimension.OptimalDimension;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.widget.composed.Wrapper;
import ej.widget.test.framework.Item;
import ej.widget.test.framework.Test;
import ej.widget.test.framework.TestHelper;

public class TestWrapperEncapsulation extends Test {

	public static void main(String[] args) {
		TestHelper.launchTest(new TestWrapperEncapsulation());
	}

	@Override
	public void run(Display display) {
		testNotAdjustedInAdjusted();
		testNotAdjustedInNotAdjusted();
		testAdjustedInNotAdjusted();
		testAdjustedInAdjusted();
	}

	private void testNotAdjustedInAdjusted() {
		CascadingStylesheet stylesheet = new CascadingStylesheet();

		Item item = new Item(10, 15);
		Wrapper outterWrapper = new Wrapper();
		Wrapper innerWrapper = new Wrapper();
		setAdjusted(stylesheet, innerWrapper, true);
		setAdjusted(stylesheet, item, false);
		innerWrapper.setChild(item);
		outterWrapper.setChild(innerWrapper);

		Display display = Display.getDisplay();
		Desktop desktop = new Desktop();
		desktop.setStylesheet(stylesheet);
		desktop.setWidget(outterWrapper);
		desktop.requestShow();
		TestHelper.waitForAllEvents();

		CheckHelper.check(getClass(), "Wrong outter width", outterWrapper.getWidth(), display.getWidth());
		CheckHelper.check(getClass(), "Wrong outter height", outterWrapper.getHeight(), display.getHeight());
		CheckHelper.check(getClass(), "Wrong inner width", innerWrapper.getWidth(), display.getWidth());
		CheckHelper.check(getClass(), "Wrong inner height", innerWrapper.getHeight(), display.getHeight());
		CheckHelper.check(getClass(), "Wrong item width", item.getWidth(), 10);
		CheckHelper.check(getClass(), "Wrong item height", item.getHeight(), 15);
	}

	private void testNotAdjustedInNotAdjusted() {
		CascadingStylesheet stylesheet = new CascadingStylesheet();

		Item item = new Item(10, 15);
		Wrapper outterWrapper = new Wrapper();
		Wrapper innerWrapper = new Wrapper();
		setAdjusted(stylesheet, innerWrapper, false);
		setAdjusted(stylesheet, item, false);
		innerWrapper.setChild(item);
		outterWrapper.setChild(innerWrapper);

		Display display = Display.getDisplay();
		Desktop desktop = new Desktop();
		desktop.setStylesheet(stylesheet);
		desktop.setWidget(outterWrapper);
		desktop.requestShow();
		TestHelper.waitForAllEvents();

		CheckHelper.check(getClass(), "Wrong outter width", outterWrapper.getWidth(), display.getWidth());
		CheckHelper.check(getClass(), "Wrong outter height", outterWrapper.getHeight(), display.getHeight());
		CheckHelper.check(getClass(), "Wrong inner width", innerWrapper.getWidth(), 10);
		CheckHelper.check(getClass(), "Wrong inner height", innerWrapper.getHeight(), 15);
		CheckHelper.check(getClass(), "Wrong item width", item.getWidth(), 10);
		CheckHelper.check(getClass(), "Wrong item height", item.getHeight(), 15);
	}

	private void testAdjustedInNotAdjusted() {
		CascadingStylesheet stylesheet = new CascadingStylesheet();

		Item item = new Item(10, 15);
		Wrapper outterWrapper = new Wrapper();
		Wrapper innerWrapper = new Wrapper();
		setAdjusted(stylesheet, innerWrapper, false);
		setAdjusted(stylesheet, item, true);
		innerWrapper.setChild(item);
		outterWrapper.setChild(innerWrapper);

		Display display = Display.getDisplay();
		Desktop desktop = new Desktop();
		desktop.setStylesheet(stylesheet);
		desktop.setWidget(outterWrapper);
		desktop.requestShow();
		TestHelper.waitForAllEvents();

		CheckHelper.check(getClass(), "Wrong outter width", outterWrapper.getWidth(), display.getWidth());
		CheckHelper.check(getClass(), "Wrong outter height", outterWrapper.getHeight(), display.getHeight());
		CheckHelper.check(getClass(), "Wrong inner width", innerWrapper.getWidth(), 10);
		CheckHelper.check(getClass(), "Wrong inner height", innerWrapper.getHeight(), 15);
		CheckHelper.check(getClass(), "Wrong item width", item.getWidth(), 10);
		CheckHelper.check(getClass(), "Wrong item height", item.getHeight(), 15);
	}

	private void testAdjustedInAdjusted() {
		CascadingStylesheet stylesheet = new CascadingStylesheet();

		Item item = new Item(10, 15);
		Wrapper outterWrapper = new Wrapper();
		Wrapper innerWrapper = new Wrapper();
		setAdjusted(stylesheet, innerWrapper, true);
		setAdjusted(stylesheet, item, true);
		innerWrapper.setChild(item);
		outterWrapper.setChild(innerWrapper);

		Display display = Display.getDisplay();
		Desktop desktop = new Desktop();
		desktop.setStylesheet(stylesheet);
		desktop.setWidget(outterWrapper);
		desktop.requestShow();
		TestHelper.waitForAllEvents();

		CheckHelper.check(getClass(), "Wrong outter width", outterWrapper.getWidth(), display.getWidth());
		CheckHelper.check(getClass(), "Wrong outter height", outterWrapper.getHeight(), display.getHeight());
		CheckHelper.check(getClass(), "Wrong inner width", innerWrapper.getWidth(), display.getWidth());
		CheckHelper.check(getClass(), "Wrong inner height", innerWrapper.getHeight(), display.getHeight());
		CheckHelper.check(getClass(), "Wrong item width", item.getWidth(), display.getWidth());
		CheckHelper.check(getClass(), "Wrong item height", item.getHeight(), display.getHeight());
	}

	private static void setAdjusted(CascadingStylesheet stylesheet, Widget widget, boolean adjusted) {
		Dimension dimension = adjusted ? NoDimension.NO_DIMENSION : OptimalDimension.OPTIMAL_DIMENSION_XY;
		TestHelper.mergeStyle(stylesheet, widget, dimension);
	}
}
