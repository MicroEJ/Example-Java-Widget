/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.widgets.widget.theme;

import com.is2t.demo.widgets.widget.BorderCompositeWithBackgroundRenderer;
import com.is2t.demo.widgets.widget.CheckboxRenderer;
import com.is2t.demo.widgets.widget.IconToggleButtonRenderer;
import com.is2t.demo.widgets.widget.LinedScaleRenderer;
import com.is2t.demo.widgets.widget.LinedToggleButtonRenderer;
import com.is2t.demo.widgets.widget.WidgetsWheelRenderer;

import ej.mwt.rendering.Look;
import ej.mwt.rendering.Theme;
import ej.widgets.widgets.button.renderer.ButtonRenderer;
import ej.widgets.widgets.button.renderer.PictoButtonRenderer;
import ej.widgets.widgets.label.renderer.HeadlineLabelRenderer;
import ej.widgets.widgets.label.renderer.LeftIconLabelRenderer;
import ej.widgets.widgets.label.renderer.MultiLineLabelRenderer;
import ej.widgets.widgets.label.renderer.TitleLabelRenderer;
import ej.widgets.widgets.renderers.RadioButtonRenderer;
import ej.widgets.widgets.renderers.plain.util.PlainDrawer;
import ej.widgets.widgets.renderers.util.Drawer;
import ej.widgets.widgets.scroll.ScrollBarRenderer;
import ej.widgets.widgets.spinner.MultipleSpinnerRenderer;

/**
 * Theme of the application.
 */
public class WidgetsTheme extends Theme {

	@Override
	public String getName() {
		return null;
	}

	@Override
	protected void populate() {
		Drawer drawer = new PlainDrawer();

		add(new MultiLineLabelRenderer());
		add(new BorderCompositeWithBackgroundRenderer());
		add(new LeftIconLabelRenderer());
		add(new HeadlineLabelRenderer());
		add(new LinedToggleButtonRenderer());
		add(new MultipleSpinnerRenderer());
		add(new WidgetsWheelRenderer());
		add(new PictoButtonRenderer(drawer));
		add(new LinedScaleRenderer());
		add(new RadioButtonRenderer(drawer));
		add(new TitleLabelRenderer());
		add(new CheckboxRenderer());
		add(new ScrollBarRenderer());
		add(new IconToggleButtonRenderer());
		add(new ButtonRenderer(drawer));
	}

	@Override
	public Look getDefaultLook() {
		return new WidgetsLook();
	}

	@Override
	public boolean isStandard() {
		return false;
	}
}
