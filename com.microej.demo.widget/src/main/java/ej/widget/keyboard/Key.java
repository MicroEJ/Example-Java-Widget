/*
 * Copyright 2016-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.keyboard;

import ej.bon.Timer;
import ej.bon.TimerTask;
import ej.microui.event.Event;
import ej.microui.event.generator.Pointer;
import ej.service.ServiceFactory;
import ej.widget.basic.Label;
import ej.widget.composed.Wrapper;
import ej.widget.listener.OnClickListener;
import ej.widget.util.Keyboard;
import ej.widget.util.States;

/**
 * Represents one of the keys of a keyboard
 */
public class Key extends Wrapper {

	private static final int REPEAT_DELAY = 600;
	private static final int REPEAT_PERIOD = 60;

	private final Keyboard keyboard;
	private final Label label;
	private OnClickListener onClickListener;
	private boolean repeatable;
	private TimerTask repeatTask;
	private boolean pressed;

	/**
	 * Constructor
	 *
	 * @param keyboard
	 *            the keyboard
	 */
	public Key(Keyboard keyboard) {
		this.keyboard = keyboard;
		this.label = new Label();
		this.onClickListener = null;
		this.repeatable = false;
		this.repeatTask = null;
		setChild(this.label);
		setEnabled(false);
	}

	/**
	 * Sets the key as a standard character input key
	 *
	 * @param character
	 *            the character printed by the key
	 */
	public void setStandard(final char character) {
		setEnabled(true);
		this.label.setText(String.valueOf(character));
		this.onClickListener = new OnClickListener() {
			@Override
			public void onClick() {
				Key.this.keyboard.send(character);
			}
		};
		this.repeatable = true;
		removeAllClassSelectors();
	}

	/**
	 * Sets the key as a standard character input key
	 *
	 * @param character
	 *            the character printed by the key
	 * @param classSelector
	 *            the class selector to set
	 */
	public void setStandard(final char character, int classSelector) {
		setStandard(character);
		addClassSelector(classSelector);
	}

	/**
	 * Sets the key as a special key
	 *
	 * @param text
	 *            the text to draw on the key
	 * @param listener
	 *            the action to execute when the key is pressed
	 */
	public void setSpecial(String text, OnClickListener listener) {
		setEnabled(true);
		this.label.setText(text);
		this.onClickListener = listener;
		this.repeatable = false;
		removeAllClassSelectors();
	}

	/**
	 * Sets the key as a special key
	 *
	 * @param text
	 *            the text to draw on the key
	 * @param listener
	 *            the action to execute when the key is pressed
	 * @param classSelector
	 *            the class selector to set
	 */
	public void setSpecial(String text, OnClickListener listener, int classSelector) {
		setSpecial(text, listener);
		addClassSelector(classSelector);
	}

	/**
	 * Sets the key as a blank key
	 */
	public void setBlank() {
		setEnabled(false);
		this.label.setText(""); //$NON-NLS-1$
		this.onClickListener = null;
		this.repeatable = false;
		removeAllClassSelectors();
	}

	@Override
	public boolean isInState(int state) {
		return (this.pressed && state == States.ACTIVE) || super.isInState(state);
	}

	@Override
	public boolean handleEvent(int event) {
		switch (Event.getType(event)) {
		case Event.POINTER:
			int action = Pointer.getAction(event);
			switch (action) {
			case Pointer.PRESSED:
				setPressed(true);
				this.onClickListener.onClick();
				startRepeatTask();
				break;
			case Pointer.RELEASED:
				if (this.pressed) {
					// Update button state & style before external handling.
					setPressed(false);
					stopRepeatTask();
					return true;
				}
				// Don't exit when the button is dragged, because the user can drag inside the button.
				// case Pointer.DRAGGED:
			case Pointer.EXITED:
				if (this.pressed) {
					setPressed(false);
					stopRepeatTask();
				}
				break;
			}
			return false;
		}
		return super.handleEvent(event);
	}

	private void setPressed(boolean pressed) {
		this.pressed = pressed;
		updateStyleRecursive();
		requestRender();
	}

	private void startRepeatTask() {
		if (this.repeatable && this.onClickListener != null) {
			this.repeatTask = new TimerTask() {
				@Override
				public void run() {
					Key.this.onClickListener.onClick();
				}
			};
			Timer timer = ServiceFactory.getService(Timer.class, Timer.class);
			timer.schedule(this.repeatTask, REPEAT_DELAY, REPEAT_PERIOD);
		}
	}

	private void stopRepeatTask() {
		if (this.repeatTask != null) {
			this.repeatTask.cancel();
			this.repeatTask = null;
		}
	}
}
