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
import ej.widget.test.framework.Test;
import ej.widget.test.framework.TestHelper;

/**
 *
 */
public class TestSplitContainerConsistency2 extends Test {

	public static void main(String[] args) {
		TestHelper.launchTest(new TestSplitContainerConsistency2());
	}

	@Override
	public void run(Display display) {
		Split splitContainer = new Split(LayoutOrientation.HORIZONTAL, 0.5f);
		CheckHelper.check(TestSplitContainerConsistency2.class, "0.5f ratio", splitContainer.getRatio(), 0.5f);
		CheckHelper.check(TestSplitContainerConsistency2.class, "horizontal", splitContainer.getOrientation(),
				LayoutOrientation.HORIZONTAL);
		splitContainer.setRatio(0.4f);
		CheckHelper.check(TestSplitContainerConsistency2.class, "0.4f ratio", splitContainer.getRatio(), 0.4f);
		CheckHelper.check(TestSplitContainerConsistency2.class, "horizontal", splitContainer.getOrientation(),
				LayoutOrientation.HORIZONTAL);
		splitContainer.setOrientation(LayoutOrientation.VERTICAL);
		CheckHelper.check(TestSplitContainerConsistency2.class, "0.4f ratio", splitContainer.getRatio(), 0.4f);
		CheckHelper.check(TestSplitContainerConsistency2.class, "vertical", splitContainer.getOrientation(),
				LayoutOrientation.VERTICAL);

		try {
			splitContainer.setRatio(0.0f);
			CheckHelper.check(TestSplitContainerConsistency2.class, "0.0f ratio", false);
		} catch (IllegalArgumentException e) {
			CheckHelper.check(TestSplitContainerConsistency2.class, "0.0f ratio", true);
		}
		try {
			splitContainer.setRatio(-10.0f);
			CheckHelper.check(TestSplitContainerConsistency2.class, "-10.0f ratio", false);
		} catch (IllegalArgumentException e) {
			CheckHelper.check(TestSplitContainerConsistency2.class, "-10.0f ratio", true);
		}
		try {
			splitContainer.setRatio(1.0f);
			CheckHelper.check(TestSplitContainerConsistency2.class, "1.0f ratio", false);
		} catch (IllegalArgumentException e) {
			CheckHelper.check(TestSplitContainerConsistency2.class, "1.0f ratio", true);
		}
		try {
			splitContainer.setRatio(10.0f);
			CheckHelper.check(TestSplitContainerConsistency2.class, "10.0f ratio", false);
		} catch (IllegalArgumentException e) {
			CheckHelper.check(TestSplitContainerConsistency2.class, "10.0f ratio", true);
		}

		splitContainer = new Split(LayoutOrientation.HORIZONTAL, 0.3f);
		CheckHelper.check(TestSplitContainerConsistency2.class, "0.3f ratio", splitContainer.getRatio(), 0.3f);
		CheckHelper.check(TestSplitContainerConsistency2.class, "horizontal", splitContainer.getOrientation(),
				LayoutOrientation.HORIZONTAL);
		splitContainer.setRatio(0.2f);
		CheckHelper.check(TestSplitContainerConsistency2.class, "0.2f ratio", splitContainer.getRatio(), 0.2f);
		CheckHelper.check(TestSplitContainerConsistency2.class, "horizontal", splitContainer.getOrientation(),
				LayoutOrientation.HORIZONTAL);
		splitContainer.setOrientation(LayoutOrientation.VERTICAL);
		CheckHelper.check(TestSplitContainerConsistency2.class, "0.2f ratio", splitContainer.getRatio(), 0.2f);
		CheckHelper.check(TestSplitContainerConsistency2.class, "vertical", splitContainer.getOrientation(),
				LayoutOrientation.VERTICAL);

		splitContainer = new Split(LayoutOrientation.VERTICAL, 0.6f);
		CheckHelper.check(TestSplitContainerConsistency2.class, "0.6f ratio", splitContainer.getRatio(), 0.6f);
		CheckHelper.check(TestSplitContainerConsistency2.class, "vertical", splitContainer.getOrientation(),
				LayoutOrientation.VERTICAL);
		splitContainer.setRatio(0.7f);
		CheckHelper.check(TestSplitContainerConsistency2.class, "0.7f ratio", splitContainer.getRatio(), 0.7f);
		CheckHelper.check(TestSplitContainerConsistency2.class, "vertical", splitContainer.getOrientation(),
				LayoutOrientation.VERTICAL);
		splitContainer.setOrientation(LayoutOrientation.HORIZONTAL);
		CheckHelper.check(TestSplitContainerConsistency2.class, "0.7f ratio", splitContainer.getRatio(), 0.7f);
		CheckHelper.check(TestSplitContainerConsistency2.class, "horizontal", splitContainer.getOrientation(),
				LayoutOrientation.HORIZONTAL);

	}

}
