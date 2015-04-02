/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.widgets;

import com.is2t.demo.utilities.automaton.impl.AutomatonManagerImpl;
import com.is2t.demo.widgets.page.AppPage;
import com.is2t.demo.widgets.page.WidgetsPage;
import com.is2t.demo.widgets.page.TransitionsHelper;
import com.is2t.demo.widgets.theme.WidgetsTheme;
import com.is2t.layers.LayersManager;
import com.is2t.transition.MonitoringLayersManager;

import ej.bon.Timer;
import ej.flow.mwt.MWTFlowManager;
import ej.flow.mwt.TransitionDesktop;
import ej.flow.stacked.StackedFlowManager;
import ej.flow.stacked.cached.CachedStackManager;
import ej.microui.io.Display;
import ej.mwt.Desktop;
import ej.mwt.MWT;
import ej.mwt.rendering.Look;
import ej.mwt.rendering.Theme;

public class Widgets {

	private LayersManager layersManager;
	private TransitionDesktop desktop;
	private MWTFlowManager<AppPage, WidgetsPage> mwtFlowManager;
	private Theme theme;
	private AutomatonManagerImpl<?> automatonManager;

	public static void main(String[] args) {
		Display display = Display.getDefaultDisplay();
		Timer timer = new Timer();
		MonitoringLayersManager.setTimer(timer);
		Widgets settings = new Widgets(display, timer);
		settings.getDesktop().show();
	}

	public Widgets(Display display, Timer timer) {
		MonitoringLayersManager monitoringLayersManager = new MonitoringLayersManager(display);
		MonitoringLayersManager.setLocation(display.getWidth() - MonitoringLayersManager.getWidth() - 70, 0);

		theme = new WidgetsTheme();
		Look look = theme.getLook();
		MWT.RenderingContext.add(theme);
		TransitionsHelper.initialize();
		TransitionsHelper.setLook(look);
		mwtFlowManager = TransitionsHelper.getMWTFlowManager();
		StackedFlowManager<AppPage, WidgetsPage> stackedFlowManager = new StackedFlowManager<AppPage, WidgetsPage>();
		stackedFlowManager.setStackManager(new CachedStackManager<AppPage, WidgetsPage>());
		mwtFlowManager.setFlowManager(stackedFlowManager);
		monitoringLayersManager.setMonitoringLook(look);
		layersManager = monitoringLayersManager;

		desktop = new TransitionDesktop(display);
		mwtFlowManager.setDesktop(desktop);
		mwtFlowManager.setTransitionListener(desktop);
		mwtFlowManager.setTimer(timer);
		mwtFlowManager.goTo(AppPage.MainPage);
		layersManager.show();
	}
	
	public void stop() {
		automatonManager.stop();
		layersManager.hide();
		desktop.hide();
		TransitionsHelper.clear();
		
		desktop.getDisplay().callSerially(new Runnable() {
			
			@Override
			public void run() {
				MWT.RenderingContext.remove(theme);
			}
		});
	}

	public Desktop getDesktop() {
		return desktop;
	}
}
