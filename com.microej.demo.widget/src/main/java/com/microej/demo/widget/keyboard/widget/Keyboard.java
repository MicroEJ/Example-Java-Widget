/*
 * Copyright 2016-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.keyboard.widget;

import java.util.ArrayList;
import java.util.List;

import ej.bon.Timer;
import ej.mwt.Container;
import ej.mwt.Widget;
import ej.mwt.util.Size;
import ej.widget.basic.OnClickListener;

/**
 * Represents a virtual keyboard widget.
 */
public class Keyboard extends Container {

	/**
	 * Keyboard layout mapping texts.
	 */
	enum Mapping {
		ABC("ABC"), NUMERIC("123"), SYMBOL("#+="); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		String string;

		Mapping(String string) {
			this.string = string;
		}

		public String getString() {
			return this.string;
		}
	}

	/**
	 * Virtual position on keyboard grid.
	 */
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

	/**
	 * Keyboard row.
	 */
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

	/** Keyboard row size. */
	private static final int ROW_SIZE = 10;
	/** Keyboard rows. */
	private static final int ROWS_COUNT = 4;
	/** First keyboard row. */
	private static final int FIRST_ROW = 0;
	/** Second keyboard row. */
	private static final int SECOND_ROW = 1;
	/** Third keyboard row. */
	private static final int THIRD_ROW = 2;
	/** Fourth keyboard row. */
	private static final int FOURTH_ROW = 3;

	private static final int BLANK_KEY_COLUMN = 8;

	private static final int LEFT_KEY_COLUMN = 1;
	private static final int SPACE_KEY_COLUMN = 2;
	private static final int RIGHT_KEY_COLUMN = 3;
	private static final int SPECIAL_KEY_COLUMN = 4;

	/*
	 * Number of cells that should be used for the space key. When adjusting this, the same number of cells afterward
	 * should be set to 0 in createKeys().
	 */
	private static final int SPACE_KEY_CELL_WRAPPING_SIZE = 5;

	private final Timer timer;
	/**
	 * Keyboard rows.
	 */
	private final Row[] rows;
	/** Keyboard layout. */
	private final KeyboardLayout layout;
	/** Keyboard events generator. */
	private final KeyboardEventGenerator keyboardEvents;

	private final int spaceKeySelector;
	private final int shiftKeyInactiveSelector;
	private final int shiftKeyActiveSelector;
	private final int switchMappingKeySelector;

	/**
	 * Creates a virtual keyboard.
	 *
	 * @param timer
	 *            the timer used to repeat events when the user keeps pressing a key
	 * @param spaceKeySelector
	 *            the selector id to set for the space key.
	 * @param shiftKeyInactiveSelector
	 *            the selector id to set for the shift key when inactive.
	 * @param shiftKeyActiveSelector
	 *            the selector id to set for the shift key when active.
	 * @param switchMappingKeySelector
	 *            the selector id to set for the switch mapping key.
	 */
	public Keyboard(Timer timer, int spaceKeySelector, int shiftKeyInactiveSelector, int shiftKeyActiveSelector,
			int switchMappingKeySelector) {
		this.timer = timer;
		this.spaceKeySelector = spaceKeySelector;
		this.shiftKeyInactiveSelector = shiftKeyInactiveSelector;
		this.shiftKeyActiveSelector = shiftKeyActiveSelector;
		this.switchMappingKeySelector = switchMappingKeySelector;
		this.keyboardEvents = new KeyboardEventGenerator();
		this.rows = new Row[ROWS_COUNT];
		// create rows
		for (int i = 0; i < this.rows.length; i++) {
			this.rows[i] = new Row(ROW_SIZE);
		}
		this.layout = new KeyboardLayout();
		createKeys();
		setLowerCaseMapping();
	}

	@Override
	protected void onShown() {
		super.onShown();
		this.keyboardEvents.addToSystemPool();
	}

	@Override
	protected void onHidden() {
		super.onHidden();
		this.keyboardEvents.removeFromSystemPool();
	}

	/**
	 * Sets the special key.
	 *
	 * @param text
	 *            the text to draw on the key
	 * @param keySelector
	 *            key color selector
	 * @param listener
	 *            the action to execute when the key is pressed
	 *
	 */
	public void setSpecialKey(String text, int keySelector, OnClickListener listener) {
		getKey(FOURTH_ROW, SPECIAL_KEY_COLUMN).setSpecial(text, listener, keySelector);
	}

	/**
	 * Create keyboard keys.
	 */
	private void createKeys() {
		assert this.rows != null;

		// fill first row and second row
		for (int rowIndex = 0; rowIndex < 2; rowIndex++) {
			createFullRow(rowIndex, ROW_SIZE, new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 });
		}

