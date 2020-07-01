/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.toggle;

import ej.annotation.Nullable;
import ej.basictool.ArrayTools;

/**
 * A toggle group provides a container to organize a group of toggles. It manages the states of each toggle in the
 * group. It switches off all toggles except the one that has been clicked, that means that at most one toggle is
 * checked at a time.
 *
 * @see ToggleModel
 */
public class ToggleGroup {

	private static final ToggleModel[] EMPTY_TOGGLES = new ToggleModel[0];

	private ToggleModel[] toggles;
	@Nullable
	private ToggleModel checked;

	/**
	 * Creates an empty toggle group.
	 */
	public ToggleGroup() {
		this.toggles = EMPTY_TOGGLES;
	}

	/**
	 * Adds a toggle to the group.
	 * <p>
	 * If the given toggle is checked and there is already a checked toggle in the group, the new one is unchecked.
	 *
	 * @param toggle
	 *            the toggle to add to the group.
	 * @throws NullPointerException
	 *             if the given toggle is <code>null</code>.
	 */
	public void addToggle(ToggleModel toggle) {
		if (ArrayTools.getIndex(this.toggles, toggle) == -1) {
			if (toggle.isChecked()) {
				if (this.checked != null) {
					toggle.changeState(false);
				} else {
					this.checked = toggle;
				}
			}

			this.toggles = ArrayTools.add(this.toggles, toggle);

			toggle.setGroup(this);
		}
	}

	/**
	 * Removes a toggle from the group.
	 * <p>
	 * Nothing is modified if the given toggle is <code>null</code> or not in the group.
	 *
	 * @param toggle
	 *            the button to remove from the group.
	 */
	public void removeToggle(ToggleModel toggle) {
		int togglesLength = this.toggles.length;
		this.toggles = ArrayTools.remove(this.toggles, toggle);
		if (this.toggles.length != togglesLength) {
			toggle.setGroup(null);
		}
	}

	/**
	 * Gets the button group's checked toggle, or <code>null</code> if no toggle is checked.
	 *
	 * @return the current checked toggle.
	 */
	@Nullable
	public ToggleModel getChecked() {
		return this.checked;
	}

	/* package */void select(@Nullable ToggleModel toggle) {
		ToggleModel previouslyChecked = this.checked;
		if (previouslyChecked != null) {
			previouslyChecked.forceState(false);
		}
		this.checked = toggle;
	}

	/**
	 * Gets the toggles list. This may be empty.
	 *
	 * @return the toggles of the group.
	 */
	public ToggleModel[] getToggles() {
		return this.toggles.clone();
	}
}
