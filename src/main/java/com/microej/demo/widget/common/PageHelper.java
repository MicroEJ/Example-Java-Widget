/*
 * Copyright 2020-2025 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.common;

import ej.microui.display.Colors;
import ej.microui.display.Image;
import ej.microui.event.Event;
import ej.microui.event.generator.Command;
import ej.mwt.Desktop;
import ej.mwt.Widget;
import ej.mwt.render.OverlapRenderPolicy;
import ej.mwt.render.RenderPolicy;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.background.NoBackground;
import ej.mwt.style.background.RectangularBackground;
import ej.mwt.style.dimension.OptimalDimension;
import ej.mwt.style.outline.FlexibleOutline;
import ej.mwt.style.outline.UniformOutline;
import ej.mwt.style.outline.border.FlexibleRectangularBorder;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.ClassSelector;
import ej.mwt.stylesheet.selector.Selector;
import ej.mwt.stylesheet.selector.StateSelector;
import ej.mwt.stylesheet.selector.combinator.AndCombinator;
import ej.mwt.util.Alignment;
import ej.widget.basic.ImageButton;
import ej.widget.basic.ImageWidget;
import ej.widget.basic.OnClickListener;
import ej.widget.container.LayoutOrientation;
import ej.widget.container.OverlapContainer;
import ej.widget.container.SimpleDock;

/**
 * Helps to create the title bar a the page structure.
 */
public class PageHelper {

	/**
	 * Thickness between the title bar and the page content.
	 */
	public static final int LEFT_PADDING = 4;

	private static final String MICROEJ_BANNER = "/images/microej_banner.png"; //$NON-NLS-1$
	private static final String ICON = "/images/ic-app_layout.png"; //$NON-NLS-1$
	private static final String MENU = "/images/ic-list.png"; //$NON-NLS-1$
	private static final String ROUNDED_CORNER = "/images/rounded-corner.png"; //$NON-NLS-1$
	private static final String ROUNDED_CORNER_BOTTOM = "/images/rounded-corner-bottom.png"; //$NON-NLS-1$

	private static final int TITLE_BUTTON_CLASSSELECTOR = 44695;
	private static final int BANNER_CLASSSELECTOR = 44696;
	private static final int ROUNDED_CORNER_CLASSSELECTOR = 44697;
	private static final int ROUNDED_CORNER_BOTTOM_CLASSSELECTOR = 44698;

	private static final int TITLE_BUTTON_PADDING = 10;
	private static final int TITLE_PADDING = 9;
	private static final int TITLE_MARGIN_SIDES = 15;
	private static final int TITLE_MARGIN_BOTTOM = 8;
	private static final int CONTENT_PADDING_SIDES = 6;
	private static final int CONTENT_PADDING_BOTTOM = 20;

	/**
	 * Class selector for the page title.
	 */
	public static final int TITLE_CLASSSELECTOR = 44699;
	/**
	 * Class selector for the page content.
	 */
	public static final int CONTENT_CLASSSELECTOR = 44700;

	private PageHelper() {
		// Prevent instantiation.
	}

	/**
	 * Adds the common page style to a stylesheet.
	 *
	 * @param stylesheet
	 *            the stylesheet.
	 */
	public static void addCommonStyle(CascadingStylesheet stylesheet) {
		Selector titleButton = new ClassSelector(TITLE_BUTTON_CLASSSELECTOR);

		EditableStyle style = stylesheet.getDefaultStyle();
		style.setColor(DemoColors.DEFAULT_FOREGROUND);
		style.setBackground(new RectangularBackground(DemoColors.DEFAULT_BACKGROUND));
		style.setFont(Fonts.getSourceSansPro19px300());
		style.setHorizontalAlignment(Alignment.HCENTER);
		style.setVerticalAlignment(Alignment.VCENTER);

		style = stylesheet.getSelectorStyle(titleButton);
		style.setBackground(new RectangularBackground(DemoColors.CORAL));
		style.setColor(Colors.WHITE);
		style.setPadding(new UniformOutline(TITLE_BUTTON_PADDING));
		style.setBorder(new FlexibleRectangularBorder(DemoColors.POMEGRANATE, 0, 0, 2, 0));

		style = stylesheet.getSelectorStyle(new AndCombinator(titleButton, new StateSelector(StateSelector.ACTIVE)));
		style.setBackground(new RectangularBackground(0xcf4520));

		style = stylesheet.getSelectorStyle(new ClassSelector(BANNER_CLASSSELECTOR));
		style.setBackground(new RectangularBackground(DemoColors.EMPTY_SPACE));
		style.setVerticalAlignment(Alignment.TOP);

		style = stylesheet.getSelectorStyle(new ClassSelector(ROUNDED_CORNER_CLASSSELECTOR));
		style.setVerticalAlignment(Alignment.TOP);
		setRoundedCornerStyle(style);

		style = stylesheet.getSelectorStyle(new ClassSelector(ROUNDED_CORNER_BOTTOM_CLASSSELECTOR));
		style.setVerticalAlignment(Alignment.BOTTOM);
		setRoundedCornerStyle(style);

		style = stylesheet.getSelectorStyle(new ClassSelector(TITLE_CLASSSELECTOR));
		style.setMargin(new FlexibleOutline(0, TITLE_MARGIN_SIDES, TITLE_MARGIN_BOTTOM, TITLE_MARGIN_SIDES));
		style.setBorder(new FlexibleRectangularBorder(DemoColors.CORAL, 0, 0, 2, 0));
		style.setPadding(new UniformOutline(TITLE_PADDING));

		style = stylesheet.getSelectorStyle(new ClassSelector(PageHelper.CONTENT_CLASSSELECTOR));
		style.setBorder(new FlexibleRectangularBorder(DemoColors.EMPTY_SPACE, 0, 0, 0, PageHelper.LEFT_PADDING));
		style.setPadding(new FlexibleOutline(0, CONTENT_PADDING_SIDES, CONTENT_PADDING_BOTTOM, CONTENT_PADDING_SIDES));
	}

