/*
 * Copyright 2016-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.keyboard;

import com.microej.demo.widget.style.ClassSelectors;

import ej.annotation.Nullable;
import ej.basictool.ArrayTools;
import ej.bon.Timer;
import ej.bon.TimerTask;
import ej.bon.XMath;
import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.microui.event.Event;
import ej.microui.event.EventHandler;
import ej.microui.event.generator.Command;
import ej.microui.event.generator.Pointer;
import ej.mwt.Container;
import ej.mwt.style.Style;
import ej.mwt.style.container.Alignment;
import ej.mwt.style.text.TextStyle;
import ej.mwt.util.Rectangle;
import ej.mwt.util.Size;
import ej.service.ServiceFactory;
import ej.widget.ElementAdapter;
import ej.widget.listener.OnClickListener;
import ej.widget.listener.OnTextChangeListener;
import ej.widget.util.ControlCharacters;
import ej.widget.util.Keyboard;
import ej.widget.util.States;

/**
 * A text is a widget that holds a string that can be modified by the user.
 */
public class KeyboardText extends Container implements EventHandler {

	private static final OnClickListener[] EMPTY_LISTENERS = new OnClickListener[0];
	private static final OnTextChangeListener[] EMPTY_TEXT_LISTENERS = new OnTextChangeListener[0];

	private static final String EMPTY_STRING = ""; //$NON-NLS-1$

	private static final long BLINK_PERIOD = 500;

	private static final String CLEAR_BUTTON_STRING = "\u00D7"; //$NON-NLS-1$

	private static final int DEFAULT_MAX_TEXT_LENGTH = 25;

	private StringBuilder buffer;
	private String placeHolder;

	private int caretStart;
	private int caretEnd;
	private OnTextChangeListener[] onTextChangeListeners;
	private OnClickListener[] onClickListeners;

	private boolean active;
	@Nullable
	private TimerTask blinkTask;
	private boolean showCaret;

	private final ElementAdapter selectionElement;
	private final ElementAdapter clearButtonElement;

	private int maxTextLength;

	/**
	 * Creates an empty text.
	 */
	public KeyboardText() {
		this(EMPTY_STRING);
	}

	/**
	 * Creates a text with an initial content.
	 *
	 * @param text
	 *            the text to set.
	 * @throws NullPointerException
	 *             if the given text is <code>null</code>.
	 */
	public KeyboardText(String text) {
		this(text, EMPTY_STRING);
	}

	/**
	 * Creates a text with an initial content and a place holder.
	 * <p>
	 * The place holder is displayed when the text is empty.
	 *
	 * @param text
	 *            the text to set.
	 * @param placeHolder
	 *            the place holder to set.
	 * @throws NullPointerException
	 *             if one or both the given text and place holder are <code>null</code>.
	 */
	public KeyboardText(String text, String placeHolder) {
		super();
		this.onTextChangeListeners = EMPTY_TEXT_LISTENERS;
		this.onClickListeners = EMPTY_LISTENERS;
		this.buffer = new StringBuilder(text);
		this.placeHolder = placeHolder;
		this.selectionElement = new ElementAdapter(this);
		this.selectionElement.addClassSelector(ClassSelectors.CLASS_SELECTOR_SELECTION);
		this.clearButtonElement = new ElementAdapter(this);
		this.clearButtonElement.addClassSelector(ClassSelectors.CLASS_SELECTOR_CLEAR_BUTTON);
		setMaxTextLength(DEFAULT_MAX_TEXT_LENGTH);
	}

	/**
	 * Gets the max text length of the text field.
	 *
	 * @return the max text length.
	 */
	public int getMaxTextLength() {
		return this.maxTextLength;
	}

	/**
	 * Sets the max text length of the text field.
	 *
	 * @param maxTextLength
	 *            the max text length.
	 */
	public void setMaxTextLength(int maxTextLength) {
		this.maxTextLength = maxTextLength;
	}

	/**
	 * Gets the place holder.
	 *
	 * @return the place holder.
	 */
	public String getPlaceHolder() {
		return this.placeHolder;
	}

	/**
	 * Gets the full text of the text field.
	 *
	 * @return the text.
	 */
	public String getText() {
		return this.buffer.toString();
	}

	/**
	 * Gets the text or the place holder depending on the state.
	 *
	 * @return the place holder if not empty, the text otherwise.
	 */
	private String getTextOrPlaceHolder() {
		if (isEmpty()) {
			return this.placeHolder;
		} else {
			return this.buffer.toString();
		}
	}

	/**
	 * Gets the full text length of the text field.
	 *
	 * @return the text length.
	 */
	public int getTextLength() {
		return this.buffer.length();
	}

