/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.composed;

import ej.widget.basic.drawing.SwitchBox;
import ej.widget.toggle.ToggleModel;

/**
 * A facility to use a switch that displays a text.
 * <p>
 * It is simply a toggle container that contains a switch box and a label, based on a toggle model.
 *
 * @see ej.widget.basic.Label
 * @see SwitchBox
 * @see ToggleModel
 */
public class Switch extends Toggle {

	/**
	 * Creates a switch with the text to display. The text cannot be <code>null</code>.
	 *
	 * @param text
	 *            the text to display.
	 * @throws NullPointerException
	 *             if the text is <code>null</code>.
	 */
	public Switch(String text) {
		super(new SwitchBox(), text);
	}

	/**
	 * Creates a switch with the text to display and its group. The text cannot be <code>null</code>.
	 *
	 * @param text
	 *            the text to display.
	 * @param group
	 *            the name of the toggle group.
	 * @throws NullPointerException
	 *             if the text is <code>null</code>.
	 */
	public Switch(String text, String group) {
		super(new ToggleModel(), new SwitchBox(), text, group);
	}

}
