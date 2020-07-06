/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test.container.split;

import com.is2t.testsuite.support.CheckHelper;

import ej.microui.display.Display;
import ej.mwt.Container;
import ej.widget.container.Split;
import ej.widget.container.util.LayoutOrientation;
import ej.widget.test.framework.Item;
import ej.widget.test.framework.Test;
import ej.widget.test.framework.TestHelper;

/**
 *
 */
public class TestSplitContainerHierarchy extends Test {

	public static void main(String[] args) {
		TestHelper.launchTest(new TestSplitContainerHierarchy());
	}

	@Override
	public void run(Display display) {
		testHorizontalHorizontalFillUniformSplit();
		testVerticalHorizontalFillUniformSplit();
		testVerticalVerticalFillUniformSplit();
		testHorizontalVerticalFillUniformSplit();
		testHorizontalHorizontalPackedUniformSplit();
		testVerticalHorizontalPackedUniformSplit();
		testVerticalVerticalPackedUniformSplit();
		testHorizontalVerticalPackedUniformSplit();
	}

	private void testHorizontalHorizontalPackedUniformSplit() {
		final int baseWidth = 30;
		final int baseHeight = 20;
		float ratio = (float) 1 / 3;

		Item leftLeftLabel = new Item(baseWidth, baseHeight);
		Item leftRightLabel = new Item(baseWidth, baseHeight);
		Split leftSplitContainer = TestSplitContainerSimple.createSplitContainer(leftLeftLabel, leftRightLabel, ratio,
				true);
		Item rightLeftLabel = new Item(baseWidth, baseHeight);
		Item rightRightLabel = new Item(baseWidth, baseHeight);
		Split rightSplitContainer = TestSplitContainerSimple.createSplitContainer(rightLeftLabel, rightRightLabel,
				ratio, true);
		Split splitContainer = createSplitContainer(leftSplitContainer, rightSplitContainer, ratio, true);
		TestHelper.showAndWait(splitContainer, false);

		// get widgets size
		CheckHelper.check(TestSplitContainerHierarchy.class, "hhpus left left width", leftLeftLabel.getWidth(),
				baseWidth);
		CheckHelper.check(TestSplitContainerHierarchy.class, "hhpus left left height", leftLeftLabel.getHeight(),
				baseHeight);
		CheckHelper.check(TestSplitContainerHierarchy.class, "hhpus left right width", leftRightLabel.getWidth(),
				baseWidth * 2);
		CheckHelper.check(TestSplitContainerHierarchy.class, "hhpus left right height", leftRightLabel.getHeight(),
				baseHeight);
		CheckHelper.check(TestSplitContainerHierarchy.class, "hhpus right left width", rightLeftLabel.getWidth(),
				baseWidth * 2);
		CheckHelper.check(TestSplitContainerHierarchy.class, "hhpus right left height", rightLeftLabel.getHeight(),
				baseHeight);
		CheckHelper.check(TestSplitContainerHierarchy.class, "hhpus right right width", rightRightLabel.getWidth(),
				baseWidth * 4);
		CheckHelper.check(TestSplitContainerHierarchy.class, "hhpus right right height", rightRightLabel.getHeight(),
				baseHeight);
		CheckHelper.check(getClass(), "left left paint", leftLeftLabel.isPaint());
		CheckHelper.check(getClass(), "left right paint", leftRightLabel.isPaint());
		CheckHelper.check(getClass(), "right left paint", rightLeftLabel.isPaint());
		CheckHelper.check(getClass(), "right right paint", rightRightLabel.isPaint());
	}

