/*
 * Copyright 2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.scrollalphabet.widget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.microej.demo.widget.common.scroll.Scroll;
import com.microej.demo.widget.common.scroll.ScrollableList;

import ej.mwt.Widget;
import ej.widget.basic.Button;
import ej.widget.basic.OnClickListener;
import ej.widget.container.LayoutOrientation;
import ej.widget.container.SimpleDock;

/**
 * A scroll widget that can be navigated using an index (alphabet).
 */
public class AlphabetScroll extends SimpleDock {

	private final Scroll main;
	private final Scroll index;

	private boolean animate;

	/**
	 * Creates a scroll widget for a list with given children, of given orientation, indexed based on given
	 * {@link Indexer}.
	 *
	 * @param orientation
	 *            the item list orientation.
	 * @param indexer
	 *            the indexer used to build the index list.
	 * @param children
	 *            the list items.
	 * @see LayoutOrientation
	 */
	public AlphabetScroll(boolean orientation, Indexer indexer, Widget... children) {
		super(!orientation);

		Arrays.sort(children, createIndexerComparator(indexer));
		this.main = createMainScroll(orientation, createMainList(orientation, children));
		this.index = createSideScroll(orientation, createIndexList(orientation, indexer, children));
		this.animate = true;

		setEnabled(true);
		setCenterChild(this.main);
		setLastChild(this.index);
	}

	/**
	 * Configures whether the index view should be displayed before or after content, according to the scroll
	 * orientation.
	 * <p>
	 * Defaults to {@code false}: display the index view after content.
	 *
	 * @param layOutIndexBeforeContent
	 *            {@code true} to display the index view before content, {@code false} to display it after.
	 */
	public void setIndexBeforeContent(boolean layOutIndexBeforeContent) {
		Scroll index = this.index;
		removeChild(index);
		if (layOutIndexBeforeContent) {
			setFirstChild(index);
		} else {
			setLastChild(index);
		}
	}

	/**
	 * Sets whether the scroll jump should be animated or not.
	 * <p>
	 * Defaults to {@code true}: scroll jumps are animated.
	 *
	 * @param animate
	 *            {@code true} to animate scroll jumps, {@code false} to have instantaneous jumps.
	 */
	public void setAnimateOnScroll(boolean animate) {
		this.animate = animate;
	}

	/**
	 * Creates the main scroll view.
	 * <p>
	 * Can be overridden to configure this {@link Scroll} instance.
	 *
	 * @param orientation
	 *            the scroll orientation.
	 * @param mainList
	 *            the scroll content.
	 * @return the scroll instance.
	 */
	protected Scroll createMainScroll(boolean orientation, Widget mainList) {
		return createScrollWithChild(orientation, mainList);
	}

	/**
	 * Creates the index scroll view.<br>
	 * By default, the scroll bar is disabled.
	 * <p>
	 * Can be overridden to configure this {@link Scroll} instance.
	 *
	 * @param orientation
	 *            the scroll orientation.
	 * @param indexList
	 *            the scroll content.
	 * @return the scroll instance.
	 */
	protected Scroll createSideScroll(boolean orientation, Widget indexList) {
		Scroll sideScroll = createScrollWithChild(orientation, indexList);
		sideScroll.showScrollbar(false);
		return sideScroll;
	}

	/**
	 * Creates the comparator used to sort items by indexes.
	 * <p>
	 * Can be overridden to further sort items with same index.<br>
	 * By default, input order is preserved.
	 *
	 * @param indexer
	 *            the indexer.
	 * @return the comparator.
	 */
	protected Comparator<Widget> createIndexerComparator(final Indexer indexer) {
		return new Comparator<Widget>() {
			@Override
			public int compare(Widget w1, Widget w2) {
				return indexer.getIndex(w1).compareTo(indexer.getIndex(w2));
			}
		};
	}

	private Widget createMainList(boolean orientation, Widget... children) {
		ScrollableList list = new ScrollableList(orientation, false);
		for (Widget child : children) {
			assert child != null;
			list.addChild(child);
		}
		return list;
	}

	/**
	 * @param children
	 *            list of the widgets to create an index list of.<br>
	 *            Must be sorted according to the {@code indexer}.
	 */
	private Widget createIndexList(boolean orientation, Indexer indexer, Widget... children) {
		List<String> keys = new ArrayList<>();
		List<Integer> indexes = new ArrayList<>();
		String lastKey = null;
		for (int i = 0, n = children.length; i < n; i++) {
			Widget child = children[i];
			assert child != null;
			String key = indexer.getIndex(child);
			if (!key.equals(lastKey)) {
				keys.add(key);
				indexes.add(Integer.valueOf(i));
				lastKey = key;
			}
		}

		ScrollableList list = new ScrollableList(orientation, true);
		for (int i = 0, n = keys.size(); i < n; i++) {
			String key = keys.get(i);
			final int index = indexes.get(i).intValue();
			Button button = new Button(key);
			button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick() {
					AlphabetScroll sa = AlphabetScroll.this;
					sa.main.scrollToIndex(index, sa.animate);
				}
			});
			list.addChild(button);
		}
		return list;
	}

	private static Scroll createScrollWithChild(boolean orientation, Widget mainList) {
		Scroll scroll = new Scroll(orientation);
		scroll.setChild(mainList);
		return scroll;
	}

	/**
	 * An indexer for {@link AlphabetScroll} items.
	 */
	public interface Indexer {

		/**
		 * Gets the index for given widget.
		 *
		 * @param widget
		 *            the widget.
		 * @return the index.
		 */
		String getIndex(Widget widget);

	}

}
