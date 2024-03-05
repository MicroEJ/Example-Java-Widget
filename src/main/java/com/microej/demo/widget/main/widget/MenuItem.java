/*
 * Copyright 2015-2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.main.widget;

import com.microej.demo.widget.common.RenderableLabel;

import ej.annotation.Nullable;
import ej.microui.event.Event;
import ej.microui.event.generator.Buttons;
import ej.microui.event.generator.Pointer;
import ej.widget.basic.OnClickListener;

/**
 * A menu item is a widget that displays a text and reacts to click events.
 */
public class MenuItem extends RenderableLabel {

	private @Nullable OnClickListener onClickListener;

	/**
	 * Creates a button with the given text to display.
	 *
	 * @param text
	 *            the text to display.
	 */
	public MenuItem(String text) {
		super(text, true);

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
			int action = Buttons.getAction(event);
			if (action == Buttons.RELEASED) {
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
