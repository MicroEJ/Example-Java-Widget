# Overview

This demo illustrates the widgets and containers available in the widget library: `ej.library.ui#widget-3.1.0 <https://repository.microej.com/artifacts/ej/library/ui/widget/3.1.0/>`_.

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
- `Button`: demonstrates different styles of the simple `Button` widget from the widget library.
- `Checkbox`: demonstrates a `Checkbox` widget.
- `Radio Button`: demonstrates a `RadioButton` widget.
- `Toggle`: demonstrates a `Toggle` widget animated when its state changes.
- `Progress Bar`: demonstrates a `ProgressBar` widget.
- `Indeterminate Progress Bar`: demonstrates an `IndeterminateProgressBar` widget.
- `List`: demonstrates the `List` container from the widget library.
- `Grid`: demonstrates the `Grid` container from the widget library.
- `Dock`: demonstrates the `Dock` container from the widget library.
- `Split`: demonstrates a `Split` container.
- `Scrollable List`: demonstrates a `Scrollable List` container.

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
_Copyright 2020 MicroEJ Corp. All rights reserved._  
_Use of this source code is governed by a BSD-style license that can be found with this software._  
