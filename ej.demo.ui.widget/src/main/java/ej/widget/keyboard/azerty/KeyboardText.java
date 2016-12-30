/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package ej.widget.keyboard.azerty;

import ej.bon.Timer;
import ej.bon.TimerTask;
import ej.bon.XMath;
import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.giml.annotation.Element;
import ej.giml.annotation.ElementAttribute;
import ej.giml.annotation.ElementConstructor;
import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.microui.event.Event;
import ej.microui.event.generator.Command;
import ej.microui.event.generator.Keyboard;
import ej.microui.event.generator.Pointer;
import ej.microui.util.EventHandler;
import ej.style.State;
import ej.style.Style;
import ej.style.container.Rectangle;
import ej.style.text.TextManager;
import ej.style.util.StyleHelper;
import ej.widget.StyledWidget;
import ej.widget.listener.OnClickListener;
import ej.widget.listener.OnTextChangeListener;
import ej.widget.util.ControlCharacters;

/** IPR start **/
/**
 * A text is a widget that holds a string that can be modified by the user.
 */
@Element
public class KeyboardText extends StyledWidget implements EventHandler {

	private static final OnClickListener[] EMPTY_LISTENERS = new OnClickListener[0];
	private static final OnTextChangeListener[] EMPTY_TEXT_LISTENERS = new OnTextChangeListener[0];

	private static final String EMPTY_STRING = ""; //$NON-NLS-1$

	private static final long BLINK_PERIOD = 500;

	private StringBuffer buffer;
	private String placeHolder;

	private int caretStart;
	private int caretEnd;
	private OnTextChangeListener[] onTextChangeListeners;
	private OnClickListener[] onClickListeners;