	private void testVerticalHorizontalPackedUniformSplit() {
		final int baseWidth = 30;
		final int baseHeight = 20;
		float ratio = (float) 1 / 3;

		Item topLeftLabel = new Item(baseWidth, baseHeight);
		Item topRightLabel = new Item(baseWidth, baseHeight);
		Split topSplitContainer = TestSplitContainerSimple.createSplitContainer(topLeftLabel, topRightLabel, ratio,
				true);
		Item bottomLeftLabel = new Item(baseWidth, baseHeight);
		Item bottomRightLabel = new Item(baseWidth, baseHeight);
		Split bottomSplitContainer = TestSplitContainerSimple.createSplitContainer(bottomLeftLabel, bottomRightLabel,
				ratio, true);
		Split splitContainer = createSplitContainer(topSplitContainer, bottomSplitContainer, ratio, false);
		TestHelper.showAndWait(splitContainer, false);

		// get widgets size
		CheckHelper.check(TestSplitContainerHierarchy.class, "vhpus top left width", topLeftLabel.getWidth(),
				baseWidth);
		CheckHelper.check(TestSplitContainerHierarchy.class, "vhpus top left height", topLeftLabel.getHeight(),
				baseHeight);
		CheckHelper.check(TestSplitContainerHierarchy.class, "vhpus top right width", topRightLabel.getWidth(),
				baseWidth * 2);
		CheckHelper.check(TestSplitContainerHierarchy.class, "vhpus top right height", topRightLabel.getHeight(),
				baseHeight);
		CheckHelper.check(TestSplitContainerHierarchy.class, "vhpus bottom left width", bottomLeftLabel.getWidth(),
				baseWidth);
		CheckHelper.check(TestSplitContainerHierarchy.class, "vhpus bottom left height", bottomLeftLabel.getHeight(),
				baseHeight * 2);
		CheckHelper.check(TestSplitContainerHierarchy.class, "vhpus bottom right width", bottomRightLabel.getWidth(),
				baseWidth * 2);
		CheckHelper.check(TestSplitContainerHierarchy.class, "vhpus bottom right height", bottomRightLabel.getHeight(),
				baseHeight * 2);
		CheckHelper.check(getClass(), "top left paint", topLeftLabel.isPaint());
		CheckHelper.check(getClass(), "top right paint", topRightLabel.isPaint());
		CheckHelper.check(getClass(), "bottom left paint", bottomLeftLabel.isPaint());
		CheckHelper.check(getClass(), "bottom right paint", bottomRightLabel.isPaint());
	}

	private void testVerticalVerticalPackedUniformSplit() {
		final int baseWidth = 30;
		final int baseHeight = 20;
		float ratio = (float) 1 / 3;

		Item topTopLabel = new Item(baseWidth, baseHeight);
		Item topBottomLabel = new Item(baseWidth, baseHeight);
		Split topSplitContainer = TestSplitContainerSimple.createSplitContainer(topTopLabel, topBottomLabel, ratio,
				false);
		Item bottomTopLabel = new Item(baseWidth, baseHeight);
		Item bottomBottomLabel = new Item(baseWidth, baseHeight);
		Split bottomSplitContainer = TestSplitContainerSimple.createSplitContainer(bottomTopLabel, bottomBottomLabel,
				ratio, false);
		Split splitContainer = createSplitContainer(topSplitContainer, bottomSplitContainer, ratio, false);
		TestHelper.showAndWait(splitContainer, false);

		// get widgets size
		CheckHelper.check(TestSplitContainerHierarchy.class, "vvpus top top width", topTopLabel.getWidth(), baseWidth);
		CheckHelper.check(TestSplitContainerHierarchy.class, "vvpus top top height", topTopLabel.getHeight(),
				baseHeight);
		CheckHelper.check(TestSplitContainerHierarchy.class, "vvpus top bottom width", topBottomLabel.getWidth(),
				baseWidth);
		CheckHelper.check(TestSplitContainerHierarchy.class, "vvpus top bottom height", topBottomLabel.getHeight(),
				baseHeight * 2);
		CheckHelper.check(TestSplitContainerHierarchy.class, "vvpus bottom top width", bottomTopLabel.getWidth(),
				baseWidth);
		CheckHelper.check(TestSplitContainerHierarchy.class, "vvpus bottom top height", bottomTopLabel.getHeight(),
				baseHeight * 2);
		CheckHelper.check(TestSplitContainerHierarchy.class, "vvpus bottom bottom width", bottomBottomLabel.getWidth(),
				baseWidth);
		CheckHelper.check(TestSplitContainerHierarchy.class, "vvpus bottom bottom height",
				bottomBottomLabel.getHeight(), baseHeight * 4);
		CheckHelper.check(getClass(), "top top paint", topTopLabel.isPaint());
		CheckHelper.check(getClass(), "top bottom paint", topBottomLabel.isPaint());
		CheckHelper.check(getClass(), "bottom top paint", bottomTopLabel.isPaint());
		CheckHelper.check(getClass(), "bottom bottom paint", bottomBottomLabel.isPaint());
	}

