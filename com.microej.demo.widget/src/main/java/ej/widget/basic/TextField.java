/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.basic;

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
import ej.mwt.Widget;
import ej.mwt.style.Style;
import ej.mwt.util.Alignment;
import ej.mwt.util.Rectangle;
import ej.mwt.util.Size;
import ej.service.ServiceFactory;
import ej.widget.listener.OnClickListener;
import ej.widget.listener.OnTextChangeListener;
import ej.widget.util.ControlCharacters;
import ej.widget.util.Keyboard;
import ej.widget.util.States;
import ej.widget.util.StringPainter;

/**
 * A text field is a widget that holds a string that can be modified by the user.
 */
public class TextField extends Widget implements EventHandler {

	/**
	 * The extra field ID for the selection color.
	 */
	public static final int SELECTION_COLOR = 0;

	/**
	 * The extra field ID for the clear button font.
	 */
	public static final int CLEAR_BUTTON_FONT = 1;

	private static final OnClickListener[] EMPTY_LISTENERS = new OnClickListener[0];
	private static final OnTextChangeListener[] EMPTY_TEXT_LISTENERS = new OnTextChangeListener[0];

	private static final String EMPTY_STRING = ""; //$NON-NLS-1$

	private static final long BLINK_PERIOD = 500;

	private static final String CLEAR_BUTTON_STRING = "\u00D7"; //$NON-NLS-1$

	private static final int DEFAULT_MAX_TEXT_LENGTH = 25;

	private final StringBuilder buffer;

	private String placeHolder;
	private int maxTextLength;

	private int caretStart;
	private int caretEnd;
	private OnTextChangeListener[] onTextChangeListeners;
	private OnClickListener[] onClickListeners;

	private boolean active;
	@Nullable
	private TimerTask blinkTask;
	private boolean showCaret;

	/**
	 * Creates a text field with an empty text.
	 */
	public TextField() {
		this(EMPTY_STRING);
	}

	/**
	 * Creates a text field with an initial text.
	 *
	 * @param text
	 *            the text to set.
	 */
	public TextField(String text) {
		this(text, EMPTY_STRING);
	}

	/**
	 * Creates a text field with an initial text and a place holder.
	 * <p>
	 * The place holder is displayed when the text is empty.
	 *
	 * @param text
	 *            the text to set.
	 * @param placeHolder
	 *            the place holder to set.
	 */
	public TextField(String text, String placeHolder) {
		this.onTextChangeListeners = EMPTY_TEXT_LISTENERS;
		this.onClickListeners = EMPTY_LISTENERS;
		this.buffer = new StringBuilder(text);
		this.placeHolder = placeHolder;
		this.maxTextLength = DEFAULT_MAX_TEXT_LENGTH;
		setEnabled(true);
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

		if (this.buffer.length() > maxTextLength) {
			this.buffer.setLength(maxTextLength);
		}
	}

	/**
	 * Gets the text of the text field.
	 *
	 * @return the text.
	 */
	public String getText() {
		return this.buffer.toString();
	}

	/**
	 * Gets the text or the place holder, depending on whether or not the text is empty.
	 *
	 * @return the text if it is not empty, the place holder otherwise.
	 */
	private String getTextOrPlaceHolder() {
		if (isEmpty()) {
			return this.placeHolder;
		} else {
			return getText();
		}
	}

	/**
	 * Gets the length of the text of the text field.
	 *
	 * @return the text length.
	 */
	public int getTextLength() {
		return this.buffer.length();
	}

	/**
	 * Sets the place holder of the text field.
	 *
	 * @param placeHolder
	 *            the place holder to set.
	 */
	public void setPlaceHolder(String placeHolder) {
		this.placeHolder = placeHolder;
	}

