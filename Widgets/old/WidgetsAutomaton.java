/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */

package com.is2t.demo.widgets.automaton;

import com.is2t.demo.widgets.page.AboutPage;
import com.is2t.demo.widgets.page.BatteryProfilePage;
import com.is2t.demo.widgets.page.DateTimePage;
import com.is2t.demo.widgets.page.MainPage;
import com.is2t.demo.widgets.page.ProfilePage;
import com.is2t.demo.widgets.page.SecurityPage;
import com.is2t.demo.widgets.page.VolumePage;
import com.is2t.demo.widgets.transition.TransitionManager;

import ej.automaton.Automaton;
import ej.components.dependencyinjection.ServiceLoaderFactory;

/**
 * Automaton for the application.
 */
public class WidgetsAutomaton implements Automaton {

	private int step;

	@Override
	public void run() {
		switch (this.step) {
		case 0:
			getTransitionManager().goTo(new AboutPage());
			break;
		case 1:
			getTransitionManager().goTo(new MainPage());
			break;
		case 2:
			getTransitionManager().goTo(new DateTimePage());
			break;
		case 3:
			getTransitionManager().goTo(new MainPage());
			break;
		case 4:
			getTransitionManager().goTo(new VolumePage());
			break;
		case 5:
			getTransitionManager().goTo(new MainPage());
			break;
		case 6:
			getTransitionManager().goTo(new ProfilePage());
			break;
		case 7:
			getTransitionManager().goTo(new MainPage());
			break;
		case 8:
			getTransitionManager().goTo(new SecurityPage());
			break;
		case 9:
			getTransitionManager().goTo(new MainPage());
			break;
		case 10:
			getTransitionManager().goTo(new BatteryProfilePage());
			break;
		case 11:
			getTransitionManager().goTo(new MainPage());
			break;

		default:
			this.step = -1;
			break;
		}

		this.step++;
	}

	protected TransitionManager getTransitionManager() {
		return ServiceLoaderFactory.getServiceLoader().getService(TransitionManager.class);
	}

	@Override
	public long getPeriod() {
		return 2000;
	}
}