	/**
	 * Sets the place holder.
	 *
	 * @param placeHolder
	 *            the place holder to set.
	 */
	public void setPlaceHolder(String placeHolder) {
		assert placeHolder != null;
		this.placeHolder = placeHolder;
	}

	/**
	 * Sets the content text.
	 *
	 * @param text
	 *            the text to set.
	 * @throws NullPointerException
	 *             if the given text is <code>null</code>.
	 */
	public void setText(String text) {
		boolean wasEmpty = isEmpty();
		this.buffer = new StringBuilder(text);
		int newCaret = text.length();
		setCaret(newCaret);
		updateEmptyState(wasEmpty, isEmpty());
		notifyOnTextChangeListeners(newCaret, text);
	}

	/**
	 * Removes the character just before the caret.
	 * <p>
	 * If a part of the text is selected, it is removed instead of the character.
	 * <p>
	 * If the text is modified, the state listener is notified.
	 *
	 * @see #addOnTextChangeListener(OnTextChangeListener)
	 */
	public void back() {
		if (!removeSelection()) {
			int caret = getCaret();
			if (caret > 0) {
				boolean wasEmpty = isEmpty();
				int newCaret = caret - 1;
				this.buffer.deleteCharAt(newCaret);
				setCaret(newCaret);
				updateEmptyState(wasEmpty, isEmpty());
				notifyOnTextChangeListeners(newCaret, getText());
			}
		}
	}

	/**
	 * Inserts a string in the text field at the caret position.
	 * <p>
	 * If a part of the text is selected, it is removed.
	 * <p>
	 * If the text is modified, the state listener is notified.
	 *
	 * @param text
	 *            the string to insert.
	 * @see #addOnTextChangeListener(OnTextChangeListener)
	 */
	public void insert(String text) {
		boolean wasEmpty = isEmpty();
		removeSelection();
		int caret = getCaret();
		this.buffer.insert(caret, text);
		int newCaret = caret + text.length();
		setCaret(newCaret);
		updateEmptyState(wasEmpty, isEmpty());
		notifyOnTextChangeListeners(newCaret, getText());
	}

	/**
	 * Inserts a character in the text field at the caret position.
	 * <p>
	 * If a part of the text is selected, it is removed.
	 * <p>
	 * If the text is modified, the state listener is notified.
	 *
	 * @param c
	 *            the character to insert.
	 * @see #addOnTextChangeListener(OnTextChangeListener)
	 */
	public void insert(char c) {
		if (getTextLength() < this.maxTextLength) {
			boolean wasEmpty = isEmpty();
			removeSelection();
			int caret = getCaret();
			this.buffer.insert(caret, c);
			int newCaret = caret + 1;
			setCaret(newCaret);
			updateEmptyState(wasEmpty, false);
			notifyOnTextChangeListeners(newCaret, getText());
			requestRender();
		}
	}

	private void updateEmptyState(boolean wasEmpty, boolean isEmpty) {
		if (wasEmpty != isEmpty) {
			updateStyleRecursive();
			requestRender();
		}
	}

	/**
	 * Gets whether or not the text is empty.
	 *
	 * @return <code>true</code> if the text is empty, <code>false</code> otherwise.
	 */
	public boolean isEmpty() {
		return this.buffer.length() == 0;
	}

	/**
	 * Gets the part of the text that is selected.
	 * <p>
	 * The selection is the part of text between selection start.
	 *
	 * @return the selected text.
	 */
	public String getSelection() {
		int selectionStart = getSelectionStart();
		int selectionEnd = getSelectionEnd();
		char[] selectedChars = new char[selectionEnd - selectionStart + 1];
		this.buffer.getChars(selectionStart, selectionStart, selectedChars, 0);
		return new String(selectedChars);
	}

	/**
	 * Gets the caret position in the text.
	 *
	 * @return the caret position.
	 */
	public int getCaret() {
		return this.caretEnd;
	}

	/**
	 * Gets the start index of the selection.
	 *
	 * @return the start index of the selection.
	 */
	public int getSelectionStart() {
		return Math.min(this.caretStart, this.caretEnd);
	}

	/**
	 * Gets the end index of the selection.
	 *
	 * @return the end index of the selection.
	 */
	public int getSelectionEnd() {
		return Math.max(this.caretStart, this.caretEnd);
	}

	/**
	 * Sets the selection range in the text.
	 *
	 * @param start
	 *            the selection start index.
	 * @param end
	 *            the selection end index.
	 */
	public void setSelection(int start, int end) {
		int length = this.buffer.length();
		start = XMath.limit(start, 0, length);
		end = XMath.limit(end, 0, length);
		this.caretStart = start;
		this.caretEnd = end;
		requestRender();
	}

	/**
	 * Sets the caret position in the text.
	 *
	 * @param position
	 *            the caret index.
	 */
	public void setCaret(int position) {
		setSelection(position, position);
	}

