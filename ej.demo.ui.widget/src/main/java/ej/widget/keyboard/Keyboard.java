/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package ej.widget.keyboard;

import java.util.ArrayList;
import java.util.List;

import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.mwt.MWT;
import ej.style.Style;
import ej.style.container.Rectangle;
import ej.widget.StyledComposite;
import ej.widget.listener.OnClickListener;
import ej.widget.util.ControlCharacters;

/**
 * Represents a virtual keyboard
 */
public class Keyboard extends StyledComposite {

	public static final String SPACE_KEY_SELECTOR = "space_key";
	public static final String BACKSPACE_KEY_SELECTOR = "backspace_key";
	public static final String SHIFT_KEY_INACTIVE_SELECTOR = "shift_key_inactive";
	public static final String SHIFT_KEY_ACTIVE_SELECTOR = "shift_key_active";
	public static final String SWITCH_MAPPING_KEY_SELECTOR = "switch_mapping_key";
	public static final String SPECIAL_KEY_SELECTOR = "special_key";

	private static final int ABC_MAPPING = 0;
	private static final int NUMERIC_MAPPING = 1;
	private static final int SYMBOL_MAPPING = 2;

	private static class Cell {

		final Key key;
		final int startColumn;
		final int colspan;

		Cell(Key key, int startColumn, int colspan) {
			this.key = key;
			this.startColumn = startColumn;
			this.colspan = colspan;
		}
	}

	private static class Row {

		final int length;
		final List<Cell> cells;

		Row(int length) {
			this.length = length;
			this.cells = new ArrayList<>(length);
		}

		public Key getKey(int column) {
			return this.cells.get(column).key;
		}
	}

	private final Row[] rows;
	private final ej.microui.event.generator.Keyboard keyboard;
	private String specialKeyText;
	private OnClickListener specialKeyListener;

	/**
	 * Constructor
	 */
	public Keyboard() {
		this.keyboard = ServiceLoaderFactory.getServiceLoader().getService(ej.microui.event.generator.Keyboard.class);
		this.rows = new Row[4];
		createKeys();
		setLowerLetterMapping();
	}

	/**
	 * Sets the special key
	 *
	 * @param text
	 *            the text to draw on the key
	 * @param listener
	 *            the action to execute when the key is pressed
	 */
	public void setSpecialKey(String text, OnClickListener listener) {
		this.specialKeyText = text;
		this.specialKeyListener = listener;
		setSpecialKey(3, 2);
	}

	private void createKeys() {
		// create rows
		this.rows[0] = new Row(10);
		this.rows[1] = new Row(10);
		this.rows[2] = new Row(9);
		this.rows[3] = new Row(10);

		// fill first and second row
		for (int i = 0; i < 10; i++) {
			add(createKey(), this.rows[0], i, 1);
			add(createKey(), this.rows[1], i, 1);
		}

		// fill third row
		add(createKey(), this.rows[2], 0, 1);
		for (int i = 1; i < 7; i++) {
			add(createKey(), this.rows[2], i, 1);
		}
		add(createKey(), this.rows[2], 7, 2);

		// fill fourth row
		add(createKey(), this.rows[3], 0, 2);
		add(createKey(), this.rows[3], 2, 5);
		add(createKey(), this.rows[3], 7, 3);
	}

	private Key createKey() {
		return new Key(this.keyboard);
	}

	private void setLowerLetterMapping() {
		// first row
		final String firstRowChars = "azertyuiop";
		for (int i = 0; i < 10; i++) {
			setStandardKey(0, i, firstRowChars.charAt(i));
		}

		// second row
		final String secondRowChars = "qsdfghjklm";
		for (int i = 0; i < 10; i++) {
			setStandardKey(1, i, secondRowChars.charAt(i));
		}

		// third row
		final String thirdRowChars = "wxcvbn";
		setShiftKey(2, 0, false);
		for (int i = 0; i < 6; i++) {
			setStandardKey(2, i + 1, thirdRowChars.charAt(i));
		}
		setBackspaceKey(2, 7);

		// fourth row
		setMappingKey(3, 0, NUMERIC_MAPPING);
		setSpaceKey(3, 1);
		setSpecialKey(3, 2);
	}

