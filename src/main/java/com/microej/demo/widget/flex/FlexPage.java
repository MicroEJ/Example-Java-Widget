/*
 * Java
 *
 * Copyright 2023-2025 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.flex;

import com.microej.demo.widget.common.DemoColors;
import com.microej.demo.widget.common.Fonts;
import com.microej.demo.widget.common.Page;
import com.microej.demo.widget.common.scroll.Scroll;
import com.microej.demo.widget.common.scroll.ScrollableList;

import ej.microui.display.Colors;
import ej.mwt.Widget;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.background.RectangularBackground;
import ej.mwt.style.dimension.OptimalDimension;
import ej.mwt.style.outline.FlexibleOutline;
import ej.mwt.style.outline.UniformOutline;
import ej.mwt.style.outline.border.RectangularBorder;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.ClassSelector;
import ej.mwt.stylesheet.selector.StateSelector;
import ej.mwt.stylesheet.selector.TypeSelector;
import ej.mwt.stylesheet.selector.combinator.AndCombinator;
import ej.mwt.util.Alignment;
import ej.widget.basic.Button;
import ej.widget.basic.Label;
import ej.widget.basic.OnClickListener;
import ej.widget.container.Dock;
import ej.widget.container.Flex;
import ej.widget.container.Flex.Align;
import ej.widget.container.Flex.Direction;
import ej.widget.container.Flex.Justify;
import ej.widget.container.LayoutOrientation;
import ej.widget.container.List;

/**
 * Page showing how to use the flex layout with a playground example.
 */
public class FlexPage implements Page {

	private static final int INITIAL_WIDGETS_COUNT = 3;
	private static final int MAXIMUM_WIDGETS_COUNT = 30;

	/** Flex test selector ID. */
	private static final int PAGE_CONTAINER = 200;
	/** Flex label test selector ID. */
	private static final int FLEX_LABEL = 201;
	/** Flex title selector ID. */
	private static final int FLEX_TITLE = 203;
	/** Widget count selector ID. */
	private static final int WIDGET_COUNT_BUTTON = 204;

	private static final int SCROLL_PADDING_SIDES = 15;
	private static final int FLEX_TITLE_MARGIN_TOP = 10;
	private static final int FLEX_TITLE_MARGIN_LEFT = 3;
	private static final int FLEX_TITLE_MARGIN_BOTTOM = 3;
	private static final int FLEX_BUTTONS_PADDING = 10;
	private static final int FLEX_BUTTONS_MARGIN = 5;
	private static final int FLEX_BUTTONS_MARGIN_RIGHT = 15;
	private static final int PLAYGROUND_MARGIN_Y = 10;
	private static final int LABEL_PADDING_SIDES = 16;
	private static final int LABEL_PADDING_TOP_BOTTOM = 10;
	private static final String DIRECTION_TITLE = "Direction"; //$NON-NLS-1$
	private static final String JUSTIFY_TITLE = "Justify"; //$NON-NLS-1$
	private static final String ALIGN_TITLE = "Align"; //$NON-NLS-1$

	/** Default flex direction value */
	public static final Direction DEFAULT_DIRECTION = Direction.ROW;
	/** Default flex justify content value */
	public static final Justify DEFAULT_JUSTIFY = Justify.START;
	/** Default flex align value */
	public static final Align DEFAULT_ALIGN = Align.START;

	@Override
	public String getName() {
		return "Flex"; //$NON-NLS-1$
	}

	@Override
	public Widget getContentWidget() {
		final Dock pageContainer = new Dock();
		pageContainer.addClassSelector(PAGE_CONTAINER);

		Flex playground = new Flex(DEFAULT_DIRECTION, DEFAULT_JUSTIFY, DEFAULT_ALIGN);
		for (int i = 0; i < INITIAL_WIDGETS_COUNT; i++) {
			playground.addChild(createLabel(i + 1));
		}

		Scroll sideMenu = new Scroll(LayoutOrientation.VERTICAL);
		sideMenu.setScrollbarOverlap(true);
		ScrollableList sideMenuList = new ScrollableList(LayoutOrientation.VERTICAL, false);
		sideMenuList.addChild(createCountMenu(playground));
		sideMenuList.addChild(createDirectionMenu(playground));
		sideMenuList.addChild(createJustifyMenu(playground));
		sideMenuList.addChild(createAlignMenu(playground));

		sideMenu.setChild(sideMenuList);

		pageContainer.addChildOnLeft(sideMenu);
		pageContainer.setCenterChild(playground);

		return pageContainer;
	}

