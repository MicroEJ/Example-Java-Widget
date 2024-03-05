/*
 * Copyright 2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.scrollalphabet;

import java.util.Comparator;

import com.microej.demo.widget.common.Page;
import com.microej.demo.widget.common.scroll.Scroll;
import com.microej.demo.widget.common.scroll.Scrollbar;
import com.microej.demo.widget.scrollalphabet.widget.AlphabetScroll;

import ej.bon.Immutables;
import ej.drawing.ShapePainter.Cap;
import ej.mwt.Widget;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.Style;
import ej.mwt.style.background.RectangularBackground;
import ej.mwt.style.dimension.FixedDimension;
import ej.mwt.style.outline.FlexibleOutline;
import ej.mwt.style.outline.UniformOutline;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.ClassSelector;
import ej.mwt.stylesheet.selector.TypeSelector;
import ej.mwt.stylesheet.selector.UniversalSelector;
import ej.mwt.stylesheet.selector.combinator.ChildCombinator;
import ej.widget.basic.Label;
import ej.widget.color.LightHelper;
import ej.widget.container.LayoutOrientation;

/**
 * Page showing a "scroll alphabet".
 */
public class ScrollAlphabetPage implements Page {

	private static final String PAGE_TITLE = "Alphabet Scroll"; //$NON-NLS-1$
	private static final String COLOR_NAMES_IMMUTABLES_ID = "ColorNames"; //$NON-NLS-1$
	private static final String COLOR_VALUES_IMMUTABLES_ID = "ColorValues"; //$NON-NLS-1$

	private static final int MAIN_SCROLL_STYLE_CLASS = (int) 8185219111359575513L;
	private static final int INDEX_SCROLL_STYLE_CLASS = (int) 8185219111359575514L;
	private static final int INDEX_LIST_STYLE_CLASS = (int) 8185219111359575515L;

	private static final int SCROLLS_PADDING = 5;
	private static final int SCROLLBAR_PADDING = 4;
	private static final int INDEX_ITEMS_WIDTH = 30;

	@Override
	public String getName() {
		return PAGE_TITLE;
	}

	@Override
	public Widget getContentWidget() {
		return new AlphabetScroll(LayoutOrientation.VERTICAL, Item.INDEXER, createItems()) {
			@Override
			protected Scroll createMainScroll(boolean orientation, Widget mainList) {
				Scroll scroll = super.createMainScroll(orientation, mainList);
				scroll.addClassSelector(MAIN_SCROLL_STYLE_CLASS);
				scroll.setScrollBarCaps(Cap.ROUNDED);
				scroll.setScrollbarBeforeContent(true);
				return scroll;
			}

			@Override
			protected Scroll createSideScroll(boolean orientation, Widget sideList) {
				sideList.addClassSelector(INDEX_LIST_STYLE_CLASS);
				Scroll scroll = super.createSideScroll(orientation, sideList);
				scroll.addClassSelector(INDEX_SCROLL_STYLE_CLASS);
				return scroll;
			}

			@Override
			protected Comparator<Widget> createIndexerComparator(Indexer indexer) {
				return new Comparator<Widget>() {
					@Override
					public int compare(Widget w1, Widget w2) {
						return ((Label) w1).getText().compareTo(((Label) w2).getText());
					}
				};
			}
		};
	}

	private Item[] createItems() {
		String[] colorNames = (String[]) Immutables.get(COLOR_NAMES_IMMUTABLES_ID);
		int[] colorValues = (int[]) Immutables.get(COLOR_VALUES_IMMUTABLES_ID);
		assert colorNames.length == colorValues.length;
		int skip = 32; // only pick 1 every skip colors to fit into current heap
		int n = colorNames.length / skip;
		Item[] list = new Item[n];
		for (int i = 0; i < n; i++) {
			String colorName = colorNames[i * skip];
			assert colorName != null;
			list[i] = new Item(colorName, colorValues[i * skip]);
		}
		return list;
	}

	@Override
	public void populateStylesheet(CascadingStylesheet stylesheet) {
		EditableStyle style;

		style = stylesheet.getSelectorStyle(new ClassSelector(MAIN_SCROLL_STYLE_CLASS));
		style.setPadding(new UniformOutline(SCROLLS_PADDING));

		style = stylesheet.getSelectorStyle(new ClassSelector(INDEX_SCROLL_STYLE_CLASS));
		style.setPadding(new FlexibleOutline(SCROLLS_PADDING, 0, SCROLLS_PADDING, 0));

		style = stylesheet.getSelectorStyle(new TypeSelector(Scrollbar.class));
		style.setPadding(new UniformOutline(SCROLLBAR_PADDING));

		style = stylesheet.getSelectorStyle(
				new ChildCombinator(new ClassSelector(INDEX_LIST_STYLE_CLASS), UniversalSelector.UNIVERSAL_SELECTOR));
		style.setDimension(new FixedDimension(INDEX_ITEMS_WIDTH, Widget.NO_CONSTRAINT));
	}

	private static class Item extends Label {

		private static final AlphabetScroll.Indexer INDEXER = new AlphabetScroll.Indexer() {
			@Override
			public String getIndex(Widget widget) {
				if (!(widget instanceof Item)) {
					throw new IllegalArgumentException();
				}
				return ((Item) widget).getText().substring(0, 1);
			}
		};

		private final int backgroundColor;
		private final int foregroundColor;

		public Item(String label, int color) {
			super(label);
			this.backgroundColor = color;
			this.foregroundColor = LightHelper.getMostContrastingColor(color);
		}

		@Override
		public void setStyle(Style previousStyle) {
			EditableStyle style = new EditableStyle();
			style.setBackground(new RectangularBackground(this.backgroundColor));
			style.setBorder(previousStyle.getBorder());
			style.setColor(this.foregroundColor);
			style.setDimension(previousStyle.getDimension());
			style.setFont(previousStyle.getFont());
			style.setHorizontalAlignment(previousStyle.getHorizontalAlignment());
			style.setMargin(previousStyle.getMargin());
			style.setPadding(previousStyle.getPadding());
			style.setVerticalAlignment(previousStyle.getVerticalAlignment());
			super.setStyle(style);
		}

	}

}
