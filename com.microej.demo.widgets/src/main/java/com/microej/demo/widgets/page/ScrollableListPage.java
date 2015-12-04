/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.microej.demo.widgets.page;

import com.microej.demo.widgets.style.ClassSelectors;

import ej.bon.Timer;
import ej.bon.TimerTask;
import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.composite.ListComposite;
import ej.composite.ScrollComposite;
import ej.microui.display.Display;
import ej.mwt.Widget;
import ej.widget.basic.Label;

/**
 * This page illustrates the scrollable list.
 */
public class ScrollableListPage extends AbstractDemoPage {

	private static final String ITEM_PREFIX = "Item "; //$NON-NLS-1$
	private static final int APPEARANCE_DELAY = 1000;
	private static final int ITEM_COUNT = 100;
	private static final int FIRST_SHOT_COUNT = 20;

	private ListComposite listComposite;
	private boolean complete;

	@Override
	protected String getTitle() {
		return "Scrollable list"; //$NON-NLS-1$
	}

	@Override
	protected Widget createMainContent() {
		// layout:
		// Item 1
		// Item 2
		// ...
		// Item n-1
		// Item n

		this.listComposite = new ListComposite();
		this.listComposite.setHorizontal(false);

		for (int i = 1; i <= FIRST_SHOT_COUNT; i++) {
			Label item = new Label(ITEM_PREFIX + i);
			item.addClassSelector(ClassSelectors.LIST_ITEM);
			this.listComposite.add(item);
		}

		return new ScrollComposite(this.listComposite, true);
	}

	@Override
	public void showNotify() {
		super.showNotify();
		// Add missing items.
		Timer timer = ServiceLoaderFactory.getServiceLoader().getService(Timer.class);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				Display.getDefaultDisplay().callSerially(new Runnable() {
					@Override
					public void run() {
						if (!ScrollableListPage.this.complete) {
							ScrollableListPage.this.complete = true;
							for (int i = FIRST_SHOT_COUNT + 1; i <= ITEM_COUNT; i++) {
								Label item = new Label(ITEM_PREFIX + i);
								item.addClassSelector(ClassSelectors.LIST_ITEM);
								ScrollableListPage.this.listComposite.add(item);
							}
						}
					}
				});
				revalidate();
			}
		}, APPEARANCE_DELAY);
	}

}
