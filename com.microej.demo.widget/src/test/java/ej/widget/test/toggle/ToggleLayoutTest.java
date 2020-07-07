/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test.toggle;

import com.is2t.testsuite.support.CheckHelper;

import ej.microui.display.Display;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.mwt.Desktop;
import ej.mwt.Widget;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.Style;
import ej.mwt.style.dimension.NoDimension;
import ej.mwt.style.dimension.OptimalDimension;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.TypeSelector;
import ej.mwt.util.Alignment;
import ej.mwt.util.Size;
import ej.widget.basic.Box;
import ej.widget.basic.Label;
import ej.widget.composed.Check;
import ej.widget.composed.Radio;
import ej.widget.composed.Switch;
import ej.widget.composed.Toggle;
import ej.widget.test.framework.Test;
import ej.widget.test.framework.TestHelper;

/**
 *
 */
public class ToggleLayoutTest extends Test {

	public static void main(String[] args) {
		TestHelper.launchTest(new ToggleLayoutTest());
	}

	@Override
	public void run(Display display) {
		testToggle(new Toggle(new Box() {

			@Override
			protected void computeContentOptimalSize(Size size) {
				size.setSize(10, 10);
			}

			@Override
			protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
				Style style = getStyle();
				g.setColor(style.getColor());
				Painter.fillRectangle(g, 0, 0, contentWidth, contentHeight);
			}
		}, "toggle"));
		testToggle(new Check("toggle"));
		testToggle(new Radio("toggle"));
		testToggle(new Switch("toggle"));

	}

	private void testToggle(Toggle toggle) {
		CascadingStylesheet stylesheet = new CascadingStylesheet();

		Widget box = toggle.getBox();
		Widget label = toggle.getLabel();

		Display display = Display.getDisplay();
		Desktop desktop = new Desktop();
		desktop.setStylesheet(stylesheet);
		desktop.setWidget(toggle);
		desktop.requestShow();
		TestHelper.waitForAllEvents();

		// Full screen, toggle is adjusted to its content.
		CheckHelper.check(getClass(), "Toggle width", toggle.getWidth(), display.getWidth());
		CheckHelper.check(getClass(), "Toggle height", toggle.getHeight(), display.getHeight());
		CheckHelper.check(getClass(), "Box x", box.getX(), 0);
		CheckHelper.check(getClass(), "Box y", box.getY(), 0);
		CheckHelper.check(getClass(), "Box width", box.getWidth(), box.getWidth());
		CheckHelper.check(getClass(), "Box height", box.getHeight(), toggle.getHeight());
		CheckHelper.check(getClass(), "Label x", label.getX(), box.getWidth());
		CheckHelper.check(getClass(), "Label y", label.getY(), 0);
		CheckHelper.check(getClass(), "Label width", label.getWidth(), toggle.getWidth() - box.getWidth());
		CheckHelper.check(getClass(), "Label height", label.getHeight(), toggle.getHeight());

		TestHelper.mergeStyle(stylesheet, label, OptimalDimension.OPTIMAL_DIMENSION_XY);
		desktop.requestLayOut();
		TestHelper.waitForAllEvents();

		// Full screen, toggle is not adjusted to its content.
		CheckHelper.check(getClass(), "Toggle width", toggle.getWidth(), display.getWidth());
		CheckHelper.check(getClass(), "Toggle height", toggle.getHeight(), display.getHeight());
		CheckHelper.check(getClass(), "Box x", box.getX(), 0);
		CheckHelper.check(getClass(), "Box y", box.getY(), 0);
		CheckHelper.check(getClass(), "Box width", box.getWidth(), box.getWidth());
		CheckHelper.check(getClass(), "Box height", box.getHeight(), box.getHeight());
		CheckHelper.check(getClass(), "Label x", label.getX(), box.getWidth());
		CheckHelper.check(getClass(), "Label y", label.getY(), 0);
		CheckHelper.check(getClass(), "Label width", label.getWidth(), label.getWidth());
		CheckHelper.check(getClass(), "Label height", label.getHeight(), label.getHeight());

		EditableStyle centerStyle = stylesheet.getSelectorStyle(new TypeSelector(Toggle.class));
		centerStyle.setHorizontalAlignment(Alignment.HCENTER);
		centerStyle.setVerticalAlignment(Alignment.VCENTER);

		centerStyle = stylesheet.getSelectorStyle(new TypeSelector(Label.class));
		centerStyle.setHorizontalAlignment(Alignment.HCENTER);
		centerStyle.setVerticalAlignment(Alignment.VCENTER);

		TestHelper.mergeStyle(stylesheet, label, NoDimension.NO_DIMENSION);
		desktop.requestLayOut();
		TestHelper.waitForAllEvents();

		// Full screen, toggle is adjusted to its content, its content is centered.
		int toggleWidth = toggle.getWidth();
		int toggleHeight = toggle.getHeight();
		CheckHelper.check(getClass(), "Toggle width", toggleWidth, display.getWidth());
		CheckHelper.check(getClass(), "Toggle height", toggleHeight, display.getHeight());
		int boxWidth = box.getWidth();
		int boxHeight = box.getHeight();
		int labelWidth = label.getWidth();
		int labelHeight = label.getHeight();
		int boxX = (toggleWidth - boxWidth - labelWidth) / 2;
		check("Box x", box.getX(), boxX);
		check("Box y", box.getY(), (toggleHeight - boxHeight) / 2);
		CheckHelper.check(getClass(), "Box width", box.getWidth(), box.getWidth());
		CheckHelper.check(getClass(), "Box height", box.getHeight(), toggle.getHeight());
		check("Label x", label.getX(), boxX + boxWidth);
		check("Label y", label.getY(), (toggleHeight - labelHeight) / 2);
		CheckHelper.check(getClass(), "Label width", label.getWidth(), toggle.getWidth() - box.getWidth());
		CheckHelper.check(getClass(), "Label height", label.getHeight(), toggle.getHeight());

		TestHelper.mergeStyle(stylesheet, label, OptimalDimension.OPTIMAL_DIMENSION_XY);
		desktop.requestLayOut();
		TestHelper.waitForAllEvents();

		// Full screen, toggle is not adjusted to its content, its content is centered.
		toggleWidth = toggle.getWidth();
		toggleHeight = toggle.getHeight();
		CheckHelper.check(getClass(), "Toggle width", toggle.getWidth(), display.getWidth());
		CheckHelper.check(getClass(), "Toggle height", toggle.getHeight(), display.getHeight());
		boxWidth = box.getWidth();
		boxHeight = box.getHeight();
		labelWidth = label.getWidth();
		labelHeight = label.getHeight();
		boxX = (toggleWidth - boxWidth - labelWidth) / 2;
		check("Box x", box.getX(), 0);
		check("Box y", box.getY(), (toggleHeight - boxHeight) / 2);
		CheckHelper.check(getClass(), "Box width", box.getWidth(), box.getWidth());
		CheckHelper.check(getClass(), "Box height", box.getHeight(), box.getHeight());
		check("Label x", label.getX(), boxX + boxWidth);
		check("Label y", label.getY(), (toggleHeight - labelHeight) / 2);
		CheckHelper.check(getClass(), "Label width", label.getWidth(), label.getWidth());
		CheckHelper.check(getClass(), "Label height", label.getHeight(), label.getHeight());

		stylesheet.reset();
	}

	private void check(String message, int value, int expectedValue) {
		if (value >= expectedValue - 1 && value <= expectedValue + 1) {
			value = expectedValue;
		}
		CheckHelper.check(getClass(), message, value, expectedValue);
	}

}
