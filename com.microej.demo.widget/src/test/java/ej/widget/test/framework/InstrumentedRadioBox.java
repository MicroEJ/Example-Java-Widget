/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test.framework;

import ej.annotation.Nullable;
import ej.microui.display.GraphicsContext;
import ej.microui.event.EventHandler;
import ej.mwt.style.Style;
import ej.mwt.util.Size;
import ej.widget.basic.drawing.RadioBox;

/**
 *
 */
public class InstrumentedRadioBox extends RadioBox implements Instrumented {

	@Nullable
	private Style validateStyle;
	@Nullable
	private Style renderStyle;
	private boolean styleUpdated;
	@Nullable
	private EventHandler eventHandler;

	public InstrumentedRadioBox() {
		setEnabled(true);
	}

	/**
	 * Gets the validateStyle.
	 *
	 * @return the validateStyle.
	 */
	@Override
	@Nullable
	public Style getValidateStyle() {
		return this.validateStyle;
	}

	/**
	 * Gets the renderStyle.
	 *
	 * @return the renderStyle.
	 */
	@Override
	@Nullable
	public Style getRenderStyle() {
		return this.renderStyle;
	}

	/**
	 * Gets the styleUpdated.
	 *
	 * @return the styleUpdated.
	 */
	@Override
	public boolean isStyleUpdated() {
		return this.styleUpdated;
	}

	@Override
	public void reset() {
		this.renderStyle = null;
		this.validateStyle = null;
		this.styleUpdated = false;
	}

	@Override
	public void updateStyle() {
		this.styleUpdated = true;
		super.updateStyle();
	}

	@Override
	protected void renderContent(GraphicsContext g, Size size) {
		this.renderStyle = getStyle();
		super.renderContent(g, size);
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		this.validateStyle = getStyle();
		super.computeContentOptimalSize(size);
	}

	@Override
	public void setEventHandler(EventHandler eventHandler) {
		this.eventHandler = eventHandler;
	}

	@Override
	public boolean handleEvent(int event) {
		EventHandler eventHandler = this.eventHandler;
		if (eventHandler != null) {
			return eventHandler.handleEvent(event);
		}
		return super.handleEvent(event);
	}

}
