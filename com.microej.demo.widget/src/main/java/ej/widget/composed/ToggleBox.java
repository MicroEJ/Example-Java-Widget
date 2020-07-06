/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.composed;

import ej.mwt.Widget;
import ej.widget.basic.Box;
import ej.widget.toggle.ToggleModel;

/**
 * A facility to use a toggle with a box.
 * <p>
 * It is simply a toggle wrapper that contains a box, based on a toggle model.
 *
 * @see Box
 * @see ToggleModel
 * @since 2.3.0
 */
public class ToggleBox extends ToggleWrapper {

	private final Box box;

	/**
	 * Creates a toggle box.
	 * <p>
	 * The box state is updated along with the state of the toggle.
	 *
	 * @param box
	 *            the widget representing the state of the toggle.
	 * @throws NullPointerException
	 *             if a parameter is <code>null</code>.
	 * @see ej.widget.util.States#CHECKED
	 */
	public ToggleBox(Box box) {
		this(new ToggleModel(), box);
	}

	/**
	 * Creates a toggle box.
	 * <p>
	 * The box state is updated along with the state of the toggle.
	 *
	 * @param toggleModel
	 *            the toggle model.
	 * @param box
	 *            the widget representing the state of the toggle.
	 * @throws NullPointerException
	 *             if a parameter is <code>null</code>.
	 * @see ej.widget.util.States#CHECKED
	 */
	public ToggleBox(ToggleModel toggleModel, Box box) {
		super(toggleModel);
		this.box = box;
		addChild(box);
	}

	/**
	 * Creates a toggle box.
	 * <p>
	 * The box state is updated along with the state of the toggle.
	 *
	 * @param toggleModel
	 *            the toggle model.
	 * @param box
	 *            the widget representing the state of the toggle.
	 * @param group
	 *            the name of the toggle group.
	 * @throws NullPointerException
	 *             if a parameter is <code>null</code>.
	 * @see ej.widget.util.States#CHECKED
	 */
	public ToggleBox(ToggleModel toggleModel, Box box, String group) {
		super(toggleModel, group);
		this.box = box;
		addChild(box);
	}

	@Override
	public void setChild(Widget widget) {
		throw new IllegalArgumentException();
	}

	@Override
	public void update() {
		// Do not call super method to avoid rendering the box twice.
		this.box.updateState(isChecked());
		// Always request to render the widget even if the style has not changed because the checked state modifies the
		// rendering of the box (see Box.setChecked()).
		updateStyle();
		requestRender();
	}

	@Override
	protected void setPressed(boolean pressed) {
		super.setPressed(pressed);
		this.box.setPressed(pressed);
	}

}