	/**
	 * Gets whether the caret is currently shown or not.
	 * <p>
	 * This state changes repeatedly when the text is active and the caret is blinking.
	 *
	 * @return <code>true</code> if the caret is shown, <code>false</code> otherwise.
	 */
	protected boolean isShownCaret() {
		return this.showCaret;
	}

	private boolean removeSelection() {
		int selectionStart = getSelectionStart();
		int selectionEnd = getSelectionEnd();
		if (selectionStart != selectionEnd) {
			this.buffer.delete(selectionStart, selectionEnd);
			setCaret(selectionStart);
			return true;
		}
		return false;
	}

	/**
	 * Adds a listener on the text change events of the text.
	 *
	 * @param onTextChangeListener
	 *            the text listener to add.
	 */
	public void addOnTextChangeListener(OnTextChangeListener onTextChangeListener) {
		assert onTextChangeListener != null;
		this.onTextChangeListeners = ArrayTools.add(this.onTextChangeListeners, onTextChangeListener);
	}

	/**
	 * Removes a listener on the text change events of the text.
	 *
	 * @param onTextChangeListener
	 *            the text listener to remove.
	 */
	public void removeOnTextChangeListener(OnTextChangeListener onTextChangeListener) {
		this.onTextChangeListeners = ArrayTools.remove(this.onTextChangeListeners, onTextChangeListener);
	}

	private void notifyOnTextChangeListeners(int newCaret, String text) {
		for (OnTextChangeListener listener : this.onTextChangeListeners) {
			listener.onTextChange(newCaret, text);
		}
	}

	/**
	 * Adds a listener on the click events of the text.
	 *
	 * @param onClickListener
	 *            the click listener to add.
	 */
	public void addOnClickListener(OnClickListener onClickListener) {
		assert onClickListener != null;
		this.onClickListeners = ArrayTools.add(this.onClickListeners, onClickListener);
	}

	/**
	 * Removes a listener on the click events of the text.
	 *
	 * @param onClickListener
	 *            the click listener to remove.
	 */
	public void removeOnClickListener(OnClickListener onClickListener) {
		this.onClickListeners = ArrayTools.remove(this.onClickListeners, onClickListener);
	}

	private void notifyOnClickListeners() {
		for (OnClickListener onClickListener : this.onClickListeners) {
			onClickListener.onClick();
		}
	}

	@Override
	public void computeContentOptimalSize(Size availableSize) {
		this.selectionElement.updateStyle();
		this.clearButtonElement.updateStyle();
		Style style = getStyle();
		Font font = getDesktop().getFont(style);
		style.getTextStyle().computeContentSize(getTextOrPlaceHolder(), font, availableSize);
		// Add selection thickness.
		availableSize.addOutline(1, 1, 0, 0);
	}

	/**
	 * Activates or deactivates the text.
	 * <p>
	 * When the text is active or has the focus, the cursor blinks, otherwise the cursor is not visible.
	 *
	 * @param active
	 *            <code>true</code> to activate the text, <code>false</code> otherwise.
	 * @since 2.3.0
	 */
	public void setActive(boolean active) {
		this.active = active;
		stopBlink();
		if (active) {
			startBlink();
		}
		updateStyleRecursive();
		requestRender();
	}

	private void startBlink() {
		TimerTask blinkTask = new TimerTask() {
			@Override
			public void run() {
				KeyboardText.this.showCaret = !KeyboardText.this.showCaret;
				requestRender();
			}

			@Override
			public boolean cancel() {
				KeyboardText.this.showCaret = false;
				requestRender();
				return super.cancel();
			}
		};
		this.blinkTask = blinkTask;
		ServiceFactory.getService(Timer.class, Timer.class).schedule(blinkTask, 0, BLINK_PERIOD);
	}

	@Override
	public void onHidden() {
		super.onHidden();
		stopBlink();
	}

	private void stopBlink() {
		TimerTask blinkTask = this.blinkTask;
		if (blinkTask != null) {
			blinkTask.cancel();
		}
	}

	@Override
	public boolean isInState(int state) {
		return (state == States.ACTIVE && this.active) || (state == States.EMPTY && isEmpty())
				|| super.isInState(state);
	}

