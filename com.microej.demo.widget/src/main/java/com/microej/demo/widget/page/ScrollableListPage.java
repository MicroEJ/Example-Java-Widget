/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package com.microej.demo.widget.page;

import com.microej.demo.widget.style.ClassSelectors;

import ej.bon.Timer;
import ej.bon.TimerTask;
import ej.microui.MicroUI;
import ej.service.ServiceFactory;
import ej.widget.basic.Label;
import ej.widget.container.List;
import ej.widget.container.Scroll;
import ej.widget.container.util.LayoutOrientation;

/**
 * This page illustrates the scrollable list.
 */
public class ScrollableListPage extends AbstractDemoPage {

	private static final String ITEM_PREFIX = "Item "; //$NON-NLS-1$
	private static final int APPEARANCE_DELAY = 1000;
	private static final int ITEM_COUNT = 100;
	private static final int FIRST_SHOT_COUNT = 20;

	private final List listComposite;
	private boolean complete;

	/**
	 * Creates a scrollable list page.
	 */
	public ScrollableListPage() {
		super(false, "Scrollable list"); //$NON-NLS-1$

		// layout:
		// Item 1
		// Item 2
		// ...
		// Item n-1
		// Item n
		this.listComposite = new List(LayoutOrientation.VERTICAL);

		addItems(1, FIRST_SHOT_COUNT);

		Scroll scroll = new Scroll(false, true);
		scroll.setWidget(this.listComposite);
		setCenter(scroll);
	}

	private void addItems(int start, int end) {
		for (int i = start; i <= end; i++) {
			Label item = new Label(ITEM_PREFIX + i);
			item.addClassSelector(ClassSelectors.LIST_ITEM);
			this.listComposite.add(item);
		}
	}

	@Override
	protected void onShown() {
		super.onShown();
		if (!ScrollableListPage.this.complete) {
			// Add missing items.
			Timer timer = ServiceFactory.getService(Timer.class);
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					if (isShown()) {
						MicroUI.callSerially(new Runnable() {
							@Override
							public void run() {
								if (!ScrollableListPage.this.complete) {
									ScrollableListPage.this.complete = true;
									addItems(FIRST_SHOT_COUNT + 1, ITEM_COUNT);
								}
							}
						});
						requestLayOut();
					}
				}
			}, APPEARANCE_DELAY);
		}
	}

}
