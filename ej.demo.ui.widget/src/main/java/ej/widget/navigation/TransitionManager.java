/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * Use of this source code is subject to license terms.
 */
package ej.widget.navigation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ej.microui.display.GraphicsContext;
import ej.microui.event.EventGenerator;
import ej.microui.util.EventHandler;
import ej.style.Style;
import ej.style.container.Rectangle;
import ej.style.util.StyleHelper;
import ej.widget.navigation.page.Page;

/**
 * Animates the transition between two pages of a navigator.
 *
 * @see Navigator
 */
public abstract class TransitionManager {

	private Navigator navigator;

	private static final List<TransitionListener> GlobalListeners = new ArrayList<>();

	private final Map<EventGenerator, EventHandler> eventHandlers;
	private final List<TransitionListener> listeners;
	private boolean isStarted = false;

	/**
	 * Creates a transition manager.
	 */
	public TransitionManager() {
		this.eventHandlers = new HashMap<>();
		this.listeners = new ArrayList<>();
	}

	/**
	 * Sets the navigator.
	 *
	 * @param navigator
	 *            the navigator to set.
	 * @throws IllegalArgumentException
	 *             if it is already associated with a navigator.
	 */
	void setNavigator(final Navigator navigator) {
		if (navigator != null && this.navigator != null) {
			throw new IllegalArgumentException();
		}
		this.navigator = navigator;
	}

	/**
	 * Gets the navigator.
	 *
	 * @return the navigator.
	 */
	protected Navigator getNavigator() {
		return this.navigator;
	}

	/**
	 * Notifies that the transition is started.
	 */
	protected void startTransition() {
		final EventGenerator[] eventGenerators = EventGenerator.get(EventGenerator.class);
		for (final EventGenerator eventGenerator : eventGenerators) {
			this.eventHandlers.put(eventGenerator, eventGenerator.getEventHandler());
			try {
				eventGenerator.setEventHandler(null);
			} catch (final SecurityException e) {
				// Cannot change this event generator handler… forget it!.
				this.eventHandlers.remove(eventGenerator);
			}
		}

	}

	/**
	 * Notifies that the transition is stopped.
	 */
	protected void stopTransition() {
		final EventGenerator[] eventGenerators = EventGenerator.get(EventGenerator.class);
		for (final EventGenerator eventGenerator : eventGenerators) {
			final EventHandler eventHandler = this.eventHandlers.remove(eventGenerator);
			if (eventHandler != null) {
				try {
					eventGenerator.setEventHandler(eventHandler);
				} catch (final SecurityException e) {
					// Nothing to do…
				}
			}
		}

	}

	/**
	 * Animates the transition between two pages of the navigation container.
	 *
	 * @param oldPage
	 *            the old page.
	 * @param newPage
	 *            the new page.
	 * @param forward
	 *            <code>true</code> if going to a page that is after the current one, <code>false</code> otherwise.
	 */
	protected abstract void animate(Page oldPage, Page newPage, boolean forward);

	/**
	 * Renders the content of the navigation container without the border, margin and padding specified in the style.
	 * <p>
	 * Do nothing by default.
	 *
	 * @param g
	 *            the graphics context where to render the content of the renderable.
	 * @param style
	 *            the style to use.
	 * @param bounds
	 *            the remaining size to render the content.
	 */
	protected void render(final GraphicsContext g, final Style style, final Rectangle bounds) {
		// Do nothing by default.
	}

	/**
	 * Handles an event that occurs on the navigation container.
	 *
	 * @param event
	 *            the event to handle.
	 * @return <code>true</code> if the widget has consumed the event, <code>false</code> otherwise.
	 */
	protected boolean handleEvent(final int event) {
		return false;
	}

	/**
	 * Adds a page in the navigation container.
	 *
	 * @param page
	 *            the page to add.
	 */
	protected void addPage(final Page page) {
		this.navigator.add(page);
	}

	/**
	 * Removes a page from the navigation container.
	 *
	 * @param page
	 *            the page to remove.
	 */
	protected void removePage(final Page page) {
		this.navigator.remove(page);
	}

	/**
	 * Sets the current page.
	 *
	 * @param newPage
	 *            the new page.
	 */
	protected void setCurrentPage(final Page newPage) {
		this.navigator.setCurrentPage(newPage);
	}

	/**
	 * Gets whether or not it is possible to go backward.
	 *
	 * @return <code>true</code> if it is possible to go backward, <code>false</code> otherwise.
	 */
	protected boolean canGoBackward() {
		return this.navigator.canGoBackward();
	}

	/**
	 * Gets the previous page.
	 *
	 * @return the previous page or <code>null</code> if cannot go backward.
	 */
	protected Page getPreviousPage() {
		return this.navigator.getPreviousPage();
	}

	protected void showPage(final Page oldPage, final Page newPage) {
		if (oldPage != null) {
			removePage(oldPage);
		}
		addPage(newPage);
		setChildBounds(newPage);
		setCurrentPage(newPage);
		notifyShow(newPage);
		getNavigator().repaint();
	}

	/**
	 * Shows the previous page.
	 * <p>
	 * Nothing is done if cannot go backward.
	 */
	protected void goBackward() {
		this.navigator.goBackward();
	}