	private void testHorizontalVerticalPackedUniformSplit() {
		final int baseWidth = 30;
		final int baseHeight = 20;
		float ratio = (float) 1 / 3;

		Item leftTopLabel = new Item(baseWidth, baseHeight);
		Item leftBottomLabel = new Item(baseWidth, baseHeight);
		Split leftSplitContainer = TestSplitContainerSimple.createSplitContainer(leftTopLabel, leftBottomLabel, ratio,
				false);
		Item rightTopLabel = new Item(baseWidth, baseHeight);
		Item rightBottomLabel = new Item(baseWidth, baseHeight);
		Split rightSplitContainer = TestSplitContainerSimple.createSplitContainer(rightTopLabel, rightBottomLabel,
				ratio, false);
		Split splitContainer = createSplitContainer(leftSplitContainer, rightSplitContainer, ratio, true);
		TestHelper.showAndWait(splitContainer, false);

		// get widgets size
		CheckHelper.check(TestSplitContainerHierarchy.class, "hvpus left top width", leftTopLabel.getWidth(),
				baseWidth);
		CheckHelper.check(TestSplitContainerHierarchy.class, "hvpus left topheight", leftTopLabel.getHeight(),
				baseHeight);
		CheckHelper.check(TestSplitContainerHierarchy.class, "hvpus left bottom width", leftBottomLabel.getWidth(),
				baseWidth);
		CheckHelper.check(TestSplitContainerHierarchy.class, "hvpus left bottom height", leftBottomLabel.getHeight(),
				baseHeight * 2);
		CheckHelper.check(TestSplitContainerHierarchy.class, "hvpus right top width", rightTopLabel.getWidth(),
				baseWidth * 2);
		CheckHelper.check(TestSplitContainerHierarchy.class, "hvpus right top height", rightTopLabel.getHeight(),
				baseHeight);
		CheckHelper.check(TestSplitContainerHierarchy.class, "hvpus right bottom width", rightBottomLabel.getWidth(),
				baseWidth * 2);
		CheckHelper.check(TestSplitContainerHierarchy.class, "hvpus right bottom height", rightBottomLabel.getHeight(),
				baseHeight * 2);
		CheckHelper.check(getClass(), "left top paint", leftTopLabel.isPaint());
		CheckHelper.check(getClass(), "left bottom paint", leftBottomLabel.isPaint());
		CheckHelper.check(getClass(), "right top paint", rightTopLabel.isPaint());
		CheckHelper.check(getClass(), "right bottom paint", rightBottomLabel.isPaint());
	}

	private void testHorizontalHorizontalFillUniformSplit() {
		final int baseWidth = 30;
		final int baseHeight = 20;
		float ratio = (float) 1 / 3;

		Item leftLeftLabel = new Item(baseWidth, baseHeight);
		Item leftRightLabel = new Item(baseWidth, baseHeight);
		Split leftSplitContainer = TestSplitContainerSimple.createSplitContainer(leftLeftLabel, leftRightLabel, ratio,
				true);
		Item rightLeftLabel = new Item(baseWidth, baseHeight);
		Item rightRightLabel = new Item(baseWidth, baseHeight);
		Split rightSplitContainer = TestSplitContainerSimple.createSplitContainer(rightLeftLabel, rightRightLabel,
				ratio, true);
		Split splitContainer = createSplitContainer(leftSplitContainer, rightSplitContainer, ratio, true);
		TestHelper.showAndWait(splitContainer, true);

		Display display = Display.getDisplay();
		int displayWidth = display.getWidth();
		int displayHeight = display.getHeight();
		// get widgets size
		CheckHelper.check(TestSplitContainerHierarchy.class, "hhfus left left width", leftLeftLabel.getWidth(),
				(int) (displayWidth * ratio * ratio));
		CheckHelper.check(TestSplitContainerHierarchy.class, "hhfus left left height", leftLeftLabel.getHeight(),
				displayHeight);
		CheckHelper.check(TestSplitContainerHierarchy.class, "hhfus left right width", leftRightLabel.getWidth(),
				(int) (displayWidth * ratio) - leftLeftLabel.getWidth());
		CheckHelper.check(TestSplitContainerHierarchy.class, "hhfus left right height", leftRightLabel.getHeight(),
				displayHeight);
		CheckHelper.check(TestSplitContainerHierarchy.class, "hhfus right left width", rightLeftLabel.getWidth(),
				(int) ((displayWidth - leftSplitContainer.getWidth()) * ratio));
		CheckHelper.check(TestSplitContainerHierarchy.class, "hhfus right left height", rightLeftLabel.getHeight(),
				displayHeight);
		CheckHelper.check(TestSplitContainerHierarchy.class, "hhfus right right width", rightRightLabel.getWidth(),
				displayWidth - leftSplitContainer.getWidth() - rightLeftLabel.getWidth());
		CheckHelper.check(TestSplitContainerHierarchy.class, "hhfus right right height", rightRightLabel.getHeight(),
				displayHeight);
		CheckHelper.check(getClass(), "left left paint", leftLeftLabel.isPaint());
		CheckHelper.check(getClass(), "left right paint", leftRightLabel.isPaint());
		CheckHelper.check(getClass(), "right left paint", rightLeftLabel.isPaint());
		CheckHelper.check(getClass(), "right right paint", rightRightLabel.isPaint());
	}

