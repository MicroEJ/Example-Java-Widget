/*
 * Copyright 2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.autoscrolllabeloncircle.widget;

import com.microej.demo.widget.common.StringOnCirclePainter;
import com.microej.demo.widget.common.StringOnCirclePainter.Direction;

import ej.annotation.Nullable;
import ej.bon.Util;
import ej.drawing.ShapePainter;
import ej.drawing.TransformPainter;
import ej.microui.MicroUI;
import ej.microui.display.BufferedImage;
import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.mwt.Widget;
import ej.mwt.animation.Animation;
import ej.mwt.animation.Animator;
import ej.mwt.style.Style;
import ej.mwt.util.Rectangle;
import ej.mwt.util.Size;

/**
 * A widget that shows a text that scrolls on a circle. The circle represents the text baseline.
 * <p>
 * The text winds along the circle in the specified direction (clockwise or counter-clockwise).
 * <p>
 * The widget draws once the circular text in a {@link BufferedImage} and then draws the rotated image for the scrolling
 * animation.
 */
public class AutoscrollLabelOnCircle extends Widget implements Animation {

	/**
	 * Extra style field for specifying the background color. The color provided with this field will be used for
	 * coloring the background of the buffered image and for hiding the text where relevant.
	 */
	public static final int EXTRA_FIELD_BACKGROUND_COLOR = 0;

	private static final float ANGLE_90 = 90f;

	private static final float ANGLE_180 = 180f;

	private static final float ANGLE_360 = 360f;

	private static final float START_ANGLE_IN_IMAGE = 90f;

	private String text;
	private final float startAngle;
	private final float arcAngle;
	private float angle;
	private final Direction direction;
	private final int duration;
	private int radius;
	private long startTime;
	private float textAngle;
	private float startOfTextAngle;
	@Nullable
	private BufferedImage textImage;

	/**
	 * Creates the scrolling text widget. The text scrolls on a circle that represents the text baseline.
	 *
	 * <p>
	 * The start angle specifies where the text starts along the circle. When the starting angle is 0, the text starts
	 * at the 3 o'clock position (positive X axis).
	 *
	 * <p>
	 * The text winds along the circle in the specified direction (clockwise or counter-clockwise).
	 *
	 * <p>
	 * The arc on which the text will scroll onto starts at {@code startAngle} and ends at
	 * {@code startAngle + arcAngle}.
	 *
	 * <p>
	 * For example, given a start angle of 30°, an arc angle of 120°, the text will scroll in the counter-clockwise
	 * direction, from the 2 o'clock position to the 10 o'clock position.
	 *
	 * <p>
	 * The text will be scrolling along the specified circle arc
	 *
	 *
	 * @param text
	 *            the text to display
	 * @param startAngle
	 *            the start angle, in degrees
	 * @param arcAngle
	 *            the arc angle that the text scrolls onto, in degrees
	 * @param textDirection
	 *            the text direction, clockwise or counter-clockwise
	 * @param scrollDuration
	 *            the scroll duration, in milliseconds
	 *
	 */
	public AutoscrollLabelOnCircle(String text, float startAngle, float arcAngle, Direction textDirection,
			int scrollDuration) {
		this.startAngle = startAngle % ANGLE_360;
		this.arcAngle = arcAngle % ANGLE_360;
		this.text = text;
		this.duration = scrollDuration;
		this.direction = textDirection;
	}

	/**
	 * Sets the text.
	 *
	 * @param text
	 *            the text to set.
	 */
	public void setText(final String text) {
		AutoscrollLabelOnCircle.this.text = text;
		if (isAttached()) {
			MicroUI.callSerially(new Runnable() {
				@Override
				public void run() {
					onTextUpdate();
				}
			});
		}
	}

	@Override
	public boolean tick(long platformTimeMillis) {
		int elapsed = (int) (Util.platformTimeMillis() - this.startTime);
		float fullAngle = this.arcAngle + this.textAngle;
		this.angle = this.startOfTextAngle + fullAngle * elapsed / this.duration - START_ANGLE_IN_IMAGE;
		requestRender();

		if (elapsed > this.duration) {
			this.startTime = Util.platformTimeMillis();
		}

		return true;
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		Style style = getStyle();
		Font font = style.getFont();
		int minContentSize = font.getHeight() * 2;
		size.setSize(minContentSize, minContentSize);
	}

