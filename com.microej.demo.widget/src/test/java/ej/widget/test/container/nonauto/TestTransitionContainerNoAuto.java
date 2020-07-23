/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test.container.nonauto;

import ej.microui.MicroUI;
import ej.microui.display.Colors;
import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.microui.event.Event;
import ej.microui.event.generator.Pointer;
import ej.motion.Motion;
import ej.motion.MotionFactory;
import ej.motion.sine.SineEaseInMotion;
import ej.mwt.Desktop;
import ej.mwt.Widget;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.background.RectangularBackground;
import ej.mwt.style.outline.UniformOutline;
import ej.mwt.style.outline.border.RectangularBorder;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.TypeSelector;
import ej.widget.animation.AnimationListener;
import ej.widget.container.Dock;
import ej.widget.container.transition.SlideDirection;
import ej.widget.container.transition.SlideScreenshotTransitionContainer;
import ej.widget.container.transition.SplashScreenshotTransitionContainer;
import ej.widget.container.transition.TransitionContainer;
import ej.widget.test.framework.Item;

public class TestTransitionContainerNoAuto {

	/**
	 * <code>true</code> to have a widget child, <code>false</code> to have a container child.
	 */
	private static final boolean SINGLE_CHILD = true;

	static class SubItem extends Item {
		public SubItem(int expectedWidth, int expectedHeight, boolean random) {
			super(expectedWidth, expectedHeight, random);
		}

		@Override
		public void render(GraphicsContext g) {
			super.render(g);
			Font font = getStyle().getFont();
			Painter.drawString(g, Integer.toString(font.getHeight()), font, 10, 10);
		}
	}

	static Widget[] labels = new Widget[] { new SubItem(1, 1, false), new SubItem(1, 1, false), };

	static int index = 0;

	public static void main(String[] args) {
		MicroUI.start();

		AnimationListener animationListener = new AnimationListener() {

			@Override
			public void onAnimationStarted() {
				System.out.println("STARTED");

			}

			@Override
			public void onAnimationStopped() {
				System.out.println("STOPPED");
			}
		};

		CascadingStylesheet stylesheet = new CascadingStylesheet();
		EditableStyle outlinedStyle = stylesheet.getSelectorStyle(new TypeSelector(TransitionContainer.class));
		UniformOutline simpleOutline = new UniformOutline(3);
		outlinedStyle.setPadding(simpleOutline);
		outlinedStyle.setMargin(simpleOutline);
		outlinedStyle.setBorder(new RectangularBorder(Colors.BLACK, 2));
		outlinedStyle.setBackground(new RectangularBackground(Colors.MAGENTA));
		outlinedStyle.setFont(Font.getFont("/fonts/source_sans_pro_24.ejf"));

		// Choose the container to test:
		// final TransitionContainer transitionContainer = new SlideTransitionContainer(SlideDirection.UP, false);
		final TransitionContainer transitionContainer = new SlideScreenshotTransitionContainer(SlideDirection.LEFT,
				true, true);
		// final TransitionContainer transitionContainer = new SplashScreenshotTransitionContainer(false);
		// final TransitionContainer transitionContainer = new CircleSplashScreenshotTransitionContainer(true);
		// final TransitionContainer transitionContainer = new FadeScreenshotTransitionContainer();
		transitionContainer.addAnimationListener(animationListener);
		final int debugMultiplicator = 1;
		transitionContainer.setDuration(300 * debugMultiplicator);
		transitionContainer.setMotionFactory(new MotionFactory() {
			@Override
			public Motion createMotion(int start, int stop, long duration) {
				return new SineEaseInMotion(start, stop, duration);
			}
		});
		Widget widget = labels[index];
		assert widget != null;
		transitionContainer.show(widget, true);
		Desktop desktop = new Desktop() {
			boolean forward = true;

			@Override
			public boolean handleEvent(int event) {
				this.forward = doTransition(transitionContainer, event, this.forward);
				return false;
			}

		};
		desktop.setStylesheet(stylesheet);
		desktop.setWidget(transitionContainer);
		desktop.requestShow();
	}

	private static boolean doTransition(final TransitionContainer transitionContainer, int event, boolean forward) {
		if (Event.getType(event) == Pointer.EVENT_TYPE && Pointer.isReleased(event)) {
			if (++index == labels.length) {
				index = 0;
			}

			if (transitionContainer instanceof SplashScreenshotTransitionContainer) {
				Pointer pointer = (Pointer) Event.getGenerator(event);
				int relativeX = pointer.getX() - transitionContainer.getAbsoluteX();
				int relativeY = pointer.getY() - transitionContainer.getAbsoluteY();
				((SplashScreenshotTransitionContainer) transitionContainer).setAnchor(relativeX, relativeY);
			}

			Widget widget;
			if (SINGLE_CHILD) {
				widget = labels[index];
				assert widget != null;
			} else {
				Dock dock = new Dock();
				dock.setCenterChild(new Item(1, 1, false));
				widget = dock;
			}
			transitionContainer.show(widget, forward);

			return !forward;
		}
		return forward;
	}
}
