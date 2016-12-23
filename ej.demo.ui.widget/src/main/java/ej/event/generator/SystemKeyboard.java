/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package ej.event.generator;

import ej.microui.event.generator.Keyboard;

/** IPR start **/

public class SystemKeyboard extends Keyboard {

	public SystemKeyboard() {
		super(1);
		addToSystemPool();
	}

}

/** IPR end **/
