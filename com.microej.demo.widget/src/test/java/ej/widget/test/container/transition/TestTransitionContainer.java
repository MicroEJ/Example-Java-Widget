/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test.container.transition;

import com.is2t.testsuite.support.CheckHelper;

import ej.annotation.Nullable;
import ej.microui.display.Display;
import ej.microui.display.GraphicsContext;
import ej.motion.Motion;
import ej.motion.MotionFactory;
import ej.motion.elastic.ElasticEaseInOutMotion;
import ej.motion.none.NoMotion;
import ej.mwt.Container;
import ej.mwt.Desktop;
import ej.widget.animation.AnimationListener;
import ej.widget.container.Canvas;
import ej.widget.container.transition.CircleSplashScreenshotTransitionContainer;
import ej.widget.container.transition.FadeScreenshotTransitionContainer;
import ej.widget.container.transition.NoTransitionContainer;
import ej.widget.container.transition.SlideDirection;
import ej.widget.container.transition.SlideScreenshotTransitionContainer;
import ej.widget.container.transition.SlideTransitionContainer;
import ej.widget.container.transition.SplashScreenshotTransitionContainer;
import ej.widget.container.transition.TransitionContainer;
import ej.widget.test.framework.Item;
import ej.widget.test.framework.Test;
import ej.widget.test.framework.TestHelper;

public class TestTransitionContainer extends Test {

	private static class InstrumentedAnimationListener implements AnimationListener {
		int start;
		int stop;

		@Override
		public void onAnimationStarted() {
			this.start++;
		}

		@Override
		public void onAnimationStopped() {
			this.stop++;
		}
	}

	private static final long TOLERANCE = 100;

	public static void main(String[] args) {
		TestHelper.launchTest(new TestTransitionContainer());
	}

	@Override
	public void run(Display display) {
		test(new NoTransitionContainer());

		testNotShown(new SlideTransitionContainer(SlideDirection.RIGHT, false));
		test(new SlideTransitionContainer(SlideDirection.RIGHT, false));
		test(new SlideTransitionContainer(SlideDirection.RIGHT, true));
		test(new SlideTransitionContainer(SlideDirection.LEFT, false));
		test(new SlideTransitionContainer(SlideDirection.LEFT, true));
		test(new SlideTransitionContainer(SlideDirection.UP, false));
		test(new SlideTransitionContainer(SlideDirection.UP, true));
		test(new SlideTransitionContainer(SlideDirection.DOWN, false));
		test(new SlideTransitionContainer(SlideDirection.DOWN, true));

		testNotShown(new SlideScreenshotTransitionContainer(SlideDirection.RIGHT, false, false));
		test(new SlideScreenshotTransitionContainer(SlideDirection.RIGHT, false, false));
		test(new SlideScreenshotTransitionContainer(SlideDirection.RIGHT, true, false));
		test(new SlideScreenshotTransitionContainer(SlideDirection.RIGHT, false, true));
		test(new SlideScreenshotTransitionContainer(SlideDirection.RIGHT, true, true));
		test(new SlideScreenshotTransitionContainer(SlideDirection.LEFT, false, false));
		test(new SlideScreenshotTransitionContainer(SlideDirection.LEFT, true, false));
		// Do not test "LEFT,clip" to avoid having a very long test.
		test(new SlideScreenshotTransitionContainer(SlideDirection.UP, false, false));
		test(new SlideScreenshotTransitionContainer(SlideDirection.UP, true, false));
		test(new SlideScreenshotTransitionContainer(SlideDirection.UP, false, true));
		test(new SlideScreenshotTransitionContainer(SlideDirection.UP, true, true));
		test(new SlideScreenshotTransitionContainer(SlideDirection.DOWN, false, false));
		test(new SlideScreenshotTransitionContainer(SlideDirection.DOWN, true, false));
		// Do not test "BOTTOM,clip" to avoid having a very long test.

		testNotShown(new SplashScreenshotTransitionContainer(true));
		test(new SplashScreenshotTransitionContainer(true));
		test(new SplashScreenshotTransitionContainer(false));
		SplashScreenshotTransitionContainer splash = new SplashScreenshotTransitionContainer(true);
		splash.setAnchor(10, 10);
		test(splash);
		splash = new SplashScreenshotTransitionContainer(false);
		splash.setAnchor(10, 10);
		test(splash);
		splash = new SplashScreenshotTransitionContainer(true);
		splash.setAnchor(45, 50);
		test(splash);
		splash = new SplashScreenshotTransitionContainer(false);
		splash.setAnchor(45, 50);
		test(splash);

		testNotShown(new CircleSplashScreenshotTransitionContainer(true));
		test(new CircleSplashScreenshotTransitionContainer(true));
		test(new CircleSplashScreenshotTransitionContainer(false));
		splash = new CircleSplashScreenshotTransitionContainer(true);
		splash.setAnchor(10, 10);
		test(splash);
		splash = new CircleSplashScreenshotTransitionContainer(false);
		splash.setAnchor(10, 10);
		test(splash);

		testNotShown(new FadeScreenshotTransitionContainer());
		test(new FadeScreenshotTransitionContainer());
	}

