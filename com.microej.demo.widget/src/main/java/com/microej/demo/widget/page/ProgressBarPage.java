/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.microej.demo.widget.page;

import ej.widget.basic.drawing.CircularProgressBar;
import ej.widget.basic.drawing.ProgressBar;
import ej.widget.basic.picto.PictoProgress;
import ej.widget.container.Grid;

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

		Grid grid = new Grid(true, 1);

		ProgressBar progressBar = new ProgressBar(MIN, MAX, INITIAL);
		progressBar.setIndeterminate(true);
		grid.add(progressBar);

		Grid splitComposite = new Grid(false, 1);
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
