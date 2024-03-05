/*
 * Copyright 2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */

package com.microej.demo.widget.test;

import ej.annotation.Nullable;
import ej.microui.display.GraphicsContext;
import ej.mwt.Desktop;
import ej.mwt.Widget;
import ej.mwt.render.DefaultRenderPolicy;
import ej.mwt.render.RenderPolicy;

class TestDesktop extends Desktop {

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
