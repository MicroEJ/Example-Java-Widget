/*
 * Copyright 2015-2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.common;

import ej.annotation.Nullable;
import ej.widget.basic.ImageWidget;
import ej.widget.basic.OnClickListener;
import ej.widget.event.ClickEventHandler;
import ej.widget.event.Clickable;

/**
 * An image button is a widget that displays an image and reacts to click events.
 */
public class TitleButton extends ImageWidget implements Clickable {

	/**
	 * Active state.
	 */
	public static final int ACTIVE = 1;

	private final ClickEventHandler eventHandler;
	private boolean pressed;

	/**
	 * Creates an image button with the resource path of the image to display.
	 *
	 * @param imagePath
	 *            the resource path of the image to display.
	 */
	public TitleButton(String imagePath) {
		super(imagePath, true);

		this.eventHandler = new ClickEventHandler(this, this);
		this.pressed = false;
	}

	/**
	 * Sets the listener on the click events of this button.
	 *
	 * @param listener
	 *            the listener to set.
	 */
	public void setOnClickListener(@Nullable OnClickListener listener) {
		this.eventHandler.setOnClickListener(listener);
	}

	@Override
	public boolean isInState(int state) {
		return (state == ACTIVE && this.pressed) || super.isInState(state);
	}

	@Override
	public boolean handleEvent(int event) {
		return this.eventHandler.handleEvent(event);
	}

	@Override
	public void setPressed(boolean pressed) {
		this.pressed = pressed;
	}

}
