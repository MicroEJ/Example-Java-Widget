/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package ej.widget.keyboard.azerty;

/** IPR start **/

import ej.widget.composed.Button;
import ej.widget.listener.OnClickListener;

public class SpecialKey extends Button {

	public SpecialKey(String text, OnClickListener onClickListener) {
		super(text);
		addOnClickListener(onClickListener);
	}

}

/** IPR end **/