	private void testVerticalHorizontalFillUniformSplit() {
		final int baseWidth = 30;
		final int baseHeight = 20;
		float ratio = (float) 1 / 3;

		Item topLeftLabel = new Item(baseWidth, baseHeight);
		Item topRightLabel = new Item(baseWidth, baseHeight);
		Split topSplitContainer = TestSplitContainerSimple.createSplitContainer(topLeftLabel, topRightLabel, ratio,
				true);
		Item bottomLeftLabel = new Item(baseWidth, baseHeight);
		Item bottomRightLabel = new Item(baseWidth, baseHeight);
		Split bottomSplitContainer = TestSplitContainerSimple.createSplitContainer(bottomLeftLabel, bottomRightLabel,
				ratio, true);
		Split splitContainer = createSplitContainer(topSplitContainer, bottomSplitContainer, ratio, false);
		TestHelper.showAndWait(splitContainer, true);

		Display display = Display.getDisplay();
		int displayWidth = display.getWidth();
		int displayHeight = display.getHeight();
		// get widgets size
		CheckHelper.check(TestSplitContainerHierarchy.class, "vhfus top left width", topLeftLabel.getWidth(),
				(int) (displayWidth * ratio));
		CheckHelper.check(TestSplitContainerHierarchy.class, "vhfus top left height", topLeftLabel.getHeight(),
				(int) (displayHeight * ratio));
		CheckHelper.check(TestSplitContainerHierarchy.class, "vhfus top right width", topRightLabel.getWidth(),
				displayWidth - topLeftLabel.getWidth());
		CheckHelper.check(TestSplitContainerHierarchy.class, "vhfus top right height", topRightLabel.getHeight(),
				(int) (displayHeight * ratio));
		CheckHelper.check(TestSplitContainerHierarchy.class, "vhfus bottom left width", bottomLeftLabel.getWidth(),
				(int) (displayWidth * ratio));
		CheckHelper.check(TestSplitContainerHierarchy.class, "vhfus bottom left height", bottomLeftLabel.getHeight(),
				displayHeight - topLeftLabel.getHeight());
		CheckHelper.check(TestSplitContainerHierarchy.class, "vhfus bottom right width", bottomRightLabel.getWidth(),
				displayWidth - bottomLeftLabel.getWidth());
		CheckHelper.check(TestSplitContainerHierarchy.class, "vhfus bottom right height", bottomRightLabel.getHeight(),
				displayHeight - topRightLabel.getHeight());
		CheckHelper.check(getClass(), "top left paint", topLeftLabel.isPaint());
		CheckHelper.check(getClass(), "top right paint", topRightLabel.isPaint());
		CheckHelper.check(getClass(), "bottom left paint", bottomLeftLabel.isPaint());
		CheckHelper.check(getClass(), "bottom right paint", bottomRightLabel.isPaint());
	}

	private void testVerticalVerticalFillUniformSplit() {
		final int baseWidth = 30;
		final int baseHeight = 20;
		float ratio = (float) 1 / 3;

		Item topTopLabel = new Item(baseWidth, baseHeight);
		Item topBottomLabel = new Item(baseWidth, baseHeight);
		Split topSplitContainer = TestSplitContainerSimple.createSplitContainer(topTopLabel, topBottomLabel, ratio,
				false);
		Item bottomTopLabel = new Item(baseWidth, baseHeight);
		Item bottomBottomLabel = new Item(baseWidth, baseHeight);
		Split bottomSplitContainer = TestSplitContainerSimple.createSplitContainer(bottomTopLabel, bottomBottomLabel,
				ratio, false);
		Split splitContainer = createSplitContainer(topSplitContainer, bottomSplitContainer, ratio, false);
		TestHelper.showAndWait(splitContainer, true);

		Display display = Display.getDisplay();
		int displayWidth = display.getWidth();
		int displayHeight = display.getHeight();
		// get widgets size
		CheckHelper.check(TestSplitContainerHierarchy.class, "vvfus top top width", topTopLabel.getWidth(),
				displayWidth);
		CheckHelper.check(TestSplitContainerHierarchy.class, "vvfus top top height", topTopLabel.getHeight(),
				(int) (displayHeight * ratio * ratio));
		CheckHelper.check(TestSplitContainerHierarchy.class, "vvfus top bottom width", topBottomLabel.getWidth(),
				displayWidth);
		CheckHelper.check(TestSplitContainerHierarchy.class, "vvfus top bottom height", topBottomLabel.getHeight(),
				(int) (displayHeight * ratio) - topTopLabel.getHeight());
		CheckHelper.check(TestSplitContainerHierarchy.class, "vvfus bottom top width", bottomTopLabel.getWidth(),
				displayWidth);
		CheckHelper.check(TestSplitContainerHierarchy.class, "vvfus bottom top height", bottomTopLabel.getHeight(),
				(int) ((displayHeight - topSplitContainer.getHeight()) * ratio));
		CheckHelper.check(TestSplitContainerHierarchy.class, "vvfus bottom bottom width", bottomBottomLabel.getWidth(),
				displayWidth);
		CheckHelper.check(TestSplitContainerHierarchy.class, "vvfus bottom bottom height",
				bottomBottomLabel.getHeight(),
				displayHeight - topSplitContainer.getHeight() - bottomTopLabel.getHeight());
		CheckHelper.check(getClass(), "top top paint", topTopLabel.isPaint());
		CheckHelper.check(getClass(), "top bottom paint", topBottomLabel.isPaint());
		CheckHelper.check(getClass(), "bottom top paint", bottomTopLabel.isPaint());
		CheckHelper.check(getClass(), "bottom bottom paint", bottomBottomLabel.isPaint());
	}

