/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.microej.demo.widgets.page;

import com.microej.demo.widgets.style.ClassSelector;

import ej.composite.GridComposite;
import ej.composite.SplitComposite;
import ej.mwt.Widget;
import ej.widget.basic.CircularProgressBar;
import ej.widget.basic.ProgressBar;
import ej.widget.basic.picto.PictoProgressBar;

/**
 *
 */
public class ProgressBarPage extends WidgetsPage {

	private static final int MIN = 0;
	private static final int MAX = 100;
	private static final int INITIAL = 0;

	@Override
	protected String getTitle() {
		return "Progress bar";
	}

	@Override
	protected boolean canGoBack() {
		return true;
	}

	@Override
	protected Widget createMainContent() {
		GridComposite grid = new GridComposite();
		grid.setHorizontal(true);
		grid.setCount(1);

		ProgressBar progressBar = new ProgressBar(MIN, MAX, INITIAL);
		progressBar.setIndeterminate(true);
		progressBar.addClassSelector(ClassSelector.MEDIUM_LABEL);
		grid.add(progressBar);

		SplitComposite splitComposite = new SplitComposite();
		splitComposite.setHorizontal(true);
		splitComposite.setRatio(0.5f);
		grid.add(splitComposite);

		CircularProgressBar circularProgressBar = new CircularProgressBar(MIN, MAX, INITIAL);
		circularProgressBar.setIndeterminate(true);
		circularProgressBar.addClassSelector(ClassSelector.MEDIUM_LABEL);
		splitComposite.add(circularProgressBar);

		PictoProgressBar pictoProgressBar = new PictoProgressBar(MIN, MAX, INITIAL);
		pictoProgressBar.setIndeterminate(true);
		pictoProgressBar.addClassSelector(ClassSelector.LARGE_ICON);
		splitComposite.add(pictoProgressBar);

		return grid;
	}

}
