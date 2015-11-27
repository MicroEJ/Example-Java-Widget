/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.microej.demo.widgets.page;

import com.microej.demo.widgets.style.ClassSelectors;

import ej.composite.ScrollComposite;
import ej.mwt.Widget;
import ej.widget.basic.Label;

/**
 * This page illustrates the scrollable text.
 */
public class ScrollableTextPage extends AbstractDemoPage {

	@Override
	protected String getTitle() {
		return "Scrollable text"; //$NON-NLS-1$
	}

	@Override
	protected boolean canGoBack() {
		return true;
	}

	@Override
	protected Widget createMainContent() {
		Label text = new Label();
		text.addClassSelector(ClassSelectors.MULTILINE);
		text.setText(
				"This is supposed to be a long text.\nSo, we will try to make it as long as possible by adding word after word of crap and other useless phrases.\n\nThis third part is the more difficult but here we go again with another crap of text!\n\nAfter a quick test, the text is not long enough, we need to add a few more words in this text to have something that can scroll on several pages.");
		return new ScrollComposite(text, true);
	}

}
