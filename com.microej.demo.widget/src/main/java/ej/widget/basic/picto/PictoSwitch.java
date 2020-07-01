/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.basic.picto;

/**
 * A switch using some pictos to render.
 */
public class PictoSwitch extends PictoToggle {

	@Override
	protected char getCheckedPicto() {
		return Pictos.SWITCH_CHECKED;
	}

	@Override
	protected char getUncheckedPicto() {
		return Pictos.SWITCH_UNCHECKED;
	}
}
