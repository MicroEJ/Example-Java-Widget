/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package com.microej.demo.widget.main.widget;

import ej.annotation.Nullable;
import ej.microui.event.Event;
import ej.microui.event.generator.Pointer;
import ej.widget.basic.Label;
import ej.widget.listener.OnClickListener;

/**
 * A menu item is a widget that displays a text and reacts to click events.
 */
public class MenuItem extends Label {

	private @Nullable OnClickListener onClickListener;

	/**
	 * Creates a button with the given text to display.
	 *
	 * @param text
	 *            the text to display.
	 */
	public MenuItem(String text) {
		super(text);
		setEnabled(true);

		this.onClickListener = null;
	}

	/**
	 * Sets the listener on the click events of this button.
	 *
	 * @param listener
	 *            the listener to set.
	 */
	public void setOnClickListener(@Nullable OnClickListener listener) {
		this.onClickListener = listener;
	}

	@Override
	public boolean handleEvent(int event) {
		int type = Event.getType(event);
		if (type == Pointer.EVENT_TYPE) {
			int action = Pointer.getAction(event);
			if (action == Pointer.RELEASED) {
				handleClick();
				return true;
			}
		}

		return super.handleEvent(event);
	}

	/**
	 * Handles a click event.
	 */
	public void handleClick() {
		OnClickListener listener = this.onClickListener;
		if (listener != null) {
			listener.onClick();
		}
	}
}
