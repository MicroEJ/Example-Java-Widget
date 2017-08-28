/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.microej.demo.widget.page;

import ej.mwt.Widget;
import ej.widget.animation.AnimationListener;
import ej.widget.animation.AnimationListenerRegistry;
import ej.widget.container.Dock;
import ej.widget.keyboard.Keyboard;
import ej.widget.keyboard.Layout;

/**
 * This page illustrates a keyboard.
 */
public abstract class KeyboardPage extends AbstractDemoPage implements AnimationListener {

	private static final float KEYBOARD_RATIO = 0.45f;

	private Keyboard keyboard;
	protected Dock dock;

	/**
	 * Constructor
	 */
	public KeyboardPage() {
		// create keyboard
	}

	@Override
	protected final Widget createMainContent() {
		this.keyboard = new Keyboard();

		this.dock = new Dock();
		Widget editionContent = createEditionContent();
		this.dock.setCenter(editionContent);
		return this.dock;
	}

	protected abstract Widget createEditionContent();

	/**
	 * Sets the keyboard layouts to use
	 *
	 * @param keyboardLayouts
	 *            the four keyboard layouts to use
	 */
	public void setKeyboardLayouts(Layout[] keyboardLayouts) {
		this.keyboard.setLayouts(keyboardLayouts);
	}

	/**
	 * Gets the title of the page
	 */
	@Override
	protected String getTitle() {
		return "Keyboard"; //$NON-NLS-1$
	}

	@Override
	public void onStartAnimation() {
		hideKeyboard();
	}

	@Override
	public void onStopAnimation() {
	}

	/**
	 * Handles show notification
	 */
	@Override
	public void showNotify() {
		super.showNotify();
		AnimationListenerRegistry.register(this);
	}

	/**
	 * Handles hide notification
	 */
	@Override
	public void hideNotify() {
		AnimationListenerRegistry.unregister(this);
		super.hideNotify();
	}

	/**
	 * Gets the keyboard
	 *
	 * @return the keyboard
	 */
	protected Keyboard getKeyboard() {
		return this.keyboard;
	}

	/**
	 * Shows the keyboard
	 */
	protected void showKeyboard() {
		// show keyboard dialog
		if (this.keyboard.getParent() != this.dock) {
			this.dock.addBottom(this.keyboard);
			revalidate();
		}
	}

	/**
	 * Hides the keyboard
	 */
	protected void hideKeyboard() {
		this.dock.remove(this.keyboard);
		revalidate();
	}
}
