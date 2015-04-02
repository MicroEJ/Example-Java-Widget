/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */

package com.is2t.demo.widgets.automaton;

import com.is2t.demo.utilities.automaton.AutomatonFactory;

public class WidgetsAutomatonFactory implements
		AutomatonFactory<WidgetsAutomaton> {

	@Override
	public WidgetsAutomaton createAutomaton() {
		return new WidgetsAutomaton();
	}

	@Override
	public void stopAutomaton(WidgetsAutomaton automaton) {
		// Nothing to do.
	}

}
