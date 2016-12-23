/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package ej.widget.keyboard.azerty;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.mwt.MWT;
import ej.mwt.Widget;
import ej.style.Style;
import ej.style.container.Rectangle;
import ej.widget.StyledComposite;
import ej.widget.composed.ButtonWrapper;
import ej.widget.listener.OnClickListener;
import ej.widget.util.ControlCharacters;

/** IPR start **/

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

		final int startColumn;
		final int colspan;

		Cell(int startColumn, int colspan) {
			this.startColumn = startColumn;
			this.colspan = colspan;
		}
	}

	private static class Row {

		final int length;
		final Map<Widget, Cell> widgets;

		Row(int length) {
			this.length = length;
			this.widgets = new HashMap<>(length);
		}
	}

	private final Row[] rows;
	private final ej.microui.event.generator.Keyboard keyboard;
	private String specialKeyText;
	private OnClickListener specialKeyListener;

	public Keyboard() {
		this.keyboard = ServiceLoaderFactory.getServiceLoader().getService(ej.microui.event.generator.Keyboard.class);
		this.rows = new Row[4];
		setLowerLetterMapping();
	}

	private void setLowerLetterMapping() {
		removeAllWidgets();

		Row firstRow = new Row(10);
		add(new StandardKey('a', this.keyboard), firstRow, 0, 1);
		add(new StandardKey('z', this.keyboard), firstRow, 1, 1);
		add(new StandardKey('e', this.keyboard), firstRow, 2, 1);
		add(new StandardKey('r', this.keyboard), firstRow, 3, 1);
		add(new StandardKey('t', this.keyboard), firstRow, 4, 1);
		add(new StandardKey('y', this.keyboard), firstRow, 5, 1);
		add(new StandardKey('u', this.keyboard), firstRow, 6, 1);
		add(new StandardKey('i', this.keyboard), firstRow, 7, 1);
		add(new StandardKey('o', this.keyboard), firstRow, 8, 1);
		add(new StandardKey('p', this.keyboard), firstRow, 9, 1);
		this.rows[0] = firstRow;

		Row secondRow = new Row(10);
		add(new StandardKey('q', this.keyboard), secondRow, 0, 1);
		add(new StandardKey('s', this.keyboard), secondRow, 1, 1);
		add(new StandardKey('d', this.keyboard), secondRow, 2, 1);
		add(new StandardKey('f', this.keyboard), secondRow, 3, 1);
		add(new StandardKey('g', this.keyboard), secondRow, 4, 1);
		add(new StandardKey('h', this.keyboard), secondRow, 5, 1);
		add(new StandardKey('j', this.keyboard), secondRow, 6, 1);
		add(new StandardKey('k', this.keyboard), secondRow, 7, 1);
		add(new StandardKey('l', this.keyboard), secondRow, 8, 1);
		add(new StandardKey('m', this.keyboard), secondRow, 9, 1);
		this.rows[1] = secondRow;

		Row thirdRow = new Row(10);
		add(newShiftKey(false), thirdRow, 0, 1);
		add(new StandardKey('w', this.keyboard), thirdRow, 1, 1);
		add(new StandardKey('x', this.keyboard), thirdRow, 2, 1);
		add(new StandardKey('c', this.keyboard), thirdRow, 3, 1);
		add(new StandardKey('v', this.keyboard), thirdRow, 4, 1);
		add(new StandardKey('b', this.keyboard), thirdRow, 5, 1);
		add(new StandardKey('n', this.keyboard), thirdRow, 6, 1);
		add(newBackspaceKey(), thirdRow, 8, 2);
		this.rows[2] = thirdRow;

		Row fourthRow = new Row(10);
		add(newSwitchMappingKey(NUMERIC_MAPPING), fourthRow, 0, 2);
		add(newSpaceKey(), fourthRow, 2, 5);
		addSpecialKey(fourthRow, 7, 3);
		this.rows[3] = fourthRow;

		revalidate();
	}

	private void setUpperLetterMapping() {
		removeAllWidgets();

		Row firstRow = new Row(10);
		add(new StandardKey('A', this.keyboard), firstRow, 0, 1);
		add(new StandardKey('Z', this.keyboard), firstRow, 1, 1);
		add(new StandardKey('E', this.keyboard), firstRow, 2, 1);
		add(new StandardKey('R', this.keyboard), firstRow, 3, 1);
		add(new StandardKey('T', this.keyboard), firstRow, 4, 1);
		add(new StandardKey('Y', this.keyboard), firstRow, 5, 1);
		add(new StandardKey('U', this.keyboard), firstRow, 6, 1);
		add(new StandardKey('I', this.keyboard), firstRow, 7, 1);
		add(new StandardKey('O', this.keyboard), firstRow, 8, 1);
		add(new StandardKey('P', this.keyboard), firstRow, 9, 1);
		this.rows[0] = firstRow;

		Row secondRow = new Row(10);
		add(new StandardKey('Q', this.keyboard), secondRow, 0, 1);
		add(new StandardKey('S', this.keyboard), secondRow, 1, 1);
		add(new StandardKey('D', this.keyboard), secondRow, 2, 1);
		add(new StandardKey('F', this.keyboard), secondRow, 3, 1);
		add(new StandardKey('G', this.keyboard), secondRow, 4, 1);
		add(new StandardKey('H', this.keyboard), secondRow, 5, 1);
		add(new StandardKey('J', this.keyboard), secondRow, 6, 1);
		add(new StandardKey('K', this.keyboard), secondRow, 7, 1);
		add(new StandardKey('L', this.keyboard), secondRow, 8, 1);
		add(new StandardKey('M', this.keyboard), secondRow, 9, 1);
		this.rows[1] = secondRow;

		Row thirdRow = new Row(10);
		add(newShiftKey(true), thirdRow, 0, 1);
		add(new StandardKey('W', this.keyboard), thirdRow, 1, 1);
		add(new StandardKey('X', this.keyboard), thirdRow, 2, 1);
		add(new StandardKey('C', this.keyboard), thirdRow, 3, 1);
		add(new StandardKey('V', this.keyboard), thirdRow, 4, 1);
		add(new StandardKey('B', this.keyboard), thirdRow, 5, 1);
		add(new StandardKey('N', this.keyboard), thirdRow, 6, 1);
		add(newBackspaceKey(), thirdRow, 8, 2);
		this.rows[2] = thirdRow;

		Row fourthRow = new Row(10);
		add(newSwitchMappingKey(NUMERIC_MAPPING), fourthRow, 0, 2);
		add(newSpaceKey(), fourthRow, 2, 5);
		addSpecialKey(fourthRow, 7, 3);
		this.rows[3] = fourthRow;

		revalidate();
	}

	private void setNumericMapping() {
		removeAllWidgets();

		Row firstRow = new Row(10);
		add(new StandardKey('1', this.keyboard), firstRow, 0, 1);
		add(new StandardKey('2', this.keyboard), firstRow, 1, 1);
		add(new StandardKey('3', this.keyboard), firstRow, 2, 1);
		add(new StandardKey('4', this.keyboard), firstRow, 3, 1);
		add(new StandardKey('5', this.keyboard), firstRow, 4, 1);
		add(new StandardKey('6', this.keyboard), firstRow, 5, 1);
		add(new StandardKey('7', this.keyboard), firstRow, 6, 1);
		add(new StandardKey('8', this.keyboard), firstRow, 7, 1);
		add(new StandardKey('9', this.keyboard), firstRow, 8, 1);
		add(new StandardKey('0', this.keyboard), firstRow, 9, 1);
		this.rows[0] = firstRow;

		Row secondRow = new Row(10);
		add(new StandardKey('-', this.keyboard), secondRow, 0, 1);
		add(new StandardKey('/', this.keyboard), secondRow, 1, 1);
		add(new StandardKey(':', this.keyboard), secondRow, 2, 1);
		add(new StandardKey(';', this.keyboard), secondRow, 3, 1);
		add(new StandardKey('(', this.keyboard), secondRow, 4, 1);
		add(new StandardKey(')', this.keyboard), secondRow, 5, 1);
		add(new StandardKey('$', this.keyboard), secondRow, 6, 1);
		add(new StandardKey('&', this.keyboard), secondRow, 7, 1);
		add(new StandardKey('@', this.keyboard), secondRow, 8, 1);
		add(new StandardKey('"', this.keyboard), secondRow, 9, 1);
		this.rows[1] = secondRow;

		Row thirdRow = new Row(9);
		add(newSwitchMappingKey(SYMBOL_MAPPING), thirdRow, 0, 2);
		add(new StandardKey('.', this.keyboard), thirdRow, 2, 1);
		add(new StandardKey(',', this.keyboard), thirdRow, 3, 1);
		add(new StandardKey('?', this.keyboard), thirdRow, 4, 1);
		add(new StandardKey('!', this.keyboard), thirdRow, 5, 1);
		add(new StandardKey('\'', this.keyboard), thirdRow, 6, 1);
		add(newBackspaceKey(), thirdRow, 7, 2);
		this.rows[2] = thirdRow;

		Row fourthRow = new Row(10);
		add(newSwitchMappingKey(ABC_MAPPING), fourthRow, 0, 2);
		add(newSpaceKey(), fourthRow, 2, 5);
		addSpecialKey(fourthRow, 7, 3);
		this.rows[3] = fourthRow;

		revalidate();
	}

	private void setSymbolMapping() {
		removeAllWidgets();

		Row firstRow = new Row(10);
		add(new StandardKey('[', this.keyboard), firstRow, 0, 1);
		add(new StandardKey(']', this.keyboard), firstRow, 1, 1);
		add(new StandardKey('{', this.keyboard), firstRow, 2, 1);
		add(new StandardKey('}', this.keyboard), firstRow, 3, 1);
		add(new StandardKey('#', this.keyboard), firstRow, 4, 1);
		add(new StandardKey('%', this.keyboard), firstRow, 5, 1);
		add(new StandardKey('^', this.keyboard), firstRow, 6, 1);
		add(new StandardKey('*', this.keyboard), firstRow, 7, 1);
		add(new StandardKey('+', this.keyboard), firstRow, 8, 1);
		add(new StandardKey('=', this.keyboard), firstRow, 9, 1);
		this.rows[0] = firstRow;

		Row secondRow = new Row(10);
		add(new StandardKey('_', this.keyboard), secondRow, 0, 1);
		add(new StandardKey('\\', this.keyboard), secondRow, 1, 1);
		add(new StandardKey('|', this.keyboard), secondRow, 2, 1);
		add(new StandardKey('~', this.keyboard), secondRow, 3, 1);
		add(new StandardKey('<', this.keyboard), secondRow, 4, 1);
		add(new StandardKey('>', this.keyboard), secondRow, 5, 1);
		add(new StandardKey('€', this.keyboard), secondRow, 6, 1);
		add(new StandardKey('£', this.keyboard), secondRow, 7, 1);
		add(new StandardKey('¥', this.keyboard), secondRow, 8, 1);
		add(new StandardKey('\u25cf', this.keyboard), secondRow, 9, 1);
		this.rows[1] = secondRow;

		Row thirdRow = new Row(9);
		add(newSwitchMappingKey(NUMERIC_MAPPING), thirdRow, 0, 2);
		add(new StandardKey('.', this.keyboard), thirdRow, 2, 1);
		add(new StandardKey(',', this.keyboard), thirdRow, 3, 1);
		add(new StandardKey('?', this.keyboard), thirdRow, 4, 1);
		add(new StandardKey('!', this.keyboard), thirdRow, 5, 1);
		add(new StandardKey('\'', this.keyboard), thirdRow, 6, 1);
		add(newBackspaceKey(), thirdRow, 7, 2);
		this.rows[2] = thirdRow;

		Row fourthRow = new Row(10);
		add(newSwitchMappingKey(ABC_MAPPING), fourthRow, 0, 2);
		add(newSpaceKey(), fourthRow, 2, 5);
		addSpecialKey(fourthRow, 7, 3);
		this.rows[3] = fourthRow;

		revalidate();
	}

	/**
	 * Sets the specialKey.
	 *
	 * @param specialKey
	 *            the specialKey to set.
	 */
	public void setSpecialKey(String text, OnClickListener listener) {
		this.specialKeyText = text;
		this.specialKeyListener = listener;
		setLowerLetterMapping();
	}

	private void addSpecialKey(Row row, int colIndex, int colspan) {
		if (this.specialKeyListener != null) {
			SpecialKey specialKey = new SpecialKey(this.specialKeyText, this.specialKeyListener);
			specialKey.addClassSelector(SPECIAL_KEY_SELECTOR);
			add(specialKey, row, colIndex, colspan);
		}
	}

	private ButtonWrapper newSpaceKey() {
		StandardKey spaceKey = new StandardKey(ControlCharacters.SPACE, this.keyboard);
		spaceKey.addClassSelector(SPACE_KEY_SELECTOR);
		return spaceKey;
	}

	private ButtonWrapper newBackspaceKey() {
		StandardKey backspaceKey = new StandardKey(ControlCharacters.BACK_SPACE, this.keyboard);
		backspaceKey.addClassSelector(BACKSPACE_KEY_SELECTOR);
		return backspaceKey;
	}

	private ButtonWrapper newSwitchMappingKey(int mappingId) {
		SpecialKey switchMappingKey;

		switch (mappingId) {
		case ABC_MAPPING:
			switchMappingKey = new SpecialKey("ABC", new OnClickListener() {

				@Override
				public void onClick() {
					setLowerLetterMapping();
				}
			});
			break;
		case NUMERIC_MAPPING:
			switchMappingKey = new SpecialKey("123", new OnClickListener() {

				@Override
				public void onClick() {
					setNumericMapping();
				}
			});
			break;
		case SYMBOL_MAPPING:
			switchMappingKey = new SpecialKey("#+=", new OnClickListener() {

				@Override
				public void onClick() {
					setSymbolMapping();
				}
			});
			break;
		default:
			throw new IllegalArgumentException();
		}

		switchMappingKey.addClassSelector(SWITCH_MAPPING_KEY_SELECTOR);
		return switchMappingKey;
	}

	private ButtonWrapper newShiftKey(boolean active) {
		if (active) {
			SpecialKey shiftKey = new SpecialKey(String.valueOf(ControlCharacters.SHIFT_IN), new OnClickListener() {

				@Override
				public void onClick() {
					setLowerLetterMapping();
				}
			});
			shiftKey.addClassSelector(SHIFT_KEY_ACTIVE_SELECTOR);
			return shiftKey;
		} else {
			SpecialKey shiftKey = new SpecialKey(String.valueOf(ControlCharacters.SHIFT_IN), new OnClickListener() {

				@Override
				public void onClick() {
					setUpperLetterMapping();
				}
			});
			shiftKey.addClassSelector(SHIFT_KEY_INACTIVE_SELECTOR);
			return shiftKey;
		}
	}

	private void add(Widget widget, Row row, int colIndex, int colspan) {
		row.widgets.put(widget, new Cell(colIndex, colspan));
		super.add(widget);
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
				for (Entry<Widget, Cell> cellEntry : row.widgets.entrySet()) {
					Widget widget = cellEntry.getKey();
					int colspan = cellEntry.getValue().colspan;
					widget.validate(cellWidth * colspan, cellHeight);
					maxCellWidth = Math.max(maxCellWidth, widget.getPreferredWidth() / colspan);
					maxCellHeight = Math.max(maxCellHeight, widget.getPreferredHeight());
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

			for (Entry<Widget, Cell> cellEntry : row.widgets.entrySet()) {
				Cell cell = cellEntry.getValue();
				cellEntry.getKey().setBounds(rowX + cell.startColumn * cellWidth, rowY, cell.colspan * cellWidth,
						cellHeight);
			}
			rowY += cellHeight;
		}
	}

}

/** IPR end **/
