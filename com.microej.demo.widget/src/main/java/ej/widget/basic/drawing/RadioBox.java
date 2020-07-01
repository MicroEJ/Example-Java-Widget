/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.basic.drawing;

import ej.drawing.ShapePainter;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.mwt.style.Style;
import ej.mwt.util.Size;
import ej.widget.basic.Box;

/**
 * Implementation of a box that represents a radio button.
 * <p>
 * The size of a radio box is dependent on the font size.
 * <p>
 * This example shows a simple radio box:
 *
 * <pre>
 * RadioBox radioBox = new RadioBox();
 * radioBox.setChecked(true);
 * </pre>
 *
 * <img src="../doc-files/radiobox-simple.png" alt="Simple radio box.">
 * <p>
 * This example shows a styled radio box:
 *
 * <pre>
 * RadioBox radioBox = new RadioBox();
 * radioBox.setChecked(true);
 *
 * CascadingStylesheet stylesheet = new CascadingStylesheet();
 * StyleHelper.setStylesheet(stylesheet);
 *
 * EditableStyle radioBoxStyle = stylesheet.addRule(new TypeSelector(RadioBox.class));
 * radioBoxStyle.setBorder(new UniformRoundedBorder(Colors.BLACK, Integer.MAX_VALUE, 2));
 * radioBoxStyle.setPadding(new UniformOutline(4));
 * radioBoxStyle.setColor(Colors.BLUE);
 * </pre>
 *
 * <img src="../doc-files/radiobox-styled.png" alt="Styled radio box.">
 * <p>
 * This example shows two styled radio box, one checked, one unchecked. The checked one border color changes:
 *
 * <pre>
 * RadioBox radioBox = new RadioBox();
 * radioBox.setChecked(true);
 * RadioBox radioBox2 = new RadioBox();
 *
 * CascadingStylesheet stylesheet = new CascadingStylesheet();
 * StyleHelper.setStylesheet(stylesheet);
 *
 * EditableStyle radioBoxStyle = stylesheet.addRule(new TypeSelector(RadioBox.class));
 * radioBoxStyle.setBorder(new UniformRoundedBorder(Colors.BLACK, Integer.MAX_VALUE, 2));
 * radioBoxStyle.setPadding(new UniformOutline(4));
 * radioBoxStyle.setColor(Colors.BLUE);
 *
 * EditableStyle checkedRadioboxStyle = stylesheet
 * 		.addRule(new AndCombinator(new TypeSelector(RadioBox.class), new StateSelector(State.Checked)));
 * radioBoxStyle.setBorder(new UniformRoundedBorder(Colors.BLUE, Integer.MAX_VALUE, 2));
 * </pre>
 *
 * <img src="../doc-files/radiobox-styled-checked.png" alt="Styled checked radio box.">
 */
public class RadioBox extends Box {

	private static final int ANTIALIASING_THICKNESS = 2;
	private static final int MINIMUM_SIZE = 8;

	@Override
	protected void renderContent(GraphicsContext g, Size size) {
		if (isChecked()) {
			Style style = getStyle();
			int width = size.getWidth();
			int height = size.getHeight();

			g.setColor(style.getColor());
			int radioSize = Math.min(width, height);
			ShapePainter.drawThickFadedCircle(g, ANTIALIASING_THICKNESS, ANTIALIASING_THICKNESS,
					radioSize - (ANTIALIASING_THICKNESS * 2 + 1), ANTIALIASING_THICKNESS, 1);
			Painter.fillCircle(g, 1, 1, radioSize - (2 + 1));
		}
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		Style style = getStyle();
		int referenceSize = Math.max(style.getFont().getHeight() >> 1, MINIMUM_SIZE);
		if ((referenceSize & 0x1) == 0) {
			// Drawing an even circle draws an odd circle -> not centered.
			referenceSize++;
		}
		size.setSize(referenceSize, referenceSize);
	}

}
