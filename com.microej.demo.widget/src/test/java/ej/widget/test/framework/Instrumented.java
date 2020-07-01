/*
 * Copyright 2017-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test.framework;

import ej.annotation.Nullable;
import ej.microui.event.EventHandler;
import ej.mwt.style.Style;

/**
 *
 */
public interface Instrumented {

	/**
	 * Gets the validateStyle.
	 *
	 * @return the validateStyle.
	 */
	@Nullable
	Style getValidateStyle();

	/**
	 * Gets the renderStyle.
	 *
	 * @return the renderStyle.
	 */
	@Nullable
	Style getRenderStyle();

	/**
	 * Gets the styleUpdated.
	 *
	 * @return the styleUpdated.
	 */
	boolean isStyleUpdated();

	void reset();

	void setEventHandler(EventHandler eventHandler);

}
