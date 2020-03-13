/*
 * Copyright 2016-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.keyboard;

import java.util.ArrayList;
import java.util.List;

import com.microej.demo.widget.style.ClassSelectors;

import ej.mwt.Container;
import ej.mwt.Widget;
import ej.mwt.util.Size;
import ej.service.ServiceFactory;
import ej.widget.listener.OnClickListener;
import ej.widget.util.ControlCharacters;

/**
 * Represents a virtual keyboard
 */
public class Keyboard extends Container {

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
	private final ej.widget.util.Keyboard keyboard;
	private Layout[] layouts;
	private String specialKeyText;
	private OnClickListener specialKeyListener;

	/**
	 * Constructor
	 */
	public Keyboard() {
		this.keyboard = ServiceFactory.getService(ej.widget.util.Keyboard.class);
		this.rows = new Row[4];
		this.layouts = null;
		createKeys();
	}

	@Override
	protected void onShown() {
		super.onShown();
		this.keyboard.addToSystemPool();
	}

	@Override
	protected void onHidden() {
		super.onHidden();
		this.keyboard.removeFromSystemPool();
	}

	/**
	 * Sets the layouts
	 *
	 * @param layouts
	 *            the four keyboard layouts to use
	 */
	public void setLayouts(Layout[] layouts) {
		this.layouts = layouts;
		setLowerCaseMapping();
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
		setSpecialKey(3, 4);
	}

	private void createKeys() {
		// create rows
		this.rows[0] = new Row(10);
		this.rows[1] = new Row(10);
		this.rows[2] = new Row(10);
		this.rows[3] = new Row(10);

		// fill first and second row
		for (int i = 0; i < 10; i++) {
			add(createKey(), this.rows[0], i, 1);
			add(createKey(), this.rows[1], i, 1);
		}

		// fill third row
		add(createKey(), this.rows[2], 0, 1);
		for (int i = 1; i < 8; i++) {
			add(createKey(), this.rows[2], i, 1);
		}
		add(createKey(), this.rows[2], 8, 2);

		// fill fourth row
		add(createKey(), this.rows[3], 0, 1);
		add(createKey(), this.rows[3], 1, 1);
		add(createKey(), this.rows[3], 2, 5);
		add(createKey(), this.rows[3], 7, 1);
		add(createKey(), this.rows[3], 8, 2);
	}

	private Key createKey() {
		return new Key(this.keyboard);
	}

	private void setLowerCaseMapping() {
		setMapping(this.layouts[0]);

		setShiftKey(2, 0, false);
		setMappingKey(3, 0, Mapping.NUMERIC);
	}

	private void setUpperCaseMapping() {
		setMapping(this.layouts[1]);

		setShiftKey(2, 0, true);
		setMappingKey(3, 0, Mapping.NUMERIC);
	}

	private void setNumericMapping() {
		setMapping(this.layouts[2]);

		setMappingKey(2, 0, Mapping.SYMBOL);
		setMappingKey(3, 0, Mapping.ABC);
	}

	private void setSymbolMapping() {
		setMapping(this.layouts[3]);

		setMappingKey(2, 0, Mapping.NUMERIC);
		setMappingKey(3, 0, Mapping.ABC);
	}

	private void setMapping(Layout layout) {
		// first row
		final String firstRowChars = layout.getFirstRow();
		for (int i = 0; i < 10; i++) {
			setStandardKey(0, i, firstRowChars.charAt(i));
		}

		// second row
		final String secondRowChars = layout.getSecondRow();
		for (int i = 0; i < 10; i++) {
			setStandardKey(1, i, secondRowChars.charAt(i));
		}

		// third row
		final String thirdRowChars = layout.getThirdRow();
		for (int i = 0; i < 7; i++) {
			setStandardKey(2, i + 1, thirdRowChars.charAt(i));
		}
		setBackspaceKey(2, 8);

		// fourth row
		setBlankKey(3, 1);
		setSpaceKey(3, 2);
		setBlankKey(3, 3);
		setSpecialKey(3, 4);
	}

