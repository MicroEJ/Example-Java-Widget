/*
 * Copyright 2010-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.util;

import ej.microui.event.Event;
import ej.microui.event.EventGenerator;

/**
 * A Keyboard event generator generates events containing a character.
 */
public class Keyboard extends EventGenerator {

	/**
	 * ID of the keyboard event generator.
	 */
	public static final int EVENTGENERATOR_ID = 0x10;

	/**
	 * @return {@link #EVENTGENERATOR_ID}
	 */
	@Override
	public int getEventType() {
		return EVENTGENERATOR_ID;
	}

	/**
	 * Send a keyboard event to the application.
	 *
	 * @param c
	 *            the character to send.
	 */
	public void send(char c) {
		done(c);
	}

	private void done(char c) {
		int microUIevent = Event.buildEvent(getEventType(), this, c);
		sendEvent(microUIevent);
	}

	/**
	 * Gets the character held by the keyboard event.
	 *
	 * @param event
	 *            the keyboard event.
	 * @return the character held by the keyboard event.
	 */
	public char getChar(int event) {
		return (char) Event.getData(event);
	}

}
