/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test.container.scroll;

import com.is2t.testsuite.support.CheckHelper;

import ej.bon.Timer;
import ej.microui.MicroUI;
import ej.microui.display.Colors;
import ej.microui.display.Display;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.microui.event.EventGenerator;
import ej.microui.event.generator.Pointer;
import ej.service.ServiceFactory;
import ej.widget.container.List;
import ej.widget.container.Scroll;
import ej.widget.container.util.LayoutOrientation;
import ej.widget.container.util.Scrollable;
import ej.widget.test.framework.Item;
import ej.widget.test.framework.Test;
import ej.widget.test.framework.TestHelper;

/**
 *
 */
public class TestScrollInList extends Test {

	public static void main(String[] args) {
		MicroUI.start();
		TestHelper.launchTest(new TestScrollInList());
		ServiceFactory.getService(Timer.class, Timer.class).cancel();
		MicroUI.stop();
	}

	@Override
	public void run(Display display) {
		testVertical();
		testHorizontal();
	}

	private void testVertical() {
		Display display = Display.getDisplay();
		int displayWidth = display.getWidth();
		int displayHeight = display.getHeight();
		final int baseWidth = displayWidth;
		final int baseHeight = displayHeight * 3;

		ScrollableItem content = new ScrollableItem(baseWidth, baseHeight);
		Scroll scrollContainer = new Scroll(false, false);
		scrollContainer.setChild(content);
		List list = new List(LayoutOrientation.VERTICAL);
		list.addChild(scrollContainer);
		TestHelper.showAndWait(list, true);
		content.viewPortUpdateCount = 0;

		Pointer pointer = EventGenerator.get(Pointer.class, 0);
		assert pointer != null;
		pointer.move(displayWidth / 2, displayHeight - 1);
		pointer.send(Pointer.PRESSED, 0);
		sleep();
		pointer.move(displayWidth / 2, displayHeight / 2);
		sleep();
		pointer.send(Pointer.RELEASED, 0);
		TestHelper.waitForAllEvents();
		// Wait for the animation to be finished.
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// Nothing to do…
		}

		CheckHelper.check(getClass(), "Not scrolled viewport", content.viewPortUpdateCount > 0);
		CheckHelper.check(getClass(), "Not scrolled y", content.getY() < 0);
	}

	private void testHorizontal() {
		Display display = Display.getDisplay();
		int displayWidth = display.getWidth();
		int displayHeight = display.getHeight();
		final int baseWidth = displayWidth * 3;
		final int baseHeight = displayHeight;

		ScrollableItem content = new ScrollableItem(baseWidth, baseHeight);
		Scroll scrollContainer = new Scroll(true, false);
		scrollContainer.setChild(content);
		List list = new List(LayoutOrientation.HORIZONTAL);
		list.addChild(scrollContainer);
		TestHelper.showAndWait(list, true);
		content.viewPortUpdateCount = 0;

		Pointer pointer = EventGenerator.get(Pointer.class, 0);
		assert pointer != null;
		pointer.move(displayWidth - 1, displayHeight / 2);
		pointer.send(Pointer.PRESSED, 0);
		sleep();
		pointer.move(displayWidth / 2, displayHeight / 2);
		sleep();
		pointer.send(Pointer.RELEASED, 0);
		TestHelper.waitForAllEvents();
		// Wait for the animation to be finished.
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// Nothing to do…
		}

		CheckHelper.check(getClass(), "Not scrolled viewport", content.viewPortUpdateCount > 0);
		CheckHelper.check(getClass(), "Not scrolled x", content.getX() < 0);
	}

	class ScrollableItem extends Item implements Scrollable {

		int viewPortUpdateCount;

		public ScrollableItem(int baseWidth, int baseHeight) {
			super(baseWidth, baseHeight);
		}

		@Override
		public void render(GraphicsContext g) {
			super.render(g);
			g.setColor(Colors.WHITE - getColor());
			Painter.drawLine(g, 0, 0, getWidth(), getHeight());
		}

		@Override
		public void initializeViewport(int width, int height) {
		}

		@Override
		public void updateViewport(int x, int y) {
			this.viewPortUpdateCount++;
		}

	}

	private void sleep() {
		TestHelper.waitForEvent();
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// Nothing to do…
		}
	}

}
