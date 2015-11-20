/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.microej.demo.widgets.page;

import com.microej.demo.widgets.style.ClassSelector;

import ej.composite.BorderComposite;
import ej.composite.GridComposite;
import ej.mwt.MWT;
import ej.mwt.Widget;
import ej.widget.StyledWidget;
import ej.widget.basic.AbstractSlider;
import ej.widget.basic.Label;
import ej.widget.basic.Toggle;
import ej.widget.basic.ToggleGroup;
import ej.widget.composed.Button;

/**
 * A page used to illustrate some widgets.
 */
public abstract class WidgetPage extends WidgetsPage {

	private static final int MIN_VALUE = 0;
	private static final int MAX_VALUE = 100;
	private static final int INITIAL_VALUE = 50;

	@Override
	protected boolean canGoBack() {
		return true;
	}

	@Override
	protected Widget createMainContent() {
		GridComposite grid = new GridComposite();
		grid.setHorizontal(true);
		grid.setCount(2);

		Toggle switch_ = newSwitch(false);
		newItem(switch_, "Switch", grid);

		ToggleGroup toggleGroup = new ToggleGroup();

		Toggle radio1 = newRadioButton(false);
		toggleGroup.addToggle(radio1);
		newItem(radio1, "Radio 1", grid);

		Toggle checkbox = newCheckBox(true);
		newItem(checkbox, "Checkbox", grid);

		Toggle radio2 = newRadioButton(true);
		toggleGroup.addToggle(radio2);
		newItem(radio2, "Radio 2", grid);

		AbstractSlider slider = newSlider(MIN_VALUE, MAX_VALUE, INITIAL_VALUE);
		grid.add(slider);

		Label buttonLabel = new Label("Button");
		Button button = new Button();
		button.setWidget(buttonLabel);
		// Button button = new SimpleButton("Button");
		button.addClassSelector(ClassSelector.MICROEJ_BUTTON);
		grid.add(button);

		return grid;
	}

	protected abstract Toggle newCheckBox(boolean checked);

	protected abstract Toggle newSwitch(boolean checked);

	protected abstract Toggle newRadioButton(boolean checked);

	protected abstract AbstractSlider newSlider(int min, int max, int initial);

	private static void newItem(StyledWidget widget, String text, GridComposite grid) {
		BorderComposite item = new BorderComposite();
		item.setHorizontal(true);
		item.add(widget, MWT.LEFT);
		Label label = new Label(text);
		label.addClassSelector(ClassSelector.MEDIUM_LABEL);
		item.add(label, MWT.CENTER);
		grid.add(item);
	}
}
