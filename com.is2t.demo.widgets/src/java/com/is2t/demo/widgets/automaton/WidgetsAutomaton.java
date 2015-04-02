/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */

package com.is2t.demo.widgets.automaton;

import com.is2t.demo.utilities.automaton.Automaton;
import com.is2t.demo.widgets.page.AppPage;
import com.is2t.demo.widgets.page.TransitionsHelper;

public class WidgetsAutomaton implements Automaton {

	private int step;

	@Override
	public void run() {
		switch (step) {
		case 0:
			TransitionsHelper.goTo(AppPage.AboutPage);
			break;
		case 1:
			TransitionsHelper.back();
			break;
		case 2:
			TransitionsHelper.goTo(AppPage.DateTimePage);
			break;	
		case 3:
			TransitionsHelper.back();
			break;
		case 4:
			TransitionsHelper.goTo(AppPage.VolumePage);
			break;
		case 5:
			TransitionsHelper.back();
			break;
		case 6:
			TransitionsHelper.goTo(AppPage.ProfilePage);
			break;	
		case 7:
			TransitionsHelper.back();
			break;
		case 8:
			TransitionsHelper.goTo(AppPage.SecurityPage);
			break;	
		case 9:
			TransitionsHelper.back();
			break;
		case 10:
			TransitionsHelper.goTo(AppPage.BatteryProfilePage);
			break;
		case 11:
			TransitionsHelper.back();
			break;
			
		default:
			step = -1;
			break;
		}

		step++;
	}

	@Override
	public long getPeriod() {
		return 2000;
	}
}
