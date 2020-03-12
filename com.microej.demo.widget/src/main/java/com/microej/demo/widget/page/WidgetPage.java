/*
 * Copyright 2014-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package com.microej.demo.widget.page;

import com.microej.demo.widget.style.ClassSelectors;

import ej.widget.basic.AbstractSlider;
import ej.widget.basic.Button;
import ej.widget.composed.ToggleWrapper;
import ej.widget.composed.Wrapper;
import ej.widget.container.Grid;
import ej.widget.toggle.ToggleGroup;

/**
 * Haptic widgets demonstration page.
 */
public abstract class WidgetPage extends AbstractDemoPage {

	private static final int MIN_VALUE = 0;
	private static final int MAX_VALUE = 100;
	private static final int INITIAL_VALUE = 50;

	/**
	 * Creates a widget page.
	 *
	 * @param title
	 *            the page title.
	 */
	public WidgetPage(String title) {
		super(false, title);

		// layout:
		// | switch | radio 1 |
		// | check box | radio 2 |
		// | slider | button |

		Grid grid = new Grid(true, 2);

		ToggleWrapper switch_ = newSwitch("Switch"); //$NON-NLS-1$
		switch_.setAdjustedToChild(false);
		grid.add(switch_);

		ToggleGroup toggleGroup = new ToggleGroup();

		ToggleWrapper radio1 = newRadioButton("Radio1"); //$NON-NLS-1$
		radio1.setAdjustedToChild(false);
		grid.add(radio1);
		toggleGroup.addToggle(radio1.getToggle());

		ToggleWrapper checkbox = newCheckBox("Checkbox"); //$NON-NLS-1$
		checkbox.setAdjustedToChild(false);
		grid.add(checkbox);

		ToggleWrapper radio2 = newRadioButton("Radio2"); //$NON-NLS-1$
		radio2.setAdjustedToChild(false);
		toggleGroup.addToggle(radio2.getToggle());
		grid.add(radio2);

		AbstractSlider slider = newSlider(MIN_VALUE, MAX_VALUE, INITIAL_VALUE);
		grid.add(slider);

		Wrapper buttonWrapper = new Wrapper();
		buttonWrapper.setAdjustedToChild(false);
		Button button = new Button("Button"); //$NON-NLS-1$
		button.addClassSelector(ClassSelectors.ILLUSTRATED_BUTTON);
		buttonWrapper.setWidget(button);
		buttonWrapper.addClassSelector(ClassSelectors.CENTERED);
		grid.add(buttonWrapper);

		setCenter(grid);
	}

	/**
	 * Gets a new check box widget with the given state.
	 *
	 * @param string
	 *            the label of the check box.
	 * @return a new check box widget with the given state.
	 */
	protected abstract ToggleWrapper newCheckBox(String string);

	/**
	 * Gets a new switch widget with the given state.
	 *
	 * @param string
	 *            the label of the switch.
	 * @return a new switch widget with the given state.
	 */
	protected abstract ToggleWrapper newSwitch(String string);

	/**
	 * Gets a new radio button widget with the given state.
	 *
	 * @param string
	 *            the label of the radio button.
	 * @return a new radio button widget with the given state.
	 */
	protected abstract ToggleWrapper newRadioButton(String string);

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

}
