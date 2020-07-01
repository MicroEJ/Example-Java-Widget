/*
 * Copyright 2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test.framework;

import ej.mwt.Container;
import ej.mwt.Widget;
import ej.mwt.util.Size;

public class ContainerValidateHelper extends Container {

	public ContainerValidateHelper(Widget child) {
		addChild(child);
	}

	@Override
	protected void layOutChildren(int contentWidth, int contentHeight) {
	}

	@Override
	public void computeChildOptimalSize(Widget widget, int widthHint, int heightHint) {
		super.computeChildOptimalSize(widget, widthHint, heightHint);
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		Widget child = getChild(0);
		computeChildOptimalSize(child, size.getWidth(), size.getHeight());
		size.setSize(child.getWidth(), child.getHeight());
	}

}
