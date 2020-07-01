/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test.lifecycle;

import com.is2t.testsuite.support.CheckHelper;

import ej.microui.display.Display;
import ej.mwt.Desktop;
import ej.widget.container.FillCarousel;
import ej.widget.container.util.LayoutOrientation;
import ej.widget.test.framework.MyFreeWidget;
import ej.widget.test.framework.Test;
import ej.widget.test.framework.TestHelper;

/**
 *
 */
public class TestShownFillCarousel extends Test {

	private static final int DURATION = 200;

	public static void main(String[] args) {
		TestHelper.launchTest(new TestShownFillCarousel());
	}

	@Override
	public void run(Display display) {
		Desktop desktop = new Desktop();
		FillCarousel fillCarousel = new FillCarousel(LayoutOrientation.VERTICAL, false);
		MyFreeWidget widget1 = new MyFreeWidget();
		MyFreeWidget widget2 = new MyFreeWidget();
		desktop.setWidget(fillCarousel);
		fillCarousel.addChild(widget1);
		fillCarousel.addChild(widget2);

		CheckHelper.check(getClass(), "Widget1 not shown yet", !widget1.isOnShown());
		CheckHelper.check(getClass(), "Widget2 not shown yet", !widget2.isOnShown());

		desktop.requestShow();
		TestHelper.waitForAllEvents();
		CheckHelper.check(getClass(), "Widget1 shown", widget1.isOnShown());
		CheckHelper.check(getClass(), "Widget2 not shown yet", !widget2.isOnShown());

		fillCarousel.goTo(1, DURATION);
		try {
			Thread.sleep(DURATION + 500);
		} catch (InterruptedException e) {
		}
		TestHelper.waitForAllEvents();
		CheckHelper.check(getClass(), "Widget1 hidden", widget1.isOnHidden());
		CheckHelper.check(getClass(), "Widget2 shown", widget2.isOnShown());

		CheckHelper.check(getClass(), "Widget2 not hidden yet", !widget2.isOnHidden());

		widget1.reset();
		widget2.reset();

		fillCarousel.goTo(0, DURATION);
		try {
			Thread.sleep(DURATION + 500);
		} catch (InterruptedException e) {
		}
		TestHelper.waitForAllEvents();
		CheckHelper.check(getClass(), "Widget2 hidden", widget2.isOnHidden());
		CheckHelper.check(getClass(), "Widget1 shown", widget1.isOnShown());

		CheckHelper.check(getClass(), "Widget1 not hidden", !widget1.isOnHidden());

		desktop.requestHide();
		TestHelper.waitForAllEvents();
		CheckHelper.check(getClass(), "Widget1 hidden", widget1.isOnHidden());
	}
}
