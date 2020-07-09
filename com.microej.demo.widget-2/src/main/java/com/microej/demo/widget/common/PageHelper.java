/*
 * Copyright 2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package com.microej.demo.widget.common;

import ej.microui.display.Colors;
import ej.microui.display.GraphicsContext;
import ej.mwt.Desktop;
import ej.mwt.Widget;
import ej.mwt.render.OverlapRenderPolicy;
import ej.mwt.render.RenderPolicy;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.background.Background;
import ej.mwt.style.background.RectangularBackground;
import ej.mwt.style.dimension.OptimalDimension;
import ej.mwt.style.outline.UniformOutline;
import ej.mwt.style.outline.border.FlexibleRectangularBorder;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.ClassSelector;
import ej.mwt.stylesheet.selector.StateSelector;
import ej.mwt.stylesheet.selector.combinator.AndCombinator;
import ej.mwt.util.Alignment;
import ej.mwt.util.Size;
import ej.widget.basic.ButtonImage;
import ej.widget.basic.ImageWidget;
import ej.widget.container.OverlapContainer;
import ej.widget.container.SimpleDock;
import ej.widget.container.util.LayoutOrientation;
import ej.widget.listener.OnClickListener;
import ej.widget.util.States;

/**
 * Helps to create the title bar a the page structure.
 */
public class PageHelper {

	/**
	 *
	 */
	private static final class OpaqueEmptyBackgroun implements Background {
		@Override
		public boolean isTransparent() {
			return false;
		}

		@Override
		public void apply(GraphicsContext g, Size size) {
		}
	}

	public static final int TITLE_BAR_WIDTH = 44; // FIXME use one of the image width
	public static final int LEFT_PADDING = 4;

	private static final String MICROEJ_BANNER = "/images/microej_banner.png"; //$NON-NLS-1$
	private static final String ICON = "/images/ic-app_layout.png"; //$NON-NLS-1$
	private static final String MENU = "/images/ic-list.png"; //$NON-NLS-1$
	private static final String ROUNDED_CORNER = "/images/rounded-corner.png"; //$NON-NLS-1$
	private static final String ROUNDED_CORNER_BOTTOM = "/images/rounded-corner-bottom.png"; //$NON-NLS-1$
	private static final int CORAL = 0xee502e;
	private static final int POMEGRANATE = 0xcf4520;
	private static final int TITLE_BAR_CLASSSELECTOR = 44696;
	private static final int ROUNDED_CORNER_CLASSSELECTOR = 44697;
	private static final int ROUNDED_CORNER_BOTTOM_CLASSSELECTOR = 44698;

	/**
	 * Adds the title bar style to a stylesheet.
	 *
	 * @param stylesheet
	 *            the stylesheet.
	 */
	public static void addTitleBarStyle(CascadingStylesheet stylesheet) {
		EditableStyle titleBarStyle = stylesheet.getSelectorStyle(new ClassSelector(TITLE_BAR_CLASSSELECTOR));
		titleBarStyle.setBackground(new RectangularBackground(CORAL));
		titleBarStyle.setColor(Colors.WHITE);
		titleBarStyle.setPadding(new UniformOutline(10));
		titleBarStyle.setBorder(new FlexibleRectangularBorder(POMEGRANATE, 0, 0, 2, 0));

		EditableStyle titleBarPressedStyle = stylesheet.getSelectorStyle(
				new AndCombinator(new ClassSelector(TITLE_BAR_CLASSSELECTOR), new StateSelector(States.ACTIVE)));
		titleBarPressedStyle.setBackground(new RectangularBackground(0xcf4520));

		EditableStyle roundedCornerStyle = stylesheet.getSelectorStyle(new ClassSelector(ROUNDED_CORNER_CLASSSELECTOR));
		roundedCornerStyle.setVerticalAlignment(Alignment.TOP);
		setRoundedCornerStyle(roundedCornerStyle);

		EditableStyle roundedCornerBottomStyle = stylesheet
				.getSelectorStyle(new ClassSelector(ROUNDED_CORNER_BOTTOM_CLASSSELECTOR));
		roundedCornerBottomStyle.setVerticalAlignment(Alignment.BOTTOM);
		setRoundedCornerStyle(roundedCornerBottomStyle);
	}

	private static void setRoundedCornerStyle(EditableStyle roundedCornerBottomStyle) {
		roundedCornerBottomStyle.setHorizontalAlignment(Alignment.LEFT);
		roundedCornerBottomStyle.setColor(Colors.BLACK);
		roundedCornerBottomStyle.setDimension(OptimalDimension.OPTIMAL_DIMENSION_XY);
		roundedCornerBottomStyle.setBackground(new OpaqueEmptyBackgroun());
	}

	/**
	 * Creates a title bar.
	 *
	 * @param canGoBack
	 *            <code>true</code> to add a back button, <code>false</code> to add an icon.
	 * @return the title bar.
	 */
	public static Widget createTitleBar(boolean canGoBack) {
		SimpleDock dock = new SimpleDock(LayoutOrientation.VERTICAL);

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
		dock.setFirstChild(top);

		ImageWidget verticalTitle = new ImageWidget(MICROEJ_BANNER);
		dock.setCenterChild(verticalTitle);

		return dock;
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

	public static Desktop createDesktop() {
		return new Desktop() {
			@Override
			protected RenderPolicy createRenderPolicy() {
				return new OverlapRenderPolicy(this);
			}
		};
	}

	private PageHelper() {
	}

}
