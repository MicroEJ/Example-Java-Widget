/*
 * Copyright 2016-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test.container.menu;

import com.is2t.testsuite.support.CheckHelper;

import ej.bon.Timer;
import ej.microui.MicroUI;
import ej.microui.display.Display;
import ej.service.ServiceFactory;
import ej.widget.basic.drawing.BulletPagingIndicator;
import ej.widget.container.AbstractCarousel;
import ej.widget.container.FillCarousel;
import ej.widget.container.ListCarousel;
import ej.widget.container.util.LayoutOrientation;
import ej.widget.test.framework.Item;
import ej.widget.test.framework.Test;
import ej.widget.test.framework.TestHelper;

public class TestCarousel extends Test {

	public static void main(String[] args) {
		MicroUI.start();
		TestHelper.launchTest(new TestCarousel());
		ServiceFactory.getService(Timer.class, Timer.class).cancel();
		MicroUI.stop();
	}

	@Override
	public void run(Display display) {
		// List menu, not cyclic.
		AbstractCarousel menu = new ListCarousel(LayoutOrientation.VERTICAL, false);
		CheckHelper.check(getClass(), "New selected index", menu.getSelectedIndex(), 0);

		menu = new ListCarousel(LayoutOrientation.VERTICAL, false, new BulletPagingIndicator(), false);
		CheckHelper.check(getClass(), "New 2 selected index", menu.getSelectedIndex(), 0);

		testAdd(menu);

		testNotCyclic(menu);

		testGoto(menu);

		// List menu, cyclic.
		menu = new ListCarousel(LayoutOrientation.VERTICAL, true);
		CheckHelper.check(getClass(), "New cyclic selected index", menu.getSelectedIndex(), 0);

		menu = new ListCarousel(LayoutOrientation.VERTICAL, true, new BulletPagingIndicator(), false);
		CheckHelper.check(getClass(), "New cyclic selected index", menu.getSelectedIndex(), 0);

		testAdd(menu);

		testCyclic(menu);

		testGoto(menu);

		// Full size menu, not cyclic.
		menu = new FillCarousel(LayoutOrientation.VERTICAL, false);
		CheckHelper.check(getClass(), "New selected index", menu.getSelectedIndex(), 0);

		menu = new FillCarousel(LayoutOrientation.VERTICAL, false, new BulletPagingIndicator(), false);
		CheckHelper.check(getClass(), "New 2 selected index", menu.getSelectedIndex(), 0);

		testAdd(menu);

		testNotCyclic(menu);

		testGoto(menu);

		// Full size menu, cyclic.
		menu = new FillCarousel(LayoutOrientation.VERTICAL, true);
		CheckHelper.check(getClass(), "New cyclic selected index", menu.getSelectedIndex(), 0);

		menu = new FillCarousel(LayoutOrientation.VERTICAL, true, new BulletPagingIndicator(), false);
		CheckHelper.check(getClass(), "New cyclic selected index", menu.getSelectedIndex(), 0);

		testAdd(menu);

		testCyclic(menu);

		testGoto(menu);

	}

	private void testAdd(AbstractCarousel menu) {
		menu.addChild(new Item(20, 10));
		menu.addChild(new Item(21, 11));
		menu.addChild(new Item(22, 12));
		CheckHelper.check(getClass(), "Add selected index", menu.getSelectedIndex(), 0);
	}

	private void testNotCyclic(AbstractCarousel menu) {
		menu.goToPrevious();
		CheckHelper.check(getClass(), "Previous 0 selected index", menu.getSelectedIndex(), 0);

		menu.goToNext();
		CheckHelper.check(getClass(), "Next 0 selected index", menu.getSelectedIndex(), 1);

		menu.goToNext();
		CheckHelper.check(getClass(), "Next 1 selected index", menu.getSelectedIndex(), 2);

		menu.goToNext();
		CheckHelper.check(getClass(), "Next 2 selected index", menu.getSelectedIndex(), 2);

		menu.goToPrevious();
		CheckHelper.check(getClass(), "Previous 2 selected index", menu.getSelectedIndex(), 1);
	}

	private void testCyclic(AbstractCarousel menu) {
		menu.goToPrevious();
		CheckHelper.check(getClass(), "Previous 0 selected index", menu.getSelectedIndex(), 2);

		menu.goToNext();
		CheckHelper.check(getClass(), "Next 2 selected index", menu.getSelectedIndex(), 0);

		menu.goToNext();
		CheckHelper.check(getClass(), "Next 0 selected index", menu.getSelectedIndex(), 1);

		menu.goToNext();
		CheckHelper.check(getClass(), "Next 1 selected index", menu.getSelectedIndex(), 2);

		menu.goToPrevious();
		CheckHelper.check(getClass(), "Previous 2 selected index", menu.getSelectedIndex(), 1);
	}

	private void testGoto(AbstractCarousel menu) {
		menu.goTo(0);
		CheckHelper.check(getClass(), "Go to 0 selected index", menu.getSelectedIndex(), 0);

		try {
			menu.goTo(-1);
			CheckHelper.check(getClass(), "Go to -1", false);
		} catch (IllegalArgumentException e) {
			CheckHelper.check(getClass(), "Go to -1", true);
		}

		try {
			menu.goTo(3);
			CheckHelper.check(getClass(), "Go to 3", false);
		} catch (IllegalArgumentException e) {
			CheckHelper.check(getClass(), "Go to 3", true);
		}

		try {
			menu.goTo(0, -1);
			CheckHelper.check(getClass(), "Go to 0 in -1ms", false);
		} catch (IllegalArgumentException e) {
			CheckHelper.check(getClass(), "Go to 0 in -1ms", true);
		}

		try {
			menu.goToNext(-1);
			CheckHelper.check(getClass(), "Go to next in -1ms", false);
		} catch (IllegalArgumentException e) {
			CheckHelper.check(getClass(), "Go to next in -1ms", true);
		}

		try {
			menu.goToPrevious(-1);
			CheckHelper.check(getClass(), "Go to previous in -1ms", false);
		} catch (IllegalArgumentException e) {
			CheckHelper.check(getClass(), "Go to previous in -1ms", true);
		}
	}

}
