/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.basic.image;

/**
 * Define the paths of the images to use for the widgets rendered with some images.
 */
public interface ImageTheme {

	/**
	 * Gets the path of the image corresponding to the checked check box.
	 *
	 * @return the character corresponding to the checked check box.
	 */
	String getCheckboxCheckedPath();

	/**
	 * Gets the path of the image corresponding to the unchecked check box.
	 *
	 * @return the character corresponding to the unchecked check box.
	 */
	String getCheckboxUncheckedPath();

	/**
	 * Gets the path of the image corresponding to the checked switch.
	 *
	 * @return the character corresponding to the checked switch.
	 */
	String getSwitchCheckedPath();

	/**
	 * Gets the path of the image corresponding to the unchecked switch.
	 *
	 * @return the character corresponding to the unchecked switch.
	 */
	String getSwitchUncheckedPath();

	/**
	 * Gets the path of the image corresponding to the checked radio button.
	 *
	 * @return the character corresponding to the checked radio button.
	 */
	String getRadioButtonCheckedPath();

	/**
	 * Gets the path of the image corresponding to the unchecked radio button.
	 *
	 * @return the character corresponding to the unchecked radio button.
	 */
	String getRadioButtonUncheckedPath();

	/**
	 * Gets the path of the image corresponding to the slider horizontal bar.
	 *
	 * @return the character corresponding to the slider horizontal bar.
	 */
	String getSliderHorizontalBarPath();

	/**
	 * Gets the path of the image corresponding to the slider cursor.
	 *
	 * @return the character corresponding to the slider cursor.
	 */
	String getSliderCursorPath();

	/**
	 * Gets the path of the image corresponding to the horizontal progress bar.
	 *
	 * @return the character corresponding to the horizontal progress bar.
	 */
	String getProgressBarHorizontalPath();

	/**
	 * Gets the path of the image corresponding to the vertical progress bar.
	 *
	 * @return the character corresponding to the vertical progress bar.
	 */
	String getProgressBarVerticalPath();
}
