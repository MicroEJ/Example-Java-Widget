/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.basic;

import ej.widget.listener.OnClickListener;
import ej.widget.util.ButtonHelper;
import ej.widget.util.GenericListener;
import ej.widget.util.States;

/**
 * A button image is a widget that displays an image and reacts to click events.
 *
 * @see ImagePath
 */
public class ButtonImagePath extends ImagePath implements GenericListener {

	private final ButtonHelper buttonHelper;

	/**
	 * Creates a button image with the path to the source to display.
	 *
	 * @param sourcePath
	 *            the path to the source to display.
	 */
	public ButtonImagePath(String sourcePath) {
		super(sourcePath);
		setEnabled(true);
		this.buttonHelper = new ButtonHelper(this);
	}

	@Override
	public void update() {
		updateStyle();
		requestRender();
	}

	/**
	 * Performs the actions associated to a click.
	 */
	public void performClick() {
		this.buttonHelper.performClick();
	}

	/**
	 * Adds a listener on the click events of the button.
	 *
	 * @param listener
	 *            the listener to add.
	 * @throws NullPointerException
	 *             if the given listener is <code>null</code>.
	 */
	public void addOnClickListener(OnClickListener listener) {
		this.buttonHelper.addOnClickListener(listener);
	}

	/**
	 * Removes a listener on the click events of the button.
	 *
	 * @param listener
	 *            the listener to remove.
	 */
	public void removeOnClickListener(OnClickListener listener) {
		this.buttonHelper.removeOnClickListener(listener);
	}

	@Override
	public boolean isInState(int state) {
		return (state == States.ACTIVE && this.buttonHelper.isPressed()) || super.isInState(state);
	}

	@Override
	public boolean handleEvent(int event) {
		if (this.buttonHelper.handleEvent(event)) {
			return true;
		}
		return super.handleEvent(event);
	}

}
