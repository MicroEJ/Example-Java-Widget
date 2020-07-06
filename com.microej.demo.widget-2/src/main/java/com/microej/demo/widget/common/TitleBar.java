/*
 * Copyright 2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package com.microej.demo.widget.common;

import ej.microui.display.Colors;
import ej.mwt.Widget;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.background.RectangularBackground;
import ej.mwt.style.outline.border.FlexibleRectangularBorder;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.ClassSelector;
import ej.mwt.stylesheet.selector.StateSelector;
import ej.mwt.stylesheet.selector.combinator.AndCombinator;
import ej.widget.basic.ImageWidget;
import ej.widget.container.SimpleDock;
import ej.widget.container.util.LayoutOrientation;
import ej.widget.listener.OnClickListener;
import ej.widget.util.States;

public class TitleBar {

	/* package */ static final int TITLE_BAR_WIDTH = 44; // FIXME use one of the image width

	private static final String MICROEJ_BANNER = "/images/microej_banner.png";
	private static final String ICON = "/images/icon.png";
	private static final String MENU = "/images/menu.png";
	private static final int CORAL = 0xee502e;
	private static final int POMEGRANATE = 0xcf4520;
	private static final int TITLE_BAR_CLASSSELECTOR = 44696;

	public static void addTitleBarStyle(CascadingStylesheet stylesheet) {
		EditableStyle titleBarStyle = stylesheet.getSelectorStyle(new ClassSelector(TITLE_BAR_CLASSSELECTOR));
		titleBarStyle.setBackground(new RectangularBackground(CORAL));
		titleBarStyle.setColor(Colors.WHITE);
		titleBarStyle.setBorder(new FlexibleRectangularBorder(POMEGRANATE, 0, 0, 2, 0));

		EditableStyle titleBarPressedStyle = stylesheet.getSelectorStyle(
				new AndCombinator(new ClassSelector(TITLE_BAR_CLASSSELECTOR), new StateSelector(States.ACTIVE)));
		titleBarPressedStyle.setBackground(new RectangularBackground(0xcf4520));
	}

	public static Widget createTitleBar(boolean canGoBack) {
		SimpleDock simpleDock = new SimpleDock(LayoutOrientation.VERTICAL);

		Widget top;
		if (canGoBack) {
			ButtonImage buttonImage = new ButtonImage(MENU);
			buttonImage.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick() {
					Navigation.showMainPage();
				}
			});
			top = buttonImage;
		} else {
			top = new ImageWidget(ICON);
		}
		top.addClassSelector(TITLE_BAR_CLASSSELECTOR);
		simpleDock.setFirstChild(top);

		ImageWidget verticalTitle = new ImageWidget(MICROEJ_BANNER);
		simpleDock.setCenterChild(verticalTitle);

		return simpleDock;
	}

	public static Widget createPage(Widget content, boolean canGoBack) {
		SimpleDock dock = new SimpleDock(LayoutOrientation.HORIZONTAL);

		Widget titleBar = TitleBar.createTitleBar(canGoBack);
		dock.setFirstChild(titleBar);

		dock.setCenterChild(content);

		return dock;
	}

}
