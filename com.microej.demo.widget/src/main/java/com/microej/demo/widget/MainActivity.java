/*
 * Java
 *
 * Copyright 2016-2017 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.microej.demo.widget;

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
		WidgetsDemo.start();
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
		WidgetsDemo.stop();
	}

	@Override
	public void onDestroy() {
		// do nothing
	}
}
