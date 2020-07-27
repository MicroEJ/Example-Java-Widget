/*
 * Copyright 2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package com.microej.demo.widget.common;

import com.microej.demo.widget.button.ButtonPage;
import com.microej.demo.widget.checkbox.CheckboxPage;
import com.microej.demo.widget.dock.DockPage;
import com.microej.demo.widget.grid.GridPage;
import com.microej.demo.widget.indeterminateprogressbar.IndeterminateProgressBarPage;
import com.microej.demo.widget.label.LabelPage;
import com.microej.demo.widget.list.ListPage;
import com.microej.demo.widget.progressbar.ProgressBarPage;
import com.microej.demo.widget.radiobutton.RadioButtonPage;
import com.microej.demo.widget.scrollablelist.ScrollableListPage;
import com.microej.demo.widget.split.SplitPage;

/**
 * List of the known pages.
 */
public class Pages {

	private static final Page[] ALL_PAGES = { //
			new LabelPage(), //
			new ButtonPage(), //
			new CheckboxPage(), //
			new RadioButtonPage(), //
			new ProgressBarPage(), //
			new IndeterminateProgressBarPage(), //
			new ListPage(), //
			new GridPage(), //
			new DockPage(), //
			new SplitPage(), //
			new ScrollableListPage() //
	};

	private Pages() {
	}

	/**
	 * Returns the number of pages
	 *
	 * @return the number of pages.
	 */
	public static int getNumPages() {
		return ALL_PAGES.length;
	}

	/**
	 * Returns the page at the given index.
	 *
	 * @param index
	 *            the index of the page to get.
	 * @return the page at the given index.
	 */
	public static Page getPage(int index) {
		return ALL_PAGES[index];
	}
}
