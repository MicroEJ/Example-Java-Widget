/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test.container.split;

import com.is2t.testsuite.support.CheckHelper;

import ej.microui.display.Display;
import ej.widget.container.Split;
import ej.widget.container.util.LayoutOrientation;
import ej.widget.test.framework.Item;
import ej.widget.test.framework.Test;
import ej.widget.test.framework.TestHelper;

/**
 *
 */
public class TestSplitContainerConsistency extends Test {

	public static void main(String[] args) {
		TestHelper.launchTest(new TestSplitContainerConsistency());
	}

	@Override
	public void run(Display display) {
		final int baseWidth = 30;
		final int baseHeight = 20;
		Item item1 = new Item(baseWidth, baseHeight);
		Item item2 = new Item(baseWidth, baseHeight);
		Item item3 = new Item(baseWidth, baseHeight);
		Split splitContainer = new Split(LayoutOrientation.HORIZONTAL, 0.5f);
		TestHelper.checkItemsCount(TestSplitContainerConsistency.class, splitContainer, 0);
		splitContainer.setFirstChild(item1);
		TestHelper.checkItemsCount(TestSplitContainerConsistency.class, splitContainer, 1);
		TestHelper.checkIsParent(TestSplitContainerConsistency.class, splitContainer, item1);
		splitContainer.setLastChild(item2);
		TestHelper.checkItemsCount(TestSplitContainerConsistency.class, splitContainer, 2);
		TestHelper.checkIsParent(TestSplitContainerConsistency.class, splitContainer, item1);
		TestHelper.checkIsParent(TestSplitContainerConsistency.class, splitContainer, item2);

		TestHelper.showAndWait(splitContainer, false);
		CheckHelper.check(getClass(), "item1 paint", item1.isPaint());
		CheckHelper.check(getClass(), "item2 paint", item2.isPaint());
		CheckHelper.check(getClass(), "item3 not paint", !item3.isPaint());
		TestHelper.hide(splitContainer);

		item1.reset();
		item2.reset();
		item3.reset();
		// test remove
		splitContainer.removeChild(item1);
		TestHelper.checkItemsCount(TestSplitContainerConsistency.class, splitContainer, 1);
		TestHelper.checkIsParent(TestSplitContainerConsistency.class, splitContainer, item2);
		splitContainer.setFirstChild(item3);
		TestHelper.checkItemsCount(TestSplitContainerConsistency.class, splitContainer, 2);
		TestHelper.checkIsParent(TestSplitContainerConsistency.class, splitContainer, item2);
		TestHelper.checkIsParent(TestSplitContainerConsistency.class, splitContainer, item3);

		// test remove all
		splitContainer.removeAllChildren();
		TestHelper.checkItemsCount(TestSplitContainerConsistency.class, splitContainer, 0);
		splitContainer.setFirstChild(item2);
		splitContainer.setLastChild(item1);
		TestHelper.checkItemsCount(TestSplitContainerConsistency.class, splitContainer, 2);
		TestHelper.checkIsParent(TestSplitContainerConsistency.class, splitContainer, item1);
		TestHelper.checkIsParent(TestSplitContainerConsistency.class, splitContainer, item2);

		// test only first validation
		splitContainer.removeAllChildren();
		splitContainer.setFirstChild(item1);
		TestHelper.checkItemsCount(TestSplitContainerConsistency.class, splitContainer, 1);
		TestHelper.showAndWait(splitContainer, false);
		CheckHelper.check(TestSplitContainerConsistency.class, "container width", splitContainer.getWidth(),
				baseWidth * 2);
		CheckHelper.check(TestSplitContainerConsistency.class, "container height", splitContainer.getHeight(),
				baseHeight);
		CheckHelper.check(getClass(), "item1 paint", item1.isPaint());
		CheckHelper.check(getClass(), "item2 not paint", !item2.isPaint());
		CheckHelper.check(getClass(), "item3 not paint", !item3.isPaint());
		TestHelper.hide(splitContainer);
		item1.reset();
		item2.reset();
		item3.reset();

		// test only last validation
		splitContainer.removeAllChildren();
		splitContainer.setLastChild(item1);
		TestHelper.checkItemsCount(TestSplitContainerConsistency.class, splitContainer, 1);
		TestHelper.showAndWait(splitContainer, false);
		CheckHelper.check(TestSplitContainerConsistency.class, "container width", splitContainer.getWidth(),
				baseWidth * 2);
		CheckHelper.check(TestSplitContainerConsistency.class, "container height", splitContainer.getHeight(),
				baseHeight);
		CheckHelper.check(getClass(), "item1 paint", item1.isPaint());
		CheckHelper.check(getClass(), "item2 not paint", !item2.isPaint());
		CheckHelper.check(getClass(), "item3 not paint", !item3.isPaint());
		TestHelper.hide(splitContainer);
		item1.reset();
		item2.reset();
		item3.reset();

		// test empty validation
		splitContainer.removeAllChildren();
		TestHelper.checkItemsCount(TestSplitContainerConsistency.class, splitContainer, 0);
		TestHelper.showAndWait(splitContainer, false);
		CheckHelper.check(TestSplitContainerConsistency.class, "container width", splitContainer.getWidth(), 0);
		CheckHelper.check(TestSplitContainerConsistency.class, "container height", splitContainer.getHeight(), 0);
		CheckHelper.check(getClass(), "item1 not paint", !item1.isPaint());
		CheckHelper.check(getClass(), "item2 not paint", !item2.isPaint());
		CheckHelper.check(getClass(), "item3 not paint", !item3.isPaint());
		TestHelper.hide(splitContainer);
		item1.reset();
		item2.reset();
		item3.reset();

		try {
			splitContainer.setRatio(0.0f);
			CheckHelper.check(TestSplitContainerConsistency.class, "0 ratio", false);
		} catch (IllegalArgumentException e) {
			CheckHelper.check(TestSplitContainerConsistency.class, "0 ratio", true);
		}

	}

}
