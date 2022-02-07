/*
 * Copyright 2021-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.keyboard;

import com.microej.demo.widget.keyboard.widget.Keyboard;
import com.microej.demo.widget.keyboard.widget.LineWrappingLabel;
import com.microej.demo.widget.keyboard.widget.TextField;

import ej.bon.Timer;
import ej.microui.event.Event;
import ej.microui.event.generator.Buttons;
import ej.microui.event.generator.Pointer;
import ej.mwt.Widget;
import ej.service.ServiceFactory;
import ej.widget.basic.OnClickListener;
import ej.widget.container.Grid;
import ej.widget.container.LayoutOrientation;
import ej.widget.container.List;
import ej.widget.container.SimpleDock;

/**
 * Represents the content of the keyboard page.
 */
/* package */ class KeyboardPageContent {

	/** Empty string. */
	private static final String EMPTY_STRING = ""; //$NON-NLS-1$
	/** First name place holder. */
	private static final String FIRST_NAME = "First name"; //$NON-NLS-1$
	/** Last name place holder. */
	private static final String LAST_NAME = "Last name"; //$NON-NLS-1$
	/** Result prefix. */
	private static final String RESULT_PREFIX = "Hello\n"; //$NON-NLS-1$
	/** Special button caption for next text field. */
	private static final String SPECIAL_NEXT = "Next"; //$NON-NLS-1$
	/** Special button caption for submit text field. */
	private static final String SPECIAL_SUBMIT = "Submit"; //$NON-NLS-1$
	/** Maximum text field length. */
	private static final int MAX_TEXT_LENGTH = 50;
	/** Text field length. */
	private static final int TEXT_FIELD_LENGTH = 25;

	/** Keyboard object. */
	private final Keyboard keyboard;
	/** First name text field. */
	private final TextField firstName;
	/** Last name text field. */
	private final TextField lastName;
	/** Result label. */
	private final LineWrappingLabel resultLabel;
	/** Page content. */
	private final SimpleDock content;

	private final int initialSpecialKeySelector;
	private final int specialKeySelector;

	/* package */ KeyboardPageContent(int formSelector, int resultLabelSelector, int spaceKeySelector,
			int shiftKeyInactiveSelector, int shiftKeyActiveSelector, int switchMappingKeySelector,
			int initialSpecialKeySelector, int specialKeySelector) {
		Timer timer = ServiceFactory.getService(Timer.class, Timer.class);

		SimpleDock dock = new SimpleDock(LayoutOrientation.VERTICAL);
		this.keyboard = new Keyboard(timer, spaceKeySelector, shiftKeyInactiveSelector, shiftKeyActiveSelector,
				switchMappingKeySelector);
		this.firstName = createTextField(FIRST_NAME, timer);
		this.lastName = createTextField(LAST_NAME, timer);
		this.resultLabel = new LineWrappingLabel(EMPTY_STRING);
		this.resultLabel.addClassSelector(resultLabelSelector);

		// list
		List list = new List(LayoutOrientation.VERTICAL);
		list.addChild(this.firstName);
		list.addChild(this.lastName);
		list.addClassSelector(formSelector);

		Grid textGrid = new Grid(LayoutOrientation.HORIZONTAL, 2);
		textGrid.addChild(list);
		textGrid.addChild(this.resultLabel);

		dock.setFirstChild(textGrid);
		dock.setCenterChild(this.keyboard);

		this.content = dock;

		this.initialSpecialKeySelector = initialSpecialKeySelector;
		this.specialKeySelector = specialKeySelector;
	}

	private TextField createTextField(String placeHolder, Timer timer) {
		TextField textField = new TextField(EMPTY_STRING, placeHolder, TEXT_FIELD_LENGTH, timer) {
			@Override
			public boolean handleEvent(int event) {
				if (Event.getType(event) == Pointer.EVENT_TYPE && Buttons.isPressed(event)) {
					activateKeyboardField(this);
				}
				return super.handleEvent(event);
			}
		};
		textField.setMaxTextLength(MAX_TEXT_LENGTH);
		return textField;
	}

	private void activateKeyboardField(TextField keyboardText) {
		keyboardText.setActive(true);
		if (keyboardText == this.firstName) {
			this.lastName.setActive(false);
			this.keyboard.setSpecialKey(SPECIAL_NEXT, this.initialSpecialKeySelector, new OnClickListener() {
				@Override
				public void onClick() {
					activateKeyboardField(KeyboardPageContent.this.lastName);
				}
			});
		} else {
			this.firstName.setActive(false);
			this.keyboard.setSpecialKey(SPECIAL_SUBMIT, this.specialKeySelector, new OnClickListener() {
				@Override
				public void onClick() {
					submit();
				}
			});
		}

		SimpleDock pageContent = this.content;
		assert pageContent != null;
		pageContent.requestRender();

		this.keyboard.getEventGenerator().setEventHandler(keyboardText);
	}

	private void submit() {
		TextField firstName = this.firstName;
		TextField lastName = this.lastName;
		LineWrappingLabel resultLabel = this.resultLabel;
		assert resultLabel != null;

		String firstNameText = firstName.getText();
		String lastNameText = lastName.getText();
		if (firstNameText.length() > 0 && lastNameText.length() > 0) {
			resultLabel.setText(RESULT_PREFIX + firstNameText + " " + lastNameText); //$NON-NLS-1$
			resultLabel.requestRender();
		} else {
			resultLabel.setText(EMPTY_STRING);
			resultLabel.requestRender();
			if (firstNameText.length() == 0) {
				activateKeyboardField(firstName);
			}
		}
	}

	/* package */ Widget getContentWidget() {
		return this.content;
	}

}
