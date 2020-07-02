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
public class TestSplitContainerAlone extends Test {

	public static void main(String[] args) {
		TestHelper.launchTest(new TestSplitContainerAlone());
	}

	@Override
	public void run(Display display) {
		testHorizontal();
		testVertical();
	}

	private void testHorizontal() {
		final int baseWidth = 30;
		final int baseHeight = 20;
		float ratio = (float) 1 / 3;

		Item leftLabel = new Item(baseWidth, baseHeight);
		Split splitContainer = new Split(LayoutOrientation.HORIZONTAL, ratio);
		splitContainer.setFirstChild(leftLabel);
		TestHelper.showAndWait(splitContainer, false);

		// get widgets size
		CheckHelper.check(TestSplitContainerAlone.class, "h left width", leftLabel.getWidth(), baseWidth);
		CheckHelper.check(TestSplitContainerAlone.class, "h left height", leftLabel.getHeight(), baseHeight);
		CheckHelper.check(TestSplitContainerAlone.class, "h container width", splitContainer.getWidth(), baseWidth * 3);
		CheckHelper.check(TestSplitContainerAlone.class, "h container height", splitContainer.getHeight(), baseHeight);
		CheckHelper.check(getClass(), "left paint", leftLabel.isPaint());
	}

	private void testVertical() {
		final int baseWidth = 30;
		final int baseHeight = 20;
		float ratio = (float) 1 / 3;

		Item topLabel = new Item(baseWidth, baseHeight);
		Split splitContainer = new Split(LayoutOrientation.VERTICAL, ratio);
		splitContainer.setFirstChild(topLabel);
		TestHelper.showAndWait(splitContainer, false);

		// get widgets size
		CheckHelper.check(TestSplitContainerAlone.class, "v top width", topLabel.getWidth(), baseWidth);
		CheckHelper.check(TestSplitContainerAlone.class, "v top height", topLabel.getHeight(), baseHeight);
		CheckHelper.check(TestSplitContainerAlone.class, "v container width", splitContainer.getWidth(), baseWidth);
		CheckHelper.check(TestSplitContainerAlone.class, "v container height", splitContainer.getHeight(),
				baseHeight * 3);
		CheckHelper.check(getClass(), "top paint", topLabel.isPaint());
	}

}
