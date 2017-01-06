package ej.demo.ui.widget.page;

import ej.demo.ui.widget.WidgetsDemo;
import ej.demo.ui.widget.keyboard.LowerCaseLayout;
import ej.demo.ui.widget.keyboard.NumericLayout;
import ej.demo.ui.widget.keyboard.SymbolLayout;
import ej.demo.ui.widget.keyboard.UpperCaseLayout;
import ej.demo.ui.widget.style.ClassSelectors;
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

	private KeyboardText[] form;
	private KeyboardText firstName;
	private KeyboardText lastName;
	private Label resultLabel;

	/**
	 * Constructor
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
	protected Widget createMainContent() {
		// create focus listener
		OnFocusListener onFocusListener = new OnFocusListener() {
			@Override
			public void onGainFocus() {
				showKeyboard();
			}

			@Override
			public void onLostFocus() {
				hideKeyboard();
			}
		};

		// first name
		this.firstName = new KeyboardText(EMPTY_STRING, FIRST_NAME);
		this.firstName.addOnFocusListener(onFocusListener);

		// last name
		this.lastName = new KeyboardText(EMPTY_STRING, LAST_NAME);
		this.lastName.addOnFocusListener(onFocusListener);

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

	/**
	 * Handles page transition stop
	 */
	@Override
	public void onTransitionStop() {
		WidgetsDemo.getPanel().setFocus(this.form[0]);
	}

	/**
	 * Shows the keyboard
	 */
	@Override
	protected void showKeyboard() {
		int focusedIndex = getFormFocusedIndex();
		if (focusedIndex != -1) {
			String specialString = (focusedIndex == this.form.length - 1 ? SPECIAL_SUBMIT : SPECIAL_NEXT);
			getKeyboard().setSpecialKey(specialString, new OnClickListener() {
				@Override
				public void onClick() {
					onSpecialKeyClick();
				}
			});
		}

		super.showKeyboard();
	}

	/**
	 * Gets the index of the widget currently focused
	 */
	private int getFormFocusedIndex() {
		Widget focusedWidget = WidgetsDemo.getPanel().getFocus();
		if (focusedWidget != null) {
			for (int w = 0; w < this.form.length; w++) {
				if (this.form[w] == focusedWidget) {
					return w;
				}
			}
		}
		return -1;
	}

	/**
	 * Handles special key click
	 */
	private void onSpecialKeyClick() {
		int focusedIndex = getFormFocusedIndex();
		if (focusedIndex != -1) {
			if (focusedIndex == this.form.length - 1) {
				String firstNameText = this.firstName.getText();
				String lastNameText = this.lastName.getText();
				if (firstNameText.length() > 0 && lastNameText.length() > 0) {
					this.resultLabel.setText(RESULT_PREFIX + this.firstName.getText() + " " + this.lastName.getText()); //$NON-NLS-1$
				} else {
					this.resultLabel.setText(RESULT_EMPTY);
				}
			} else {
				Widget nextWidget = this.form[focusedIndex + 1];
				WidgetsDemo.getPanel().setFocus(nextWidget);
			}
		}
	}
}
