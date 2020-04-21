/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package com.microej.demo.widget.page;

import com.microej.demo.widget.keyboard.LowerCaseLayout;
import com.microej.demo.widget.keyboard.NumericLayout;
import com.microej.demo.widget.keyboard.SymbolLayout;
import com.microej.demo.widget.keyboard.UpperCaseLayout;
import com.microej.demo.widget.style.ClassSelectors;

import ej.microui.event.Event;
import ej.mwt.Widget;
import ej.service.ServiceFactory;
import ej.widget.basic.Label;
import ej.widget.container.List;
import ej.widget.container.Scroll;
import ej.widget.container.util.LayoutOrientation;
import ej.widget.keyboard.Keyboard;
import ej.widget.keyboard.KeyboardText;
import ej.widget.keyboard.Layout;
import ej.widget.listener.OnClickListener;

/**
 * This page illustrates a keyboard.
 */
public class KeyboardPage extends AbstractDemoPage {

	private static final String EMPTY_STRING = ""; //$NON-NLS-1$
	private static final String FIRST_NAME = "First name"; //$NON-NLS-1$
	private static final String LAST_NAME = "Last name"; //$NON-NLS-1$
	private static final String RESULT_EMPTY = "Please fill the form"; //$NON-NLS-1$
	private static final String RESULT_PREFIX = "Your name is "; //$NON-NLS-1$
	private static final String SPECIAL_NEXT = "Next"; //$NON-NLS-1$
	private static final String SPECIAL_SUBMIT = "Submit"; //$NON-NLS-1$

	private static final int MAX_TEXT_LENGTH = 33;

	private final Keyboard keyboard;

	private KeyboardText firstName;
	private KeyboardText lastName;
	private Label resultLabel;

	/**
	 * Creates a keyboard page.
	 */
	public KeyboardPage() {
		super(false, "Keyboard"); //$NON-NLS-1$

		this.keyboard = new Keyboard();

		// set keyboard layouts
		Layout[] layouts = new Layout[] { new LowerCaseLayout(), new UpperCaseLayout(), new NumericLayout(),
				new SymbolLayout() };
		setKeyboardLayouts(layouts);

		Widget editionContent = createForm();
		setCenter(editionContent);
	}

	/**
	 * Creates the page form.
	 *
	 * @return a widget containing the form.
	 */
	/**
	 * Creates the widget representing the main content of the page
	 */
	private Widget createForm() {
		// first name
		this.firstName = new KeyboardText(EMPTY_STRING, FIRST_NAME) {
			@Override
			public boolean handleEvent(int event) {
				if (Event.getType(event) == Event.POINTER) {
					showKeyboard(KeyboardPage.this.firstName);
				}
				return super.handleEvent(event);
			}
		};
		this.firstName.setMaxTextLength(MAX_TEXT_LENGTH);

		// last name
		this.lastName = new KeyboardText(EMPTY_STRING, LAST_NAME) {
			@Override
			public boolean handleEvent(int event) {
				if (Event.getType(event) == Event.POINTER) {
					showKeyboard(KeyboardPage.this.lastName);
				}
				return super.handleEvent(event);
			}
		};
		this.lastName.setMaxTextLength(MAX_TEXT_LENGTH);

		// result label
		this.resultLabel = new Label(RESULT_EMPTY);
		this.resultLabel.addClassSelector(ClassSelectors.RESULT_LABEL);

		// list
		List list = new List(LayoutOrientation.VERTICAL);
		list.add(this.firstName);
		list.add(this.lastName);
		list.add(this.resultLabel);
		list.addClassSelector(ClassSelectors.FORM);

		// scroll
		final Scroll scroll = new Scroll(false, false);
		scroll.setWidget(list);
		return scroll;
	}

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
		if (this.keyboard.getParent() != this) {
			setLast(this.keyboard);
			requestLayOut();
		}
	}

	/**
	 * Hides the keyboard
	 */
	protected void hideKeyboard() {
		remove(this.keyboard);
		requestLayOut();
	}

	private void showKeyboard(KeyboardText keyboardText) {
		showKeyboard();
		keyboardText.setActive(true);
		if (keyboardText == this.firstName) {
			this.lastName.setActive(false);
			getKeyboard().setSpecialKey(SPECIAL_NEXT, new OnClickListener() {
				@Override
				public void onClick() {
					showKeyboard(KeyboardPage.this.lastName);
				}
			});
		} else {
			this.firstName.setActive(false);
			getKeyboard().setSpecialKey(SPECIAL_SUBMIT, new OnClickListener() {
				@Override
				public void onClick() {
					submit();
				}
			});
		}
		requestPaint();
		ej.widget.util.Keyboard keyboard = ServiceFactory.getService(ej.widget.util.Keyboard.class);
		if (keyboard != null) {
			keyboard.setEventHandler(keyboardText);
		}
	}

	private void submit() {
		String firstNameText = this.firstName.getText();
		String lastNameText = this.lastName.getText();
		if (firstNameText.length() > 0 && lastNameText.length() > 0) {
			this.resultLabel.setText(RESULT_PREFIX + this.firstName.getText() + " " + this.lastName.getText()); //$NON-NLS-1$
		} else {
			this.resultLabel.setText(RESULT_EMPTY);
			if (firstNameText.length() == 0) {
				this.firstName.setActive(true);
				this.lastName.setActive(false);
			}
		}
	}

}
