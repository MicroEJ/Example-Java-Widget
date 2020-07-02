/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test;

import com.is2t.testsuite.support.CheckHelper;

import ej.microui.display.Display;
import ej.widget.test.basic.AnimatedImageTest;
import ej.widget.test.basic.BoxTest;
import ej.widget.test.basic.ButtonImagePathTest;
import ej.widget.test.basic.ButtonImageViewTest;
import ej.widget.test.basic.ImageWidgetTest;
import ej.widget.test.basic.ImageViewTest;
import ej.widget.test.basic.PasswordFieldTest;
import ej.widget.test.basic.RenderableLabelTest;
import ej.widget.test.basic.TextFieldTest;
import ej.widget.test.basic.drawing.CircularProgressBarTest;
import ej.widget.test.composed.TestButtonWrapper;
import ej.widget.test.composed.TestButtonWrapperOver;
import ej.widget.test.composed.TestToggleWrapperGroup;
import ej.widget.test.composed.TestToggleWrapperOver;
import ej.widget.test.composed.TestWrapper;
import ej.widget.test.composed.TestWrapperEncapsulation;
import ej.widget.test.container.menu.TestCarousel;
import ej.widget.test.container.scroll.TestScrollContainerSimple;
import ej.widget.test.container.scroll.TestScrollInList;
import ej.widget.test.framework.Test;
import ej.widget.test.framework.TestHelper;
import ej.widget.test.lifecycle.TestShownFillCarousel;
import ej.widget.test.lifecycle.TestShownListCarousel;
import ej.widget.test.lifecycle.TestShownTransitionContainer;
import ej.widget.test.model.TestDefaultBoundedRangeModel;
import ej.widget.test.toggle.RadioTest;
import ej.widget.test.toggle.ToggleHelperTest;
import ej.widget.test.toggle.ToggleLayoutTest;
import ej.widget.test.toggle.ToggleTest;
import ej.widget.test.update.UpdateTest2;
import ej.widget.test.util.ButtonHelperTest;

public class TestAll extends Test {

	public static void main(String[] args) {
		TestHelper.launchTest(new TestAll());
	}

	private boolean globalState;

	@Override
	public void run(Display display) {
		this.globalState = true;

		CheckHelper.verboseMode = false;

		//// MWT ////

		// test lifecycle
		launchTest(display, new TestShownFillCarousel());
		launchTest(display, new TestShownListCarousel());
		launchTest(display, new TestShownTransitionContainer());

		//// Containers ////

		launchTest(display, new TestCarousel());

		launchTest(display, new TestScrollContainerSimple());
		launchTest(display, new TestScrollInList());

		//// Widgets ////

		// basic
		launchTest(display, new AnimatedImageTest());
		launchTest(display, new BoxTest());
		launchTest(display, new ButtonImagePathTest());
		launchTest(display, new ButtonImageViewTest());
		launchTest(display, new ImageWidgetTest());
		launchTest(display, new ImageViewTest());
		launchTest(display, new PasswordFieldTest());
		launchTest(display, new RenderableLabelTest());
		launchTest(display, new TextFieldTest());

		// basic/drawing
		launchTest(display, new CircularProgressBarTest());

		// composed
		launchTest(display, new TestButtonWrapper());
		launchTest(display, new TestButtonWrapperOver());
		launchTest(display, new TestToggleWrapperGroup());
		launchTest(display, new TestToggleWrapperOver());
		launchTest(display, new TestWrapper());
		launchTest(display, new TestWrapperEncapsulation());
		launchTest(display, new ej.widget.test.composed.ToggleTest());

		// model
		launchTest(display, new TestDefaultBoundedRangeModel());

		// toggle
		launchTest(display, new RadioTest());
		launchTest(display, new ToggleLayoutTest());
		launchTest(display, new ToggleTest());

		// update
		launchTest(display, new UpdateTest2());

		// util
		launchTest(display, new ButtonHelperTest());
		launchTest(display, new ToggleHelperTest());

		if (this.globalState) {
			System.out.println("PASSED");
		} else {
			System.out.println("FAILED");
		}
	}

	private void launchTest(Display display, Test test) {
		try {
			CheckHelper.startCheck(test.getClass());
			test.run(display);
		} catch (Error e) {
			CheckHelper.check(test.getClass(), e.getMessage(), false);
			e.printStackTrace();
		} finally {
			if (!CheckHelper.getState()) {
				this.globalState = false;
			}
			CheckHelper.endCheck(test.getClass());
		}
	}

}
