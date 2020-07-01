/*
 * Copyright 2017-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.container.transition;

import ej.mwt.Widget;

/**
 * A no transition container switch directly the widgets without any animation.
 * <p>
 * Only useful to easily and dynamically replace another transition container.
 *
 * @since 2.3.0
 */
public class NoTransitionContainer extends TransitionContainer {

	/**
	 * Creates a no transition container.
	 */
	public NoTransitionContainer() {
	}

	@Override
	public void show(Widget widget, boolean forward) {
		removeAllChildren();
		addChild(widget);
		requestLayOut();
	}

	@Override
	protected void updateStep(int step) {
		// Nothing to do.
	}

}
