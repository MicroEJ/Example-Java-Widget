/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.basic;

import ej.annotation.Nullable;
import ej.mwt.animation.Animation;
import ej.mwt.animation.Animator;
import ej.service.ServiceFactory;
import ej.widget.model.BoundedRangeModel;

/**
 * A progress is a widget that displays the progress of some task. It uses a bounded range model as its data model.
 */
public abstract class AbstractProgress extends BoundedRange {

	private boolean indeterminate;
	@Nullable
	private Animation indeterminateAnimation;

	/**
	 * Creates a progress bar with the given model.
	 *
	 * @param model
	 *            the model to use.
	 */
	public AbstractProgress(BoundedRangeModel model) {
		super(model);
	}

	/**
	 * Creates a progress bar with a default bounded range model as model.
	 *
	 * @param min
	 *            the minimum value of the progress bar.
	 * @param max
	 *            the maximum value of the progress bar.
	 * @param initialValue
	 *            the initial value of the progress bar.
	 * @see ej.widget.model.DefaultBoundedRangeModel
	 */
	public AbstractProgress(int min, int max, int initialValue) {
		super(min, max, initialValue);
	}

	/**
	 * Sets whether or not the progress bar is in an indeterminate state.
	 *
	 * @param indeterminate
	 *            the new state of the progress bar.
	 */
	public void setIndeterminate(boolean indeterminate) {
		if (indeterminate != this.indeterminate) {
			this.indeterminate = indeterminate;
			if (isShown()) {
				if (indeterminate) {
					startIndeterminateAnimation();
				} else {
					stopIndeterminateAnimation();
				}
			}
		}
	}

	/**
	 * Starts the indeterminate animation.
	 */
	protected void startIndeterminateAnimation() {
		// Ensures it is not already running.
		stopIndeterminateAnimation();

		Animation animation = new Animation() {

			@Override
			public boolean tick(long currentTimeMillis) {
				indeterminateTick();
				return true;
			}
		};
		this.indeterminateAnimation = animation;
		Animator animator = ServiceFactory.getService(Animator.class, Animator.class);
		animator.startAnimation(animation);
	}

	/**
	 * Stops the indeterminate animation.
	 */
	protected void stopIndeterminateAnimation() {
		Animation indeterminateAnimation = this.indeterminateAnimation;
		if (indeterminateAnimation != null) {
			Animator animator = ServiceFactory.getService(Animator.class, Animator.class);
			animator.stopAnimation(indeterminateAnimation);
		}
	}

	/**
	 * Indeterminate animation tick.
	 * <p>
	 * Do nothing by default, subclasses can add behavior to animate the progress bar.
	 */
	protected void indeterminateTick() {
		// Do nothing by default.
	}

	/**
	 * Gets whether or not the progress bar is in an indeterminate state.
	 *
	 * @return true if the progress bar is in an indeterminate state otherwise false.
	 */
	public boolean isIndeterminate() {
		return this.indeterminate;
	}

	@Override
	protected void onShown() {
		super.onShown();
		if (isIndeterminate()) {
			startIndeterminateAnimation();
		}
	}

	@Override
	protected void onHidden() {
		super.onHidden();
		if (isIndeterminate()) {
			stopIndeterminateAnimation();
		}
	}
}