	private void testHorizontalVerticalFillUniformSplit() {
		final int baseWidth = 30;
		final int baseHeight = 20;
		float ratio = (float) 1 / 3;

		Item leftTopLabel = new Item(baseWidth, baseHeight);
		Item leftBottomLabel = new Item(baseWidth, baseHeight);
		Split leftSplitContainer = TestSplitContainerSimple.createSplitContainer(leftTopLabel, leftBottomLabel, ratio,
				false);
		Item rightTopLabel = new Item(baseWidth, baseHeight);
		Item rightBottomLabel = new Item(baseWidth, baseHeight);
		Split rightSplitContainer = TestSplitContainerSimple.createSplitContainer(rightTopLabel, rightBottomLabel,
				ratio, false);
		Split splitContainer = createSplitContainer(leftSplitContainer, rightSplitContainer, ratio, true);
		TestHelper.showAndWait(splitContainer, true);

		Display display = Display.getDisplay();
		int displayWidth = display.getWidth();
		int displayHeight = display.getHeight();
		// get widgets size
		CheckHelper.check(TestSplitContainerHierarchy.class, "hvfus left top width", leftTopLabel.getWidth(),
				(int) (displayWidth * ratio));
		CheckHelper.check(TestSplitContainerHierarchy.class, "hvfus left topheight", leftTopLabel.getHeight(),
				(int) (displayHeight * ratio));
		CheckHelper.check(TestSplitContainerHierarchy.class, "hvfus left bottom width", leftBottomLabel.getWidth(),
				(int) (displayWidth * ratio));
		CheckHelper.check(TestSplitContainerHierarchy.class, "hvfus left bottom height", leftBottomLabel.getHeight(),
				displayHeight - leftTopLabel.getHeight());
		CheckHelper.check(TestSplitContainerHierarchy.class, "hvfus right top width", rightTopLabel.getWidth(),
				displayWidth - leftTopLabel.getWidth());
		CheckHelper.check(TestSplitContainerHierarchy.class, "hvfus right top height", rightTopLabel.getHeight(),
				(int) (displayHeight * ratio));
		CheckHelper.check(TestSplitContainerHierarchy.class, "hvfus right bottom width", rightBottomLabel.getWidth(),
				displayWidth - leftBottomLabel.getWidth());
		CheckHelper.check(TestSplitContainerHierarchy.class, "hvfus right bottom height", rightBottomLabel.getHeight(),
				displayHeight - rightTopLabel.getHeight());
		CheckHelper.check(getClass(), "left top paint", leftTopLabel.isPaint());
		CheckHelper.check(getClass(), "left bottom paint", leftBottomLabel.isPaint());
		CheckHelper.check(getClass(), "right top paint", rightTopLabel.isPaint());
		CheckHelper.check(getClass(), "right bottom paint", rightBottomLabel.isPaint());
	}

	static Split createSplitContainer(Container firstLabel, Container secondLabel, float ratio, boolean horizontal) {
		boolean orientation = (horizontal ? LayoutOrientation.HORIZONTAL : LayoutOrientation.VERTICAL);
		Split splitContainer = new Split(orientation, ratio);
		splitContainer.setFirstChild(firstLabel);
		splitContainer.setLastChild(secondLabel);
		return splitContainer;
	}

}
