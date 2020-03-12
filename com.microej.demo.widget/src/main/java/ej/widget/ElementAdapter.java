/*
 * Copyright 2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget;

import ej.microui.display.GraphicsContext;
import ej.mwt.Container;
import ej.mwt.Widget;
import ej.mwt.util.Size;

/**
 *
 */
public class ElementAdapter extends Widget {

	private Container parent;

	public ElementAdapter() {
	}

	public ElementAdapter(Container parent) {
		this.parent = parent;
	}

	@Override
	public Container getParent() {
		return this.parent;
	}

	@Override
	protected void computeContentOptimalSize(Size availableSize) {
	}

	@Override
	protected void renderContent(GraphicsContext g, Size size) {
	}

}
