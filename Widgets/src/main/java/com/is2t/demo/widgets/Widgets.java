/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.widgets;

import com.is2t.demo.widgets.page.MainPage;
import com.is2t.demo.widgets.page.WidgetsPage;
import com.is2t.demo.widgets.widget.theme.WidgetsTheme;

import ej.bon.Timer;
import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.flow.mwt.MWTFlowManager;
import ej.flow.mwt.TransitionDesktop;
import ej.flow.mwt.TransitionManager;
import ej.flow.mwt.translation.HorizontalTransitionManager;
import ej.flow.stacked.StackedFlowManager;
import ej.flow.stacked.cached.CachedStackManager;
import ej.microui.MicroUI;
import ej.motion.ease.EaseMotionManager;
import ej.mwt.MWT;

/**
 * This demo represents a settings menu you can find on your smartphone. The main page show all available settings, each
 * one leading to a page illustrating a different widget.
 */
public class Widgets {

	/**
	 * Transition duration.
	 */
	private static final int DURATION = 800;

	/**
	 * Time between two steps in a transition.
	 */
	private static final int PERIOD = 50;

	// Prevents initialization.
	private Widgets() {
	}

	/**
	 * Application entry point.
	 * 
	 * @param args
	 *            useless.
	 */
	public static void main(String[] args) {
		MicroUI.errorLog(true);
		MWT.RenderingContext.add(new WidgetsTheme());
		MWTFlowManager<WidgetsPage, WidgetsPage> mwtFlowManager = ServiceLoaderFactory.getServiceLoader().getService(
				MWTFlowManager.class);
		initializeMWTFlowManager(mwtFlowManager);
		mwtFlowManager.goTo(new MainPage());
	}

	private static void initializeMWTFlowManager(MWTFlowManager mwtFlowManager) {
		StackedFlowManager<WidgetsPage, WidgetsPage> stackedFlowManager = new StackedFlowManager<>();
		stackedFlowManager.setStackManager(new CachedStackManager<WidgetsPage, WidgetsPage>());
		mwtFlowManager.setFlowManager(stackedFlowManager);
		TransitionDesktop desktop = new TransitionDesktop();
		mwtFlowManager.setDesktop(desktop);
		mwtFlowManager.setTransitionListener(desktop);
		Timer timer = ServiceLoaderFactory.getServiceLoader().getService(Timer.class);
		mwtFlowManager.setTimer(timer);
		mwtFlowManager.setTransitionManager(newTransitionManager());
		desktop.show();
	}

	private static TransitionManager newTransitionManager() {
		TransitionManager transitionManager = new HorizontalTransitionManager();
		transitionManager.setMotionManager(new EaseMotionManager());
		transitionManager.setDuration(DURATION);
		transitionManager.setPeriod(PERIOD);
		return transitionManager;
	}
}
