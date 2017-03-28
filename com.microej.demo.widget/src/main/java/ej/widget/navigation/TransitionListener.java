/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * Use of this source code is subject to license terms.
 */
package ej.widget.navigation;

import ej.widget.navigation.page.Page;

/**
 *
 */
public interface TransitionListener {

	/**
	 * Callback function when a transition starts.
	 *
	 * @param transitionsSteps
	 *            The number of transition steps. The steps start at 0.
	 * @param transitionsStop
	 * @param from
	 *            The page the transition is coming from.
	 * @param to
	 *            The page the transition is going to.
	 */
	void onTransitionStart(int transitionsSteps, int transitionsStop, Page from, Page to);

	/**
	 * Callback function when a step occurs.
	 *
	 * @param step
	 *            the current step.
	 */
	void onTransitionStep(int step);

	/**
	 * Callback function when a transition stops.
	 */
	void onTransitionStop();
}