	private static void setRoundedCornerStyle(EditableStyle roundedCornerBottomStyle) {
		roundedCornerBottomStyle.setHorizontalAlignment(Alignment.LEFT);
		roundedCornerBottomStyle.setColor(Colors.BLACK);
		roundedCornerBottomStyle.setDimension(OptimalDimension.OPTIMAL_DIMENSION_XY);
		roundedCornerBottomStyle.setBackground(NoBackground.NO_BACKGROUND);
	}

	/* package */ static int getTitleBarWidth() {
		return Image.getImage(MICROEJ_BANNER).getWidth();
	}

	/**
	 * Creates a title bar.
	 *
	 * @param canGoBack
	 *            <code>true</code> to add a back button, <code>false</code> to add an icon.
	 * @return the title bar.
	 */
	private static Widget createTitleBar(boolean canGoBack) {
		SimpleDock dock = new SimpleDock(LayoutOrientation.VERTICAL);

		Widget top = createTitleButton(canGoBack);
		dock.setFirstChild(top);

		ImageWidget banner = new ImageWidget(MICROEJ_BANNER);
		banner.addClassSelector(BANNER_CLASSSELECTOR);
		dock.setCenterChild(banner);

		return dock;
	}

	private static Widget createTitleButton(boolean canGoBack) {
		Widget widget;
		if (canGoBack) {
			TitleButton titleButton = new TitleButton(MENU);
			titleButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick() {
					Navigation.showMainPage();
				}
			});
			widget = titleButton;
		} else {
			widget = new ImageWidget(ICON);
		}
		widget.addClassSelector(TITLE_BUTTON_CLASSSELECTOR);
		return widget;
	}

	/**
	 * Updates the title bar of the given page widget which was created using {@link #createPage(Widget, boolean)}.
	 *
	 * @param pageWidget
	 *            the page widget to update.
	 * @param canGoBack
	 *            <code>true</code> to add a back button, <code>false</code> to add an icon.
	 */
	public static void updateTitleBar(Widget pageWidget, boolean canGoBack) {
		SimpleDock mainDock = (SimpleDock) pageWidget;
		SimpleDock titleBar = (SimpleDock) mainDock.getFirstChild();
		assert (titleBar != null);
		titleBar.setFirstChild(createTitleButton(canGoBack));
	}

	/**
	 * Creates a page hierarchy. The given content is wrapped in a page along with a title bar.
	 *
	 * @param content
	 *            the content.
	 * @param canGoBack
	 *            <code>true</code> to add a back button, <code>false</code> to add an icon.
	 * @return the page root widget.
	 */
	public static Widget createPage(Widget content, boolean canGoBack) {
		SimpleDock dock = new SimpleDock(LayoutOrientation.HORIZONTAL);

		Widget titleBar = PageHelper.createTitleBar(canGoBack);
		dock.setFirstChild(titleBar);

		OverlapContainer overlapContainer = new OverlapContainer();
		overlapContainer.addChild(content);

		ImageWidget topImage = new ImageWidget(ROUNDED_CORNER);
		topImage.addClassSelector(ROUNDED_CORNER_CLASSSELECTOR);
		overlapContainer.addChild(topImage);
		ImageWidget bottomImage = new ImageWidget(ROUNDED_CORNER_BOTTOM);
		bottomImage.addClassSelector(ROUNDED_CORNER_BOTTOM_CLASSSELECTOR);
		overlapContainer.addChild(bottomImage);

		dock.setCenterChild(overlapContainer);
		return dock;
	}

	/**
	 * Creates a desktop to use for each page.
	 *
	 * @return a desktop.
	 */
	public static Desktop createDesktop() {
		return new Desktop() {
			@Override
			public boolean handleEvent(int event) {
				if (Event.getType(event) == Command.EVENT_TYPE && Event.getData(event) == Command.SELECT) {
					Navigation.showMainPage();
				}
				return super.handleEvent(event);
			}

			@Override
			protected RenderPolicy createRenderPolicy() {
				return new OverlapRenderPolicy(this);
			}
		};
	}
}
