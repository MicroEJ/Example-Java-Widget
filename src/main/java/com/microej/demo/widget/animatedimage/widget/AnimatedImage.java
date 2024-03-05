/*
 * Copyright 2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.animatedimage.widget;

import java.util.StringTokenizer;

import ej.annotation.Nullable;
import ej.basictool.ThreadUtils;
import ej.bon.Timer;
import ej.bon.TimerTask;
import ej.microui.MicroUIException;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.microui.display.ResourceImage;
import ej.mwt.Widget;
import ej.mwt.style.Style;
import ej.mwt.util.Alignment;
import ej.mwt.util.Size;
import ej.service.ServiceFactory;

/**
 * A widget that displays a sequence of images at a fixed rate.
 *
 * @see ej.microui.display.Image
 */
public class AnimatedImage extends Widget {

	private static final String SPACE = " "; //$NON-NLS-1$

	private String[] frames;
	private long period;

	private int currentIndex;

	@Nullable
	private TimerTask timerTask;

	@Nullable
	private ResourceImage frame;

	/**
	 * Creates an animated image with the path to the images to display.
	 * <p>
	 * The image loader service is used to load the images from the given paths.
	 *
	 * @param frames
	 *            the paths to the frames to display.
	 * @param period
	 *            the period between each image.
	 * @throws NullPointerException
	 *             if the given frames array is <code>null</code>.
	 * @throws IllegalArgumentException
	 *             if the given frames array is empty.
	 * @throws IllegalArgumentException
	 *             if the given period is lower or equal to zero.
	 */
	public AnimatedImage(String[] frames, long period) {
		if (frames.length == 0) {
			throw new IllegalArgumentException();
		}
		this.frames = frames.clone();
		setPeriod(period);
	}

	/**
	 * Creates an animated image with the path to the images to display.
	 * <p>
	 * The image loader service is used to load the images from the given paths.
	 * <p>
	 * The given sources string is a space separated list of paths.
	 *
	 * @param frames
	 *            the paths to the frames to display.
	 * @param period
	 *            the period between each image.
	 * @throws NullPointerException
	 *             if the given frames string is <code>null</code>.
	 * @throws IllegalArgumentException
	 *             if the given frames string is empty or contains only spaces.
	 * @throws IllegalArgumentException
	 *             if the given period is lower or equal to zero.
	 */
	public AnimatedImage(String frames, long period) {
		StringTokenizer tokenizer = new StringTokenizer(frames, SPACE);
		int length = tokenizer.countTokens();
		if (length == 0) {
			throw new IllegalArgumentException();
		}
		String[] framesArray = new String[length];
		int currentIndex = 0;
		while (tokenizer.hasMoreTokens()) {
			framesArray[currentIndex++] = tokenizer.nextToken();
		}
		this.frames = framesArray;
		setPeriod(period);
	}

	/**
	 * Sets the period.
	 *
	 * @param period
	 *            the period to set.
	 * @throws IllegalArgumentException
	 *             if the given period is lower or equal to zero.
	 */
	private void setPeriod(long period) {
		if (period <= 0) {
			throw new IllegalArgumentException();
		}
		this.period = period;
	}

	/**
	 * Gets the period.
	 *
	 * @return the period.
	 */
	public long getPeriod() {
		return this.period;
	}

	/**
	 * Sets the frames to display for this animated image.
	 *
	 * @param frames
	 *            the path to the frames to display.
	 * @throws NullPointerException
	 *             if the given frames array is <code>null</code>.
	 * @throws IllegalArgumentException
	 *             if the given frames array is empty.
	 */
	public void setFrames(String[] frames) {
		// Copy array to avoid external modifications.
		String[] framesCopy = frames.clone();
		setFramesInternal(framesCopy);
	}

	/**
	 * Sets the frames. The given array is stored directly. Make sure the array is referenced only by this instance.
	 *
	 * @param frames
	 *            the path to the frames to display.
	 */
	private void setFramesInternal(String[] frames) {
		if (frames.length == 0) {
			throw new IllegalArgumentException();
		}
		this.frames = frames; // NOSONAR see javadoc.
		this.currentIndex = 0;
	}

	/**
	 * Gets the frames.
	 *
	 * @return the frames.
	 */
	public String[] getFrames() {
		return this.frames.clone();
	}

	@Override
	protected void onAttached() {
		super.onAttached();
		this.frame = loadImage(this.currentIndex);
	}

	@Override
	protected void onShown() {
		super.onShown();
		Timer timer = ServiceFactory.getService(Timer.class, Timer.class);
		this.timerTask = new TimerTask() {
			@Override
			public void run() {
				next();
			}
		};
		timer.schedule(this.timerTask, this.period, this.period);
	}

	@Override
	protected void onHidden() {
		super.onHidden();
		TimerTask timerTask = this.timerTask;
		if (timerTask != null) {
			timerTask.cancel();
		}
		closeFrameResource();
	}

	private void next() {
		closeFrameResource();

		if (this.currentIndex == this.frames.length - 1) {
			this.currentIndex = 0;
		} else {
			this.currentIndex++;
		}

		this.frame = loadImage(this.currentIndex);

		requestRender();
	}

	@Override
	protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
		ResourceImage currentFrame = this.frame;
		if (currentFrame != null) {
			Style style = getStyle();
			int horizontalAlignment = style.getHorizontalAlignment();
			int verticalAlignment = style.getVerticalAlignment();

			int x = Alignment.computeLeftX(currentFrame.getWidth(), 0, contentWidth, horizontalAlignment);
			int y = Alignment.computeTopY(currentFrame.getHeight(), 0, contentHeight, verticalAlignment);

			g.setColor(style.getColor());
			Painter.drawImage(g, currentFrame, x, y);
		}
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		ResourceImage firstImage = this.frame;

		if (firstImage != null) {
			int width = getSize(size.getWidth(), firstImage.getWidth());
			int height = getSize(size.getHeight(), firstImage.getHeight());
			size.setSize(width, height);
		} else {
			size.setSize(0, 0);
		}
	}

	private int getSize(int available, int optimal) {
		return available == Widget.NO_CONSTRAINT ? optimal : Math.min(available, optimal);
	}

	@Nullable
	private ResourceImage loadImage(int currentIndex) {
		String currentFrame = this.frames[currentIndex];
		assert currentFrame != null;

		try {
			return ResourceImage.loadImage(currentFrame);
		} catch (MicroUIException e) {
			ThreadUtils.handleUncaughtException(e);
			return null;
		}
	}

	private void closeFrameResource() {
		final ResourceImage usedFrame = this.frame;
		if (usedFrame != null) {
			usedFrame.close();
			this.frame = null;
		}
	}
}
