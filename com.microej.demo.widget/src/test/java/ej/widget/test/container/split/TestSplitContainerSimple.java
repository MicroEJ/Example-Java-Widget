/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test.container.split;

import com.is2t.testsuite.support.CheckHelper;

import ej.microui.display.Display;
import ej.mwt.Widget;
import ej.mwt.style.EditableStyle;
import ej.mwt.stylesheet.Stylesheet;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.TypeSelector;
import ej.widget.container.Split;
import ej.widget.container.util.LayoutOrientation;
import ej.widget.test.framework.Item;
import ej.widget.test.framework.Test;
import ej.widget.test.framework.TestHelper;

/**
 *
 */
public class TestSplitContainerSimple extends Test {

	public static void main(String[] args) {
		TestHelper.launchTest(new TestSplitContainerSimple());
	}

	@Override
	public void run(Display display) {
		testHorizontalPackedUniformSplit();
		testVerticalPackedUniformSplit();
		testHorizontalFillUniformSplit();
		testVerticalFillUniformSplit();
		testHorizontalPackedVariableSplit();
		testVerticalPackedVariableSplit();
	}

	private void testHorizontalPackedUniformSplit() {
		final int baseWidth = 30;
		final int baseHeight = 20;
		float ratio = (float) 1 / 3;

		Item leftLabel = new Item(baseWidth, baseHeight);
		Item rightLabel = new Item(baseWidth, baseHeight);
		Split splitContainer = createSplitContainer(leftLabel, rightLabel, true);
		TestHelper.showAndWait(splitContainer, false, createSplitStylesheet(ratio));

		// get widgets size
		CheckHelper.check(TestSplitContainerSimple.class, "hpus left width", leftLabel.getWidth(), baseWidth);
		CheckHelper.check(TestSplitContainerSimple.class, "hpus left height", leftLabel.getHeight(), baseHeight);
		CheckHelper.check(TestSplitContainerSimple.class, "hpus right width", rightLabel.getWidth(), baseWidth * 2);
		CheckHelper.check(TestSplitContainerSimple.class, "hpus right height", rightLabel.getHeight(), baseHeight);
		CheckHelper.check(TestSplitContainerSimple.class, "hpus container width", splitContainer.getWidth(),
				baseWidth * 3);
		CheckHelper.check(TestSplitContainerSimple.class, "hpus container height", splitContainer.getHeight(),
				baseHeight);
		CheckHelper.check(getClass(), "left paint", leftLabel.isPaint());
		CheckHelper.check(getClass(), "right paint", rightLabel.isPaint());
	}

	private void testHorizontalPackedVariableSplit() {
		final int baseWidth = 30;
		final int biggestHeight = 40;
		final int smallestHeight = 20;
		float ratio = (float) 1 / 3;

		// test biggest first
		Item leftLabel = new Item(baseWidth, biggestHeight);
		Item rightLabel = new Item(baseWidth, smallestHeight);
		Split splitContainer = createSplitContainer(leftLabel, rightLabel, true);
		TestHelper.showAndWait(splitContainer, false, createSplitStylesheet(ratio));

		// get widgets size
		CheckHelper.check(TestSplitContainerSimple.class, "hpvs left width", leftLabel.getWidth(), baseWidth);
		CheckHelper.check(TestSplitContainerSimple.class, "hpvs left height", leftLabel.getHeight(), biggestHeight);
		CheckHelper.check(TestSplitContainerSimple.class, "hpvs right width", rightLabel.getWidth(), baseWidth * 2);
		CheckHelper.check(TestSplitContainerSimple.class, "hpvs right height", rightLabel.getHeight(), biggestHeight);
		CheckHelper.check(TestSplitContainerSimple.class, "hpvs container width", splitContainer.getWidth(),
				baseWidth * 3);
		CheckHelper.check(TestSplitContainerSimple.class, "hpvs container height", splitContainer.getHeight(),
				biggestHeight);
		CheckHelper.check(getClass(), "left paint", leftLabel.isPaint());
		CheckHelper.check(getClass(), "right paint", rightLabel.isPaint());

		leftLabel.reset();
		rightLabel.reset();
		// test smallest first
		leftLabel = new Item(baseWidth, smallestHeight);
		rightLabel = new Item(baseWidth, biggestHeight);
		splitContainer = createSplitContainer(leftLabel, rightLabel, true);
		TestHelper.showAndWait(splitContainer, false, createSplitStylesheet(ratio));

		// get widgets size
		CheckHelper.check(TestSplitContainerSimple.class, "hpvs left width", leftLabel.getWidth(), baseWidth);
		CheckHelper.check(TestSplitContainerSimple.class, "hpvs left height", leftLabel.getHeight(),
				Math.max(biggestHeight, smallestHeight));
		CheckHelper.check(TestSplitContainerSimple.class, "hpvs right width", rightLabel.getWidth(), baseWidth * 2);
		CheckHelper.check(TestSplitContainerSimple.class, "hpvs right height", rightLabel.getHeight(),
				Math.max(biggestHeight, smallestHeight));
		CheckHelper.check(TestSplitContainerSimple.class, "hpvs container width", splitContainer.getWidth(),
				baseWidth * 3);
		CheckHelper.check(TestSplitContainerSimple.class, "hpvs container height", splitContainer.getHeight(),
				biggestHeight);
		CheckHelper.check(getClass(), "left paint", leftLabel.isPaint());
		CheckHelper.check(getClass(), "right paint", rightLabel.isPaint());
	}

