package ej.demo.ui.widget.page;

import ej.widget.StyledComposite;
import ej.widget.keyboard.azerty.Keyboard;

/**
 * This page illustrates an azerty keyboard.
 */
public class AzertyKeyboardPage extends KeyboardPage {

	private static final float KEYBOARD_RATIO = 0.50f;

	@Override
	protected StyledComposite createKeyboard() {
		return new Keyboard();
	}

	@Override
	protected float getKeyboardRatio() {
		return KEYBOARD_RATIO;
	}

}
