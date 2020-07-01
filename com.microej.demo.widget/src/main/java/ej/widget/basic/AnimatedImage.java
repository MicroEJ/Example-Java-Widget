/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.basic;

import java.util.StringTokenizer;

import ej.annotation.Nullable;
import ej.bon.Timer;
import ej.bon.TimerTask;
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

	/**
	 * Creates an animated image with the path to the images to display.
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
	public void setPeriod(long period) {
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
	}

	private void next() {
		if (this.currentIndex == this.frames.length - 1) {
			this.currentIndex = 0;
		} else {
			this.currentIndex++;
		}
		requestRender();
	}

	@Override
	protected void renderContent(GraphicsContext g, Size availableBounds) {
		String currentFrame = this.frames[this.currentIndex];
		assert currentFrame != null;
		try (ResourceImage frame = ResourceImage.loadImage(currentFrame)) {
			Style style = getStyle();
			int horizontalAlignment = style.getHorizontalAlignment();
			int verticalAlignment = style.getVerticalAlignment();
			int x = Alignment.computeLeftX(frame.getWidth(), 0, availableBounds.getWidth(), horizontalAlignment);
			int y = Alignment.computeTopY(frame.getHeight(), 0, availableBounds.getHeight(), verticalAlignment);
			g.setColor(style.getColor());
			Painter.drawImage(g, frame, x, y);
		}
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		String firstFrame = this.frames[0];
		assert firstFrame != null;
		try (ResourceImage firstImage = ResourceImage.loadImage(firstFrame)) {
			int width = getSize(size.getWidth(), firstImage.getWidth());
			int height = getSize(size.getHeight(), firstImage.getHeight());
			size.setSize(width, height);
		}
	}

	private int getSize(int available, int optimal) {
		return available == Widget.NO_CONSTRAINT ? optimal : Math.min(available, optimal);
	}

}
