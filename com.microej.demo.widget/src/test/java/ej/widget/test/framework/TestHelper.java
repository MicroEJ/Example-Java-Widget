/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test.framework;

import com.is2t.testsuite.support.CheckHelper;

import ej.annotation.Nullable;
import ej.bon.Timer;
import ej.microui.MicroUI;
import ej.microui.display.Display;
import ej.mwt.Container;
import ej.mwt.Desktop;
import ej.mwt.Widget;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.dimension.Dimension;
import ej.mwt.stylesheet.Stylesheet;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.service.ServiceFactory;
import ej.widget.container.Canvas;

/**
 *
 */
public class TestHelper {

	@Nullable
	private static Desktop desktop;

	public static void launchTest(Test test) {
		MicroUI.start();
		try {
			Display display = Display.getDisplay();
			CheckHelper.startCheck(test.getClass());
			test.run(display);
		} catch (Error e) {
			CheckHelper.check(test.getClass(), e.getMessage(), false);
			e.printStackTrace();
		} finally {
			CheckHelper.endCheck(test.getClass());
			MicroUI.stop();
			ServiceFactory.getService(Timer.class, Timer.class).cancel();
		}
	}

	public static Desktop showAndWait(Widget widget, boolean fill, @Nullable Stylesheet stylesheet) {
		Desktop desktop = new Desktop();
		TestHelper.desktop = desktop;

		if (stylesheet != null) {
			desktop.setStylesheet(stylesheet);
		}

		if (fill) {
			desktop.setWidget(widget);
		} else {
			Canvas canvas = new Canvas();
			canvas.addChild(widget, 0, 0, Widget.NO_CONSTRAINT, Widget.NO_CONSTRAINT);
			desktop.setWidget(canvas);
		}

		desktop.requestShow();
		waitForAllEvents();

		return desktop;
	}

	public static Desktop showAndWait(Widget widget, boolean fill) {
		return showAndWait(widget, fill, null);
	}

	public static void waitForEvent() {
		WaitForEventRunnable runnable = new WaitForEventRunnable();
		MicroUI.callSerially(runnable);
		runnable.waitForExecution();
	}

	public static void waitForAllEvents() {
		// make sure every event is executed before continuing
		for (int i = 0; i < 5; i++) { // 5 events should be enough for all use-cases
			waitForEvent();
		}
	}

	public static void hide(Widget widget) {
		Container parent = widget.getParent();
		if (parent != null) {
			((Canvas) parent).removeChild(widget);
		}
		Desktop desktop = TestHelper.desktop;
		assert desktop != null;
		desktop.setWidget(null);
		desktop.requestHide();
		waitForAllEvents();
	}

	private static boolean contains(Container container, Widget widget) {
		int numChildren = container.getChildrenCount();
		for (int i = 0; i < numChildren; i++) {
			Widget child = container.getChild(i);
			if (child == widget) {
				return true;
			}
		}
		return false;
	}

	public static void checkItemsCount(Class<?> clazz, Container container, int count) {
		CheckHelper.check(clazz, "contains " + count + " item" + (count > 1 ? "s" : ""), container.getChildrenCount(),
				count);
	}

	public static void checkIsParent(Class<?> clazz, Container container, Widget widget) {
		CheckHelper.check(clazz, "is parent", widget.getParent() == container);
		CheckHelper.check(clazz, "contains item", contains(container, widget));
	}

	public static void checkIsNotParent(Class<?> clazz, Container container, Widget widget) {
		CheckHelper.check(clazz, "is not parent", widget.getParent() == null);
		CheckHelper.check(clazz, "does not contain item", !contains(container, widget));
	}

	public static void checkWidget(Class<?> clazz, String name, Widget widget, int x, int y, int width, int height) {
		CheckHelper.check(clazz, name + " x", widget.getX(), x);
		CheckHelper.check(clazz, name + " y", widget.getY(), y);
		CheckHelper.check(clazz, name + " width", widget.getWidth(), width);
		CheckHelper.check(clazz, name + " height", widget.getHeight(), height);
	}

	public static void mergeStyle(CascadingStylesheet stylesheet, Widget widget, int horizontalAlignment,
			int verticalAlignment) {
		InstanceSelector selector = new InstanceSelector(widget);
		EditableStyle style = stylesheet.getSelectorStyle(selector);
		style.setHorizontalAlignment(horizontalAlignment);
		style.setVerticalAlignment(verticalAlignment);
		if (widget.isShown()) {
			widget.updateStyle();
		}
	}

	public static void mergeStyle(CascadingStylesheet stylesheet, Widget widget, Dimension dimension) {
		InstanceSelector selector = new InstanceSelector(widget);
		EditableStyle style = stylesheet.getSelectorStyle(selector);
		style.setDimension(dimension);
		if (widget.isShown()) {
			widget.updateStyle();
		}
	}

}
