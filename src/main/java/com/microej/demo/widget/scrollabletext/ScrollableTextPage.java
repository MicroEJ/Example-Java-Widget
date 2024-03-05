/*
 * Copyright 2021-2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.scrollabletext;

import java.io.IOException;
import java.io.InputStream;

import com.microej.demo.widget.common.DemoColors;
import com.microej.demo.widget.common.Fonts;
import com.microej.demo.widget.common.Page;
import com.microej.demo.widget.common.RenderableLabel;
import com.microej.demo.widget.common.scroll.Scroll;
import com.microej.demo.widget.common.scroll.ScrollableList;
import com.microej.demo.widget.common.scroll.Scrollbar;
import com.microej.demo.widget.scrollabletext.widget.LineWrappingLabel;

import ej.drawing.ShapePainter.Cap;
import ej.mwt.Widget;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.background.NoBackground;
import ej.mwt.style.dimension.FixedDimension;
import ej.mwt.style.outline.FlexibleOutline;
import ej.mwt.style.outline.UniformOutline;
import ej.mwt.style.outline.border.FlexibleRectangularBorder;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.ClassSelector;
import ej.mwt.stylesheet.selector.FirstChildSelector;
import ej.mwt.stylesheet.selector.TypeSelector;
import ej.mwt.stylesheet.selector.combinator.AndCombinator;
import ej.mwt.util.Alignment;
import ej.widget.container.LayoutOrientation;
import ej.widget.container.List;

/**
 * Page showing a scrollable text.
 */
public class ScrollableTextPage implements Page {

	private static final int CLASS_TEXT_TITLE = 1653;
	private static final int CLASS_TEXT_CONTENT = 1654;

	private static final int INPUT_BUFFER_SIZE = 128;

	private static final int SCROLLBAR_WIDTH = 10;

	// The titlebar has a margin which is set in PageHelper addCommonStyle see TITLE_CLASSSELECTOR
	private static final int TITLEBAR_MARGIN_BOTTOM = 8;
	private static final int TITLEBAR_MARGIN_SIDES = 15;

	private static final int MARGIN_TEXT_TITLE = 10;
	private static final int SCROLL_MARGIN_SIDES = 10 + TITLEBAR_MARGIN_SIDES;
	private static final int SCROLL_MARGIN_TOP = MARGIN_TEXT_TITLE - TITLEBAR_MARGIN_BOTTOM;

	@Override
	public String getName() {
		return "Scrollable Text"; //$NON-NLS-1$
	}

	@Override
	public void populateStylesheet(CascadingStylesheet stylesheet) {
		EditableStyle style = stylesheet.getSelectorStyle(new TypeSelector(Scrollbar.class));
		style.setBackground(NoBackground.NO_BACKGROUND);
		style.setDimension(new FixedDimension(SCROLLBAR_WIDTH, Widget.NO_CONSTRAINT));
		style.setPadding(new UniformOutline(1));
		style.setColor(DemoColors.DEFAULT_FOREGROUND);

		style = stylesheet.getSelectorStyle(new TypeSelector(Scroll.class));
		style.setMargin(new FlexibleOutline(SCROLL_MARGIN_TOP, SCROLL_MARGIN_SIDES, 0, SCROLL_MARGIN_SIDES));
		style.setBackground(NoBackground.NO_BACKGROUND);
		style = stylesheet.getSelectorStyle(new TypeSelector(ScrollableList.class));
		style.setBackground(NoBackground.NO_BACKGROUND);

		style = stylesheet.getSelectorStyle(new ClassSelector(CLASS_TEXT_TITLE));
		style.setFont(Fonts.getSourceSansPro16px700());
		style.setHorizontalAlignment(Alignment.LEFT);
		style.setVerticalAlignment(Alignment.TOP);
		style.setBorder(new FlexibleRectangularBorder(DemoColors.DEFAULT_BORDER, 0, 0, 1, 0));
		style.setMargin(new FlexibleOutline(MARGIN_TEXT_TITLE, 0, MARGIN_TEXT_TITLE, 0));

		style = stylesheet.getSelectorStyle(
				new AndCombinator(new ClassSelector(CLASS_TEXT_TITLE), FirstChildSelector.FIRST_CHILD_SELECTOR));
		style.setMargin(new FlexibleOutline(0, 0, MARGIN_TEXT_TITLE, 0));

		style = stylesheet.getSelectorStyle(new ClassSelector(CLASS_TEXT_CONTENT));
		style.setFont(Fonts.getSourceSansPro12px400());
		style.setHorizontalAlignment(Alignment.LEFT);
		style.setVerticalAlignment(Alignment.TOP);

	}

	@Override
	public Widget getContentWidget() {
		Scroll scroll = new Scroll(LayoutOrientation.VERTICAL);
		scroll.setScrollBarCaps(Cap.ROUNDED);
		scroll.setScrollbarOverlap(true);
		ScrollableList list = new ScrollableList(LayoutOrientation.VERTICAL, false);

		addTitle("Lorem Ipsum", list); //$NON-NLS-1$
		addTextFromFile("/resources/lipsum-1.txt", list); //$NON-NLS-1$
		addTitle("Dolor Si Amet", list); //$NON-NLS-1$
		addTextFromFile("/resources/lipsum-2.txt", list); //$NON-NLS-1$

		scroll.setChild(list);

		return scroll;
	}

	private void addTitle(String title, List list) {
		RenderableLabel titleLabel = new RenderableLabel();
		titleLabel.addClassSelector(CLASS_TEXT_TITLE);
		titleLabel.setText(title);
		list.addChild(titleLabel);
	}

	private void addTextFromFile(String filename, List list) {
		String description = read(filename);

		LineWrappingLabel descriptionLabel = new LineWrappingLabel(description);
		descriptionLabel.addClassSelector(CLASS_TEXT_CONTENT);

		list.addChild(descriptionLabel);
	}

	private String read(String filename) {
		String ret = ""; //$NON-NLS-1$
		try {
			InputStream inputStream = ScrollableTextPage.class.getResourceAsStream(filename);
			if (inputStream != null) {
				StringBuilder textBuffer = new StringBuilder();
				byte[] buffer = new byte[INPUT_BUFFER_SIZE];
				while (inputStream.available() != 0) {
					int read = inputStream.read(buffer);
					textBuffer.append(new String(buffer, 0, read));
				}
				ret = textBuffer.toString();
			}
		} catch (IOException e) {
			// Ignore Exception. In case of read error we just don't display anything.
		}
		return ret;
	}
}