	private void setUpperLetterMapping() {
		// first row
		final String firstRowChars = "AZERTYUIOP";
		for (int i = 0; i < 10; i++) {
			setStandardKey(0, i, firstRowChars.charAt(i));
		}

		// second row
		final String secondRowChars = "QSDFGHJKLM";
		for (int i = 0; i < 10; i++) {
			setStandardKey(1, i, secondRowChars.charAt(i));
		}

		// third row
		final String thirdRowChars = "WXCVBN";
		setShiftKey(2, 0, true);
		for (int i = 0; i < 6; i++) {
			setStandardKey(2, i + 1, thirdRowChars.charAt(i));
		}
		setBackspaceKey(2, 7);

		// fourth row
		setMappingKey(3, 0, NUMERIC_MAPPING);
		setSpaceKey(3, 1);
		setSpecialKey(3, 2);
	}

	private void setNumericMapping() {
		// first row
		final String firstRowChars = "1234567890";
		for (int i = 0; i < 10; i++) {
			setStandardKey(0, i, firstRowChars.charAt(i));
		}

		// second row
		final String secondRowChars = "-/:;()$&@\"";
		for (int i = 0; i < 10; i++) {
			setStandardKey(1, i, secondRowChars.charAt(i));
		}

		// third row
		final String thirdRowChars = ".,?!\'§";
		setMappingKey(2, 0, SYMBOL_MAPPING);
		for (int i = 0; i < 6; i++) {
			setStandardKey(2, i + 1, thirdRowChars.charAt(i));
		}
		setBackspaceKey(2, 7);

		// fourth row
		setMappingKey(3, 0, ABC_MAPPING);
		setSpaceKey(3, 1);
		setSpecialKey(3, 2);
	}

	private void setSymbolMapping() {
		// first row
		final String firstRowChars = "[]{}#%^*+=";
		for (int i = 0; i < 10; i++) {
			setStandardKey(0, i, firstRowChars.charAt(i));
		}

		// second row
		final String secondRowChars = "_\\|~<>€£¥\u25cf";
		for (int i = 0; i < 10; i++) {
			setStandardKey(1, i, secondRowChars.charAt(i));
		}

		// third row
		final String thirdRowChars = ".,?!\'§";
		setMappingKey(2, 0, NUMERIC_MAPPING);
		for (int i = 0; i < 6; i++) {
			setStandardKey(2, i + 1, thirdRowChars.charAt(i));
		}
		setBackspaceKey(2, 7);

		// fourth row
		setMappingKey(3, 0, ABC_MAPPING);
		setSpaceKey(3, 1);
		setSpecialKey(3, 2);
	}

	private Key getKey(int row, int col) {
		return this.rows[row].getKey(col);
	}

	private void setStandardKey(int row, int col, char character) {
		getKey(row, col).setStandard(character);
	}

	private void setSpaceKey(int row, int col) {
		getKey(row, col).setStandard(ControlCharacters.SPACE, SPACE_KEY_SELECTOR);
	}

	private void setBackspaceKey(int row, int col) {
		getKey(row, col).setStandard(ControlCharacters.BACK_SPACE, BACKSPACE_KEY_SELECTOR);
	}

	private void setShiftKey(int row, int col, final boolean active) {
		OnClickListener listener = new OnClickListener() {
			@Override
			public void onClick() {
				if (active) {
					setLowerLetterMapping();
				} else {
					setUpperLetterMapping();
				}
			}
		};

		String text = String.valueOf(ControlCharacters.SHIFT_IN);
		String classSelector = (active ? SHIFT_KEY_ACTIVE_SELECTOR : SHIFT_KEY_INACTIVE_SELECTOR);

		getKey(row, col).setSpecial(text, listener, classSelector);
	}