	@Override
	public void renderContent(GraphicsContext g, Size size) {
		Style style = getStyle();
		Font font = getDesktop().getFont(style);
		TextStyle textManager = style.getTextStyle();
		// Keep call to getText() for subclasses (such as Password).
		String text = getText();
		int alignment = style.getAlignment();
		int foregroundColor = style.getForegroundColor();

		// Remove selection thickness.
		int width = size.getWidth() - 1;
		int height = size.getHeight() - 1;

		// Compute selection bounds and draw it.
		int selectionStart = getSelectionStart();
		int selectionEnd = getSelectionEnd();
		if (selectionStart != selectionEnd || isShownCaret()) {
			Style selectionStyle = this.selectionElement.getStyle();
			Rectangle[] selection = textManager.getBounds(selectionStart, selectionEnd, text, font, width, height,
					alignment);
			g.setColor(selectionStyle.getForegroundColor());
			for (Rectangle rectangle : selection) {
				int w = (selectionStart != selectionEnd ? rectangle.getWidth() : 1);
				Painter.fillRectangle(g, rectangle.getX() + 1, rectangle.getY(), w, rectangle.getHeight());
			}
		}
		// handling placeholder case
		if (isEmpty()) {
			text = this.placeHolder;
		}

		// Shift selection thickness.
		g.translate(1, 1);
		textManager.drawText(g, text, font, foregroundColor, width, height, alignment);
		g.translate(-1, -1);

		// Draw clear button.
		Style clearButtonStyle = this.clearButtonElement.getStyle();
		Font clearButtonFont = getDesktop().getFont(clearButtonStyle);
		textManager.drawText(g, CLEAR_BUTTON_STRING, clearButtonFont, clearButtonStyle.getForegroundColor(), width,
				height, clearButtonStyle.getAlignment());
	}

	@Override
	public boolean handleEvent(int event) {
		int type = Event.getType(event);
		switch (type) {
		case Event.COMMAND:
			int data = Event.getData(event);
			if (onCommand(data)) {
				return true;
			}
			break;
		case Keyboard.EVENTGENERATOR_ID:
			handleKeyboard(event);
			return true;
		case Event.POINTER:
			Pointer pointer = (Pointer) Event.getGenerator(event);
			int pointerX = pointer.getX();
			int pointerY = pointer.getY();
			int action = Pointer.getAction(event);
			switch (action) {
			case Pointer.PRESSED:
				onPointerPressed(pointerX, pointerY);
				break;
			case Pointer.RELEASED:
				onPointerReleased();
				return true;
			case Pointer.DRAGGED:
				onPointerDragged(pointerX, pointerY);
				return true;
			case Pointer.DOUBLE_CLICKED:
				onPointerDoubleClicked();
				break;
			}
			break;
		}
		return super.handleEvent(event);
	}

	private void handleKeyboard(int event) {
		Keyboard keyboard = (Keyboard) Event.getGenerator(event);
		char c = keyboard.getChar(event);
		if (c == ControlCharacters.BACK_SPACE) {
			back();
		} else {
			insert(c);
		}
	}

	private void onPointerPressed(int pointerX, int pointerY) {
		int newCaret = getCaret(pointerX, pointerY);
		setCaret(newCaret); // reset selection and caret to current position

		// check clear button event
		Style style = this.clearButtonElement.getStyle();
		int clearButtonWidth = getDesktop().getFont(style).stringWidth(CLEAR_BUTTON_STRING);
		int clearButtonX = Alignment.computeLeftX(clearButtonWidth, getContentX(), getContentWidth(),
				style.getAlignment());
		int pX = getRelativeX(pointerX);
		if (pX >= clearButtonX && pX < clearButtonX + clearButtonWidth) {
			setText(EMPTY_STRING);
		}
	}

	private void onPointerDragged(int pointerX, int pointerY) {
		int newCaret = getCaret(pointerX, pointerY);

		// Retrieve selection beginning.
		int caret = getCaret();
		int start = getSelectionStart();
		int end = getSelectionEnd();
		int caretStart;
		if (start == caret) {
			caretStart = end;
		} else {
			caretStart = start;
		}

		// Update selection.
		setSelection(caretStart, newCaret);
	}

	private int getCaret(int pointerX, int pointerY) {
		int x = getRelativeX(pointerX);
		int y = getRelativeY(pointerY);
		Style style = getStyle();
		Font font = getDesktop().getFont(style);
		Rectangle contentBounds = getContentBounds();
		return style.getTextStyle().getIndex(x - contentBounds.getX(), y - contentBounds.getY(), getText(), font,
				contentBounds.getWidth(), contentBounds.getHeight(), style.getAlignment());
	}

	private void onPointerReleased() {
		notifyOnClickListeners();
	}

	private void onPointerDoubleClicked() {
		setSelection(0, getTextLength());
	}

	private boolean onCommand(int command) {
		int caret = getCaret();
		if (command == Command.LEFT) {
			if (caret > 0) {
				setCaret(caret - 1);
				return true;
			}
		} else if (command == Command.RIGHT) {
			if (caret < getTextLength()) {
				setCaret(caret + 1);
				return true;
			}
		}

		return false;
	}

	@Override
	protected void layOutChildren(int contentWidth, int contentHeight) {
	}

}
