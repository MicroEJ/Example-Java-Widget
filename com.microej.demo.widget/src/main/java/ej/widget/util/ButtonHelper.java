/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.util;

import ej.basictool.ArrayTools;
import ej.microui.event.Event;
import ej.microui.event.EventHandler;
import ej.microui.event.generator.Command;
import ej.microui.event.generator.Pointer;
import ej.mwt.event.DesktopEventGenerator;
import ej.mwt.event.PointerEventDispatcher;
import ej.widget.listener.GenericListener;
import ej.widget.listener.OnClickListener;

/**
 * A button helper manages the behavior of a button: it handles pointer events, and notifies listeners when the button
 * is clicked.
 */
public class ButtonHelper implements EventHandler {

	private static final OnClickListener[] EMPTY_LISTENERS = new OnClickListener[0];

	private final GenericListener helperListener;

	private boolean pressed;
	private OnClickListener[] onClickListeners;

	/**
	 * Creates a button helper.
	 *
	 * @param helperListener
	 *            the listener to notify when the state changed.
	 */
	public ButtonHelper(GenericListener helperListener) {
		this.helperListener = helperListener;
		this.onClickListeners = EMPTY_LISTENERS;
	}

	/**
	 * Performs the actions associated to a click.
	 */
	public void performClick() {
		notifyOnClickListeners();
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
		assert listener != null;
		this.onClickListeners = ArrayTools.add(this.onClickListeners, listener);
	}

	/**
	 * Removes a listener on the click events of the button.
	 *
	 * @param listener
	 *            the listener to remove.
	 */
	public void removeOnClickListener(OnClickListener listener) {
		this.onClickListeners = ArrayTools.remove(this.onClickListeners, listener);
	}

	/**
	 * Notifies the listeners that a click event happened.
	 */
	protected void notifyOnClickListeners() {
		for (OnClickListener onClickListener : this.onClickListeners) {
			onClickListener.onClick();
		}
	}

	/**
	 * Gets whether the button is pressed or not.
	 *
	 * @return <code>true</code> if the button is pressed, <code>false</code> otherwise.
	 */
	public boolean isPressed() {
		return this.pressed;
	}

	/**
	 * Sets the pressed state.
	 *
	 * @param pressed
	 *            the pressed state to set.
	 * @see #isPressed()
	 */
	private void setPressed(boolean pressed) {
		this.pressed = pressed;
		this.helperListener.update();
	}

	@Override
	public boolean handleEvent(int event) {
		// /!\ Very similar code in ToggleHelper.
		switch (Event.getType(event)) {
		case Command.EVENT_TYPE:
			if (Event.getData(event) == Command.SELECT) {
				performClick();
				return true;
			}
			break;
		case Pointer.EVENT_TYPE:
			int action = Pointer.getAction(event);
			switch (action) {
			case Pointer.PRESSED:
				setPressed(true);
				break;
			case Pointer.RELEASED:
				if (this.pressed) {
					// Update button state & style before external handling.
					setPressed(false);

					performClick();
					return true;
				}
				break;
			// Don't exit when the button is dragged, because the user can drag inside the button.
			// case Pointer.DRAGGED:
			}
			break;
		case DesktopEventGenerator.EVENT_TYPE:
			action = DesktopEventGenerator.getAction(event);
			if (action == PointerEventDispatcher.EXITED) {
				setPressed(false);
			}
			break;
		}
		return false;
	}

}