	private void testHorizontalFillUniformSplit() {
		final int baseWidth = 30;
		final int baseHeight = 20;
		float ratio = (float) 1 / 3;

		Item leftLabel = new Item(baseWidth, baseHeight);
		Item rightLabel = new Item(baseWidth, baseHeight);
		Split splitContainer = createSplitContainer(leftLabel, rightLabel, true);
		TestHelper.showAndWait(splitContainer, true, createSplitStylesheet(ratio));

		Display display = Display.getDisplay();
		int displayWidth = display.getWidth();
		int displayHeight = display.getHeight();
		// get widgets size
		CheckHelper.check(TestSplitContainerSimple.class, "hfus left width", leftLabel.getWidth(),
				displayWidth * ratio);
		CheckHelper.check(TestSplitContainerSimple.class, "hfus left height", leftLabel.getHeight(), displayHeight);
		CheckHelper.check(TestSplitContainerSimple.class, "hfus right width", rightLabel.getWidth(),
				displayWidth - leftLabel.getWidth());
		CheckHelper.check(TestSplitContainerSimple.class, "hfus right height", rightLabel.getHeight(), displayHeight);
		CheckHelper.check(TestSplitContainerSimple.class, "hfus container width", splitContainer.getWidth(),
				displayWidth);
		CheckHelper.check(TestSplitContainerSimple.class, "hfus container height", splitContainer.getHeight(),
				displayHeight);
		CheckHelper.check(getClass(), "left paint", leftLabel.isPaint());
		CheckHelper.check(getClass(), "right paint", rightLabel.isPaint());
	}

	private void testVerticalPackedUniformSplit() {
		final int baseWidth = 30;
		final int baseHeight = 20;
		float ratio = (float) 1 / 3;

		Item topLabel = new Item(baseWidth, baseHeight);
		Item bottomLabel = new Item(baseWidth, baseHeight);
		Split splitContainer = createSplitContainer(topLabel, bottomLabel, false);
		TestHelper.showAndWait(splitContainer, false, createSplitStylesheet(ratio));

		// get widgets size
		CheckHelper.check(TestSplitContainerSimple.class, "vpus top width", topLabel.getWidth(), baseWidth);
		CheckHelper.check(TestSplitContainerSimple.class, "vpus top height", topLabel.getHeight(), baseHeight);
		CheckHelper.check(TestSplitContainerSimple.class, "vpus bottom width", bottomLabel.getWidth(), baseWidth);
		CheckHelper.check(TestSplitContainerSimple.class, "vpus bottom height", bottomLabel.getHeight(),
				baseHeight * 2);
		CheckHelper.check(TestSplitContainerSimple.class, "vpus container width", splitContainer.getWidth(), baseWidth);
		CheckHelper.check(TestSplitContainerSimple.class, "vpus container height", splitContainer.getHeight(),
				baseHeight * 3);
		CheckHelper.check(getClass(), "top paint", topLabel.isPaint());
		CheckHelper.check(getClass(), "bottom paint", bottomLabel.isPaint());
	}