	/**
	 * Gets whether or not it is possible to go forward.
	 *
	 * @return <code>true</code> if it is possible to go forward, <code>false</code> otherwise.
	 */
	protected boolean canGoForward() {
		return this.navigator.canGoForward();
	}

	/**
	 * Gets the next page.
	 *
	 * @return the next page or <code>null</code> if cannot go forward.
	 */
	protected Page getNextPage() {
		return this.navigator.getNextPage();
	}

	/**
	 * Shows the next page.
	 * <p>
	 * Nothing is done if cannot go forward.
	 */
	protected void goForward() {
		this.navigator.goForward();
	}

	/**
	 * Sets the bounds of a new page.
	 *
	 * @param newPage
	 *            the new page.
	 * @see #getContentBounds()
	 */
	protected void setChildBounds(final Page newPage) {
		this.navigator.setChildBounds(newPage);
	}

	/**
	 * Gets the bounds of a child page.
	 *
	 * @return the bounds of a child page.
	 */
	protected Rectangle getContentBounds() {
		final Navigator navigator = this.navigator;
		final Rectangle bounds = new Rectangle(0, 0, navigator.getWidth(), navigator.getHeight());
		final Style style = navigator.getStyle();
		final Rectangle contentBounds = StyleHelper.computeContentBounds(bounds, style);
		return contentBounds;
	}

	/**
	 * Shows a new page. If there is already a page shown, it is hidden.
	 *
	 * @param newPage
	 *            the new page to show.
	 * @param forward
	 *            <code>true</code> if going to a new page, <code>false</code> if going back in the stack of pages.
	 */
	protected void show(final Page newPage, final boolean forward) {
		this.navigator.show(newPage, forward);
	}

	/**
	 * Add a listener for the transition.
	 *
	 * @param listener
	 *            The listener.
	 * @return true (as specified by Collection.add)
	 * @see List#add(Object)
	 */
	public boolean addTransitionListener(final TransitionListener listener) {
		return this.listeners.add(listener);
	}

	/**
	 * Removes a listener for the transition.
	 *
	 * @param listener
	 *            The listener.
	 * @return true if this list contained the specified element
	 * @see List#remove(Object)
	 */
	public boolean removeTransitionListener(final TransitionListener listener) {
		return this.listeners.remove(listener);
	}

	/**
	 * Add a listener for the transition.
	 *
	 * @param listener
	 *            The listener.
	 * @return true (as specified by Collection.add)
	 * @see List#add(Object)
	 */
	public static boolean addGlobalTransitionListener(final TransitionListener listener) {
		return GlobalListeners.add(listener);
	}

	/**
	 * Removes a listener for the transition.
	 *
	 * @param listener
	 *            The listener.
	 * @return true if this list contained the specified element
	 * @see List#remove(Object)
	 */
	public static boolean removeGlobalTransitionListener(final TransitionListener listener) {
		return GlobalListeners.remove(listener);
	}

	/**
	 * Notify the listerners that a transition starts.
	 *
	 * @param transitionsSteps
	 *            The number of a steps for the transition.
	 * @param from
	 *            The page the transition is comming from.
	 * @param to
	 *            The page the transition is going to.
	 * @param forward
	 *            Wether the transition is going forward.
	 */
	protected void notifyTransitionStart(final int transitionsStart, final int transitionsStop, final Page from,
			final Page to) {
		if (!this.isStarted) {
			this.isStarted = true;
			for (final TransitionListener transitionListener : this.listeners) {
				transitionListener.onTransitionStart(transitionsStart, transitionsStop, from, to);
			}
			for (final TransitionListener transitionListener : GlobalListeners) {
				transitionListener.onTransitionStart(transitionsStart, transitionsStop, from, to);
			}
		}
	}

	/**
	 * Notify the listeners of a transition step.
	 *
	 * @param step
	 *            the current step.
	 */
	protected void notifyTransitionTick(final int step) {
		for (final TransitionListener transitionListener : this.listeners) {
			transitionListener.onTransitionStep(step);
		}

		for (final TransitionListener transitionListener : GlobalListeners) {
			transitionListener.onTransitionStep(step);
		}
	}

	protected void notifyTransitionStop() {
		if (this.isStarted) {
			this.isStarted = false;
			for (final TransitionListener transitionListener : this.listeners) {
				transitionListener.onTransitionStop();
			}
			for (final TransitionListener transitionListener : GlobalListeners) {
				transitionListener.onTransitionStop();
			}
		}
	}

	/**
	 * @param currentPage
	 */
	public void notifyShow(final Page currentPage) {
		notifyTransitionStart(0, 0, this.navigator.getPreviousPage(), currentPage);
		notifyTransitionStop();
	}

	public static void notifyGlobalListeners(final int transitionsStart, final int transitionsStop, final Page from,
			final Page to) {
		for (final TransitionListener transitionListener : GlobalListeners) {
			transitionListener.onTransitionStart(transitionsStart, transitionsStop, from, to);
		}
		for (final TransitionListener transitionListener : GlobalListeners) {
			transitionListener.onTransitionStop();
		}
	}
}
