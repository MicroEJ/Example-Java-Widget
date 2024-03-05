/*
 * Java
 *
 * Copyright 2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.flex;

import ej.annotation.Nullable;
import ej.widget.basic.Button;
import ej.widget.basic.OnClickListener;

/**
 * A flex option button is a widget that displays the option text for each of the possible flex values of a layout.
 */
public class FlexMenuItem extends Button implements OnClickListener {

	/**
	 * Selected state.
	 */
	public static final int SELECTED = 3;
	private final Group group;

	@Nullable
	private OnClickListener listener;

	/**
	 * Creates a flex option button with the given text to display and the group in which it belongs.
	 *
	 * @param text
	 *            the text to display.
	 * @param group
	 *            the button group.
	 * @param isDefault
	 *            checks if button is default.
	 */
	public FlexMenuItem(String text, Group group, boolean isDefault) {
		super(text);

		this.group = group;
		if (isDefault) {
			this.group.setChecked(this);
		}

		super.setOnClickListener(this);
	}

	@Override
	public void setOnClickListener(@Nullable OnClickListener listener) {
		this.listener = listener;
	}

	@Override
	public boolean isInState(int state) {
		return (state == SELECTED && this.group.isChecked(this)) || super.isInState(state);
	}

	@Override
	public void onClick() {
		this.group.setChecked(this);
		OnClickListener listener = this.listener;
		if (listener != null) {
			listener.onClick();
		}
	}

}
