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
import ej.mwt.style.dimension.NoDimension;
import ej.mwt.style.dimension.OptimalDimension;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.util.Alignment;
import ej.widget.composed.Wrapper;
import ej.widget.container.Canvas;
import ej.widget.test.framework.ContainerValidateHelper;
import ej.widget.test.framework.Item;
import ej.widget.test.framework.TestHelper;

public class TestWrapper extends TestButtonWrapper {

	public static void main(String[] args) {
		TestHelper.launchTest(new TestWrapper());
	}

	@Override
	public void run(Display display) {
		CascadingStylesheet stylesheet = new CascadingStylesheet();

		Item item = new Item(10, 15);
		Wrapper wrapper = new Wrapper();
		wrapper.setChild(item);
		ContainerValidateHelper container = new ContainerValidateHelper(wrapper);

		Desktop desktop = new Desktop();
		desktop.setStylesheet(stylesheet);
		desktop.setWidget(container);

		container.updateStyle();
		container.computeChildOptimalSize(wrapper, 30, 20);
		CheckHelper.check(getClass(), "Adjusted, Bigger, Optimal Width", wrapper.getWidth(), item.getExpectedWidth());
		CheckHelper.check(getClass(), "Adjusted, Bigger, Optimal Height", wrapper.getHeight(),
				item.getExpectedHeight());
		testAlignments(stylesheet, wrapper, item, 30, 20, 30, 20, "Adjusted, Bigger");
		container.computeChildOptimalSize(wrapper, 5, 10);
		CheckHelper.check(getClass(), "Adjusted, Smaller, Optimal Width", wrapper.getWidth(), item.getExpectedWidth());
		CheckHelper.check(getClass(), "Adjusted, Smaller, Optimal Height", wrapper.getHeight(),
				item.getExpectedHeight());
		testAlignments(stylesheet, wrapper, item, 5, 10, 5, 10, "Adjusted, Smaller");
		container.computeChildOptimalSize(wrapper, Widget.NO_CONSTRAINT, Widget.NO_CONSTRAINT);
		CheckHelper.check(getClass(), "Adjusted, Pack, Optimal Width", wrapper.getWidth(), item.getExpectedWidth());
		CheckHelper.check(getClass(), "Adjusted, Pack, Optimal Height", wrapper.getHeight(), item.getExpectedHeight());

		TestHelper.mergeStyle(stylesheet, item, OptimalDimension.OPTIMAL_DIMENSION_XY);
		container.computeChildOptimalSize(wrapper, 30, 20);
		CheckHelper.check(getClass(), "Not adjusted, Bigger, Optimal Width", wrapper.getWidth(),
				item.getExpectedWidth());
		CheckHelper.check(getClass(), "Not adjusted, Bigger, Optimal Height", wrapper.getHeight(),
				item.getExpectedHeight());
		testAlignments(stylesheet, wrapper, item, 30, 20, item.getExpectedWidth(), item.getExpectedHeight(),
				"Not adjusted, Bigger");
		container.computeChildOptimalSize(wrapper, 5, 10);
		CheckHelper.check(getClass(), "Not adjusted, Smaller, Optimal Width", wrapper.getWidth(),
				item.getExpectedWidth());
		CheckHelper.check(getClass(), "Not adjusted, Smaller, Optimal Height", wrapper.getHeight(),
				item.getExpectedHeight());
		testAlignments(stylesheet, wrapper, item, 10, 5, item.getExpectedWidth(), item.getExpectedHeight(),
				"Not adjusted, Smaller");
		container.computeChildOptimalSize(wrapper, Widget.NO_CONSTRAINT, Widget.NO_CONSTRAINT);
		CheckHelper.check(getClass(), "Not adjusted, Pack, Optimal Width", wrapper.getWidth(), item.getExpectedWidth());
		CheckHelper.check(getClass(), "Not adjusted, Pack, Optimal Height", wrapper.getHeight(),
				item.getExpectedHeight());

		TestHelper.mergeStyle(stylesheet, item, NoDimension.NO_DIMENSION);
		testAlignments(stylesheet, wrapper, item, 30, 20, 30, 20, "Adjusted, Bigger");
		testAlignments(stylesheet, wrapper, item, 5, 10, 5, 10, "Adjusted, Smaller");
	}

	private void testAlignments(CascadingStylesheet stylesheet, Wrapper wrapper, Item item, int widthHint,
			int heightHint, int expectedWidth, int expectedHeight, String message) {
		// remove the item back from its initial wrapper
		Wrapper oldWrapper = wrapper;
		oldWrapper.removeChild();

		// create a new wrapper to make sure we don't remove the initial one from its canvas
		wrapper = new Wrapper();
		wrapper.setChild(item);

		Canvas canvas = new Canvas();
		canvas.addChild(wrapper, 0, 0, widthHint, heightHint);

		Desktop desktop = new Desktop();
		desktop.setStylesheet(stylesheet);
		desktop.setWidget(canvas);
		desktop.requestShow();
		TestHelper.waitForEvent();

		TestHelper.mergeStyle(stylesheet, wrapper, Alignment.LEFT, Alignment.TOP);
		TestHelper.mergeStyle(stylesheet, item, Alignment.LEFT, Alignment.TOP);
		canvas.requestLayOut();
		TestHelper.waitForAllEvents();
		CheckHelper.check(getClass(), message + ", TopLeft, X", item.getX(), 0);
		CheckHelper.check(getClass(), message + ", TopLeft, Y", item.getY(), 0);
		CheckHelper.check(getClass(), message + ", TopLeft, Width", item.getWidth(), expectedWidth);
		CheckHelper.check(getClass(), message + ", TopLeft, Height", item.getHeight(), expectedHeight);

		TestHelper.mergeStyle(stylesheet, wrapper, Alignment.RIGHT, Alignment.BOTTOM);
		TestHelper.mergeStyle(stylesheet, item, Alignment.RIGHT, Alignment.BOTTOM);
		canvas.requestLayOut();
		TestHelper.waitForAllEvents();
		CheckHelper.check(getClass(), message + ", BottomRight, X", item.getX(), wrapper.getWidth() - item.getWidth());
		CheckHelper.check(getClass(), message + ", BottomRight, Y", item.getY(),
				wrapper.getHeight() - item.getHeight());
		CheckHelper.check(getClass(), message + ", BottomRight, Width", item.getWidth(), expectedWidth);
		CheckHelper.check(getClass(), message + ", BottomRight, Height", item.getHeight(), expectedHeight);

		TestHelper.mergeStyle(stylesheet, wrapper, Alignment.HCENTER, Alignment.VCENTER);
		TestHelper.mergeStyle(stylesheet, item, Alignment.HCENTER, Alignment.VCENTER);
		canvas.requestLayOut();
		TestHelper.waitForAllEvents();
		check(message + ", Center, X", item.getX(), (wrapper.getWidth() - item.getWidth()) / 2);
		check(message + ", Center, Y", item.getY(), (wrapper.getHeight() - item.getHeight()) / 2);
		CheckHelper.check(getClass(), message + ", Center, Width", item.getWidth(), expectedWidth);
		CheckHelper.check(getClass(), message + ", Center, Height", item.getHeight(), expectedHeight);

		// put the item back to its initial wrapper
		wrapper.removeChild();
		oldWrapper.setChild(item);
	}

	private void check(String message, int value, int expectedValue) {
		CheckHelper.check(getClass(), message, value >= expectedValue - 1 && value <= expectedValue + 1);
	}
}
