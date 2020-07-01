/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test.framework;

/**
 *
 */
public interface InstrumentedItem {

	int getAbsoluteX();

	int getAbsoluteY();

	int getX();

	int getY();

	int getWidth();

	int getHeight();

	boolean isPaint();

	int getTranslateX();

	int getTranslateY();

	int getClipX();

	int getClipY();

	int getClipWidth();

	int getClipHeight();

	boolean hasReceivedEvent();

	int getEvent();

	void reset();

	boolean isOnAttached();

	boolean isOnDetached();

	boolean isOnShown();

	boolean isOnHidden();

	boolean isLaidOut();

}
