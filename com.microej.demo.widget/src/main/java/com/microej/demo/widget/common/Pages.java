/*
 * Copyright 2020-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.common;

import com.microej.demo.widget.autoscrolllabel.AutoscrollLabelPage;
import com.microej.demo.widget.barchart.BarChartPage;
import com.microej.demo.widget.bufferedscroll.BufferedScrollPage;
import com.microej.demo.widget.button.ButtonPage;
import com.microej.demo.widget.carousel.CarouselPage;
import com.microej.demo.widget.checkbox.CheckboxPage;
import com.microej.demo.widget.circulardottedprogress.CircularDottedProgressPage;
import com.microej.demo.widget.circularindeterminateprogress.CircularIndeterminateProgressPage;
import com.microej.demo.widget.circularprogress.CircularProgressPage;
import com.microej.demo.widget.dock.DockPage;
import com.microej.demo.widget.grid.GridPage;
import com.microej.demo.widget.imagewidget.ImageWidgetPage;
import com.microej.demo.widget.indeterminateprogressbar.IndeterminateProgressBarPage;
import com.microej.demo.widget.keyboard.KeyboardPage;
import com.microej.demo.widget.label.LabelPage;
import com.microej.demo.widget.linechart.LineChartPage;
import com.microej.demo.widget.list.ListPage;
import com.microej.demo.widget.progressbar.ProgressBarPage;
import com.microej.demo.widget.radiobutton.RadioButtonPage;
import com.microej.demo.widget.scrollablelist.ScrollableListPage;
import com.microej.demo.widget.scrollabletext.ScrollableTextPage;
import com.microej.demo.widget.secretscroll.SecretScrollPage;
import com.microej.demo.widget.selectablelist.SelectableListPage;
import com.microej.demo.widget.sliderwithprogress.SliderWithProgressPage;
import com.microej.demo.widget.sliderwithvalue.SliderWithValuePage;
import com.microej.demo.widget.snapscroll.SnapScrollPage;
import com.microej.demo.widget.split.SplitPage;
import com.microej.demo.widget.toggle.TogglePage;
import com.microej.demo.widget.wheel.WheelPage;

import ej.basictool.ArrayTools;

/**
 * List of the known pages.
 */
public class Pages {

	private static Page[] allPages = { //
			new LabelPage(), //
			new AutoscrollLabelPage(), //
			new ImageWidgetPage(), //
			new SliderWithValuePage(), //
			new SliderWithProgressPage(), //
			new ButtonPage(), //
			new CheckboxPage(), //
			new RadioButtonPage(), //
			new TogglePage(), //
			new ProgressBarPage(), //
			new IndeterminateProgressBarPage(), //
			new KeyboardPage(), //
			new CircularIndeterminateProgressPage(), //
			new CircularDottedProgressPage(), //
			new CircularProgressPage(), //
			new WheelPage(), //
			new CarouselPage(), //
			new ListPage(), //
			new GridPage(), //
			new DockPage(), //
			new SplitPage(), //
			new ScrollableListPage(), //
			new ScrollableTextPage(), //
			new SnapScrollPage(), //
			new SecretScrollPage(), //
			new BufferedScrollPage(), //
			new SelectableListPage(), //
			new LineChartPage(), //
			new BarChartPage(), //
	};

	private Pages() {
		// Prevent instantiation.
	}

	/**
	 * Returns the number of pages.
	 *
	 * @return the number of pages.
	 */
	public static int getNumPages() {
		return allPages.length;
	}

	/**
	 * Returns the page at the given index.
	 *
	 * @param index
	 *            the index of the page to get.
	 * @return the page at the given index.
	 */
	public static Page getPage(int index) {
		Page page = allPages[index];
		assert page != null;
		return page;
	}

	/**
	 * Inserts a page to the list at the given position.
	 *
	 * @param index
	 *            the position at which the page should be inserted, starting from 0
	 * @param page
	 *            the page to insert
	 * @throws IndexOutOfBoundsException
	 *             if the given index is not valid
	 */
	public static void insertPage(int index, Page page) {
		allPages = ArrayTools.insert(allPages, index, page);
	}

}