	private void testNotShown(TransitionContainer container) {
		InstrumentedAnimationListener animationListener = new InstrumentedAnimationListener();
		container.addAnimationListener(animationListener);

		// Show a first widget while not shown.
		Item item1 = new Item(100, 100);
		container.show(item1, true);

		waitForAnimation(container.getDuration());

		check(container, item1, null, animationListener, 0, false);

		// Show a second widget while not shown.
		Item item2 = new Item(100, 100);
		container.show(item2, true);

		waitForAnimation(container.getDuration());

		check(container, item2, item1, animationListener, 0, false);

		Desktop desktop = new Desktop();
		Canvas canvas = new Canvas();

		canvas.addChild(container, 10, 20, 90, 100);
		desktop.setWidget(canvas);
		desktop.requestShow();

		TestHelper.waitForAllEvents();

		// Show a widget while shown.
		Item item3 = new Item(100, 100);
		container.show(item3, true);

		waitForAnimation(container.getDuration());

		check(container, item3, item2, animationListener, 1);
	}

	private void test(TransitionContainer container) {
		InstrumentedAnimationListener animationListener = new InstrumentedAnimationListener();
		container.addAnimationListener(animationListener);

		Desktop desktop = new Desktop();
		Canvas canvas = new Canvas();

		canvas.addChild(container, 10, 20, 90, 100);
		desktop.setWidget(canvas);
		desktop.requestShow();

		TestHelper.waitForAllEvents();

		// Show a first widget.
		Item item1 = new Item(100, 100);
		container.show(item1, true);

		waitForAnimation(container.getDuration());

		check(container, item1, null, animationListener, 0);

		waitBeforeNextTest();
		// Show a widget forward.
		Item item2 = new Item(100, 100);
		container.show(item2, true);

		waitForAnimation(container.getDuration());
		check(container, item2, item1, animationListener, 1);

		waitBeforeNextTest();
		// Show a widget backward.
		Item item3 = new Item(100, 100);
		container.show(item3, false);

		waitForAnimation(container.getDuration());
		check(container, item3, item2, animationListener, 2);

		waitBeforeNextTest();
		// Change duration and show a widget.
		container.setDuration(10);
		CheckHelper.check(getClass(), "Change duration " + container, container.getDuration(), 10);
		container.show(item1, true);

		waitForAnimation(container.getDuration());
		check(container, item1, item3, animationListener, 3);

		waitBeforeNextTest();
		// Reset duration.
		container.setDuration(60);
		// Show again the same widget.
		try {
			container.show(item1, true);
			CheckHelper.check(getClass(), "Shown twice " + container, false);
		} catch (IllegalArgumentException e) {
			CheckHelper.check(getClass(), "Shown twice " + container, true);
		}

		waitBeforeNextTest();
		// Change motion factory and show a widget.
		MotionFactory motionFactory = new MotionFactory() {
			@Override
			public Motion createMotion(int start, int stop, long duration) {
				return new ElasticEaseInOutMotion(start, stop, duration);
			}
		};
		container.setMotionFactory(motionFactory);
		CheckHelper.check(getClass(), "Change motion factory " + container, container.getMotionFactory(),
				motionFactory);
		container.show(item2, true);

		waitForAnimation(container.getDuration());
		check(container, item2, item1, animationListener, 4);

		waitBeforeNextTest();
		// Change motion factory and show a widget.
		motionFactory = new MotionFactory() {
			@Override
			public Motion createMotion(int start, int stop, long duration) {
				return new NoMotion(start, stop);
			}
		};
		container.setMotionFactory(motionFactory);
		CheckHelper.check(getClass(), "Change motion factory " + container, container.getMotionFactory(),
				motionFactory);
		container.show(item3, true);

		waitForAnimation(container.getDuration());
		check(container, item3, item2, animationListener, 5);

		waitBeforeNextTest();
		// Remove listeners and check they are not notified again.
		container.removeAnimationListener(animationListener);

		container.show(item1, true);

		waitForAnimation(container.getDuration());

		check(container, item1, item3, animationListener, 5);

		waitBeforeNextTest();
		// Show quickly several widgets.
		container.removeAnimationListener(animationListener);
		container.show(new Item(100, 100), true);
		container.show(new Item(100, 100), true);
		container.show(new Item(100, 100), true);
		container.show(item2, true);
		container.show(item3, true);

		waitForAnimation(container.getDuration());
		check(container, item3, item2, animationListener, 5);
		container.addAnimationListener(animationListener);

		// Make sure everything is finished.
		waitBeforeNextTest();
		waitForAnimation(container.getDuration());
	}

