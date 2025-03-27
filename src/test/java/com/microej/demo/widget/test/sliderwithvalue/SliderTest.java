/*
 * Copyright 2021-2025 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */

package com.microej.demo.widget.test.sliderwithvalue;

import com.microej.demo.widget.sliderwithvalue.widget.SliderWithValue;
import ej.microui.MicroUI;
import ej.microui.display.Display;
import ej.mwt.Desktop;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.outline.UniformOutline;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.util.Rectangle;
import ej.widget.container.Dock;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test class for testing that a Slider is fully visible when it is the center child of a dock
 */
public class SliderTest {

    static CascadingStylesheet stylesheet;
    static Desktop desktop;
    static SliderWithValue slider;


    /**
     * Starts MicroUI.
     *
     * @see MicroUI#start
     */
    @BeforeClass
    public static void setUpBeforeClass() {
        MicroUI.start();
    }

    /**
     * Stops MicroUI.
     *
     * @see MicroUI#stop
     */
    @AfterClass
    public static void tearDownAfterClass() {
        MicroUI.stop();
    }

    @Before
    public void setup() throws InterruptedException {
        stylesheet = new CascadingStylesheet();
        EditableStyle style = stylesheet.getDefaultStyle();
        style.setPadding(new UniformOutline(10));
        slider = new SliderWithValue(0,10,5);
        desktop = new Desktop();
        desktop.setStylesheet(stylesheet);
        Dock dock = new Dock();
        dock.setCenterChild(slider);
        desktop.setWidget(dock);
        desktop.requestShow();
        Thread.sleep(1000);
    }

    @Test
    public void sliderFullyVisible() {
        Display display = Display.getDisplay();


        Rectangle contentBounds = slider.getContentBounds();


        int startX = contentBounds.getX();
        int contentWidth = contentBounds.getWidth();
        int endX = contentWidth + startX;

        int startY = contentBounds.getY();
        int contentHeight = contentBounds.getHeight();
        int endY = contentHeight + startY;



        // 5% difference between rows allowed
        float threshold = 0.05f;
        display.getGraphicsContext().setTranslation(0,0);

        // For each row count the number of colored pixels and if they change too rapidly between rows then we can conclude the circle is not drawn correctly
        int lastRowNonWhitePixels = 0;
        for(int y = startY; y < endY; y++){
            int nonWhitePixels = 0;
            for(int x = startX; x < endX; x++){
                if(!isPixelWhite(display,x,y)){
                    nonWhitePixels++;
                }
            }

            // Ignore any line that could be the bar
            if(nonWhitePixels< contentHeight) {
                int differenceBetweenRows = Math.abs(nonWhitePixels - lastRowNonWhitePixels);
                Assert.assertTrue("Slider is not circular", differenceBetweenRows < threshold * contentHeight);

                lastRowNonWhitePixels = nonWhitePixels;
            }
        }



    }

    private static boolean isPixelWhite(Display display, int x, int y) {

        return display.getGraphicsContext().readPixel(x, y) == (0xff000000 | display.getDisplayColor(0xffffff));
    }


}
