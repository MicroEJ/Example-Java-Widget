package com.microej.demo.widget.page;

import com.microej.demo.widget.WidgetsDemo;
import com.microej.demo.widget.keyboard.LowerCaseLayout;
import com.microej.demo.widget.keyboard.NumericLayout;
import com.microej.demo.widget.keyboard.SymbolLayout;
import com.microej.demo.widget.keyboard.UpperCaseLayout;
import com.microej.demo.widget.style.ClassSelectors;

import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.microui.event.generator.Keyboard;
import ej.mwt.Widget;
import ej.widget.basic.Label;
import ej.widget.container.List;
import ej.widget.container.Scroll;
import ej.widget.keyboard.KeyboardText;
import ej.widget.keyboard.Layout;
import ej.widget.listener.OnClickListener;
import ej.widget.listener.OnFocusListener;

/**
 * This page illustrates a form and a keyboard.
 */
public class EditionPage extends KeyboardPage {

	private static final String EMPTY_STRING = ""; //$NON-NLS-1$
	private static final String FIRST_NAME = "First name"; //$NON-NLS-1$
	private static final String LAST_NAME = "Last name"; //$NON-NLS-1$
	private static final String RESULT_EMPTY = "Please fill the form"; //$NON-NLS-1$
	private static final String RESULT_PREFIX = "Your name is "; //$NON-NLS-1$
	private static final String SPECIAL_NEXT = "Next"; //$NON-NLS-1$
	private static final String SPECIAL_SUBMIT = "Submit"; //$NON-NLS-1$

	private static final int MAX_TEXT_LENGTH = 33;

	private KeyboardText[] form;
	private KeyboardText firstName;
	private KeyboardText lastName;
	private Label resultLabel;

	/**
	 * Creates an edition page.
	 */
	public EditionPage() {
		// set keyboard layouts
		Layout[] layouts = new Layout[] { new LowerCaseLayout(), new UpperCaseLayout(), new NumericLayout(),
				new SymbolLayout() };
		setKeyboardLayouts(layouts);
	}

	/**
	 * Creates the widget representing the main content of the page
	 */
	@Override
	protected Widget createForm() {
		// first name
		this.firstName = new KeyboardText(EMPTY_STRING, FIRST_NAME);
		this.firstName.setMaxTextLength(MAX_TEXT_LENGTH);
		this.firstName.addOnFocusListener(new OnFocusListener() {
			@Override
			public void onGainFocus() {
				showKeyboard(EditionPage.this.firstName);
			}

			@Override
			public void onLostFocus() {
				// Nothing to do.
			}
		});

		// last name
		this.lastName = new KeyboardText(EMPTY_STRING, LAST_NAME);
		this.lastName.setMaxTextLength(MAX_TEXT_LENGTH);
		this.lastName.addOnFocusListener(new OnFocusListener() {
			@Override
			public void onGainFocus() {
				showKeyboard(EditionPage.this.lastName);
			}

			@Override
			public void onLostFocus() {
				// Nothing to do.
			}
		});

		// result label
		this.resultLabel = new Label(RESULT_EMPTY);
		this.resultLabel.addClassSelector(ClassSelectors.RESULT_LABEL);

		// form
		this.form = new KeyboardText[] { this.firstName, this.lastName };

		// list
		List list = new List(false);
		list.add(this.firstName);
		list.add(this.lastName);
		list.add(this.resultLabel);
		list.addClassSelector(ClassSelectors.FORM);

		// scroll
		final Scroll scroll = new Scroll(false, false);
		scroll.setWidget(list);
		return scroll;
	}

	@Override
	public void onStopAnimation() {
		WidgetsDemo.getPanel().setFocus(this.form[0]);
	}

	private void showKeyboard(KeyboardText keyboardText) {
		showKeyboard();
		keyboardText.setActive(true);
		if (keyboardText == this.firstName) {
			this.lastName.setActive(false);
			getKeyboard().setSpecialKey(SPECIAL_NEXT, new OnClickListener() {
				@Override
				public void onClick() {
					EditionPage.this.lastName.requestFocus();
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
		Keyboard keyboard = ServiceLoaderFactory.getServiceLoader().getService(Keyboard.class);
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
				this.firstName.requestFocus();
			}
		}
	}

}