	private TimerTask blinkTask;
	private boolean showCursor;
	private boolean focused;

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
	@ElementConstructor
	public KeyboardText(@ElementAttribute(defaultValue = EMPTY_STRING) String text,
			@ElementAttribute(defaultValue = EMPTY_STRING) String placeHolder) {
		this.onTextChangeListeners = EMPTY_TEXT_LISTENERS;
		this.onClickListeners = EMPTY_LISTENERS;
		setText(text);
		setPlaceHolder(placeHolder);
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
	 * @throws NullPointerException
	 *             if the given place holder is <code>null</code>.
	 */
	public void setPlaceHolder(String placeHolder) {
		if (placeHolder == null) {
			throw new NullPointerException();
		}
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
		this.buffer = new StringBuffer(text);
		int newCaret = text.length();
		setCaret(newCaret);
		invalidate();
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
				invalidate();
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
		invalidate();
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
		boolean wasEmpty = isEmpty();
		removeSelection();
		int caret = getCaret();
		this.buffer.insert(caret, c);
		int newCaret = caret + 1;
		setCaret(newCaret);
		invalidate();
		updateEmptyState(wasEmpty, false);
		notifyOnTextChangeListeners(newCaret, getText());
	}

	private void updateEmptyState(boolean wasEmpty, boolean isEmpty) {
		if (wasEmpty != isEmpty) {
			updateStyle();
		}
	}

	/**
	 * Gets whether or not the text is empty.
	 *
	 * @return <code>true</code> if the text is empty, <code>false</code> otherwise.
	 */
	public boolean isEmpty() {
		try {
			return this.buffer.length() == 0;
		} catch (NullPointerException e) {
			return true;
		}
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
		repaint();
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

	private boolean removeSelection() {
		int selectionStart = getSelectionStart();
		int selectionEnd = getSelectionEnd();
		if (selectionStart != selectionEnd) {
			this.buffer.delete(selectionStart, selectionEnd);
			invalidate();
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
		OnTextChangeListener[] onTextChangeListeners = this.onTextChangeListeners;
		int listenersLength = onTextChangeListeners.length;
		OnTextChangeListener[] newArray = new OnTextChangeListener[listenersLength + 1];
		System.arraycopy(onTextChangeListeners, 0, newArray, 0, listenersLength);
		newArray[listenersLength] = onTextChangeListener;
		this.onTextChangeListeners = newArray;
	}

	/**
	 * Removes a listener on the text change events of the text.
	 *
	 * @param onTextChangeListener
	 *            the text listener to remove.
	 */
	public void removeOnTextChangeListener(OnTextChangeListener onTextChangeListener) {
		OnTextChangeListener[] onTextChangeListeners = this.onTextChangeListeners;
		int listenersLength = onTextChangeListeners.length;
		for (int i = listenersLength; --i >= 0;) {
			OnTextChangeListener candidate = onTextChangeListeners[i];
			if (candidate.equals(onTextChangeListener)) {
				if (listenersLength == 1) {
					this.onTextChangeListeners = EMPTY_TEXT_LISTENERS;
				} else {
					OnTextChangeListener[] newArray = new OnTextChangeListener[listenersLength - 1];
					System.arraycopy(onTextChangeListeners, 0, newArray, 0, i);
					System.arraycopy(onTextChangeListeners, i + 1, newArray, i, listenersLength - i - 1);
					this.onTextChangeListeners = newArray;
				}
			}
		}
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
		OnClickListener[] onClickListeners = this.onClickListeners;
		int listenersLength = onClickListeners.length;
		OnClickListener[] newArray = new OnClickListener[listenersLength + 1];
		System.arraycopy(onClickListeners, 0, newArray, 0, listenersLength);
		newArray[listenersLength] = onClickListener;
		this.onClickListeners = newArray;
	}

	/**
	 * Removes a listener on the click events of the text.
	 *
	 * @param onClickListener
	 *            the click listener to remove.
	 */
	public void removeOnClickListener(OnClickListener onClickListener) {
		OnClickListener[] onClickListeners = this.onClickListeners;
		int listenersLength = onClickListeners.length;
		for (int i = listenersLength; --i >= 0;) {
			OnClickListener candidate = onClickListeners[i];
			if (candidate.equals(onClickListener)) {
				if (listenersLength == 1) {
					this.onClickListeners = EMPTY_LISTENERS;
				} else {
					OnClickListener[] newArray = new OnClickListener[listenersLength - 1];
					System.arraycopy(onClickListeners, 0, newArray, 0, i);
					System.arraycopy(onClickListeners, i + 1, newArray, i, listenersLength - i - 1);
					this.onClickListeners = newArray;
				}
			}
		}
	}

	private void notifyOnClickListeners() {
		for (OnClickListener onClickListener : this.onClickListeners) {
			onClickListener.onClick();
		}
	}

	@Override
	public void renderContent(GraphicsContext g, Style style, Rectangle bounds) {
		Font font = StyleHelper.getFont(style);
		TextManager textManager = style.getTextManager();
		// Keep call to getText() for subclasses (such as Password).
		String text = getText();
		int alignment = style.getAlignment();
		int foregroundColor = style.getForegroundColor();

		// Remove selection thickness.
		bounds.decrementSize(1, 1);

		// handling placeholder case
		if (isEmpty()) {
			text = this.placeHolder;
		}

		// Compute selection bounds and draw it.
		int selectionStart = getSelectionStart();
		int selectionEnd = getSelectionEnd();
		if (selectionStart != selectionEnd || this.showCursor) {
			Rectangle[] selection = textManager.getBounds(selectionStart, selectionEnd, text, font, bounds, alignment);
			g.setColor(foregroundColor);
			for (Rectangle rectangle : selection) {
				g.drawRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
			}
		}

		// Shift selection thickness.
		g.translate(1, 1);
		textManager.drawText(g, text, font, foregroundColor, bounds, alignment);
		g.translate(-1, -1);
	}

	@Override
	public Rectangle validateContent(Style style, Rectangle availableSize) {
		Font font = StyleHelper.getFont(style);
		Rectangle contentSize = style.getTextManager().computeContentSize(getTextOrPlaceHolder(), font, availableSize);
		// Add selection thickness.
		contentSize.incrementSize(1, 1);
		return contentSize;
	}

	@Override
	public void gainFocus() {
		Keyboard keyboard = ServiceLoaderFactory.getServiceLoader().getService(Keyboard.class);
		if (keyboard != null) {
			keyboard.setEventHandler(this);
		}
		this.focused = true;
		super.gainFocus();
		this.blinkTask = new TimerTask() {
			@Override
			public void run() {
				KeyboardText.this.showCursor = !KeyboardText.this.showCursor;
				repaint();
			}

			@Override
			public boolean cancel() {
				KeyboardText.this.showCursor = false;
				return super.cancel();
			}
		};
		ServiceLoaderFactory.getServiceLoader().getService(Timer.class).schedule(this.blinkTask, 0, BLINK_PERIOD);

		updateStyle();
	}

	@Override
	public void lostFocus() {
		super.lostFocus();
		this.focused = false;
		this.blinkTask.cancel();
		updateStyle();
	}

	@Override
	public boolean isInState(State state) {
		return (state == State.Focus && getPanel() != null && getPanel().getFocus() == this)
				|| (state == State.Empty && isEmpty()) || super.isInState(state);
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
		case Event.KEYBOARD:
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
		if (keyboard.getAction(event) == Keyboard.TEXT_INPUT) {
			char c = keyboard.getNextChar(event);
			if (c == ControlCharacters.BACK_SPACE) {
				back();
			} else {
				insert(c);
			}
		}
	}

	private void onPointerPressed(int pointerX, int pointerY) {
		int newCaret = getCaret(pointerX, pointerY);
		setCaret(newCaret); // reset selection and caret to current position
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
		Rectangle remainingBounds = getContentBounds();
		int caretIndex = style.getTextManager().getIndex(x, y, getText(), StyleHelper.getFont(style), remainingBounds,
				style.getAlignment());
		return caretIndex;
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

}
/** IPR end **/
