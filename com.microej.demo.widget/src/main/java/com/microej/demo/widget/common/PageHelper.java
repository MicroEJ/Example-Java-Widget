/*
 * Copyright 2020 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.common;

import ej.microui.display.Colors;
import ej.microui.display.GraphicsContext;
import ej.mwt.Desktop;
import ej.mwt.Widget;
import ej.mwt.animation.Animator;
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
	/* package */ static final int TITLE_BAR_WIDTH = 44; // FIXME use one of the image width

	private static final String MICROEJ_BANNER = "/images/microej_banner.png"; //$NON-NLS-1$
	private static final String ICON = "/images/ic-app_layout.png"; //$NON-NLS-1$
	private static final String MENU = "/images/ic-list.png"; //$NON-NLS-1$
	private static final String ROUNDED_CORNER = "/images/rounded-corner.png"; //$NON-NLS-1$
	private static final String ROUNDED_CORNER_BOTTOM = "/images/rounded-corner-bottom.png"; //$NON-NLS-1$

	private static final int TITLE_BAR_CLASSSELECTOR = 44696;
	private static final int ROUNDED_CORNER_CLASSSELECTOR = 44697;
	private static final int ROUNDED_CORNER_BOTTOM_CLASSSELECTOR = 44698;
	/**
	 * Class selector for the page title.
	 */
	public static final int TITLE_CLASSSELECTOR = 44699;
	/**
	 * Class selector for the page content.
	 */
	public static final int CONTENT_CLASSSELECTOR = 44700;

	private static final Animator ANIMATOR = new Animator();

	private PageHelper() {
	}

	/**
	 * Returns the animator instance.
	 *
	 * @return the animator instance.
	 */
	public static Animator getAnimator() {
		return ANIMATOR;
	}

	/**
	 * Adds the common page style to a stylesheet.
	 *
	 * @param stylesheet
	 *            the stylesheet.
	 */
	public static void addCommonStyle(CascadingStylesheet stylesheet) {
		EditableStyle style = stylesheet.getDefaultStyle();
		style.setColor(DemoColors.DEFAULT_FOREGROUND);
		style.setBackground(new RectangularBackground(DemoColors.DEFAULT_BACKGROUND));
		style.setFont(Fonts.getDefaultFont());
		style.setHorizontalAlignment(Alignment.HCENTER);
		style.setVerticalAlignment(Alignment.VCENTER);

		style = stylesheet.getSelectorStyle(new ClassSelector(TITLE_BAR_CLASSSELECTOR));
		style.setBackground(new RectangularBackground(DemoColors.CORAL));
		style.setColor(Colors.WHITE);
		style.setPadding(new UniformOutline(10));
		style.setBorder(new FlexibleRectangularBorder(DemoColors.POMEGRANATE, 0, 0, 2, 0));

		style = stylesheet.getSelectorStyle(
				new AndCombinator(new ClassSelector(TITLE_BAR_CLASSSELECTOR), new StateSelector(ImageButton.ACTIVE)));
		style.setBackground(new RectangularBackground(0xcf4520));

		style = stylesheet.getSelectorStyle(new ClassSelector(ROUNDED_CORNER_CLASSSELECTOR));
		style.setVerticalAlignment(Alignment.TOP);
		setRoundedCornerStyle(style);

		style = stylesheet.getSelectorStyle(new ClassSelector(ROUNDED_CORNER_BOTTOM_CLASSSELECTOR));
		style.setVerticalAlignment(Alignment.BOTTOM);
		setRoundedCornerStyle(style);

		style = stylesheet.getSelectorStyle(new ClassSelector(TITLE_CLASSSELECTOR));
		style.setMargin(new FlexibleOutline(0, 15, 8, 15));
		style.setBorder(new FlexibleRectangularBorder(DemoColors.CORAL, 0, 0, 2, 0));
		style.setPadding(new UniformOutline(9));

		style = stylesheet.getSelectorStyle(new ClassSelector(PageHelper.CONTENT_CLASSSELECTOR));
		style.setBorder(new FlexibleRectangularBorder(DemoColors.EMPTY_SPACE, 0, 0, 0, PageHelper.LEFT_PADDING));
		style.setPadding(new FlexibleOutline(0, 6, 20, 6));
	}

	private static void setRoundedCornerStyle(EditableStyle roundedCornerBottomStyle) {
		roundedCornerBottomStyle.setHorizontalAlignment(Alignment.LEFT);
		roundedCornerBottomStyle.setColor(Colors.BLACK);
		roundedCornerBottomStyle.setDimension(OptimalDimension.OPTIMAL_DIMENSION_XY);
		roundedCornerBottomStyle.setBackground(NoBackground.NO_BACKGROUND);
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

		ImageWidget verticalTitle = new ImageWidget(MICROEJ_BANNER);
		dock.setCenterChild(verticalTitle);

		return dock;
	}

	private static Widget createTitleButton(boolean canGoBack) {
		Widget widget;
		if (canGoBack) {
			ImageButton imageButton = new ImageButton(MENU);
			imageButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick() {
					Navigation.showMainPage();
				}
			});
			widget = imageButton;
		} else {
			widget = new ImageWidget(ICON);
		}
		widget.addClassSelector(TITLE_BAR_CLASSSELECTOR);
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
		titleBar.requestLayOut();
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
			protected RenderPolicy createRenderPolicy() {
				return new OverlapRenderPolicy(this);
			}
		};
	}
}
