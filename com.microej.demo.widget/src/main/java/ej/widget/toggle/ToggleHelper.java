/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.toggle;

import java.util.Map;

import ej.annotation.Nullable;
import ej.microui.event.Event;
import ej.microui.event.EventHandler;
import ej.microui.event.generator.Command;
import ej.microui.event.generator.Pointer;
import ej.mwt.event.DesktopEventGenerator;
import ej.mwt.event.PointerEventDispatcher;
import ej.util.map.WeakValueHashMap;
import ej.widget.listener.OnStateChangeListener;
import ej.widget.util.GenericListener;

/**
 * A toggle helper manages the behavior of a toggle: it handles pointer events, changes the state of the toggle and
 * notifies listeners when the toggle is clicked.
 */
public class ToggleHelper implements EventHandler, OnStateChangeListener {

	private static Map<String, ToggleGroup> groups = createGroups();

	/**
	 * Kernel lazy initialization method for {@link ej.widget.composed.ToggleWrapper#Groups}.
	 *
	 * @return the new map.
	 */
	private static Map<String, ToggleGroup> createGroups() {
		return new WeakValueHashMap<>();
	}

	private final GenericListener helperListener;

	private final ToggleModel toggle;
	private boolean pressed;

	/**
	 * Creates a toggle helper.
	 *
	 * @param helperListener
	 *            the listener to notify when the state changed.
	 */
	public ToggleHelper(GenericListener helperListener) {
		this(helperListener, new ToggleModel());
	}

	/**
	 * Creates a toggle helper with a referent toggle model.
	 * <p>
	 * Events received by the toggle button are forwarded to the referent toggle.
	 *
	 * @param helperListener
	 *            the listener to notify when the state changed.
	 * @param toggle
	 *            the referent toggle.
	 * @throws NullPointerException
	 *             if the given toggle is <code>null</code>.
	 */
	public ToggleHelper(GenericListener helperListener, ToggleModel toggle) {
		this.helperListener = helperListener;
		this.toggle = toggle;
		toggle.addOnStateChangeListener(this); // May throw NPE.
	}

	/**
	 * Creates a toggle helper with a referent toggle model and a group.
	 * <p>
	 * Events received by the toggle button are forwarded to the referent toggle.
	 * <p>
	 * The toggle is registered in the group with the given name.
	 *
	 * @param helperListener
	 *            the listener to notify when the state changed.
	 * @param toggle
	 *            the referent toggle.
	 * @param group
	 *            the name of the toggle group.
	 * @throws NullPointerException
	 *             if the given toggle is <code>null</code>.
	 */
	public ToggleHelper(GenericListener helperListener, ToggleModel toggle, String group) {
		this(helperListener, toggle);
		setGroup(group);
	}

	/**
	 * Gets the toggle.
	 *
	 * @return the toggle.
	 */
	public ToggleModel getToggle() {
		return this.toggle;
	}

	/**
	 * Sets the group of this toggle.
	 * <p>
	 * For each name, a toggle group is created to group the toggles (only one toggle selected at a time).
	 *
	 * @param groupName
	 *            the name of the toggle group.
	 * @see ToggleGroup
	 */
	public void setGroup(@Nullable String groupName) {
		ToggleGroup oldGroup = this.toggle.getGroup();
		if (oldGroup != null) {
			oldGroup.removeToggle(this.toggle);
		}
		if (groupName != null) {
			ToggleGroup toggleGroup = getToggleGroup(groupName);
			toggleGroup.addToggle(this.toggle);
		}
	}

	private ToggleGroup getToggleGroup(String groupName) {
		ToggleGroup toggleGroup = groups.get(groupName);
		if (toggleGroup == null) {
			toggleGroup = new ToggleGroup();
			groups.put(groupName, toggleGroup);
		}
		return toggleGroup;
	}

	/**
	 * Adds a listener on the state change event of the toggle.
	 *
	 * @param listener
	 *            the listener to add.
	 * @throws NullPointerException
	 *             if the given listener is <code>null</code>.
	 */
	public void addOnStateChangeListener(OnStateChangeListener listener) {
		assert listener != null;
		this.toggle.addOnStateChangeListener(listener);
	}

	/**
	 * Removes a listener on the state change event of the toggle.
	 *
	 * @param listener
	 *            the listener to add.
	 */
	public void removeOnStateChangeListener(OnStateChangeListener listener) {
		this.toggle.removeOnStateChangeListener(listener);
	}

	/**
	 * Sets the state of the toggle.
	 * <p>
	 * If the toggle is not enabled or is already in the given state, nothing change.
	 *
	 * @param checked
	 *            the new state of the toggle.
	 */
	public void setChecked(boolean checked) {
		this.toggle.setChecked(checked);
	}

	/**
	 * Changes the state of the toggle to the inverse of the current state.
	 * <p>
	 * If the toggle is not enabled, nothing change.
	 */
	public void toggle() {
		this.toggle.toggle();
	}

	/**
	 * Gets whether or not the toggle button is checked.
	 *
	 * @return <code>true</code> if the toggle button is checked otherwise <code>false</code>.
	 */
	public boolean isChecked() {
		return this.toggle.isChecked();
	}

	/**
	 * Gets the pressed.
	 *
	 * @return the pressed.
	 */
	public boolean isPressed() {
		return this.pressed;
	}

	/**
	 * Sets the pressed state of the toggle.
	 *
	 * @param pressed
	 *            the new pressed state of the toggle.
	 */
	public void setPressed(boolean pressed) {
		this.pressed = pressed;
		this.helperListener.update();
	}

	@Override
	public void onStateChange(boolean newState) {
		this.helperListener.update();
	}

	@Override
	public boolean handleEvent(int event) {
		// /!\ Very similar code in ButtonHelper.
		switch (Event.getType(event)) {
		case Command.EVENT_TYPE:
			if (Event.getData(event) == Command.SELECT) {
				this.toggle.toggle();
				return true;
			}
			break;
		case Pointer.EVENT_TYPE:
			int action = Pointer.getAction(event);
			switch (action) {
			case Pointer.PRESSED:
				setPressed(true);
				break;

			case Pointer.RELEASED:
				if (this.pressed) {
					setPressed(false);

					this.toggle.toggle();
					return true;
				}
				break;
			// Don't exit when the button is dragged, because the user can drag inside the button.
			// case Pointer.DRAGGED:
			}
			break;
		case DesktopEventGenerator.EVENT_TYPE:
			action = DesktopEventGenerator.getAction(event);
			if (action == PointerEventDispatcher.EXITED) {
				setPressed(false);
			}
			break;
		}
		return false;
	}

}