	private void setMappingKey(int row, int col, int mappingId) {
		String text;
		OnClickListener listener;

		switch (mappingId) {
		case ABC_MAPPING:
			text = "ABC";
			listener = new OnClickListener() {
				@Override
				public void onClick() {
					setLowerLetterMapping();
				}
			};
			break;
		case NUMERIC_MAPPING:
			text = "123";
			listener = new OnClickListener() {
				@Override
				public void onClick() {
					setNumericMapping();
				}
			};
			break;
		case SYMBOL_MAPPING:
			text = "#+=";
			listener = new OnClickListener() {
				@Override
				public void onClick() {
					setSymbolMapping();
				}
			};
			break;
		default:
			throw new IllegalArgumentException();
		}

		getKey(row, col).setSpecial(text, listener, SWITCH_MAPPING_KEY_SELECTOR);
	}

	private void setSpecialKey(int row, int col) {
		if (this.specialKeyListener == null) {
			getKey(row, col).setBlank();
		} else {
			getKey(row, col).setSpecial(this.specialKeyText, this.specialKeyListener, SPECIAL_KEY_SELECTOR);
		}
	}

	private void add(Key key, Row row, int colIndex, int colspan) {
		row.cells.add(new Cell(key, colIndex, colspan));
		super.add(key);
	}

	@Override
	public Rectangle validateContent(Style style, Rectangle bounds) {
		int widthHint = bounds.getWidth();
		int heightHint = bounds.getHeight();

		int length = getWidgetsCount();
		if (length != 0) {
			boolean computeWidth = widthHint == MWT.NONE;
			boolean computeHeight = heightHint == MWT.NONE;

			int cellWidth;
			int maxRowLength = 0;
			if (computeWidth) {
				cellWidth = MWT.NONE;
			} else {
				for (Row row : this.rows) {
					maxRowLength = Math.max(maxRowLength, row.length);
				}
				cellWidth = widthHint / maxRowLength;
			}

			int cellHeight = computeHeight ? MWT.NONE : heightHint / this.rows.length;

			int maxCellWidth = 0;
			int maxCellHeight = 0;

			for (Row row : this.rows) {
				for (Cell cell : row.cells) {
					cell.key.validate(cellWidth * cell.colspan, cellHeight);
					maxCellWidth = Math.max(maxCellWidth, cell.key.getPreferredWidth() / cell.colspan);
					maxCellHeight = Math.max(maxCellHeight, cell.key.getPreferredHeight());
				}
			}

			// Compute composite preferred size if necessary.
			if (computeWidth) {
				widthHint = maxCellWidth * maxRowLength;
			}
			if (computeHeight) {
				heightHint = maxCellHeight * this.rows.length;
			}
		}

		// Set composite preferred size.
		return new Rectangle(0, 0, widthHint, heightHint);
	}

	@Override
	protected void setBoundsContent(Rectangle bounds) {
		int x = bounds.getX();
		int y = bounds.getY();
		int width = bounds.getWidth();
		int height = bounds.getHeight();

		// TODO Try to merge with validate.
		int length = getWidgetsCount();
		if (length == 0) {
			return;
		}

		int cellHeight = height / this.rows.length;
		int maxRowLength = 0;
		for (Row row : this.rows) {
			maxRowLength = Math.max(maxRowLength, row.length);
		}
		int cellWidth = width / maxRowLength;

		int rowY = y;
		for (Row row : this.rows) {
			int rowWidth = row.length * cellWidth;
			int rowX = x + ((width - rowWidth) >> 1);

			for (Cell cell : row.cells) {
				cell.key.setBounds(rowX + cell.startColumn * cellWidth, rowY, cell.colspan * cellWidth, cellHeight);
			}
			rowY += cellHeight;
		}
	}
}
