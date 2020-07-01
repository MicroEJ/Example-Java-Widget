/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test.framework;

import ej.microui.display.Colors;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.mwt.Widget;
import ej.mwt.util.Size;

/**
 *
 */
public abstract class MyWidget extends Widget implements InstrumentedItem {

	private boolean isTransparent;
	private boolean isPaint;
	private int translateX;
	private int translateY;
	private int clipX;
	private int clipY;
	private int clipWidth;
	private int clipHeight;
	private boolean isOnAttached;
	private boolean isOnDetached;
	private boolean isOnShown;
	private boolean isOnHidden;
	private boolean isValidated;

	/**
	 *
	 */
	public MyWidget() {
		super();
	}

	public void setTransparent(boolean isTransparent) {
		this.isTransparent = isTransparent;
	}

	@Override
	public boolean isTransparent() {
		return this.isTransparent;
	}

	@Override
	public void reset() {
		this.isPaint = false;
		this.translateX = 0;
		this.translateY = 0;
		this.clipX = 0;
		this.clipY = 0;
		this.clipWidth = 0;
		this.clipHeight = 0;
		this.isOnAttached = false;
		this.isOnDetached = false;
		this.isOnShown = false;
		this.isOnHidden = false;
		this.isValidated = false;
	}

	@Override
	protected void renderContent(GraphicsContext g, Size size) {
		this.isPaint = true;
		this.translateX = g.getTranslateX();
		this.translateY = g.getTranslateY();
		this.clipX = g.getClipX();
		this.clipY = g.getClipY();
		this.clipWidth = g.getClipWidth();
		this.clipHeight = g.getClipHeight();
		g.setColor((int) (Math.random() * Colors.WHITE));
		Painter.fillRectangle(g, 0, 0, this.getWidth(), this.getHeight());
	}

	@Override
	public boolean isPaint() {
		return this.isPaint;
	}

	@Override
	public int getTranslateX() {
		return this.translateX;
	}

	@Override
	public int getTranslateY() {
		return this.translateY;
	}

	@Override
	public int getClipX() {
		return this.clipX;
	}

	@Override
	public int getClipY() {
		return this.clipY;
	}

	@Override
	public int getClipWidth() {
		return this.clipWidth;
	}

	@Override
	public int getClipHeight() {
		return this.clipHeight;
	}

	@Override
	public boolean hasReceivedEvent() {
		return false;
	}

	@Override
	public int getEvent() {
		return 0;
	}

	@Override
	protected void onAttached() {
		super.onAttached();
		this.isOnAttached = true;
	}

	@Override
	protected void onDetached() {
		super.onDetached();
		this.isOnDetached = true;
	}

	@Override
	public boolean isOnAttached() {
		return this.isOnAttached;
	}

	@Override
	public boolean isOnDetached() {
		return this.isOnDetached;
	}

	@Override
	protected void onShown() {
		super.onShown();
		this.isOnShown = true;
	}

	@Override
	protected void onHidden() {
		super.onHidden();
		this.isOnHidden = true;
	}

	@Override
	public boolean isOnShown() {
		return this.isOnShown;
	}

	@Override
	public boolean isOnHidden() {
		return this.isOnHidden;
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		this.isValidated = true;
		size.setSize(0, 0);
	}

	/**
	 * Gets the isValidated.
	 *
	 * @return the isValidated.
	 */
	@Override
	public boolean isLaidOut() {
		return this.isValidated;
	}

}