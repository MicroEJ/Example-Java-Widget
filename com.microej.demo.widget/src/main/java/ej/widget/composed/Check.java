/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.composed;

import ej.widget.basic.drawing.CheckBox;
import ej.widget.toggle.ToggleModel;

/**
 * A facility to use a check that displays a text.
 * <p>
 * It is simply a toggle container that contains a check box and a label, based on a toggle model.
 *
 * @see ej.widget.basic.Label
 * @see CheckBox
 * @see ToggleModel
 */
public class Check extends Toggle {

	/**
	 * Creates a check with the text to display. The text cannot be <code>null</code>.
	 *
	 * @param text
	 *            the text to display.
	 * @throws NullPointerException
	 *             if the text is <code>null</code>.
	 */
	public Check(String text) {
		super(new CheckBox(), text);
	}

	/**
	 * Creates a check with the text to display and its group. The text cannot be <code>null</code>.
	 *
	 * @param text
	 *            the text to display.
	 * @param group
	 *            the name of the toggle group.
	 * @throws NullPointerException
	 *             if the text is <code>null</code>.
	 */
	public Check(String text, String group) {
		super(new ToggleModel(), new CheckBox(), text, group);
	}

}
