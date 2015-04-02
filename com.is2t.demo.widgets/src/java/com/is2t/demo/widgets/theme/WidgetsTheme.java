/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.widgets.theme;

import com.is2t.demo.widgets.widget.CheckboxRenderer;
import com.is2t.demo.widgets.widget.IconToggleButtonRenderer;
import com.is2t.demo.widgets.widget.LinedScaleRenderer;
import com.is2t.demo.widgets.widget.LinedToggleButtonRenderer;
import com.is2t.demo.widgets.widget.WidgetsPageRenderer;
import com.is2t.demo.widgets.widget.WidgetsWheelRenderer;
import com.is2t.mwt.widgets.button.renderer.ButtonRenderer;
import com.is2t.mwt.widgets.button.renderer.PictoButtonRenderer;
import com.is2t.mwt.widgets.label.renderer.HeadlineLabelRenderer;
import com.is2t.mwt.widgets.label.renderer.LeftIconLabelRenderer;
import com.is2t.mwt.widgets.label.renderer.MultiLineLabelRenderer;
import com.is2t.mwt.widgets.label.renderer.TitleLabelRenderer;
import com.is2t.mwt.widgets.renderers.RadioButtonRenderer;
import com.is2t.mwt.widgets.renderers.plain.util.PlainDrawer;
import com.is2t.mwt.widgets.renderers.util.Drawer;
import com.is2t.mwt.widgets.scroll.ScrollBarRenderer;
import com.is2t.mwt.widgets.spinner.MultipleSpinnerRenderer;

import ej.mwt.rendering.Look;
import ej.mwt.rendering.Theme;

public class WidgetsTheme extends Theme {

	@Override
	public String getName() {
		return null;
	}

	@Override
	protected void populate() {
		Drawer drawer = new PlainDrawer();
		
		add(new MultiLineLabelRenderer());
		add(new LeftIconLabelRenderer());
		add(new HeadlineLabelRenderer());
		add(new WidgetsPageRenderer());
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