	/**
	 * Sets the text of the text field.
	 *
	 * @param text
	 *            the text to set.
	 */
	public void setText(String text) {
		boolean wasEmpty = isEmpty();
		this.buffer.setLength(0);
		this.buffer.append(text);
		setCaret(text.length());
		updateEmptyState(wasEmpty, isEmpty());
		notifyOnTextChangeListeners(text);
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
			int caret = this.caretEnd;
			if (caret > 0) {
				boolean wasEmpty = isEmpty();
				int newCaret = caret - 1;
				this.buffer.deleteCharAt(newCaret);
				setCaret(newCaret);
				updateEmptyState(wasEmpty, isEmpty());
				notifyOnTextChangeListeners(getText());
			}
		}
	}

	/**
	 * Inserts a character in the text at the caret position.
	 * <p>
	 * If a part of the text is selected, it is removed and replaced by the given character.
	 * <p>
	 * If the text is modified, the state listener is notified.
	 *
	 * @param c
	 *            the character to insert.
	 * @see #addOnTextChangeListener(OnTextChangeListener)
	 */
	public void insert(char c) {
		if (this.buffer.length() < this.maxTextLength) {
			boolean wasEmpty = isEmpty();
			removeSelection();
			int caret = this.caretEnd;
			this.buffer.insert(caret, c);
			int newCaret = caret + 1;
			setCaret(newCaret);
			updateEmptyState(wasEmpty, false);
			notifyOnTextChangeListeners(getText());
		}
	}

	private void updateEmptyState(boolean wasEmpty, boolean isEmpty) {
		if (wasEmpty != isEmpty) {
			updateStyle();
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
		if (start != this.caretStart || end != this.caretEnd) {
			int length = this.buffer.length();
			start = XMath.limit(start, 0, length);
			end = XMath.limit(end, 0, length);
			this.caretStart = start;
			this.caretEnd = end;
			if (this.active) {
				this.showCaret = true;
				if (start == end) {
					startBlink();
				} else {
					stopBlink();
				}
			}
			requestRender();
		}
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
	 *            the text change listener to add.
	 */
	public void addOnTextChangeListener(OnTextChangeListener onTextChangeListener) {
		this.onTextChangeListeners = ArrayTools.add(this.onTextChangeListeners, onTextChangeListener);
	}

	/**
	 * Removes a listener on the text change events of the text.
	 *
	 * @param onTextChangeListener
	 *            the text change listener to remove.
	 */
	public void removeOnTextChangeListener(OnTextChangeListener onTextChangeListener) {
		this.onTextChangeListeners = ArrayTools.remove(this.onTextChangeListeners, onTextChangeListener);
	}

	private void notifyOnTextChangeListeners(String text) {
		for (OnTextChangeListener listener : this.onTextChangeListeners) {
			listener.onTextChange(text);
		}
	}

	/**
	 * Adds a listener on the click events of the text.
	 *
	 * @param onClickListener
	 *            the click listener to add.
	 */
	public void addOnClickListener(OnClickListener onClickListener) {
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
	protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
		Style style = getStyle();
		int horizontalAlignment = style.getHorizontalAlignment();
		int verticalAlignment = style.getVerticalAlignment();
		Font font = style.getFont();

		// Compute selection bounds and draw it.
		int selectionStart = getSelectionStart();
		int selectionEnd = getSelectionEnd();
		String text = getTextOrPlaceHolder();
		if (this.showCaret) {
			int selectionColor = style.getExtraInt(SELECTION_COLOR, style.getColor());
			g.setColor(selectionColor);
			Rectangle selection = getBounds(selectionStart, selectionEnd, text, font, contentWidth, contentHeight,
					horizontalAlignment, verticalAlignment);
			int w = (selectionStart != selectionEnd ? selection.getWidth() : 1);
			Painter.fillRectangle(g, selection.getX(), selection.getY(), w, selection.getHeight());
		}

		// Draw text.
		g.resetEllipsis();
		g.setColor(style.getColor());
		StringPainter.drawStringInArea(g, text, font, 0, 0, contentWidth, contentHeight, horizontalAlignment,
				verticalAlignment);

		// Draw clear button.
		Font clearButtonFont = style.getExtraObject(CLEAR_BUTTON_FONT, Font.class, font);
		StringPainter.drawStringInArea(g, CLEAR_BUTTON_STRING, clearButtonFont, 0, 0, contentWidth, contentHeight,
				Alignment.RIGHT, Alignment.VCENTER);
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		Font font = getStyle().getFont();
		StringPainter.computeOptimalSize(getTextOrPlaceHolder(), font, size);
	}

	private Rectangle getBounds(int startIndex, int endIndex, String text, Font font, int areaWidth, int areaHeight,
			int horizontalAlignment, int verticalAlignment) {
		// Shift to beginning of the text.
		int xLeft = Alignment.computeLeftX(font.stringWidth(text), 0, areaWidth, horizontalAlignment);
		int yTop = Alignment.computeTopY(font.getHeight(), 0, areaHeight, verticalAlignment);
		int startY = yTop;
		int startX = xLeft + getX(startIndex, 0, font, text);
		int endX = xLeft + getX(endIndex, 0, font, text);
		return new Rectangle(startX, startY, endX - startX, font.getHeight());
	}

	private static int getX(int index, int currentIndex, Font font, String line) {
		if (index < currentIndex) {
			return font.stringWidth(line);
		} else {
			return font.substringWidth(line, 0, index - currentIndex);
		}
	}

	/**
	 * Activates or deactivates the text field.
	 * <p>
	 * When the text field is active the cursor blinks, otherwise the cursor is not visible.
	 *
	 * @param active
	 *            <code>true</code> to activate the text field, <code>false</code> otherwise.
	 * @since 2.3.0
	 */
	public void setActive(boolean active) {
		if (active != this.active) {
			this.active = active;
			if (active) {
				startBlink();
			} else {
				stopBlink();
				this.showCaret = false;
			}
			updateStyle();
			requestRender();
		}
	}

	private void startBlink() {
		if (this.blinkTask == null) {
			TimerTask blinkTask = new TimerTask() {
				@Override
				public void run() {
					TextField.this.showCaret = !TextField.this.showCaret;
					requestRender();
				}
			};
			this.blinkTask = blinkTask;
			ServiceFactory.getService(Timer.class, Timer.class).schedule(blinkTask, 0, BLINK_PERIOD);
		}
	}

	@Override
	protected void onHidden() {
		super.onHidden();
		stopBlink();
	}

	private void stopBlink() {
		TimerTask blinkTask = this.blinkTask;
		if (blinkTask != null) {
			blinkTask.cancel();
			this.blinkTask = null;
		}
	}

	@Override
	public boolean isInState(int state) {
		return (state == States.ACTIVE && this.active) || (state == States.EMPTY && isEmpty())
				|| super.isInState(state);
	}

	@Override
	public boolean handleEvent(int event) {
		int type = Event.getType(event);

		switch (type) {
		case Command.EVENT_TYPE:
			int data = Event.getData(event);
			if (onCommand(data)) {
				return true;
			}
			break;
		case Keyboard.EVENTGENERATOR_ID:
			handleKeyboard(event);
			return true;
		case Pointer.EVENT_TYPE:
			Pointer pointer = (Pointer) Event.getGenerator(event);
			int pointerX = pointer.getX();
			int action = Pointer.getAction(event);
			switch (action) {
			case Pointer.PRESSED:
				onPointerPressed(pointerX);
				break;
			case Pointer.RELEASED:
				onPointerReleased();
				return true;
			case Pointer.DRAGGED:
				onPointerDragged(pointerX);
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

	private void onPointerPressed(int pointerX) {
		Style style = getStyle();
		Rectangle contentBounds = getContentBounds();

		// check clear button click
		Font clearButtonFont = style.getExtraObject(CLEAR_BUTTON_FONT, Font.class, style.getFont());
		int clearButtonWidth = clearButtonFont.stringWidth(CLEAR_BUTTON_STRING);
		int clearButtonX = Alignment.computeLeftX(clearButtonWidth, contentBounds.getX(), contentBounds.getWidth(),
				Alignment.RIGHT);
		int pX = pointerX - getAbsoluteX();
		if (pX >= clearButtonX && pX < clearButtonX + clearButtonWidth) {
			setText(EMPTY_STRING);
		} else {
			int newCaret = getCaret(pointerX);
			setCaret(newCaret); // reset selection and caret to current position
		}
	}

	private void onPointerDragged(int pointerX) {
		int newCaret = getCaret(pointerX);

		// Update selection.
		setSelection(this.caretStart, newCaret);
	}

	private int getCaret(int pointerX) {
		int x = pointerX - getAbsoluteX();
		Style style = getStyle();
		Font font = style.getFont();
		Rectangle contentBounds = getContentBounds();
		return getIndex(x - contentBounds.getX(), getText(), font, contentBounds.getWidth(),
				style.getHorizontalAlignment());
	}

	private int getIndex(int x, String text, Font font, int areaWidth, int horizontalAlignment) {
		int textLength = text.length();
		if (textLength == 0) {
			return 0;
		}

		// Shift to beginning of the text.
		int xLeft = Alignment.computeLeftX(font.stringWidth(text), 0, areaWidth, horizontalAlignment);
		x -= xLeft;

		int min = 0;
		int max = textLength;
		int startX = 0;

		// Search the character under the given position.
		// Use dichotomy.
		while (max - min > 1) {
			int half = (max - min) >> 1;
			int halfWidth = font.substringWidth(text, min, half);
			if (startX + halfWidth > x) {
				// Select first part.
				max = min + half;
			} else {
				// Select second part.
				min += half;
				startX += halfWidth;
			}
		}

		// Select the right side of the selected character.
		if ((startX + font.charWidth(text.charAt(min)) / 2) < x) {
			min++;
		}
		return min;
	}

	private void onPointerReleased() {
		notifyOnClickListeners();
	}

	private void onPointerDoubleClicked() {
		setSelection(0, getTextLength());
	}

	private boolean onCommand(int command) {
		int caret = this.caretEnd;
		if (command == Command.LEFT) {
			if (caret > 0) {
				setCaret(caret - 1);
				return true;
			}
		} else if (command == Command.RIGHT) {
			if (caret < this.buffer.length()) {
				setCaret(caret + 1);
				return true;
			}
		}
		return false;
	}
}
