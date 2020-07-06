/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test.framework;

import com.is2t.testsuite.support.CheckHelper;

import ej.microui.display.Display;

/**
 *
 */
public abstract class Test {

	public Test() {
		super();
	}

	public void start() {
		CheckHelper.startCheck(getClass());
		run(Display.getDisplay());
		CheckHelper.endCheck(getClass());
	}

	public abstract void run(Display display);

}