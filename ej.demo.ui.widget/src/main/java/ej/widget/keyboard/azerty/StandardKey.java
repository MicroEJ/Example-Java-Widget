/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package ej.widget.keyboard.azerty;

import ej.microui.event.generator.Keyboard;
import ej.widget.composed.Button;
import ej.widget.listener.OnClickListener;

/** IPR start **/

public class StandardKey extends Button {

	public StandardKey(String text, final char character, final Keyboard keyboard) {
		super(text);
		addOnClickListener(new OnClickListener() {

			@Override
			public void onClick() {
				keyboard.send(Keyboard.TEXT_INPUT, character);
			}
		});
	}

	public StandardKey(final char character, final Keyboard keyboard) {
		this(String.valueOf(character), character, keyboard);
	}

}

/** IPR end **/
