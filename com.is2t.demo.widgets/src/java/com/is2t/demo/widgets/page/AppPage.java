/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.widgets.page;

import ej.flow.PageType;

public enum AppPage implements PageType<WidgetsPage> {

	MainPage(MainPage.class), DateTimePage(DateTimePage.class), VolumePage(VolumePage.class), ProfilePage(
			ProfilePage.class), SecurityPage(SecurityPage.class), AboutPage(AboutPage.class), BatteryProfilePage(
			BatteryProfilePage.class);

	private final Class<? extends WidgetsPage> clazz;

	private AppPage(Class<? extends WidgetsPage> clazz) {
		this.clazz = clazz;
	}

	@Override
	public WidgetsPage createPage() {
		try {
			return clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			// should not occur
		}

		// could not create page?
		throw new IllegalArgumentException();
	}
}