	private void testVerticalPackedVariableSplit() {
		final int biggestWidth = 30;
		final int smallestWidth = 15;
		final int baseHeight = 20;
		float ratio = (float) 1 / 3;

		// test biggest width first
		Item topLabel = new Item(biggestWidth, baseHeight);
		Item bottomLabel = new Item(smallestWidth, baseHeight);
		Split splitContainer = createSplitContainer(topLabel, bottomLabel, false);
		TestHelper.showAndWait(splitContainer, false, createSplitStylesheet(ratio));

		// get widgets size
		CheckHelper.check(TestSplitContainerSimple.class, "vpvs top width", topLabel.getWidth(), biggestWidth);
		CheckHelper.check(TestSplitContainerSimple.class, "vpvs top height", topLabel.getHeight(), baseHeight);
		CheckHelper.check(TestSplitContainerSimple.class, "vpvs bottom width", bottomLabel.getWidth(), biggestWidth);
		CheckHelper.check(TestSplitContainerSimple.class, "vpvs bottom height", bottomLabel.getHeight(),
				baseHeight * 2);
		CheckHelper.check(TestSplitContainerSimple.class, "vpvs container width", splitContainer.getWidth(),
				biggestWidth);
		CheckHelper.check(TestSplitContainerSimple.class, "vpvs container height", splitContainer.getHeight(),
				baseHeight * 3);
		CheckHelper.check(getClass(), "top paint", topLabel.isPaint());
		CheckHelper.check(getClass(), "bottom paint", bottomLabel.isPaint());

		topLabel.reset();
		bottomLabel.reset();
		// test smallest width first
		topLabel = new Item(smallestWidth, baseHeight);
		bottomLabel = new Item(biggestWidth, baseHeight);
		splitContainer = createSplitContainer(topLabel, bottomLabel, false);
		TestHelper.showAndWait(splitContainer, false, createSplitStylesheet(ratio));

		// get widgets size
		CheckHelper.check(TestSplitContainerSimple.class, "vpvs top width", topLabel.getWidth(), biggestWidth);
		CheckHelper.check(TestSplitContainerSimple.class, "vpvs top height", topLabel.getHeight(), baseHeight);
		CheckHelper.check(TestSplitContainerSimple.class, "vpvs bottom width", bottomLabel.getWidth(), biggestWidth);
		CheckHelper.check(TestSplitContainerSimple.class, "vpvs bottom height", bottomLabel.getHeight(),
				baseHeight * 2);
		CheckHelper.check(TestSplitContainerSimple.class, "vpvs container width", splitContainer.getWidth(),
				biggestWidth);
		CheckHelper.check(TestSplitContainerSimple.class, "vpvs container height", splitContainer.getHeight(),
				baseHeight * 3);
		CheckHelper.check(getClass(), "top paint", topLabel.isPaint());
		CheckHelper.check(getClass(), "bottom paint", bottomLabel.isPaint());
	}

	private void testVerticalFillUniformSplit() {
		final int baseWidth = 30;
		final int baseHeight = 20;
		float ratio = (float) 1 / 3;

		Item topLabel = new Item(baseWidth, baseHeight);
		Item bottomLabel = new Item(baseWidth, baseHeight);
		Split splitContainer = createSplitContainer(topLabel, bottomLabel, false);
		TestHelper.showAndWait(splitContainer, true, createSplitStylesheet(ratio));

		Display display = Display.getDisplay();
		int displayWidth = display.getWidth();
		int displayHeight = display.getHeight();
		// get widgets size
		CheckHelper.check(TestSplitContainerSimple.class, "vfus top width", topLabel.getWidth(), displayWidth);
		CheckHelper.check(TestSplitContainerSimple.class, "vfus top height", topLabel.getHeight(),
				(int) (displayHeight * ratio));
		CheckHelper.check(TestSplitContainerSimple.class, "vfus bottom width", bottomLabel.getWidth(), displayWidth);
		CheckHelper.check(TestSplitContainerSimple.class, "vfus bottom height", bottomLabel.getHeight(),
				displayHeight - topLabel.getHeight());
		CheckHelper.check(TestSplitContainerAlone.class, "vfus container width", splitContainer.getWidth(),
				displayWidth);
		CheckHelper.check(TestSplitContainerAlone.class, "vfus container height", splitContainer.getHeight(),
				displayHeight);
		CheckHelper.check(getClass(), "top paint", topLabel.isPaint());
		CheckHelper.check(getClass(), "bottom paint", bottomLabel.isPaint());
	}

	private static Split createSplitContainer(Widget firstLabel, Widget secondLabel, boolean horizontal) {
		boolean orientation = (horizontal ? LayoutOrientation.HORIZONTAL : LayoutOrientation.VERTICAL);
		Split splitContainer = new Split(orientation);
		splitContainer.setFirstChild(firstLabel);
		splitContainer.setLastChild(secondLabel);
		return splitContainer;
	}

	private static Stylesheet createSplitStylesheet(float ratio) {
		CascadingStylesheet stylesheet = new CascadingStylesheet();
		EditableStyle style = stylesheet.getSelectorStyle(new TypeSelector(Split.class));
		style.setExtraFloat(Split.RATIO_FIELD, ratio);
		return stylesheet;
	}
}
