/*
 * Java
 *
 * Copyright  2017-2019 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
 */
package com.microej.demo.widget.keyboard;

import ej.widget.keyboard.Layout;

/**
 * Represents a lower case layout
 */
public class LowerCaseLayout implements Layout {

	@Override
	public String getFirstRow() {
		return "qwertyuiop"; //$NON-NLS-1$
	}

	@Override
	public String getSecondRow() {
		return "asdfghjkl\00"; //$NON-NLS-1$
	}

	@Override
	public String getThirdRow() {
		return "zxcvbnm"; //$NON-NLS-1$
	}
}
