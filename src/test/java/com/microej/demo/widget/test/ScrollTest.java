/*
 * Copyright 2023-2024 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */

package com.microej.demo.widget.test;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.microej.demo.widget.common.scroll.Scroll;

import ej.microui.MicroUI;
import ej.microui.display.Colors;
import ej.microui.display.Display;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.mwt.Widget;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.Style;
import ej.mwt.style.outline.UniformOutline;
import ej.mwt.stylesheet.Stylesheet;
import ej.mwt.util.Size;
import ej.widget.container.LayoutOrientation;
import ej.widget.swipe.SwipeEventHandler;

/**
 * Test class for the different scroll implementations.
 * <p>
 * <b>Warning:</b> Since MicroUI doesn't support restart, only one UI test class can be executed in a single run.
 */
public class ScrollTest {

	private static final boolean ANIMATION = true;
	private static final boolean NO_ANIMATION = false;

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
	 * Tests {@link com.microej.demo.widget.common.scroll.Scroll#scrollTo(int, boolean)} with limit values.
	 */
	@Test
	public void testMainScrollTo() throws InterruptedException {
		final TestDesktop desktop = createDesktop();
		final Scroll scroll = createScroll();
		Widget content = createContent();

		scroll.setChild(content);
		desktop.setWidget(scroll);
		desktop.runAndWaitRender(scroll, new Runnable() {
			@Override
			public void run() {
				desktop.requestShow();
			}
		});

		desktop.runAndWaitRender(scroll, new Runnable() {
			@Override
			public void run() {
				scroll.scrollTo(Integer.MAX_VALUE, NO_ANIMATION);
			}
		});

		assertEquals(scroll.getAbsoluteY() + scroll.getContentY() + scroll.getContentHeight(),
				content.getAbsoluteY() + content.getHeight());

		desktop.runAndWaitRender(scroll, new Runnable() {
			@Override
			public void run() {
				scroll.scrollTo(0, NO_ANIMATION);
			}
		});

		assertEquals(scroll.getAbsoluteY() + scroll.getContentY(), content.getAbsoluteY());

		desktop.runAndWaitRender(scroll, new Runnable() {
			@Override
			public void run() {
				scroll.scrollTo(Integer.MAX_VALUE, ANIMATION);
			}
		});
		Thread.sleep(SwipeEventHandler.DEFAULT_DURATION);

		assertEquals(scroll.getAbsoluteY() + scroll.getContentY() + scroll.getContentHeight(),
				content.getAbsoluteY() + content.getHeight());

		desktop.runAndWaitRender(scroll, new Runnable() {
			@Override
			public void run() {
				scroll.scrollTo(0, ANIMATION);
			}
		});
		Thread.sleep(SwipeEventHandler.DEFAULT_DURATION);

		assertEquals(scroll.getAbsoluteY() + scroll.getContentY(), content.getAbsoluteY());
	}

	/**
	 * Tests {@link com.microej.demo.widget.common.scroll.Scroll#scrollTo(int, boolean)} with limit values with a small
	 * content that does not require to scroll.
	 */
	@Test
	public void testMainScrollToSmall() throws InterruptedException {
		final TestDesktop desktop = createDesktop();
		final Scroll scroll = createScroll();
		Widget content = createSmallContent();

		scroll.setChild(content);
		desktop.setWidget(scroll);
		desktop.runAndWaitRender(scroll, new Runnable() {
			@Override
			public void run() {
				desktop.requestShow();
			}
		});

		scroll.scrollTo(Integer.MAX_VALUE, NO_ANIMATION);

		assertEquals(scroll.getAbsoluteY() + scroll.getContentY(), content.getAbsoluteY());

		scroll.scrollTo(0, NO_ANIMATION);

		assertEquals(scroll.getAbsoluteY() + scroll.getContentY(), content.getAbsoluteY());

		scroll.scrollTo(Integer.MAX_VALUE, ANIMATION);
		Thread.sleep(SwipeEventHandler.DEFAULT_DURATION);

		assertEquals(scroll.getAbsoluteY() + scroll.getContentY(), content.getAbsoluteY());

		scroll.scrollTo(0, ANIMATION);
		Thread.sleep(SwipeEventHandler.DEFAULT_DURATION);

		assertEquals(scroll.getAbsoluteY() + scroll.getContentY(), content.getAbsoluteY());
	}

	/**
	 * Tests {@link com.microej.demo.widget.common.scroll.Scroll#scrollToBeginning(boolean)} and
	 * {@link com.microej.demo.widget.common.scroll.Scroll#scrollToEnd(boolean)}.
	 */
	@Test
	public void testMainScrollToLimits() throws InterruptedException {
		final TestDesktop desktop = createDesktop();
		final Scroll scroll = createScroll();
		Widget content = createContent();

		scroll.setChild(content);
		desktop.setWidget(scroll);
		desktop.runAndWaitRender(scroll, new Runnable() {
			@Override
			public void run() {
				desktop.requestShow();
			}
		});

		desktop.runAndWaitRender(scroll, new Runnable() {
			@Override
			public void run() {
				scroll.scrollToEnd(NO_ANIMATION);
			}
		});

		assertEquals(scroll.getAbsoluteY() + scroll.getContentY() + scroll.getContentHeight(),
				content.getAbsoluteY() + content.getHeight());

		desktop.runAndWaitRender(scroll, new Runnable() {
			@Override
			public void run() {
				scroll.scrollToBeginning(NO_ANIMATION);
			}
		});

		assertEquals(scroll.getAbsoluteY() + scroll.getContentY(), content.getAbsoluteY());

		desktop.runAndWaitRender(scroll, new Runnable() {
			@Override
			public void run() {
				scroll.scrollToEnd(ANIMATION);
			}
		});
		Thread.sleep(SwipeEventHandler.DEFAULT_DURATION);

		assertEquals(scroll.getAbsoluteY() + scroll.getContentY() + scroll.getContentHeight(),
				content.getAbsoluteY() + content.getHeight());

		desktop.runAndWaitRender(scroll, new Runnable() {
			@Override
			public void run() {
				scroll.scrollToBeginning(ANIMATION);
			}
		});
		Thread.sleep(SwipeEventHandler.DEFAULT_DURATION);

		assertEquals(scroll.getAbsoluteY() + scroll.getContentY(), content.getAbsoluteY());
	}

	/**
	 * Tests {@link com.microej.demo.widget.common.scroll.Scroll#scrollToBeginning(boolean)} and
	 * {@link com.microej.demo.widget.common.scroll.Scroll#scrollToEnd(boolean)} with a small content that does not
	 * require to scroll.
	 */
	@Test
	public void testMainScrollToLimitsSmall() throws InterruptedException {
		final TestDesktop desktop = createDesktop();
		final Scroll scroll = createScroll();
		Widget content = createSmallContent();

		scroll.setChild(content);
		desktop.setWidget(scroll);
		desktop.runAndWaitRender(scroll, new Runnable() {
			@Override
			public void run() {
				desktop.requestShow();
			}
		});

		scroll.scrollToEnd(NO_ANIMATION);

		assertEquals(scroll.getAbsoluteY() + scroll.getContentY(), content.getAbsoluteY());

		scroll.scrollToBeginning(NO_ANIMATION);

		assertEquals(scroll.getAbsoluteY() + scroll.getContentY(), content.getAbsoluteY());

		scroll.scrollToEnd(ANIMATION);
		Thread.sleep(SwipeEventHandler.DEFAULT_DURATION);

		assertEquals(scroll.getAbsoluteY() + scroll.getContentY(), content.getAbsoluteY());

		scroll.scrollToBeginning(ANIMATION);
		Thread.sleep(SwipeEventHandler.DEFAULT_DURATION);

		assertEquals(scroll.getAbsoluteY() + scroll.getContentY(), content.getAbsoluteY());
	}

	/**
	 * Tests {@link com.microej.demo.widget.common.scroll.Scroll#scrollTo(int, boolean)} directly updates child
	 * coordinates.
	 */
	@Test
	public void testScrollToDirectyUpdatesCoordinates() {
		final TestDesktop desktop = createDesktop();
		final Scroll scroll = createScroll();
		Widget content = createContent();

		scroll.setChild(content);
		desktop.setWidget(scroll);
		desktop.runAndWaitRender(scroll, new Runnable() {
			@Override
			public void run() {
				desktop.requestShow();
			}
		});

		int shift = scroll.getWidth() / 2;

		assertEquals(0, content.getY());
		scroll.scrollTo(shift, NO_ANIMATION);
		assertEquals(-shift, content.getY());
	}

	private static TestDesktop createDesktop() {
		TestDesktop desktop = new TestDesktop();
		desktop.setStylesheet(new Stylesheet() {
			@Override
			public Style getStyle(Widget widget) {
				EditableStyle style = new EditableStyle();
				style.setPadding(new UniformOutline(10));
				return style;
			}
		});
		return desktop;
	}

	private static Scroll createScroll() {
		return new Scroll(LayoutOrientation.VERTICAL);
	}

	/**
	 * Creates a widget twice the height of the display.
	 */
	private static Widget createContent() {
		return new Widget() {
			@Override
			protected void computeContentOptimalSize(Size size) {
				size.setHeight(2 * Display.getDisplay().getHeight());
			}

			@Override
			protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
				int halfHeight = (contentHeight + 1) >> 1;
				g.setColor(Colors.WHITE);
				Painter.fillRectangle(g, 0, 0, contentWidth, halfHeight);
				g.setColor(Colors.BLACK);
				Painter.fillRectangle(g, 0, halfHeight, contentWidth, halfHeight);
			}

		};
	}

	/**
	 * Creates a widget half the height of the display.
	 */
	private static Widget createSmallContent() {
		return new Widget() {
			@Override
			protected void computeContentOptimalSize(Size size) {
				size.setHeight(Display.getDisplay().getHeight() / 2);
			}

			@Override
			protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
				int halfHeight = (contentHeight + 1) >> 1;
				g.setColor(Colors.WHITE);
				Painter.fillRectangle(g, 0, 0, contentWidth, halfHeight);
				g.setColor(Colors.BLACK);
				Painter.fillRectangle(g, 0, halfHeight, contentWidth, halfHeight);
			}

		};
	}

}