	@Override
	protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
		Style style = getStyle();
		int backgroundColor = style.getExtraInt(EXTRA_FIELD_BACKGROUND_COLOR, style.getColor());
		int centerX = contentWidth / 2;
		int centerY = contentHeight / 2;
		drawTextInCircle(g, centerX, centerY);
		drawMask(g, style.getFont(), backgroundColor, centerX, centerY);
	}

	private void drawTextInCircle(GraphicsContext g, int centerX, int centerY) {
		int x = this.direction != Direction.CLOCKWISE ? 0 : centerX;
		assert this.textImage != null;
		// draw an image of the text
		TransformPainter.drawRotatedImageBilinear(g, this.textImage, x, 0, centerX, centerY, this.angle);
	}

	private void drawMask(GraphicsContext g, Font font, int color, int centerX, int centerY) {
		g.setColor(color);
		int baselinePosition = font.getBaselinePosition();
		int textHeight = font.getHeight();
		int maskRadius = this.radius + (this.direction != Direction.CLOCKWISE ? textHeight / 2 - baselinePosition
				: baselinePosition - textHeight / 2);
		float maskArcAngle = (ANGLE_360 - Math.abs(this.arcAngle)) * (this.arcAngle > 0 ? 1f : -1f);
		ShapePainter.drawThickCircleArc(g, centerX - maskRadius, centerY - maskRadius, maskRadius * 2,
				this.startAngle + this.arcAngle, maskArcAngle, textHeight);
	}

	@Override
	protected void onLaidOut() {
		super.onLaidOut();
		updateRadius();
		onTextUpdate();
		this.angle = this.startOfTextAngle - START_ANGLE_IN_IMAGE;
	}

	private void onTextUpdate() {
		updateTextArcAngle();
		updateStartOfTextAngle();
		createTextImage();
	}

	private void createTextImage() {
		closeTextImage();

		// compute the size of the image for the text
		Rectangle contentBounds = getContentBounds();
		int width;
		int height;
		int contentWidth = contentBounds.getWidth();
		int contentHeight = contentBounds.getHeight();
		if (this.textAngle < ANGLE_90) {
			width = contentWidth / 2;
			height = contentHeight / 2;
		} else if (this.textAngle < ANGLE_180) {
			width = contentWidth / 2;
			height = contentHeight;
		} else {
			width = contentWidth;
			height = contentHeight;
		}

		// create a buffered image to draw the text into
		BufferedImage image = new BufferedImage(width, height);
		GraphicsContext graphicsContext = image.getGraphicsContext();
		Style style = getStyle();
		int textColor = style.getColor();
		int backgroundColor = style.getExtraInt(EXTRA_FIELD_BACKGROUND_COLOR, textColor);
		graphicsContext.setColor(backgroundColor);
		Painter.fillRectangle(graphicsContext, 0, 0, width, height);

		// draw the text in circle in the image
		graphicsContext.setColor(textColor);
		int x = this.direction != Direction.CLOCKWISE ? contentWidth / 2 : 0;
		StringOnCirclePainter.drawStringOnCircleBilinear(graphicsContext, this.text, style.getFont(), x,
				contentHeight / 2, this.radius, START_ANGLE_IN_IMAGE, this.direction);
		this.textImage = image;
	}

	@Override
	protected void onShown() {
		super.onShown();
		startAnimation();
	}

	@Override
	protected void onHidden() {
		super.onHidden();
		stopAnimation();
	}

	@Override
	protected void onDetached() {
		super.onDetached();
		closeTextImage();
	}

	private void closeTextImage() {
		BufferedImage textImage = this.textImage;
		if (textImage != null) {
			textImage.close();
		}
		this.textImage = null;
	}

	private void startAnimation() {
		Animator animator = getDesktop().getAnimator();
		this.startTime = Util.platformTimeMillis();
		animator.startAnimation(this);
	}

	private void stopAnimation() {
		getDesktop().getAnimator().stopAnimation(this);
	}

	/**
	 * Computes the radius of the circle on which the text is drawn.
	 * <p>
	 * The computation maximizes the size of the circle, given the content bounds of the widget, the selected font and
	 * the text direction.
	 */
	private void updateRadius() {
		Font font = getStyle().getFont();
		int baselinePosition = font.getBaselinePosition();
		Rectangle contentBounds = getContentBounds();
		int textOverflow = this.direction != Direction.CLOCKWISE ? font.getHeight() - baselinePosition
				: baselinePosition;
		int diameter = Math.min(contentBounds.getWidth(), contentBounds.getHeight()) - 2 * textOverflow;
		this.radius = diameter / 2;
	}

	/**
	 * Computes the angle of the arc needed for displaying the entire text.
	 */
	private void updateTextArcAngle() {
		Style style = getStyle();
		Font font = style.getFont();
		float arcLength = font.stringWidth(this.text);
		this.textAngle = (float) Math.toDegrees(arcLength / this.radius) * (this.arcAngle > 0 ? 1f : -1f);
	}

	private void updateStartOfTextAngle() {
		if (this.direction == Direction.CLOCKWISE ? this.arcAngle > 0 : this.arcAngle < 0) {
			this.startOfTextAngle = this.startAngle;
		} else {
			this.startOfTextAngle = this.startAngle - this.textAngle;
		}
	}

}
