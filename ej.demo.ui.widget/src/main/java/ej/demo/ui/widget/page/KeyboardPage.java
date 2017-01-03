/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package ej.demo.ui.widget.page;

import ej.demo.ui.widget.WidgetsDemo;
import ej.widget.StyledPanel;
import ej.widget.keyboard.Keyboard;
import ej.widget.navigation.TransitionListener;
import ej.widget.navigation.TransitionManager;
import ej.widget.navigation.page.Page;

/**
 * This page illustrates a keyboard.
 */
public abstract class KeyboardPage extends AbstractDemoPage implements TransitionListener {

	private static final float KEYBOARD_RATIO = 0.45f;

	private final Keyboard keyboard;
	private final StyledPanel keyboardDialog;

	/**
	 * Constructor
	 */
	public KeyboardPage() {
		// create keyboard dialog
		final int keyboardHeight = (int) (WidgetsDemo.HEIGHT * KEYBOARD_RATIO);
		this.keyboardDialog = new StyledPanel() {
			@Override
			public void validate(int widthHint, int heightHint) {
				super.validate(WidgetsDemo.WIDTH, keyboardHeight);
			}
		};
		this.keyboardDialog.setBounds(0, WidgetsDemo.HEIGHT - keyboardHeight, WidgetsDemo.WIDTH, keyboardHeight);

		// create keyboard
		this.keyboard = new Keyboard();
		this.keyboardDialog.setWidget(this.keyboard);
	}

	/**
	 * Gets the title of the page
	 */
	@Override
	protected String getTitle() {
		return "Keyboard"; //$NON-NLS-1$
	}

	/**
	 * Handles page transition start
	 */
	@Override
	public void onTransitionStart(int transitionsSteps, int transitionsStop, Page from, Page to) {
		hideKeyboard();
	}

	/**
	 * Handles page transition step
	 */
	@Override
	public void onTransitionStep(int step) {
		// do nothing
	}

	/**
	 * Handles page transition stop
	 */
	@Override
	public void onTransitionStop() {
		showKeyboard();
	}

	/**
	 * Handles show notification
	 */
	@Override
	public void showNotify() {
		super.showNotify();
		TransitionManager.addGlobalTransitionListener(this);
	}

	/**
	 * Handles hide notification
	 */
	@Override
	public void hideNotify() {
		TransitionManager.removeGlobalTransitionListener(this);
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
		this.keyboardDialog.show(WidgetsDemo.getDesktop());

		// resize main panel
		final int keyboardHeight = (int) (WidgetsDemo.HEIGHT * KEYBOARD_RATIO);
		WidgetsDemo.getPanel().setBounds(0, 0, WidgetsDemo.WIDTH, WidgetsDemo.HEIGHT - keyboardHeight);
	}

	/**
	 * Hides the keyboard
	 */
	protected void hideKeyboard() {
		// resize main panel
		WidgetsDemo.getPanel().setBounds(0, 0, WidgetsDemo.WIDTH, WidgetsDemo.HEIGHT);

		// hide keyboard dialog
		this.keyboardDialog.hide();
	}
}
