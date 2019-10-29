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
 * Represents a symbol layout
 */
public class SymbolLayout implements Layout {

	@Override
	public String getFirstRow() {
		return "[]{}#%^*+="; //$NON-NLS-1$
	}

	@Override
	public String getSecondRow() {
		return "_\\|~<>€£¥\u25cf"; //$NON-NLS-1$
	}

	@Override
	public String getThirdRow() {
		return ".,?!\'§¤"; //$NON-NLS-1$
	}
}
