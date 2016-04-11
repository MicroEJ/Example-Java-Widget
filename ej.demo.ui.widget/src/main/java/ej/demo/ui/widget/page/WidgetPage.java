/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package ej.demo.ui.widget.page;

import ej.container.Grid;
import ej.demo.ui.widget.style.ClassSelectors;
import ej.mwt.Widget;
import ej.widget.basic.AbstractSlider;
import ej.widget.composed.Button;
import ej.widget.composed.ToggleComposite;
import ej.widget.toggle.ToggleGroup;

/**
 * Haptic widgets demonstration page.
 */
public abstract class WidgetPage extends AbstractDemoPage {

	private static final int MIN_VALUE = 0;
	private static final int MAX_VALUE = 100;
	private static final int INITIAL_VALUE = 50;

	@Override
	protected Widget createMainContent() {
		// layout:
		// | switch | radio 1 |
		// | check box | radio 2 |
		// | slider | button |

		Grid grid = new Grid(true, 2);

		ToggleComposite switch_ = newSwitch("Switch"); //$NON-NLS-1$
		grid.add(switch_);

		ToggleGroup toggleGroup = new ToggleGroup();

		ToggleComposite radio1 = newRadioButton("Radio1"); //$NON-NLS-1$
		grid.add(radio1);
		toggleGroup.addToggle(radio1.getToggle());

		ToggleComposite checkbox = newCheckBox("Checkbox"); //$NON-NLS-1$
		grid.add(checkbox);

		ToggleComposite radio2 = newRadioButton("Radio2"); //$NON-NLS-1$
		toggleGroup.addToggle(radio2.getToggle());
		grid.add(radio2);

		AbstractSlider slider = newSlider(MIN_VALUE, MAX_VALUE, INITIAL_VALUE);
		grid.add(slider);

		Button button = new Button("Button"); //$NON-NLS-1$
		button.addClassSelector(ClassSelectors.ILLUSTRATED_BUTTON);
		grid.add(button);

		return grid;
	}

	/**
	 * Gets a new check box widget with the given state.
	 *
	 * @param string
	 *            the label of the check box.
	 * @return a new check box widget with the given state.
	 */
	protected abstract ToggleComposite newCheckBox(String string);

	/**
	 * Gets a new switch widget with the given state.
	 *
	 * @param string
	 *            the label of the switch.
	 * @return a new switch widget with the given state.
	 */
	protected abstract ToggleComposite newSwitch(String string);

	/**
	 * Gets a new radio button widget with the given state.
	 *
	 * @param string
	 *            the label of the radio button.
	 * @return a new radio button widget with the given state.
	 */
	protected abstract ToggleComposite newRadioButton(String string);

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
