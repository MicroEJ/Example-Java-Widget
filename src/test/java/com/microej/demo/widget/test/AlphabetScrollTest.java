/*
 * Copyright 2023-2024 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.microej.demo.widget.common.scroll.Scroll;
import com.microej.demo.widget.scrollalphabet.widget.AlphabetScroll;
import com.microej.demo.widget.scrollalphabet.widget.AlphabetScroll.Indexer;

import ej.microui.MicroUI;
import ej.mwt.Widget;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.dimension.FixedDimension;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.TypeSelector;
import ej.widget.container.LayoutOrientation;

public class AlphabetScrollTest {

	private static final int NON_EMPTY = 20;

	/**
	 * Starts MicroUI.
	 *
	 * @see MicroUI#start
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		MicroUI.start();
	}

	/**
	 * Stops MicroUI.
	 *
	 * @see MicroUI#stop
	 */
	@AfterClass
	public static void tearDownAfterClass() {
		MicroUI.stop();
	}

	/**
	 * Tests {@link com.microej.demo.widget.scrollalphabet.widget.AlphabetScroll#setIndexBeforeContent(boolean)}.
	 */
	@Test
	public void testSetIndexBeforeContent() {
		final TestDesktop desktop = new TestDesktop();
		final CascadingStylesheet stylesheet = new CascadingStylesheet();
		EditableStyle style = stylesheet.getSelectorStyle(new TypeSelector(Scroll.class));
		style.setDimension(new FixedDimension(NON_EMPTY, NON_EMPTY));
		desktop.setStylesheet(stylesheet);
		final Scroll[] scrolls = new Scroll[2];
		final AlphabetScroll scroll = new AlphabetScroll(LayoutOrientation.VERTICAL, createDummyIndexer()) {
			@Override
			protected Scroll createMainScroll(boolean orientation, Widget mainList) {
				Scroll mainScroll = super.createMainScroll(orientation, mainList);
				scrolls[0] = mainScroll;
				return mainScroll;
			}

			@Override
			protected Scroll createSideScroll(boolean orientation, Widget mainList) {
				Scroll sideScroll = super.createSideScroll(orientation, mainList);
				scrolls[1] = sideScroll;
				return sideScroll;
			}
		};
		Scroll mainScroll = scrolls[0];
		Scroll sideScroll = scrolls[1];

		assertNotNull(mainScroll);
		assertNotNull(sideScroll);

		desktop.runAndWaitRender(scroll, new Runnable() {
			@Override
			public void run() {
				desktop.setWidget(scroll);
				desktop.requestShow();
			}
		});

		assertTrue(mainScroll.getX() < sideScroll.getX());

		desktop.runAndWaitRender(scroll, new Runnable() {
			@Override
			public void run() {
				scroll.setIndexBeforeContent(true);
				scroll.requestLayOut();
			}
		});

		assertTrue(mainScroll.getX() > sideScroll.getX());

		desktop.runAndWaitRender(scroll, new Runnable() {
			@Override
			public void run() {
				scroll.setIndexBeforeContent(false);
				scroll.requestLayOut();
			}
		});

		assertTrue(mainScroll.getX() < sideScroll.getX());
	}

	private static Indexer createDummyIndexer() {
		return new AlphabetScroll.Indexer() {
			@Override
			public String getIndex(Widget widget) {
				return "";
			}
		};
	}

}
