/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.basic.picto;

/**
 * A check box using some pictos to render.
 */
public class PictoCheck extends PictoToggle {

	@Override
	protected char getCheckedPicto() {
		return Pictos.CHECKBOX_CHECKED;
	}

	@Override
	protected char getUncheckedPicto() {
		return Pictos.CHECKBOX_UNCHECKED;
	}

}
