/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.widgets.widget;

import ej.microui.Command;
import ej.microui.Event;
import ej.microui.Listener;
import ej.microui.io.Pointer;
import ej.widgets.widgets.IButton;
import ej.widgets.widgets.Picto;
import ej.widgets.widgets.label.LeftIconLabel;

/**
 * Selectable label with an icon.
 */
public class LeftIconLabelButton extends LeftIconLabel implements IButton {

	/**
	 * Label listener.
	 */
	protected Listener selectionListener;
	private boolean pressed;

	/**
	 * Creates a LeftIconLabelButton with the given text, the given max line and the given icon.
	 * 
	 * @param text
	 *            the text of the label.
	 * @param maxLineCount
	 *            the number of maximum lines the label will display.
	 * @param icon
	 *            the icon of the label.
	 */
	public LeftIconLabelButton(String text, int maxLineCount, Picto icon) {
		super(text, maxLineCount, icon);
		setEnabled(true);
	}

	/**
	 * Creates a LeftIconLabelButton with the given text and the given icon. There is no limit on the number of line to
	 * display.
	 * 
	 * @param text
	 *            the text of the label.
	 * @param icon
	 *            the icon of the label.
	 */
	public LeftIconLabelButton(String text, Picto icon) {
		super(text, icon);
		setEnabled(true);
	}

	/**
	 * Notify the listener of the label.
	 */
	protected void notifyListener() {
		if (this.selectionListener != null) {
			this.selectionListener.performAction(0, this);
		}
	}

	@Override
	public Listener getListener() {
		return this.selectionListener;
	}

	@Override
	public void setListener(Listener listener) {
		this.selectionListener = listener;
	}

	@Override
	public void select() {
		if (!isEnabled()) {
			return;
		}
		notifyListener();
	}

	@Override
	public boolean handleEvent(int event) {
		int type = Event.getType(event);
		if (type == Event.COMMAND) {
			int data = Event.getData(event);
			if (data == Command.SELECT) {
				select();
				return true;
			}
		} else if (type == Event.POINTER) {
			int action = Pointer.getAction(event);
			switch (action) {
			case Pointer.PRESSED:
				this.pressed = true;
				break;
			case Pointer.RELEASED:
				if (this.pressed) {
					this.pressed = false;
					select();
				}
				break;
			case Pointer.DRAGGED:
				break;
			}
		}
		return false;
	}
}
