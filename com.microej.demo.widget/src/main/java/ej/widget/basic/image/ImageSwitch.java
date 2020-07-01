/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.basic.image;

/**
 * A switch using some images to render.
 */
public class ImageSwitch extends ImageToggle {

	@Override
	protected String getCheckedImagePath() {
		return ImageHelper.getTheme().getSwitchCheckedPath();
	}

	@Override
	protected String getUncheckedImagePath() {
		return ImageHelper.getTheme().getSwitchUncheckedPath();
	}
}
