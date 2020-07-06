/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test.basic;

import java.util.Arrays;

import com.is2t.testsuite.support.CheckHelper;

import ej.microui.display.Display;
import ej.widget.basic.AnimatedImage;
import ej.widget.test.framework.Test;
import ej.widget.test.framework.TestHelper;

/**
 *
 */
public class AnimatedImageTest extends Test {

	private static final int PERIOD = 80;

	public static void main(String[] args) {
		TestHelper.launchTest(new AnimatedImageTest());
	}

	@Override
	public void run(Display display) {
		String[] framesArray = new String[] { "/images/cowboy0.png", "/images/cowboy1.png", "/images/cowboy2.png",
				"/images/cowboy3.png" };
		AnimatedImage animatedImage = new AnimatedImage(framesArray, PERIOD);
		CheckHelper.check(getClass(), "New array wrong array", Arrays.equals(animatedImage.getFrames(), framesArray));
		CheckHelper.check(getClass(), "New array wrong period", animatedImage.getPeriod(), PERIOD);

		String frames = "/images/cowboy0.png /images/cowboy1.png /images/cowboy2.png /images/cowboy3.png";
		animatedImage = new AnimatedImage(frames, PERIOD);
		CheckHelper.check(getClass(), "New string wrong array", Arrays.equals(animatedImage.getFrames(), framesArray));
		CheckHelper.check(getClass(), "New string wrong period", animatedImage.getPeriod(), PERIOD);

		animatedImage.setFrames(framesArray);
		CheckHelper.check(getClass(), "Set wrong array", Arrays.equals(animatedImage.getFrames(), framesArray));
		animatedImage.setPeriod(PERIOD * 2);
		CheckHelper.check(getClass(), "Set wrong period", animatedImage.getPeriod(), PERIOD * 2);

	}

}