		// fill third row
		createFullRow(THIRD_ROW, ROW_SIZE - 1, new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 2 });

		// fill fourth row
		createFullRow(FOURTH_ROW, ROW_SIZE - 1, new int[] { 1, 1, SPACE_KEY_CELL_WRAPPING_SIZE, 0, 0, 0, 0, 1, 2 });
	}

	/**
	 * Create one keyboard row.
	 *
	 * @param rowIndex
	 *            row index
	 * @param size
	 *            how many keys are created
	 * @param cellWrap
	 *            array with cell wrapping for each key
	 */
	private void createFullRow(int rowIndex, int size, int[] cellWrap) {
		Row row = this.rows[rowIndex];
		assert row != null;

		KeyboardEventGenerator keyboardEvents = this.keyboardEvents;
		for (int i = 0; i < size; i++) {
			int colspan = cellWrap[i];
			if (colspan > 0) {
				add(new Key(keyboardEvents, this.timer), row, i, colspan);
			}
		}
	}

	/**
	 * Add a key to the keyboard.
	 *
	 * @param key
	 *            key to be added
	 * @param row
	 *            row
	 * @param colIndex
	 *            column
	 * @param colspan
	 *            how many cells it extends
	 */
	private void add(Key key, Row row, int colIndex, int colspan) {
		row.cells.add(new Cell(key, colIndex, colspan));
		super.addChild(key);
	}

	/**
	 * Lower case mapping.
	 */
	private void setLowerCaseMapping() {
		setMapping(KeyboardLayout.LOWER_CASE_LAYOUT_INDEX);

		setShiftKey(THIRD_ROW, 0, false);
		setMappingKey(FOURTH_ROW, 0, Mapping.NUMERIC);
	}

	/**
	 * Upper case mapping.
	 */
	private void setUpperCaseMapping() {
		setMapping(KeyboardLayout.UPPER_CASE_LAYOUT_INDEX);

		setShiftKey(THIRD_ROW, 0, true);
		setMappingKey(FOURTH_ROW, 0, Mapping.NUMERIC);
	}

	/**
	 * Numeric mapping.
	 */
	private void setNumericMapping() {
		setMapping(KeyboardLayout.NUMERIC_LAYOUT_INDEX);

		setMappingKey(THIRD_ROW, 0, Mapping.SYMBOL);
		setMappingKey(FOURTH_ROW, 0, Mapping.ABC);
	}

	/**
	 * Symbol mapping.
	 */
	private void setSymbolMapping() {
		setMapping(KeyboardLayout.SYMBOL_LAYOUT_INDEX);

		setMappingKey(THIRD_ROW, 0, Mapping.NUMERIC);
		setMappingKey(FOURTH_ROW, 0, Mapping.ABC);
	}

	/**
	 * Change mapping.
	 *
	 * @param layoutId
	 *            new mapping to be set
	 */
	private void setMapping(int layoutId) {
		KeyboardLayout layout = this.layout;
		layout.setCurrentLayout(layoutId);
		// first row
		final String firstRowChars = layout.getFirstRow();
		for (int i = 0; i < ROW_SIZE; i++) {
			setStandardKey(FIRST_ROW, i, firstRowChars.charAt(i));
		}

		// second row
		final String secondRowChars = layout.getSecondRow();
		for (int i = 0; i < ROW_SIZE; i++) {
			setStandardKey(SECOND_ROW, i, secondRowChars.charAt(i));
		}

		// third row
		final String thirdRowChars = layout.getThirdRow();
		for (int i = 0; i < thirdRowChars.length(); i++) {
			// i + 1 because the key in the first column is the shift key.
			setStandardKey(THIRD_ROW, i + 1, thirdRowChars.charAt(i));
		}
		setBlankKey(THIRD_ROW, BLANK_KEY_COLUMN);

		// fourth row
		setStandardKey(FOURTH_ROW, LEFT_KEY_COLUMN, ControlCharacters.VK_LEFT);
		setSpaceKey(FOURTH_ROW, SPACE_KEY_COLUMN);
		setStandardKey(FOURTH_ROW, RIGHT_KEY_COLUMN, ControlCharacters.VK_RIGHT);
		requestLayOut();
	}

	/**
	 * Get a key at row and column.
	 *
	 * @param row
	 *            row
	 * @param col
	 *            column
	 * @return key at row and column
	 */
	private Key getKey(int row, int col) {
		return this.rows[row].getKey(col);
	}

	/**
	 * Set a standard key at row, column with the attached character key.
	 *
	 * @param row
	 *            row
	 * @param col
	 *            column
	 * @param character
	 *            attached character key
	 */
	private void setStandardKey(int row, int col, char character) {
		if (character == ControlCharacters.NULL) {
			getKey(row, col).setBlank();
		} else {
			getKey(row, col).setStandard(character);
		}
	}

	/**
	 * Set a blank key at row and column.
	 *
	 * @param row
	 *            row
	 * @param col
	 *            column
	 */
	private void setBlankKey(int row, int col) {
		getKey(row, col).setBlank();
	}

	/**
	 * Set space key.
	 *
	 * @param row
	 *            row
	 * @param col
	 *            column
	 */
	private void setSpaceKey(int row, int col) {
		getKey(row, col).setStandard(ControlCharacters.SPACE, this.spaceKeySelector);
	}

	/**
	 * Set shift key.
	 *
	 * @param row
	 *            row
	 * @param col
	 *            column
	 * @param active
	 *            true for lower case mapping, false for upper case mapping
	 */
	private void setShiftKey(int row, int col, final boolean active) {
		OnClickListener listener = new OnClickListener() {
			@Override
			public void onClick() {
				if (active) {
					setLowerCaseMapping();
				} else {
					setUpperCaseMapping();
				}
			}
		};

		String text = String.valueOf(ControlCharacters.SHIFT_IN);
		int classSelector = (active ? this.shiftKeyActiveSelector : this.shiftKeyInactiveSelector);

		getKey(row, col).setSpecial(text, listener, classSelector);
	}

	/**
	 * Set the keyboard to a new mapping.
	 *
	 * @param row
	 *            row
	 * @param col
	 *            column
	 * @param mapping
	 *            desired mapping
	 */
	private void setMappingKey(int row, int col, Mapping mapping) {
		OnClickListener listener;

		switch (mapping) {
		case ABC:
			listener = new OnClickListener() {
				@Override
				public void onClick() {
					setLowerCaseMapping();
				}
			};
			break;
		case NUMERIC:
			listener = new OnClickListener() {
				@Override
				public void onClick() {
					setNumericMapping();
				}
			};
			break;
		case SYMBOL:
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

		String text = mapping.getString();
		getKey(row, col).setSpecial(text, listener, this.switchMappingKeySelector);
	}

	@Override
	public void computeContentOptimalSize(Size availableSize) {
		int widthHint = availableSize.getWidth();
		int heightHint = availableSize.getHeight();

		int length = getChildrenCount();
		if (length != 0) {
			boolean widthConstraint = widthHint != Widget.NO_CONSTRAINT;
			boolean heightConstraint = heightHint != Widget.NO_CONSTRAINT;

			int cellWidth;
			// maxRowLength cannot be zero
			int maxRowLength = 1;
			Row[] rows = this.rows;
			if (widthConstraint) {
				for (Row row : rows) {
					maxRowLength = Math.max(maxRowLength, row.length);
				}
				cellWidth = widthHint / maxRowLength;
			} else {
				cellWidth = Widget.NO_CONSTRAINT;
			}

			int cellHeight = heightConstraint ? heightHint / rows.length : Widget.NO_CONSTRAINT;

			int maxCellWidth = 0;
			int maxCellHeight = 0;

			for (Row row : rows) {
				for (Cell cell : row.cells) {
					Key key = cell.key;
					int colspan = cell.colspan;
					computeChildOptimalSize(key, cellWidth * colspan, cellHeight);
					maxCellWidth = Math.max(maxCellWidth, key.getWidth() / colspan);
					maxCellHeight = Math.max(maxCellHeight, key.getHeight());
				}
			}

			// Compute composite preferred size.
			widthHint = maxCellWidth * maxRowLength;
			heightHint = maxCellHeight * rows.length;
			availableSize.setSize(widthHint, heightHint);
		}
	}

	@Override
	protected void layOutChildren(int contentWidth, int contentHeight) {
		int length = getChildrenCount();
		if (length == 0) {
			return;
		}

		Row[] rows = this.rows;
		int cellHeight = contentHeight / rows.length;
		int maxRowLength = 1;
		for (Row row : rows) {
			maxRowLength = Math.max(maxRowLength, row.length);
		}
		int cellWidth = contentWidth / maxRowLength;

		int rowY = 0;
		for (Row row : rows) {
			int rowWidth = row.length * cellWidth;
			int rowX = ((contentWidth - rowWidth) >> 1);

			for (Cell cell : row.cells) {
				layOutChild(cell.key, rowX + cell.startColumn * cellWidth, rowY, cell.colspan * cellWidth, cellHeight);
			}
			rowY += cellHeight;
		}
	}

	/**
	 * Return event generator.
	 *
	 * @return event generator
	 */
	public KeyboardEventGenerator getEventGenerator() {
		return this.keyboardEvents;
	}
}
