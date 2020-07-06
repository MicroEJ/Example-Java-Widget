/*
 * Java
 *
 * Copyright 2010-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test.framework;

import ej.microui.MicroUI;

public class WaitForEventRunnable implements Runnable {

	protected volatile boolean isFinished;

	@Override
	public void run() {
		synchronized (this) {
			this.isFinished = true;
			notifyWaitingThread();
		}
	}

	protected void notifyWaitingThread() {
		notify();
	}

	/**
	 * Wait until this runnable has been processed by the DisplayPump
	 */
	public void waitForExecution() {
		synchronized (this) {
			while (!this.isFinished && MicroUI.isStarted()) {
				try {
					wait();
				} catch (InterruptedException ex) {
					continue;
				}
			}
		}
	}

}
