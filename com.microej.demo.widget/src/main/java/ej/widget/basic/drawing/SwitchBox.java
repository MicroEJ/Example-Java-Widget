/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.basic.drawing;

import ej.drawing.ShapePainter;
import ej.drawing.ShapePainter.Cap;
import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.mwt.style.Style;
import ej.mwt.util.Alignment;
import ej.mwt.util.Size;
import ej.widget.basic.Box;

/**
 * Implementation of a box that represents a switch.
 * <p>
 * The size of a switch box is dependent on the font size.
 * <p>
 * This example shows simple switch boxes (one checked, the other not):
 *
 * <pre>
 * SwitchBox switchBox = new SwitchBox();
 * switchBox.setChecked(true);
 * SwitchBox switchBox2 = new SwitchBox();
 * </pre>
 *
 * <img src="../doc-files/switchbox-simple.png" alt="Switch box.">
 * <p>
 * This example shows a styled switche box:
 *
 * <pre>
 * SwitchBox switchBox = new SwitchBox();
 * switchBox.setChecked(true);
 * SwitchBox switchBox2 = new SwitchBox();
 *
 * CascadingStylesheet stylesheet = new CascadingStylesheet();
 * StyleHelper.setStylesheet(stylesheet);
 *
 * EditableStyle switchBoxStyle = stylesheet.addRule(new TypeSelector(SwitchBox.class));
 * switchBoxStyle.setBorder(new UniformRoundedBorder(Colors.BLACK, Integer.MAX_VALUE, 2));
 * switchBoxStyle.setColor(Colors.BLUE);
 * switchBoxStyle.setPadding(new FlexibleOutline(4, 20, 4, 4));
 * switchBoxStyle.setAlignment(AlignmentHelper.LEFT_VCENTER);
 *
 * EditableStyle checkedSwitchboxStyle = stylesheet
 * 		.addRule(new AndCombinator(new TypeSelector(SwitchBox.class), new StateSelector(State.Checked)));
 * switchBoxStyle.setBorder(new UniformRoundedBorder(Colors.BLUE, Integer.MAX_VALUE, 2));
 * checkedSwitchboxStyle.setPadding(new FlexibleOutline(4, 4, 4, 20));
 * checkedSwitchboxStyle.setAlignment(AlignmentHelper.RIGHT_VCENTER);
 * </pre>
 *
 * <img src="../doc-files/switchbox-styled-checked.png" alt="Styled switch box.">
 */
public class SwitchBox extends Box {

	private static final int THICKNESS = 2;
	private static final int MINIMUM_SIZE = 12;

	@Override
	protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
		Style style = getStyle();

		g.setColor(style.getColor());
		int horizontalAlignment = style.getHorizontalAlignment();
		int verticalAlignment = style.getVerticalAlignment();
		int switchSize = Math.min(contentWidth, contentHeight) - 2 * THICKNESS;
		int leftX = Alignment.computeLeftX(switchSize, THICKNESS, contentWidth - 2 * THICKNESS, horizontalAlignment);
		int topY = Alignment.computeTopY(switchSize, THICKNESS, contentHeight - 2 * THICKNESS, verticalAlignment);
		if (isChecked()) {
			int centerX = leftX + (switchSize >> 1);
			ShapePainter.drawThickFadedLine(g, centerX, topY, centerX, topY + switchSize, THICKNESS, 1, Cap.ROUNDED,
					Cap.ROUNDED);
		} else {
			ShapePainter.drawThickFadedCircle(g, leftX, topY, switchSize, THICKNESS, 1);
		}
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		Style style = getStyle();
		Font font = style.getFont();
		int referenceSize = Math.max(font.getHeight() >> 1, MINIMUM_SIZE);
		if ((referenceSize & 0x1) == 0) {
			// Forces it to be odd to center the content.
			referenceSize++;
		}
		size.setSize(referenceSize, referenceSize);
	}

}
