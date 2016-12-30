/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package ej.demo.ui.widget.page;

import ej.demo.ui.widget.WidgetsDemo;
import ej.demo.ui.widget.style.ClassSelectors;
import ej.microui.display.Display;
import ej.mwt.Widget;
import ej.widget.StyledPanel;
import ej.widget.container.List;
import ej.widget.container.Scroll;
import ej.widget.keyboard.azerty.KeyboardText;

/**
 * This page illustrates a keyboard.
 */
public abstract class KeyboardPage extends AbstractDemoPage {

	private static final String FIRST_NAME = "First name"; //$NON-NLS-1$
	private static final String LAST_NAME = "Last name"; //$NON-NLS-1$
	private static final String EMPTY_STRING = ""; //$NON-NLS-1$

	private KeyboardText firstName;
	private StyledPanel keyboardDialog;

	@Override
	protected String getTitle() {
		return "Keyboard"; //$NON-NLS-1$
	}

	@Override
	protected Widget createMainContent() {
		this.firstName = new KeyboardText(EMPTY_STRING, FIRST_NAME);
		final KeyboardText lastName = new KeyboardText(EMPTY_STRING, LAST_NAME);

		final List list = new List(false);
		list.add(this.firstName);
		list.add(lastName);
		list.addClassSelector(ClassSelectors.FORM);

		final Scroll scroll = new Scroll(false, false);
		scroll.setWidget(list);
		return scroll;
	}

	private int getKeyboardHeight() {
		return (int) (getKeyboardRatio() * WidgetsDemo.HEIGHT);
	}

	@Override
	public void showNotify() {
		this.keyboardDialog = new StyledPanel() {
			@Override
			public void validate(int widthHint, int heightHint) {
				int keyboardHeight = getKeyboardHeight();
				super.validate(WidgetsDemo.WIDTH, keyboardHeight);
				setBounds(0, WidgetsDemo.HEIGHT - keyboardHeight, WidgetsDemo.WIDTH, keyboardHeight);
			}
		};

		Widget keyboard = createKeyboard();
		this.keyboardDialog.setWidget(keyboard);
		this.keyboardDialog.show(WidgetsDemo.getDesktop());

		WidgetsDemo.KEYBOARD_HEIGHT = getKeyboardHeight();
		WidgetsDemo.panel.invalidate();
		WidgetsDemo.panel.validate();

		Display.getDefaultDisplay().callSerially(new Runnable() {
			@Override
			public void run() {
				WidgetsDemo.panel.setFocus(KeyboardPage.this.firstName);
			}
		});
	}

	@Override
	public void hideNotify() {
		this.keyboardDialog.hide();

		WidgetsDemo.KEYBOARD_HEIGHT = 0;
		WidgetsDemo.panel.invalidate();
		WidgetsDemo.panel.validate();
	}

	/**
	 * Creates the keyboard
	 *
	 * @return the keyboard widget
	 */
	protected abstract Widget createKeyboard();

	/**
	 * Gets the keyboard height ratio
	 *
	 * @return the keyboard height ratio
	 */
	protected abstract float getKeyboardRatio();

}
