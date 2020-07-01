/*
 * Copyright 2016-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test.util;

import com.is2t.testsuite.support.CheckHelper;

import ej.microui.display.Display;
import ej.microui.event.EventHandler;
import ej.microui.event.generator.Pointer;
import ej.mwt.event.DesktopEventGenerator;
import ej.mwt.event.PointerEventDispatcher;
import ej.widget.listener.OnClickListener;
import ej.widget.test.framework.TestEvent;
import ej.widget.test.framework.TestHelper;
import ej.widget.util.ButtonHelper;
import ej.widget.util.GenericListener;

public class ButtonHelperTest extends TestEvent {

	public static void main(String[] args) {
		TestHelper.launchTest(new ButtonHelperTest());
	}

	@Override
	public void run(Display display) {
		InstrumentedButtonHelperListener helperListener = new InstrumentedButtonHelperListener();
		ButtonHelper button = new ButtonHelper(helperListener);
		InstrumentedOnClickListener listener = new InstrumentedOnClickListener();
		button.addOnClickListener(listener);
		button.performClick();
		CheckHelper.check(getClass(), "helperListener not updated no ui", !helperListener.updated);
		helperListener.updated = false;
		CheckHelper.check(getClass(), "button pressed state", button.isPressed(), false);
		CheckHelper.check(getClass(), "listener notified no ui", listener.notified);
		listener.notified = false;

		button.removeOnClickListener(listener);
		button.performClick();
		CheckHelper.check(getClass(), "helperListener not updated no ui", !helperListener.updated);
		helperListener.updated = false;
		CheckHelper.check(getClass(), "button pressed state", button.isPressed(), false);
		CheckHelper.check(getClass(), "listener not notified no ui", !listener.notified);

		Pointer pointer = getOrCreatePointer(Display.getDisplay());
		EventHandler originalEventHandler = pointer.getEventHandler();
		pointer.setEventHandler(button);
		pointer.send(Pointer.PRESSED, 0);
		CheckHelper.check(getClass(), "helperListener updated no ui", helperListener.updated);
		helperListener.updated = false;
		CheckHelper.check(getClass(), "button pressed state", button.isPressed(), true);

		pointer.send(Pointer.RELEASED, 0);
		CheckHelper.check(getClass(), "helperListener updated no ui", helperListener.updated);
		helperListener.updated = false;
		CheckHelper.check(getClass(), "button pressed state", button.isPressed(), false);

		pointer.send(Pointer.PRESSED, 0);
		CheckHelper.check(getClass(), "helperListener updated no ui", helperListener.updated);
		helperListener.updated = false;
		CheckHelper.check(getClass(), "button pressed state", button.isPressed(), true);

		DesktopEventGenerator desktopGenerator = new DesktopEventGenerator();
		desktopGenerator.setEventHandler(button);
		desktopGenerator.send(PointerEventDispatcher.EXITED);
		CheckHelper.check(getClass(), "helperListener updated no ui", helperListener.updated);
		helperListener.updated = false;
		CheckHelper.check(getClass(), "button pressed state", button.isPressed(), false);

		pointer.send(Pointer.RELEASED, 0);
		CheckHelper.check(getClass(), "helperListener not updated no ui", !helperListener.updated);
		helperListener.updated = false;
		CheckHelper.check(getClass(), "button pressed state", button.isPressed(), false);

		pointer.setEventHandler(originalEventHandler);
	}

	private final class InstrumentedButtonHelperListener implements GenericListener {

		boolean updated;

		@Override
		public void update() {
			this.updated = true;
		}

	}

	private class InstrumentedOnClickListener implements OnClickListener {

		boolean notified;

		@Override
		public void onClick() {
			this.notified = true;
		}

	}

}
