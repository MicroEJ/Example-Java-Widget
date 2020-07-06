/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.composed;

import ej.widget.basic.drawing.RadioBox;
import ej.widget.toggle.RadioModel;

/**
 * A facility to use a radio that displays a text.
 * <p>
 * It is simply a toggle container that contains a radio box and a label, based on a radio model.
 *
 * @see ej.widget.basic.Label
 * @see RadioModel
 * @see RadioBox
 */
public class Radio extends Toggle {

	/**
	 * Creates a radio with the text to display. The text cannot be <code>null</code>.
	 *
	 * @param text
	 *            the text to display.
	 * @throws NullPointerException
	 *             if the text is <code>null</code>.
	 */
	public Radio(String text) {
		super(new RadioModel(), new RadioBox(), text);
	}

	/**
	 * Creates a radio with the text to display and its group. The text cannot be <code>null</code>.
	 *
	 * @param text
	 *            the text to display.
	 * @param group
	 *            the name of the toggle group.
	 * @throws NullPointerException
	 *             if the text is <code>null</code>.
	 */
	public Radio(String text, String group) {
		super(new RadioModel(), new RadioBox(), text, group);
	}

}
