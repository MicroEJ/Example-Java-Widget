/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.widgets.page;

import com.is2t.transition.HorizontalTransitionManager;
import com.is2t.transition.MWTFlowManagerImpl;

import ej.flow.mwt.MWTFlowManager;
import ej.flow.mwt.TransitionManager;
import ej.motion.MotionManager;
import ej.motion.ease.EaseMotionManager;
import ej.motion.none.NoMotionManager;
import ej.mwt.rendering.Look;

public class TransitionsHelper {

	private static final int DURATION = 800;
	private static final int PERIOD = 50;
	private static final boolean ANIMATIONS_DISABLED = System.getProperty("com.is2t.demo.NoAnimation") != null;
	private static final boolean LAYERS_DISABLED = System.getProperty("com.is2t.demo.NoLayer") != null;

	private static TransitionManager HORIZONTAL_MANAGER;
	private static MWTFlowManager<AppPage, WidgetsPage> MWTFLOWMANAGER;

	private TransitionsHelper() {
	}
	
	public static void initialize() {
		MotionManager motionManager = null;

		if (!ANIMATIONS_DISABLED) {
			motionManager = new EaseMotionManager();
		} else {
			motionManager = new NoMotionManager();
		}
		
		if (LAYERS_DISABLED) {
			HORIZONTAL_MANAGER = new ej.flow.mwt.translation.HorizontalTransitionManager();
			MWTFLOWMANAGER = new ej.flow.mwt.impl.MWTFlowManagerImpl<>();
		} else {
			HORIZONTAL_MANAGER = new HorizontalTransitionManager();
			MWTFLOWMANAGER = new MWTFlowManagerImpl<>();
		}
		
		HORIZONTAL_MANAGER.setMotionManager(motionManager);
		HORIZONTAL_MANAGER.setDuration(DURATION);
		HORIZONTAL_MANAGER.setPeriod(PERIOD);
		MWTFLOWMANAGER.setTransitionManager(HORIZONTAL_MANAGER);
	}
	
	public static MWTFlowManager<AppPage, WidgetsPage> getMWTFlowManager() {
		return MWTFLOWMANAGER;
	}
	
	public static void clear(){
		MWTFLOWMANAGER = null;
		HORIZONTAL_MANAGER = null;
	}

	public static WidgetsPage goTo(AppPage appPage) {
		// flowManager.clearHistory();
		WidgetsPage goTo = MWTFLOWMANAGER.goTo(appPage);
		return goTo;
	}

	public static void back() {
		MWTFLOWMANAGER.back();
	}
	
	public static void backUntil(AppPage page) {
		MWTFLOWMANAGER.backUntil(page);
	}

	public static void setLook(Look look) {
		HORIZONTAL_MANAGER.setLook(look);
	}
}
