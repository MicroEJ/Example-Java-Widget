/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package ej.widget;

import java.util.Iterator;

import ej.microui.display.GraphicsContext;
import ej.mwt.Composite;
import ej.mwt.Widget;
import ej.style.Element;
import ej.style.State;
import ej.style.Style;
import ej.style.container.Rectangle;
import ej.style.selector.InstanceSelector;
import ej.style.util.StyleHelper;

/**
 * A widget composite using a style specified in a style sheet. Especially the style sheet defines a margin, a border
 * and a padding for each widget.
 *
 * DemoWidget modifications:
 *
 * - setEnabled(): don't update style if enabled state hasn't changed
 *
 * - removeClassSelectors(): don't update style if there is no class selector
 */
public abstract class StyledComposite extends Composite implements Element, StyledRenderable {

	// Some (most) of the code in this class is duplicated in all StyledRenderable implementors for footprint
	// optimization (flash & RAM).

	/**
	 * The cached transparent state.
	 */
	private boolean transparent;
	/**
	 * The cached style.
	 */
	private Style style;
	/**
	 * The class selectors.
	 */
	private String[] classSelectors;
	/**
	 * The validated state.
	 */
	private boolean validated;

	/**
	 * Creates a styled composite without class selector.
	 */
	public StyledComposite() {
		this.classSelectors = StyledHelper.EMPTY_STRING_ARRAY;
	}

	@Override
	public boolean isTransparent() {
		return this.transparent;
	}

	@Override
	public Element[] getChildrenElements() {
		Widget[] widgets = getWidgets();
		int widgetsLength = widgets.length;
		Element[] result = new Element[widgetsLength];
		System.arraycopy(widgets, 0, result, 0, widgetsLength);
		return result;
	}

	@Override
	public Element getChild(int index) {
		return (Element) getWidget(index);
	}

	@Override
	public int getChildrenCount() {
		return getWidgetsCount();
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * If the given widget is not an {@link Element}, an {@link IllegalArgumentException} is thrown.
	 *
	 * @throws IllegalArgumentException
	 *             if the given widget is not an element.
	 * @see Element
	 * @see StyledWidget
	 * @see StyledComposite
	 */
	@Override
	protected void add(Widget widget) throws NullPointerException, IllegalArgumentException {
		if (widget instanceof Element) {
			super.add(widget);
		} else if (widget == null) {
			throw new NullPointerException();
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Element getParentElement() {
		return StyledHelper.getParentElement(this);
	}

	@Override
	public void mergeStyle(Style style) {
		StyleHelper.getStylesheet().addRule(new InstanceSelector(this), style);
		updateStyle();
	}

	@Override
	public void removeInstanceStyle() {
		StyleHelper.getStylesheet().removeRule(new InstanceSelector(this));
		updateStyle();
	}

	@Override
	public Style getStyle() {
		if (this.style == null) {
			setStyle(StyledHelper.getStyleFromStylesheet(this));
		}
		return this.style;
	}

	private void setStyle(Style newStyle) {
		this.style = newStyle;
		this.transparent = this.style.getBackground().isTransparent();
	}

	/**
	 * Updates the style of the widget.
	 */
	protected void updateStyle() {
		// Do not update style if not shown. See validate method.
		if (isShown() && updateStyleOnly()) {
			repaint();
		}
	}

	/**
	 * Update widget style without repainting it.
	 *
	 * @return <code>true</code> if the style of this composite or one of its children style has been updated,
	 *         <code>false</code> otherwise.
	 */
	protected boolean updateStyleOnly() {
		Style newStyle = StyledHelper.getStyleFromStylesheet(this);

		boolean updated = false;
		if (!newStyle.equals(this.style)) {
			setStyle(newStyle);
			updated = true;
		}
		Iterator<Widget> iterator = iterator();
		while (iterator.hasNext()) {
			Widget child = iterator.next();
			if (child instanceof StyledComposite) {
				updated |= ((StyledComposite) child).updateStyleOnly();
			} else if (child instanceof StyledWidget) {
				updated |= ((StyledWidget) child).updateStyleOnly();
			}
		}
		return updated;
	}

	@Override
	public void render(GraphicsContext g) {
		StyledHelper.render(g, this);
	}

	@Override
	public void renderContent(GraphicsContext g, Style style, Rectangle bounds) {
		// Do nothing by default.
	}

	@Override
	public void validate(int widthHint, int heightHint) {
		if (isVisible()) {
			setStyle(StyledHelper.getStyleFromStylesheet(this));
			Rectangle bounds = StyledHelper.validate(widthHint, heightHint, this);
			setPreferredSize(bounds.getWidth(), bounds.getHeight());
		} else {
			setPreferredSize(0, 0);
		}
		this.validated = true;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Lays out the children of this composite.
	 */
	@Override
	public abstract Rectangle validateContent(Style style, Rectangle bounds);

	@Override
	public void setBounds(int x, int y, int width, int height) {
		boolean resized = getWidth() != width || getHeight() != height;
		super.setBounds(x, y, width, height);
		if (this.validated || resized) {
			this.validated = false;
			Rectangle bounds = new Rectangle(0, 0, width, height);
			Rectangle contentBounds = StyleHelper.computeContentBounds(bounds, getStyle());
			setBoundsContent(contentBounds);
		}
	}

	/**
	 * Sets the bounds of this widget by taking into account the border, margin and padding specified in the style.
	 *
	 * @param bounds
	 *            the bounds available for the content.
	 */
	protected abstract void setBoundsContent(Rectangle bounds);

	@Override
	public void setEnabled(boolean enable) {
		if (enable != isEnabled()) {
			super.setEnabled(enable);
			updateStyle();
		}
	}

	@Override
	public boolean hasClassSelector(String classSelector) {
		return StyledHelper.hasClassSelector(this.classSelectors, classSelector);
	}

	@Override
	public void addClassSelector(String classSelector) {
		this.classSelectors = StyledHelper.addClassSelector(this.classSelectors, classSelector);
		updateStyle();
	}

	@Override
	public void removeClassSelector(String classSelector) {
		this.classSelectors = StyledHelper.removeClassSelector(this.classSelectors, classSelector);
		updateStyle();
	}

	@Override
	public void setClassSelectors(String classSelector) {
		this.classSelectors = StyledHelper.setClassSelectors(classSelector);
		updateStyle();
	}

	@Override
	public void removeAllClassSelectors() {
		if (this.classSelectors.length > 0) {
			this.classSelectors = StyledHelper.EMPTY_STRING_ARRAY;
			updateStyle();
		}
	}

	@Override
	public boolean isInState(State state) {
		return (state == State.Enabled && isEnabled()) || (state == State.Disabled && !isEnabled());
	}

	@Override
	public String getAttribute(String attribute) {
		return null;
	}

}
