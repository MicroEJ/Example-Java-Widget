/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package ej.widget.keyboard;

import ej.microui.event.generator.Keyboard;
import ej.widget.composed.Button;
import ej.widget.listener.OnClickListener;

/**
 * Represents one of the keys of a keyboard
 */
public class Key extends Button {

	final Keyboard keyboard;
	OnClickListener currentClickListener;

	/**
	 * Constructor
	 *
	 * @param keyboard
	 *            the keyboard
	 */
	public Key(Keyboard keyboard) {
		this.keyboard = keyboard;
		this.currentClickListener = null;
	}

	/**
	 * Adds a listener on the click events of the button
	 */
	@Override
	public void addOnClickListener(OnClickListener listener) {
		if (this.currentClickListener != null) {
			removeOnClickListener(this.currentClickListener);
		}

		super.addOnClickListener(listener);
		this.currentClickListener = listener;
	}

	/**
	 * Removes a listener on the click events of the button
	 */
	@Override
	public void removeOnClickListener(OnClickListener listener) {
		if (this.currentClickListener == listener) {
			this.currentClickListener = null;
		}
		super.removeOnClickListener(listener);
	}

	/**
	 * Sets the key as a standard character input key
	 *
	 * @param character
	 *            the character printed by the key
	 */
	public void setStandard(final char character) {
		setText(String.valueOf(character));
		addOnClickListener(new OnClickListener() {
			@Override
			public void onClick() {
				Key.this.keyboard.send(Keyboard.TEXT_INPUT, character);
			}
		});
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
	public void setStandard(final char character, String classSelector) {
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
		setText(text);
		addOnClickListener(listener);
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
	public void setSpecial(String text, OnClickListener listener, String classSelector) {
		setSpecial(text, listener);
		addClassSelector(classSelector);
	}

	/**
	 * Sets the key as a blank key
	 */
	public void setBlank() {
		setText(""); //$NON-NLS-1$
		if (this.currentClickListener != null) {
			removeOnClickListener(this.currentClickListener);
		}
		removeAllClassSelectors();
	}
}
