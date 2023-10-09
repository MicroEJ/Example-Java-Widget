/*
 * Copyright 2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */

package com.microej.demo.widget.test;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.microej.demo.widget.common.scroll.Scroll;

import ej.annotation.Nullable;
import ej.microui.MicroUI;
import ej.microui.display.Colors;
import ej.microui.display.Display;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.mwt.Desktop;
import ej.mwt.Widget;
import ej.mwt.render.DefaultRenderPolicy;
import ej.mwt.render.RenderPolicy;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.Style;
import ej.mwt.style.outline.UniformOutline;
import ej.mwt.stylesheet.Stylesheet;
import ej.mwt.util.Size;
import ej.widget.container.LayoutOrientation;

/**
 * Test class for the different scroll implementations.
 * <p>
 * <b>Warning:</b> Since MicroUI doesn't support restart, only one UI test class can be executed in a single run.
 */
public class ScrollTest {

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
	 * Tests {@link com.microej.demo.widget.main.widget.Scroll#scrollTo(int, boolean)} with limit values.
	 */
	@Test
	public void testMainScrollTo() {
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

	private static class TestDesktop extends Desktop {

		private final Object lock;

		private @Nullable Widget lastRendered;
		private boolean waitForRender;
		private boolean renderAcknowledged;

		public TestDesktop() {
			this.lock = new Object();
		}

		@Override
		protected RenderPolicy createRenderPolicy() {
			return new DefaultRenderPolicy(this) {
				@Override
				protected void renderWidget(GraphicsContext g, Widget widget) {
					super.renderWidget(g, widget);
					onWidgetRendered(widget);
				}
			};
		}

		private void onWidgetRendered(Widget widget) {
			Object lock = this.lock;
			synchronized (lock) {
				this.lastRendered = widget;
				if (this.waitForRender) {
					lock.notifyAll();
					this.renderAcknowledged = false;
					do {
						try {
							lock.wait();
						} catch (InterruptedException e) {
							// do nothing
						}
					} while (!this.renderAcknowledged);
				}
			}
		}

		public void runAndWaitRender(Widget widget, @Nullable Runnable runnable) {
			Object lock = this.lock;
			synchronized (lock) {
				this.waitForRender = true;
				this.lastRendered = null;

				if (runnable != null) {
					runnable.run();
				}

				do {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						// do nothing
					}
					this.renderAcknowledged = true;
					lock.notifyAll();
				} while (!widget.equals(this.lastRendered));

				this.waitForRender = false;
			}
		}

	}

}
