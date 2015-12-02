/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.microej.demo.widgets.page;

import java.io.IOException;
import java.io.InputStream;

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
	protected Widget createMainContent() {
		String description = readDescription();

		Label text = new Label();
		text.addClassSelector(ClassSelectors.MULTILINE);
		text.setText(description);
		return new ScrollComposite(text, true);
	}

	private String readDescription() {
		try {
			InputStream inputStream = ScrollableTextPage.class.getResourceAsStream("description.txt"); //$NON-NLS-1$
			StringBuilder textBuffer = new StringBuilder();
			byte[] buffer = new byte[128];
			while (inputStream.available() != 0) {
				int read = inputStream.read(buffer);
				textBuffer.append(new String(buffer, 0, read));
			}
			return textBuffer.toString();
		} catch (IOException e) {
			return ""; //$NON-NLS-1$
		}
	}

}
