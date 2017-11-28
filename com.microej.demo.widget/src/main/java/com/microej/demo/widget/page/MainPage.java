/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.microej.demo.widget.page;

import com.microej.demo.widget.WidgetsDemo;
import com.microej.demo.widget.style.ClassSelectors;

import ej.mwt.Widget;
import ej.widget.basic.Button;
import ej.widget.container.List;
import ej.widget.container.Scroll;
import ej.widget.listener.OnClickListener;

/**
 * Main page of the application. It allows to access to all the pages of the application.
 */
public class MainPage extends AbstractDemoPage {

	/**
	 * Creates a main page.
	 */
	public MainPage() {
		super(true, "MicroEJ Widgets"); //$NON-NLS-1$

		List listComposite = new List(false);
		listComposite.add(newSelectableItem("Basic widgets - Picto", PictoWidgetPage.class)); //$NON-NLS-1$
		listComposite.add(newSelectableItem("Basic widgets - Image", ImageWidgetPage.class)); //$NON-NLS-1$
		listComposite.add(newSelectableItem("Basic widgets - Drawing", VectorWidgetPage.class)); //$NON-NLS-1$
		listComposite.add(newSelectableItem("Progress bar", ProgressBarPage.class)); //$NON-NLS-1$
		listComposite.add(newSelectableItem("Scrollable list", ScrollableListPage.class)); //$NON-NLS-1$
		listComposite.add(newSelectableItem("Scrollable text", ScrollableTextPage.class)); //$NON-NLS-1$
		listComposite.add(newSelectableItem("Keyboard", EditionPage.class)); //$NON-NLS-1$
		listComposite.add(newSelectableItem("Chart", ChartPage.class)); //$NON-NLS-1$
		listComposite.add(newSelectableItem("Wheel", DatePage.class)); //$NON-NLS-1$
		listComposite.add(newSelectableItem("Carousel", CarouselPage.class)); //$NON-NLS-1$
		Scroll scroll = new Scroll(false, true);
		scroll.setWidget(listComposite);
		setCenter(scroll);
	}

	// A button that leads to the given page.
	private Button newSelectableItem(String name, final Class<? extends Widget> clazz) {
		Button button = new Button(name);
		button.addClassSelector(ClassSelectors.LIST_ITEM);
		button.addOnClickListener(new OnClickListener() {

			@Override
			public void onClick() {
				WidgetsDemo.show(clazz);
			}
		});
		return button;
	}

}
