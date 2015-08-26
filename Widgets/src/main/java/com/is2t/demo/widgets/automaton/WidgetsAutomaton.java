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
import com.is2t.demo.widgets.page.ProfilePage;
import com.is2t.demo.widgets.page.SecurityPage;
import com.is2t.demo.widgets.page.VolumePage;
import com.is2t.demo.widgets.page.WidgetsPage;

import ej.automaton.Automaton;
import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.flow.mwt.MWTFlowManager;

/**
 * Automaton for the application.
 */
public class WidgetsAutomaton implements Automaton {

	private int step;

	@Override
	public void run() {
		switch (this.step) {
		case 0:
			getFlowManager().goTo(new AboutPage());
			break;
		case 1:
			getFlowManager().back();
			break;
		case 2:
			getFlowManager().goTo(new DateTimePage());
			break;
		case 3:
			getFlowManager().back();
			break;
		case 4:
			getFlowManager().goTo(new VolumePage());
			break;
		case 5:
			getFlowManager().back();
			break;
		case 6:
			getFlowManager().goTo(new ProfilePage());
			break;
		case 7:
			getFlowManager().back();
			break;
		case 8:
			getFlowManager().goTo(new SecurityPage());
			break;
		case 9:
			getFlowManager().back();
			break;
		case 10:
			getFlowManager().goTo(new BatteryProfilePage());
			break;
		case 11:
			getFlowManager().back();
			break;

		default:
			this.step = -1;
			break;
		}

		this.step++;
	}

	private MWTFlowManager<WidgetsPage, WidgetsPage> getFlowManager() {
		MWTFlowManager<WidgetsPage, WidgetsPage> mwtFlowManager = ServiceLoaderFactory.getServiceLoader().getService(
				MWTFlowManager.class);
		return mwtFlowManager;
	}

	@Override
	public long getPeriod() {
		return 2000;
	}
}
