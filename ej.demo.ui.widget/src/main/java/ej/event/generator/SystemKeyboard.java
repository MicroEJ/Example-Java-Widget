/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package ej.event.generator;

import ej.microui.event.generator.Keyboard;

/**
 * Represents a simple keyboard registered in the system pool
 */
public class SystemKeyboard extends Keyboard {

	/**
	 * Constructor
	 */
	public SystemKeyboard() {
		super(1);
		addToSystemPool();
	}
}
