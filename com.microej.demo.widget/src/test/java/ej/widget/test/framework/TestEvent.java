/*
 * Copyright 2016-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test.framework;

import ej.annotation.Nullable;
import ej.microui.display.Display;
import ej.microui.event.EventGenerator;
import ej.microui.event.generator.Command;
import ej.microui.event.generator.Pointer;
import ej.mwt.Widget;

public abstract class TestEvent extends Test {
	@Nullable
	private static Pointer ExtraPointer;
	@Nullable
	private static Command ExtraCommand;

	protected Pointer getOrCreatePointer(Display display) {
		Pointer pointer = EventGenerator.get(Pointer.class, 0);
		if (pointer == null) {
			pointer = ExtraPointer;
			if (pointer == null) {
				pointer = new Pointer(display.getWidth(), display.getHeight());
				pointer.addToSystemPool();
				pointer.setEventHandler(display.getEventHandler());
				ExtraPointer = pointer;
			}
		}
		return pointer;
	}

	protected void moveCenter(Display display, Pointer pointer, Widget renderable) {
		moveTo(display, pointer, renderable.getAbsoluteX() + renderable.getWidth() / 2,
				renderable.getAbsoluteY() + renderable.getHeight() / 2);
	}

	protected void moveTo(Display display, Pointer pointer, int x, int y) {
		reset();
		pointer.move(x, y);
		TestHelper.waitForEvent();
	}

	protected void move(Display display, Pointer pointer, int x, int y) {
		moveTo(display, pointer, pointer.getX() + x, pointer.getY() + y);
	}

	protected void release(Display display, Pointer pointer) {
		send(display, pointer, Pointer.RELEASED);
	}

	protected void press(Display display, Pointer pointer) {
		send(display, pointer, Pointer.PRESSED);
	}

	private void send(Display display, Pointer pointer, int action) {
		reset();
		pointer.send(action, 0);
		TestHelper.waitForEvent();
	}

	protected Command getOrCreateCommand(Display display) {
		Command command = EventGenerator.get(Command.class, 0);
		if (command == null) {
			command = ExtraCommand;
			if (command == null) {
				command = new Command();
				command.addToSystemPool();
				command.setEventHandler(display.getEventHandler());
				ExtraCommand = command;
			}
		}
		return command;
	}

	protected void reset() {
	}
}
