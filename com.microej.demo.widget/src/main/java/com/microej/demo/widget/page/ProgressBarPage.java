/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package com.microej.demo.widget.page;

import ej.widget.basic.drawing.CircularProgressBar;
import ej.widget.basic.drawing.ProgressBar;
import ej.widget.basic.picto.PictoProgress;
import ej.widget.container.Grid;
import ej.widget.container.util.LayoutOrientation;

/**
 * This page illustrates different implementations of a progress bar.
 */
public class ProgressBarPage extends AbstractDemoPage {

	private static final int MIN = 0;
	private static final int MAX = 100;
	private static final int INITIAL = 0;

	/**
	 * Creates a progress bar page.
	 */
	public ProgressBarPage() {
		super(false, "Progress bar"); //$NON-NLS-1$

		// layout:
		// | progress bar |
		// | circular progress bar - picto progress bar |

		Grid grid = new Grid(LayoutOrientation.HORIZONTAL, 1);

		ProgressBar progressBar = new ProgressBar(MIN, MAX, INITIAL);
		progressBar.setIndeterminate(true);
		grid.add(progressBar);

		Grid splitComposite = new Grid(LayoutOrientation.VERTICAL, 1);
		grid.add(splitComposite);

		PictoProgress pictoProgressBar = new PictoProgress(MIN, MAX, INITIAL);
		pictoProgressBar.setIndeterminate(true);
		splitComposite.add(pictoProgressBar);

		CircularProgressBar circularProgressBar = new CircularProgressBar(MIN, MAX, INITIAL);
		circularProgressBar.setIndeterminate(true);
		splitComposite.add(circularProgressBar);

		setCenter(grid);
	}

}
