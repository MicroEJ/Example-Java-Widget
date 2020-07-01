/*
 * Copyright 2016-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test.toggle;

import com.is2t.testsuite.support.CheckHelper;

import ej.microui.display.Display;
import ej.microui.event.EventHandler;
import ej.microui.event.generator.Pointer;
import ej.mwt.event.DesktopEventGenerator;
import ej.mwt.event.PointerEventDispatcher;
import ej.widget.listener.OnStateChangeListener;
import ej.widget.test.framework.TestEvent;
import ej.widget.test.framework.TestHelper;
import ej.widget.toggle.ToggleHelper;
import ej.widget.util.GenericListener;

public class ToggleHelperTest extends TestEvent {

	public static void main(String[] args) {
		TestHelper.launchTest(new ToggleHelperTest());
	}

	@Override
	public void run(Display display) {
		InstrumentedToggleHelperListener helperListener = new InstrumentedToggleHelperListener();
		ToggleHelper toggle = new ToggleHelper(helperListener);
		InstrumentedOnStateChangeListener listener = new InstrumentedOnStateChangeListener();
		toggle.addOnStateChangeListener(listener);
		toggle.toggle();
		CheckHelper.check(getClass(), "helperListener not updated", helperListener.updated);
		helperListener.updated = false;
		CheckHelper.check(getClass(), "toggle checked state", toggle.isChecked(), true);
		CheckHelper.check(getClass(), "toggle pressed state", toggle.isPressed(), false);
		CheckHelper.check(getClass(), "listener notified", listener.notified);
		CheckHelper.check(getClass(), "listener state", listener.newState, toggle.isChecked());
		listener.notified = false;

		toggle.removeOnStateChangeListener(listener);
		toggle.toggle();
		CheckHelper.check(getClass(), "helperListener not updated", helperListener.updated);
		helperListener.updated = false;
		CheckHelper.check(getClass(), "toggle checked state", toggle.isChecked(), false);
		CheckHelper.check(getClass(), "toggle pressed state", toggle.isPressed(), false);
		CheckHelper.check(getClass(), "listener not notified", !listener.notified);

		Pointer pointer = getOrCreatePointer(Display.getDisplay());
		EventHandler originalEventHandler = pointer.getEventHandler();
		pointer.setEventHandler(toggle);
		pointer.send(Pointer.PRESSED, 0);
		CheckHelper.check(getClass(), "helperListener updated", helperListener.updated);
		helperListener.updated = false;
		CheckHelper.check(getClass(), "toggle checked state", toggle.isChecked(), false);
		CheckHelper.check(getClass(), "toggle pressed state", toggle.isPressed(), true);

		pointer.send(Pointer.RELEASED, 0);
		CheckHelper.check(getClass(), "helperListener updated", helperListener.updated);
		helperListener.updated = false;
		CheckHelper.check(getClass(), "toggle checked state", toggle.isChecked(), true);
		CheckHelper.check(getClass(), "toggle pressed state", toggle.isPressed(), false);

		pointer.send(Pointer.PRESSED, 0);
		CheckHelper.check(getClass(), "helperListener updated", helperListener.updated);
		helperListener.updated = false;
		CheckHelper.check(getClass(), "toggle checked state", toggle.isChecked(), true);
		CheckHelper.check(getClass(), "toggle pressed state", toggle.isPressed(), true);

		DesktopEventGenerator desktopGenerator = new DesktopEventGenerator();
		desktopGenerator.setEventHandler(toggle);
		desktopGenerator.send(PointerEventDispatcher.EXITED);
		CheckHelper.check(getClass(), "helperListener updated", helperListener.updated);
		helperListener.updated = false;
		CheckHelper.check(getClass(), "toggle checked state", toggle.isChecked(), true);
		CheckHelper.check(getClass(), "toggle pressed state", toggle.isPressed(), false);

		pointer.send(Pointer.RELEASED, 0);
		CheckHelper.check(getClass(), "helperListener not updated", !helperListener.updated);
		helperListener.updated = false;
		CheckHelper.check(getClass(), "toggle checked state", toggle.isChecked(), true);
		CheckHelper.check(getClass(), "toggle pressed state", toggle.isPressed(), false);

		pointer.setEventHandler(originalEventHandler);
	}

	private final class InstrumentedToggleHelperListener implements GenericListener {

		boolean updated;

		@Override
		public void update() {
			this.updated = true;
		}

	}

	private class InstrumentedOnStateChangeListener implements OnStateChangeListener {

		boolean newState;
		boolean notified;

		@Override
		public void onStateChange(boolean newState) {
			this.newState = newState;
			this.notified = true;
		}

	}

}