	private List createCountMenu(final Flex playground) {
		final List list = new List(LayoutOrientation.HORIZONTAL);

		final Button addWidget = new Button("ADD"); //$NON-NLS-1$
		addWidget.addClassSelector(WIDGET_COUNT_BUTTON);
		addWidget.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick() {
				int count = playground.getChildrenCount();
				if (count < MAXIMUM_WIDGETS_COUNT) {
					playground.addChild(createLabel(count + 1));
					playground.requestLayOut();
				}
			}
		});

		final Button removeWidget = new Button("REMOVE"); //$NON-NLS-1$
		removeWidget.addClassSelector(WIDGET_COUNT_BUTTON);
		removeWidget.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick() {
				int count = playground.getChildrenCount();
				if (count > 0) {
					playground.removeChild(playground.getChild(count - 1));
					playground.requestLayOut();
				}
			}
		});

		list.addChild(addWidget);
		list.addChild(removeWidget);

		return list;
	}

	private List createDirectionMenu(final Flex playground) {
		final List directionMenu = new List(LayoutOrientation.VERTICAL);

		directionMenu.addChild(createFlexTitle(DIRECTION_TITLE));

		Group directionGroup = new Group();
		for (final Direction value : Direction.values()) {
			final FlexMenuItem flexMenuItem = new FlexMenuItem(value.toString(), directionGroup,
					value.equals(DEFAULT_DIRECTION));

			flexMenuItem.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick() {
					playground.setDirection(value);
					playground.requestLayOut();
				}
			});
			directionMenu.addChild(flexMenuItem);
		}

		return directionMenu;
	}

	private List createJustifyMenu(final Flex playground) {
		final List justifyMenu = new List(LayoutOrientation.VERTICAL);

		justifyMenu.addChild(createFlexTitle(JUSTIFY_TITLE));

		Group justifyGroup = new Group();
		for (final Justify value : Justify.values()) {
			final FlexMenuItem flexMenuItem = new FlexMenuItem(value.toString(), justifyGroup,
					value.equals(DEFAULT_JUSTIFY));

			flexMenuItem.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick() {
					playground.setJustify(value);
					playground.requestLayOut();
				}
			});
			justifyMenu.addChild(flexMenuItem);
		}

		return justifyMenu;
	}

	private List createAlignMenu(final Flex playground) {
		final List alignMenu = new List(LayoutOrientation.VERTICAL);

		alignMenu.addChild(createFlexTitle(ALIGN_TITLE));

		Group alignGroup = new Group();
		for (final Align value : Align.values()) {
			final FlexMenuItem flexMenuItem = new FlexMenuItem(value.toString(), alignGroup,
					value.equals(DEFAULT_ALIGN));

			flexMenuItem.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick() {
					playground.setAlign(value);
					playground.requestLayOut();
				}
			});
			alignMenu.addChild(flexMenuItem);
		}

		return alignMenu;
	}

	private static Label createLabel(int id) {
		Label label = new Label(Integer.toString(id));
		label.addClassSelector(FLEX_LABEL);
		return label;
	}

	private static Label createFlexTitle(String title) {
		Label label = new Label(title);
		label.addClassSelector(FLEX_TITLE);
		return label;
	}

	@Override
	public void populateStylesheet(CascadingStylesheet stylesheet) {
		// side menu
		EditableStyle style = stylesheet.getSelectorStyle(new TypeSelector(Scroll.class));
		style.setPadding(new FlexibleOutline(0, 0, 0, SCROLL_PADDING_SIDES));

		// side menu titles
		style = stylesheet.getSelectorStyle(new ClassSelector(FLEX_TITLE));
		style.setHorizontalAlignment(Alignment.LEFT);
		style.setFont(Fonts.getSourceSansPro15px600());
		style.setMargin(
				new FlexibleOutline(FLEX_TITLE_MARGIN_TOP, 0, FLEX_TITLE_MARGIN_BOTTOM, FLEX_TITLE_MARGIN_LEFT));
		style.setColor(DemoColors.DEFAULT_FOREGROUND);

		// all buttons
		style = stylesheet.getSelectorStyle(new TypeSelector(Button.class));
		style.setDimension(OptimalDimension.OPTIMAL_DIMENSION_XY);
		style.setHorizontalAlignment(Alignment.LEFT);
		style.setPadding(new UniformOutline(FLEX_BUTTONS_PADDING));
		style.setMargin(new FlexibleOutline(FLEX_BUTTONS_MARGIN, 0, 0, 0));
		style.setFont(Fonts.getSourceSansPro12px400());
		style.setBackground(new RectangularBackground(DemoColors.ALTERNATE_BACKGROUND));
		style.setColor(Colors.WHITE);

		style = stylesheet.getSelectorStyle(new TypeSelector(FlexMenuItem.class));
		style.setMargin(new FlexibleOutline(FLEX_BUTTONS_MARGIN, FLEX_BUTTONS_MARGIN_RIGHT, 0, 0));

		RectangularBackground orangeRectangularBackground = new RectangularBackground(DemoColors.POMEGRANATE);

		// widget count button ON ACTIVE
		style = stylesheet.getSelectorStyle(
				new AndCombinator(new ClassSelector(WIDGET_COUNT_BUTTON), new StateSelector(StateSelector.ACTIVE)));
		style.setBackground(orangeRectangularBackground);

		// flex option button ON SELECTED
		style = stylesheet.getSelectorStyle(
				new AndCombinator(new TypeSelector(FlexMenuItem.class), new StateSelector(FlexMenuItem.SELECTED)));
		style.setBackground(orangeRectangularBackground);

		// flex playground
		style = stylesheet.getSelectorStyle(new TypeSelector(Flex.class));
		style.setMargin(new UniformOutline(PLAYGROUND_MARGIN_Y));
		style.setPadding(new UniformOutline(1));
		style.setBorder(new RectangularBorder(Colors.WHITE, 1));

		// each widget inside playground
		style = stylesheet.getSelectorStyle(new ClassSelector(FLEX_LABEL));
		style.setColor(Colors.WHITE);
		style.setFont(Fonts.getSourceSansPro16px700());
		style.setPadding(new FlexibleOutline(LABEL_PADDING_TOP_BOTTOM, LABEL_PADDING_SIDES, LABEL_PADDING_TOP_BOTTOM,
				LABEL_PADDING_SIDES));
		style.setMargin(new UniformOutline(1));
		style.setBorder(new RectangularBorder(Colors.WHITE, 1));
	}

}
