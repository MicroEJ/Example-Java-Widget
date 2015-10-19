/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.is2t.demo.widgets;

/**
 * Example configuration.
 */
public interface Configuration {

	/**
	 * Allows to slow down the animation. Higher the value is slower the animation is.
	 */
	int DEBUG_FACTOR = 1;

	/**
	 * Transition duration.
	 */
	int TRANSITION_DURATION = 800 * DEBUG_FACTOR;

	/**
	 * Transition period.
	 */
	int TRANSITION_PERIOD = 80 * DEBUG_FACTOR;

	/**
	 * Period between transitions.
	 */
	int NEXT_DESTINATION_PERIOD = 1000 * DEBUG_FACTOR;
}