	private void test(NoTransitionContainer container) {
		Desktop desktop = new Desktop();
		Canvas canvas = new Canvas();

		canvas.addChild(container, 10, 20, 90, 100);
		desktop.setWidget(canvas);
		desktop.requestShow();

		InstrumentedAnimationListener animationListener = new InstrumentedAnimationListener();
		container.addAnimationListener(animationListener);

		TestHelper.waitForAllEvents();

		Item item1 = new Item(100, 100);
		container.show(item1, true);

		waitBeforeNextTest();

		check(container, item1, null, animationListener, 0);

		Item item2 = new Item(100, 100);
		container.show(item2, true);

		waitBeforeNextTest();

		check(container, item2, item1, animationListener, 0);

		Item item3 = new Item(100, 100);
		container.show(item3, false);

		waitBeforeNextTest();

		check(container, item3, item2, animationListener, 0);

		container.removeAnimationListener(animationListener);

	}

	private void check(Container container, Item widget, @Nullable Item previousWidget,
			InstrumentedAnimationListener animationListener, int expectedAnimation) {
		check(container, widget, previousWidget, animationListener, expectedAnimation, true);
	}

	private void check(Container container, Item widget, @Nullable Item previousWidget,
			InstrumentedAnimationListener animationListener, int expectedAnimation, boolean checkPixels) {
		CheckHelper.check(getClass(), "One child " + container, container.getChildrenCount(), 1);
		CheckHelper.check(getClass(), "Only child " + container, widget.getParent(), container);
		CheckHelper.check(getClass(), "Animation start " + container, animationListener.start, expectedAnimation);
		CheckHelper.check(getClass(), "Animation stop " + container, animationListener.stop, expectedAnimation);
		CheckHelper.check(getClass(), "Same width as parent " + container, widget.getWidth(), container.getWidth());
		CheckHelper.check(getClass(), "Same height as parent " + container, widget.getHeight(), container.getHeight());
		if (previousWidget != null) {
			CheckHelper.check(getClass(), "Orphan", previousWidget.getParent(), null);
		}

		if (checkPixels) {
			checkPixels(container, widget);
		}
	}

	private void checkPixels(Container container, Item widget) {
		Display display = Display.getDisplay();
		int containerX = container.getAbsoluteX();
		int containerY = container.getAbsoluteY();
		int containerWidth = container.getWidth();
		int containerHeight = container.getHeight();
		GraphicsContext graphicsContext = display.getGraphicsContext();
		graphicsContext.reset();
		int expectedColor = display.getDisplayColor(widget.getColor() | 0xff000000);
		CheckHelper.check(getClass(), "Color left " + container,
				graphicsContext.readPixel(containerX, containerY + containerHeight / 2) & 0xffffff, expectedColor);
		CheckHelper.check(getClass(), "Color right " + container,
				graphicsContext.readPixel(containerX + containerWidth - 1, containerY + containerHeight / 2) & 0xffffff,
				expectedColor);
		CheckHelper.check(getClass(), "Color top " + container,
				graphicsContext.readPixel(containerX + containerWidth / 2, containerY) & 0xffffff, expectedColor);
		CheckHelper.check(getClass(), "Color bottom " + container,
				graphicsContext.readPixel(containerX + containerWidth / 2, containerY + containerHeight - 1) & 0xffffff,
				expectedColor);
	}

	private void waitForAnimation(long duration) {
		TestHelper.waitForEvent();
		try {
			Thread.sleep(duration + TOLERANCE);
		} catch (InterruptedException e) {
		}
		TestHelper.waitForEvent();
		TestHelper.waitForEvent();
	}

	private void waitBeforeNextTest() {
		try {
			Thread.sleep(TOLERANCE);
		} catch (InterruptedException e) {
		}
		TestHelper.waitForEvent();
		TestHelper.waitForEvent();
	}
}
