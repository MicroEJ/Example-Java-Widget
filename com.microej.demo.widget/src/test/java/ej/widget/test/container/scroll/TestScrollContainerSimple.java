/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test.container.scroll;

import com.is2t.testsuite.support.CheckHelper;

import ej.bon.Timer;
import ej.microui.MicroUI;
import ej.microui.display.Display;
import ej.microui.event.EventGenerator;
import ej.microui.event.generator.Pointer;
import ej.service.ServiceFactory;
import ej.widget.container.Scroll;
import ej.widget.test.framework.Item;
import ej.widget.test.framework.Test;
import ej.widget.test.framework.TestHelper;

/**
 *
 */
public class TestScrollContainerSimple extends Test {

	public static void main(String[] args) {
		MicroUI.start();
		TestHelper.launchTest(new TestScrollContainerSimple());
		ServiceFactory.getService(Timer.class, Timer.class).cancel();
		MicroUI.stop();
	}

	@Override
	public void run(Display display) {
		testHorizontalFill();
		testVerticalFill();
	}

	private void testHorizontalFill() {
		Display display = Display.getDisplay();
		int displayWidth = display.getWidth();
		int displayHeight = display.getHeight();
		final int baseWidth = displayWidth * 3;
		final int baseHeight = displayHeight;

		Item content = new Item(baseWidth, baseHeight);
		Scroll scrollContainer = new Scroll(true, false);
		scrollContainer.setChild(content);
		TestHelper.showAndWait(scrollContainer, true);
		TestHelper.checkWidget(getClass(), "h first", content, 0, 0, baseWidth, baseHeight);
		TestHelper.checkWidget(getClass(), "h container", scrollContainer, 0, 0, displayWidth, displayHeight);
		CheckHelper.check(getClass(), "h first paint", content.isPaint());

		content.reset();

		scrollContainer.scrollTo(displayWidth);
		TestHelper.waitForAllEvents();
		TestHelper.checkWidget(getClass(), "h first", content, -displayWidth, 0, baseWidth, baseHeight);
		TestHelper.checkWidget(getClass(), "h container", scrollContainer, 0, 0, displayWidth, displayHeight);
		CheckHelper.check(getClass(), "h first paint", content.isPaint());

		content.reset();

		scrollContainer.scrollTo(baseWidth);
		TestHelper.waitForAllEvents();
		TestHelper.checkWidget(getClass(), "h first", content, -baseWidth + displayWidth, 0, baseWidth, baseHeight);
		TestHelper.checkWidget(getClass(), "h container", scrollContainer, 0, 0, displayWidth, displayHeight);
		CheckHelper.check(getClass(), "h first paint", content.isPaint());

		content.reset();

		scrollContainer.scrollTo(-10);
		TestHelper.waitForAllEvents();
		TestHelper.checkWidget(getClass(), "h first", content, 0, 0, baseWidth, baseHeight);
		TestHelper.checkWidget(getClass(), "h container", scrollContainer, 0, 0, displayWidth, displayHeight);
		CheckHelper.check(getClass(), "h first paint", content.isPaint());

		content.reset();

		Pointer pointer = EventGenerator.get(Pointer.class, 0);
		assert pointer != null;
		pointer.move(displayWidth - 10, 10);
		pointer.send(Pointer.PRESSED, 0);
		sleep();
		pointer.move(10, 10);
		sleep();
		pointer.send(Pointer.RELEASED, 0);
		// Wait for the animation to be finished.
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// Nothing to do…
		}
		TestHelper.waitForAllEvents();
		CheckHelper.check(getClass(), "h first" + " x", content.getX() != 0);
		CheckHelper.check(getClass(), "h first" + " y", content.getY(), 0);
		CheckHelper.check(getClass(), "h first" + " width", content.getWidth(), baseWidth);
		CheckHelper.check(getClass(), "h first" + " height", content.getHeight(), baseHeight);
		TestHelper.checkWidget(getClass(), "h container", scrollContainer, 0, 0, displayWidth, displayHeight);
		CheckHelper.check(getClass(), "h first paint", content.isPaint());
	}

	private void testVerticalFill() {
		Display display = Display.getDisplay();
		int displayWidth = display.getWidth();
		int displayHeight = display.getHeight();
		final int baseWidth = displayWidth;
		final int baseHeight = displayHeight * 3;

		Item content = new Item(baseWidth, baseHeight);
		Scroll scrollContainer = new Scroll(false, false);
		scrollContainer.setChild(content);
		TestHelper.showAndWait(scrollContainer, true);
		TestHelper.checkWidget(getClass(), "h first", content, 0, 0, baseWidth, baseHeight);
		TestHelper.checkWidget(getClass(), "h container", scrollContainer, 0, 0, displayWidth, displayHeight);
		CheckHelper.check(getClass(), "h first paint", content.isPaint());

		content.reset();

		scrollContainer.scrollTo(displayHeight);
		TestHelper.waitForAllEvents();
		TestHelper.checkWidget(getClass(), "h first", content, 0, -displayHeight, baseWidth, baseHeight);
		TestHelper.checkWidget(getClass(), "h container", scrollContainer, 0, 0, displayWidth, displayHeight);
		CheckHelper.check(getClass(), "h first paint", content.isPaint());

		content.reset();

		scrollContainer.scrollTo(baseHeight);
		TestHelper.waitForAllEvents();
		TestHelper.checkWidget(getClass(), "h first", content, 0, -baseHeight + displayHeight, baseWidth, baseHeight);
		TestHelper.checkWidget(getClass(), "h container", scrollContainer, 0, 0, displayWidth, displayHeight);
		CheckHelper.check(getClass(), "h first paint", content.isPaint());

		content.reset();

		scrollContainer.scrollTo(-10);
		TestHelper.waitForAllEvents();
		TestHelper.checkWidget(getClass(), "h first", content, 0, 0, baseWidth, baseHeight);
		TestHelper.checkWidget(getClass(), "h container", scrollContainer, 0, 0, displayWidth, displayHeight);
		CheckHelper.check(getClass(), "h first paint", content.isPaint());

		content.reset();

		Pointer pointer = EventGenerator.get(Pointer.class, 0);
		assert pointer != null;
		pointer.move(10, displayHeight - 10);
		pointer.send(Pointer.PRESSED, 0);
		sleep();
		pointer.move(10, 10);
		sleep();
		pointer.send(Pointer.RELEASED, 0);
		// Wait for the animation to be finished.
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// Nothing to do…
		}
		TestHelper.waitForAllEvents();
		CheckHelper.check(getClass(), "h first" + " x", content.getX(), 0);
		CheckHelper.check(getClass(), "h first" + " y", content.getY() != 0);
		CheckHelper.check(getClass(), "h first" + " width", content.getWidth(), baseWidth);
		CheckHelper.check(getClass(), "h first" + " height", content.getHeight(), baseHeight);
		TestHelper.checkWidget(getClass(), "h container", scrollContainer, 0, 0, displayWidth, displayHeight);
		CheckHelper.check(getClass(), "h first paint", content.isPaint());
	}

	private void sleep() {
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// Nothing to do…
		}
	}

}
