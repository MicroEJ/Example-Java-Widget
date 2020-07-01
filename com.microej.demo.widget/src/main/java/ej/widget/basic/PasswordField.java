/*
 * Copyright 2016-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.basic;

import java.util.Arrays;

import ej.annotation.Nullable;
import ej.bon.Timer;
import ej.bon.TimerTask;
import ej.service.ServiceFactory;

/**
 * A password field is a text field with hidden characters.
 * <p>
 * The {@link #getText()} method is the text displayed. It returns a string that replaces each character of the password
 * by the character '\u25cf'. The real value of the password can be retrieved using {@link #getPassword()}.
 */
public class PasswordField extends TextField {

	private static final char HIDING_CHARACTER = '\u25cf';
	private static final long HIDE_NEW_CHARACTER_DELAY = 500;

	private boolean hasNewCharacter;
	private boolean reveal;
	private char newCharacter;
	@Nullable
	private TimerTask hideNewCharacterTask;

	/**
	 * Creates a password field with an empty password.
	 */
	public PasswordField() {
		super();
	}

	/**
	 * Creates a password field with an initial password.
	 *
	 * @param password
	 *            the password to set.
	 * @throws NullPointerException
	 *             if the given text is <code>null</code>.
	 */
	public PasswordField(String password) {
		super(password);
	}

	/**
	 * Creates a password field with an initial password and a place holder.
	 * <p>
	 * The place holder is displayed when the password is empty.
	 *
	 * @param password
	 *            the password to set.
	 * @param placeHolder
	 *            the place holder to set.
	 * @throws NullPointerException
	 *             if one or both the given text and place holder are <code>null</code>.
	 */
	public PasswordField(String password, String placeHolder) {
		super(password, placeHolder);
	}

	@Override
	public String getText() {
		if (this.reveal) {
			return super.getText();
		} else {
			char[] characters = new char[getTextLength()];
			Arrays.fill(characters, HIDING_CHARACTER);
			if (this.hasNewCharacter && characters.length > 0) {
				characters[characters.length - 1] = this.newCharacter;
			}
			return new String(characters);
		}
	}

	/**
	 * Gets whether or not the password is revealed.
	 *
	 * @return <code>true</code> if the password is revealed, <code>false</code> otherwise.
	 */
	public boolean isRevealed() {
		return this.reveal;
	}

	/**
	 * Reveals or hides the password.
	 *
	 * @param reveal
	 *            <code>true</code> to reveal the password and <code>false</code> to hide it.
	 */
	public void reveal(boolean reveal) {
		this.reveal = reveal;
		requestRender();
	}

	/**
	 * Gets the text of the password without hiding its characters.
	 *
	 * @return the text of the password.
	 */
	public String getPassword() {
		return super.getText();
	}

	@Override
	public void insert(char c) {
		this.hasNewCharacter = true;
		this.newCharacter = c;
		hideNewCharacterAfterDelay();
		super.insert(c);
	}

	private void hideNewCharacterAfterDelay() {
		cancelHideTask();

		Timer timer = ServiceFactory.getService(Timer.class, Timer.class);
		this.hideNewCharacterTask = new TimerTask() {
			@Override
			public void run() {
				hideLastCharacter();
			}
		};
		timer.schedule(this.hideNewCharacterTask, HIDE_NEW_CHARACTER_DELAY);
	}

	private void hideLastCharacter() {
		this.hasNewCharacter = false;
		requestRender();
	}

	@Override
	protected void onHidden() {
		super.onHidden();
		cancelHideTask();
	}

	private void cancelHideTask() {
		TimerTask hideNewCharacterTask = this.hideNewCharacterTask;
		if (hideNewCharacterTask != null) {
			hideNewCharacterTask.cancel();
		}
	}
}
