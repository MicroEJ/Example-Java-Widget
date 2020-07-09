/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.basic.drawing;

import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.mwt.style.Style;
import ej.mwt.util.Size;
import ej.widget.basic.Box;

/**
 * Implementation of a box that represents a check box.
 * <p>
 * The size of a check box is dependent on the font size.
 * <p>
 * This example shows a simple check box:
 *
 * <pre>
 * CheckBox checkbox = new CheckBox();
 * checkbox.setChecked(true);
 * </pre>
 *
 * <img src="../doc-files/checkbox-simple.png" alt="Simple check box.">
 * <p>
 * This example shows a styled check box:
 *
 * <pre>
 * CascadingStylesheet stylesheet = new CascadingStylesheet();
 * StyleHelper.setStylesheet(stylesheet);
 *
 * EditableStyle checkboxStyle = new EditableStyle();
 * checkboxStyle.setBorder(new UniformRectangularBorder(Colors.BLACK, 2));
 * checkboxStyle.setPadding(new UniformOutline(2));
 * checkboxStyle.setColor(Colors.BLUE);
 * stylesheet.addRule(new TypeSelector(CheckBox.class), checkboxStyle);
 * </pre>
 *
 * <img src="../doc-files/checkbox-styled.png" alt="Styled check box.">
 * <p>
 * This example shows two styled check box, one checked, one unchecked. The checked one border color changes:
 *
 * <pre>
 * CheckBox checkbox = new CheckBox();
 * checkbox.setChecked(true);
 * CheckBox checkbox2 = new CheckBox();
 *
 * CascadingStylesheet stylesheet = new CascadingStylesheet();
 * StyleHelper.setStylesheet(stylesheet);
 *
 * EditableStyle checkboxStyle = stylesheet.addRule(new TypeSelector(CheckBox.class));
 * checkboxStyle.setBorder(new UniformRectangularBorder(Colors.BLACK, 2));
 * checkboxStyle.setPadding(new UniformOutline(2));
 * checkboxStyle.setColor(Colors.BLUE);
 *
 * EditableStyle checkedCheckboxStyle = stylesheet
 * 		.addRule(new AndCombinator(new TypeSelector(CheckBox.class), new StateSelector(State.Checked)));
 * checkboxStyle.setBorder(new UniformRectangularBorder(Colors.BLUE, 2));
 * </pre>
 *
 * <img src="../doc-files/checkbox-styled-checked.png" alt="Styled checked check box.">
 *
 */
public class CheckBox extends Box {

	@Override
	protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
		if (isChecked()) {
			Style style = getStyle();

			int checkSize = Math.min(contentWidth, contentHeight);
			g.setColor(style.getColor());
			Painter.fillRectangle(g, 0, 0, checkSize, checkSize);
		}
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		Style style = getStyle();
		int referenceSize = style.getFont().getHeight() >> 1;
		size.setSize(referenceSize, referenceSize);
	}

}