	private Key getKey(int row, int col) {
		return this.rows[row].getKey(col);
	}

	private void setBlankKey(int row, int col) {
		getKey(row, col).setBlank();
	}

	private void setStandardKey(int row, int col, char character) {
		if (character == '\00') {
			getKey(row, col).setBlank();
		} else {
			getKey(row, col).setStandard(character);
		}
	}

	private void setSpaceKey(int row, int col) {
		getKey(row, col).setStandard(ControlCharacters.SPACE, ClassSelectors.SPACE_KEY_SELECTOR);
	}

	private void setBackspaceKey(int row, int col) {
		getKey(row, col).setStandard(ControlCharacters.BACK_SPACE, ClassSelectors.BACKSPACE_KEY_SELECTOR);
	}

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
		String classSelector = (active ? ClassSelectors.SHIFT_KEY_ACTIVE_SELECTOR
				: ClassSelectors.SHIFT_KEY_INACTIVE_SELECTOR);

		getKey(row, col).setSpecial(text, listener, classSelector);
	}

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
		getKey(row, col).setSpecial(text, listener, ClassSelectors.SWITCH_MAPPING_KEY_SELECTOR);
	}

	private void setSpecialKey(int row, int col) {
		if (this.specialKeyListener == null) {
			getKey(row, col).setBlank();
		} else {
			getKey(row, col).setSpecial(this.specialKeyText, this.specialKeyListener,
					ClassSelectors.SPECIAL_KEY_SELECTOR);
		}
	}

	private void add(Key key, Row row, int colIndex, int colspan) {
		row.cells.add(new Cell(key, colIndex, colspan));
		super.add(key);
	}

	@Override
	public void computeContentOptimalSize(Size availableSize) {
		int widthHint = availableSize.getWidth();
		int heightHint = availableSize.getHeight();

		int length = getChildrenCount();
		if (length != 0) {
			boolean computeWidth = widthHint == Widget.NO_CONSTRAINT;
			boolean computeHeight = heightHint == Widget.NO_CONSTRAINT;

			int cellWidth;
			int maxRowLength = 0;
			if (computeWidth) {
				cellWidth = Widget.NO_CONSTRAINT;
			} else {
				for (Row row : this.rows) {
					maxRowLength = Math.max(maxRowLength, row.length);
				}
				cellWidth = widthHint / maxRowLength;
			}

			int cellHeight = computeHeight ? Widget.NO_CONSTRAINT : heightHint / this.rows.length;

			int maxCellWidth = 0;
			int maxCellHeight = 0;

			for (Row row : this.rows) {
				for (Cell cell : row.cells) {
					computeChildOptimalSize(cell.key, cellWidth * cell.colspan, cellHeight);
					maxCellWidth = Math.max(maxCellWidth, cell.key.getWidth() / cell.colspan);
					maxCellHeight = Math.max(maxCellHeight, cell.key.getHeight());
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
		availableSize.setSize(widthHint, heightHint);
	}

	@Override
	protected void layOutChildren(int contentWidth, int contentHeight) {
		int length = getChildrenCount();
		if (length == 0) {
			return;
		}

		int cellHeight = contentHeight / this.rows.length;
		int maxRowLength = 0;
		for (Row row : this.rows) {
			maxRowLength = Math.max(maxRowLength, row.length);
		}
		int cellWidth = contentWidth / maxRowLength;

		int rowY = 0;
		for (Row row : this.rows) {
			int rowWidth = row.length * cellWidth;
			int rowX = ((contentWidth - rowWidth) >> 1);

			for (Cell cell : row.cells) {
				layOutChild(cell.key, rowX + cell.startColumn * cellWidth, rowY, cell.colspan * cellWidth, cellHeight);
			}
			rowY += cellHeight;
		}
	}
}
