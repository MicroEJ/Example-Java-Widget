/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.microej.demo.widgets.page;

import com.microej.demo.widgets.style.ClassSelectors;

import ej.composite.BorderComposite;
import ej.composite.GridComposite;
import ej.mwt.MWT;
import ej.mwt.Widget;
import ej.widget.basic.AbstractSlider;
import ej.widget.basic.Label;
import ej.widget.basic.Toggle;
import ej.widget.basic.ToggleGroup;
import ej.widget.composed.SimpleButton;
import ej.widget.composed.ToggleButton;

/**
 * Haptic widgets demonstration page.
 */
public abstract class WidgetPage extends AbstractDemoPage {

	private static final int MIN_VALUE = 0;
	private static final int MAX_VALUE = 100;
	private static final int INITIAL_VALUE = 50;

	@Override
	protected boolean canGoBack() {
		return true;
	}

	@Override
	protected Widget createMainContent() {
		// layout:
		// | switch | radio 1 |
		// | check box | radio 2 |
		// | slider | button |

		GridComposite grid = new GridComposite();
		grid.setHorizontal(true);
		grid.setCount(2);

		Toggle switch_ = newSwitch(false);
		newToggleItem(switch_, "Switch", grid); //$NON-NLS-1$

		ToggleGroup toggleGroup = new ToggleGroup();

		Toggle radio1 = newRadioButton(false);
		toggleGroup.addToggle(radio1);
		newToggleItem(radio1, "Radio 1", grid); //$NON-NLS-1$

		Toggle checkbox = newCheckBox(true);
		newToggleItem(checkbox, "Checkbox", grid); //$NON-NLS-1$

		Toggle radio2 = newRadioButton(true);
		toggleGroup.addToggle(radio2);
		newToggleItem(radio2, "Radio 2", grid); //$NON-NLS-1$

		AbstractSlider slider = newSlider(MIN_VALUE, MAX_VALUE, INITIAL_VALUE);
		grid.add(slider);

		SimpleButton button = new SimpleButton("Button"); //$NON-NLS-1$
		button.addClassSelector(ClassSelectors.ILLUSTRATED_BUTTON);
		grid.add(button);

		return grid;
	}

	/**
	 * Gets a new check box widget with the given state.
	 *
	 * @param checked
	 *            the state of the check box to create.
	 * @return a new check box widget with the given state.
	 */
	protected abstract Toggle newCheckBox(boolean checked);

	/**
	 * Gets a new switch widget with the given state.
	 *
	 * @param checked
	 *            the state of the switch to create.
	 * @return a new switch widget with the given state.
	 */
	protected abstract Toggle newSwitch(boolean checked);

	/**
	 * Gets a new radio button widget with the given state.
	 *
	 * @param checked
	 *            the state of the radio button to create.
	 * @return a new radio button widget with the given state.
	 */
	protected abstract Toggle newRadioButton(boolean checked);

	/**
	 * Gets a new slider widget with the given parameters.
	 *
	 * @param min
	 *            the minimum value of the slider.
	 * @param max
	 *            the maximum value of the slider.
	 * @param initial
	 *            the initial value of the slider.
	 *
	 * @return a new slider widget.
	 */
	protected abstract AbstractSlider newSlider(int min, int max, int initial);

	// Creates a toggle that changes its state also when the text is clicked.
	private static void newToggleItem(Toggle toggle, String text, GridComposite grid) {
		Label label = new Label(text);
		ToggleButton toggleButton = new ToggleButton(toggle);
		BorderComposite toggleButtonContent = new BorderComposite();
		toggleButtonContent.add(toggle, MWT.LEFT);
		toggleButtonContent.add(label, MWT.CENTER);
		toggleButton.setWidget(toggleButtonContent);
		grid.add(toggleButton);
	}
}
