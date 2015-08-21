/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.widgets;

import com.is2t.demo.utilities.automaton.impl.AutomatonManagerImpl;
import com.is2t.demo.widgets.page.AppPage;
import com.is2t.demo.widgets.page.TransitionsHelper;
import com.is2t.demo.widgets.page.WidgetsPage;
import com.is2t.demo.widgets.theme.WidgetsTheme;

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

	private final TransitionDesktop desktop;
	private final MWTFlowManager<AppPage, WidgetsPage> mwtFlowManager;
	private final Theme theme;
	private AutomatonManagerImpl<?> automatonManager;

	public static void main(String[] args) {
		Display display = Display.getDefaultDisplay();
		Timer timer = new Timer();
		Widgets settings = new Widgets(display, timer);
		settings.getDesktop().show();
	}

	public Widgets(Display display, Timer timer) {
		this.theme = new WidgetsTheme();
		Look look = this.theme.getLook();
		MWT.RenderingContext.add(this.theme);
		TransitionsHelper.initialize();
		TransitionsHelper.setLook(look);
		this.mwtFlowManager = TransitionsHelper.getMWTFlowManager();
		StackedFlowManager<AppPage, WidgetsPage> stackedFlowManager = new StackedFlowManager<AppPage, WidgetsPage>();
		stackedFlowManager.setStackManager(new CachedStackManager<AppPage, WidgetsPage>());
		this.mwtFlowManager.setFlowManager(stackedFlowManager);

		this.desktop = new TransitionDesktop(display);
		this.mwtFlowManager.setDesktop(this.desktop);
		this.mwtFlowManager.setTransitionListener(this.desktop);
		this.mwtFlowManager.setTimer(timer);
		this.mwtFlowManager.goTo(AppPage.MainPage);
	}

	public void stop() {
		this.automatonManager.stop();
		this.desktop.hide();
		TransitionsHelper.clear();

		this.desktop.getDisplay().callSerially(new Runnable() {

			@Override
			public void run() {
				MWT.RenderingContext.remove(Widgets.this.theme);
			}
		});
	}

	public Desktop getDesktop() {
		return this.desktop;
	}
}
