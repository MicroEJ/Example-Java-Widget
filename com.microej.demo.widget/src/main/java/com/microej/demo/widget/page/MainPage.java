/*
 * Copyright 2014-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package com.microej.demo.widget.page;

import com.microej.demo.widget.WidgetsDemo;
import com.microej.demo.widget.style.ClassSelectors;

import ej.mwt.Widget;
import ej.widget.basic.Button;
import ej.widget.container.List;
import ej.widget.container.Scroll;
import ej.widget.container.util.LayoutOrientation;
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

		List listComposite = new List(LayoutOrientation.VERTICAL);
		listComposite.addChild(newSelectableItem("Basic widgets - Picto", PictoWidgetPage.class)); //$NON-NLS-1$
		listComposite.addChild(newSelectableItem("Basic widgets - Image", ImageWidgetPage.class)); //$NON-NLS-1$
		listComposite.addChild(newSelectableItem("Basic widgets - Drawing", VectorWidgetPage.class)); //$NON-NLS-1$
		listComposite.addChild(newSelectableItem("Progress bar", ProgressBarPage.class)); //$NON-NLS-1$
		listComposite.addChild(newSelectableItem("Scrollable list", ScrollableListPage.class)); //$NON-NLS-1$
		listComposite.addChild(newSelectableItem("Scrollable text", ScrollableTextPage.class)); //$NON-NLS-1$
		listComposite.addChild(newSelectableItem("Keyboard", KeyboardPage.class)); //$NON-NLS-1$
		listComposite.addChild(newSelectableItem("Chart", ChartPage.class)); //$NON-NLS-1$
		listComposite.addChild(newSelectableItem("Wheel", DatePage.class)); //$NON-NLS-1$
		listComposite.addChild(newSelectableItem("Carousel", CarouselPage.class)); //$NON-NLS-1$
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
