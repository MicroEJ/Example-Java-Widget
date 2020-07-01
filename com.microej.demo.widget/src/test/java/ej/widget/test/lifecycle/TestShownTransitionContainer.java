/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test.lifecycle;

import com.is2t.testsuite.support.CheckHelper;

import ej.microui.display.Display;
import ej.mwt.Desktop;
import ej.widget.container.transition.SlideDirection;
import ej.widget.container.transition.SlideScreenshotTransitionContainer;
import ej.widget.container.transition.TransitionContainer;
import ej.widget.test.framework.MyFreeWidget;
import ej.widget.test.framework.Test;
import ej.widget.test.framework.TestHelper;

/**
 *
 */
public class TestShownTransitionContainer extends Test {

	public static void main(String[] args) {
		TestHelper.launchTest(new TestShownTransitionContainer());
	}

	@Override
	public void run(Display display) {
		Desktop desktop = new Desktop();
		TransitionContainer transitionContainer = new SlideScreenshotTransitionContainer(SlideDirection.LEFT, false,
				false);
		MyFreeWidget widget1 = new MyFreeWidget();
		MyFreeWidget widget2 = new MyFreeWidget();
		desktop.setWidget(transitionContainer);
		transitionContainer.show(widget1, true);

		CheckHelper.check(getClass(), "Widget1 not shown yet", !widget1.isOnShown());
		CheckHelper.check(getClass(), "Widget2 not shown yet", !widget2.isOnShown());

		desktop.requestShow();
		TestHelper.waitForAllEvents();
		CheckHelper.check(getClass(), "Widget1 shown", widget1.isOnShown());
		CheckHelper.check(getClass(), "Widget2 not shown yet", !widget2.isOnShown());

		transitionContainer.show(widget2, true);
		CheckHelper.check(getClass(), "Widget1 hidden", widget1.isOnHidden());
		try {
			Thread.sleep(transitionContainer.getDuration() + 500);
		} catch (InterruptedException e) {
		}
		TestHelper.waitForAllEvents();
		CheckHelper.check(getClass(), "Widget2 shown", widget2.isOnShown());

		CheckHelper.check(getClass(), "Widget2 not hidden yet", !widget2.isOnHidden());

		desktop.requestHide();
		TestHelper.waitForAllEvents();
		CheckHelper.check(getClass(), "Widget2 hidden", widget2.isOnHidden());
	}
}
