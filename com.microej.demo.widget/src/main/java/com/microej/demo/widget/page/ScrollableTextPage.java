/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package com.microej.demo.widget.page;

import java.io.IOException;
import java.io.InputStream;

import com.microej.demo.widget.style.ClassSelectors;

import ej.widget.basic.Label;
import ej.widget.container.List;
import ej.widget.container.Scroll;
import ej.widget.container.ScrollableList;
import ej.widget.container.util.LayoutOrientation;

/**
 * This page illustrates the scrollable text.
 */
public class ScrollableTextPage extends AbstractDemoPage {

	/**
	 * Creates a scrollable text page.
	 */
	public ScrollableTextPage() {
		super(false, "Scrollable text"); //$NON-NLS-1$

		List listComposite = new ScrollableList(LayoutOrientation.VERTICAL);
		listComposite.addClassSelector(ClassSelectors.TEXT_SCROLL);

		add("MicroEJ SDK", "sdk.txt", listComposite); //$NON-NLS-1$ //$NON-NLS-2$
		add("MicroEJ Studio", "studio.txt", listComposite); //$NON-NLS-1$ //$NON-NLS-2$
		add("MicroEJ Application Store", "store.txt", listComposite); //$NON-NLS-1$ //$NON-NLS-2$
		add("MicroEJ OS", "os.txt", listComposite); //$NON-NLS-1$ //$NON-NLS-2$

		Scroll scroll = new Scroll(false, true);
		scroll.setChild(listComposite);
		setCenterChild(scroll);
	}

	private void add(String title, String filename, List listComposite) {
		Label titleLabel = new Label();
		titleLabel.addClassSelector(ClassSelectors.TEXT_TITLE);
		titleLabel.setText(title);

		String description = read(filename);
		Label descriptionLabel = new Label();
		descriptionLabel.addClassSelector(ClassSelectors.MULTILINE);
		descriptionLabel.setText(description);

		listComposite.addChild(titleLabel);
		listComposite.addChild(descriptionLabel);
	}

	private String read(String filename) {
		try {
			InputStream inputStream = ScrollableTextPage.class.getResourceAsStream(filename);
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
