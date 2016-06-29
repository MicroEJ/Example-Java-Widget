/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package ej.demo.ui.widget.page;

import java.io.IOException;
import java.io.InputStream;

import ej.container.List;
import ej.container.Scroll;
import ej.demo.ui.widget.style.ClassSelectors;
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
		List listComposite = new List(false);

		add("MicroEJ SDK", "sdk.txt", listComposite); //$NON-NLS-1$ //$NON-NLS-2$
		add("MicroEJ Studio", "studio.txt", listComposite); //$NON-NLS-1$ //$NON-NLS-2$
		add("MicroEJ Application Store", "store.txt", listComposite); //$NON-NLS-1$ //$NON-NLS-2$
		add("MicroEJ OS", "os.txt", listComposite); //$NON-NLS-1$ //$NON-NLS-2$

		Scroll scroll = new Scroll(false, true);
		scroll.setWidget(listComposite);
		return scroll;
	}

	private void add(String title, String filename, List listComposite) {
		Label titleLabel = new Label();
		titleLabel.addClassSelector(ClassSelectors.TEXT_TITLE);
		titleLabel.setText(title);

		String description = read(filename);
		Label descriptionLabel = new Label();
		descriptionLabel.addClassSelector(ClassSelectors.MULTILINE);
		descriptionLabel.setText(description);

		Label emptyLabel = new Label();

		listComposite.add(titleLabel);
		listComposite.add(descriptionLabel);
		listComposite.add(emptyLabel);
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
