/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package ej.demo.ui.widget;

import ej.wadapps.app.Activity;

/**
 * Represents the application activity
 */
public class MainActivity implements Activity {

	@Override
	public String getID() {
		return "DemoWidget"; //$NON-NLS-1$
	}

	@Override
	public void onCreate() {
		// do nothing
	}

	@Override
	public void onRestart() {
		// do nothing
	}

	@Override
	public void onStart() {
		WidgetsDemo.main(new String[0]);
	}

	@Override
	public void onResume() {
		// do nothing
	}

	@Override
	public void onPause() {
		// do nothing
	}

	@Override
	public void onStop() {
		// do nothing
	}

	@Override
	public void onDestroy() {
		// do nothing
	}
}
