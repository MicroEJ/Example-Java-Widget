/*
 * Java
 *
 * Copyright  2016-2019 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
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
