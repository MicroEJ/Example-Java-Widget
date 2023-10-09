# Overview

This demo illustrates the widgets and containers available in the widget library: [ej.library.ui#widget-4.2.0](https://repository.microej.com/modules/ej/library/ui/widget/4.2.0/).

At startup, it shows a list of items.
Clicking on an item opens a new page showing a widget or a set of widgets.
In each of these pages, a button allows to go back to the main list.

For each page, a package exists in the source with the same name.
(Example for the `Label` page: `com.microej.demo.widget.label`.)
The package contains the page and some widgets if necessary.

Each page class contains 2 methods:

- `getContentWidget()` that creates the page content (the hierarchy of widgets and containers),
- `populateStylesheet(CascadingStylesheet)` that creates the styles for the widgets in the page.

Here are the pages:

- `Label`: demonstrates the simple `Label` widget from the widget library.
- `AutoscrollLabel`: demonstrates a label which autoscroll when the label text is longer than label length.
- `Image Widget` : demonstrates how to display an image using `ImageWidget` from the widget library.
- `Animated image`: demonstrates an `AnimatedImage` widget.
- `Slider with value` : demonstrates a slider with displayed value.
- `Slider with progress` : demonstrates a slider with progress.
- `Circular Slider` : demonstrates a circular slider.
- `Double Temperature Slider` : demonstrates a double slider with a dynamic gradient.
- `Button`: demonstrates different styles of the simple `Button` widget from the widget library.
- `Checkbox`: demonstrates a `Checkbox` widget.
- `Radio Button`: demonstrates a `RadioButton` widget.
- `Toggle`: demonstrates a `Toggle` widget animated when its state changes.
- `Progress Bar`: demonstrates a `ProgressBar` widget.
- `Indeterminate Progress Bar`: demonstrates an `IndeterminateProgressBar` widget.
- `Keyboard`: demonstrates a keyboard widget.
- `Circular Indeterminate Progress`: demonstrates a `CircularIndeterminateProgress` widget.
- `Circular Dotted Progress`: demonstrates a `CircularDottedProgress` widget.
- `Circular Progress`: demonstrates a `CircularProgress` widget.
- `Circular Progress With Gradient`: demonstrates a `CircularProgressWithGradient` widget.
- `Wheel` : demonstrates a `Wheel` widget.
- `WheelWithHighlight` : demonstrates a `WheelWithHighlight` widget, where the selected item is highlighted with a larger font.
- `ScrollingTextOnCircle` : demonstrates a `ScrollingTextOnCircle` widget, which draws a scrolling text on a circle.
- `Carousel`: demonstrates a `Carousel` widget.
- `List`: demonstrates the `List` container from the widget library.
- `Grid`: demonstrates the `Grid` container from the widget library.
- `Fixed Grid`: demonstrates the `FixedGrid` container from the widget library.
- `Dock`: demonstrates the `Dock` container from the widget library.
- `Split`: demonstrates a `Split` container.
- `Scrollable List`: demonstrates a `ScrollableList` container.
- `Scrollable Text`: demonstrates displaying a long, scrollable text.
- `Snap Scroll`: demonstrates a scroll that snaps on its children.
- `Secret Scroll`: demonstrates a container with an hidden child that can be shown by scrolling.
- `Buffered Scroll`: demonstrates a scroll that uses the display buffer to scroll faster.
- `Selectable List`: demonstrates a list of items that are selectable with an animation.
- `Line Chart`: demonstrates a `LineChart` widget.
- `Bar Chart`: demonstrates a `BarChart` widget.

# Usage

The main class is [Navigation.java](src/main/java/com/microej/demo/widget/common/Navigation.java).

Two launchers are available:

- `DemoWidget (SIM)`: launches the demo on the simulator of the STM32F7508 board,
- `DemoWidget (EMB)`: builds a binary of the demo for the STM32F7508 board.

# Requirements

This library requires the following Foundation Libraries:

    EDC-1.3, BON-1.4, MICROUI-3.0, DRAWING-1.0

# Dependencies

_All dependencies are retrieved transitively by MicroEJ Module Manager_.


# Source

N/A

# Restrictions

None.

---  
_Copyright 2020-2023 MicroEJ Corp. All rights reserved._  
_Use of this source code is governed by a BSD-style license that can be found with this software._  
