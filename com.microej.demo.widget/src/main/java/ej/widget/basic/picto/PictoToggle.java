/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.basic.picto;

import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.mwt.style.Style;
import ej.mwt.util.Alignment;
import ej.mwt.util.Size;
import ej.widget.basic.Box;

/**
 * A toggle using some pictos to render.
 */
public abstract class PictoToggle extends Box {

	@Override
	protected void renderContent(GraphicsContext g, Size size) {
		Style style = getStyle();
		char picto = isChecked() ? getCheckedPicto() : getUncheckedPicto();
		Font font = style.getFont();
		g.setColor(style.getColor());
		int x = Alignment.computeLeftX(font.charWidth(picto), 0, size.getWidth(), Alignment.HCENTER);
		int y = Alignment.computeTopY(font.getHeight(), 0, size.getHeight(), Alignment.VCENTER);
		Painter.drawChar(g, font, picto, x, y);
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		Style style = getStyle();
		Font font = style.getFont();
		char picto = isChecked() ? getCheckedPicto() : getUncheckedPicto();
		size.setSize(font.charWidth(picto), font.getHeight());
	}

	/**
	 * Gets the picto corresponding to the checked state of the toggle.
	 *
	 * @return the picto corresponding to the checked state of the toggle.
	 */
	protected abstract char getCheckedPicto();

	/**
	 * Gets the picto corresponding to the unchecked state of the toggle.
	 *
	 * @return the picto corresponding to the unchecked state of the toggle.
	 */
	protected abstract char getUncheckedPicto();

}
