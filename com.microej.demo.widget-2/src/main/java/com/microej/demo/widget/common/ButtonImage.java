/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package com.microej.demo.widget.common;

import ej.annotation.Nullable;
import ej.microui.event.Event;
import ej.microui.event.generator.Pointer;
import ej.mwt.event.DesktopEventGenerator;
import ej.mwt.event.PointerEventDispatcher;
import ej.widget.basic.ImageWidget;
import ej.widget.listener.OnClickListener;
import ej.widget.util.States;

/**
 * A button is a widget that displays a text and reacts to click events.
 */
public class ButtonImage extends ImageWidget {

	private @Nullable OnClickListener onClickListener;
	private boolean pressed;

	/**
	 * Creates a button with an empty text.
	 */
	public ButtonImage() {
		this(""); //$NON-NLS-1$
	}

	/**
	 * Creates a button with the resource path of the image to display.
	 *
	 * @param imagePath
	 *            the resource path of the image to display.
	 */
	public ButtonImage(String imagePath) {
		super(imagePath);
		setEnabled(true);

		this.onClickListener = null;
		this.pressed = false;
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
	public boolean isInState(int state) {
		return (state == States.ACTIVE && this.pressed) || super.isInState(state);
	}

	@Override
	public boolean handleEvent(int event) {
		int type = Event.getType(event);
		if (type == Pointer.EVENT_TYPE) {
			int action = Pointer.getAction(event);
			if (action == Pointer.PRESSED) {
				setPressed(true);
			} else if (action == Pointer.RELEASED) {
				setPressed(false);
				handleClick();
				return true;
			}
		} else if (type == DesktopEventGenerator.EVENT_TYPE) {
			int action = DesktopEventGenerator.getAction(event);
			if (action == PointerEventDispatcher.EXITED) {
				setPressed(false);
			}
		}

		return super.handleEvent(event);
	}

	private void setPressed(boolean pressed) {
		this.pressed = pressed;
		updateStyle();
		requestRender();
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
