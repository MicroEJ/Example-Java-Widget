/*
 * Copyright 2014-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test.framework;

import ej.mwt.util.Size;

public class MyFreeWidget extends MyWidget {

	private int width;
	private int height;

	public MyFreeWidget() {
	}

	public MyFreeWidget(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		super.computeContentOptimalSize(size);
		size.setSize(this.width, this.height);
	}

}